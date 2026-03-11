package om.api.utils.enums.cp;

import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Material;

public enum Hat {

	STONE_BRICKS(Material.SMOOTH_BRICK, 0, 75, "§7Stone Bricks Hat", "StoneBricks", null),
	GREEN_GLASS(Material.STAINED_GLASS, 5, 125, "§aLime Stained Glass Hat", "GreenGlass", null),
	CACTUS(Material.CACTUS, 0, 100, "§2Cactus Hat", "Cactus", null),
	SNOW(Material.SNOW, 0, 75, "§fSnow Block Hat", "Snow", null),
	TNT(Material.TNT, 0, 125, "§cTNT Hat", "TNT", null),
	COAL_ORE(Material.COAL_ORE, 0, 100, "§8Coal Ore Hat", "CoalOre", null),
	BLACK_GLASS(Material.STAINED_GLASS, 15, 125, "§8Black Stained Glass Hat", "BlackGlass", null),
	FURNACE(Material.FURNACE, 0, 100, "§7Furnace Hat", "Furnace", null),
	QUARTZ_ORE(Material.QUARTZ_ORE, 0, 100, "§cQuartz Ore Hat", "QuartzOre", null),
	HAY_BALE(Material.HAY_BLOCK, 0, 100, "§eHay Bale Hat", "HayBale", null),
	REDSTONE_ORE(Material.REDSTONE_ORE, 0, 125, "§4Redstone Ore Hat", "RedstoneOre", null),
	ICE(Material.ICE, 0, 150, "§bIce Hat", "Ice", null),
	WORKBENCH(Material.WORKBENCH, 0, 100, "§6Workbench Hat", "Workbench", null),
	GRASS(Material.GRASS, 0, 100, "§aGrass Hat", "Grass", null),
	RED_GLASS(Material.STAINED_GLASS, 14, 125, "§4Red Stained Glass Hat", "RedGlass", null),
	BEDROCK(Material.BEDROCK, 0, 125, "§8Bedrock Hat", "Bedrock", null),
	LAPIS_ORE(Material.LAPIS_ORE, 0, 100, "§1Lapis Ore Hat", "LapisOre", null),
	REDSTONE_BLOCK(Material.REDSTONE_BLOCK, 0, 125, "§4Redstone Block Hat", "RedstoneBlock", null),
	QUARTZ_BLOCK(Material.QUARTZ_BLOCK, 0, 75, "§fQuartz Block Hat", "QuartzBlock", null),
	LAPIS_BLOCK(Material.LAPIS_BLOCK, 0, 100, "§1Lapis Block Hat", "LapisBlock", null),
	MAGENTA_GLASS(Material.STAINED_GLASS, 2, 125, "§dMagenta Stained Glass Hat", "MagentaGlass", null),
	COAL_BLOCK(Material.COAL_BLOCK, 0, 100, "§0Coal Block Hat", "CoalBlock", null),
	MELON(Material.MELON_BLOCK, 0, 100, "§2Melon Hat", "Melon", null),
	GLASS(Material.GLASS, 0, 100, "§fGlass Hat", "Glass", null),
	YELLOW_GLASS(Material.STAINED_GLASS, 4, 125, "§eYellow Stained Glass Hat", "YellowGlass", null),
	MYCELIUM(Material.MYCEL, 4, 75, "§7Mycelium Hat", "Mycelium", null),
	LEAVES(Material.LEAVES, 0, 125, "§2Leaves Hat", "Leaves", null),
	ORANGE_GLASS(Material.STAINED_GLASS, 1, 125, "§6Orange Stained Glass Hat", "OrangeGlass", null),
	
