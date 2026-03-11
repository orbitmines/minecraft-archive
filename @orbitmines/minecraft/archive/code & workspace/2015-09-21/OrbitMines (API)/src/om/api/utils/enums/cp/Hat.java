package om.api.utils.enums.cp;

import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Material;

public enum Hat {

	STONE_BRICKS(Material.SMOOTH_BRICK, 0, 75, "§7Stone Bricks Hat", "StoneBricks"),
	GREEN_GLASS(Material.STAINED_GLASS, 5, 125, "§aLime Stained Glass Hat", "GreenGlass"),
	CACTUS(Material.CACTUS, 0, 100, "§2Cactus Hat", "Cactus"),
	SNOW(Material.SNOW, 0, 75, "§fSnow Block Hat", "Snow"),
	TNT(Material.TNT, 0, 125, "§cTNT Hat", "TNT"),
	COAL_ORE(Material.COAL_ORE, 0, 100, "§8Coal Ore Hat", "CoalOre"),
	BLACK_GLASS(Material.STAINED_GLASS, 15, 125, "§8Black Stained Glass Hat", "BlackGlass"),
	FURNACE(Material.FURNACE, 0, 100, "§7Furnace Hat", "Furnace"),
	QUARTZ_ORE(Material.QUARTZ_ORE, 0, 100, "§cQuartz Ore Hat", "QuartzOre"),
	HAY_BALE(Material.HAY_BLOCK, 0, 100, "§eHay Bale Hat", "HayBale"),
	REDSTONE_ORE(Material.REDSTONE_ORE, 0, 125, "§4Redstone Ore Hat", "RedstoneOre"),
	ICE(Material.ICE, 0, 150, "§bIce Hat", "Ice"),
	WORKBENCH(Material.WORKBENCH, 0, 100, "§6Workbench Hat", "Workbench"),
	GRASS(Material.GRASS, 0, 100, "§aGrass Hat", "Grass"),
	RED_GLASS(Material.STAINED_GLASS, 14, 125, "§4Red Stained Glass Hat", "RedGlass"),
	BEDROCK(Material.BEDROCK, 0, 125, "§8Bedrock Hat", "Bedrock"),
	LAPIS_ORE(Material.LAPIS_ORE, 0, 100, "§1Lapis Ore Hat", "LapisOre"),
	REDSTONE_BLOCK(Material.REDSTONE_BLOCK, 0, 125, "§4Redstone Block Hat", "RedstoneBlock"),
	QUARTZ_BLOCK(Material.QUARTZ_BLOCK, 0, 75, "§fQuartz Block Hat", "QuartzBlock"),
	LAPIS_BLOCK(Material.LAPIS_BLOCK, 0, 100, "§1Lapis Block Hat", "LapisBlock"),
	MAGENTA_GLASS(Material.STAINED_GLASS, 2, 125, "§dMagenta Stained Glass Hat", "MagentaGlass"),
	COAL_BLOCK(Material.COAL_BLOCK, 0, 100, "§0Coal Block Hat", "CoalBlock"),
	MELON(Material.MELON_BLOCK, 0, 100, "§2Melon Hat", "Melon"),
	GLASS(Material.GLASS, 0, 100, "§fGlass Hat", "Glass"),
	YELLOW_GLASS(Material.STAINED_GLASS, 4, 125, "§eYellow Stained Glass Hat", "YellowGlass"),
	MYCELIUM(Material.MYCEL, 4, 75, "§7Mycelium Hat", "Mycelium"),
	LEAVES(Material.LEAVES, 0, 125, "§2Leaves Hat", "Leaves"),
	ORANGE_GLASS(Material.STAINED_GLASS, 1, 125, "§6Orange Stained Glass Hat", "OrangeGlass"),
	
