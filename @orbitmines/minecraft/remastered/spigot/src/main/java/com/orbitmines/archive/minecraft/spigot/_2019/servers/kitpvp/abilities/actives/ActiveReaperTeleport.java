package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActiveReaperTeleport implements Active.Handler {

    private final Cooldown[] cooldowns = new Cooldown[] {
            new Cooldown(15 * 1000),
            new Cooldown(13 * 1000),
            new Cooldown(11 * 1000)
    };

    private static final Map<UUID, PreviewState> previews = new HashMap<>();

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        PreviewState state = previews.get(player.getUniqueId());
        if (state == null || state.preview == null || state.preview.isDead())
            return;

        /* Confirm teleport */
        Location targetLoc = state.preview.getLocation().clone();
        targetLoc.setYaw(player.getLocation().getYaw());
        targetLoc.setPitch(player.getLocation().getPitch());

        /* Particle burst at arrival */
        targetLoc.getWorld().spawnParticle(Particle.PORTAL, targetLoc.clone().add(0, 1, 0), 30, 0.5, 1.0, 0.5, 0.5);

        player.teleport(targetLoc);
        omp.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);

        /* Clean up preview */
        removePreview(player.getUniqueId());
    }

    public static void startPreview(Player player, KitPvP kitPvP) {
        removePreview(player.getUniqueId());

        PreviewState state = new PreviewState();
        previews.put(player.getUniqueId(), state);

        state.task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || player.isDead()) {
                    removePreview(player.getUniqueId());
                    cancel();
                    return;
                }

                /* Raycast from eye */
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

                if (state.preview == null || state.preview.isDead()) {
                    /* Spawn armorstand preview */
                    state.preview = player.getWorld().spawn(targetLoc, ArmorStand.class);
                    state.preview.setGravity(false);
                    state.preview.setVisible(true);
                    state.preview.setInvulnerable(true);
                    state.preview.setCustomName("§8§lGrim Reaper");
                    state.preview.setCustomNameVisible(true);
                    state.preview.setMarker(true);

                    /* Copy armor from player */
                    if (player.getInventory().getHelmet() != null)
                        state.preview.getEquipment().setHelmet(player.getInventory().getHelmet().clone());
                    if (player.getInventory().getChestplate() != null)
                        state.preview.getEquipment().setChestplate(player.getInventory().getChestplate().clone());
                    if (player.getInventory().getLeggings() != null)
                        state.preview.getEquipment().setLeggings(player.getInventory().getLeggings().clone());
                    if (player.getInventory().getBoots() != null)
                        state.preview.getEquipment().setBoots(player.getInventory().getBoots().clone());

                    state.preview.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_HOE));
                } else {
                    /* Update position */
                    state.preview.teleport(targetLoc);
                }

                /* Particle orbits around preview */
                double angle = (System.currentTimeMillis() / 50.0) * 0.3;
                double radius = 1.2;
                double tilt = Math.toRadians(30);
                double x1 = Math.cos(angle) * radius;
                double y1 = Math.sin(angle) * Math.cos(tilt) * radius;
                double z1 = Math.sin(angle) * Math.sin(tilt) * radius;
                targetLoc.getWorld().spawnParticle(Particle.WITCH,
                        targetLoc.getX() + x1, targetLoc.getY() + 1.0 + y1, targetLoc.getZ() + z1,
                        1, 0, 0, 0, 0);
                double x2 = Math.cos(angle + Math.PI) * Math.cos(-tilt) * radius;
                double y2 = Math.sin(angle + Math.PI) * radius;
                double z2 = Math.cos(angle + Math.PI) * Math.sin(-tilt) * radius;
                targetLoc.getWorld().spawnParticle(Particle.WITCH,
                        targetLoc.getX() + x2, targetLoc.getY() + 1.0 + y2, targetLoc.getZ() + z2,
                        1, 0, 0, 0, 0);
            }
        };
        state.task.runTaskTimer(kitPvP.getPlugin(), 0, 1);
    }

    public static void removePreview(UUID uuid) {
        PreviewState state = previews.remove(uuid);
        if (state != null) {
            if (state.preview != null && !state.preview.isDead()) {
                state.preview.remove();
            }
            if (state.task != null) {
                state.task.cancel();
            }
        }
    }

    public static boolean hasPreview(UUID uuid) {
        return previews.containsKey(uuid);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldowns[level - 1];
    }

    private static class PreviewState {
        ArmorStand preview;
        BukkitRunnable task;
    }

    public static class ShadowStepListener implements Listener {

        @EventHandler
        public void onItemSwitch(PlayerItemHeldEvent event) {
            Player player = event.getPlayer();
            KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

            /* Check if the new slot has the shadow step item */
            ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
            boolean newSlotIsShadowStep = newItem != null && isShadowStepItem(kitPvP, newItem);

            if (newSlotIsShadowStep) {
                if (!hasPreview(player.getUniqueId())) {
                    startPreview(player, kitPvP);
                }
            } else {
                if (hasPreview(player.getUniqueId())) {
                    removePreview(player.getUniqueId());
                }
            }
        }

        private boolean isShadowStepItem(KitPvP kitPvP, ItemStack item) {
            java.util.Map<String, String> keys = kitPvP.getNms().customItem().getMetaData(item).getKeys("active");
            return keys != null && keys.containsKey(Active.REAPER_TELEPORT.toString());
        }
    }
}
