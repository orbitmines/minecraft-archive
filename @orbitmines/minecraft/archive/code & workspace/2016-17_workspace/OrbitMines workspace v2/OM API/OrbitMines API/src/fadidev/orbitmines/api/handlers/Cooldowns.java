package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.handlers.cooldowns.CooldownMessage;
import fadidev.orbitmines.api.handlers.cooldowns.CooldownNpcInteract;
import fadidev.orbitmines.api.handlers.cooldowns.CooldownPortalUsage;
import fadidev.orbitmines.api.handlers.cooldowns.CooldownTeleporting;
import fadidev.orbitmines.api.handlers.cooldowns.gadget.CooldownBookExplosion;
import fadidev.orbitmines.api.handlers.cooldowns.gadget.CooldownCreeperLauncher;
import fadidev.orbitmines.api.handlers.cooldowns.gadget.CooldownSgaUsage;
import fadidev.orbitmines.api.handlers.cooldowns.gadget.CooldownSwapTeleporter;
import fadidev.orbitmines.api.handlers.cooldowns.pet.*;

/**
 * Created by Fadi on 5-9-2016.
 */
public class Cooldowns {

    public static Cooldown BOOK_EXPLOSION = new CooldownBookExplosion();
    public static Cooldown CREEPER_LAUNCHER = new CooldownCreeperLauncher();
    public static Cooldown SGA_USAGE = new CooldownSgaUsage();
    public static Cooldown SWAP_TELEPORTER = new CooldownSwapTeleporter();

    public static Cooldown PET_BABY_FIREWORK = new CooldownPetBabyFirework();
    public static Cooldown PET_BARK = new CooldownPetBark();
    public static Cooldown PET_INK_BOMB = new CooldownPetInkBomb();
    public static Cooldown PET_JUMP = new CooldownPetJump();
    public static Cooldown PET_KITTY_CANNON_USAGE = new CooldownPetKittyCannonUsage();
    public static Cooldown PET_LEAP_USAGE = new CooldownPetLeapUsage();
    public static Cooldown PET_MILK_EXPLOSION = new CooldownPetMilkExplosion();
    public static Cooldown PET_SILVERFISH_BOMB_USAGE = new CooldownPetSilverfishBombUsage();
    public static Cooldown PET_SPIDER_LAUNCHER = new CooldownPetSpiderLauncher();
    public static Cooldown PET_WEBS = new CooldownPetWebs();

    public static Cooldown MESSAGE = new CooldownMessage();
    public static Cooldown NPC_INTERACT = new CooldownNpcInteract();
    public static Cooldown PORTAL_USAGE = new CooldownPortalUsage();
    public static Cooldown TELEPORTING = new CooldownTeleporting();
}
