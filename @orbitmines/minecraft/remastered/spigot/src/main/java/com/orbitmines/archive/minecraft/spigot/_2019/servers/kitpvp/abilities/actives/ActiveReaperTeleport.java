package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class ActiveReaperTeleport implements Active.Handler {

    private final Cooldown[] cooldowns = new Cooldown[] {
            new Cooldown(15 * 1000),
            new Cooldown(13 * 1000),
            new Cooldown(11 * 1000)
    };

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        /* Raycast up to 15 blocks */
        RayTraceResult result = player.getWorld().rayTraceBlocks(
                player.getEyeLocation(), player.getEyeLocation().getDirection(), 15,
                FluidCollisionMode.NEVER, true
        );

        Location targetLoc;
        if (result != null && result.getHitBlock() != null) {
            targetLoc = result.getHitBlock().getLocation().add(0.5, 1, 0.5);
        } else {
            targetLoc = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(15));
        }

        targetLoc.setYaw(player.getLocation().getYaw());
        targetLoc.setPitch(player.getLocation().getPitch());

        /* Spawn armorstand preview with same armor */
        ArmorStand preview = player.getWorld().spawn(targetLoc, ArmorStand.class);
        preview.setGravity(false);
        preview.setVisible(true);
        preview.setInvulnerable(true);
        preview.setCustomName("§8§lGrim Reaper");
        preview.setCustomNameVisible(true);

        /* Copy armor from player */
        if (player.getInventory().getHelmet() != null)
            preview.getEquipment().setHelmet(player.getInventory().getHelmet().clone());
        if (player.getInventory().getChestplate() != null)
            preview.getEquipment().setChestplate(player.getInventory().getChestplate().clone());
        if (player.getInventory().getLeggings() != null)
            preview.getEquipment().setLeggings(player.getInventory().getLeggings().clone());
        if (player.getInventory().getBoots() != null)
            preview.getEquipment().setBoots(player.getInventory().getBoots().clone());

        /* Give it a stone hoe */
        preview.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_HOE));

        omp.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);

        /* Dual tilted particle orbits */
        final Location particleCenter = targetLoc.clone();

        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                if (tick >= 30) {
                    cancel();

                    /* Teleport player and remove preview */
                    player.teleport(targetLoc);
                    preview.remove();
                    omp.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);

                    /* Burst of particles at arrival */
                    particleCenter.getWorld().spawnParticle(Particle.PORTAL, particleCenter, 30, 0.5, 1.0, 0.5, 0.5);
                    return;
                }

                double angle = (tick * Math.PI * 2) / 15;
                double radius = 1.2;

                /* Circle 1: tilted 30 degrees on X axis */
                double tilt1 = Math.toRadians(30);
                double x1 = Math.cos(angle) * radius;
                double y1 = Math.sin(angle) * Math.cos(tilt1) * radius;
                double z1 = Math.sin(angle) * Math.sin(tilt1) * radius;

                /* Circle 2: tilted -30 degrees on Z axis (crossing the first) */
                double tilt2 = Math.toRadians(-30);
                double x2 = Math.cos(angle + Math.PI) * Math.cos(tilt2) * radius;
                double y2 = Math.sin(angle + Math.PI) * radius;
                double z2 = Math.cos(angle + Math.PI) * Math.sin(tilt2) * radius;

                particleCenter.getWorld().spawnParticle(Particle.WITCH,
                        particleCenter.getX() + x1, particleCenter.getY() + 1.0 + y1, particleCenter.getZ() + z1,
                        1, 0, 0, 0, 0);
                particleCenter.getWorld().spawnParticle(Particle.WITCH,
                        particleCenter.getX() + x2, particleCenter.getY() + 1.0 + y2, particleCenter.getZ() + z2,
                        1, 0, 0, 0, 0);

                tick++;
            }
        }.runTaskTimer(kitPvP.getPlugin(), 0, 1);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldowns[level - 1];
    }
}
