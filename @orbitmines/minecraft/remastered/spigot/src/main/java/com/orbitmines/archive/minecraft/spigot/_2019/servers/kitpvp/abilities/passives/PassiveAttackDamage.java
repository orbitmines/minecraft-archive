package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import org.bukkit.event.Event;

public class PassiveAttackDamage implements Passive.Handler<Event> {

    @Override
    public void trigger(KitEvent passiveEvent, Event event, int level) {
        KitPvPPlayer player = passiveEvent.getPlayer();

        player.server().getNms().entity().setAttribute(
            player.bukkit(), EntityNms.Attribute.ATTACK_DAMAGE, level
        );
    }
}
