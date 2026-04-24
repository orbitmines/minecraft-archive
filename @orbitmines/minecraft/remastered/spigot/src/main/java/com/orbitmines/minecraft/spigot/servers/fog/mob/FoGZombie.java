package com.orbitmines.minecraft.spigot.servers.fog.mob;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

/**
 * Zombie variant whose melee attacks can apply the hunger debuff. Hunger application
 * logic lives in events/DamageListener so it can observe EntityDamageByEntityEvent cleanly.
 */
public class FoGZombie extends FoGMob {

    public FoGZombie(Run run) {
        super(run);
    }

    @Override public double baseHealth()     { return 24.0; }
    @Override public String translationKey() { return "mob.zombie.name"; }
    @Override public String colorPrefix()    { return "§7"; }

    @Override
    public void spawn(Location at) {
        Zombie z = (Zombie) at.getWorld().spawnEntity(at, EntityType.ZOMBIE);
        z.setMaxHealth(baseHealth());
        z.setHealth(baseHealth());
        z.setCustomName(displayName());
        z.setCustomNameVisible(true);
        this.entity = z;
        this.entityUuid = z.getUniqueId();
    }
}
