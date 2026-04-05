package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

        /* Build drops list and fire PlayerDeathEvent so plugins can modify drops */
        List<ItemStack> drops = new ArrayList<>();
        for (ItemStack item : bukkit.getInventory().getContents()) {
            if (item != null && !item.containsEnchantment(Enchantment.VANISHING_CURSE))
                drops.add(item);
        }

        int droppedExp = Math.min(bukkit.getLevel() * 7, 100);

        PlayerDeathEvent deathEvent = new PlayerDeathEvent(bukkit, event.getDamageSource(), drops, droppedExp, null);
        Bukkit.getPluginManager().callEvent(deathEvent);

        /* Apply drops unless keepInventory is set */
        if (!deathEvent.getKeepInventory()) {
            for (ItemStack item : deathEvent.getDrops()) {
                bukkit.getWorld().dropItem(bukkit.getLocation(), item, drop -> {
                    ThreadLocalRandom r = ThreadLocalRandom.current();
                    drop.setVelocity(new Vector(r.nextDouble(-0.2, 0.2), r.nextDouble(0.1, 0.3), r.nextDouble(-0.2, 0.2)));
                });
            }
            bukkit.getInventory().clear();
        }

        if (!deathEvent.getKeepLevel()) {
            if (deathEvent.getDroppedExp() > 0)
                bukkit.getWorld().spawn(bukkit.getLocation(), org.bukkit.entity.ExperienceOrb.class, orb -> orb.setExperience(deathEvent.getDroppedExp()));
        }

        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.clearPotionEffects();

        if (!deathEvent.getKeepLevel())
            player.clearExperience();

        player.setBackLocation(player.getLocation());

        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(survival.getSpawn());
                player.setVelocity(new Vector(0, 0, 0));
            }
        }.runTaskLater(survival.getPlugin(), 1);

        survival.discord(bot -> bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
            bot.getTextChannel().sendMessage(":skull_crossbones: " + player.getRawName() + " died").queue();
        }));
    }
}
