package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.MetaData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class SpawnerMinerEvents implements Listener {

    private Survival survival;

    public SpawnerMinerEvents(Survival survival) {
        this.survival = survival;
    }

    /* Stop players from changing spawner graphType */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.isCancelled() || event.getClickedBlock().getType() != Material.SPAWNER)
            return;

        Player player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand != null && ItemUtils.EGGS.contains(mainHand.getType()) || offHand != null && ItemUtils.EGGS.contains(offHand.getType())) {
            event.setCancelled(true);
            PlayerUtils.updateInventory(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getBlock().getType() != Material.SPAWNER) {
            if (Survival.SPAWNER_MINER.equals(item)) {
                SurvivalPlayer omp = survival.getPlayer(player);

                new ActionBar(omp.bukkit(), () -> "§c§l" + omp.translate("survival", "player.spawner_miner.only_spawners", Survival.SPAWNER_MINER.getDisplayName() + "§c§l"), 100).send();
                event.setCancelled(true);
                omp.updateInventory();
            }

            return;
        }

        if (!Survival.SPAWNER_MINER.equals(item))
            return;

        Mob mob = survival.getNms().world().getSpawner(event.getBlock().getLocation());

        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        event.setExpToDrop(0);

        player.getWorld().dropItemNaturally(event.getBlock().getLocation(), getSpawner(mob));

        player.getInventory().setItemInMainHand(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent event) {
        if (event.isCancelled() || event.getBlock().getType() != Material.SPAWNER)
            return;

        ItemStack item = event.getItemInHand();
        Mob mob = getMob(item);

        survival.getNms().world().setSpawner(event.getBlockPlaced().getLocation(), mob);
    }

    private ItemStack getSpawner(Mob mob) {
        ItemStack item = new ItemBuilder(Material.SPAWNER, 1, "§d§l" + mob.getName() + " Spawner").build();
        MetaData metaData = survival.getNms().customItem().getMetaData(item);

        return metaData.set("orbitmines_survival", "spawner", mob.toString());
    }

    private Mob getMob(ItemStack itemStack) {
        if (itemStack == null)
            return Mob.PIG;

        String mob = survival.getNms().customItem().getMetaData(itemStack).getAsString("orbitmines_survival", "spawner");

        if (mob == null)
            return Mob.PIG;

        return Mob.valueOf(mob);
    }
}
