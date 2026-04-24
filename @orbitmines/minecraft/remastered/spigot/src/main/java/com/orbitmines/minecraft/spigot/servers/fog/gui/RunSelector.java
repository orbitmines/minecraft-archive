package com.orbitmines.minecraft.spigot.servers.fog.gui;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Top-level run selector. Shown when the player joins FoG without an active
 * run, or when they right-click / inventory-click the Switch Run kit item.
 *
 * <p>If the player already has an active run, a leftmost "Continue" option
 * appears (orange, spruce hanging sign) so they can resume it without picking
 * one of the other flows.</p>
 */
public class RunSelector {

    public enum Action { CONTINUE, NEW_RUN, COOP_JOIN, SPECTATE_RUN }

    private static final String[] NO_DESCRIPTION = new String[0];

    public static void open(FoG server, FoGPlayer viewer) {
        List<HologramSelector.Option<Action>> options = new ArrayList<>();

        boolean hasActiveRun = viewer.getActiveRun() != null
                || (viewer.getPlayerModel() != null && viewer.getPlayerModel().getActiveRunId() != null);
        if (hasActiveRun) {
            options.add(new HologramSelector.Option<>(
                    Material.SPRUCE_HANGING_SIGN,
                    "§6§l" + viewer.translate("fog", "run.selector.continue.name"),
                    NO_DESCRIPTION,
                    Action.CONTINUE));
        }

        options.add(new HologramSelector.Option<>(
                Material.GRASS_BLOCK,
                "§a§l" + viewer.translate("fog", "run.selector.new.name"),
                NO_DESCRIPTION,
                Action.NEW_RUN));
        options.add(new HologramSelector.Option<>(
                Material.DARK_OAK_BOAT,
                "§2§l" + viewer.translate("fog", "run.selector.coop.name"),
                NO_DESCRIPTION,
                Action.COOP_JOIN));
        options.add(new HologramSelector.Option<>(
                Material.ENDER_EYE,
                "§5§l" + viewer.translate("fog", "run.selector.spectate.name"),
                NO_DESCRIPTION,
                Action.SPECTATE_RUN));

        new HologramSelector<>(server, viewer, options, (sel, action) -> {
            switch (action) {
                case CONTINUE: {
                    Run current = viewer.getActiveRun();
                    Long runId = current != null ? current.getId()
                            : viewer.getPlayerModel() != null ? viewer.getPlayerModel().getActiveRunId() : null;
                    if (runId != null) server.getRunManager().joinRun(viewer, runId);
                    break;
                }
                case NEW_RUN:
                    DifficultySelector.open(server, viewer);
                    break;
                case COOP_JOIN:
                    CoopJoinGUI.open(server, viewer);
                    break;
                case SPECTATE_RUN:
                    SpectatorJoinGUI.open(server, viewer);
                    break;
            }
        }).open();
    }
}
