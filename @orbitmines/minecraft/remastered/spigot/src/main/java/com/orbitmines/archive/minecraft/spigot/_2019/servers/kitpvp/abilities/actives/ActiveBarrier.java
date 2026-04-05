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

    private final Cooldown cooldown = new Cooldown(25 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        Location center = player.getLocation();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        /* Spawn spherical cage of leaves */
        List<Block> placedBlocks = new ArrayList<>();
        int radius = 4;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -1; y <= radius * 2; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double dist = Math.sqrt(x * x + Math.pow(y - radius + 1, 2) + z * z);

                    /* Only place blocks on the shell of the sphere */
                    if (dist < radius - 0.5 || dist > radius + 0.5)
                        continue;

                    Block block = center.getWorld().getBlockAt(
                        center.getBlockX() + x,
                        center.getBlockY() + y,
                        center.getBlockZ() + z
                    );
                    if (block.getType() == Material.AIR) {
                        block.setType(Material.OAK_LEAVES);
                        placedBlocks.add(block);
                    }
                }
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
        return cooldown;
    }
}
