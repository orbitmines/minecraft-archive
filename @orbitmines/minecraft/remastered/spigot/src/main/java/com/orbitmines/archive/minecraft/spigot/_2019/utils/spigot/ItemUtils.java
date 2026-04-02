package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemUtils {

    public static Set<Material> toMaterials(ItemStack... itemStacks) {
        return Arrays.stream(itemStacks).
                filter(Objects::nonNull).
                map(ItemStack::getType).
                collect(Collectors.toSet());
    }

    /*

        ItemStack Selectors

     */

    public static List<ItemStack> selectItemStacks(ItemStack[] itemStacks, Material material) {
        return itemStacksWith(itemStacks, Collections.singletonList(material));
    }

    public static List<ItemStack> selectItemStacks(ItemStack[] itemStacks, Material... materials) {
        return itemStacksWith(itemStacks, Arrays.asList(materials));
    }

    public static List<ItemStack> itemStacksWith(ItemStack[] itemStacks, Collection<Material> materials) {
        return Arrays.stream(itemStacks).
                filter(Objects::nonNull).
                filter(itemStack -> materials.contains(itemStack.getType())).
                collect(Collectors.toList());
    }

    public static List<ItemStack> selectItemStacks(ItemStack[] itemStacks, Predicate<? super ItemStack> filter, Material material) {
        return selectItemStacks(itemStacks, filter, Collections.singletonList(material));
    }

    public static List<ItemStack> selectItemStacks(ItemStack[] itemStacks, Predicate<? super ItemStack> filter, Material... materials) {
        return selectItemStacks(itemStacks, filter, Arrays.asList(materials));
    }

    public static List<ItemStack> selectItemStacks(ItemStack[] itemStacks, Predicate<? super ItemStack> filter, Collection<Material> materials) {
        return Arrays.stream(itemStacks).
                filter(Objects::nonNull).
                filter(itemStack -> materials.contains(itemStack.getType())).
                filter(filter).
                collect(Collectors.toList());
    }

    public static List<ItemStack> selectItemStacksEmpty(ItemStack[] itemStacks) {
        return Arrays.stream(itemStacks).
                filter(itemStack -> itemStack == null || itemStack.getType() == Material.AIR).
                collect(Collectors.toList());
    }

    /*

        Item Meta checks

     */

    public static boolean hasItemMeta(ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null;
    }

    public static boolean hasDisplayName(ItemStack itemStack) {
        return hasItemMeta(itemStack) && itemStack.getItemMeta().getDisplayName() != null;
    }

    public static boolean hasDisplayName(ItemStack itemStack, String displayname) {
        return hasItemMeta(itemStack) && hasDisplayName(itemStack) && itemStack.getItemMeta().getDisplayName().equals(displayname);
    }

    public static boolean hasLocalizedName(ItemStack itemStack) {
        return hasItemMeta(itemStack) && itemStack.getItemMeta().getLocalizedName() != null;
    }

    public static boolean hasLocalizedName(ItemStack itemStack, String displayname) {
        return hasItemMeta(itemStack) && hasLocalizedName(itemStack) && itemStack.getItemMeta().getLocalizedName().equals(displayname);
    }

    public static boolean hasLore(ItemStack itemStack) {
        return hasItemMeta(itemStack) && itemStack.getItemMeta().getLore() != null;
    }

    public static boolean hasDisplayNameAndLore(ItemStack itemStack) {
        return hasItemMeta(itemStack) && itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getLore() != null;
    }

    public static boolean hasLocalizedNameAndLore(ItemStack itemStack) {
        return hasItemMeta(itemStack) && itemStack.getItemMeta().getLocalizedName() != null && itemStack.getItemMeta().getLore() != null;
    }

    /*

        Item Naming

     */

    public static String getName(Material material) {
        StringBuilder stringBuilder = new StringBuilder();

        String[] parts = material.toString().split("_");
        for (int i = 0; i < parts.length; i++) {
            if (i != 0)
                stringBuilder.append(" ");

            stringBuilder.append(parts[i].substring(0, 1).toUpperCase());
            stringBuilder.append(parts[i].substring(1).toLowerCase());
        }

        return stringBuilder.toString();
    }

    // TODO, removed color code &7
    public static String getName(Enchantment enchantment, int level) {
        StringBuilder stringBuilder = new StringBuilder();

        String[] parts = enchantment.getKey().getKey().split("_");
        for (int i = 0; i < parts.length; i++) {
            if (i != 0)
                stringBuilder.append(" ");

            stringBuilder.append(parts[i].substring(0, 1).toUpperCase());
            stringBuilder.append(parts[i].substring(1).toLowerCase());
        }

        return stringBuilder.toString() + " " + NumberUtils.toRoman(level);
    }

    public static String getName(PotionEffectType effectType) {
        if (effectType == PotionEffectType.SPEED) {
            return "Speed";
        } else if (effectType == PotionEffectType.HASTE) {
            return "Haste";
        } else if (effectType == PotionEffectType.STRENGTH) {
            return "Strength";
        } else if (effectType == PotionEffectType.INSTANT_HEALTH) {
            return "Instant Health";
        } else if (effectType == PotionEffectType.JUMP_BOOST) {
            return "Jump Boost";
        } else if (effectType == PotionEffectType.REGENERATION) {
            return "Regeneration";
        } else if (effectType == PotionEffectType.RESISTANCE) {
            return "Resistance";
        } else if (effectType == PotionEffectType.FIRE_RESISTANCE) {
            return "Fire Resistance";
        } else if (effectType == PotionEffectType.WATER_BREATHING) {
            return "Water Breathing";
        } else if (effectType == PotionEffectType.INVISIBILITY) {
            return "Invisibility";
        } else if (effectType == PotionEffectType.NIGHT_VISION) {
            return "Night Vision";
        } else if (effectType == PotionEffectType.HEALTH_BOOST) {
            return "Health Boost";
        } else if (effectType == PotionEffectType.ABSORPTION) {
            return "Absorption";
        } else if (effectType == PotionEffectType.SATURATION) {
            return "Saturation";
        } else if (effectType == PotionEffectType.GLOWING) {
            return "Glowing";
        } else if (effectType == PotionEffectType.LUCK) {
            return "Luck";
        } else if (effectType == PotionEffectType.CONDUIT_POWER) {
            return "Conduit Power";
        } else if (effectType == PotionEffectType.DOLPHINS_GRACE) {
            return "Dolphins Grace";
        } else if (effectType == PotionEffectType.SLOWNESS) {
            return "Slowness";
        } else if (effectType == PotionEffectType.MINING_FATIGUE) {
            return "Mining Fatigue";
        } else if (effectType == PotionEffectType.INSTANT_DAMAGE) {
            return "Harming";
        } else if (effectType == PotionEffectType.NAUSEA) {
            return "Nausea";
        } else if (effectType == PotionEffectType.BLINDNESS) {
            return "Blindness";
        } else if (effectType == PotionEffectType.HUNGER) {
            return "Hunger";
        } else if (effectType == PotionEffectType.WEAKNESS) {
            return "Weakness";
        } else if (effectType == PotionEffectType.POISON) {
            return "Poison";
        } else if (effectType == PotionEffectType.WITHER) {
            return "Wither";
        } else if (effectType == PotionEffectType.LEVITATION) {
            return "Levitation";
        } else if (effectType == PotionEffectType.UNLUCK) {
            return "Bad Luck";
        } else if (effectType == PotionEffectType.SLOW_FALLING) {
            return "Slow Falling";
        } else {
            return null;
        }
    }

    /*

        Item Groups

     */

    public static final Set<Material> FARM_MATERIALS = new HashSet<>(Arrays.asList(
            Material.BEETROOTS,
//            Material.CACTUS,
            Material.CARROTS,
            Material.CHORUS_FLOWER,
//            Material.FROSTED_ICE,
//            Material.KELP,
            Material.MELON_STEM,
            Material.NETHER_WART,
            Material.POTATOES,
            Material.PUMPKIN_STEM,
            Material.SUGAR_CANE,
            Material.WHEAT,
            Material.BAMBOO,
            Material.BAMBOO_SAPLING,
            Material.SWEET_BERRIES,
            Material.SWEET_BERRY_BUSH
    ));

    public static boolean isFarmMaterial(Material material) {
        return FARM_MATERIALS.contains(material);
    }

    public static boolean hasFarmMaterial(Collection<Material> materials) {
        return !Collections.disjoint(FARM_MATERIALS, materials);
    }

    public static boolean isFarmMaterial(ItemStack itemStack) {
        return itemStack != null && isFarmMaterial(itemStack.getType());
    }

    public static boolean hasFarmMaterial(ItemStack... itemStacks) {
        return hasFarmMaterial(toMaterials(itemStacks));
    }

    public static final Set<Material> INTERACTABLE = new HashSet<>(Arrays.asList(
            Material.CHEST,
            Material.ENDER_CHEST,
            Material.TRAPPED_CHEST,
            Material.FURNACE,
            Material.BLAST_FURNACE,
            Material.CRAFTING_TABLE,
            Material.ANVIL,
            Material.ENCHANTING_TABLE,
            Material.DISPENSER,
            Material.HOPPER,
            Material.DROPPER,
            Material.BEACON,
            Material.BARREL,
            Material.BELL,
            Material.CAMPFIRE,
            Material.COMPOSTER,
            Material.FLETCHING_TABLE,
            Material.GRINDSTONE,
            Material.LECTERN,
            Material.LOOM,
            Material.SMITHING_TABLE,
            Material.SMOKER,
            Material.STONECUTTER,

            Material.FIRE,

            Material.ACACIA_TRAPDOOR,
            Material.BIRCH_TRAPDOOR,
            Material.DARK_OAK_TRAPDOOR,
            Material.JUNGLE_TRAPDOOR,
            Material.OAK_TRAPDOOR,
            Material.SPRUCE_TRAPDOOR,

            Material.LEVER,
            Material.STONE_BUTTON,

            Material.ACACIA_BUTTON,
            Material.BIRCH_BUTTON,
            Material.DARK_OAK_BUTTON,
            Material.JUNGLE_BUTTON,
            Material.OAK_BUTTON,
            Material.SPRUCE_BUTTON,

            Material.ACACIA_DOOR,
            Material.BIRCH_DOOR,
            Material.DARK_OAK_DOOR,
            Material.JUNGLE_DOOR,
            Material.OAK_DOOR,
            Material.SPRUCE_DOOR,

            Material.ACACIA_FENCE_GATE,
            Material.BIRCH_FENCE_GATE,
            Material.DARK_OAK_FENCE_GATE,
            Material.JUNGLE_FENCE_GATE,
            Material.OAK_FENCE_GATE,
            Material.SPRUCE_FENCE_GATE,

            Material.BLACK_BED,
            Material.BLUE_BED,
            Material.BROWN_BED,
            Material.CYAN_BED,
            Material.GRAY_BED,
            Material.GREEN_BED,
            Material.LIGHT_BLUE_BED,
            Material.LIGHT_GRAY_BED,
            Material.LIME_BED,
            Material.MAGENTA_BED,
            Material.ORANGE_BED,
            Material.PINK_BED,
            Material.PURPLE_BED,
            Material.RED_BED,
            Material.WHITE_BED,
            Material.YELLOW_BED,

            Material.BREWING_STAND,
            Material.CAKE,
            Material.CAULDRON,

            Material.COMMAND_BLOCK,
            Material.CHAIN_COMMAND_BLOCK,
            Material.REPEATING_COMMAND_BLOCK,

            Material.END_PORTAL_FRAME,
            Material.FIRE,
            Material.FLOWER_POT,
            Material.JUKEBOX,
            Material.OBSERVER,
            Material.COMPARATOR,

            Material.RAIL,
            Material.ACTIVATOR_RAIL,
            Material.DETECTOR_RAIL,
            Material.POWERED_RAIL,

            Material.SHULKER_BOX,
            Material.BLACK_SHULKER_BOX,
            Material.BLUE_SHULKER_BOX,
            Material.BROWN_SHULKER_BOX,
            Material.CYAN_SHULKER_BOX,
            Material.GRAY_SHULKER_BOX,
            Material.GREEN_SHULKER_BOX,
            Material.LIGHT_BLUE_SHULKER_BOX,
            Material.LIGHT_GRAY_SHULKER_BOX,
            Material.LIME_SHULKER_BOX,
            Material.MAGENTA_SHULKER_BOX,
            Material.ORANGE_SHULKER_BOX,
            Material.PINK_SHULKER_BOX,
            Material.PURPLE_SHULKER_BOX,
            Material.RED_SHULKER_BOX,
            Material.WHITE_SHULKER_BOX,
            Material.YELLOW_SHULKER_BOX,

            Material.ACACIA_SIGN,
            Material.BIRCH_SIGN,
            Material.DARK_OAK_SIGN,
            Material.JUNGLE_SIGN,
            Material.OAK_SIGN,
            Material.SPRUCE_SIGN,

            Material.ACACIA_WALL_SIGN,
            Material.BIRCH_WALL_SIGN,
            Material.DARK_OAK_WALL_SIGN,
            Material.JUNGLE_WALL_SIGN,
            Material.OAK_WALL_SIGN,
            Material.SPRUCE_WALL_SIGN
    ));

    public static boolean isInteractable(Material material) {
        return INTERACTABLE.contains(material);
    }

    public static boolean hasInteractable(Collection<Material> materials) {
        return !Collections.disjoint(INTERACTABLE, materials);
    }

    public static boolean isInteractable(ItemStack itemStack) {
        return itemStack != null && isInteractable(itemStack.getType());
    }

    public static boolean hasInteractable(ItemStack... itemStacks) {
        return hasInteractable(toMaterials(itemStacks));
    }

    public static final Set<Material> RAILS = new HashSet<>(Arrays.asList(
            Material.RAIL,
            Material.ACTIVATOR_RAIL,
            Material.DETECTOR_RAIL,
            Material.POWERED_RAIL
    ));

    public static boolean isRail(Material material) {
        return RAILS.contains(material);
    }

    public static boolean hasRail(Collection<Material> materials) {
        return !Collections.disjoint(RAILS, materials);
    }

    public static boolean isRail(ItemStack itemStack) {
        return itemStack != null && isRail(itemStack.getType());
    }

    public static boolean hasRail(ItemStack... itemStacks) {
        return hasRail(toMaterials(itemStacks));
    }

    public static final Set<Material> MINECARTS = new HashSet<>(Arrays.asList(
            Material.MINECART,
            Material.CHEST_MINECART,
            Material.COMMAND_BLOCK_MINECART,
            Material.FURNACE_MINECART,
            Material.HOPPER_MINECART,
            Material.TNT_MINECART
    ));

    public static boolean isMinecart(Material material) {
        return MINECARTS.contains(material);
    }

    public static boolean hasMinecart(Collection<Material> materials) {
        return !Collections.disjoint(MINECARTS, materials);
    }

    public static boolean isMinecart(ItemStack itemStack) {
        return itemStack != null && isMinecart(itemStack.getType());
    }

    public static boolean hasMinecart(ItemStack... itemStacks) {
        return hasMinecart(toMaterials(itemStacks));
    }

    public static final Set<Material> EGGS = new HashSet<>(Arrays.asList(
            Material.EGG,
            Material.DRAGON_EGG,
            Material.TURTLE_EGG,

            Material.BAT_SPAWN_EGG,
            Material.BLAZE_SPAWN_EGG,
            Material.CAVE_SPIDER_SPAWN_EGG,
            Material.CHICKEN_SPAWN_EGG,
            Material.COD_SPAWN_EGG,
            Material.COW_SPAWN_EGG,
            Material.CREEPER_SPAWN_EGG,
            Material.DOLPHIN_SPAWN_EGG,
            Material.DONKEY_SPAWN_EGG,
            Material.DROWNED_SPAWN_EGG,
            Material.ELDER_GUARDIAN_SPAWN_EGG,
            Material.ENDERMAN_SPAWN_EGG,
            Material.ENDERMITE_SPAWN_EGG,
            Material.EVOKER_SPAWN_EGG,
            Material.GHAST_SPAWN_EGG,
            Material.GUARDIAN_SPAWN_EGG,
            Material.HORSE_SPAWN_EGG,
            Material.HUSK_SPAWN_EGG,
            Material.LLAMA_SPAWN_EGG,
            Material.MAGMA_CUBE_SPAWN_EGG,
            Material.MOOSHROOM_SPAWN_EGG,
            Material.MULE_SPAWN_EGG,
            Material.OCELOT_SPAWN_EGG,
            Material.PARROT_SPAWN_EGG,
            Material.PHANTOM_SPAWN_EGG,
            Material.PIG_SPAWN_EGG,
            Material.POLAR_BEAR_SPAWN_EGG,
            Material.PUFFERFISH_SPAWN_EGG,
            Material.RABBIT_SPAWN_EGG,
            Material.SALMON_SPAWN_EGG,
            Material.SHEEP_SPAWN_EGG,
            Material.SHULKER_SPAWN_EGG,
            Material.SILVERFISH_SPAWN_EGG,
            Material.SKELETON_HORSE_SPAWN_EGG,
            Material.SKELETON_SPAWN_EGG,
            Material.SLIME_SPAWN_EGG,
            Material.SPIDER_SPAWN_EGG,
            Material.SQUID_SPAWN_EGG,
            Material.STRAY_SPAWN_EGG,
            Material.TROPICAL_FISH_SPAWN_EGG,
            Material.TURTLE_SPAWN_EGG,
            Material.VEX_SPAWN_EGG,
            Material.VILLAGER_SPAWN_EGG,
            Material.VINDICATOR_SPAWN_EGG,
            Material.WITCH_SPAWN_EGG,
            Material.WITHER_SKELETON_SPAWN_EGG,
            Material.WOLF_SPAWN_EGG,
            Material.ZOMBIE_HORSE_SPAWN_EGG,
            Material.ZOMBIFIED_PIGLIN_SPAWN_EGG,
            Material.ZOMBIE_SPAWN_EGG,
            Material.ZOMBIE_VILLAGER_SPAWN_EGG,
            Material.CAT_SPAWN_EGG,
            Material.FOX_SPAWN_EGG,
            Material.PANDA_SPAWN_EGG,
            Material.PILLAGER_SPAWN_EGG,
            Material.PUFFERFISH_SPAWN_EGG,
            Material.RAVAGER_SPAWN_EGG,
            Material.TRADER_LLAMA_SPAWN_EGG,
            Material.WANDERING_TRADER_SPAWN_EGG
    ));

    public static boolean isEgg(Material material) {
        return EGGS.contains(material);
    }

    public static boolean hasEgg(Collection<Material> materials) {
        return !Collections.disjoint(EGGS, materials);
    }

    public static boolean isEgg(ItemStack itemStack) {
        return itemStack != null && isEgg(itemStack.getType());
    }

    public static boolean hasEgg(ItemStack... itemStacks) {
        return hasEgg(toMaterials(itemStacks));
    }

    public static Set<Material> PRESSURE_PLATES = new HashSet<>(Arrays.asList(
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,

            Material.ACACIA_PRESSURE_PLATE,
            Material.BIRCH_PRESSURE_PLATE,
            Material.DARK_OAK_PRESSURE_PLATE,
            Material.JUNGLE_PRESSURE_PLATE,
            Material.OAK_PRESSURE_PLATE,
            Material.SPRUCE_PRESSURE_PLATE,

            Material.STONE_PRESSURE_PLATE
    ));

    public static boolean isPressurePlate(Material material) {
        return PRESSURE_PLATES.contains(material);
    }

    public static boolean hasPressurePlate(Collection<Material> materials) {
        return !Collections.disjoint(PRESSURE_PLATES, materials);
    }

    public static boolean isPressurePlate(ItemStack itemStack) {
        return itemStack != null && isPressurePlate(itemStack.getType());
    }

    public static boolean hasPressurePlate(ItemStack... itemStacks) {
        return hasPressurePlate(toMaterials(itemStacks));
    }

    public static Set<Material> BUCKETS = new HashSet<>(Arrays.asList(
            Material.BUCKET,
            Material.LAVA_BUCKET,
            Material.MILK_BUCKET,
            Material.WATER_BUCKET,

            Material.COD_BUCKET,
            Material.PUFFERFISH_BUCKET,
            Material.SALMON_BUCKET,
            Material.TROPICAL_FISH_BUCKET
    ));

    public static boolean isBucket(Material material) {
        return BUCKETS.contains(material);
    }

    public static boolean hasBucket(Collection<Material> materials) {
        return !Collections.disjoint(BUCKETS, materials);
    }

    public static boolean isBucket(ItemStack itemStack) {
        return itemStack != null && isBucket(itemStack.getType());
    }

    public static boolean hasBucket(ItemStack... itemStacks) {
        return hasBucket(toMaterials(itemStacks));
    }

    public static Set<Material> WATER_BUCKETS = new HashSet<>(Arrays.asList(
            Material.WATER_BUCKET,

            Material.COD_BUCKET,
            Material.PUFFERFISH_BUCKET,
            Material.SALMON_BUCKET,
            Material.TROPICAL_FISH_BUCKET
    ));

    public static boolean isWaterBucket(Material material) {
        return WATER_BUCKETS.contains(material);
    }

    public static boolean hasWaterBucket(Collection<Material> materials) {
        return !Collections.disjoint(WATER_BUCKETS, materials);
    }

    public static boolean isWaterBucket(ItemStack itemStack) {
        return itemStack != null && isWaterBucket(itemStack.getType());
    }

    public static boolean hasWaterBucket(ItemStack... itemStacks) {
        return hasWaterBucket(toMaterials(itemStacks));
    }

    public static Set<Material> SHULKER_BOXES = new HashSet<>(Arrays.asList(
            Material.SHULKER_BOX,

            Material.WHITE_SHULKER_BOX,
            Material.ORANGE_SHULKER_BOX,
            Material.MAGENTA_SHULKER_BOX,
            Material.LIGHT_BLUE_SHULKER_BOX,
            Material.YELLOW_SHULKER_BOX,
            Material.LIME_SHULKER_BOX,
            Material.PINK_SHULKER_BOX,
            Material.GRAY_SHULKER_BOX,
            Material.LIGHT_GRAY_SHULKER_BOX,
            Material.CYAN_SHULKER_BOX,
            Material.PURPLE_SHULKER_BOX,
            Material.BLUE_SHULKER_BOX,
            Material.BROWN_SHULKER_BOX,
            Material.GREEN_SHULKER_BOX,
            Material.RED_SHULKER_BOX,
            Material.BLACK_SHULKER_BOX
    ));
    public static boolean isShulkerBox(Material material) {
        return SHULKER_BOXES.contains(material);
    }
    public static boolean hasShulkerBox(Collection<Material> materials) {
        return !Collections.disjoint(SHULKER_BOXES, materials);
    }
    public static boolean isShulkerBox(ItemStack itemStack) {
        return itemStack != null && isShulkerBox(itemStack.getType());
    }
    public static boolean hasShulkerBox(ItemStack... itemStacks) {
        return hasShulkerBox(toMaterials(itemStacks));
    }

    public static Set<Material> PICKAXES = new HashSet<>(Arrays.asList(
            Material.DIAMOND_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.WOODEN_PICKAXE
    ));
    public static boolean isPickaxe(Material material) {
        return PICKAXES.contains(material);
    }
    public static boolean hasPickaxe(Collection<Material> materials) {
        return !Collections.disjoint(PICKAXES, materials);
    }
    public static boolean isPickaxe(ItemStack itemStack) {
        return itemStack != null && isPickaxe(itemStack.getType());
    }
    public static boolean hasPickaxe(ItemStack... itemStacks) {
        return hasPickaxe(toMaterials(itemStacks));
    }
    
    public static Set<Material> AXES = new HashSet<>(Arrays.asList(
            Material.DIAMOND_AXE,
            Material.IRON_AXE,
            Material.GOLDEN_AXE,
            Material.STONE_AXE,
            Material.WOODEN_AXE
    ));
    public static boolean isAxe(Material material) {
        return AXES.contains(material);
    }
    public static boolean hasAxe(Collection<Material> materials) {
        return !Collections.disjoint(AXES, materials);
    }
    public static boolean isAxe(ItemStack itemStack) {
        return itemStack != null && isAxe(itemStack.getType());
    }
    public static boolean hasAxe(ItemStack... itemStacks) {
        return hasAxe(toMaterials(itemStacks));
    }
    
    public static Set<Material> SHOVELS = new HashSet<>(Arrays.asList(
            Material.DIAMOND_SHOVEL,
            Material.IRON_SHOVEL,
            Material.GOLDEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.WOODEN_SHOVEL
    ));
    public static boolean isShovel(Material material) {
        return SHOVELS.contains(material);
    }
    public static boolean hasShovel(Collection<Material> materials) {
        return !Collections.disjoint(SHOVELS, materials);
    }
    public static boolean isShovel(ItemStack itemStack) {
        return itemStack != null && isShovel(itemStack.getType());
    }
    public static boolean hasShovel(ItemStack... itemStacks) {
        return hasShovel(toMaterials(itemStacks));
    }

    public static Set<Material> LOGS = new HashSet<>(Arrays.asList(
        Material.ACACIA_LOG,
        Material.BIRCH_LOG,
        Material.DARK_OAK_LOG,
        Material.JUNGLE_LOG,
        Material.OAK_LOG,
        Material.SPRUCE_LOG
    ));
    public static boolean isLog(Material material) {
        return LOGS.contains(material);
    }
    public static boolean hasLog(Collection<Material> materials) {
        return !Collections.disjoint(LOGS, materials);
    }
    public static boolean isLog(ItemStack itemStack) {
        return itemStack != null && isLog(itemStack.getType());
    }
    public static boolean hasLog(ItemStack... itemStacks) {
        return hasLog(toMaterials(itemStacks));
    }
    
    public static Set<Material> LEAVES = new HashSet<>(Arrays.asList(
        Material.ACACIA_LEAVES,
        Material.BIRCH_LEAVES,
        Material.DARK_OAK_LEAVES,
        Material.JUNGLE_LEAVES,
        Material.OAK_LEAVES,
        Material.SPRUCE_LEAVES
    ));
    public static boolean isLeaves(Material material) {
        return LEAVES.contains(material);
    }
    public static boolean hasLeaves(Collection<Material> materials) {
        return !Collections.disjoint(LEAVES, materials);
    }
    public static boolean isLeaves(ItemStack itemStack) {
        return itemStack != null && isLeaves(itemStack.getType());
    }
    public static boolean hasLeaves(ItemStack... itemStacks) {
        return hasLeaves(toMaterials(itemStacks));
    }

    public static Set<Material> SIGNS = new HashSet<>(Arrays.asList(
        Material.ACACIA_SIGN,
        Material.BIRCH_SIGN,
        Material.DARK_OAK_SIGN,
        Material.JUNGLE_SIGN,
        Material.OAK_SIGN,
        Material.SPRUCE_SIGN,
        
        Material.ACACIA_WALL_SIGN,
        Material.BIRCH_WALL_SIGN,
        Material.DARK_OAK_WALL_SIGN,
        Material.JUNGLE_WALL_SIGN,
        Material.OAK_WALL_SIGN,
        Material.SPRUCE_WALL_SIGN
    ));
    public static boolean isSign(Material material) {
        return SIGNS.contains(material);
    }
    public static boolean hasSign(Collection<Material> materials) {
        return !Collections.disjoint(SIGNS, materials);
    }
    public static boolean isSign(ItemStack itemStack) {
        return itemStack != null && isSign(itemStack.getType());
    }
    public static boolean hasSign(ItemStack... itemStacks) {
        return hasSign(toMaterials(itemStacks));
    }

    public static final Set<PotionEffectType> POSITIVE_EFFECTS = new HashSet<>(Arrays.asList(
            PotionEffectType.SPEED,
            PotionEffectType.HASTE,
            PotionEffectType.STRENGTH,
            PotionEffectType.INSTANT_HEALTH,
            PotionEffectType.JUMP_BOOST,
            PotionEffectType.REGENERATION,
            PotionEffectType.RESISTANCE,
            PotionEffectType.FIRE_RESISTANCE,
            PotionEffectType.WATER_BREATHING,
            PotionEffectType.INVISIBILITY,
            PotionEffectType.NIGHT_VISION,
            PotionEffectType.HEALTH_BOOST,
            PotionEffectType.ABSORPTION,
            PotionEffectType.SATURATION,
            PotionEffectType.GLOWING,
            PotionEffectType.LUCK,
            PotionEffectType.CONDUIT_POWER,
            PotionEffectType.DOLPHINS_GRACE
    ));

    public static boolean isPositiveEffect(PotionEffectType material) {
        return POSITIVE_EFFECTS.contains(material);
    }

    public static boolean hasPositiveEffect(Collection<PotionEffectType> materials) {
        return !Collections.disjoint(POSITIVE_EFFECTS, materials);
    }

    public static final Set<PotionEffectType> NEGATIVE_EFFECTS = new HashSet<>(Arrays.asList(
            PotionEffectType.SLOWNESS,
            PotionEffectType.MINING_FATIGUE,
            PotionEffectType.INSTANT_DAMAGE,
            PotionEffectType.NAUSEA,
            PotionEffectType.BLINDNESS,
            PotionEffectType.HUNGER,
            PotionEffectType.WEAKNESS,
            PotionEffectType.POISON,
            PotionEffectType.WITHER,
            PotionEffectType.LEVITATION,
            PotionEffectType.UNLUCK,
            PotionEffectType.SLOW_FALLING
    ));

    public static boolean isNegativeEffect(PotionEffectType material) {
        return NEGATIVE_EFFECTS.contains(material);
    }

    public static boolean hasNegativeEffect(Collection<PotionEffectType> materials) {
        return !Collections.disjoint(NEGATIVE_EFFECTS, materials);
    }
}
