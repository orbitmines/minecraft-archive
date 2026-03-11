package fadidev.orbitmines.api.utils.enums.perks;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.utils.enums.Mob;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;

public enum Pet {
	
	MUSHROOM_COW(Mob.MUSHROOM_COW, "§4Mushroom Cow Pet", "MushroomCow", 475, 96),
	PIG(Mob.PIG, "§dPig Pet", "Pig", 425, 90),
	WOLF(Mob.WOLF, "§7Wolf Pet", "Wolf", 475, 95),
	SHEEP(Mob.SHEEP, "§fSheep Pet", "Sheep", 425, 91),
	HORSE(Mob.HORSE, "§6Horse Pet", "Horse", 525, 100),
	MAGMA_CUBE(Mob.MAGMA_CUBE, "§cMagma Cube Pet", "MagmaCube", 500, 62),
	SLIME(Mob.SLIME, "§aSlime Pet", "Slime", 475, 55),
	COW(Mob.COW, "§8Cow Pet", "Cow", 425, 92),
	SILVERFISH(Mob.SILVERFISH, "§7Silverfish Pet", "Silverfish", 450, 60),
	OCELOT(Mob.OCELOT, "§eOcelot Pet", "Ocelot", 450, 98),
	CREEPER(Mob.CREEPER, "§aCreeper Pet", "Creeper", 425, 50),
	SQUID(Mob.SQUID, "§9Squid Pet", "Squid", 600, 94),
	SPIDER(Mob.SPIDER, "§8Spider Pet", "Spider", 500, 52),
	CHICKEN(Mob.CHICKEN, "§7Chicken Pet", "Chicken", 425, 93);

    private Mob mob;
	private String name;
	private String databaseName;
	private int price;
	private int durability;
	
	Pet(Mob mob, String name, String databaseName, int price, int durability){
		this.mob = mob;
	    this.name = name;
		this.databaseName = databaseName;
		this.price = price;
		this.durability = durability;
	}

    public Mob getMob() {
        return mob;
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
		return "§c" + Messages.WORD_PRICE.get(omp) + ": §b" + getPrice() + " VIP Points";
	}
	
	public boolean hasPet(OMPlayer omp){
		return omp.hasPet(this);
	}
	
	public Material getMaterial(){
		return Material.MONSTER_EGG;
	}
	
	public short getDurability(){
		return (short) durability;
	}
	
	public void spawn(OMPlayer omp){
		Location l = omp.getPlayer().getLocation();

		Entity entity = mob.spawnPet(l, omp.getPetName(this));

	    switch(this){
			case CHICKEN:
                ((Chicken) entity).setAge(1);
				break;
			case COW:
                ((Cow) entity).setAge(1);
				break;
			case CREEPER:
				break;
			case HORSE:
				break;
			case MAGMA_CUBE:
                ((MagmaCube) entity).setSize(3);
				break;
			case MUSHROOM_COW:
			    omp.setPetShroomTrail(false);
				break;
			case OCELOT:
                ((Ocelot) entity).setAge(1);
				break;
			case PIG:
			    omp.setPetBabyPigs(false);
				break;
			case SHEEP:
                ((Sheep) entity).setAge(1);
			    omp.setPetSheepDisco(false);
				break;
			case SILVERFISH:
				break;
			case SLIME:
                ((Slime) entity).setSize(3);
				break;
			case SPIDER:
				break;
			case SQUID:
				break;
			case WOLF:
                ((Wolf) entity).setAge(1);
				break;
			default:
				break;
	    }

        omp.setPet(entity);
        omp.setPetEnabled(this);
	}

	//TODO TEST
	/*private void updateLE(EntityLiving pet, World nmsWorld, Location l, OMPlayer omp){
	    pet.setPosition(l.getX(), l.getY(), l.getZ());
	    nmsWorld.addEntity(pet);
	    
	    pet.setCustomName(omp.getPetName(this));
	    pet.setCustomNameVisible(true);
		
	    omp.setPet(pet.getBukkitEntity());
		omp.setPetEnabled(this);
	}*/
}

