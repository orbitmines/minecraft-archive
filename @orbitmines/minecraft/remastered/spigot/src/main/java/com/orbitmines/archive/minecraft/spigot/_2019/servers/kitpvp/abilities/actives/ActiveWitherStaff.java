package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class ActiveWitherStaff implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(5 * 1000);

    /* Separate "no soul" cooldown so it doesn't consume the real cooldown */
    private final Cooldown noSoulCooldown = new Cooldown(0);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();

        /* Check for soul */
        int soulSlot = findSoulSlot(player);
        if (soulSlot == -1) {
            new ActionBar(player, () -> "§c§lNo Soul available!", 40).send();
            return;
        }

        /* Consume soul */
        ItemStack soul = player.getInventory().getItem(soulSlot);
        if (soul.getAmount() > 1)
            soul.setAmount(soul.getAmount() - 1);
        else
            player.getInventory().setItem(soulSlot, null);

        /* Shoot wither skulls */
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection().multiply(1.5);

        launchSkull(player, eyeLoc, direction);

        /* Spray skulls */
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 2; i++) {
            Vector spray = direction.clone().add(new Vector(
                r.nextDouble(-0.5, 0.5),
                r.nextDouble(-0.3, 0.3),
                r.nextDouble(-0.5, 0.5)
            ));
            launchSkull(player, eyeLoc, spray);
        }

        /* Apply wither effect to nearby enemies hit by skulls */
        for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
            if (entity instanceof LivingEntity && entity != player) {
                LivingEntity target = (LivingEntity) entity;
                Location targetLoc = target.getLocation();
                Vector toTarget = targetLoc.toVector().subtract(eyeLoc.toVector()).normalize();
                double dot = direction.normalize().dot(toTarget);

                /* Only affect entities roughly in the direction the skulls are going */
                if (dot > 0.8 && targetLoc.distance(eyeLoc) <= 8) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, getWitherDuration(level), getWitherAmplifier(level)));
                }
            }
        }

        omp.playSound(Sound.ENTITY_WITHER_SHOOT);
    }

    private void launchSkull(Player shooter, Location loc, Vector velocity) {
        WitherSkull skull = shooter.launchProjectile(WitherSkull.class);
        skull.setVelocity(velocity);
        skull.setCharged(false);
    }

    private int findSoulSlot(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null || item.getType() != Material.REDSTONE)
                continue;

            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                if (meta.getDisplayName().contains("Soul"))
                    return i;
            }
        }
        return -1;
    }

    private int getWitherDuration(int level) {
        switch (level) {
            case 1: return 60;
            case 2: return 80;
            case 3: return 100;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int getWitherAmplifier(int level) {
        return level >= 2 ? 1 : 0;
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
