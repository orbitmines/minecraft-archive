package com.orbitmines.minecraft.spigot.servers.fog.crate;

import com.orbitmines.minecraft.spigot.servers.fog.choice.Rarity;

import java.util.Map;
import java.util.Random;

/** Opens a crate and produces a "Level-up trigger" of a particular rarity. */
public class Crate {

    private static final Random RANDOM = new Random();

    /** Returns a rarity to use when pushing tracked XP / level-up choice. */
    public static Rarity roll(CrateType crate) {
        int total = 0;
        for (int w : crate.getRarityWeights().values()) total += w;
        int r = RANDOM.nextInt(total);
        int cum = 0;
        for (Map.Entry<Rarity, Integer> e : crate.getRarityWeights().entrySet()) {
            cum += e.getValue();
            if (r < cum) return e.getKey();
        }
        return Rarity.COMMON;
    }

    /** Amount of tracked XP a rarity grants when converted from a crate. */
    public static int xpReward(Rarity rarity) {
        return switch (rarity) {
            case COMMON -> 50;
            case RARE -> 150;
            case EPIC -> 400;
            case LEGENDARY -> 1000;
        };
    }
}
