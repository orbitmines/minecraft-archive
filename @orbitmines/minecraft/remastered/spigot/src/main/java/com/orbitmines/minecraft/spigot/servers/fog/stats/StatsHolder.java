package com.orbitmines.minecraft.spigot.servers.fog.stats;

import java.util.UUID;

/**
 * Anything in FoG that can accrue stats (a player, a drone, a party slot, …).
 * Provides the key prefix used inside {@link com.orbitmines.minecraft.spigot.servers.fog.run.RunStore}.
 */
public interface StatsHolder {

    UUID getOwnerUuid();

    /** Key prefix inside the RunStore. E.g. `member:&lt;uuid&gt;` or `member:&lt;uuid&gt;:drone:&lt;id&gt;`. */
    String getStatsPrefix();

    Stats getStats();
}
