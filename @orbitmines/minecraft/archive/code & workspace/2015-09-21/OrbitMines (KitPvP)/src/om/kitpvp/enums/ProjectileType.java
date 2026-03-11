package om.kitpvp.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.KitPvPKit;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ProjectileType;

import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public enum ProjectileType {
	
	UNDEATH_I,
	UNDEATH_II,
	EXPLOSIVE_I,
	ARROW_SPLIT_I,
	LIGHTNING_I,
	WITHER_I,
	BLOCK_EXPLOSION_I,
	PAINTBALLS_I;

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
			case BLOCK_EXPLOSION_I:
				return "§7Block Explosion I";
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
	
	public static void playEnchantment(OMPlayer ompD, OMPlayer ompE, Projectile proj){
		switch(ServerData.getKitPvP().getProjectileType(proj)){
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
				}.runTaskLater(Start.getInstance(), 300);
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
				}.runTaskLater(Start.getInstance(), 300);
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
