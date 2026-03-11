package fadidev.orbitmines.prison.handlers;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.prison.handlers.cooldown.CooldownResetMine;
import fadidev.orbitmines.prison.handlers.cooldown.CooldownStarterKit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Fadi on 19-9-2016.
 */
public class PrisonCooldowns {

    public static HashMap<UUID, Map<Cooldown, Long>> PREV_COOLDOWNS = new HashMap<>();

    public static Cooldown RESET_MINE = new CooldownResetMine();
    public static Cooldown STARTER_KIT = new CooldownStarterKit();

}
