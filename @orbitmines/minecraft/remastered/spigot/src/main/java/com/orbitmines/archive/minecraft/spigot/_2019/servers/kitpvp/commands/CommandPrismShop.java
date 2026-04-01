package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui.KitPvPPrismSolarShopGUI;

public class CommandPrismShop extends Command<KitPvP, KitPvPPlayer> {

    public CommandPrismShop(KitPvP plugin) {
        super(plugin, Server.KITPVP, "prismshop", "solarshop");

        executes((Executor0<KitPvP, KitPvPPlayer>) (player) -> {
            new KitPvPPrismSolarShopGUI(player).open();
        });
    }

    @Override
    public String getDescription(KitPvPPlayer player) {
        return "Open the prism and solar shop";
    }
}
