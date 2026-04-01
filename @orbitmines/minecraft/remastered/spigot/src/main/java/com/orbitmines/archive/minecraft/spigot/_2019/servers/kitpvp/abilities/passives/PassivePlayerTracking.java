package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PassivePlayerTracking implements Passive.Handler<Event> {

    @Override
    public void trigger(KitEvent<Event> passiveEvent, Event event, int level) {
        Player player = passiveEvent.getPlayer().bukkit();

//        Player closest = PlayerUtils.getClosestPlayer(
//            player.getLocation(),
//            p -> !p.equals(player) && p.getGameMode() == GameMode.SURVIVAL && !p.hasPotionEffect(PotionEffectType.INVISIBILITY)
//        );
//
//        if (closest == null)
//            return;

//        player.setCompassTarget(closest.getLocation());
    }
}
