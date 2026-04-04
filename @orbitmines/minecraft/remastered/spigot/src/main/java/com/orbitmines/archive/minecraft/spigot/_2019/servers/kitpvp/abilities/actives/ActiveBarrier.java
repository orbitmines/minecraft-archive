package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ActiveBarrier implements Active.Handler {

    private final Cooldown cooldownLow = new Cooldown(100 * 1000);
    private final Cooldown cooldownHigh = new Cooldown(80 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        Location center = player.getLocation();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        /* Spawn barrier blocks */
        List<Block> placedBlocks = new ArrayList<>();
        int[][] offsets = {
            {-2, 0, -2}, {-1, 0, -2}, {0, 0, -2}, {1, 0, -2}, {2, 0, -2},
            {-2, 0, -1}, {2, 0, -1},
            {-2, 0, 0}, {2, 0, 0},
            {-2, 0, 1}, {2, 0, 1},
            {-2, 0, 2}, {-1, 0, 2}, {0, 0, 2}, {1, 0, 2}, {2, 0, 2},
            {-1, 3, 0}, {1, 3, 0}, {0, 3, -1}, {0, 3, 1}
        };

        for (int[] offset : offsets) {
            Block block = center.getWorld().getBlockAt(
                center.getBlockX() + offset[0],
                center.getBlockY() + offset[1],
                center.getBlockZ() + offset[2]
            );
            if (block.getType() == Material.AIR) {
                block.setType(Material.OAK_LEAVES);
                placedBlocks.add(block);
            }
        }

        /* Apply resistance */
        omp.addPotionEffect(new PotionBuilder(PotionEffectType.RESISTANCE, getDuration(level), getAmplifier(level)).build());
        omp.playSound(Sound.BLOCK_WOOD_PLACE);

        /* Remove blocks after 100 ticks */
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Block block : placedBlocks) {
                    if (block.getType() == Material.OAK_LEAVES)
                        block.setType(Material.AIR);
                }
            }
        }.runTaskLater(kitPvP.getPlugin(), 100);
    }

    private int getDuration(int level) {
        switch (level) {
            case 1: return 100;
            case 2: return 120;
            case 3: return 120;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int getAmplifier(int level) {
        return level >= 3 ? 1 : 0;
    }

    public PotionBuilder getBuilder(int level) {
        return new PotionBuilder(PotionEffectType.RESISTANCE, getDuration(level), getAmplifier(level));
    }

    @Override
    public Cooldown getCooldown(int level) {
        return level >= 3 ? cooldownHigh : cooldownLow;
    }
}
