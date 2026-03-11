package om.api.utils.enums.cp;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;
import om.api.handlers.players.OMPlayer;
import om.api.nms.pets.PetChicken;
import om.api.nms.pets.PetCow;
import om.api.nms.pets.PetCreeper;
import om.api.nms.pets.PetMagmaCube;
import om.api.nms.pets.PetMushroomCow;
import om.api.nms.pets.PetOcelot;
import om.api.nms.pets.PetPig;
import om.api.nms.pets.PetSheep;
import om.api.nms.pets.PetSilverfish;
import om.api.nms.pets.PetSlime;
import om.api.nms.pets.PetSpider;
import om.api.nms.pets.PetSquid;
import om.api.nms.pets.PetWolf;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.inventory.ItemStack;

public enum Pet {
	
	MUSHROOM_COW("§4Mushroom Cow Pet", "MushroomCow", 475, 96), 
	PIG("§dPig Pet", "Pig", 425, 90), 
	WOLF("§7Wolf Pet", "Wolf", 475, 95), 
	SHEEP("§fSheep Pet", "Sheep", 425, 91), 
	HORSE("§6Horse Pet", "Horse", 525, 100), 
	MAGMA_CUBE("§cMagma Cube Pet", "MagmaCube", 500, 62), 
	SLIME("§aSlime Pet", "Slime", 475, 55), 
	COW("§8Cow Pet", "Cow", 425, 92), 
	SILVERFISH("§7Silverfish Pet", "Silverfish", 450, 60), 
	OCELOT("§eOcelot Pet", "Ocelot", 450, 98), 
	CREEPER("§aCreeper Pet", "Creeper", 425, 50), 
	SQUID("§9Squid Pet", "Squid", 600, 94), 
	SPIDER("§8Spider Pet", "Spider", 500, 52), 
	CHICKEN("§7Chicken Pet", "Chicken", 425, 93);
	
	public static List<Entity> pets = new ArrayList<Entity>();
	
	private String name;
	private String databaseName;
	private int price;
	private int durability;
	
	Pet(String name, String databaseName, int price, int durability){
		this.name = name;
		this.databaseName = databaseName;
		this.price = price;
		this.durability = durability;
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
	
	public String getPriceName(){
		return "§cPrice: §b" + getPrice() + " VIP Points";
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
		
	    World nmsWorld = ((CraftWorld) l.getWorld()).getHandle();
		
	    switch(this){
			case CHICKEN:
			    PetChicken chicken = new PetChicken(nmsWorld);
			    updateLE(chicken, nmsWorld, l, omp);
			    chicken.setAge(1);
				break;
			case COW:
			    PetCow cow = new PetCow(nmsWorld);
			    updateLE(cow, nmsWorld, l, omp);
			    cow.setAge(1);
				break;
			case CREEPER:
			    PetCreeper creeper = new PetCreeper(nmsWorld);
			    updateLE(creeper, nmsWorld, l, omp);
				break;
			case HORSE:
				Horse horse = (Horse) l.getWorld().spawnEntity(l, EntityType.HORSE);
				horse.setAdult();
				horse.setTamed(true);
				horse.setRemoveWhenFarAway(false);
				horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
				horse.setColor(Horse.Color.BROWN);
				horse.setStyle(Style.WHITE);

				AttributeInstance currentSpeed = ((EntityInsentient) ((CraftLivingEntity) horse).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
				currentSpeed.setValue(0.25);

				horse.setCustomName(omp.getPetName(this));
				horse.setCustomNameVisible(true);
				
			    omp.setPet(horse);
				omp.setPetEnabled(this);
				break;
			case MAGMA_CUBE:
			    PetMagmaCube magmacube = new PetMagmaCube(nmsWorld);
			    updateLE(magmacube, nmsWorld, l, omp);
			    magmacube.setSize(3);
				break;
			case MUSHROOM_COW:
			    PetMushroomCow mushroomcow = new PetMushroomCow(nmsWorld);
			    updateLE(mushroomcow, nmsWorld, l, omp);
			    omp.setPetShroomTrail(false);
				break;
			case OCELOT:
			    PetOcelot ocelot = new PetOcelot(nmsWorld);
			    updateLE(ocelot, nmsWorld, l, omp);
			    ocelot.setAge(1);
				break;
			case PIG:
			    PetPig pig = new PetPig(nmsWorld);
			    pig.setAge(1);
			    updateLE(pig, nmsWorld, l, omp);
			    omp.setPetBabyPigs(false);
				break;
			case SHEEP:
			    PetSheep sheep = new PetSheep(nmsWorld);
			    sheep.setAge(1);
			    updateLE(sheep, nmsWorld, l, omp);
			    omp.setPetSheepDisco(false);
				break;
			case SILVERFISH:
			    PetSilverfish silverfish = new PetSilverfish(nmsWorld);
			    updateLE(silverfish, nmsWorld, l, omp);
				break;
			case SLIME:
			    PetSlime slime = new PetSlime(nmsWorld);
			    updateLE(slime, nmsWorld, l, omp);
			    slime.setSize(3);
				break;
			case SPIDER:
			    PetSpider spider = new PetSpider(nmsWorld);
			    updateLE(spider, nmsWorld, l, omp);
				break;
			case SQUID:
			    PetSquid squid = new PetSquid(nmsWorld);
			    updateLE(squid, nmsWorld, l, omp);
				break;
			case WOLF:
			    PetWolf wolf = new PetWolf(nmsWorld);
			    updateLE(wolf, nmsWorld, l, omp);
			    wolf.setAge(1);
				break;
			default:
				break;
	    }
	}
	
	private void updateLE(EntityLiving pet, World nmsWorld, Location l, OMPlayer omp){
	    pet.setPosition(l.getX(), l.getY(), l.getZ());
	    nmsWorld.addEntity(pet);
	    
	    pet.setCustomName(omp.getPetName(this));
	    pet.setCustomNameVisible(true);
		
	    omp.setPet(pet.getBukkitEntity());
		omp.setPetEnabled(this);
	}
}

