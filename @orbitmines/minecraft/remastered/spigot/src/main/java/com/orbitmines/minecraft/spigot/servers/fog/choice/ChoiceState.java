package com.orbitmines.minecraft.spigot.servers.fog.choice;

public enum ChoiceState {
    /** Choice is active — effects apply, items/enchants normal. */
    ACTIVE,
    /** Choice was tied to a level that was lost on death. Item/enchant/unlock
        is suppressed and visually marked (strikethrough + red "DAMAGED"). */
    DAMAGED;

    public static ChoiceState parse(String s) {
        if (s == null) return ACTIVE;
        try { return ChoiceState.valueOf(s.toUpperCase()); } catch (IllegalArgumentException e) { return ACTIVE; }
    }
}
