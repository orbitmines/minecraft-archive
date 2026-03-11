package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R2.BlockLog1;
import net.minecraft.server.v1_8_R2.EntityArmorStand;
import net.minecraft.server.v1_8_R2.EntityBat;
import net.minecraft.server.v1_8_R2.EntityBlaze;
import net.minecraft.server.v1_8_R2.EntityCaveSpider;
import net.minecraft.server.v1_8_R2.EntityChicken;
import net.minecraft.server.v1_8_R2.EntityCow;
import net.minecraft.server.v1_8_R2.EntityCreeper;
import net.minecraft.server.v1_8_R2.EntityEnderDragon;
import net.minecraft.server.v1_8_R2.EntityEnderman;
import net.minecraft.server.v1_8_R2.EntityEndermite;
import net.minecraft.server.v1_8_R2.EntityFallingBlock;
import net.minecraft.server.v1_8_R2.EntityGhast;
import net.minecraft.server.v1_8_R2.EntityGiantZombie;
import net.minecraft.server.v1_8_R2.EntityGuardian;
import net.minecraft.server.v1_8_R2.EntityHorse;
import net.minecraft.server.v1_8_R2.EntityIronGolem;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntityMagmaCube;
import net.minecraft.server.v1_8_R2.EntityMushroomCow;
import net.minecraft.server.v1_8_R2.EntityOcelot;
import net.minecraft.server.v1_8_R2.EntityPig;
import net.minecraft.server.v1_8_R2.EntityPigZombie;
import net.minecraft.server.v1_8_R2.EntityRabbit;
import net.minecraft.server.v1_8_R2.EntitySheep;
import net.minecraft.server.v1_8_R2.EntitySilverfish;
import net.minecraft.server.v1_8_R2.EntitySkeleton;
import net.minecraft.server.v1_8_R2.EntitySlime;
import net.minecraft.server.v1_8_R2.EntitySnowman;
import net.minecraft.server.v1_8_R2.EntitySpider;
import net.minecraft.server.v1_8_R2.EntitySquid;
import net.minecraft.server.v1_8_R2.EntityVillager;
import net.minecraft.server.v1_8_R2.EntityWitch;
import net.minecraft.server.v1_8_R2.EntityWither;
import net.minecraft.server.v1_8_R2.EntityWolf;
import net.minecraft.server.v1_8_R2.EntityZombie;
import net.minecraft.server.v1_8_R2.NBTTagByte;
import net.minecraft.server.v1_8_R2.NBTTagCompound;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R2.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R2.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Utils {
	
	public static Vector getRandomVelocity(){
        Random i = new Random();
        int iInt;
        
        float x = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        float y = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        float z = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        for(int iCount = 1; iCount <= 1; iCount++){
        	iInt = i.nextInt(4);
        	
        	if(iInt == 0){
        		return new Vector(x -0.2, y, z -0.2);
        	}
        	else if(iInt == 1){
        		return new Vector(x, y, z);
        	}
        	else if(iInt == 2){
        		return new Vector(x -0.2, y, z);
        	}
          	else if(iInt == 3){
          		return new Vector(x, y, z -0.2);
        	}
        }
        return null;
	}
	
	public class ComponentMessage {
		
		private List<TextComponent> tcs;
		
		public ComponentMessage(){
			tcs = new ArrayList<TextComponent>();
		}
		
		public void addPart(String part, ClickEvent.Action clickaction, String clickevent, HoverEvent.Action hoveraction, String hoverevent){
			TextComponent tc = new TextComponent(part);
			if(clickaction != null){
				tc.setClickEvent(new ClickEvent(clickaction, clickevent));
			}
			if(hoveraction != null){
				tc.setHoverEvent(new HoverEvent(hoveraction, new ComponentBuilder(hoverevent).create()));
			}
			
			tcs.add(tc);
		}
		
		public void send(Player... players){
			TextComponent tc = new TextComponent((TextComponent[]) this.tcs.toArray());
			for(Player player : players){
				player.spigot().sendMessage(tc);
			}
		}
	}
	
	public enum InventoryEnum {
		
		CONFIRM_PURCHASE;
		
	}
	
	public enum ArmorStandType {

		NORMAL,
		SERVER_INFO_KITPVP,
		SERVER_INFO_PRISON,
		SERVER_INFO_CREATIVE,
		SERVER_INFO_SURVIVAL,
		SERVER_INFO_SKYBLOCK,
		SERVER_INFO_MINIGAMES,
		TOP_DONATOR;
		
	}
	
	public enum ChatColor {

		WHITE,
		DARK_RED,
		BLUE,
		GREEN,
		BLACK,
		LIGHT_BLUE,
		PINK,
		LIGHT_GREEN,
		DARK_BLUE,
		RED,
		DARK_GRAY;
		
	}
	
	public enum Currency {

		ORBITMINES_TOKENS,
		VIP_POINTS,
		MINIGAME_POINTS;
	
	}
	
	public enum Disguise {

		ENDERMAN,
		HORSE,
		IRON_GOLEM,
		GHAST,
		SNOWMAN,
		RABBIT,
		WITCH,
		BAT,
		CHICKEN,
		OCELOT,
		MUSHROOM_COW,
		SQUID,
		SLIME,
		ZOMBIE_PIGMAN,
		MAGMA_CUBE,
		SKELETON,
		COW,
		WOLF,
		SPIDER,
		SILVERFISH,
		SHEEP,
		CAVE_SPIDER,
		CREEPER;
		
		public static void disguiseAsBlock(Player p, int id, Player... players){
			EntityFallingBlock disguise = new EntityFallingBlock(((CraftPlayer) p).getHandle().world, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), new BlockLog1().getBlockData());
			disguise.locX = p.getLocation().getX();
	        disguise.locY = p.getLocation().getY();
	        disguise.locZ = p.getLocation().getZ();
	        disguise.yaw = p.getLocation().getYaw();
	        disguise.pitch = p.getLocation().getPitch();
	        disguise.d(((CraftPlayer) p).getHandle().getId());
	        
	        OMPlayer omp = OMPlayer.getOMPlayer(p);
	        omp.setDisguised(true);
	        omp.setDisguiseBlockID(id);
	        
	        for(Player player : players){
	        	if(player != p){
	        		OMPlayer omplayer = OMPlayer.getOMPlayer(player);
	        		if(omplayer.hasPlayersEnabled()){
	        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy((((CraftPlayer) p).getHandle().getId())));
	        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntity(disguise, 70, id));
	        		}
	        	}
	        }
		}
		
		public static void disguiseAsMob(Player p, EntityType type, Player... players){
		    EntityLiving disguise = null;
		    
		    switch(type) {
	    	case BAT:
	    		disguise = new EntityBat(((CraftPlayer) p).getHandle().world);
		        break;
	        case BLAZE:
	            disguise = new EntityBlaze(((CraftPlayer) p).getHandle().world);
	            break;
			case ARMOR_STAND:
	            disguise = new EntityArmorStand(((CraftPlayer) p).getHandle().world);
				break;
			case ARROW:
				break;
			case BOAT:
				break;
			case CAVE_SPIDER:
				disguise = new EntityCaveSpider(((CraftPlayer) p).getHandle().world);
				break;
			case CHICKEN:
				disguise = new EntityChicken(((CraftPlayer) p).getHandle().world);
				break;
			case COMPLEX_PART:
				break;
			case COW:
				disguise = new EntityCow(((CraftPlayer) p).getHandle().world);
				break;
			case CREEPER:
				disguise = new EntityCreeper(((CraftPlayer) p).getHandle().world);
				break;
			case DROPPED_ITEM:
				break;
			case EGG:
				break;
			case ENDERMAN:
				disguise = new EntityEnderman(((CraftPlayer) p).getHandle().world);
				break;
			case ENDERMITE:
				disguise = new EntityEndermite(((CraftPlayer) p).getHandle().world);
				break;
			case ENDER_CRYSTAL:
				break;
			case ENDER_DRAGON:
				disguise = new EntityEnderDragon(((CraftPlayer) p).getHandle().world);
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
				disguise = new EntityGhast(((CraftPlayer) p).getHandle().world);
				break;
			case GIANT:
				disguise = new EntityGiantZombie(((CraftPlayer) p).getHandle().world);
				break;
			case GUARDIAN:
				disguise = new EntityGuardian(((CraftPlayer) p).getHandle().world);
				break;
			case HORSE:
				disguise = new EntityHorse(((CraftPlayer) p).getHandle().world);
				break;
			case IRON_GOLEM:
				disguise = new EntityIronGolem(((CraftPlayer) p).getHandle().world);
				break;
			case ITEM_FRAME:
				break;
			case LEASH_HITCH:
				break;
			case LIGHTNING:
				break;
			case MAGMA_CUBE:
				disguise = new EntityMagmaCube(((CraftPlayer) p).getHandle().world);
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
				disguise = new EntityMushroomCow(((CraftPlayer) p).getHandle().world);
				break;
			case OCELOT:
				disguise = new EntityOcelot(((CraftPlayer) p).getHandle().world);
				break;
			case PAINTING:
				break;
			case PIG:
				disguise = new EntityPig(((CraftPlayer) p).getHandle().world);
				break;
			case PIG_ZOMBIE:
				disguise = new EntityPigZombie(((CraftPlayer) p).getHandle().world);
				break;
			case PLAYER:
				break;
			case PRIMED_TNT:
				break;
			case RABBIT:
				disguise = new EntityRabbit(((CraftPlayer) p).getHandle().world);
				break;
			case SHEEP:
				disguise = new EntitySheep(((CraftPlayer) p).getHandle().world);
				break;
			case SILVERFISH:
				disguise = new EntitySilverfish(((CraftPlayer) p).getHandle().world);
				break;
			case SKELETON:
				disguise = new EntitySkeleton(((CraftPlayer) p).getHandle().world);
				break;
			case SLIME:
				disguise = new EntitySlime(((CraftPlayer) p).getHandle().world);
				break;
			case SMALL_FIREBALL:
				break;
			case SNOWBALL:
				break;
			case SNOWMAN:
				disguise = new EntitySnowman(((CraftPlayer) p).getHandle().world);
				break;
			case SPIDER:
				disguise = new EntitySpider(((CraftPlayer) p).getHandle().world);
				break;
			case SPLASH_POTION:
				break;
			case SQUID:
				disguise = new EntitySquid(((CraftPlayer) p).getHandle().world);
				break;
			case THROWN_EXP_BOTTLE:
				break;
			case UNKNOWN:
				break;
			case VILLAGER:
				disguise = new EntityVillager(((CraftPlayer) p).getHandle().world);
				break;
			case WEATHER:
				break;
			case WITCH:
				disguise = new EntityWitch(((CraftPlayer) p).getHandle().world);
				break;
			case WITHER:
				disguise = new EntityWither(((CraftPlayer) p).getHandle().world);
				break;
			case WITHER_SKULL:
				break;
			case WOLF:
				disguise = new EntityWolf(((CraftPlayer) p).getHandle().world);
				break;
			case ZOMBIE:
				disguise = new EntityZombie(((CraftPlayer) p).getHandle().world);
				break;
			default:
				break;
		    }
		    
		    if(disguise != null){
		        disguise.locX = p.getLocation().getX();
		        disguise.locY = p.getLocation().getY();
		        disguise.locZ = p.getLocation().getZ();
		        disguise.yaw = p.getLocation().getYaw();
		        disguise.pitch = p.getLocation().getPitch();
		        disguise.d(((CraftPlayer) p).getHandle().getId());
		        
		        OMPlayer omp = OMPlayer.getOMPlayer(p);
		        omp.setDisguised(true);
		        omp.setDisguiseEntityType(type);
		        
		        for(Player player : players){
		        	if(player != p){
		        		OMPlayer omplayer = OMPlayer.getOMPlayer(player);
		        		if(omplayer.hasPlayersEnabled()){
		        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy((((CraftPlayer) p).getHandle().getId())));
		        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(disguise));
		        		}
		        	}
		        }
		    }
		}
	}
	
	public enum Gadget {

		STACKER, 
		PAINTBALLS, 
		CREEPER_LAUNCHER, 
		PET_RIDE, 
		BOOK_EXPLOSION, 
		SWAP_TELEPORTER, 
		FIREWORK_GUN, 
		MAGMACUBE_SOCCER,
		SNOWMAN_ATTACK;
		
	}
	
	public enum Hat {

		STONE_BRICKS,
		GREEN_GLASS,
		CACTUS,
		SNOW,
		TNT,
		COAL_ORE,
		BLACK_GLASS,
		FURNACE,
		QUARTZ_ORE,
		HAY_BALE,
		REDSTONE_ORE,
		ICE,
		WORKBENCH,
		GRASS,
		RED_GLASS,
		BEDROCK,
		LAPIS_ORE,
		REDSTONE_BLOCK,
		QUARTZ_BLOCK,
		LAPIS_BLOCK,
		MAGENTA_GLASS,
		COAL_BLOCK,
		MELON,
		GLASS,
		YELLOW_GLASS,
		MYCELIUM,
		LEAVES,
		ORANGE_GLASS,
		
		DIORITE,
		DARK_PRISMARINE,
		SLIME_BLOCK,
		GRANITE,
		SEA_LANTERN,
		PRISMARINE_BRICKS,
		SPONGE,
		CHEST,
		GLOWSTONE,
		WET_SPONGE,
		ANDESITE,
		BLUE_GLASS,
		
		IRON_ORE,
		IRON_BLOCK,
		GOLD_ORE,
		GOLD_BLOCK,
		DIAMOND_ORE,
		DIAMOND_BLOCK,
		EMERALD_ORE,
		EMERALD_BLOCK;
		
	}
	
	public enum Pet {

		NONE,
		MUSHROOMCOW, 
		PIG, 
		WOLF, 
		SHEEP, 
		HORSE, 
		MAGMACUBE, 
		SLIME, 
		COW, 
		SILVERFISH, 
		OCELOT, 
		CREEPER, 
		SQUID, 
		SPIDER, 
		CHICKEN;
		
	}
	
	public enum Server {

		KITPVP, 
		PRISON, 
		CREATIVE, 
		HUB, 
		SURVIVAL, 
		SKYBLOCK, 
		MINIGAMES, 
		ALPHA;
		
		public String getIP(){
			switch(this){
				case ALPHA:
					return "77.111.240.219";
				case CREATIVE:
					return "77.111.200.65";
				case HUB:
					return "77.111.240.219";
				case KITPVP:
					return "77.111.248.172"; 
				case MINIGAMES:
					return "77.111.204.209";
				case PRISON:
					return "77.111.236.34";
				case SKYBLOCK:
					return "77.111.192.151";
				case SURVIVAL:
					return "77.111.206.117";
				default:
					return null;
			}
		}
	}
	
	public enum StaffRank {

		User,
		Builder,
		Moderator,
		Owner;
		
	}
	
	public enum Trail {

		FIREWORK_SPARK,
		HAPPY_VILLAGER,
		HEART,
		TNT,
		MAGIC,
		ANGRY_VILLAGER,
		LAVA,
		SLIME,
		SMOKE,
		WITCH,
		CRIT,
		WATER,
		MUSIC,
		SNOW,
		ENCHANTMENT_TABLE,
		RAINBOW,
		BUBBLE,
		MOB_SPAWNER;
		
	}
	
	public enum TrailType {

		BASIC_TRAIL,
		GROUND_TRAIL,
		HEAD_TRAIL,
		BODY_TRAIL,
		BIG_TRAIL,
		VERTICAL_TRAIL;
		
	}
	
	public enum VIPRank {

		User,
		Iron_VIP,
		Gold_VIP,
		Diamond_VIP,
		Emerald_VIP;
		
	}
	
	public static class ReflectionUtil {
	    public static Object getClass(String name, Object... args) throws Exception{
	        Class<?> c = Class.forName(ReflectionUtil.getPackageName() + "." + name);
	        int params = 0;
	        if(args != null){
	            params = args.length;
	        }
	        for(Constructor<?> co : c.getConstructors()){
	            if (co.getParameterTypes().length == params){
	                return co.newInstance(args);
	            }
	        }
	        return null;
	    }
	 
	    public static Method getMethod(String name, Class<?> c, int params){
	        for(Method m : c.getMethods()){
	            if(m.getName().equals(name) && m.getParameterTypes().length == params){
	                return m;
	            }
	        }
	        return null;
	    }
	 
	    public static void setValue(Object instance, String fieldName, Object value) throws Exception{
	        Field field = instance.getClass().getDeclaredField(fieldName);
	        field.setAccessible(true);
	        field.set(instance, value);
	    }
	 
	    public static String getPackageName(){
	        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	    }
	}
	
	public static class CustomItem {

	    public static ItemStack hideFlags(ItemStack item, int... hideflags){
		    try{
		        net.minecraft.server.v1_8_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		
		        NBTTagCompound tag = new NBTTagCompound();
		        
		        if(nmsStack.hasTag()){
		        	tag = nmsStack.getTag();
		        }
		        
		        int hide = 0;
		        
		        for(int i : hideflags){
		        	hide = hide + i;
		        }
		        
		        if(hide != 0){
		        	tag.set("HideFlags", new NBTTagByte((byte) hide));
		    		nmsStack.setTag(tag);
		        }
		       
		        return CraftItemStack.asCraftMirror(nmsStack);
	    	}catch(NullPointerException ex){}
	    	return item;
	    }
	    
	    public static ItemStack setUnbreakable(ItemStack item){
	    	try{
	    		net.minecraft.server.v1_8_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

		        NBTTagCompound tag = new NBTTagCompound();
		        
		        if(nmsStack.hasTag()){
		        	tag = nmsStack.getTag();
		        }
		     
		        tag.set("Unbreakable", new NBTTagByte((byte) 1));
		    	nmsStack.setTag(tag);
		       
		        return CraftItemStack.asCraftMirror(nmsStack);
	    	}catch(NullPointerException ex){}
	    	return item;
	    }
	}
}
