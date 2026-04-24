package com.orbitmines.minecraft.spigot.servers.fog.quest;

import lombok.Getter;
import lombok.Setter;

/** One active quest scoped to a (run, player) pair. */
public class Quest {

    @Getter private final String id;
    @Getter private final QuestType type;
    @Getter private final String target;    // Material name or EntityType name
    @Getter private final int requiredAmount;
    @Getter @Setter private int progress;
    @Getter private final int creditsReward;
    @Getter private final int xpReward;

    public Quest(String id, QuestType type, String target, int requiredAmount, int creditsReward, int xpReward) {
        this.id = id;
        this.type = type;
        this.target = target;
        this.requiredAmount = requiredAmount;
        this.creditsReward = creditsReward;
        this.xpReward = xpReward;
        this.progress = 0;
    }

    public boolean isDone() { return progress >= requiredAmount; }
}
