package com.orbitmines.minecraft.spigot.servers.fog.gui;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.run.Difficulty;

import java.util.ArrayList;
import java.util.List;

/**
 * Hologram-based difficulty picker. Like the RunSelector, each option shows a
 * title (Normal / Hard / Hardcore) above the floating icon — descriptions are
 * omitted.
 */
public class DifficultySelector {

    private static final String[] NO_DESCRIPTION = new String[0];

    public static void open(FoG server, FoGPlayer viewer) {
        List<HologramSelector.Option<Difficulty>> options = new ArrayList<>();
        for (Difficulty d : Difficulty.values()) {
            options.add(new HologramSelector.Option<>(
                    d.getIcon(),
                    d.getColoredName(viewer),
                    NO_DESCRIPTION,
                    d
            ));
        }

        new HologramSelector<>(server, viewer, options,
                (sel, difficulty) -> server.getRunManager().startNewRun(viewer, difficulty)
        ).open();
    }
}
