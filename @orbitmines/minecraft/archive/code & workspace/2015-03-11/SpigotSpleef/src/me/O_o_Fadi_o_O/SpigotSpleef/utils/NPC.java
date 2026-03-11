package me.O_o_Fadi_o_O.SpigotSpleef.utils;

import me.O_o_Fadi_o_O.SpigotSpleef.Start;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomBlaze;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftBlaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class NPC {

	private NPCType npctype;
	private String displayname;
	private Entity entity;
	private Item item;
	private Kit kit;
	
	public NPC(NPCType npctype, String displayname, Entity entity, Item item, Kit kit){
		this.npctype = npctype;
		this.displayname = displayname;
		this.entity = entity;
		this.item = item;
		this.kit = kit;
		
		new BukkitRunnable(){
			public void run(){
				if(!getEntity().isDead()){
					checkEntity();
				}
			}
		}.runTaskTimer(Start.getInstance(), 0, 3);
	}

	public NPCType getNPCType(){
		return this.npctype;
	}
	public void setNPCType(NPCType npctype){
		this.npctype = npctype;
	}
	
	public String getDisplayname() {
		return this.displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public boolean isNPCType(NPCType npctype){
		if(getNPCType() == npctype){
			return true;
		}
		return false;
	}

	public Entity getEntity(){
		return this.entity;
	}
	public void setEntity(Entity entity){
		this.entity = entity;
	}

	public Item getItem(){
		return this.item;
	}
	public void setItem(Item item){
		this.item = item;
	}

	public Kit getKit(){
		return this.kit;
	}
	public void setKit(Kit kit){
		this.kit = kit;
	}
	
	public void checkEntity(){
	    if(getEntity().getPassenger() == null){
		    Item iEn = getDisplaynameItem(getEntity().getWorld(), getEntity().getLocation(), getEntity().getCustomName());
		    
		    getEntity().setPassenger(iEn);
		    setItem(iEn);
	    }
	}
	
	public void newEntity(EntityType entitytype, Location location, String displayname){
		World w = location.getWorld();
		
		switch(entitytype){
		case ARMOR_STAND:
			break;
		case ARROW:
			break;
		case BAT:
			break;
		case BLAZE:
		    spawnBlaze(w, location, displayname);
			break;
		case BOAT:
			break;
		case CAVE_SPIDER:
			break;
		case CHICKEN:
			break;
		case COMPLEX_PART:
			break;
		case COW:
			break;
		case CREEPER:
			break;
		case DROPPED_ITEM:
			break;
		case EGG:
			break;
		case ENDERMAN:
			break;
		case ENDERMITE:
			break;
		case ENDER_CRYSTAL:
			break;
		case ENDER_DRAGON:
			break;
		case ENDER_PEARL:
			break;
		case ENDER_SIGNAL:
			break;
		case EXPERIENCE_ORB:
			break;
		case FALLING_BLOCK:
			break;
		case FIREBALL:
			break;
		case FIREWORK:
			break;
		case FISHING_HOOK:
			break;
		case GHAST:
			break;
		case GIANT:
			break;
		case GUARDIAN:
			break;
		case HORSE:
			break;
		case IRON_GOLEM:
			break;
		case ITEM_FRAME:
			break;
		case LEASH_HITCH:
			break;
		case LIGHTNING:
			break;
		case MAGMA_CUBE:
			break;
		case MINECART:
			break;
		case MINECART_CHEST:
			break;
		case MINECART_COMMAND:
			break;
		case MINECART_FURNACE:
			break;
		case MINECART_HOPPER:
			break;
		case MINECART_MOB_SPAWNER:
			break;
		case MINECART_TNT:
			break;
		case MUSHROOM_COW:
			break;
		case OCELOT:
			break;
		case PAINTING:
			break;
		case PIG:
			break;
		case PIG_ZOMBIE:
			break;
		case PLAYER:
			break;
		case PRIMED_TNT:
			break;
		case RABBIT:
			break;
		case SHEEP:
			break;
		case SILVERFISH:
			break;
		case SKELETON:
			break;
		case SLIME:
			break;
		case SMALL_FIREBALL:
			break;
		case SNOWBALL:
			break;
		case SNOWMAN:
			break;
		case SPIDER:
			break;
		case SPLASH_POTION:
			break;
		case SQUID:
			break;
		case THROWN_EXP_BOTTLE:
			break;
		case UNKNOWN:
			break;
		case VILLAGER:
			break;
		case WEATHER:
			break;
		case WITCH:
			break;
		case WITHER:
			break;
		case WITHER_SKULL:
			break;
		case WOLF:
			break;
		case ZOMBIE:
			break;
		default:
			break;
		
		}
	}
	
	public Item getDisplaynameItem(World w, Location location, String displayname){
	    ItemStack item = new ItemStack(Material.WOOD_BUTTON);
	    final Item iEn = w.dropItem(location, item);
	    iEn.setPickupDelay(Integer.MAX_VALUE);
	    iEn.setCustomName(displayname);
	    iEn.setCustomNameVisible(true);
	    
	    return iEn;
	}
	
	public void spawnBlaze(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R2.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomBlaze e = new CustomBlaze(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    e.setCustomName(displayname);
	    e.setCustomNameVisible(true);
	    Item iEn = getDisplaynameItem(w, location, displayname);
	    e.getBukkitEntity().setPassenger(iEn);
	    ((CraftBlaze) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		setItem(iEn);
	}
}
