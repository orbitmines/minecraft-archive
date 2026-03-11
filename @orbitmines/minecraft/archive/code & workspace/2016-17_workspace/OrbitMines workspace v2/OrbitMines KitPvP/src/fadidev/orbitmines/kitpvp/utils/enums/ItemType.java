package fadidev.orbitmines.kitpvp.utils.enums;

import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public enum ItemType {
	
	LIGHTNING_I("§7Lightning I"),
	LIGHTNING_II("§7Lightning II"),
	VAMPIRE_I("§7Vampire I"),
	KNOCKUP_I("§7Knockup I"),
	BLINDNESS_I("§7Blindness I"),
	BLINDNESS_II("§7Blindness II"),
	TRADE_I("§7Trade I"),
	ARTHROPODS_I("§7Arthropods I"),
	ARTHROPODS_II("§7Arthropods II"),
	ARTHROPODS_III("§7Arthropods III"),
	MAGIC_I("§7Magic I"),
	WITHER_I("§7Wither I"),
	FIRE_SPELL_I("§7Fire Spell I"),
	FIRE_SPELL_II("§7Fire Spell II"),
	HEALING_I("§7Healing I"),
	HEALING_II("§7Healing II"),
	BARRIER_I("§7Barrier I"),
	BARRIER_II("§7Barrier II"),
	TNT_I("§7TNT I"),
	FISH_ATTACK_I("§7Fish Attack I"),
	SHIELD_I("§7Shield I"),
	SHIELD_II("§7Shield II"),
	POTION_LAUNCHER_I("§7Potion Launcher I"),
	HEALING_KIT_I("§7Healing Kit I"),
	BLOCK_EXPLOSION_I("§7Block Explosion I"),
	UNDEATH_SUMMON_I("§7Undeath Summon I"),
	PAINTBALLS_I("§7Paintballs I");
	
	private OrbitMinesKitPvP kitPvP;
	private String name;
	
	ItemType(String name){
		this.kitPvP = OrbitMinesKitPvP.getKitPvP();
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public List<String> addEnchantment(List<String> itemlore){
		itemlore.add(getName());
		return itemlore;
	}
	
	public void playEnchantment(KitPvPPlayer ompD, KitPvPPlayer ompE, double damage){
		Player pD = ompD.getPlayer();
		final Player pE = ompE.getPlayer();
		
		switch(this){
			case ARTHROPODS_I:
				if(new Random().nextInt(6) == 0){
		        	final Spider s = (Spider) (pE.getWorld().spawnEntity(pE.getLocation(), EntityType.SPIDER));
					s.setCustomName("§6Spiderman");
					s.setCustomNameVisible(true);
					s.setTarget(pE);
				
					new BukkitRunnable(){
						public void run(){
							if(!s.isDead()){
								s.getWorld().playEffect(s.getLocation(), Effect.STEP_SOUND, 152);
								s.remove();
							}
						}
					}.runTaskLater(kitPvP, 200);
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
					s.setTarget(pE);
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
					}.runTaskLater(kitPvP, 200);
				}
				
				ompE.addPotionEffect(PotionEffectType.POISON, 2, 2);
				break;
			case ARTHROPODS_III:
				if(new Random().nextInt(3) == 0){
					final List<Entity> entities = new ArrayList<Entity>();
					
	        		final Skeleton sk = (Skeleton) (pE.getWorld().spawnEntity(pE.getLocation(), EntityType.SKELETON));
	        		sk.setSkeletonType(SkeletonType.NORMAL);
	        		sk.setTarget(pE);
	        		entities.add(sk);
					
		        	Spider s = (Spider) (pE.getWorld().spawnEntity(pE.getLocation(), EntityType.SPIDER));
					s.setCustomName("§6Spiderman");
					s.setCustomNameVisible(true);
					s.setTarget(pE);
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
					}.runTaskLater(kitPvP, 200);
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
					new BukkitRunnable(){
						public void run(){
							pE.setVelocity(new Vector(0, 0.9, 0));
							pE.getWorld().playSound(pE.getLocation(), Sound.ENTITY_GENERIC_BIG_FALL, 6, 2);
						}
					}.runTaskLater(kitPvP, 1);
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
	    			pD.playSound(pD.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 5, 1);
	    			pE.playSound(pE.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 5, 1);
	    		}
				break;
			case VAMPIRE_I:
				if(pD.getHealth() + 1.5 <= pD.getMaxHealth()){
					pD.setHealth(pD.getHealth() + 1.5);
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
		List<ItemType> types = new ArrayList<>();

		switch(kit){
			case ARCHER:
				break;
			case BEAST:
				types = Collections.singletonList(ItemType.KNOCKUP_I);
				break;
			case DARKMAGE:
				types = Arrays.asList(ItemType.MAGIC_I, ItemType.POTION_LAUNCHER_I);
				break;
			case DRUNK:
				types = Arrays.asList(ItemType.BLINDNESS_I, ItemType.BLINDNESS_II);
				break;
			case ENGINEER:
				types = Collections.singletonList(ItemType.PAINTBALLS_I);
				break;
			case FARMER:
				types = Collections.singletonList(ItemType.BLOCK_EXPLOSION_I);
				break;
			case FISHERMAN:
				types = Collections.singletonList(ItemType.FISH_ATTACK_I);
				break;
			case KING:
				types = Arrays.asList(ItemType.HEALING_I, ItemType.HEALING_II);
				break;
			case MINER:
				types = Collections.singletonList(ItemType.HEALING_KIT_I);
				break;
			case NECROMANCER:
				types = Collections.singletonList(ItemType.WITHER_I);
				break;
			case SNOWGOLEM:
				types = Arrays.asList(ItemType.SHIELD_I, ItemType.SHIELD_II);
				break;
			case SOLDIER:
				types = Arrays.asList(ItemType.LIGHTNING_I, ItemType.LIGHTNING_II);
				break;
			case SPIDER:
				types = Arrays.asList(ItemType.ARTHROPODS_I, ItemType.ARTHROPODS_II, ItemType.ARTHROPODS_III);
				break;
			case TNT:
				types = Collections.singletonList(ItemType.TNT_I);
				break;
			case TREE:
				types = Arrays.asList(ItemType.BARRIER_I, BARRIER_II);
				break;
			case UNDEATH_KING:
				types = Collections.singletonList(ItemType.UNDEATH_SUMMON_I);
				break;
			case VAMPIRE:
				types = Collections.singletonList(ItemType.VAMPIRE_I);
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
