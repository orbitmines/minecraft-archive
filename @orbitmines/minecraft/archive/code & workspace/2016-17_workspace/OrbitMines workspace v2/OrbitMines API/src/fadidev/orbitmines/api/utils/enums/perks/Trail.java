package fadidev.orbitmines.api.utils.enums.perks;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.Material;
import org.bukkit.Particle;

public enum Trail {

	FIREWORK_SPARK("§cFireWork Spark Trail", "Firework", 400, null, Particle.FIREWORKS_SPARK, Material.FIREWORK, 0),
	HAPPY_VILLAGER("§aHappy Villager Trail", null, -1, VIPRank.EMERALD_VIP, Particle.VILLAGER_HAPPY, Material.EMERALD, 0),
	HEART("§cHeart Trail", "Hearts", 300, null, Particle.HEART, Material.NETHER_STALK, 0),
	TNT("§4TNT Trail", "Explode", 475, null, Particle.EXPLOSION_NORMAL, Material.TNT, 0),
	MAGIC("§3Magic Trail", null, -1, VIPRank.IRON_VIP, Particle.CRIT_MAGIC, Material.INK_SACK, 6),
	ANGRY_VILLAGER("§7AngryVillager Trail", "AngryVillager", 400, null, Particle.VILLAGER_ANGRY, Material.COAL, 0),
	LAVA("§6Lava Trail", null, -1, VIPRank.DIAMOND_VIP, Particle.LAVA, Material.LAVA_BUCKET, 0),
	SLIME("§aSlime Trail", "Slime", 275, null, Particle.SLIME, Material.SLIME_BALL, 0),
	SMOKE("§0Smoke Trail", "Smoke", 325, null, Particle.SMOKE_LARGE, Material.INK_SACK, 0),
	WITCH("§5Witch Trail", "Witch", 350, null, Particle.SPELL_WITCH, Material.INK_SACK, 5),
	CRIT("§bCrit Trail", "Crit", 375, null, Particle.CRIT, Material.DIAMOND_SWORD, 0),
	WATER("§9Water Trail", "Water", 425, null, Particle.WATER_SPLASH, Material.WATER_BUCKET, 0),
	MUSIC("§dMusic Trail", "Music", 625, null, Particle.NOTE, Material.NOTE_BLOCK, 0),
	SNOW("§fSnow Trail", "Snow", 475, null, Particle.SNOW_SHOVEL, Material.SNOW_BALL, 0),
	ENCHANTMENT_TABLE("§1Enchantment Table Trail", "ETable", 400, null, Particle.ENCHANTMENT_TABLE, Material.ENCHANTMENT_TABLE, 0),
	RAINBOW("§4Rainbow Trail", null, -1, VIPRank.GOLD_VIP, Particle.REDSTONE, Material.REDSTONE, 0),
	BUBBLE("§fBubble Trail", "Bubble", 375, null, Particle.SPELL, Material.WEB, 0),
	MOB_SPAWNER("§7Mob Spawner Trail", "MobSpawner", 525, null, Particle.FLAME, Material.MOB_SPAWNER, 0);
	
	private String name;
	private String databaseName;
	private int price;
	private VIPRank viprank;
	private Particle particle;
	private Material material;
	private int durability;
	
	Trail(String name, String datbaseName, int price, VIPRank viprank, Particle particle, Material material, int durability){
		this.name = name;
		this.databaseName = datbaseName;
		this.price = price;
		this.viprank = viprank;
		this.particle = particle;
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
	
	public String getPriceName(OMPlayer omp){
		if(getVIPRank() != null)
			return "§c" + Messages.WORD_REQUIRED.get(omp) + ": " + getVIPRank().getRankString() + " VIP";
		return "§c" + Messages.WORD_PRICE.get(omp) + ": §b" + getPrice() + " VIP Points";
	}
	
	public boolean hasTrail(OMPlayer omp){
		if(getVIPRank() != null)
		    return omp.hasPerms(getVIPRank());
		return omp.hasTrail(this);
	}
	
	public VIPRank getVIPRank(){
		return viprank;
	}
	
	public Particle getParticle(){
		return particle;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public short getDurability(){
		return (short) durability;
	}
}

