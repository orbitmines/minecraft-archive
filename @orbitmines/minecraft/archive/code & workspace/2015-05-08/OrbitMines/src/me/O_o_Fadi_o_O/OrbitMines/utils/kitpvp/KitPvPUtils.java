package me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;

import org.bukkit.Material;

public class KitPvPUtils {

	public static enum Booster {
		
		IRON_BOOSTER,
		GOLD_BOOSTER,
		DIAMOND_BOOSTER,
		EMERALD_BOOSTER;
		
		public String getName(){
			switch(this){
				case DIAMOND_BOOSTER:
					return "§b§lDiamond VIP Booster";
				case EMERALD_BOOSTER:
					return "§a§lEmerald VIP Booster";
				case GOLD_BOOSTER:
					return "§6§lGold VIP Booster";
				case IRON_BOOSTER:
					return "§7§lIron VIP Booster";
				default:
					return null;
			}
		}
		
		public int getPrice(){
			return 250;
		}
		
		public double getMultiplier(){
			switch(this){
				case DIAMOND_BOOSTER:
					return 2.00;
				case EMERALD_BOOSTER:
					return 2.50;
				case GOLD_BOOSTER:
					return 1.50;
				case IRON_BOOSTER:
					return 1.25;
				default:
					return 1.00;
			}
		}
		
		public boolean hasPerms(OMPlayer omp){
			switch(this){
				case DIAMOND_BOOSTER:
					return omp.hasPerms(VIPRank.Diamond_VIP);
				case EMERALD_BOOSTER:
					return omp.hasPerms(VIPRank.Emerald_VIP);
				case GOLD_BOOSTER:
					return omp.hasPerms(VIPRank.Gold_VIP);
				case IRON_BOOSTER:
					return omp.hasPerms(VIPRank.Iron_VIP);
				default:
					return false;
			}
		}
		
		public Material getMaterial(){
			switch(this){
				case DIAMOND_BOOSTER:
					return Material.DIAMOND;
				case EMERALD_BOOSTER:
					return Material.EMERALD;
				case GOLD_BOOSTER:
					return Material.GOLD_INGOT;
				case IRON_BOOSTER:
					return Material.IRON_INGOT;
				default:
					return null;
			}
		}
		
		public String getPriceName(OMPlayer omp){
			if(hasPerms(omp)){
				return "§cPrice: §b" + getPrice() + " VIP Points";
			}
			return "§cRequired: " + omp.getVIPRank().getRankString();
		}
	}
	
	public static enum KitPvPKit {

		KNIGHT,
		ARCHER,
		SOLDIER,
		WIZARD,
		TANK,
		DRUNK,
		PYRO,
		BUNNY,
		NECROMANCER,
		KING,
		TREE,
		BLAZE,
		TNT,
		FISHERMAN,
		SNOWGOLEM,
		LIBRARIAN,
		SPIDER,
		VILLAGER,
		ASSASSIN,
		LORD,
		VAMPIRE,
		DARKMAGE,
		BEAST,
		FISH,
		HEAVY,
		GRIMREAPER,
		MINER;
		
		public Kit getKit(int level){
			return Kit.getKit(getName() + " " + level);
		}
		
		public String getName(){
			switch(this){
				case KNIGHT:
					return "Knight";
				case ARCHER:
					return "Archer";
				case SOLDIER:
					return "Soldier";
				case WIZARD:
					return "Wizard";
				case TANK:
					return "Tank";
				case DRUNK:
					return "Drunk";
				case PYRO:
					return "Pyro";
				case BUNNY:
					return "Bunny";
				case NECROMANCER:
					return "Necromancer";
				case KING:
					return "King";
				case TREE:
					return "Tree";
				case BLAZE:
					return "Blaze";
				case TNT:
					return "TNT";
				case FISHERMAN:
					return "Fisherman";
				case SNOWGOLEM:
					return "SnowGolem";
				case LIBRARIAN:
					return "Librarian";
				case SPIDER:
					return "Spider";
				case VILLAGER:
					return "Villager";
				case ASSASSIN:
					return "Assassin";
				case VAMPIRE:
					return "Vampire";
				case DARKMAGE:
					return "DarkMage";
				case BEAST:
					return "Beast";
				case FISH:
					return "Fish";
				case HEAVY:
					return "Heavy";
				case GRIMREAPER:
					return "GrimReaper";
				case MINER:
					return "Miner";
				default:
					return null;
			}
		}
		
		public Material getMaterial(){
			switch(this){
				case ARCHER:
					break;
				case ASSASSIN:
					break;
				case BEAST:
					break;
				case BLAZE:
					break;
				case BUNNY:
					break;
				case DARKMAGE:
					break;
				case DRUNK:
					break;
				case FISH:
					break;
				case FISHERMAN:
					break;
				case GRIMREAPER:
					break;
				case HEAVY:
					break;
				case KING:
					break;
				case KNIGHT:
					return Material.STONE_SWORD;
				case LIBRARIAN:
					break;
				case LORD:
					break;
				case MINER:
					break;
				case NECROMANCER:
					break;
				case PYRO:
					break;
				case SNOWGOLEM:
					break;
				case SOLDIER:
					break;
				case SPIDER:
					break;
				case TANK:
					break;
				case TNT:
					break;
				case TREE:
					break;
				case VAMPIRE:
					break;
				case VILLAGER:
					break;
				case WIZARD:
					break;
				default:
					return null;
			}
		}
		
		public int getHighestLevel(){
			switch(this){
				case ASSASSIN:
					return 1;
				case BEAST:
					return 1;
				case DARKMAGE:
					return 1;
				case FISH:
					return 1;
				case GRIMREAPER:
					return 1;
				case HEAVY:
					return 1;
				case LORD:
					return 1;
				case MINER:
					return 1;
				case VAMPIRE:
					return 1;
				default:
					return 3;
			}
		}
		
		public String getSelectedKitName(int level){
			return "§b§l" + getName() + " §7(§aLvL " + level + "§7)";
		}
		
		public String getKitName(int level){
			if(level != 0){
				return "§b§l" + getName() + " §a§lLvL " + level;
			}
			return "§b§l" + getName();
		}
		
		public List<String> getKitLore(int level){
			List<String> kitlore = new ArrayList<String>();
			kitlore.add("");
			if(ServerData.getKitPvP().isFreeKitEnabled()){
				kitlore.add("§d§lFREE Kit Saturday!");
			}
			else{
				if(level != 0){
					kitlore.add("§a§lUnlocked §7§o(§a§oLvL " + level + "§7§o)");
				}
				else{
					kitlore.add("§4§lLocked");
				}
			}
			kitlore.add("");
			if(level != 0){
				kitlore.add("§c§o§l§mRight Click §c§m(Select Kit)");
				if(level != getHighestLevel()){
					kitlore.add("§6§l§oLeft Click §7(Details & Upgrade Kit)");
				}
				else{
					kitlore.add("§6§l§oLeft Click §7(Details)");
				}
			}
			else{
				kitlore.add("§e§l§oRight Click §7(Select Kit)");
				kitlore.add("§6§l§oLeft Click §7(Details & Buy Kit)");
			}
			kitlore.add("");
			
			return kitlore;
		}
	}
}
