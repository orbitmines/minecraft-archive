package com.orbitmines.minecraft.spigot.servers.fog.kit;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.interactive.InteractiveKit;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.gui.HologramSelector;
import com.orbitmines.minecraft.spigot.servers.fog.gui.RecipeBookGUI;
import com.orbitmines.minecraft.spigot.servers.fog.gui.RunSelector;
import com.orbitmines.minecraft.spigot.servers.fog.gui.SpectatorJoinGUI;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * Kit given to FoG players: a drone toggle + codex on the hotbar, and
 * Spectate + Switch Run pinned to the top-right of the main inventory.
 *
 * <p>Interactions have two dispatch paths — both fire the same action:
 * <ol>
 *   <li>Right-clicking the item while it's held, via
 *       {@link com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemInteraction}
 *       and the already-registered ItemHandlerEvents.</li>
 *   <li>Clicking the item in the inventory view on one of the locked slots,
 *       handled by {@link com.orbitmines.minecraft.spigot.servers.fog.events.LockedSlotsListener}
 *       which calls {@link #fireSlotAction(int, FoGPlayer)}.</li>
 * </ol>
 *
 * <p>Both paths route through the private {@link #fire} helper so the behavior
 * stays symmetric.</p>
 */
public class FoGLobbyKit extends InteractiveKit<FoG, FoGPlayer> {

    public static final int SLOT_DRONE_TOGGLE  = 2;
    public static final int SLOT_RECIPE_BOOK   = 4;
    public static final int SLOT_SPECTATE      = 16;
    public static final int SLOT_SWITCH_RUN    = 17;

    @Getter private final Set<Integer> lockedSlots = new HashSet<>();

    private final FoG fog;

    public FoGLobbyKit(FoG server) {
        this.fog = server;

        lockedSlots.add(SLOT_DRONE_TOGGLE);
        lockedSlots.add(SLOT_RECIPE_BOOK);
        lockedSlots.add(SLOT_SPECTATE);
        lockedSlots.add(SLOT_SWITCH_RUN);

        /* Drone toggle — material flips based on player.isDronesActive(). */
        set(SLOT_DRONE_TOGGLE,
            player -> new ItemBuilder(
                player.isDronesActive() ? Material.REDSTONE_TORCH : Material.LEVER, 1,
                "§b§l" + player.translate("fog", "item.drone_toggle.name")),
            new InteractiveKit.Interaction<FoG, FoGPlayer>(server) {
                @Override
                public void onInteract(FoGPlayer player, PlayerInteractEvent event, ItemStack item) {
                    fire(SLOT_DRONE_TOGGLE, player);
                }
            }.onActionBarHover((player, item) ->
                "§b§l" + player.translate("fog", "item.drone_toggle.name")
                        + "§r §8- "
                        + (player.isDronesActive()
                                ? "§a§l" + player.translate("fog", "drone.active")
                                : "§6§l" + player.translate("fog", "drone.idle"))
                        + " §8- §e§l" + player.translate("spigot", "player.mouse.right_click"))
        );

        set(SLOT_RECIPE_BOOK,
            player -> new ItemBuilder(Material.KNOWLEDGE_BOOK, 1,
                "§a§l" + player.translate("fog", "item.recipe_book.name")),
            new InteractiveKit.Interaction<FoG, FoGPlayer>(server) {
                @Override
                public void onInteract(FoGPlayer player, PlayerInteractEvent event, ItemStack item) {
                    fire(SLOT_RECIPE_BOOK, player);
                }
            }.onActionBarHover((player, item) ->
                "§a§l" + player.translate("fog", "item.recipe_book.name")
                        + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click"))
        );

        set(SLOT_SPECTATE,
            player -> new ItemBuilder(Material.ENDER_EYE, 1,
                "§5§l" + player.translate("fog", "item.spectator_join.name")),
            new InteractiveKit.Interaction<FoG, FoGPlayer>(server) {
                @Override
                public void onInteract(FoGPlayer player, PlayerInteractEvent event, ItemStack item) {
                    fire(SLOT_SPECTATE, player);
                }
            }.onActionBarHover((player, item) ->
                "§5§l" + player.translate("fog", "item.spectator_join.name")
                        + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click"))
        );

        set(SLOT_SWITCH_RUN,
            player -> new ItemBuilder(Material.COMPASS, 1,
                "§2§l" + player.translate("fog", "item.run_switcher.name")),
            new InteractiveKit.Interaction<FoG, FoGPlayer>(server) {
                @Override
                public void onInteract(FoGPlayer player, PlayerInteractEvent event, ItemStack item) {
                    fire(SLOT_SWITCH_RUN, player);
                }
            }.onActionBarHover((player, item) ->
                "§2§l" + player.translate("fog", "item.run_switcher.name")
                        + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click"))
        );
    }

    /** Re-copy just the drone-toggle slot so its material (LEVER↔REDSTONE_TORCH) refreshes. */
    public void refreshDroneToggle(FoGPlayer player) {
        player.getInventory().setItem(SLOT_DRONE_TOGGLE, build(get(SLOT_DRONE_TOGGLE), player));
    }

    /**
     * Set only the always-on top-right items (Switch Run, Spectate). Used on
     * every join / state change so the player always has an escape route — even
     * while spectating someone else's run or when they have no active run at all.
     */
    public void copyAlwaysOnToInventory(FoGPlayer player) {
        player.getInventory().setItem(SLOT_SWITCH_RUN, build(get(SLOT_SWITCH_RUN), player));
        player.getInventory().setItem(SLOT_SPECTATE,   build(get(SLOT_SPECTATE),   player));
    }

    /** Fire the slot's action from an inventory click. */
    public void fireSlotAction(int slot, FoGPlayer player) {
        fire(slot, player);
    }

    private void fire(int slot, FoGPlayer player) {
        /* Blocked while a hologram selector is up — can't double-trigger selectors. */
        if (HologramSelector.hasOpen(player.getUUID())) return;
        switch (slot) {
            case SLOT_DRONE_TOGGLE: player.toggleDroneMode(); break;
            case SLOT_RECIPE_BOOK:  new RecipeBookGUI(fog, player).open(); break;
            case SLOT_SPECTATE:     SpectatorJoinGUI.open(fog, player); break;
            case SLOT_SWITCH_RUN:
                /* Lift the player to the sky anchor first so the selector hologram
                   is visible in open air (same UX as the initial join). */
                fog.teleportToSkyAnchor(player);
                fog.runSync(() -> RunSelector.open(fog, player));
                break;
            default:                /* no-op */
        }
    }
}
