package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class ActiveHealing implements Active.Handler {

    private Cooldown cooldown = new Cooldown(10 * 1000);

    private PotionBuilder builder = new PotionBuilder(PotionEffectType.REGENERATION, 2);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        omp.playSound(Sound.ENTITY_GENERIC_DRINK);

        builder.setDuration((level + 1) * 20);
        omp.addPotionEffect(builder.build());
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }

    public PotionBuilder getBuilder(int level){
        builder.setDuration((level + 1) * 20);
        return builder;
    }
}
