package fadidev.orbitmines.minigames.handlers;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.minigames.handlers.cooldowns.*;

/**
 * Created by Fadi on 30-9-2016.
 */
public class MiniGamesCooldowns {

    public static Cooldown FEATHER_ATTACK = new CooldownFeatherAttack();
    public static Cooldown EGG_BOMB = new CooldownEggBomb();
    public static Cooldown FIRE_SHIELD = new CooldownFireShield();
    public static Cooldown IRON_FIST = new CooldownIronFist();

    public static Cooldown WOOL_TRAIL = new CooldownWoolTrail();
}
