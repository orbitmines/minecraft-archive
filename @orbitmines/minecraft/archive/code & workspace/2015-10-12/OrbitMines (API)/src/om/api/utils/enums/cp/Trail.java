package om.api.utils.enums.cp;

import net.minecraft.server.v1_8_R3.EnumParticle;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Material;

public enum Trail {

	FIREWORK_SPARK("§cFireWork Spark Trail", "Firework", 400, null, EnumParticle.FIREWORKS_SPARK, Material.FIREWORK, 0),
	HAPPY_VILLAGER("§aHappy Villager Trail", null, -1, VIPRank.Emerald_VIP, EnumParticle.VILLAGER_HAPPY, Material.EMERALD, 0),
	HEART("§cHeart Trail", "Hearts", 300, null, EnumParticle.HEART, Material.NETHER_STALK, 0),
	TNT("§4TNT Trail", "Explode", 475, null, EnumParticle.EXPLOSION_NORMAL, Material.TNT, 0),
	MAGIC("§3Magic Trail", null, -1, VIPRank.Iron_VIP, EnumParticle.CRIT_MAGIC, Material.INK_SACK, 6),
	ANGRY_VILLAGER("§7AngryVillager Trail", "AngryVillager", 400, null, EnumParticle.VILLAGER_ANGRY, Material.COAL, 0),
	LAVA("§6Lava Trail", null, -1, VIPRank.Diamond_VIP, EnumParticle.LAVA, Material.LAVA_BUCKET, 0),
	SLIME("§aSlime Trail", "Slime", 275, null, EnumParticle.SLIME, Material.SLIME_BALL, 0),
	SMOKE("§0Smoke Trail", "Smoke", 325, null, EnumParticle.SMOKE_LARGE, Material.INK_SACK, 0),
	WITCH("§5Witch Trail", "Witch", 350, null, EnumParticle.SPELL_WITCH, Material.INK_SACK, 5),
	CRIT("§bCrit Trail", "Crit", 375, null, EnumParticle.CRIT, Material.DIAMOND_SWORD, 0),
	WATER("§9Water Trail", "Water", 425, null, EnumParticle.WATER_SPLASH, Material.WATER_BUCKET, 0),
	MUSIC("§dMusic Trail", "Music", 625, null, EnumParticle.NOTE, Material.NOTE_BLOCK, 0),
	SNOW("§fSnow Trail", "Snow", 475, null, EnumParticle.SNOW_SHOVEL, Material.SNOW_BALL, 0),
	ENCHANTMENT_TABLE("§1Enchantment Table Trail", "ETable", 400, null, EnumParticle.ENCHANTMENT_TABLE, Material.ENCHANTMENT_TABLE, 0),
	RAINBOW("§4Rainbow Trail", null, -1, VIPRank.Gold_VIP, EnumParticle.REDSTONE, Material.REDSTONE, 0),
	BUBBLE("§fBubble Trail", "Bubble", 375, null, EnumParticle.SPELL, Material.WEB, 0),
	MOB_SPAWNER("§7Mob Spawner Trail", "MobSpawner", 525, null, EnumParticle.FLAME, Material.MOB_SPAWNER, 0);
	
	private String name;
	private String databaseName;
	private int price;
	private VIPRank viprank;
	private EnumParticle enumParticle;
	private Material material;
	private int durability;
	
	Trail(String name, String datbaseName, int price, VIPRank viprank, EnumParticle enumParticle, Material material, int durability){
		this.name = name;
		this.databaseName = datbaseName;
		this.price = price;
		this.viprank = viprank;
		this.enumParticle = enumParticle;
		this.material = material;
		this.durability = durability;
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
	
	public String getPriceName(){
		if(getVIPRank() != null) return "§cRequired: " + getVIPRank().getRankString();
		return "§cPrice: §b" + getPrice() + " VIP Points";
	}
	
	public boolean hasTrail(OMPlayer omp){
		if(getVIPRank() != null) return omp.hasPerms(VIPRank.Gold_VIP);
		return omp.hasTrail(this);
	}
	
	public VIPRank getVIPRank(){
		return viprank;
	}
	
	public EnumParticle getEnumParticle(){
		return enumParticle;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public short getDurability(){
		return (short) durability;
	}
}

