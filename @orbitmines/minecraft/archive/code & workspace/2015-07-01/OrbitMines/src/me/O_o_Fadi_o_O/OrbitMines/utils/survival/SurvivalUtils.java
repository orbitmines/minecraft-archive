package me.O_o_Fadi_o_O.OrbitMines.utils.survival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SurvivalUtils {

	public Material getMaterial(Biome b){
		switch(b){
			case BEACH:
				return Material.SAND;
			case BIRCH_FOREST:
				return Material.SAPLING;
			case BIRCH_FOREST_HILLS:
				return Material.SAPLING;
			case BIRCH_FOREST_HILLS_MOUNTAINS:
				return Material.SAPLING;
			case BIRCH_FOREST_MOUNTAINS:
				return Material.SAPLING;
			case COLD_BEACH:
				return Material.SAND;
			case COLD_TAIGA:
				return Material.SAPLING;
			case COLD_TAIGA_HILLS:
				return Material.SAPLING;
			case COLD_TAIGA_MOUNTAINS:
				return Material.SAPLING;
			case DEEP_OCEAN:
				return Material.WATER_BUCKET;
			case DESERT:
				return Material.SAND;
			case DESERT_HILLS:
				return Material.SAND;
			case DESERT_MOUNTAINS:
				return Material.SAND;
			case EXTREME_HILLS:
				return Material.STONE;
			case EXTREME_HILLS_MOUNTAINS:
				return Material.STONE;
			case EXTREME_HILLS_PLUS:
				return Material.STONE;
			case EXTREME_HILLS_PLUS_MOUNTAINS:
				return Material.STONE;
			case FLOWER_FOREST:
				return Material.RED_ROSE;
			case FOREST:
				return Material.SAPLING;
			case FOREST_HILLS:
				return Material.SAPLING;
			case FROZEN_OCEAN:
				return Material.ICE;
			case FROZEN_RIVER:
				return Material.ICE;
			case HELL:
				return Material.NETHERRACK;
			case ICE_MOUNTAINS:
				return Material.ICE;
			case ICE_PLAINS:
				return Material.ICE;
			case ICE_PLAINS_SPIKES:
				return Material.ICE;
			case JUNGLE:
				return Material.SAPLING;
			case JUNGLE_EDGE:
				return Material.SAPLING;
			case JUNGLE_EDGE_MOUNTAINS:
				return Material.SAPLING;
			case JUNGLE_HILLS:
				return Material.SAPLING;
			case JUNGLE_MOUNTAINS:
				return Material.SAPLING;
			case MEGA_SPRUCE_TAIGA:
				return Material.SAPLING;
			case MEGA_SPRUCE_TAIGA_HILLS:
				return Material.SAPLING;
			case MEGA_TAIGA:
				return Material.SAPLING;
			case MEGA_TAIGA_HILLS:
				return Material.SAPLING;
			case MESA:
				return Material.STAINED_CLAY;
			case MESA_BRYCE:
				return Material.STAINED_CLAY;
			case MESA_PLATEAU:
				return Material.STAINED_CLAY;
			case MESA_PLATEAU_FOREST:
				return Material.STAINED_CLAY;
			case MESA_PLATEAU_FOREST_MOUNTAINS:
				return Material.STAINED_CLAY;
			case MESA_PLATEAU_MOUNTAINS:
				return Material.STAINED_CLAY;
			case MUSHROOM_ISLAND:
				return Material.HUGE_MUSHROOM_2;
			case MUSHROOM_SHORE:
				return Material.HUGE_MUSHROOM_2;
			case OCEAN:
				return Material.WATER_BUCKET;
			case PLAINS:
				return Material.GRASS;
			case RIVER:
				return Material.WATER_BUCKET;
			case ROOFED_FOREST:
				return Material.SAPLING;
			case ROOFED_FOREST_MOUNTAINS:
				return Material.SAPLING;
			case SAVANNA:
				return Material.SAPLING;
			case SAVANNA_MOUNTAINS:
				return Material.SAPLING;
			case SAVANNA_PLATEAU:
				return Material.SAPLING;
			case SAVANNA_PLATEAU_MOUNTAINS:
				return Material.SAPLING;
			case SKY:
				return Material.SNOW_BLOCK;
			case SMALL_MOUNTAINS:
				return Material.STONE;
			case STONE_BEACH:
				return Material.STONE;
			case SUNFLOWER_PLAINS:
				return Material.DOUBLE_PLANT;
			case SWAMPLAND:
				return Material.VINE;
			case SWAMPLAND_MOUNTAINS:
				return Material.VINE;
			case TAIGA:
				return Material.SAPLING;
			case TAIGA_HILLS:
				return Material.SAPLING;
			case TAIGA_MOUNTAINS:
				return Material.SAPLING;
			default:
				return null;
		}
	}
	
	public short getDurability(Biome b){
		switch(b){
			case BIRCH_FOREST:
				return 2;
			case BIRCH_FOREST_HILLS:
				return 2;
			case BIRCH_FOREST_HILLS_MOUNTAINS:
				return 2;
			case BIRCH_FOREST_MOUNTAINS:
				return 2;
			case COLD_TAIGA:
				return 1;
			case COLD_TAIGA_HILLS:
				return 1;
			case COLD_TAIGA_MOUNTAINS:
				return 1;
			case JUNGLE:
				return 3;
			case JUNGLE_EDGE:
				return 3;
			case JUNGLE_EDGE_MOUNTAINS:
				return 3;
			case JUNGLE_HILLS:
				return 3;
			case JUNGLE_MOUNTAINS:
				return 3;
			case MEGA_SPRUCE_TAIGA:
				return 1;
			case MEGA_SPRUCE_TAIGA_HILLS:
				return 1;
			case MEGA_TAIGA:
				return 1;
			case MEGA_TAIGA_HILLS:
				return 1;
			case MESA:
				return 4;
			case MESA_BRYCE:
				return 4;
			case MESA_PLATEAU:
				return 4;
			case MESA_PLATEAU_FOREST:
				return 4;
			case MESA_PLATEAU_FOREST_MOUNTAINS:
				return 4;
			case MESA_PLATEAU_MOUNTAINS:
				return 4;
			case ROOFED_FOREST:
				return 5;
			case ROOFED_FOREST_MOUNTAINS:
				return 5;
			case SAVANNA:
				return 4;
			case SAVANNA_MOUNTAINS:
				return 4;
			case SAVANNA_PLATEAU:
				return 4;
			case SAVANNA_PLATEAU_MOUNTAINS:
				return 4;
			case TAIGA:
				return 1;
			case TAIGA_HILLS:
				return 1;
			case TAIGA_MOUNTAINS:
				return 1;
			default:
				return 0;
		}
	}
	
	public String getBiomeName(Biome b){
		switch(b){
			case BEACH:
				return "§eBeach"; 
			case BIRCH_FOREST:
				return "§fBirch Forest"; 
			case BIRCH_FOREST_HILLS:
				return "§fBirch Forest Hills"; 
			case BIRCH_FOREST_HILLS_MOUNTAINS:
				return "§fBirch Forest Mountain Hills"; 
			case BIRCH_FOREST_MOUNTAINS:
				return "§fBirch Forest Mountains"; 
			case COLD_BEACH:
				return "§bCold Beach"; 
			case COLD_TAIGA:
				return "§2Cold Taiga"; 
			case COLD_TAIGA_HILLS:
				return "§2Cold Taiga Hills"; 
			case COLD_TAIGA_MOUNTAINS:
				return "§2Cold Taiga Mountains"; 
			case DEEP_OCEAN:
				return "§9Deep Ocean"; 
			case DESERT:
				return "§eDesert"; 
			case DESERT_HILLS:
				return "§eDesert Hills"; 
			case DESERT_MOUNTAINS:
				return "§eDesert Mountains"; 
			case EXTREME_HILLS:
				return "§7Extreme Hills"; 
			case EXTREME_HILLS_MOUNTAINS:
				return "§7Extreme Mountain Hills"; 
			case EXTREME_HILLS_PLUS:
				return "§7Extreme Hills"; 
			case EXTREME_HILLS_PLUS_MOUNTAINS:
				return "§7Extreme Mountain Hills"; 
			case FLOWER_FOREST:
				return "§cFlower Forest"; 
			case FOREST:
				return "§aForest"; 
			case FOREST_HILLS:
				return "§aForest Hills"; 
			case FROZEN_OCEAN:
				return "§bFrozen Ocean"; 
			case FROZEN_RIVER:
				return "§fFrozen River"; 
			case HELL:
				return "§cNether"; 
			case ICE_MOUNTAINS:
				return "§bCold Mountains"; 
			case ICE_PLAINS:
				return "§bIce Plains"; 
			case ICE_PLAINS_SPIKES:
				return "§bIce Spike Plains"; 
			case JUNGLE:
				return "§2Jungle"; 
			case JUNGLE_EDGE:
				return "§2Jungle"; 
			case JUNGLE_EDGE_MOUNTAINS:
				return "§2Jungle Mountains"; 
			case JUNGLE_HILLS:
				return "§2Jungle Hills"; 
			case JUNGLE_MOUNTAINS:
				return "§2Jungle Mountains"; 
			case MEGA_SPRUCE_TAIGA:
				return "§2Mega Taiga"; 
			case MEGA_SPRUCE_TAIGA_HILLS:
				return "§2Mega Taiga Hills"; 
			case MEGA_TAIGA:
				return "§2Mega Taiga"; 
			case MEGA_TAIGA_HILLS:
				return "§2Mega Taiga Hills"; 
			case MESA:
				return "§6Mesa"; 
			case MESA_BRYCE:
				return "§6Mesa"; 
			case MESA_PLATEAU:
				return "§6Mesa"; 
			case MESA_PLATEAU_FOREST:
				return "§6Mesa"; 
			case MESA_PLATEAU_FOREST_MOUNTAINS:
				return "§6Mesa"; 
			case MESA_PLATEAU_MOUNTAINS:
				return "§6Mesa"; 
			case MUSHROOM_ISLAND:
				return "§cMushroom"; 
			case MUSHROOM_SHORE:
				return "§cMushroom"; 
			case OCEAN:
				return "§9Ocean"; 
			case PLAINS:
				return "§aPlains"; 
			case RIVER:
				return "§9River"; 
			case ROOFED_FOREST:
				return "§2Roofed Forest"; 
			case ROOFED_FOREST_MOUNTAINS:
				return "§2Roofed Mountain Forest"; 
			case SAVANNA:
				return "§eSavanna"; 
			case SAVANNA_MOUNTAINS:
				return "§eSavanna Mountains"; 
			case SAVANNA_PLATEAU:
				return "§eSavanna"; 
			case SAVANNA_PLATEAU_MOUNTAINS:
				return "§eSavanna Mountains"; 
			case SKY:
				return "§fSky"; 
			case SMALL_MOUNTAINS:
				return "§7Mountains"; 
			case STONE_BEACH:
				return "§7Stone Beach"; 
			case SUNFLOWER_PLAINS:
				return "§6Sunflower Plains"; 
			case SWAMPLAND:
				return "§8Swampland"; 
			case SWAMPLAND_MOUNTAINS:
				return "§8Swampland"; 
			case TAIGA:
				return "§2Taiga"; 
			case TAIGA_HILLS:
				return "§2Taiga Hills"; 
			case TAIGA_MOUNTAINS:
				return "§2Taiga Mountains"; 
			default:
				return null;
		}
	}
	
	public ItemStack getRegionItemStack(Region region){
		ItemStack item = new ItemStack(getMaterial(region.getBiome()), region.getRegionID());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§7§lRegion §a§l" + region.getRegionID());
		List<String> lore = new ArrayList<String>();
		lore.add(" §7Biome: " + getBiomeName(region.getBiome()));
		lore.add(" §7XYZ: §a" + region.getLocation().getBlockX() + " §7/ §a" + region.getLocation().getBlockY() + " §7/ §a" + region.getLocation().getBlockZ());
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.setDurability(getDurability(region.getBiome()));
		
		return item;
	}
}
