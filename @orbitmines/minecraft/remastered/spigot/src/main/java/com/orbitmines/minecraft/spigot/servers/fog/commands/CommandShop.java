package com.orbitmines.minecraft.spigot.servers.fog.commands;

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.gui.ShopGUI;

public class CommandShop extends Command<FoG, FoGPlayer> {

    public CommandShop(FoG server) {
        super(server, Server.FOG, "fogshop");

        executes((Executor0<FoG, FoGPlayer>) player -> {
            new ShopGUI(server, player).open();
        });
    }

    @Override
    public String getDescription(FoGPlayer player) {
        return player.translate("fog", "player.command.shop.description");
    }
}
