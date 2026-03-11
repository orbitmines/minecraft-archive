package fadidev.orbitmines.kitpvp.utils.enums;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import org.bukkit.Effect;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ProjectileType {
	
	UNDEATH_I("§7Undeath I"),
	UNDEATH_II("§7Undeath II"),
	EXPLOSIVE_I("§7Explosive I"),
	ARROW_SPLIT_I("§7Arrow Split I"),
	LIGHTNING_I("§7Lightning I"),
	WITHER_I("§7Wither I"),
	BLOCK_EXPLOSION_I("§7Block Explosion I"),
	PAINTBALLS_I("§7Paintballs I");

	private OrbitMinesKitPvP kitPvP;
	private String name;
	
	ProjectileType(String name){
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
	
	public void playEnchantment(OMPlayer ompD, OMPlayer ompE, Projectile proj){
		switch(this){
			case EXPLOSIVE_I:				
				TNTPrimed tnt = proj.getWorld().spawn(proj.getLocation(), TNTPrimed.class);
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
	        		z.setTarget(ompE.getPlayer());
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
				}.runTaskLater(kitPvP, 300);
				break;
			case UNDEATH_II:
				final List<Entity> undeath2 = new ArrayList<Entity>();
				
				for(int i = 0; i < 3; i++){
					Zombie z = (Zombie) (proj.getWorld().spawnEntity(proj.getLocation(), EntityType.ZOMBIE));
					z.setCustomName("§4Undeath Knight");
					z.setCustomNameVisible(true);
	        		z.setTarget(ompE.getPlayer());
					undeath2.add(z);
				}
				
				final Skeleton s = (Skeleton) (proj.getWorld().spawnEntity(proj.getLocation(), EntityType.SKELETON));
				s.setSkeletonType(SkeletonType.NORMAL);
				s.setCustomName("§4Undeath Archer");
				s.setCustomNameVisible(true);
        		s.setTarget(ompE.getPlayer());
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
				}.runTaskLater(kitPvP, 300);
				break;
			case WITHER_I:
				ompE.addPotionEffect(PotionEffectType.WITHER, 5, 0);
				break;
			default:
				break;
		}
	}
	
	public static List<ProjectileType> getProjectileTypes(KitPvPKit kit){
		List<ProjectileType> types = new ArrayList<>();
		
		switch(kit){
			case ARCHER:
				types = Collections.singletonList(ProjectileType.LIGHTNING_I);
				break;
			case ENGINEER:
				types = Collections.singletonList(ProjectileType.PAINTBALLS_I);
				break;
			case FARMER:
				types = Collections.singletonList(ProjectileType.BLOCK_EXPLOSION_I);
				break;
			case HEAVY:
				types = Collections.singletonList(ProjectileType.ARROW_SPLIT_I);
				break;
			case NECROMANCER:
				types = Arrays.asList(ProjectileType.UNDEATH_I, ProjectileType.UNDEATH_II, ProjectileType.WITHER_I);
				break;
			case TNT:
				types = Collections.singletonList(ProjectileType.EXPLOSIVE_I);
				break;
			default:
				break;
		}
		
		return types;
	}
}
