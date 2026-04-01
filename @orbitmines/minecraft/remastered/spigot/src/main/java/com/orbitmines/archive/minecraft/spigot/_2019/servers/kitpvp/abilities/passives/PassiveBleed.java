package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotTimer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class PassiveBleed implements Passive.Handler<EntityDamageByEntityEvent> {

    private final Map<LivingEntity, SpigotTimer> bleedSpigotTimers = new HashMap<>();

    private final Map<Block, SpigotTimer> blockSpigotTimers = new HashMap<>();

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getDamager() instanceof LivingEntity) || !(event.getEntity() instanceof LivingEntity))
            return;

        /* There's a chance of the lightning hitting, otherwise move on */
        if (Math.random() >= getChance(level))
            return;

        LivingEntity damager = (LivingEntity) event.getDamager();
        LivingEntity entity = (LivingEntity) event.getEntity();
        double damage = getDamage(level);
        int seconds = getSeconds(level);
        double damagePerTick = damage / (seconds * 2); /* 2 Damage ticks per second */

        if (bleedSpigotTimers.containsKey(entity))
            bleedSpigotTimers.get(entity).cancel();

        KitPvP kitPvP = getKitPvP();

        SpigotTimer timer = new SpigotTimer(kitPvP, Interval.of(TimeUnit.SECOND, seconds), Interval.of(TimeUnit.TICK, 10)) {
            @Override
            public void onFinish() {
                bleedSpigotTimers.remove(entity);
            }

            @Override
            public void onInterval() {
                if (entity.isDead() || entity instanceof Player && (kitPvP.getPlayer((Player) entity) == null || kitPvP.getPlayer((Player) entity).getSelectedKit() == null)) {
                    cancel();
                    bleedSpigotTimers.remove(entity);
                    return;
                }

                entity.damage(damagePerTick);
                entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);

                /* Redstone effect on ground */
                Block block = entity.getLocation().getBlock();

                if (block.getType() != Material.AIR || block.getRelative(BlockFace.DOWN).getBlockData() instanceof Waterlogged /* Is waterlogged if not full block */)
                    return;

                if (blockSpigotTimers.containsKey(block))
                    blockSpigotTimers.get(block).cancel();
                else
                    block.setType(Material.REDSTONE_WIRE);

                SpigotTimer timer = new SpigotTimer(kitPvP, Interval.of(TimeUnit.SECOND, seconds), Interval.of(TimeUnit.SECOND, 1)) {
                    @Override
                    public void onFinish() {
                        block.setType(Material.AIR);
                        blockSpigotTimers.remove(block);
                    }

                    @Override
                    public void onInterval() { }
                };
                timer.start();

                blockSpigotTimers.put(block, timer);
            }
        };
        timer.start();

        bleedSpigotTimers.put(entity, timer);
    }

    private KitPvP getKitPvP() {
        return (KitPvP) KitPvP.getInstance();
    }

    public double getDamage(int level) {
        return 4.0D;
    }

    public double getChance(int level) {
        switch (level) {
            case 1:
                return 0.125D;
            case 2:
                return 0.150D;
            case 3:
                return 0.250D;
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getSeconds(int level) {
        return 4;
    }
}
