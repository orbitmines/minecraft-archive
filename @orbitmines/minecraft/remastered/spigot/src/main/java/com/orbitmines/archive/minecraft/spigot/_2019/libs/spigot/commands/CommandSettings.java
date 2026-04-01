package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.SettingsGUI;

public class CommandSettings<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandSettings(S plugin) {
        super(plugin, "settings", "setting", "prefs", "preferences");

        executes((Executor0<S, P>) player -> {
            new SettingsGUI<>(player, player).open();
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.settings.description");
    }
}
