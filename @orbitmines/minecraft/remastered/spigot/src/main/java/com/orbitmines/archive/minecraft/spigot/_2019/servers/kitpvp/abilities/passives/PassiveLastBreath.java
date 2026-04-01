package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

public class PassiveLastBreath implements Passive.LowHealthHandler {

    private final PotionBuilder builder = new PotionBuilder(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0);

    @Override
    public void trigger(KitEvent<Event> passiveEvent, Event event, int level) {
        KitPvPPlayer player = passiveEvent.getPlayer();
        PotionBuilder builder = getBuilder(level);

        if (!player.hasPotionEffect(builder.getType()))
            player.addPotionEffect(builder.build());
    }

    @Override
    public void triggerOff(KitEvent<Event> event, int level) {
        KitPvPPlayer player = event.getPlayer();
        PotionEffectType type = getBuilder(level).getType();

        player.removePotionEffect(type);
    }

    @Override
    public double getPercentage(int level) {
        return 0.33D; /* Below 33% */
    }

    public PotionBuilder getBuilder(int level) {
        return builder;
    }
}
