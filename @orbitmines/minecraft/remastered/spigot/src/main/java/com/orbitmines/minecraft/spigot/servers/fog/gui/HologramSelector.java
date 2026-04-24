package com.orbitmines.minecraft.spigot.servers.fog.gui;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.freezer.Freezer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.FloatingItem;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.util.HologramTag;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * Reusable hologram-based choice selector.
 *
 * <p>Spawns N {@link FloatingItem}s in a horizontal arc in front of the viewer at
 * eye level and freezes the viewer in place (head-turn only) until one is clicked.
 * Click dispatch goes through {@code NpcEvents}+{@code Npc.InteractAction}.</p>
 *
 * <p>Chain-of-selectors behaviour: opening a selector while another is already
 * up re-uses the existing freeze; resolving only releases the freeze if no new
 * selector follows in the same tick. This prevents the player from being
 * repeatedly teleported between prompts.</p>
 */
public class HologramSelector<T> {

    /** Horizontal radius of the arc — all options sit this distance from the player.
        Kept well within survival reach (~3 blocks) so the player can right-click
        any option without moving. */
    public static final double OPTION_RADIUS = 3.0;
    /** Angular separation between adjacent options, in degrees. Wider = more visual
        breathing room without pushing options out of reach. */
    public static final double OPTION_ANGLE_STEP_DEG = 50.0;
    /**
     * Y-offset from the viewer's feet to the FloatingItem's user-anchor.
     *
     * <p>{@code FloatingItem} (2-arg constructor) places the visible item 1.75
     * above the anchor you pass it, and its invisible clickbox hitbox spans
     * from anchor+0.75 to anchor+2.75 — which wraps the item visual. If we
     * deviate from this geometry (e.g. by passing {@code yOff=0}), the clickbox
     * no longer overlaps the item and right-clicks don't fire.</p>
     *
     * <p>A negative value here keeps the item at chest/belly height while
     * preserving that clickbox/visual relationship. With -0.5 the item appears
     * at feet+1.25 and the clickbox covers feet+0.25 to feet+2.25.</p>
     */
    public static final double OPTION_HEIGHT = -0.5;

    /** UUID → currently-open selector. A player can only have one open at a time. */
    private static final Map<UUID, HologramSelector<?>> OPEN = new HashMap<>();

    public static class Option<T> {
        public final Material icon;
        public final String title;        // coloured display name
        public final String[] description;
        public final T value;

        public Option(Material icon, String title, String[] description, T value) {
            this.icon = icon;
            this.title = title;
            this.description = description == null ? new String[0] : description;
            this.value = value;
        }
    }

    @Getter private final FoG server;
    @Getter private final FoGPlayer viewer;
    @Getter private final List<Option<T>> options;
    @Getter private final BiConsumer<HologramSelector<T>, T> onSelect;

    private final List<FloatingItem<FoGPlayer>> items = new ArrayList<>();
    private final Set<Integer> ourEntityIds = new HashSet<>();
    private boolean resolved;

    public HologramSelector(FoG server, FoGPlayer viewer, List<Option<T>> options,
                            BiConsumer<HologramSelector<T>, T> onSelect) {
        this.server = server;
        this.viewer = viewer;
        this.options = options;
        this.onSelect = onSelect;
    }

    public static boolean hasOpen(UUID uuid) {
        HologramSelector<?> sel = OPEN.get(uuid);
        return sel != null && !sel.resolved;
    }

    /** Opens the selector. Must be called on the main thread. */
    public void open() {
        /* If another selector was already up for this player, close it first. */
        HologramSelector<?> prev = OPEN.get(viewer.getUUID());
        if (prev != null && prev != this) prev.destroy();

        OPEN.put(viewer.getUUID(), this);

        /* Freeze the viewer in place (head-turn only). No teleport, no float-up. */
        if (!viewer.isFrozen()) {
            viewer.freeze(Freezer.MOVE);
        }

        Location anchor = viewer.getLocation().clone().add(0, OPTION_HEIGHT, 0);

        /* Arc layout: all options sit at OPTION_RADIUS from the viewer, spread
           across an angular range centred on the viewer's facing direction.
           Bukkit yaw: yaw=0 faces +Z ("south"); forward = (-sin(yaw), 0, cos(yaw)). */
        double baseYawRad = Math.toRadians(viewer.getLocation().getYaw());
        double stepRad = Math.toRadians(OPTION_ANGLE_STEP_DEG);

        int n = options.size();
        double mid = (n - 1) / 2.0;
        for (int i = 0; i < n; i++) {
            Option<T> opt = options.get(i);
            double angle = baseYawRad + (i - mid) * stepRad;
            double dx = -Math.sin(angle) * OPTION_RADIUS;
            double dz =  Math.cos(angle) * OPTION_RADIUS;
            Location optLoc = anchor.clone().add(new Vector(dx, 0, dz));
            render(opt, optLoc);
        }
    }

    private void render(Option<T> opt, Location at) {
        /* Apply the title to the ItemStack itself (shown on hover). If title is
           null, the item is a bare floating block/item with no name. */
        ItemStack stack = opt.title == null
                ? new ItemBuilder(opt.icon).build()
                : new ItemBuilder(opt.icon, 1, opt.title).build();

        /* 2-arg FloatingItem — its internal yOff=1.75 is what keeps the invisible
           clickbox aligned over the item visual. See OPTION_HEIGHT's javadoc. */
        FloatingItem<FoGPlayer> floating = new FloatingItem<>(() -> stack, at);
        floating.setInteractAction((event, player) -> {
            if (resolved) return;
            if (!player.getUUID().equals(viewer.getUUID())) return;
            resolve(opt.value);
        });

        /* Title line above the item — skipped entirely for "label-less" options. */
        if (opt.title != null) {
            floating.addLine(() -> opt.title, false);
        }
        for (String line : opt.description) {
            if (line == null) continue;
            floating.addLine(() -> "§7" + line, false);
        }
        floating.create(viewer.bukkit());

        /* Tag every entity (armor stands + the floating item) so the world-load
           cleanup can remove any leftovers without touching player-placed ones. */
        for (Entity e : floating.getEntities()) {
            HologramTag.tag(e);
        }

        items.add(floating);
        ourEntityIds.add(floating.getItem() == null ? -1 : floating.getItem().getEntityId());
    }

    private void resolve(T value) {
        if (resolved) return;
        resolved = true;

        destroyRenderedOnly();

        if (onSelect != null) onSelect.accept(this, value);

        /* If the callback didn't open a successor selector, release the freeze.
           If it did, the new selector owns the freeze and we leave it alone. */
        HologramSelector<?> now = OPEN.get(viewer.getUUID());
        if (now == this) {
            OPEN.remove(viewer.getUUID());
            if (viewer.isFrozen()) viewer.clearFreeze();
        }
    }

    /** Destroy without firing the callback (used when a newer selector replaces us). */
    public void destroy() {
        if (resolved) return;
        resolved = true;
        destroyRenderedOnly();
        if (OPEN.get(viewer.getUUID()) == this) OPEN.remove(viewer.getUUID());
    }

    public void cancel() {
        destroy();
        if (viewer.isFrozen()) viewer.clearFreeze();
    }

    private void destroyRenderedOnly() {
        for (FloatingItem<FoGPlayer> it : items) {
            try { it.destroy(); } catch (Exception ignored) {}
        }
        items.clear();
    }
}