	DIORITE(Material.STONE, 4, 100, "§fPolished Diorite Hat", "Diorite"),
	DARK_PRISMARINE(Material.PRISMARINE, 2, 150, "§3Dark Prismarine Hat", "DarkPrismarine"),
	SLIME_BLOCK(Material.SLIME_BLOCK, 0, 200, "§aSlime Block Hat", "SlimeBlock"),
	GRANITE(Material.STONE, 2, 100, "§cPolished Granite Hat", "Granite"),
	SEA_LANTERN(Material.SEA_LANTERN, 0, 225, "§fSea Lantern Hat", "SeaLantern"),
	PRISMARINE_BRICKS(Material.PRISMARINE, 1, 150, "§9Prismarine Bricks Hat", "PrismarineBricks"),
	SPONGE(Material.SPONGE, 0, 100, "§eSponge Hat", "Sponge"),
	CHEST(Material.ENDER_CHEST, 0, 175, "§3EnderChest Hat", "Chest"),
	GLOWSTONE(Material.GLOWSTONE, 0, 200, "§6Glowstone Hat", "Glowstone"),
	WET_SPONGE(Material.SPONGE, 1, 125, "§eWet Sponge Hat", "WetSponge"),
	ANDESITE(Material.STONE, 6, 100, "§7Polished Andesite Hat", "Andesite"),
	BLUE_GLASS(Material.STAINED_GLASS, 11, 125, "§1Blue Stained Glass Hat", "BlueGlass"),
	
	ACACIA_WOOD(Material.WOOD, 4, 75, "§6Acacia Wood Hat", "AcaciaWood"),
	RED_WOOL(Material.WOOL, 14, 100, "§cRed Wool Hat", "RedWool"),
	BROWN_GLASS(Material.STAINED_GLASS, 12, 125, "§fBrown Stained Glass Hat", "BrownGlass"),
	SOUL_SAND(Material.SOUL_SAND, 0, 100, "§eSoul Sand Hat", "SoulSand"),
	CHISELLED_STONE_BRICKS(Material.SMOOTH_BRICK, 3, 100, "§7Chiselled Stone Bricks Hat", "ChiselledStoneBricks"),
	BOOKSHELF(Material.BOOKSHELF, 0, 100, "§eBookshelf Hat", "Bookshelf"),
	NETHERRACK(Material.NETHERRACK, 0, 75, "§cNetherrack Hat", "Netherrack"),
	CYAN_GLASS(Material.STAINED_GLASS, 9, 125, "§3Cyan Stained Glass Hat", "CyanGlass"),
	
	IRON_ORE(Material.IRON_ORE, 0, -1, "§7Iron Ore Hat", null),
	IRON_BLOCK(Material.IRON_BLOCK, 0, -1, "§7Iron Block Hat", null),
	GOLD_ORE(Material.GOLD_ORE, 0, -1, "§6Gold Ore Hat", null),
	GOLD_BLOCK(Material.GOLD_BLOCK, 0, -1, "§6Gold Block Hat", null),
	DIAMOND_ORE(Material.DIAMOND_ORE, 0, -1, "§bDiamond Ore Hat", null),
	DIAMOND_BLOCK(Material.DIAMOND_BLOCK, 0, -1, "§bDiamond Block Hat", null),
	EMERALD_ORE(Material.EMERALD_ORE, 0, -1, "§aEmerald Ore Hat", null),
	EMERALD_BLOCK(Material.EMERALD_BLOCK, 0, -1, "§aEmerald Block Hat", null);
	
	Hat(Material material, int durability, int price, String name, String databaseName){
		
	}
	
	public boolean hasHat(OMPlayer omp){
		switch(this){
			case DIAMOND_BLOCK:
				return omp.hasPerms(VIPRank.Diamond_VIP);
			case DIAMOND_ORE:
				return omp.hasPerms(VIPRank.Diamond_VIP);
			case EMERALD_BLOCK:
				return omp.hasPerms(VIPRank.Emerald_VIP);
			case EMERALD_ORE:
				return omp.hasPerms(VIPRank.Emerald_VIP);
			case GOLD_BLOCK:
				return omp.hasPerms(VIPRank.Gold_VIP);
			case GOLD_ORE:
				return omp.hasPerms(VIPRank.Gold_VIP);
			case IRON_BLOCK:
				return omp.hasPerms(VIPRank.Iron_VIP);
			case IRON_ORE:
				return omp.hasPerms(VIPRank.Iron_VIP);
			default:
				return omp.hasHat(this);
		}
	}
	
