package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import org.bukkit.event.entity.EntityDamageEvent;

public class PassiveLightningProtection implements Passive.Handler<EntityDamageEvent> {

    /* 12.5% per level chance to evade lightning */

    @Override
    public void trigger(KitEvent<EntityDamageEvent> passiveEvent, EntityDamageEvent event, int level) {
        if (event.getCause() != EntityDamageEvent.DamageCause.LIGHTNING)
            return;

        /* There's a chance of evading the lightning hitting, otherwise move on */
        if (Math.random() >= getChance(level))
            return;

        event.setCancelled(true);

        KitPvPPlayer player = passiveEvent.getPlayer();

        new ActionBar(player, () -> "§e§l" + player.translate("kitpvp", "player.passive.lightning_protection.evaded_lightning"), 30).send();
    }

    public double getChance(int level) {
        return 0.125D * level;
    }
}
