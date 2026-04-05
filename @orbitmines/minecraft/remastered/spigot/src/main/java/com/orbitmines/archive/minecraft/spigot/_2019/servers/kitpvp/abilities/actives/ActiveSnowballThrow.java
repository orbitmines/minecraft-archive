package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;

public class ActiveSnowballThrow implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();

        Snowball snowball = player.launchProjectile(Snowball.class);
        snowball.setVelocity(player.getEyeLocation().getDirection().multiply(getSpeed(level)));

        omp.playSound(Sound.ENTITY_SNOWBALL_THROW);
    }

    private double getSpeed(int level) {
        switch (level) {
            case 1: return 1.5;
            case 2: return 1.8;
            case 3: return 2.0;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