	public VIPRank getVIPRank(){
		switch(this){
			case DIAMOND_BLOCK:
				return VIPRank.Diamond_VIP;
			case DIAMOND_ORE:
				return VIPRank.Diamond_VIP;
			case EMERALD_BLOCK:
				return VIPRank.Emerald_VIP;
			case EMERALD_ORE:
				return VIPRank.Emerald_VIP;
			case GOLD_BLOCK:
				return VIPRank.Gold_VIP;
			case GOLD_ORE:
				return VIPRank.Gold_VIP;
			case IRON_BLOCK:
				return VIPRank.Iron_VIP;
			case IRON_ORE:
				return VIPRank.Iron_VIP;
			default:
				return null;
		}
	}
	
	public String getPriceName(){
		switch(this){
			case DIAMOND_BLOCK:
				return "§cRequired: §b§lDiamond VIP";
			case DIAMOND_ORE:
				return "§cRequired: §b§lDiamond VIP";
			case EMERALD_BLOCK:
				return "§cRequired: §a§lEmerald VIP";
			case EMERALD_ORE:
				return "§cRequired: §a§lEmerald VIP";
			case GOLD_BLOCK:
				return "§cRequired: §6§lGold VIP";
			case GOLD_ORE:
				return "§cRequired: §6§lGold VIP";
			case IRON_BLOCK:
				return "§cRequired: §7§lIron VIP";
			case IRON_ORE:
				return "§cRequired: §7§lIron VIP";
			default:
				return "§cPrice: §b" + getPrice() + " VIP Points";
		}
	}
	
	public Material getMaterial(){
		switch(this){
			case ANDESITE:
				return Material.STONE;
			case BEDROCK:
				return Material.BEDROCK;
			case BLACK_GLASS:
				return Material.STAINED_GLASS;
			case BLUE_GLASS:
				return Material.STAINED_GLASS;
			case CACTUS:
				return Material.CACTUS;
			case CHEST:
				return Material.ENDER_CHEST;
			case COAL_BLOCK:
				return Material.COAL_BLOCK;
			case COAL_ORE:
				return Material.COAL_ORE;
			case DARK_PRISMARINE:
				return Material.PRISMARINE;
			case DIAMOND_BLOCK:
				return Material.DIAMOND_BLOCK;
			case DIAMOND_ORE:
				return Material.DIAMOND_ORE;
			case DIORITE:
				return Material.STONE;
			case EMERALD_BLOCK:
				return Material.EMERALD_BLOCK;
			case EMERALD_ORE:
				return Material.EMERALD_ORE;
			case FURNACE:
				return Material.FURNACE;
			case GLASS:
				return Material.GLASS;
			case GLOWSTONE:
				return Material.GLOWSTONE;
			case GOLD_BLOCK:
				return Material.GOLD_BLOCK;
			case GOLD_ORE:
				return Material.GOLD_ORE;
			case GRANITE:
				return Material.STONE;
			case GRASS:
				return Material.GRASS;
			case GREEN_GLASS:
				return Material.STAINED_GLASS;
			case HAY_BALE:
				return Material.HAY_BLOCK;
			case ICE:
				return Material.ICE;
			case IRON_BLOCK:
				return Material.IRON_BLOCK;
			case IRON_ORE:
				return Material.IRON_ORE;
			case LAPIS_BLOCK:
				return Material.LAPIS_BLOCK;
			case LAPIS_ORE:
				return Material.LAPIS_ORE;
			case LEAVES:
				return Material.LEAVES;
			case MAGENTA_GLASS:
				return Material.STAINED_GLASS;
			case MELON:
				return Material.MELON_BLOCK;
			case MYCELIUM:
				return Material.MYCEL;
			case ORANGE_GLASS:
				return Material.STAINED_GLASS;
			case PRISMARINE_BRICKS:
				return Material.PRISMARINE;
			case QUARTZ_BLOCK:
				return Material.QUARTZ_BLOCK;
			case QUARTZ_ORE:
				return Material.QUARTZ_ORE;
			case REDSTONE_BLOCK:
				return Material.REDSTONE_BLOCK;
			case REDSTONE_ORE:
				return Material.REDSTONE_ORE;
			case RED_GLASS:
				return Material.STAINED_GLASS;
			case SEA_LANTERN:
				return Material.SEA_LANTERN;
			case SLIME_BLOCK:
				return Material.SLIME_BLOCK;
			case SNOW:
				return Material.SNOW_BLOCK;
			case SPONGE:
				return Material.SPONGE;
			case STONE_BRICKS:
				return Material.SMOOTH_BRICK;
			case TNT:
				return Material.TNT;
			case WET_SPONGE:
				return Material.SPONGE;
			case WORKBENCH:
				return Material.WORKBENCH;
			case YELLOW_GLASS:
				return Material.STAINED_GLASS;
			case ACACIA_WOOD:
				return Material.WOOD;
			case RED_WOOL:
				return Material.WOOL;
			case BROWN_GLASS:
				return Material.STAINED_GLASS;
			case SOUL_SAND:
				return Material.SOUL_SAND;
			case CHISELLED_STONE_BRICKS:
				return Material.SMOOTH_BRICK;
			case BOOKSHELF:
				return Material.BOOKSHELF;
			case NETHERRACK:
				return Material.NETHERRACK;
			case CYAN_GLASS:
				return Material.STAINED_GLASS;
			default:
				return null;
		}
	}
	
