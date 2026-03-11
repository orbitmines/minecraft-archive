package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

public class RandomFallingBlock {

	private Location location;
	private Material material;
	private byte durability;
	private boolean drop;
	private double multiply;
	
	public RandomFallingBlock(Location location){
		this.location = location;
		this.multiply = 1;
	}

	public Location getLocation(){
		return location;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Material getMaterial(){
		return material;
	}

	public void setMaterial(Material material){
		this.material = material;
	}

	public byte getDurability(){
		return durability;
	}

	public void setDurability(byte durability){
		this.durability = durability;
	}

	public boolean hasDrop(){
		return drop;
	}

	public void setDrop(boolean drop){
		this.drop = drop;
	}
	
	public double getMultiply(){
		return multiply;
	}

	public void setMultiply(double multiply){
		this.multiply = multiply;
	}

	public void spawn(){
		Vector v = Utils.randomVelocity();
		if(v == null)
            return;

        FallingBlock fb = getLocation().getWorld().spawnFallingBlock(getLocation(), getMaterial(), getDurability());
        fb.setDropItem(hasDrop());
        fb.setVelocity(v.multiply(getMultiply()));
	}
}
