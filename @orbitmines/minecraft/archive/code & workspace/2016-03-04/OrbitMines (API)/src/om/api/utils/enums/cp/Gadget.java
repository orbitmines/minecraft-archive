package om.api.utils.enums.cp;

import om.api.handlers.players.OMPlayer;

import org.bukkit.Material;

public enum Gadget {

	STACKER("§6§lStacker", null, -1, Material.LEASH),
	PAINTBALLS("§f§lPaintballs", "Paintballs", 700, Material.SNOW_BALL),
	CREEPER_LAUNCHER("§a§lCreeper Launcher", "CreeperLauncher", 525, Material.SKULL_ITEM),
	PET_RIDE("§e§lPet Ride", "PetRide", 500, Material.SADDLE),
	BOOK_EXPLOSION("§7§lBook Explosion", "BookExplosion", 475, Material.BOOK),
	SWAP_TELEPORTER("§2§lSwap Teleporter", "SwapTeleporter", 500, Material.EYE_OF_ENDER),
	FIREWORK_GUN("§c§lFirework Gun", null, -1, Material.FIREBALL),
	MAGMACUBE_SOCCER("§c§lMagmaCube Soccer", "MagmaCubeSoccer", 1000, Material.MAGMA_CREAM),
	SNOWMAN_ATTACK("§6§lSnowman Attack", "SnowmanAttack", 1200, Material.PUMPKIN),
	FLAME_THROWER("§e§lFlame Thrower", "FlameThrower", 575, Material.BLAZE_POWDER),
	GRAPPLING_HOOK("§7§lGrappling Hook", "GrapplingHook", 1250, Material.FISHING_ROD);
	
	private String name;
	private String databaseName;
	private int price;
	private Material material;
	
	Gadget(String name, String databaseName, int price, Material material){
		this.name = name;
		this.databaseName = databaseName;
		this.price = price;
		this.material = material;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDatabaseName(){
		return databaseName;
	}
	
	public int getPrice(){
		return price;
	}
	
	public String getPriceName(OMPlayer omp){
		switch(this){
			case PET_RIDE:
				if(omp.hasPet()){
					return "§cPrice: §b" + getPrice() + " VIP Points";
				}
				else{
					return "§cRequired: §7§la Pet";
				}
			default:
				return "§cPrice: §b" + getPrice() + " VIP Points";
		}
	}
	
	public boolean hasGadget(OMPlayer omp){
		switch(this){
			case FIREWORK_GUN:
				return true;
			case STACKER:
				return true;
			default:
				return omp.hasGadget(this);
		}
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public short getDurability(){
		switch(this){
			case CREEPER_LAUNCHER:
				return 4;
			default:
				return 0;
		}
	}
}