	public short getDurability(){
		switch(this){
			case ANDESITE:
				return 6;
			case BLACK_GLASS:
				return 15;
			case BLUE_GLASS:
				return 11;
			case DARK_PRISMARINE:
				return 2;
			case DIORITE:
				return 4;
			case GRANITE:
				return 2;
			case GREEN_GLASS:
				return 5;
			case MAGENTA_GLASS:
				return 2;
			case MYCELIUM:
				return 4;
			case ORANGE_GLASS:
				return 1;
			case PRISMARINE_BRICKS:
				return 1;
			case RED_GLASS:
				return 14;
			case WET_SPONGE:
				return 1;
			case YELLOW_GLASS:
				return 4;
			case ACACIA_WOOD:
				return 4;
			case RED_WOOL:
				return 14;
			case BROWN_GLASS:
				return 12;
			case CHISELLED_STONE_BRICKS:
				return 3;
			case CYAN_GLASS:
				return 9;
			default:
				return 0;
		}
	}
	
	public int getPrice(){
		switch(this){
			case ANDESITE:
				return 100;
			case BEDROCK:
				return 125;
			case BLACK_GLASS:
				return 125;
			case BLUE_GLASS:
				return 125;
			case CACTUS:
				return 100;
			case CHEST:
				return 175;
			case COAL_BLOCK:
				return 100;
			case COAL_ORE:
				return 100;
			case DARK_PRISMARINE:
				return 150;
			case DIAMOND_BLOCK:
				return 0; //Diamond VIP
			case DIAMOND_ORE:
				return 0; //Diamond VIP
			case DIORITE:
				return 100;
			case EMERALD_BLOCK:
				return 0; //Emerald VIP
			case EMERALD_ORE:
				return 0; //Emerald VIP
			case FURNACE:
				return 100;
			case GLASS:
				return 100;
			case GLOWSTONE:
				return 200;
			case GOLD_BLOCK:
				return 0; //Gold VIP
			case GOLD_ORE:
				return 0; //Gold VIP
			case GRANITE:
				return 100;
			case GRASS:
				return 100;
			case GREEN_GLASS:
				return 125;
			case HAY_BALE:
				return 100;
			case ICE:
				return 150;
			case IRON_BLOCK:
				return 0; //Iron VIP
			case IRON_ORE:
				return 0; //Iron VIP
			case LAPIS_BLOCK:
				return 100;
			case LAPIS_ORE:
				return 100;
			case LEAVES:
				return 125;
			case MAGENTA_GLASS:
				return 125;
			case MELON:
				return 100;
			case MYCELIUM:
				return 75;
			case ORANGE_GLASS:
				return 125;
			case PRISMARINE_BRICKS:
				return 150;
			case QUARTZ_BLOCK:
				return 75;
			case QUARTZ_ORE:
				return 100;
			case REDSTONE_BLOCK:
				return 125;
			case REDSTONE_ORE:
				return 125;
			case RED_GLASS:
				return 125;
			case SEA_LANTERN:
				return 225;
			case SLIME_BLOCK:
				return 200;
			case SNOW:
				return 75;
			case SPONGE:
				return 100;
			case STONE_BRICKS:
				return 75;
			case TNT:
				return 125;
			case WET_SPONGE:
				return 125;
			case WORKBENCH:
				return 100;
			case YELLOW_GLASS:
				return 125;
			case ACACIA_WOOD:
				return 75;
			case RED_WOOL:
				return 100;
			case BROWN_GLASS:
				return 125;
			case SOUL_SAND:
				return 100;
			case CHISELLED_STONE_BRICKS:
				return 100;
			case BOOKSHELF:
				return 100;
			case NETHERRACK:
				return 75;
			case CYAN_GLASS:
				return 125;
			default:
				return 0;
		}
	}
	
