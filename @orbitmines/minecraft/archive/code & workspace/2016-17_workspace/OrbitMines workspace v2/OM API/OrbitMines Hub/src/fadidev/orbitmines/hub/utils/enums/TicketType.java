package fadidev.orbitmines.hub.utils.enums;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.hub.handlers.HubMessages;
import fadidev.orbitmines.hub.handlers.Ticket;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Fadi on 8-9-2016.
 */
public enum TicketType {
    
    // SG Kits \\
    RUNNER_KIT(0, "Runner Kit", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.ONE_TIME, 11, 3),
    FIGHTER_KIT(1, "Fighter Kit", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.ONE_TIME, 21, 3),
    WARRIOR_KIT(2, "Warrior Kit", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.ONE_TIME, 23, 3),
    BOMBER_KIT(3, "Bomber Kit", MiniGameType.SURVIVAL_GAMES, Rarity.LEGENDARY, Uses.ONE_TIME, 15, 3),
    ARCHER_KIT(4, "Archer Kit", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.ONE_TIME, 13, 3),

    // SG Game Effects \\
    WHITE_ARMOR(5, "White Armor", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.PERMANENT, 12, 1),
    BLUE_ARMOR(6, "Blue Armor", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.PERMANENT, 21, 1),
    GREEN_ARMOR(7, "Green Armor", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.PERMANENT, 14, 1),
    BLACK_ARMOR(8, "Black Armor", MiniGameType.SURVIVAL_GAMES, Rarity.LEGENDARY, Uses.PERMANENT, 13, 1),
    LIGHT_BLUE_ARMOR(9, "Light Blue Armor", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.PERMANENT, 16, 1),
    PINK_ARMOR(10, "Pink Armor", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.PERMANENT, 10, 1),
    LIGHT_GREEN_ARMOR(11, "Light Green Armor", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.PERMANENT, 25, 1),
    DARK_BLUE_ARMOR(12, "Dark Blue Armor", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.PERMANENT, 23, 1),
    PURPLE_ARMOR(13, "Purple Armor", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.PERMANENT, 24, 1),
    ORANGE_ARMOR(14, "Orange Armor", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.PERMANENT, 19, 1),
    RED_ARMOR(15, "Red Armor", MiniGameType.SURVIVAL_GAMES, Rarity.LEGENDARY, Uses.PERMANENT, 22, 1),
    CYAN_ARMOR(16, "Cyan Armor", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.PERMANENT, 20, 1),
    YELLOW_ARMOR(17, "Yellow Armor", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.PERMANENT, 15, 1),
    GRAY_ARMOR(18, "Gray Armor", MiniGameType.SURVIVAL_GAMES, Rarity.COMMON, Uses.PERMANENT, 11, 1),
    ENABLE_POTIONS(19, "Enable Potions [All]", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.ONE_TIME, 29, 1),
    DOUBLE_LOOT(20, "Double Loot [All]", MiniGameType.SURVIVAL_GAMES, Rarity.RARE, Uses.ONE_TIME, 33, 1),
    ENABLE_POTIONS_PLAYER(21, "Enable Potions [Solo]", MiniGameType.SURVIVAL_GAMES, Rarity.LEGENDARY, Uses.ONE_TIME, 30, 1),
    DOUBLE_LOOT_PLAYER(22, "Double Loot [Solo]", MiniGameType.SURVIVAL_GAMES, Rarity.LEGENDARY, Uses.ONE_TIME, 32, 1),

    // UHC Kits \\
    SURVIVOR_KIT(23, "Survivor Kit", MiniGameType.ULTRA_HARD_CORE, Rarity.COMMON, Uses.ONE_TIME, 11, 3),
    STARTER_KIT(24, "Starter Kit", MiniGameType.ULTRA_HARD_CORE, Rarity.COMMON, Uses.ONE_TIME, 21, 3),
    APPLETREE_KIT(25, "Appletree Kit", MiniGameType.ULTRA_HARD_CORE, Rarity.RARE, Uses.ONE_TIME, 13, 3),
    SPEEDSTER_KIT(26, "Speedster Kit", MiniGameType.ULTRA_HARD_CORE, Rarity.RARE, Uses.ONE_TIME, 23, 3),
    MINER_KIT(27, "Miner Kit", MiniGameType.ULTRA_HARD_CORE, Rarity.LEGENDARY, Uses.ONE_TIME, 15, 3),

