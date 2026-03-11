package om.fog.utils.enums;

import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_8_R3.World;
import om.fog.nms.zombie.Zombie;
import om.fog.nms.zombiearcher.ZombieArcher;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public enum Mob {

	ZOMBIE("§7Zombie"),
	ZOMBIE_ARCHER("§7Zombie Archer");
	
	private String name;
	
	Mob(String name){
		this.name = name;
	}
	
	public String getName(int level) {
		return name + " §6LvL " + level + "§r";
	}
	
	public String getRawName(){
		return name;
	}
	
	public double getMaxHealth(int level){
		switch(this){
			case ZOMBIE:
				switch(level){
					case 1:
						return 7.5D;
					case 2:
						return 15.0D;
				}
				break;
			case ZOMBIE_ARCHER:
				switch(level){
					case 1:
						return 10.0D;
					case 2:
						return 17.5D;
				}
				break;
		}
		return 1;
	}
	
	public double getAttackDamage(int level){
		switch(this){
			case ZOMBIE:
				switch(level){
					case 1:
						return 3.0D;
					case 2:
						return 4.5D;
				}
				break;
			case ZOMBIE_ARCHER:
				switch(level){
					case 1:
						return 2.0D;
					case 2:
						return 3.0D;
				}
				break;
		}
		return 1;
	}
	
	public void dropItems(Location l, int level){
		double r = Math.random();
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		l.getWorld().dropItemNaturally(l, SwordLore.random());
		if(r < 1 - 0.01 * level) l.getWorld().dropItemNaturally(l, SwordLore.random());//TODO Make more rare for higher level players,.
		
		switch(this){
			case ZOMBIE:
				switch(level){
					case 1:
						l.getWorld().dropItemNaturally(l, Ore.COPPER.getItem(1));
						l.getWorld().dropItemNaturally(l, Ore.COBALT.getItem(1));
						break;
					case 2:
						l.getWorld().dropItemNaturally(l, Ore.COPPER.getItem(2));
						l.getWorld().dropItemNaturally(l, Ore.COBALT.getItem(2));
						break;
				}
				break;
			case ZOMBIE_ARCHER:
				switch(level){
					case 1:
						l.getWorld().dropItemNaturally(l, Ore.COPPER.getItem(2));
						l.getWorld().dropItemNaturally(l, Ore.COBALT.getItem(2));
						break;
					case 2:
						l.getWorld().dropItemNaturally(l, Ore.COPPER.getItem(3));
						l.getWorld().dropItemNaturally(l, Ore.COBALT.getItem(3));
						break;
				}
				break;
		}
	}
	
	public Entity spawn(Location l, int level){
	    World nmsWorld = ((CraftWorld) l.getWorld()).getHandle();
	    
		switch(this){
			case ZOMBIE:
			    Zombie e1 = new Zombie(nmsWorld);
			    e1.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			    nmsWorld.addEntity(e1);
			    
				e1.getAttributeInstance(GenericAttributes.maxHealth).setValue(getMaxHealth(level));
				e1.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(getAttackDamage(level));
			    
			    e1.setCustomName(getName(level));
			    e1.setCustomNameVisible(true);
				return e1.getBukkitEntity();
			case ZOMBIE_ARCHER:
			    ZombieArcher e2 = new ZombieArcher(nmsWorld);
			    e2.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			    nmsWorld.addEntity(e2);
			    
			    e2.goalSelector.a(4, new PathfinderGoalArrowAttack(e2, getAttackDamage(level), 20, 60, 15.0F));
			    e2.getAttributeInstance(GenericAttributes.maxHealth).setValue(getMaxHealth(level));
			    
			    EntityEquipment ee = ((LivingEntity) e2.getBukkitEntity()).getEquipment();
			    ee.setItemInHand(new ItemStack(Material.BOW));
			    
			    e2.setCustomName(getName(level));
			    e2.setCustomNameVisible(true);
				return e2.getBukkitEntity();
		}
		return null;
	}
	
	public static int parseLevel(String customName){
		for(Mob mob : Mob.values()){
			if(customName.startsWith(mob.getRawName() + " §6LvL ")){
				return Integer.parseInt(customName.substring(mob.getRawName().length() + 7, customName.indexOf("§r")));
			}
		}
		return -1;
	}
	
	public static Mob getMob(String customName){
		for(Mob mob : Mob.values()){
			if(customName.startsWith(mob.getRawName() + " §6LvL ")){
				return mob;
			}
		}
		return null;
	}
}