	public String getName(){
		switch(this){
			case ANDESITE:
				return "§7Polished Andesite Hat";
			case BEDROCK:
				return "§8Bedrock Hat";
			case BLACK_GLASS:
				return "§8Black Stained Glass Hat";
			case BLUE_GLASS:
				return "§1Blue Stained Glass Hat";
			case CACTUS:
				return "§2Cactus Hat";
			case CHEST:
				return "§3EnderChest Hat";
			case COAL_BLOCK:
				return "§0Coal Block Hat";
			case COAL_ORE:
				return "§8Coal Ore Hat";
			case DARK_PRISMARINE:
				return "§3Dark Prismarine Hat";
			case DIAMOND_BLOCK:
				return "§bDiamond Block Hat";
			case DIAMOND_ORE:
				return "§bDiamond Ore Hat";
			case DIORITE:
				return "§fPolished Diorite Hat";
			case EMERALD_BLOCK:
				return "§aEmerald Block Hat";
			case EMERALD_ORE:
				return "§aEmerald Ore Hat";
			case FURNACE:
				return "§7Furnace Hat";
			case GLASS:
				return "§fGlass Hat";
			case GLOWSTONE:
				return "§6Glowstone Hat";
			case GOLD_BLOCK:
				return "§6Gold Block Hat";
			case GOLD_ORE:
				return "§6Gold Ore Hat";
			case GRANITE:
				return "§cPolished Granite Hat";
			case GRASS:
				return "§aGrass Hat";
			case GREEN_GLASS:
				return "§aLime Stained Glass Hat";
			case HAY_BALE:
				return "§eHay Bale Hat";
			case ICE:
				return "§bIce Hat";
			case IRON_BLOCK:
				return "§7Iron Block Hat";
			case IRON_ORE:
				return "§7Iron Ore Hat";
			case LAPIS_BLOCK:
				return "§1Lapis Block Hat";
			case LAPIS_ORE:
				return "§1Lapis Ore Hat";
			case LEAVES:
				return "§2Leaves Hat";
			case MAGENTA_GLASS:
				return "§dMagenta Stained Glass Hat";
			case MELON:
				return "§2Melon Hat";
			case MYCELIUM:
				return "§7Mycelium Hat";
			case ORANGE_GLASS:
				return "§6Orange Stained Glass Hat";
			case PRISMARINE_BRICKS:
				return "§9Prismarine Bricks Hat";
			case QUARTZ_BLOCK:
				return "§fQuartz Block Hat";
			case QUARTZ_ORE:
				return "§cQuartz Ore Hat";
			case REDSTONE_BLOCK:
				return "§4Redstone Block Hat";
			case REDSTONE_ORE:
				return "§4Redstone Ore Hat";
			case RED_GLASS:
				return "§4Red Stained Glass Hat";
			case SEA_LANTERN:
				return "§fSea Lantern Hat";
			case SLIME_BLOCK:
				return "§aSlime Block Hat";
			case SNOW:
				return "§fSnow Block Hat";
			case SPONGE:
				return "§eSponge Hat";
			case STONE_BRICKS:
				return "§7Stone Bricks Hat";
			case TNT:
				return "§cTNT Hat";
			case WET_SPONGE:
				return "§eWet Sponge Hat";
			case WORKBENCH:
				return "§6Workbench Hat";
			case YELLOW_GLASS:
				return "§eYellow Stained Glass Hat";
			case ACACIA_WOOD:
				return "§6Acacia Wood Hat";
			case RED_WOOL:
				return"§cRed Wool Hat";
			case BROWN_GLASS:
				return "§fBrown Stained Glass Hat";
			case SOUL_SAND:
				return "§eSoul Sand Hat";
			case CHISELLED_STONE_BRICKS:
				return "§7Chiselled Stone Bricks Hat";
			case BOOKSHELF:
				return "§eBookshelf Hat";
			case NETHERRACK:
				return "§cNetherrack Hat";
			case CYAN_GLASS:
				return "§3Cyan Stained Glass Hat";
			default:
				return null;
		}
	}
	
