package com.orbitmines.minecraft.spigot.servers.fog.ability;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import lombok.Getter;

public enum Ability {

    SHIELD_BASH(Choice.ABILITY_SHIELD, 8000, 3.0),
    SWEEP_ATTACK(Choice.ABILITY_SWEEP, 4000, 2.0),
    LEAP(Choice.ABILITY_LEAP, 6000, 0.0);

    @Getter private final Choice unlockChoice;
    @Getter private final long cooldownMs;
    @Getter private final double damage;

    Ability(Choice unlockChoice, long cooldownMs, double damage) {
        this.unlockChoice = unlockChoice;
        this.cooldownMs = cooldownMs;
        this.damage = damage;
    }

    public String nameKey() { return "ability." + name().toLowerCase() + ".name"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
}
