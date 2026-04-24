package com.orbitmines.minecraft.spigot.servers.fog.choice;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import lombok.Getter;
import org.bukkit.Material;

/**
 * Master enum of all level-up choices. Display name + description come from
 * {@code fog/choice.<name>.{name,description}}.
 */
public enum Choice {

    /* === FACTIONS === (unique, one-per-player once-per-run) */
    FACTION_OMEGA     (Rarity.LEGENDARY, true, Category.FACTION,   Material.ENDER_EYE,           1, 1),
    FACTION_ALPHA     (Rarity.LEGENDARY, true, Category.FACTION,   Material.BLAZE_POWDER,        1, 1),
    FACTION_BETA      (Rarity.LEGENDARY, true, Category.FACTION,   Material.PRISMARINE_CRYSTALS, 1, 1),

    /* === STATS === */
    EXTRA_HP_4        (Rarity.COMMON,    false, Category.STAT,     Material.RED_DYE,             1, 10),
    DAMAGE_5          (Rarity.COMMON,    false, Category.STAT,     Material.IRON_SWORD,          1, 10),
    SPEED_5           (Rarity.COMMON,    false, Category.STAT,     Material.FEATHER,             1, 5),
    MINING_SPEED_5    (Rarity.COMMON,    false, Category.STAT,     Material.IRON_PICKAXE,        1, 10),

    /* === SKILLS === */
    SKILL_LUMBERJACK  (Rarity.RARE,      false, Category.SKILL,    Material.IRON_AXE,            1, 1),
    SKILL_FISHING     (Rarity.RARE,      false, Category.SKILL,    Material.FISHING_ROD,         1, 1),
    SKILL_BOTANY      (Rarity.RARE,      false, Category.SKILL,    Material.OAK_SAPLING,         1, 5),
    SKILL_BEEKEEPING  (Rarity.RARE,      false, Category.SKILL,    Material.HONEY_BOTTLE,        1, 1),

    /* === ORES === */
    ORE_COPPER        (Rarity.COMMON,    false, Category.ORE,      Material.COPPER_INGOT,        1, 1),
    ORE_COBALT        (Rarity.RARE,      false, Category.ORE,      Material.LIGHT_BLUE_DYE,      3, 1),
    ORE_STRONTIUM     (Rarity.RARE,      false, Category.ORE,      Material.GLOWSTONE_DUST,      5, 1),
    ORE_AMETHYST      (Rarity.RARE,      false, Category.ORE,      Material.AMETHYST_SHARD,      5, 1),
    ORE_URANIUM       (Rarity.EPIC,      false, Category.ORE,      Material.LIME_DYE,            8, 1),
    ORE_IRIDIUM       (Rarity.EPIC,      false, Category.ORE,      Material.LIGHT_GRAY_DYE,      10, 1),
    ORE_FRANCIUM      (Rarity.LEGENDARY, false, Category.ORE,      Material.MAGENTA_DYE,         15, 1),

    /* === TREES === */
    TREE_TAR          (Rarity.COMMON,    false, Category.TREE,     Material.COAL,                1, 1),
    TREE_GLOWWOOD     (Rarity.RARE,      false, Category.TREE,     Material.GLOW_INK_SAC,        3, 1),
    TREE_VOIDOAK      (Rarity.EPIC,      false, Category.TREE,     Material.SCULK,               8, 1),

    /* === DRONES === */
    DRONE_UNLOCK      (Rarity.EPIC,      false, Category.DRONE,    Material.ALLAY_SPAWN_EGG,     10, 9),
    DRONE_FACTORY     (Rarity.LEGENDARY, true,  Category.STRUCTURE,Material.SMITHING_TABLE,      10, 1),

    /* === STRUCTURES === */
    STRUCTURE_FARM    (Rarity.EPIC,      true,  Category.STRUCTURE,Material.WHEAT,               1, 1),
    STRUCTURE_ENCHANT (Rarity.EPIC,      true,  Category.STRUCTURE,Material.ENCHANTING_TABLE,    1, 1),
    STRUCTURE_CRATES  (Rarity.LEGENDARY, true,  Category.STRUCTURE,Material.CHEST,               1, 1),

    /* === MAP EXPANSION === */
    MAP_EXPANSION     (Rarity.LEGENDARY, false, Category.MAP,      Material.MAP,                 1, 20),

    /* === ENCHANTS === */
    ENCHANT_AUTO_REPLANT (Rarity.RARE,      false, Category.ENCHANT, Material.ENCHANTED_BOOK, 1, 1),
    ENCHANT_HOMING_ARROW (Rarity.EPIC,      false, Category.ENCHANT, Material.ENCHANTED_BOOK, 3, 1),
    ENCHANT_RAIN_ARROWS  (Rarity.LEGENDARY, false, Category.ENCHANT, Material.ENCHANTED_BOOK, 5, 1),
    ENCHANT_ABSORPTION   (Rarity.RARE,      false, Category.ENCHANT, Material.ENCHANTED_BOOK, 3, 1),
    ENCHANT_CHOP_TREE    (Rarity.EPIC,      false, Category.ENCHANT, Material.ENCHANTED_BOOK, 3, 1),

    /* === RECIPES === */
    RECIPE_ORE_SUIT   (Rarity.RARE,      false, Category.RECIPE,   Material.IRON_CHESTPLATE,   2, 1),
    RECIPE_PICKAXE_UP (Rarity.RARE,      false, Category.RECIPE,   Material.DIAMOND_PICKAXE,   2, 1),
    RECIPE_BACKPACK   (Rarity.RARE,      false, Category.RECIPE,   Material.BUNDLE,            2, 1),

    /* === ABILITIES === */
    ABILITY_SHIELD    (Rarity.EPIC,      false, Category.ABILITY,  Material.SHIELD,            5, 1),
    ABILITY_SWEEP     (Rarity.RARE,      false, Category.ABILITY,  Material.IRON_AXE,          3, 1),
    ABILITY_LEAP      (Rarity.RARE,      false, Category.ABILITY,  Material.RABBIT_FOOT,       3, 1),

    /* === QUESTS === */
    QUEST_COLLECT     (Rarity.COMMON,    false, Category.QUEST,    Material.WRITABLE_BOOK,     1, 1),
    QUEST_DEFEAT      (Rarity.COMMON,    false, Category.QUEST,    Material.WRITABLE_BOOK,     1, 1);

    public enum Category {
        FACTION, STAT, SKILL, ORE, TREE, DRONE, STRUCTURE, MAP, ENCHANT, RECIPE, ABILITY, QUEST
    }

    @Getter private final Rarity rarity;
    @Getter private final boolean unique;
    @Getter private final Category category;
    @Getter private final Material icon;
    @Getter private final int minLevel;
    @Getter private final int maxStacks;

    Choice(Rarity rarity, boolean unique, Category category, Material icon, int minLevel, int maxStacks) {
        this.rarity = rarity;
        this.unique = unique;
        this.category = category;
        this.icon = icon;
        this.minLevel = minLevel;
        this.maxStacks = maxStacks;
    }

    public String nameKey()        { return "choice." + name().toLowerCase() + ".name"; }
    public String descriptionKey() { return "choice." + name().toLowerCase() + ".description"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
    public String[] getDescriptionLines(Languageable viewer) {
        return viewer.getLanguage().getStringArray("fog", descriptionKey());
    }

    public String getStorageKey() {
        return name().toLowerCase();
    }

    public static Choice parse(String s) {
        if (s == null) return null;
        try { return Choice.valueOf(s.toUpperCase()); } catch (IllegalArgumentException e) { return null; }
    }
}
