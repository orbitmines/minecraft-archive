package me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Spider;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class KitPvPUtils {

	public static enum ItemType {
		
		LIGHTNING_I,
		LIGHTNING_II,
		VAMPIRE_I,
		KNOCKUP_I,
		BLINDNESS_I,
		BLINDNESS_II,
		TRADE_I,
		ARTHROPODS_I,
		ARTHROPODS_II,
		ARTHROPODS_III,
		MAGIC_I,
		WITHER_I,
		FIRE_SPELL_I,
		FIRE_SPELL_II,
		HEALING_I,
		HEALING_II,
		BARRIER_I,
		BARRIER_II,
		LIGHT_I,
		TNT_I,
		FISH_ATTACK_I,
		SHIELD_I,
		SHIELD_II,
		POTION_LAUNCHER_I,
		HEALING_KIT_I;
		
		public String getName(){
			switch(this){
				case ARTHROPODS_I:
					return "§7Arthropods I";
				case ARTHROPODS_II:
					return "§7Arthropods II";
				case ARTHROPODS_III:
					return "§7Arthropods III";
				case BLINDNESS_I:
					return "§7Blindness I";
				case BLINDNESS_II:
					return "§7Blindness II";
				case KNOCKUP_I:
					return "§7Knockup I";
				case LIGHTNING_I:
					return "§7Lightning I";
				case LIGHTNING_II:
					return "§7Lightning II";
				case MAGIC_I:
					return "§7Magic I";
				case TRADE_I:
					return "§7Trade I";
				case VAMPIRE_I:
					return "§7Vampire I";
				case WITHER_I:
					return "§7Wither I";
				case FIRE_SPELL_I:
					return "§7Fire Spell I";
				case FIRE_SPELL_II:
					return "§7Fire Spell II";
				case HEALING_I:
					return "§7Healing I";
				case HEALING_II:
					return "§7Healing II";
				case BARRIER_I:
					return "§7Barrier I";
				case BARRIER_II:
					return "§7Barrier II";
				case LIGHT_I:
					return "§7Light I";
				case TNT_I:
					return "§7TNT I";
				case FISH_ATTACK_I:
					return "§7Fish Attack I";
				case SHIELD_I:
					return "§7Shield I";
				case SHIELD_II:
					return "§7Shield II";
				case POTION_LAUNCHER_I:
					return "§7Potion Launcher I";
				case HEALING_KIT_I:
					return "§7Healing Kit I";
				default:
					return "";
			}
		}
		
		public List<String> addEnchantment(List<String> itemlore){
			itemlore.add(getName());
			return itemlore;
		}
		
		public void playEnchantment(OMPlayer ompD, OMPlayer ompE, double damage){
			Player pD = ompD.getPlayer();
			Player pE = ompE.getPlayer();
			
			switch(this){
				case ARTHROPODS_I:
					if(new Random().nextInt(6) == 0){
			        	final Spider s = (Spider) (pE.getWorld().spawnEntity(pE.getLocation(), EntityType.SPIDER));
						s.setCustomName("§6Spiderman");
						s.setCustomNameVisible(true);
						s.setTarget((LivingEntity) pE);
					
						new BukkitRunnable(){
							public void run(){
								if(!s.isDead()){
									s.getWorld().playEffect(s.getLocation(), Effect.STEP_SOUND, 152);
									s.remove();
								}
							}
						}.runTaskLater(Start.getInstance(), 200);
					}
					
					ompE.addPotionEffect(PotionEffectType.POISON, 1, 2);
					break;
				case ARTHROPODS_II:
					if(new Random().nextInt(5) == 0){
						final List<Entity> entities = new ArrayList<Entity>();
						
		        		final Skeleton sk = (Skeleton) (pE.getWorld().spawnEntity(pE.getLocation(), EntityType.SKELETON));
		        		sk.setSkeletonType(SkeletonType.NORMAL);
		        		sk.setTarget((LivingEntity) pE);
		        		entities.add(sk);
						
			        	Spider s = (Spider) (pE.getWorld().spawnEntity(pE.getLocation(), EntityType.SPIDER));
						s.setCustomName("§6Spiderman");
						s.setCustomNameVisible(true);
						s.setTarget((LivingEntity) pE);
						entities.add(s);
					
						new BukkitRunnable(){
							public void run(){
								for(Entity en : entities){
									if(!en.isDead()){
										en.getWorld().playEffect(en.getLocation(), Effect.STEP_SOUND, 152);
										en.remove();
									}
								}
							}
						}.runTaskLater(Start.getInstance(), 200);
					}
					
					ompE.addPotionEffect(PotionEffectType.POISON, 2, 2);
					break;
				case ARTHROPODS_III:
					if(new Random().nextInt(3) == 0){
						final List<Entity> entities = new ArrayList<Entity>();
						
		        		final Skeleton sk = (Skeleton) (pE.getWorld().spawnEntity(pE.getLocation(), EntityType.SKELETON));
		        		sk.setSkeletonType(SkeletonType.NORMAL);
		        		sk.setTarget((LivingEntity) pE);
		        		entities.add(sk);
						
			        	Spider s = (Spider) (pE.getWorld().spawnEntity(pE.getLocation(), EntityType.SPIDER));
						s.setCustomName("§6Spiderman");
						s.setCustomNameVisible(true);
						s.setTarget((LivingEntity) pE);
						entities.add(s);
					
						new BukkitRunnable(){
							public void run(){
								for(Entity en : entities){
									if(!en.isDead()){
										en.getWorld().playEffect(en.getLocation(), Effect.STEP_SOUND, 152);
										en.remove();
									}
								}
							}
						}.runTaskLater(Start.getInstance(), 200);
					}
					
					ompE.addPotionEffect(PotionEffectType.POISON, 4, 2);
					break;
				case BLINDNESS_I:
					ompE.addPotionEffect(PotionEffectType.BLINDNESS, 2, 0);
					break;
				case BLINDNESS_II:
					ompE.addPotionEffect(PotionEffectType.BLINDNESS, 3, 0);
					break;
				case KNOCKUP_I:
					if(new Random().nextInt(2) == 0){
						pE.setVelocity(new Vector(0, 0.9, 0));
						pE.getWorld().playSound(pE.getLocation(), Sound.FALL_BIG, 6, 2);
					}
					break;
				case LIGHTNING_I:
					if(new Random().nextInt(4) == 0){
						pE.getWorld().strikeLightning(pE.getLocation());
					}
					break;
				case LIGHTNING_II:
					if(new Random().nextInt(3) == 0){
						pE.getWorld().strikeLightning(pE.getLocation());
					}
					break;
				case MAGIC_I:
					ompE.addPotionEffect(PotionEffectType.WITHER, 4, 0);
					break;
				case TRADE_I:
					ItemStack item = pD.getItemInHand();
					if(item.getAmount() > 1){
						item.setAmount(item.getAmount() -1);
					}
					else{
						pD.getInventory().remove(item);
					}
	    			pD.playSound(pD.getLocation(), Sound.VILLAGER_HAGGLE, 5, 1);
	    			pE.playSound(pE.getLocation(), Sound.VILLAGER_HAGGLE, 5, 1);
					break;
				case VAMPIRE_I:
					if(pD.getHealth() + damage <= pD.getMaxHealth()){
						pD.setHealth(pD.getHealth() + damage);
					}
					else{
						pD.setHealth(pD.getMaxHealth());
					}
					break;
				default:
					break;
			}
		}
	}
	
	public static enum ArmorType {
		
		MOLTEN_ARMOR_I,
		WITHER_ARMOR_I;
		
		public String getName(){
			switch(this){
				case MOLTEN_ARMOR_I:
					return "§7Molten Armor I";
				case WITHER_ARMOR_I:
					return "§7Wither Armor I";
				default:
					return "";
			}
		}
		
		public List<String> addEnchantment(List<String> itemlore){
			itemlore.add(getName());
			return itemlore;
		}
		
		public void playEnchantment(OMPlayer ompD, OMPlayer ompE){
			switch(this){
				case MOLTEN_ARMOR_I:
					ompE.addPotionEffect(PotionEffectType.BLINDNESS, 5, 0);
					ompE.addPotionEffect(PotionEffectType.SLOW, 5, 5);
					break;
				case WITHER_ARMOR_I:
					ompE.addPotionEffect(PotionEffectType.WITHER, 5, 1);
					break;
				default:
					break;
			}
		}
	}
	
	public static enum ProjectileType {
		
		UNDEATH_I,
		UNDEATH_II,
		EXPLOSIVE_I,
		ARROW_SPLIT_I,
		LIGHTNING_I,
		WITHER_I;

		public String getName(){
			switch(this){
				case ARROW_SPLIT_I:
					return "§7Arrow Split I";
				case EXPLOSIVE_I:
					return "§7Explosive I";
				case UNDEATH_I:
					return "§7Undeath I";
				case UNDEATH_II:
					return "§7Undeath II";
				case LIGHTNING_I:
					return "§7Lightning I";
				case WITHER_I:
					return "§7Wither I";
				default:
					return "";
			}
		}
		
		public List<String> addEnchantment(List<String> itemlore){
			itemlore.add(getName());
			return itemlore;
		}
	}
	
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
					return Material.BOW;
				case ASSASSIN:
					return Material.DIAMOND_SWORD;
				case BEAST:
					return Material.IRON_AXE;
				case BLAZE:
					return Material.BLAZE_POWDER;
				case BUNNY:
					return Material.LEATHER_BOOTS;
				case DARKMAGE:
					return Material.BLAZE_ROD;
				case DRUNK:
					return Material.CHAINMAIL_LEGGINGS;
				case FISH:
					return Material.RAW_FISH;
				case FISHERMAN:
					return Material.FISHING_ROD;
				case GRIMREAPER:
					return Material.SKULL_ITEM;
				case HEAVY:
					return Material.ARROW;
				case KING:
					return Material.DIAMOND_HELMET;
				case KNIGHT:
					return Material.STONE_SWORD;
				case LIBRARIAN:
					return Material.BOOKSHELF;
				case LORD:
					return Material.GOLD_SWORD;
				case MINER:
					return Material.GOLD_PICKAXE;
				case NECROMANCER:
					return Material.GOLD_HOE;
				case PYRO:
					return Material.GOLD_HELMET;
				case SNOWGOLEM:
					return Material.PUMPKIN;
				case SOLDIER:
					return Material.LEATHER_LEGGINGS;
				case SPIDER:
					return Material.SPIDER_EYE;
				case TANK:
					return Material.DIAMOND_CHESTPLATE;
				case TNT:
					return Material.TNT;
				case TREE:
					return Material.LEAVES;
				case VAMPIRE:
					return Material.REDSTONE;
				case VILLAGER:
					return Material.EMERALD;
				case WIZARD:
					return Material.POTION;
				default:
					return null;
			}
		}
		
		public short getDurability(){
			switch(this){
				case GRIMREAPER:
					return 1;
				default:
					return 0;
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
		
		public int getNextArrow(){
			switch(this){
				case ARCHER:
					return 10;
				case HEAVY:
					return 21;
				case NECROMANCER:
					return 27;
				case PYRO:
					return 22;
				case SOLDIER:
					return 18;
				case TNT:
					return 30;
				default:
					return 0;
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
