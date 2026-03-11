package om.kitpvp.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ItemType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.KitPvPKit;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public enum ItemType {
	
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
	TNT_I,
	FISH_ATTACK_I,
	SHIELD_I,
	SHIELD_II,
	POTION_LAUNCHER_I,
	HEALING_KIT_I,
	BLOCK_EXPLOSION_I,
	UNDEATH_SUMMON_I,
	PAINTBALLS_I;
	
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
			case BLOCK_EXPLOSION_I:
				return "§7Block Explosion I";
			case UNDEATH_SUMMON_I:
				return "§7Undeath Summon I";
			case PAINTBALLS_I:
				return "§7Paintballs I";
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

	    		ItemStack item2 = pE.getItemInHand();
	    		if(item2 != null){
	    			pD.getInventory().addItem(item2);
	    			pE.getInventory().removeItem(item2);
	    			pD.playSound(pD.getLocation(), Sound.VILLAGER_HAGGLE, 5, 1);
	    			pE.playSound(pE.getLocation(), Sound.VILLAGER_HAGGLE, 5, 1);
	    		}
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
	
	public static List<ItemType> getItemTypes(KitPvPKit kit){
		List<ItemType> types = new ArrayList<ItemType>();
		
		switch(kit){
			case ARCHER:
				types = Arrays.asList(ItemType.LIGHTNING_I, ItemType.LIGHTNING_II);
				break;
			case BEAST:
				types = Arrays.asList(ItemType.KNOCKUP_I);
				break;
			case DARKMAGE:
				types = Arrays.asList(ItemType.MAGIC_I, ItemType.POTION_LAUNCHER_I);
				break;
			case DRUNK:
				types = Arrays.asList(ItemType.BLINDNESS_I, ItemType.BLINDNESS_II);
				break;
			case ENGINEER:
				types = Arrays.asList(ItemType.PAINTBALLS_I);
				break;
			case FARMER:
				types = Arrays.asList(ItemType.BLOCK_EXPLOSION_I);
				break;
			case FISHERMAN:
				types = Arrays.asList(ItemType.FISH_ATTACK_I);
				break;
			case KING:
				types = Arrays.asList(ItemType.HEALING_I, ItemType.HEALING_II);
				break;
			case MINER:
				types = Arrays.asList(ItemType.HEALING_KIT_I);
				break;
			case NECROMANCER:
				types = Arrays.asList(ItemType.WITHER_I);
				break;
			case SNOWGOLEM:
				types = Arrays.asList(ItemType.SHIELD_I, ItemType.SHIELD_II);
				break;
			case SOLDIER:
				break;
			case SPIDER:
				types = Arrays.asList(ItemType.ARTHROPODS_I, ItemType.ARTHROPODS_II, ItemType.ARTHROPODS_III);
				break;
			case TNT:
				types = Arrays.asList(ItemType.TNT_I);
				break;
			case TREE:
				types = Arrays.asList(ItemType.BARRIER_I, BARRIER_II);
				break;
			case UNDEATH_KING:
				types = Arrays.asList(ItemType.UNDEATH_SUMMON_I);
				break;
			case VAMPIRE:
				types = Arrays.asList(ItemType.VAMPIRE_I);
				break;
			case VILLAGER:
				types = Arrays.asList(values());
				break;
			case WIZARD:
				types = Arrays.asList(ItemType.FIRE_SPELL_I, ItemType.FIRE_SPELL_II);
				break;
			default:
				break;
		}
		
		return types;
	}
}
