package om.fog.utils.enums;

import net.minecraft.server.v1_8_R3.World;
import om.fog.nms.zombie.Zombie;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;

public enum Mob {

	ZOMBIE("§7Zombie");
	
	private String name;
	
	Mob(String name){
		this.name = name;
	}
	
	public String getName(int level) {
		return name + " §6LvL " + level + "§r";
	}
	
	public double getMaxHealth(int level){
		switch(this){
			case ZOMBIE:
				switch(level){
					case 1:
						return 30.0D;
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
						return 4.0D;
				}
				break;
		}
		return 1;
	}
	
	public Entity spawn(Location l, int level){
		switch(this){
			case ZOMBIE:
			    World nmsWorld = ((CraftWorld) l.getWorld()).getHandle();
			    Zombie e = new Zombie(nmsWorld);
			    e.setPositionRotation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			    nmsWorld.addEntity(e);
			    
			    e.setCustomName(getName());
			    e.setCustomNameVisible(true);
				return e.getBukkitEntity();
		}
		return null;
	}
}
