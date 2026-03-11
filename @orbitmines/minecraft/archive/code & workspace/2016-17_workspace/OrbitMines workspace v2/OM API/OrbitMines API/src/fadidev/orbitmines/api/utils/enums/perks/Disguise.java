package fadidev.orbitmines.api.utils.enums.perks;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum Disguise {

	ENDERMAN("§8Enderman Disguise", "Enderman", 500, null, Mob.ENDERMAN, EntityType.ENDERMAN),
	HORSE("§6Horse Disguise", "Horse", 475, null, Mob.HORSE, EntityType.HORSE),
	IRON_GOLEM("§fIron Golem Disguise", "IronGolem", 575, null, Mob.IRON_GOLEM, EntityType.IRON_GOLEM),
	GHAST("§7Ghast Disguise", "Ghast", 500, null, Mob.GHAST, EntityType.GHAST),
	SNOWMAN("§fSnowman Disguise", "Snowman", -1, null, Mob.SNOWMAN, EntityType.SNOWMAN),
	RABBIT("§6Rabbit Disguise", "Rabbit", 650, null, Mob.RABBIT, EntityType.RABBIT),
	WITCH("§2Witch Disguise", "Witch", 450, null, Mob.WITCH, EntityType.WITCH),
	BAT("§8Bat Disguise", "Bat", 400, null, Mob.BAT, EntityType.BAT),
	CHICKEN("§fChicken Disguise", "Chicken", 325, null, Mob.CHICKEN, EntityType.CHICKEN),
	OCELOT("§eOcelot Disguise", "Ocelot", 375, null, Mob.OCELOT, EntityType.OCELOT),
	MUSHROOM_COW("§4Mushroom Cow Disguise", "MushroomCow", 350, null, Mob.MUSHROOM_COW, EntityType.MUSHROOM_COW),
	SQUID("§9Squid Disguise", "Squid", 500, null, Mob.SQUID, EntityType.SQUID),
	SLIME("§aSlime Disguise", "Slime", 425, null, Mob.SLIME, EntityType.SLIME),
	ZOMBIE_PIGMAN("§dZombie Pigman Disguise", "ZombiePigman", 400, null, Mob.ZOMBIE, EntityType.ZOMBIE),
	MAGMA_CUBE("§cMagma Cube Disguise", "MagmaCube", 475, null, Mob.MAGMA_CUBE, EntityType.MAGMA_CUBE),
	SKELETON("§7Skeleton Disguise", "Skeleton", 500, null, Mob.SKELETON, EntityType.SKELETON),
	COW("§8Cow Disguise", "Cow", 350, null, Mob.COW, EntityType.COW),
	WOLF("§7Wolf Disguise", "Wolf", 400, null, Mob.WOLF, EntityType.WOLF),
	SPIDER("§8Spider Disguise", "Spider", 375, null, Mob.SPIDER, EntityType.SPIDER),
	SILVERFISH("§7Silverfish Disguise", "Silverfish", 475, null, Mob.SILVERFISH, EntityType.SILVERFISH),
	SHEEP("§fSheep Disguise", "Sheep", 375, null, Mob.SHEEP, EntityType.SHEEP),
	CAVE_SPIDER("§3Cave Spider Disguise", "CaveSpider", 400, null, Mob.CAVE_SPIDER, EntityType.CAVE_SPIDER),
	CREEPER("§aCreeper Disguise", "Creeper", 475, null, Mob.CREEPER, EntityType.CREEPER),
	PIG("§dPig Disguise", "Pig", -1, VIPRank.IRON_VIP, Mob.PIG, EntityType.PIG),
	BLAZE("§6Blaze Disguise", "Blaze", -1, VIPRank.EMERALD_VIP, Mob.BLAZE, EntityType.BLAZE),
	ZOMBIE("§2Zombie Disguise", "Zombie", -1, VIPRank.GOLD_VIP, Mob.ZOMBIE, EntityType.ZOMBIE),
	VILLAGER("§6Villager Disguise", "Villager", -1, VIPRank.DIAMOND_VIP, Mob.VILLAGER, EntityType.VILLAGER),
	ARMOR_STAND("§6ArmorStand Disguise", "ArmorStand", -1, null, null, EntityType.ARMOR_STAND);

	private String name;
	private String databaseName;
	private int price;
	private VIPRank viprank;
	private Mob mob;
	private EntityType entityType;
	
	Disguise(String name, String databaseName, int price, VIPRank viprank, Mob mob, EntityType entityType){
		this.name = name;
		this.databaseName = databaseName;
		this.price = price;
		this.viprank = viprank;
		this.mob = mob;
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
	
	public String getPriceName(OMPlayer omp){
		if(this == SNOWMAN)
			return Messages.INV_ACHIEVED_AT_CHRISTMAS.get(omp);
		else if(this == ARMOR_STAND)
			return Messages.INV_ACHIEVED_AT_OM_BIRTHDAY.get(omp);
		else if(getVIPRank() != null)
			return "§c" + Messages.WORD_REQUIRED.get(omp) + ": " + getVIPRank().getRankString() + " VIP";
		else
			return "§c" + Messages.WORD_PRICE.get(omp) + ": §b" + getPrice() + " VIP Points";
	}
	
	public Material getMaterial(){
		if(this == ARMOR_STAND)
			return Material.ARMOR_STAND;
		return Material.MONSTER_EGG;
	}

    public Mob getMob() {
        return mob;
    }

    public EntityType getEntityType(){
		return entityType;
	}
}
