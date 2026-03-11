package om.api.utils.enums.cp;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;
import net.minecraft.server.v1_8_R3.EnumParticle;

import org.bukkit.Material;

public enum Trail {

	FIREWORK_SPARK,
	HAPPY_VILLAGER,
	HEART,
	TNT,
	MAGIC,
	ANGRY_VILLAGER,
	LAVA,
	SLIME,
	SMOKE,
	WITCH,
	CRIT,
	WATER,
	MUSIC,
	SNOW,
	ENCHANTMENT_TABLE,
	RAINBOW,
	BUBBLE,
	MOB_SPAWNER;
	
	public String getName(){
		switch(this){
			case ANGRY_VILLAGER:
				return "§7AngryVillager Trail";
			case BUBBLE:
				return "§fBubble Trail";
			case CRIT:
				return "§bCrit Trail";
			case ENCHANTMENT_TABLE:
				return "§1Enchantment Table Trail";
			case FIREWORK_SPARK:
				return "§cFireWork Spark Trail";
			case HAPPY_VILLAGER:
				return "§aHappy Villager Trail";
			case HEART:
				return "§cHeart Trail";
			case LAVA:
				return "§6Lava Trail";
			case MAGIC:
				return "§3Magic Trail";
			case MOB_SPAWNER:
				return "§7Mob Spawner Trail";
			case MUSIC:
				return "§dMusic Trail";
			case RAINBOW:
				return "§4Rainbow Trail";
			case SLIME:
				return "§aSlime Trail";
			case SMOKE:
				return "§0Smoke Trail";
			case SNOW:
				return "§fSnow Trail";
			case TNT:
				return "§4TNT Trail";
			case WATER:
				return "§9Water Trail";
			case WITCH:
				return "§5Witch Trail";
			default:
				return null;
		}
	}		
	
	public String getDatabaseName(){
		switch(this){
		case ANGRY_VILLAGER:
			return "AngryVillager";
		case BUBBLE:
			return "Bubble";
		case CRIT:
			return "Crit";
		case ENCHANTMENT_TABLE:
			return "ETable";
		case FIREWORK_SPARK:
			return "Firework";
		case HEART:
			return "Hearts";
		case MOB_SPAWNER:
			return "MobSpawner";
		case MUSIC:
			return "Music";
		case SLIME:
			return "Slime";
		case SMOKE:
			return "Smoke";
		case SNOW:
			return "Snow";
		case TNT:
			return "Explode";
		case WATER:
			return "Water";
		case WITCH:
			return "Witch";
		default:
			return null;
	}
}
	
	public int getPrice(){
		switch(this){
			case ANGRY_VILLAGER:
				return 400;
			case BUBBLE:
				return 375;
			case CRIT:
				return 375;
			case ENCHANTMENT_TABLE:
				return 400;
			case FIREWORK_SPARK:
				return 400;
			case HAPPY_VILLAGER:
				return 0;
			case HEART:
				return 300;
			case LAVA:
				return 0;
			case MAGIC:
				return 0;
			case MOB_SPAWNER:
				return 525;
			case MUSIC:
				return 625;
			case RAINBOW:
				return 0;
			case SLIME:
				return 275;
			case SMOKE:
				return 325;
			case SNOW:
				return 475;
			case TNT:
				return 475;
			case WATER:
				return 425;
			case WITCH:
				return 350;
			default:
				return 0;
		}
	}
	
	public String getPriceName(){
		switch(this){
			case HAPPY_VILLAGER:
				return "§cRequired: §a§lEmerald VIP";
			case LAVA:
				return "§cRequired: §b§lDiamond VIP";
			case MAGIC:
				return "§cRequired: §7§lIron VIP";
			case RAINBOW:
				return "§cRequired: §6§lGold VIP";
			default:
				return "§cPrice: §b" + getPrice() + " VIP Points";
		}
	}
	
	public boolean hasTrail(OMPlayer omp){
		switch(this){
			case HAPPY_VILLAGER:
				return omp.hasPerms(VIPRank.Emerald_VIP);
			case LAVA:
				return omp.hasPerms(VIPRank.Diamond_VIP);
			case MAGIC:
				return omp.hasPerms(VIPRank.Iron_VIP);
			case RAINBOW:
				return omp.hasPerms(VIPRank.Gold_VIP);
			default:
				return omp.hasTrail(this);
		}
	}
	
	public VIPRank getVIPRank(){
		switch(this){
			case HAPPY_VILLAGER:
				return VIPRank.Emerald_VIP;
			case LAVA:
				return VIPRank.Diamond_VIP;
			case MAGIC:
				return VIPRank.Iron_VIP;
			case RAINBOW:
				return VIPRank.Gold_VIP;
			default:
				return null;
		}
	}
	
	public EnumParticle getEnumParticle(){
		switch(this){
			case ANGRY_VILLAGER:
				return EnumParticle.VILLAGER_ANGRY;
			case BUBBLE:
				return EnumParticle.SPELL;
			case CRIT:
				return EnumParticle.CRIT;
			case ENCHANTMENT_TABLE:
				return EnumParticle.ENCHANTMENT_TABLE;
			case FIREWORK_SPARK:
				return EnumParticle.FIREWORKS_SPARK;
			case HAPPY_VILLAGER:
				return EnumParticle.VILLAGER_HAPPY;
			case HEART:
				return EnumParticle.HEART;
			case LAVA:
				return EnumParticle.LAVA;
			case MAGIC:
				return EnumParticle.CRIT_MAGIC;
			case MOB_SPAWNER:
				return EnumParticle.FLAME;
			case MUSIC:
				return EnumParticle.NOTE;
			case RAINBOW:
				return EnumParticle.REDSTONE;
			case SLIME:
				return EnumParticle.SLIME;
			case SMOKE:
				return EnumParticle.SMOKE_LARGE;
			case SNOW:
				return EnumParticle.SNOW_SHOVEL;
			case TNT:
				return EnumParticle.EXPLOSION_NORMAL;
			case WATER:
				return EnumParticle.WATER_SPLASH;
			case WITCH:
				return EnumParticle.SPELL_WITCH;
			default:
				return null;
		}
	}
	
	public Material getMaterial(){
		switch(this){
			case ANGRY_VILLAGER:
				return Material.COAL;
			case BUBBLE:
				return Material.WEB;
			case CRIT:
				return Material.DIAMOND_SWORD;
			case ENCHANTMENT_TABLE:
				return Material.ENCHANTMENT_TABLE;
			case FIREWORK_SPARK:
				return Material.FIREWORK;
			case HAPPY_VILLAGER:
				return Material.EMERALD;
			case HEART:
				return Material.NETHER_STALK;
			case LAVA:
				return Material.LAVA_BUCKET;
			case MAGIC:
				return Material.INK_SACK;
			case MOB_SPAWNER:
				return Material.MOB_SPAWNER;
			case MUSIC:
				return Material.NOTE_BLOCK;
			case RAINBOW:
				return Material.REDSTONE;
			case SLIME:
				return Material.SLIME_BALL;
			case SMOKE:
				return Material.INK_SACK;
			case SNOW:
				return Material.SNOW_BALL;
			case TNT:
				return Material.TNT;
			case WATER:
				return Material.WATER_BUCKET;
			case WITCH:
				return Material.INK_SACK;
			default:
				return null;
		}
	}
	
	public short getDurability(){
		switch(this){
			case MAGIC:
				return 6;
			case WITCH:
				return 5;
			default:
				return 0;
		}
	}
}

