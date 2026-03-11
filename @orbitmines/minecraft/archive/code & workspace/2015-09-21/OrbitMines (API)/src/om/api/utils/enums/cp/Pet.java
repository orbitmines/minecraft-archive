package om.api.utils.enums.cp;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import om.api.handlers.players.OMPlayer;
import om.api.nms.PetChicken;
import om.api.nms.PetCow;
import om.api.nms.PetCreeper;
import om.api.nms.PetMagmaCube;
import om.api.nms.PetMushroomCow;
import om.api.nms.PetOcelot;
import om.api.nms.PetPig;
import om.api.nms.PetSheep;
import om.api.nms.PetSilverfish;
import om.api.nms.PetSlime;
import om.api.nms.PetSpider;
import om.api.nms.PetSquid;
import om.api.nms.PetWolf;

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
	
	MUSHROOM_COW, 
	PIG, 
	WOLF, 
	SHEEP, 
	HORSE, 
	MAGMA_CUBE, 
	SLIME, 
	COW, 
	SILVERFISH, 
	OCELOT, 
	CREEPER, 
	SQUID, 
	SPIDER, 
	CHICKEN;
	
	public static List<Entity> pets = new ArrayList<Entity>();
	
	public String getName(){
		switch(this){
			case CHICKEN:
				return "§7Chicken Pet";
			case COW:
				return "§8Cow Pet";
			case CREEPER:
				return "§aCreeper Pet";
			case HORSE:
				return "§6Horse Pet";
			case MAGMA_CUBE:
				return "§cMagma Cube Pet";
			case MUSHROOM_COW:
				return "§4Mushroom Cow Pet";
			case OCELOT:
				return "§eOcelot Pet";
			case PIG:
				return "§dPig Pet";
			case SHEEP:
				return "§fSheep Pet";
			case SILVERFISH:
				return "§7Silverfish Pet";
			case SLIME:
				return "§aSlime Pet";
			case SPIDER:
				return "§8Spider Pet";
			case SQUID:
				return "§9Squid Pet";
			case WOLF:
				return "§7Wolf Pet";
			default:
				return null;
		}
	}
	
	public String getDatabaseName(){
		switch(this){
			case CHICKEN:
				return "Chicken";
			case COW:
				return "Cow";
			case CREEPER:
				return "Creeper";
			case HORSE:
				return "Horse";
			case MAGMA_CUBE:
				return "MagmaCube";
			case MUSHROOM_COW:
				return "MushroomCow";
			case OCELOT:
				return "Ocelot";
			case PIG:
				return "Pig";
			case SHEEP:
				return "Sheep";
			case SILVERFISH:
				return "Silverfish";
			case SLIME:
				return "Slime";
			case SPIDER:
				return "Spider";
			case SQUID:
				return "Squid";
			case WOLF:
				return "Wolf";
			default:
				return null;
		}
	}
	
	public int getPrice(){
		switch(this){
			case CHICKEN:
				return 425;
			case COW:
				return 425;
			case CREEPER:
				return 525;
			case HORSE:
				return 525;
			case MAGMA_CUBE:
				return 500;
			case MUSHROOM_COW:
				return 475;
			case OCELOT:
				return 450;
			case PIG:
				return 425;
			case SHEEP:
				return 425;
			case SILVERFISH:
				return 450;
			case SLIME:
				return 475;
			case SPIDER:
				return 500;
			case SQUID:
				return 600;
			case WOLF:
				return 475;
			default:
				return 0;
		}
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
		switch(this){
			case CHICKEN:
				return 93;
			case COW:
				return 92;
			case CREEPER:
				return 50;
			case HORSE:
				return 100;
			case MAGMA_CUBE:
				return 62;
			case MUSHROOM_COW:
				return 96;
			case OCELOT:
				return 98;
			case PIG:
				return 90;
			case SHEEP:
				return 91;
			case SILVERFISH:
				return 60;
			case SLIME:
				return 55;
			case SPIDER:
				return 52;
			case SQUID:
				return 94;
			case WOLF:
				return 95;
			default:
				return 0;
		}
	}
	
	public void spawn(OMPlayer omp){
		Location l = omp.getPlayer().getLocation();
		
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) l.getWorld()).getHandle();
		
	    switch(this){
			case CHICKEN:
			    PetChicken chicken = new PetChicken(nmsWorld);
			    chicken.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(chicken);
			    chicken.setAge(1);
			    
			    chicken.setCustomName(omp.getPetName(this));
			    chicken.setCustomNameVisible(true);
				
			    omp.setPet(chicken.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case COW:
			    PetCow cow = new PetCow(nmsWorld);
			    cow.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(cow);
			    cow.setAge(1);
			    
			    cow.setCustomName(omp.getPetName(this));
			    cow.setCustomNameVisible(true);
				
			    omp.setPet(cow.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case CREEPER:
			    PetCreeper creeper = new PetCreeper(nmsWorld);
			    creeper.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(creeper);
			    
			    creeper.setCustomName(omp.getPetName(this));
			    creeper.setCustomNameVisible(true);
				
			    omp.setPet(creeper.getBukkitEntity());
				omp.setPetEnabled(this);
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
			    magmacube.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(magmacube);
			    magmacube.setSize(3);
			    
			    magmacube.setCustomName(omp.getPetName(this));
			    magmacube.setCustomNameVisible(true);
				
			    omp.setPet(magmacube.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case MUSHROOM_COW:
			    PetMushroomCow mushroomcow = new PetMushroomCow(nmsWorld);
			    mushroomcow.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(mushroomcow);
			    
			    mushroomcow.setCustomName(omp.getPetName(this));
			    mushroomcow.setCustomNameVisible(true);
			    
			    omp.setPetShroomTrail(false);
				
			    omp.setPet(mushroomcow.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case OCELOT:
			    PetOcelot ocelot = new PetOcelot(nmsWorld);
			    ocelot.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(ocelot);
			    ocelot.setAge(1);

			    ocelot.setCustomName(omp.getPetName(this));
			    ocelot.setCustomNameVisible(true);
				
			    omp.setPet(ocelot.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case PIG:
			    PetPig pig = new PetPig(nmsWorld);
			    pig.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(pig);
			    pig.setAge(1);

			    pig.setCustomName(omp.getPetName(this));
			    pig.setCustomNameVisible(true);
			    
			    omp.setPetBabyPigs(false);
				
			    omp.setPet(pig.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case SHEEP:
			    PetSheep sheep = new PetSheep(nmsWorld);
			    sheep.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(sheep);
			    sheep.setAge(1);
			    
			    sheep.setCustomName(omp.getPetName(this));
			    sheep.setCustomNameVisible(true);
			    
			    omp.setPetSheepDisco(false);
				
			    omp.setPet(sheep.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case SILVERFISH:
			    PetSilverfish silverfish = new PetSilverfish(nmsWorld);
			    silverfish.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(silverfish);

			    silverfish.setCustomName(omp.getPetName(this));
			    silverfish.setCustomNameVisible(true);
				
			    omp.setPet(silverfish.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case SLIME:
			    PetSlime slime = new PetSlime(nmsWorld);
			    slime.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(slime);
			    slime.setSize(3);

			    slime.setCustomName(omp.getPetName(this));
			    slime.setCustomNameVisible(true);
				
			    omp.setPet(slime.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case SPIDER:
			    PetSpider spider = new PetSpider(nmsWorld);
			    spider.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(spider);

			    spider.setCustomName(omp.getPetName(this));
			    spider.setCustomNameVisible(true);
				
			    omp.setPet(spider.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case SQUID:
			    PetSquid squid = new PetSquid(nmsWorld);
			    squid.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(squid);

			    squid.setCustomName(omp.getPetName(this));
			    squid.setCustomNameVisible(true);
				
			    omp.setPet(squid.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			case WOLF:
			    PetWolf wolf = new PetWolf(nmsWorld);
			    wolf.setPosition(l.getX(), l.getY(), l.getZ());
			    nmsWorld.addEntity(wolf);
			    wolf.setAge(1);

			    wolf.setCustomName(omp.getPetName(this));
			    wolf.setCustomNameVisible(true);
				
			    omp.setPet(wolf.getBukkitEntity());
				omp.setPetEnabled(this);
				break;
			default:
				break;
	    }
	}
}

