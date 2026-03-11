package om.kitpvp.utils.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.kitpvp.KitPvP;

import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public enum ProjectileType {
	
	UNDEATH_I("§7Undeath I"),
	UNDEATH_II("§7Undeath II"),
	EXPLOSIVE_I("§7Explosive I"),
	ARROW_SPLIT_I("§7Arrow Split I"),
	LIGHTNING_I("§7Lightning I"),
	WITHER_I("§7Wither I"),
	BLOCK_EXPLOSION_I("§7Block Explosion I"),
	PAINTBALLS_I("§7Paintballs I");

	private KitPvP kitpvp;
	private String name;
	
	ProjectileType(String name){
		this.kitpvp = KitPvP.getInstance();
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public List<String> addEnchantment(List<String> itemlore){
		itemlore.add(getName());
		return itemlore;
	}
	
	public void playEnchantment(OMPlayer ompD, OMPlayer ompE, Projectile proj){
		switch(this){
			case EXPLOSIVE_I:				
				TNTPrimed tnt = (TNTPrimed) proj.getWorld().spawn(proj.getLocation(), TNTPrimed.class);
				tnt.setFuseTicks(10);
				break;
			case LIGHTNING_I:
				proj.getWorld().strikeLightning(proj.getLocation());
				break;
			case UNDEATH_I:
				final List<Entity> undeath1 = new ArrayList<Entity>();
				
				for(int i = 0; i < 3; i++){
					Zombie z = (Zombie) (proj.getWorld().spawnEntity(proj.getLocation(), EntityType.ZOMBIE));
					z.setCustomName("§4Undeath Knight");
					z.setCustomNameVisible(true);
	        		z.setTarget((LivingEntity) ompE.getPlayer());
					undeath1.add(z);
				}
				
				new BukkitRunnable(){
					public void run(){
						for(Entity en : undeath1){
							if(!en.isDead()){
								en.getWorld().playEffect(en.getLocation(), Effect.STEP_SOUND, 152);
								en.remove();
							}
						}
					}
				}.runTaskLater(kitpvp, 300);
				break;
			case UNDEATH_II:
				final List<Entity> undeath2 = new ArrayList<Entity>();
				
				for(int i = 0; i < 3; i++){
					Zombie z = (Zombie) (proj.getWorld().spawnEntity(proj.getLocation(), EntityType.ZOMBIE));
					z.setCustomName("§4Undeath Knight");
					z.setCustomNameVisible(true);
	        		z.setTarget((LivingEntity) ompE.getPlayer());
					undeath2.add(z);
				}
				
				final Skeleton s = (Skeleton) (proj.getWorld().spawnEntity(proj.getLocation(), EntityType.SKELETON));
				s.setSkeletonType(SkeletonType.NORMAL);
				s.setCustomName("§4Undeath Archer");
				s.setCustomNameVisible(true);
        		s.setTarget((LivingEntity) ompE.getPlayer());
				undeath2.add(s);
				
				new BukkitRunnable(){
					public void run(){
						for(Entity en : undeath2){
							if(!en.isDead()){
								en.getWorld().playEffect(en.getLocation(), Effect.STEP_SOUND, 152);
								en.remove();
							}
						}
					}
				}.runTaskLater(kitpvp, 300);
				break;
			case WITHER_I:
				ompE.addPotionEffect(PotionEffectType.WITHER, 5, 0);
				break;
			default:
				break;
		}
	}
	
	public static List<ProjectileType> getProjectileTypes(KitPvPKit kit){
		List<ProjectileType> types = new ArrayList<ProjectileType>();
		
		switch(kit){
			case ARCHER:
				types = Arrays.asList(ProjectileType.LIGHTNING_I);
				break;
			case ENGINEER:
				types = Arrays.asList(ProjectileType.PAINTBALLS_I);
				break;
			case FARMER:
				types = Arrays.asList(ProjectileType.BLOCK_EXPLOSION_I);
				break;
			case HEAVY:
				types = Arrays.asList(ProjectileType.ARROW_SPLIT_I);
				break;
			case NECROMANCER:
				types = Arrays.asList(ProjectileType.UNDEATH_I, ProjectileType.UNDEATH_II, ProjectileType.WITHER_I);
				break;
			case TNT:
				types = Arrays.asList(ProjectileType.EXPLOSIVE_I);
				break;
			default:
				break;
		}
		
		return types;
	}
}
