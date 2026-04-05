package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PassiveUndeath implements Passive.Handler<ProjectileHitEvent> {

    @Override
    public void trigger(KitEvent<ProjectileHitEvent> passiveEvent, ProjectileHitEvent event, int level) {
        Location loc = event.getEntity().getLocation();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        LivingEntity target = event.getHitEntity() instanceof LivingEntity ? (LivingEntity) event.getHitEntity() : null;
        String ownerUuid = passiveEvent.getPlayer() != null ? passiveEvent.getPlayer().bukkit().getUniqueId().toString() : null;

        List<Entity> spawned = new ArrayList<>();

        /* Spawn zombies */
        for (int i = 0; i < 3; i++) {
            Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
            zombie.setCustomName("§4Undeath Knight");
            zombie.setCustomNameVisible(true);
            zombie.setAdult();
            if (target != null) zombie.setTarget(target);
            if (ownerUuid != null)
                zombie.setMetadata("kitpvp_owner", new FixedMetadataValue(kitPvP.getPlugin(), ownerUuid));
            spawned.add(zombie);
        }

        /* Level 2+ spawn skeletons */
        int skeletonCount = level >= 3 ? 2 : level >= 2 ? 1 : 0;
        for (int i = 0; i < skeletonCount; i++) {
            Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
            skeleton.setCustomName("§4Undeath Archer");
            skeleton.setCustomNameVisible(true);
            if (target != null) skeleton.setTarget(target);
            if (ownerUuid != null)
                skeleton.setMetadata("kitpvp_owner", new FixedMetadataValue(kitPvP.getPlugin(), ownerUuid));
            spawned.add(skeleton);
        }

        loc.getWorld().playSound(loc, Sound.ENTITY_WITHER_AMBIENT, 1.0f, 2.0f);

        /* Remove after 300 ticks */
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : spawned) {
                    if (!entity.isDead()) entity.remove();
                }
            }
        }.runTaskLater(kitPvP.getPlugin(), 300);
    }
}
