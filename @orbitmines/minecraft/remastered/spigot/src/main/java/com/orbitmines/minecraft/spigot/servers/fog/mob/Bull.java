package com.orbitmines.minecraft.spigot.servers.fog.mob;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ravager;

/** Bull that charges at players and knocks them into the air on hit. */
public class Bull extends FoGMob {

    public Bull(Run run) {
        super(run);
    }

    @Override public double baseHealth()     { return 60.0; }
    @Override public String translationKey() { return "mob.bull.name"; }
    @Override public String colorPrefix()    { return "§6"; }

    @Override
    public void spawn(Location at) {
        Ravager r = (Ravager) at.getWorld().spawnEntity(at, EntityType.RAVAGER);
        r.setMaxHealth(baseHealth());
        r.setHealth(baseHealth());
        r.setCustomName(displayName());
        r.setCustomNameVisible(true);
        this.entity = r;
        this.entityUuid = r.getUniqueId();
    }
}
