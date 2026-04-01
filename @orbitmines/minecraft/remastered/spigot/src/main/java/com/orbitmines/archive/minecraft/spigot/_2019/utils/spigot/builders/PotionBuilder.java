package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionBuilder {

    @Getter private PotionEffectType type;
    @Getter private int duration;
    @Getter private int amplifier;
    @Getter private boolean ambient;
    @Getter private boolean particles;
    @Getter private boolean icon;

    public PotionBuilder(PotionEffectType type, int amplifier) {
        this(type, 0, amplifier, true);
    }

    public PotionBuilder(PotionEffectType type, int duration, int amplifier) {
        this(type, duration, amplifier, true);
    }

    public PotionBuilder(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        this(type, duration, amplifier, ambient, true);
    }

    public PotionBuilder(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        this(type, duration, amplifier, ambient, particles, true);
    }

    public PotionBuilder(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.particles = particles;
        this.icon = icon;
    }

    public PotionBuilder setType(PotionEffectType type) { this.type = type; return this; }
    public PotionBuilder setDuration(int duration) { this.duration = duration; return this; }
    public PotionBuilder setAmplifier(int amplifier) { this.amplifier = amplifier; return this; }
    public PotionBuilder setAmbient(boolean ambient) { this.ambient = ambient; return this; }
    public PotionBuilder setParticles(boolean particles) { this.particles = particles; return this; }

    public PotionEffect build() {
        return new PotionEffect(type, duration, amplifier, ambient, particles, icon);
    }
}
