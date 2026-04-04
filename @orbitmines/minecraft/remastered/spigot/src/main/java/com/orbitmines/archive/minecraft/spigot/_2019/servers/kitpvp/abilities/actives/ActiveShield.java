package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class ActiveShield implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(40 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        omp.addPotionEffect(new PotionBuilder(PotionEffectType.RESISTANCE, getDuration(level), getAmplifier(level)).build());
        omp.playSound(Sound.BLOCK_ANVIL_LAND);
    }

    private int getDuration(int level) {
        switch (level) {
            case 1: return 200;
            case 2: return 240;
            case 3: return 300;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int getAmplifier(int level) {
        return level >= 2 ? 1 : 0;
    }

    public PotionBuilder getBuilder(int level) {
        return new PotionBuilder(PotionEffectType.RESISTANCE, getDuration(level), getAmplifier(level));
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
