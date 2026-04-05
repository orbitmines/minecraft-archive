package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PassiveArthropods implements Passive.Handler<EntityDamageByEntityEvent> {

    private static boolean listenerRegistered = false;

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;

        if (Math.random() >= getChance(level))
            return;

        LivingEntity victim = (LivingEntity) event.getEntity();
        Location spawnLoc = victim.getLocation();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        Player owner = passiveEvent.getPlayer().bukkit();

        /* Register target listener once */
        if (!listenerRegistered) {
            kitPvP.getPlugin().getServer().getPluginManager().registerEvents(new MobTargetListener(), kitPvP.getPlugin());
            listenerRegistered = true;
        }

        /* Spawn spider */
        Spider spider = (Spider) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.SPIDER);
        spider.setCustomName("§8Spiderman");
        spider.setCustomNameVisible(true);
        spider.setTarget(victim);
        spider.setMetadata("kitpvp_owner", new FixedMetadataValue(kitPvP.getPlugin(), owner.getUniqueId().toString()));

        /* Level 2+ spawns a skeleton rider */
        if (level >= 2) {
            Skeleton skeleton = (Skeleton) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.SKELETON);
            skeleton.setCustomName("§8Spiderman");
            skeleton.setCustomNameVisible(true);
            skeleton.setMetadata("kitpvp_owner", new FixedMetadataValue(kitPvP.getPlugin(), owner.getUniqueId().toString()));
            spider.addPassenger(skeleton);

            final Entity skeletonRef = skeleton;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!skeletonRef.isDead()) skeletonRef.remove();
                }
            }.runTaskLater(kitPvP.getPlugin(), 200);
        }

        /* Apply poison */
        victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, getPoisonDuration(level), 1));

        /* Remove spider after 200 ticks */
        final Entity spiderRef = spider;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!spiderRef.isDead()) spiderRef.remove();
            }
        }.runTaskLater(kitPvP.getPlugin(), 200);
    }

    public double getChance(int level) {
        switch (level) {
            case 1: return 0.20D;
            case 2: return 0.25D;
            case 3: return 0.30D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getPoisonDuration(int level) {
        switch (level) {
            case 1: return 20;
            case 2: return 40;
            case 3: return 80;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static class MobTargetListener implements Listener {

        @EventHandler
        public void onTarget(EntityTargetLivingEntityEvent event) {
            if (!event.getEntity().hasMetadata("kitpvp_owner"))
                return;

            if (!(event.getTarget() instanceof Player))
                return;

            String ownerUuid = event.getEntity().getMetadata("kitpvp_owner").get(0).asString();
            if (((Player) event.getTarget()).getUniqueId().toString().equals(ownerUuid)) {
                event.setCancelled(true);
            }
        }
    }
}
