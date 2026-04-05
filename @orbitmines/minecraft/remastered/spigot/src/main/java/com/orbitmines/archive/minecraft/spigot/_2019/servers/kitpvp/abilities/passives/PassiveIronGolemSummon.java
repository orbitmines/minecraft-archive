package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PassiveIronGolemSummon implements Passive.Handler<Event> {

    private static final Map<UUID, IronGolem> golems = new HashMap<>();
    private static boolean listenerRegistered = false;

    @Override
    public void trigger(KitEvent<Event> passiveEvent, Event event, int level) {
        KitPvPPlayer omp = passiveEvent.getPlayer();
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        removeGolem(player.getUniqueId());

        if (!listenerRegistered) {
            kitPvP.getPlugin().getServer().getPluginManager().registerEvents(new SnowballTargetListener(), kitPvP.getPlugin());
            listenerRegistered = true;
        }

        IronGolem golem = player.getWorld().spawn(player.getLocation().add(1, 0, 1), IronGolem.class);
        golem.setPlayerCreated(true);
        golem.setMetadata("kitpvp_owner", new FixedMetadataValue(kitPvP.getPlugin(), player.getUniqueId().toString()));
        golems.put(player.getUniqueId(), golem);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || player.isDead() || golem.isDead() || !golem.isValid()) {
                    removeGolem(player.getUniqueId());
                    cancel();
                    return;
                }

                double distance = golem.getLocation().distance(player.getLocation());
                if (distance > 15) {
                    golem.teleport(player.getLocation().add(1, 0, 1));
                }
            }
        }.runTaskTimer(kitPvP.getPlugin(), 20, 20);
    }

    public static IronGolem getGolem(UUID playerUUID) {
        IronGolem golem = golems.get(playerUUID);
        if (golem != null && (golem.isDead() || !golem.isValid())) {
            golems.remove(playerUUID);
            return null;
        }
        return golem;
    }

    public static void removeGolem(UUID playerUUID) {
        IronGolem golem = golems.remove(playerUUID);
        if (golem != null && !golem.isDead() && golem.isValid()) {
            golem.remove();
        }
    }

    public static class SnowballTargetListener implements Listener {

        @EventHandler
        public void onSnowballHit(ProjectileHitEvent event) {
            if (!(event.getEntity() instanceof Snowball))
                return;

            if (!(event.getEntity().getShooter() instanceof Player))
                return;

            if (!(event.getHitEntity() instanceof LivingEntity))
                return;

            Player shooter = (Player) event.getEntity().getShooter();
            LivingEntity target = (LivingEntity) event.getHitEntity();

            /* Deal damage to the target */
            target.damage(3.0, shooter);

            IronGolem golem = getGolem(shooter.getUniqueId());
            if (golem == null)
                return;

            golem.setTarget(target);
        }
    }
}
