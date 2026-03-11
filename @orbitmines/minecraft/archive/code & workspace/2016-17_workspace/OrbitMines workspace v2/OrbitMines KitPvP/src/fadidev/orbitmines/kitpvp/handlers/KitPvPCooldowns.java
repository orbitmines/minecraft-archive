package fadidev.orbitmines.kitpvp.handlers;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.kitpvp.handlers.cooldowns.*;

/**
 * Created by Fadi on 13-9-2016.
 */
public class KitPvPCooldowns {

    public static Cooldown BARRIER_I = new CooldownBarrier1();
    public static Cooldown BARRIER_II = new CooldownBarrier2();
    public static Cooldown BLOCK_EXPLOSION_I = new CooldownBlockExplosion1();
    public static Cooldown FIRE_SPELL_I = new CooldownFireSpell1();
    public static Cooldown FIRE_SPELL_II = new CooldownFireSpell2();
    public static Cooldown FISH_ATTACK_I = new CooldownFishAttack1();
    public static Cooldown HEALING_I = new CooldownHealing1();
    public static Cooldown HEALING_II = new CooldownHealing2();
    public static Cooldown MAGIC_I = new CooldownMagic1();
    public static Cooldown PAINTBALLS_I = new CooldownPaintballs1();
    public static Cooldown PAINTBALLS_I_USAGE = new CooldownPaintballs1Usage();
    public static Cooldown POTION_LAUNCHER_I = new CooldownPotionLauncher1();
    public static Cooldown SHIELD_I = new CooldownShield1();
    public static Cooldown SHIELD_II = new CooldownShield2();
    public static Cooldown TNT_I = new CooldownTNT1();
    public static Cooldown UNDEATH_SUMMON_I = new CooldownUndeathSummon1();
    public static Cooldown WITHER_I = new CooldownWither1();
}