	public String getDatabaseName(){
		switch(this){
			case ANDESITE:
				return "Andesite";
			case BEDROCK:
				return "Bedrock";
			case BLACK_GLASS:
				return "BlackGlass";
			case BLUE_GLASS:
				return "BlueGlass";
			case CACTUS:
				return "Cactus";
			case CHEST:
				return "Chest";
			case COAL_BLOCK:
				return "CoalBlock";
			case COAL_ORE:
				return "CoalOre";
			case DARK_PRISMARINE:
				return "DarkPrismarine";
			case DIORITE:
				return "Diorite";
			case FURNACE:
				return "Furnace";
			case GLASS:
				return "Glass";
			case GLOWSTONE:
				return "Glowstone";
			case GRANITE:
				return "Granite";
			case GRASS:
				return "Grass";
			case GREEN_GLASS:
				return "GreenGlass";
			case HAY_BALE:
				return "HayBale";
			case ICE:
				return "Ice";
			case LAPIS_BLOCK:
				return "LapisBlock";
			case LAPIS_ORE:
				return "LapisOre";
			case LEAVES:
				return "Leaves";
			case MAGENTA_GLASS:
				return "MagentaGlass";
			case MELON:
				return "Melon";
			case MYCELIUM:
				return "Mycelium";
			case ORANGE_GLASS:
				return "OrangeGlass";
			case PRISMARINE_BRICKS:
				return "PrismarineBricks";
			case QUARTZ_BLOCK:
				return "QuartzBlock";
			case QUARTZ_ORE:
				return "QuartzOre";
			case REDSTONE_BLOCK:
				return "RedstoneBlock";
			case REDSTONE_ORE:
				return "RedstoneOre";
			case RED_GLASS:
				return "RedGlass";
			case SEA_LANTERN:
				return "SeaLantern";
			case SLIME_BLOCK:
				return "SlimeBlock";
			case SNOW:
				return "Snow";
			case SPONGE:
				return "Sponge";
			case STONE_BRICKS:
				return "StoneBricks";
			case TNT:
				return "TNT";
			case WET_SPONGE:
				return "WetSponge";
			case WORKBENCH:
				return "Workbench";
			case YELLOW_GLASS:
				return "YellowGlass";
			case ACACIA_WOOD:
				return "AcaciaWood";
			case RED_WOOL:
				return "RedWool";
			case BROWN_GLASS:
				return "BrownGlass";
			case SOUL_SAND:
				return "SoulSand";
			case CHISELLED_STONE_BRICKS:
				return "ChiselledStoneBricks";
			case BOOKSHELF:
				return "Bookshelf";
			case NETHERRACK:
				return "Netherrack";
			case CYAN_GLASS:
				return "CyanGlass";
			default:
				return null;
		}
	}
}