    // UHC Game Effects \\
    DOUBLE_IRON(28, "Double Iron [All]", MiniGameType.ULTRA_HARD_CORE, Rarity.RARE, Uses.ONE_TIME, 12, 1),
    DOUBLE_IRON_PLAYER(29, "Double Iron [Solo]", MiniGameType.ULTRA_HARD_CORE, Rarity.RARE, Uses.ONE_TIME, 21, 1),
    GOLD_FROM_LAPIS(30, "Blue Gold [Solo]", MiniGameType.ULTRA_HARD_CORE, Rarity.RARE, Uses.ONE_TIME, 14, 1),
    GOLD_FROM_REDSTONE(31, "Red Gold [Solo]", MiniGameType.ULTRA_HARD_CORE, Rarity.LEGENDARY, Uses.ONE_TIME, 23, 1),

    // Skywars Kits \\
    TANK_KIT(32, "Tank Kit", MiniGameType.SKYWARS, Rarity.COMMON, Uses.ONE_TIME, 11, 3),
    SNOWGOLEM_KIT(33, "SnowGolem Kit", MiniGameType.SKYWARS, Rarity.COMMON, Uses.ONE_TIME, 21, 3),
    CREEPER_KIT(34, "Creeper Kit", MiniGameType.SKYWARS, Rarity.RARE, Uses.ONE_TIME, 13, 3),
    ENCHANTER_KIT(35, "Enderman Kit", MiniGameType.SKYWARS, Rarity.LEGENDARY, Uses.ONE_TIME, 23, 3),
    ENDERMAN_KIT(36, "Enchanter Kit", MiniGameType.SKYWARS, Rarity.RARE, Uses.ONE_TIME, 15, 3),

