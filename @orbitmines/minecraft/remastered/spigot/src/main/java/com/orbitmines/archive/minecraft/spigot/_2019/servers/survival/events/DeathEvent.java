package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DeathEvent implements Listener {

    private Survival survival;

    public DeathEvent(Survival survival) {
        this.survival = survival;
    }

    /* Intercept lethal damage to prevent actual death — in 26.1 PlayerDeathEvent + setHealth
       leaves the entity in a corrupt state where the tracker stops sending movement updates. */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLethalDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player bukkit = (Player) event.getEntity();
        if (bukkit.getHealth() - event.getFinalDamage() > 0)
            return;

        /* This hit would kill — prevent it */
        event.setCancelled(true);

        SurvivalPlayer player = survival.getPlayer(bukkit);

        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);

        player.setBackLocation(player.getLocation());

        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(survival.getSpawn());
                player.setVelocity(new Vector(0, 0, 0));
                player.setFireTicks(0);

                player.clearExperience();
                player.clearPotionEffects();
            }
        }.runTaskLater(survival.getPlugin(), 1);

        survival.discord(bot -> bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
            bot.getTextChannel().sendMessage(":skull_crossbones: " + player.getRawName() + " died").queue();
        }));
    }
}
