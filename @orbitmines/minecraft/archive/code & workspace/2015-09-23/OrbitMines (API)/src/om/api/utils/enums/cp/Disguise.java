package om.api.utils.enums.cp;

import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum Disguise {

	ENDERMAN("§8Enderman Disguise", "Enderman", 500, null, 58, EntityType.ENDERMAN),
	HORSE("§6Horse Disguise", "Horse", 475, null, 100, EntityType.HORSE),
	IRON_GOLEM("§fIron Golem Disguise", "IronGolem", 575, null, 99, EntityType.IRON_GOLEM),
	GHAST("§7Ghast Disguise", "Ghast", 500, null, 56, EntityType.GHAST),
	SNOWMAN("§fSnowman Disguise", "Snowman", -1, null, 97, EntityType.SNOWMAN),
	RABBIT("§6Rabbit Disguise", "Rabbit", 650, null, 101, EntityType.RABBIT),
	WITCH("§2Witch Disguise", "Witch", 450, null, 66, EntityType.WITCH),
	BAT("§8Bat Disguise", "Bat", 400, null, 65, EntityType.BAT),
	CHICKEN("§fChicken Disguise", "Chicken", 325, null, 93, EntityType.CHICKEN),
	OCELOT("§eOcelot Disguise", "Ocelot", 375, null, 98, EntityType.OCELOT),
	MUSHROOM_COW("§4Mushroom Cow Disguise", "MushroomCow", 350, null, 96, EntityType.MUSHROOM_COW),
	SQUID("§9Squid Disguise", "Squid", 500, null, 94, EntityType.SQUID),
	SLIME("§aSlime Disguise", "Slime", 425, null, 55, EntityType.SLIME),
	ZOMBIE_PIGMAN("§dZombie Pigman Disguise", "ZombiePigman", 400, null, 57, EntityType.ZOMBIE),
	MAGMA_CUBE("§cMagma Cube Disguise", "MagmaCube", 475, null, 62, EntityType.MAGMA_CUBE),
	SKELETON("§7Skeleton Disguise", "Skeleton", 500, null, 51, EntityType.SKELETON),
	COW("§8Cow Disguise", "Cow", 350, null, 92, EntityType.COW),
	WOLF("§7Wolf Disguise", "Wolf", 400, null, 95, EntityType.WOLF),
	SPIDER("§8Spider Disguise", "Spider", 375, null, 52, EntityType.SPIDER),
	SILVERFISH("§7Silverfish Disguise", "Silverfish", 475, null, 60, EntityType.SILVERFISH),
	SHEEP("§fSheep Disguise", "Sheep", 375, null, 91, EntityType.SHEEP),
	CAVE_SPIDER("§3Cave Spider Disguise", "CaveSpider", 400, null, 59, EntityType.CAVE_SPIDER),
	CREEPER("§aCreeper Disguise", "Creeper", 475, null, 50, EntityType.CREEPER),
	PIG("§dPig Disguise", "Pig", -1, VIPRank.Iron_VIP, 90, EntityType.PIG),
	BLAZE("§6Blaze Disguise", "Blaze", -1, VIPRank.Emerald_VIP, 61, EntityType.BLAZE),
	ZOMBIE("§2Zombie Disguise", "Zombie", -1, VIPRank.Gold_VIP, 54, EntityType.ZOMBIE),
	VILLAGER("§6Villager Disguise", "Villager", -1, VIPRank.Diamond_VIP, 120, EntityType.VILLAGER);
	
	private String name;
	private String databaseName;
	private int price;
	private VIPRank viprank;
	private int durability;
	private EntityType entityType;
	
	Disguise(String name, String databaseName, int price, VIPRank viprank, int durability, EntityType entityType){
		this.name = name;
		this.databaseName = databaseName;
		this.price = price;
		this.viprank = viprank;
		this.durability = durability;
		this.entityType = entityType;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDatabaseName(){
		return databaseName;
	}

	public int getPrice(){
		return price;
	}
	
	public boolean hasDisguise(OMPlayer omp){
		if(getVIPRank() != null) return omp.hasPerms(getVIPRank());
		return omp.hasDisguise(this);
	}
	
	public VIPRank getVIPRank(){
		return viprank;	
	}
	
	public String getPriceName(){
		if(this == SNOWMAN){
			return "§aAchieved at Christmas 2014";
		}
		else if(getVIPRank() != null){
			return "§cRequired: " + getVIPRank().getRankString();
		}
		else{
			return "§cPrice: §b" + getPrice() + " VIP Points";
		}
	}
	
	public Material getMaterial(){
		return Material.MONSTER_EGG;
	}
	
	public short getDurability(){
		return (short) durability;
	}
	
	public EntityType getEntityType(){
		return entityType;
	}
}
