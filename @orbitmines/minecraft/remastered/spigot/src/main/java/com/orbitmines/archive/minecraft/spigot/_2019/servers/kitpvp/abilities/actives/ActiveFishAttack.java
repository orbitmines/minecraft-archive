package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ActiveFishAttack implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(5 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        double radius = getRadius(level);

        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player) || entity == player)
                continue;

            KitPvPPlayer target = kitPvP.getPlayer((Player) entity);
            if (target == null || target.getSelectedKit() == null || target.isSpectator())
                continue;

            ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.POISON, getDuration(level), getAmplifier(level)));
        }

        player.getWorld().spawnParticle(Particle.SPLASH, player.getLocation().add(0, 1, 0), 50, 1, 1, 1, 0.1);
        omp.playSound(Sound.WEATHER_RAIN);
    }

    private double getRadius(int level) {
        switch (level) {
            case 1: return 4;
            case 2: return 5;
            case 3: return 6;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int getDuration(int level) {
        return level >= 2 ? 100 : 80;
    }

    private int getAmplifier(int level) {
        return level >= 3 ? 2 : 1;
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