	DIORITE(Material.STONE, 4, 100, "§fPolished Diorite Hat", "Diorite", null),
	DARK_PRISMARINE(Material.PRISMARINE, 2, 150, "§3Dark Prismarine Hat", "DarkPrismarine", null),
	SLIME_BLOCK(Material.SLIME_BLOCK, 0, 200, "§aSlime Block Hat", "SlimeBlock", null),
	GRANITE(Material.STONE, 2, 100, "§cPolished Granite Hat", "Granite", null),
	SEA_LANTERN(Material.SEA_LANTERN, 0, 225, "§fSea Lantern Hat", "SeaLantern", null),
	PRISMARINE_BRICKS(Material.PRISMARINE, 1, 150, "§9Prismarine Bricks Hat", "PrismarineBricks", null),
	SPONGE(Material.SPONGE, 0, 100, "§eSponge Hat", "Sponge", null),
	CHEST(Material.ENDER_CHEST, 0, 175, "§3EnderChest Hat", "Chest", null),
	GLOWSTONE(Material.GLOWSTONE, 0, 200, "§6Glowstone Hat", "Glowstone", null),
	WET_SPONGE(Material.SPONGE, 1, 125, "§eWet Sponge Hat", "WetSponge", null),
	ANDESITE(Material.STONE, 6, 100, "§7Polished Andesite Hat", "Andesite", null),
	BLUE_GLASS(Material.STAINED_GLASS, 11, 125, "§1Blue Stained Glass Hat", "BlueGlass", null),
	
	ACACIA_WOOD(Material.WOOD, 4, 75, "§6Acacia Wood Hat", "AcaciaWood", null),
	RED_WOOL(Material.WOOL, 14, 100, "§cRed Wool Hat", "RedWool", null),
	BROWN_GLASS(Material.STAINED_GLASS, 12, 125, "§fBrown Stained Glass Hat", "BrownGlass", null),
	SOUL_SAND(Material.SOUL_SAND, 0, 100, "§eSoul Sand Hat", "SoulSand", null),
	CHISELLED_STONE_BRICKS(Material.SMOOTH_BRICK, 3, 100, "§7Chiselled Stone Bricks Hat", "ChiselledStoneBricks", null),
	BOOKSHELF(Material.BOOKSHELF, 0, 100, "§eBookshelf Hat", "Bookshelf", null),
	NETHERRACK(Material.NETHERRACK, 0, 75, "§cNetherrack Hat", "Netherrack", null),
	CYAN_GLASS(Material.STAINED_GLASS, 9, 125, "§3Cyan Stained Glass Hat", "CyanGlass", null),
	
	IRON_ORE(Material.IRON_ORE, 0, -1, "§7Iron Ore Hat", null, VIPRank.Iron_VIP),
	IRON_BLOCK(Material.IRON_BLOCK, 0, -1, "§7Iron Block Hat", null, VIPRank.Iron_VIP),
	GOLD_ORE(Material.GOLD_ORE, 0, -1, "§6Gold Ore Hat", null, VIPRank.Gold_VIP),
	GOLD_BLOCK(Material.GOLD_BLOCK, 0, -1, "§6Gold Block Hat", null, VIPRank.Gold_VIP),
	DIAMOND_ORE(Material.DIAMOND_ORE, 0, -1, "§bDiamond Ore Hat", null, VIPRank.Diamond_VIP),
	DIAMOND_BLOCK(Material.DIAMOND_BLOCK, 0, -1, "§bDiamond Block Hat", null, VIPRank.Diamond_VIP),
	EMERALD_ORE(Material.EMERALD_ORE, 0, -1, "§aEmerald Ore Hat", null, VIPRank.Emerald_VIP),
	EMERALD_BLOCK(Material.EMERALD_BLOCK, 0, -1, "§aEmerald Block Hat", null, VIPRank.Emerald_VIP);
	
	private Material material;
	private int durability;
	private int price;
	private String name;
	private String databaseName;
	private VIPRank viprank;
	
	Hat(Material material, int durability, int price, String name, String databaseName, VIPRank viprank){
		this.material = material;
		this.durability = durability;
		this.price = price;
		this.name = name;
		this.databaseName = databaseName;
		this.viprank = viprank;
	}
	
	public boolean hasHat(OMPlayer omp){
		if(getVIPRank() != null) return omp.hasPerms(getVIPRank());
		return omp.hasHat(this);
	}
	
	public VIPRank getVIPRank(){
		return viprank;
	}
	
	public String getPriceName(){
		if(getVIPRank() != null) return "§cRequired: " + getVIPRank().getRankString();
		return "§cPrice: §b" + getPrice() + " VIP Points";
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public short getDurability(){
		return (short) durability;
	}
	
	public int getPrice(){
		return price;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDatabaseName(){
		return databaseName;
	}
}

