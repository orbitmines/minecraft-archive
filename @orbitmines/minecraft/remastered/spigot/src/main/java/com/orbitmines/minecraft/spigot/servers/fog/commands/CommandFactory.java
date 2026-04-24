package com.orbitmines.minecraft.spigot.servers.fog.commands;

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.gui.DroneFactoryGUI;

public class CommandFactory extends Command<FoG, FoGPlayer> {

    public CommandFactory(FoG server) {
        super(server, Server.FOG, "factory", "drone");

        executes((Executor0<FoG, FoGPlayer>) player -> {
            new DroneFactoryGUI(server, player).open();
        });
    }

    @Override
    public String getDescription(FoGPlayer player) {
        return player.translate("fog", "player.command.factory.description");
    }
}
