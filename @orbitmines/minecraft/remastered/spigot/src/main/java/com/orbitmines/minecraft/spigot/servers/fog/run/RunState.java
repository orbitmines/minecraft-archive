package com.orbitmines.minecraft.spigot.servers.fog.run;

public enum RunState {
    /** Cave scoring / world generation in progress. */
    PREPARING,
    /** Run is live and playable. */
    ACTIVE,
    /** Run is over — everyone in permadeath, or owner ended it. Spectators still allowed. */
    ENDED;

    public static RunState parse(String s) {
        if (s == null) return PREPARING;
        try {
            return RunState.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PREPARING;
        }
    }
}
