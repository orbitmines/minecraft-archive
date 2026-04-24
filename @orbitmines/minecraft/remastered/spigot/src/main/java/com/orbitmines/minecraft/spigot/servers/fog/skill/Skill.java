package com.orbitmines.minecraft.spigot.servers.fog.skill;

import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import lombok.Getter;

public enum Skill {

    LUMBERJACK(Choice.SKILL_LUMBERJACK),
    FISHING(Choice.SKILL_FISHING),
    BOTANY(Choice.SKILL_BOTANY),
    BEEKEEPING(Choice.SKILL_BEEKEEPING);

    @Getter private final Choice unlockChoice;

    Skill(Choice unlockChoice) {
        this.unlockChoice = unlockChoice;
    }
}
