package com.orbitmines.minecraft.spigot.servers.fog.gui;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * List of running players' runs. A scrollable inventory list fits better than
 * holograms here because there may be many candidates.
 *
 * <p>If the viewer closes this GUI without clicking anyone, the RunSelector is
 * re-opened — cancelling spectate drops them back at the top-level choice, same
 * UX as cancelling the coop GUI.</p>
 */
public class SpectatorJoinGUI extends GUI<FoGPlayer> {

    private static final Map<UUID, SpectatorJoinGUI> OPEN = new HashMap<>();

    private boolean spectateLaunched;

    public static void open(FoG server, FoGPlayer viewer) {
        SpectatorJoinGUI gui = new SpectatorJoinGUI(server, viewer);
        OPEN.put(viewer.getUUID(), gui);
        gui.open();
    }

    /**
     * Called from {@link com.orbitmines.minecraft.spigot.servers.fog.events.RunGUICloseListener}.
     * If the closed inventory matches the registered SpectatorJoinGUI and the
     * viewer didn't commit to spectating anyone, re-open the RunSelector.
     */
    public static void onInventoryClose(FoG server, FoGPlayer viewer, Inventory closed) {
        SpectatorJoinGUI gui = OPEN.get(viewer.getUUID());
        if (gui == null) return;
        if (gui.getInventory() != closed) return;
        OPEN.remove(viewer.getUUID());
        if (!gui.spectateLaunched) {
            server.runSync(() -> RunSelector.open(server, viewer));
        }
    }

    private SpectatorJoinGUI(FoG server, FoGPlayer viewer) {
        super(54, "§0§l" + viewer.translate("fog", "gui.spectator_join.title"), viewer);

        int slot = 0;
        for (FoGPlayer other : server.getPlayers()) {
            if (other == viewer) continue;
            Run run = other.getActiveRun();
            if (run == null) continue;
            final Run finalRun = run;
            final String otherName = other.getName();
            set(slot++, new Item<FoGPlayer, MutableItemBuilder>(() ->
                new ItemBuilder(Material.PLAYER_HEAD, 1, "§e§l" + otherName)
                    .addLore(viewer.translate("fog", "gui.spectator_join.run", finalRun.getId()))
                    .addLore(viewer.translate("fog", "gui.spectator_join.difficulty", finalRun.getDifficulty().getColoredName(viewer)))
                    .addLore(" ")
                    .addLore("§a§l" + viewer.translate("fog", "gui.spectator_join.click_to_spectate")),
                event -> {
                    this.spectateLaunched = true;
                    OPEN.remove(viewer.getUUID());
                    viewer.closeInventory();
                    server.getRunManager().spectateRun(viewer, finalRun.getId());
                }
            ));
            if (slot >= 45) break;
        }

        for (int s = slot; s < getInventory().getSize(); s++) {
            set(s, new Item<FoGPlayer, MutableItemBuilder>(() ->
                new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, "§f")));
        }
    }
}
