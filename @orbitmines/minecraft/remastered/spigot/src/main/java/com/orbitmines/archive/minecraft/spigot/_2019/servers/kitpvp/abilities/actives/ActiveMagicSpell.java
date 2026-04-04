package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ActiveMagicSpell implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(30 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (!(entity instanceof Player) || entity == player)
                continue;

            KitPvPPlayer target = kitPvP.getPlayer((Player) entity);
            if (target == null || target.getSelectedKit() == null || target.isSpectator())
                continue;

            Player targetBukkit = (Player) entity;
            targetBukkit.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, getDuration(level), getAmplifier(level)));
            targetBukkit.getWorld().playSound(targetBukkit.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0f, 2.0f);
        }

        omp.playSound(Sound.ENTITY_WITHER_AMBIENT);
    }

    private int getAmplifier(int level) {
        return level >= 2 ? 1 : 0;
    }

    private int getDuration(int level) {
        return level >= 3 ? 200 : 150;
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
