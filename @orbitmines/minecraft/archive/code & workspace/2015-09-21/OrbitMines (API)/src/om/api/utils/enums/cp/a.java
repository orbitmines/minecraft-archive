package om.api.utils.enums.cp;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;

import org.bukkit.Material;

public enum Hat {

	STONE_BRICKS,
	GREEN_GLASS,
	CACTUS,
	SNOW,
	TNT,
	COAL_ORE,
	BLACK_GLASS,
	FURNACE,
	QUARTZ_ORE,
	HAY_BALE,
	REDSTONE_ORE,
	ICE,
	WORKBENCH,
	GRASS,
	RED_GLASS,
	BEDROCK,
	LAPIS_ORE,
	REDSTONE_BLOCK,
	QUARTZ_BLOCK,
	LAPIS_BLOCK,
	MAGENTA_GLASS,
	COAL_BLOCK,
	MELON,
	GLASS,
	YELLOW_GLASS,
	MYCELIUM,
	LEAVES,
	ORANGE_GLASS,
	
	DIORITE,
	DARK_PRISMARINE,
	SLIME_BLOCK,
	GRANITE,
	SEA_LANTERN,
	PRISMARINE_BRICKS,
	SPONGE,
	CHEST,
	GLOWSTONE,
	WET_SPONGE,
	ANDESITE,
	BLUE_GLASS,
	
	ACACIA_WOOD,
	RED_WOOL,
	BROWN_GLASS,
	SOUL_SAND,
	CHISELLED_STONE_BRICKS,
	BOOKSHELF,
	NETHERRACK,
	CYAN_GLASS,
	
	IRON_ORE,
	IRON_BLOCK,
	GOLD_ORE,
	GOLD_BLOCK,
	DIAMOND_ORE,
	DIAMOND_BLOCK,
	EMERALD_ORE,
	EMERALD_BLOCK;
	
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

