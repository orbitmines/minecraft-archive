package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class PassiveKnockup implements Passive.Handler<EntityDamageByEntityEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;

        if (Math.random() >= getChance(level))
            return;

        LivingEntity entity = (LivingEntity) event.getEntity();

        /* Launch victim upward */
        entity.setVelocity(entity.getVelocity().add(new Vector(0, getVelocityY(level), 0)));
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);

        /* Throw falling blocks from the environment around the hit player */
        Location center = entity.getLocation();
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int blockCount = getBlockCount(level);

        for (int i = 0; i < blockCount; i++) {
            int dx = r.nextInt(-2, 3);
            int dz = r.nextInt(-2, 3);

            /* Find a solid block below the victim's feet */
            Block block = center.getWorld().getBlockAt(
                center.getBlockX() + dx,
                center.getBlockY() - 1,
                center.getBlockZ() + dz
            );

            if (block.getType() == Material.AIR || !block.getType().isSolid())
                continue;

            /* Spawn a falling block visual of that block type */
            FallingBlock fb = center.getWorld().spawnFallingBlock(
                block.getLocation().add(0.5, 1.0, 0.5),
                block.getBlockData()
            );
            fb.setVelocity(new Vector(
                r.nextDouble(-0.3, 0.3),
                r.nextDouble(0.5, 1.0),
                r.nextDouble(-0.3, 0.3)
            ));
            fb.setDropItem(false);
            fb.setHurtEntities(false);
            fb.setCancelDrop(true);
        }
    }

    public double getChance(int level) {
        return 0.50D + (0.05D * (level - 1));
    }

    public double getVelocityY(int level) {
        switch (level) {
            case 1: return 0.3D;
            case 2: return 0.33D;
            case 3: return 0.37D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int getBlockCount(int level) {
        switch (level) {
            case 1: return 2;
            case 2: return 3;
            case 3: return 4;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
