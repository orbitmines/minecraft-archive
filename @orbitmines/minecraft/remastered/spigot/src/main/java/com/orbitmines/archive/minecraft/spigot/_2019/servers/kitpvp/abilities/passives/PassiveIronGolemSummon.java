package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
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
        golem.setPlayerCreated(false);
        golem.setMetadata("kitpvp_owner", new FixedMetadataValue(kitPvP.getPlugin(), player.getUniqueId().toString()));
        golems.put(player.getUniqueId(), golem);

        new BukkitRunnable() {
            private int deathTicks = -1;

            @Override
            public void run() {
                if (golem.isDead() || !golem.isValid()) {
                    golems.remove(player.getUniqueId());
                    cancel();
                    return;
                }

                /* Despawn 5 seconds after owner dies or disconnects */
                KitPvPPlayer omp2 = player.isOnline() ? kitPvP.getPlayer(player) : null;
                if (!player.isOnline() || omp2 == null || omp2.getSelectedKit() == null) {
                    if (deathTicks < 0) deathTicks = 0;
                    deathTicks++;
                    if (deathTicks >= 5) {
                        removeGolem(player.getUniqueId());
                        cancel();
                    }
                    return;
                } else {
                    deathTicks = -1;
                }

                /* Teleport if too far */
                double distance = golem.getLocation().distance(player.getLocation());
                if (distance > 15) {
                    golem.teleport(player.getLocation().add(1, 0, 1));
                }

                /* Auto-target nearest non-owner player within 10 blocks */
                if (golem.getTarget() == null || golem.getTarget().isDead()) {
                    Player nearest = null;
                    double nearestDist = 10.0;
                    for (Player nearby : golem.getWorld().getPlayers()) {
                        if (nearby.equals(player))
                            continue;
                        KitPvPPlayer nearbyOmp = kitPvP.getPlayer(nearby);
                        if (nearbyOmp.getSelectedKit() == null || nearbyOmp.isSpectator())
                            continue;
                        double d = nearby.getLocation().distance(golem.getLocation());
                        if (d < nearestDist) {
                            nearestDist = d;
                            nearest = nearby;
                        }
                    }
                    if (nearest != null) {
                        golem.setTarget(nearest);
                    }
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

            /* Don't damage own golem */
            if (target instanceof IronGolem && target.equals(getGolem(shooter.getUniqueId())))
                return;

            /* Deal damage to the target */
            target.damage(6.0, shooter);

            IronGolem golem = getGolem(shooter.getUniqueId());
            if (golem == null)
                return;

            golem.setTarget(target);
        }

        /* Prevent golem from ever targeting its owner */
        @EventHandler
        public void onGolemTarget(EntityTargetEvent event) {
            if (!(event.getEntity() instanceof IronGolem))
                return;

            if (!(event.getTarget() instanceof Player))
                return;

            IronGolem golem = (IronGolem) event.getEntity();
            Player target = (Player) event.getTarget();

            List<MetadataValue> meta = golem.getMetadata("kitpvp_owner");
            if (!meta.isEmpty() && meta.get(0).asString().equals(target.getUniqueId().toString()))
                event.setCancelled(true);
        }

        /* Cancel damage from owner to their own golem (punching) */
        @EventHandler
        public void onOwnerDamageGolem(EntityDamageByEntityEvent event) {
            if (!(event.getEntity() instanceof IronGolem))
                return;

            Player damager = null;
            if (event.getDamager() instanceof Player)
                damager = (Player) event.getDamager();
            else if (event.getDamager() instanceof Snowball && ((Snowball) event.getDamager()).getShooter() instanceof Player)
                damager = (Player) ((Snowball) event.getDamager()).getShooter();

            if (damager == null)
                return;

            IronGolem golem = (IronGolem) event.getEntity();
            List<MetadataValue> meta = golem.getMetadata("kitpvp_owner");
            if (!meta.isEmpty() && meta.get(0).asString().equals(damager.getUniqueId().toString()))
                event.setCancelled(true);
        }
    }
}
