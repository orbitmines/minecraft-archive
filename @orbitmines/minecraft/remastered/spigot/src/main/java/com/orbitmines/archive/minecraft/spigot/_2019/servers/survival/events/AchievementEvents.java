package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.StoredProgressAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.SurvivalAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AchievementEvents implements Listener {

    private final Survival survival;

    private final List<DroppedItem> droppedItems;

    public AchievementEvents(Survival survival) {
        this.survival = survival;

        this.droppedItems = new ArrayList<>();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.DIAMOND_ORE)
            return;

        SurvivalPlayer omp = survival.getPlayer(event.getPlayer());

        DroppedItem item = new DroppedItem(omp, event.getBlock().getLocation());
        droppedItems.add(item);

        new BukkitRunnable() {
            @Override
            public void run() {
                droppedItems.remove(item);
            }
        }.runTaskLater(survival.getPlugin(), 3);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawn(ItemSpawnEvent event) {
        if (event.getEntity().getItemStack().getType() != Material.DIAMOND)
            return;

        for (DroppedItem droppedItem : droppedItems) {
            if (!droppedItem.equals(event.getLocation()))
                continue;

            StoredProgressAchievement handler = (StoredProgressAchievement) SurvivalAchievement.DIAMONDS.getHandler();
            handler.progress(droppedItem.omp, event.getEntity().getItemStack().getAmount(), true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExp(PlayerExpChangeEvent event) {
        SurvivalPlayer player = survival.getPlayer(event.getPlayer());

        StoredProgressAchievement handler = (StoredProgressAchievement) SurvivalAchievement.LOADS_OF_EXPERIENCE.getHandler();
        handler.setHighest(player, player.getLevel(), true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof WitherSkeleton))
            return;

        for (ItemStack item : event.getDrops()) {
            if (item.getType() != Material.WITHER_SKELETON_SKULL)
                continue;

            SurvivalPlayer player = survival.getPlayer(event.getEntity().getKiller());

            StoredProgressAchievement handler = (StoredProgressAchievement) SurvivalAchievement.TIME_WITHERED_AWAY.getHandler();
            handler.progress(player, 1, true);
        }
    }

    private class DroppedItem {

        private final SurvivalPlayer omp;
        private final Location location;

        public DroppedItem(SurvivalPlayer omp, Location location) {
            this.omp = omp;
            this.location = location;
        }

        public boolean equals(Location location) {
            return BlockUtils.equals(this.location.getBlock(), location.getBlock());
        }
    }
}
