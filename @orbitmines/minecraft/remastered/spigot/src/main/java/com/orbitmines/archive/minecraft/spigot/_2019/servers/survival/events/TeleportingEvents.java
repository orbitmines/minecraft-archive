package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.teleportable.Teleportable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.Title;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TeleportingEvents implements Listener {

    private Survival survival;

    public TeleportingEvents(Survival survival) {
        this.survival = survival;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        SurvivalPlayer player = survival.getPlayer(event.getPlayer());

        if (player.getTeleportingTo() == null)
            return;

        if (!player.isMoving(event))
            return;

        Teleportable teleportable = player.getTeleportingTo();

        String name = teleportable.getColor().getCc() + "§l" + teleportable.getName() + "§7§l";
        new Title<SurvivalPlayer>(p -> "", p -> "§7§l" + p.translate("survival", "player.cancelled_teleport", name), 0, 40, 20).send(player);

        player.setTeleportingTo(null);
        player.getTeleportingTimer().cancel();
        player.setTeleportingTimer(null);
    }
}
