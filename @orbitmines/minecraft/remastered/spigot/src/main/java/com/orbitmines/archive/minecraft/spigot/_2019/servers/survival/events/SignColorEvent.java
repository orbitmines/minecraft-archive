package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class SignColorEvent implements Listener {

    private final Survival survival;

    public SignColorEvent(Survival survival) {
        this.survival = survival;
    }

    @EventHandler
    public void onChange(SignChangeEvent event) {
        SurvivalPlayer omp = survival.getPlayer(event.getPlayer());

        if (!omp.isEligible(VipRank.EMERALD))
            return;

        for (int i = 0; i < 4; i++) {
            event.setLine(i, ChatColor.translateAlternateColorCodes('&', event.getLine(i)));
        }
    }
}
