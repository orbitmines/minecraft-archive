package com.orbitmines.minecraft.spigot.servers.fog.level;

/** Level/XP curves for FoG. Mirrors KitPvP's feel. */
public class LevelFormulas {

    public static final int MAX_LEVEL = 100;

    /** XP required *to reach* level (level+1) from the start of level. */
    public static long required(int level) {
        return (level + 1L) * (300 + 8L * level);
    }

    /** Given total XP earned in the run, compute current level and leftover XP. */
    public static int computeLevel(long totalXp) {
        long remaining = totalXp;
        for (int i = 0; i <= MAX_LEVEL; i++) {
            long req = required(i);
            if (remaining >= req) {
                remaining -= req;
            } else {
                return i;
            }
        }
        return MAX_LEVEL;
    }

    public static long xpIntoLevel(long totalXp, int level) {
        long remaining = totalXp;
        for (int i = 0; i < level; i++) {
            remaining -= required(i);
        }
        return Math.max(0, remaining);
    }
}
