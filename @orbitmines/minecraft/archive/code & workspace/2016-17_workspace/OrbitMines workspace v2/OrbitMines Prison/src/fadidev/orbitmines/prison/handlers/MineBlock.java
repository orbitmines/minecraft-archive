package fadidev.orbitmines.prison.handlers;

import org.bukkit.Material;

public class MineBlock {

	private Material material;
	private int durability;
	private double percentage;
	
	public MineBlock(Material material, int durability, double percentage){
		this.material = material;
		this.durability = durability;
		this.percentage = percentage;
	}

	public Material getMaterial() {
		return material;
	}

	public byte getDurability() {
		return (byte) durability;
	}

	public double getPercentage() {
		return percentage;
	}

	public MineBlock copy(){
        return new MineBlock(getMaterial(), getDurability(), getPercentage());
    }

    public Material getDropped(){
		switch(getMaterial()){
            case STONE:
                return Material.COBBLESTONE;
            case COAL_ORE:
                return Material.COAL;
            case IRON_ORE:
                return Material.IRON_INGOT;
            case DIAMOND_ORE:
                return Material.DIAMOND;
            case SNOW_BLOCK:
                return Material.SNOW_BALL;
            default:
                return getMaterial();
        }
	}
}
