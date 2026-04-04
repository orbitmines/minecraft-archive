package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class ActiveTNTLauncher implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(7 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        Location loc = player.getLocation().add(0, 1, 0);
        Vector direction = player.getEyeLocation().getDirection().multiply(2);

        TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.TNT);
        tnt.setFuseTicks(getFuseTicks(level));
        tnt.setVelocity(direction);
        tnt.setSource(player);
        tnt.setIsIncendiary(false);

        omp.playSound(Sound.ENTITY_TNT_PRIMED);
    }

    private int getFuseTicks(int level) {
        switch (level) {
            case 1: return 15;
            case 2: return 12;
            case 3: return 10;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
