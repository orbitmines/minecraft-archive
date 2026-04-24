package com.orbitmines.minecraft.spigot.servers.fog.ability;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Per-player ability cooldown tracker. */
public class AbilityRegistry {

    private final Map<UUID, Map<Ability, Long>> cooldowns = new HashMap<>();

    public boolean tryTrigger(UUID uuid, Ability ability) {
        Map<Ability, Long> map = cooldowns.computeIfAbsent(uuid, k -> new HashMap<>());
        long now = System.currentTimeMillis();
        long ready = map.getOrDefault(ability, 0L);
        if (now < ready) return false;
        map.put(ability, now + ability.getCooldownMs());
        return true;
    }

    public long remaining(UUID uuid, Ability ability) {
        Map<Ability, Long> map = cooldowns.get(uuid);
        if (map == null) return 0;
        return Math.max(0, map.getOrDefault(ability, 0L) - System.currentTimeMillis());
    }
}