    // Skywars Perks \\
    THE_WOOL_HUT_CAGE(37, "The Wool Hut", MiniGameType.SKYWARS, Rarity.LEGENDARY, Uses.PERMANENT, 12, 1),
    NETHER_CAGE(38, "Nether Cage", MiniGameType.SKYWARS, Rarity.COMMON, Uses.PERMANENT, 10, 1),
    DEATH_CAGE(39, "Death Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 13, 1),
    CAVE_CAGE(40, "Cave Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 24, 1),
    SUN_CAGE(41, "Sun Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 15, 1),
    HOT_AIR_BALLOON_CAGE(42, "Hot Air Balloon Cage", MiniGameType.SKYWARS, Rarity.LEGENDARY, Uses.PERMANENT, 21, 1),
    MESA_CAGE(43, "Mesa Cage", MiniGameType.SKYWARS, Rarity.COMMON, Uses.PERMANENT, 20, 1),
    SEA_CAGE(44, "Sea Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 23, 1),
    WOODEN_CAGE(45, "Wooden Cage", MiniGameType.SKYWARS, Rarity.COMMON, Uses.PERMANENT, 16, 1),
    CACTUS_CAGE(46, "Cactus Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 14, 1),
    ENCHANTER_CAGE(47, "Enchanter Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 19, 1),
    GRAY_RAINBOW_CAGE(48, "Gray Rainbow Cage", MiniGameType.SKYWARS, Rarity.LEGENDARY, Uses.PERMANENT, 11, 1),
    SLIME_CAGE(49, "Slime Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 25, 1),
    THE_END_CAGE(50, "End Cage", MiniGameType.SKYWARS, Rarity.COMMON, Uses.PERMANENT, 22, 1),

    // Chicken Fight Kits \\
    CHICKEN_MAMA_KIT(51, "Chicken Mama Kit", MiniGameType.CHICKEN_FIGHT, Rarity.COMMON, Uses.PERMANENT, 11, 1),
    BABY_CHICKEN_KIT(52, "Baby Chicken Kit", MiniGameType.CHICKEN_FIGHT, Rarity.COMMON, Uses.ONE_TIME, 21, 3),
    HOT_WING_KIT(53, "Hot Wing Kit", MiniGameType.CHICKEN_FIGHT, Rarity.RARE, Uses.ONE_TIME, 13, 3),
    CHICKEN_WARLORD_KIT(54, "Chicken Warlord Kit", MiniGameType.CHICKEN_FIGHT, Rarity.RARE, Uses.ONE_TIME, 23, 3),
    CHICKEN_KIT(55, "'Chicken' Kit", MiniGameType.CHICKEN_FIGHT, Rarity.LEGENDARY, Uses.ONE_TIME, 15, 3),

    // Chicken Fight Perks \\
    SPEED_BOOST(56, "Speed Boost [All]", MiniGameType.CHICKEN_FIGHT, Rarity.COMMON, Uses.ONE_TIME, 22, 1),
    JUMP_BOOST(57, "Jump Boost [All]", MiniGameType.CHICKEN_FIGHT, Rarity.RARE, Uses.ONE_TIME, 13, 1),

    ISLAND_CAGE(58, "Island Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 28, 1), // Added: 08-08-2015 (Skywars Perk) \\
    TROPHEE_CAGE(59, "Trophee Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 29, 1), // Added: 15-08-2015 (Skywars Perk) \\
    OCTOPUS_CAGE(60, "Octopus Cage", MiniGameType.SKYWARS, Rarity.LEGENDARY, Uses.PERMANENT, 30, 1), // Added: 30-08-2015 (Skywars Perk) \\
    CASTLE_CAGE(61, "Castle Cage", MiniGameType.SKYWARS, Rarity.RARE, Uses.PERMANENT, 31, 1); // Added: 19-09-2015 (Skywars Perk) \\
    
    private String name;
    private MiniGameType type;
    private Rarity rarity;
    private Uses uses;
    private int slot;
    private int maxAmount;

    TicketType(int ticketid, String name, MiniGameType type, Rarity rarity, Uses uses, int slot, int maxAmount){
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.uses = uses;
        this.slot = slot;
        this.maxAmount = maxAmount;
    }

    public String getName() {
        return name;
    }

    public MiniGameType getType() {
        return type;
    }

    public String getNameWithGame(){
        return getName() + " (" + getType().getSignName() + ")";
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Uses getUses() {
        return uses;
    }

    public int getSlot() {
        return slot;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public ItemStack getItemStack(){
        switch(this){
            case ARCHER_KIT:
                return new ItemStack(Material.BOW);
            case BLACK_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK);
            case BLUE_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLUE);
            case BOMBER_KIT:
                return new ItemStack(Material.TNT);
            case CYAN_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.TEAL);
            case DARK_BLUE_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.NAVY);
            case DOUBLE_LOOT:
                return new ItemStack(Material.CHEST);
            case DOUBLE_LOOT_PLAYER:
                return new ItemStack(Material.CHEST);
            case ENABLE_POTIONS:
                return new ItemStack(Material.POTION);
            case ENABLE_POTIONS_PLAYER:
                return new ItemStack(Material.POTION);
            case FIGHTER_KIT:
                return new ItemStack(Material.STONE_AXE);
            case GRAY_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.GRAY);
            case GREEN_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.GREEN);
            case LIGHT_BLUE_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.AQUA);
            case LIGHT_GREEN_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.LIME);
            case ORANGE_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.ORANGE);
            case PINK_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.FUCHSIA);
            case PURPLE_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.PURPLE);
            case RED_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.RED);
            case RUNNER_KIT:
                return new ItemStack(Material.LEATHER_BOOTS);
            case WARRIOR_KIT:
                return new ItemStack(Material.IRON_CHESTPLATE);
            case WHITE_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.WHITE);
            case YELLOW_ARMOR:
                return ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.YELLOW);
            case APPLETREE_KIT:
                return new ItemStack(Material.SAPLING);
            case DOUBLE_IRON:
                return new ItemStack(Material.IRON_ORE);
            case DOUBLE_IRON_PLAYER:
                return new ItemStack(Material.IRON_ORE);
            case GOLD_FROM_LAPIS:
                return new ItemStack(Material.LAPIS_ORE);
            case GOLD_FROM_REDSTONE:
                return new ItemStack(Material.REDSTONE_ORE);
            case MINER_KIT:
                return new ItemStack(Material.DIAMOND_PICKAXE);
            case SPEEDSTER_KIT:
                return OrbitMinesAPI.getApi().getNms().customItem().hideFlags(ItemUtils.itemstack(Material.POTION, 1, 8194), 2);
            case STARTER_KIT:
                return new ItemStack(Material.WOOD_AXE);
            case SURVIVOR_KIT:
                return new ItemStack(Material.COOKED_BEEF);
            case CACTUS_CAGE:
                return new ItemStack(Material.CACTUS);
            case CAVE_CAGE:
                return new ItemStack(Material.SMOOTH_BRICK);
            case CREEPER_KIT:
                return new ItemStack(Material.TNT);
            case DEATH_CAGE:
                return OrbitMinesAPI.getApi().getNms().customItem().hideFlags(new ItemStack(Material.SKULL_ITEM), 1);
            case ENCHANTER_CAGE:
                return new ItemStack(Material.ENCHANTMENT_TABLE);
            case ENCHANTER_KIT:
                return new ItemStack(Material.ENCHANTMENT_TABLE);
            case ENDERMAN_KIT:
                return new ItemStack(Material.ENDER_PEARL);
            case GRAY_RAINBOW_CAGE:
                return ItemUtils.itemstack(Material.STAINED_GLASS, 1, 7);
            case HOT_AIR_BALLOON_CAGE:
                return new ItemStack(Material.WOOL);
            case MESA_CAGE:
                return new ItemStack(Material.RED_SANDSTONE);
            case NETHER_CAGE:
                return new ItemStack(Material.NETHER_BRICK);
            case SEA_CAGE:
                return new ItemStack(Material.PRISMARINE);
            case SLIME_CAGE:
                return new ItemStack(Material.SLIME_BLOCK);
            case SNOWGOLEM_KIT:
                return new ItemStack(Material.SNOW_BALL);
            case SUN_CAGE:
                return ItemUtils.itemstack(Material.STAINED_GLASS, 1, 4);
            case ISLAND_CAGE:
                return new ItemStack(Material.GRASS);
            case TROPHEE_CAGE:
                return new ItemStack(Material.FLOWER_POT_ITEM);
            case OCTOPUS_CAGE:
                return ItemUtils.itemstack(Material.STAINED_CLAY, 1, 10);
            case CASTLE_CAGE:
                return new ItemStack(Material.FENCE);
            case TANK_KIT:
                return new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            case THE_END_CAGE:
                return new ItemStack(Material.ENDER_PORTAL_FRAME);
            case THE_WOOL_HUT_CAGE:
                return ItemUtils.itemstack(Material.WOOL, 1, 11);
            case WOODEN_CAGE:
                return ItemUtils.itemstack(Material.LOG, 1, 1);
            case BABY_CHICKEN_KIT:
                return new ItemStack(Material.EGG);
            case CHICKEN_KIT:
                return new ItemStack(Material.WOOL);
            case CHICKEN_MAMA_KIT:
                return new ItemStack(Material.FEATHER);
            case CHICKEN_WARLORD_KIT:
                return new ItemStack(Material.IRON_INGOT);
            case HOT_WING_KIT:
                return new ItemStack(Material.FIREBALL);
            case JUMP_BOOST:
                return OrbitMinesAPI.getApi().getNms().customItem().hideFlags(ItemUtils.itemstack(Material.POTION, 1, 8203), 2);
            case SPEED_BOOST:
                return OrbitMinesAPI.getApi().getNms().customItem().hideFlags(ItemUtils.itemstack(Material.POTION, 1, 8194), 2);
        }
        return null;
    }


    public List<String> getDescription(HubPlayer omp){
        switch(this){
            case ARCHER_KIT:
                return Arrays.asList("Bow", "2 Arrows", getUses().getName());
            case BLACK_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Black"), getUses().getName());
            case BLUE_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Blue"), getUses().getName());
            case BOMBER_KIT:
                return Arrays.asList("10 TNT", getUses().getName());
            case CYAN_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Cyan"), getUses().getName());
            case DARK_BLUE_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Dark Blue"), getUses().getName());
            case DOUBLE_LOOT:
                return Arrays.asList(HubMessages.INV_DOUBLE_LOOT_CHESTS.get(omp), "(" + HubMessages.INV_EFFECTS_ALL.get(omp) + ")", getUses().getName());
            case DOUBLE_LOOT_PLAYER:
                return Arrays.asList(HubMessages.INV_DOUBLE_LOOT_CHESTS.get(omp), "(" + HubMessages.INV_EFFECTS_YOU.get(omp) + ")", getUses().getName());
            case ENABLE_POTIONS:
                return Arrays.asList(HubMessages.INV_POTION_CHESTS.get(omp), "(" + HubMessages.INV_EFFECTS_ALL.get(omp) + ")", getUses().getName());
            case ENABLE_POTIONS_PLAYER:
                return Arrays.asList(HubMessages.INV_POTION_CHESTS.get(omp), "(" + HubMessages.INV_EFFECTS_YOU.get(omp) + ")", getUses().getName());
            case FIGHTER_KIT:
                return Arrays.asList("Wood/Stone " + HubMessages.WORD_WEAPON.get(omp), getUses().getName());
            case GRAY_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Gray"), getUses().getName());
            case GREEN_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Green"), getUses().getName());
            case LIGHT_BLUE_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Light Blue"), getUses().getName());
            case LIGHT_GREEN_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Light Green"), getUses().getName());
            case ORANGE_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Orange"), getUses().getName());
            case PINK_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Pink"), getUses().getName());
            case PURPLE_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Purple"), getUses().getName());
            case RED_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Red"), getUses().getName());
            case RUNNER_KIT:
                return Arrays.asList(HubMessages.INV_RUNNER_KIT.get(omp), getUses().getName());
            case WARRIOR_KIT:
                return Arrays.asList(HubMessages.INV_WARRIOR_KIT.get(omp), getUses().getName());
            case WHITE_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "White"), getUses().getName());
            case YELLOW_ARMOR:
                return Arrays.asList(HubMessages.INV_LEATHER_WILL_BE.get(omp, "Yellow"), getUses().getName());
            case APPLETREE_KIT:
                return Arrays.asList(HubMessages.INV_APPLETREE_KIT.get(omp), getUses().getName());
            case DOUBLE_IRON:
                return Arrays.asList("+1 Iron Ore " + HubMessages.WORD_FROM.get(omp) + " Iron Ore.", "50% " + HubMessages.WORD_CHANCE.get(omp) + " (" + HubMessages.INV_EFFECTS_ALL.get(omp) + ")", getUses().getName());
            case DOUBLE_IRON_PLAYER:
                return Arrays.asList("+1 Iron Ore " + HubMessages.WORD_FROM.get(omp) + " Iron Ore.", "50% " + HubMessages.WORD_CHANCE.get(omp) + " (" + HubMessages.INV_EFFECTS_YOU.get(omp) + ")", getUses().getName());
            case GOLD_FROM_LAPIS:
                return Arrays.asList("+1 Gold Ore " + HubMessages.WORD_FROM.get(omp) + " Lapis Ore", "50% " + HubMessages.WORD_CHANCE.get(omp) + " (" + HubMessages.INV_EFFECTS_YOU.get(omp) + ")", getUses().getName());
            case GOLD_FROM_REDSTONE:
                return Arrays.asList("+1 Gold Ore " + HubMessages.WORD_FROM.get(omp) + " Redstone Ore", "50% " + HubMessages.WORD_CHANCE.get(omp), getUses().getName());
            case MINER_KIT:
                return Arrays.asList(HubMessages.INV_MINER_KIT.get(omp), getUses().getName());
            case SPEEDSTER_KIT:
                return Arrays.asList("Swiftness I Potion (3:00).", getUses().getName());
            case STARTER_KIT:
                return Arrays.asList("Wooden Sword", "Wooden Pickaxe", "Wooden Axe", "Wooden Shovel", getUses().getName());
            case SURVIVOR_KIT:
                return Arrays.asList("15 Cooked Steak.", getUses().getName());
            case CACTUS_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Cactus"), getUses().getName());
            case CAVE_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Cave"), getUses().getName());
            case CREEPER_KIT:
                return Arrays.asList("20 TNT", "Flint and Steel", getUses().getName());
            case DEATH_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Death"), getUses().getName());
            case ENCHANTER_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Enchanter"), getUses().getName());
            case ENCHANTER_KIT:
                return Arrays.asList("Enchantment Table", "15 EXP Potions", getUses().getName());
            case ENDERMAN_KIT:
                return Arrays.asList("2 Enderpearls", getUses().getName());
            case GRAY_RAINBOW_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Gray Rainbow"), getUses().getName());
            case HOT_AIR_BALLOON_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Hot Air Balloon"), getUses().getName());
            case MESA_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Mesa"), getUses().getName());
            case NETHER_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Nether"), getUses().getName());
            case SEA_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Sea"), getUses().getName());
            case SLIME_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Slime"), getUses().getName());
            case SNOWGOLEM_KIT:
                return Arrays.asList("Black Leather Helmet", "1 Carrot", "48 Snowballs", getUses().getName());
            case SUN_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Sun"), getUses().getName());
            case TANK_KIT:
                return Arrays.asList("Leather Helmet", "Chainmail Chestplate", "Chainmail Leggings", "Leather Boots", getUses().getName());
            case THE_END_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "End"), getUses().getName());
            case THE_WOOL_HUT_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Wool Hut"), getUses().getName());
            case WOODEN_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Wooden"), getUses().getName());
            case ISLAND_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Island"), getUses().getName());
            case TROPHEE_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Trophee"), getUses().getName());
            case OCTOPUS_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Octopus"), getUses().getName());
            case CASTLE_CAGE:
                return Arrays.asList(HubMessages.INV_SPAWN_IN_CAGE.get(omp, "Castle"), getUses().getName());
            case BABY_CHICKEN_KIT:
                return Arrays.asList("Ability: Egg Bomb", HubMessages.INV_TAKEN_KNOCKBACK.get(omp) + ": 150%", HubMessages.INV_TAKEN_DAMAGE.get(omp) + ": 110%", HubMessages.WORD_DAMAGE.get(omp) + ": 100%", getUses().getName());
            case CHICKEN_KIT:
                return Arrays.asList("Ability: Wool Trail", HubMessages.INV_TAKEN_KNOCKBACK.get(omp) + ": 95%", HubMessages.INV_TAKEN_DAMAGE.get(omp) + ": 90%", HubMessages.WORD_DAMAGE.get(omp) + ": 120%", getUses().getName());
            case CHICKEN_MAMA_KIT:
                return Arrays.asList("Ability: Feather Attack", HubMessages.INV_TAKEN_KNOCKBACK.get(omp) + ": 120%", HubMessages.INV_TAKEN_DAMAGE.get(omp) + ": 100%", HubMessages.WORD_DAMAGE.get(omp) + ": 100%", getUses().getName());
            case CHICKEN_WARLORD_KIT:
                return Arrays.asList("Ability: Iron Fist", HubMessages.INV_TAKEN_KNOCKBACK.get(omp) + "T: 90%", HubMessages.INV_TAKEN_DAMAGE.get(omp) + ": 80%", HubMessages.WORD_DAMAGE.get(omp) + ": 150%", getUses().getName());
            case HOT_WING_KIT:
                return Arrays.asList("Ability: Fire Shield", HubMessages.INV_TAKEN_KNOCKBACK.get(omp) + ": 130%", HubMessages.INV_TAKEN_DAMAGE.get(omp) + ": 150%", HubMessages.WORD_DAMAGE.get(omp) + ": 100-400% [Fire]", getUses().getName());
            case JUMP_BOOST:
                return Arrays.asList("Jump Boost V.", "(" + HubMessages.INV_EFFECTS_ALL.get(omp) + ")", getUses().getName());
            case SPEED_BOOST:
                return Arrays.asList("Speed Boost V.", "(" + HubMessages.INV_EFFECTS_ALL.get(omp) + ")", getUses().getName());
        }
        return new ArrayList<>();
    }

    public Kit getKit(){
        return Kit.getKit(toString());
    }

    public void setup(){
        Ticket.TICKETS.add(this);

        boolean kit = toString().endsWith("_KIT");
        if(kit)
            Ticket.KITS.add(this);

        switch(type){
            case SURVIVAL_GAMES:
                if(kit)
                    Ticket.SG_KITS.add(this);
                else
                    Ticket.SG_PERKS.add(this);
                break;
            case ULTRA_HARD_CORE:
                if(kit)
                    Ticket.UHC_KITS.add(this);
                else
                    Ticket.UHC_PERKS.add(this);
                break;
            case SKYWARS:
                if(kit)
                    Ticket.SKYWARS_KITS.add(this);
                else
                    Ticket.SKYWARS_PERKS.add(this);
                break;
            case CHICKEN_FIGHT:
                if(kit)
                    Ticket.CF_KITS.add(this);
                else
                    Ticket.CF_PERKS.add(this);
                break;
            case GHOST_ATTACK:
                break;
            case SPLEEF:
                break;
            case SPLATCRAFT:
                break;
        }
    }

    public static Ticket getTicket(ItemStack item){
        TicketType type = null;
        for(TicketType tickettype : Ticket.TICKETS){
            if(item.getItemMeta().getDisplayName().endsWith(tickettype.getNameWithGame()))
                type = tickettype;
        }

        if(type.getMaxAmount() != 1)
            return new Ticket(type, Integer.parseInt(item.getItemMeta().getDisplayName().split(" ")[0].substring(2).replace("x", "")));
        return new Ticket(type, 1);
    }

    public static ItemStack getRandom(){
        List<Ticket> tickets = new ArrayList<>();
        for(TicketType type : Ticket.TICKETS){
            for(int i = 0; i < type.getRarity().getAmount(); i++)
                tickets.add(new Ticket(type, Utils.RANDOM.nextInt(type.getMaxAmount()) +1));
        }

        Ticket ticket = tickets.get(Utils.RANDOM.nextInt(tickets.size()));
        TicketType type = ticket.getType();

        ItemStack item = type.getItemStack();
        ItemMeta meta = item.getItemMeta();
        if(type.getMaxAmount() != 1)
            meta.setDisplayName(type.getRarity().getColor() + ticket.getAmount() + "x " + type.getNameWithGame());
        else
            meta.setDisplayName(type.getRarity().getColor() + type.getNameWithGame());
        item.setItemMeta(meta);

        return OrbitMinesAPI.getApi().getNms().customItem().hideFlags(item, 34);
    }

    public static TicketType fromID(int ticketId){
        for(TicketType type : Ticket.TICKETS){
            if(type.ordinal() == ticketId)
                return type;
        }
        return null;
    }
}
