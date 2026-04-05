package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PassiveFireTrail implements Passive.Handler<PlayerMoveEvent> {

    @Override
    public void trigger(KitEvent<PlayerMoveEvent> passiveEvent, PlayerMoveEvent event, int level) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ())
            return;

        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        Block block = to.getWorld().getBlockAt(to.getBlockX(), to.getBlockY(), to.getBlockZ());
        Block below = to.getWorld().getBlockAt(to.getBlockX(), to.getBlockY() - 1, to.getBlockZ());

        if (block.getType() != Material.AIR || !below.getType().isSolid())
            return;

        block.setType(Material.FIRE);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (block.getType() == Material.FIRE) {
                    block.setType(Material.AIR);
                }
            }
        }.runTaskLater(kitPvP.getPlugin(), getDuration(level));
    }

    public int getDuration(int level) {
        switch (level) {
            case 1: return 30;
            case 2: return 45;
            case 3: return 60;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
