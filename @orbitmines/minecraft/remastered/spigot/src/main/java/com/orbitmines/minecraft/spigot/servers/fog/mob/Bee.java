package com.orbitmines.minecraft.spigot.servers.fog.mob;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/** Aggressive bee-like mob. Poison chance on sting handled in DamageListener. */
public class Bee extends FoGMob {

    public Bee(Run run) {
        super(run);
    }

    @Override public double baseHealth()     { return 10.0; }
    @Override public String translationKey() { return "mob.bee.name"; }
    @Override public String colorPrefix()    { return "§e"; }

    @Override
    public void spawn(Location at) {
        org.bukkit.entity.Bee b = (org.bukkit.entity.Bee) at.getWorld().spawnEntity(at, EntityType.BEE);
        b.setMaxHealth(baseHealth());
        b.setHealth(baseHealth());
        b.setAnger(Integer.MAX_VALUE);
        b.setCustomName(displayName());
        b.setCustomNameVisible(true);
        this.entity = b;
        this.entityUuid = b.getUniqueId();
    }
}
