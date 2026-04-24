package com.orbitmines.minecraft.spigot.servers.fog.mob;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.util.Fogi18n;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

/** Base class for all FoG mobs. Owns a spawn hook + a per-tick behavior hook. */
public abstract class FoGMob {

    @Getter protected final Run run;
    @Getter protected LivingEntity entity;
    @Getter protected UUID entityUuid;

    protected FoGMob(Run run) {
        this.run = run;
    }

    public abstract double baseHealth();

    /** Namespace key for this mob's display name (under `fog/mob.<name>.name`). */
    public abstract String translationKey();

    /** Chat-colour prefix applied to the default-locale display name. */
    public abstract String colorPrefix();

    public final String displayName() {
        return colorPrefix() + "§l" + Fogi18n.defaultText(translationKey());
    }

    public abstract void spawn(Location at);

    /** Invoked periodically while the mob is alive. Override to express AI. */
    public void tick() { /* default no-op */ }

    public boolean isAlive() {
        return entity != null && entity.isValid() && !entity.isDead();
    }

    public void remove() {
        if (entity != null && entity.isValid()) entity.remove();
    }
}
