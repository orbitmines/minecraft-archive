package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DeathEvent implements Listener {

    private Survival survival;

    public DeathEvent(Survival survival) {
        this.survival = survival;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        SurvivalPlayer player = survival.getPlayer(event.getEntity());

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
        }.runTaskLater(survival, 1);

        SpigotDiscordBot bot = survival.getDiscordBot();
        bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
            bot.getTextChannel().sendMessage(":skull_crossbones:" + event.getDeathMessage().replaceAll(player.getRawName(), bot.getPlayerDisplay(player, emote, player.getRawName()))).queue();
        });
    }
}
