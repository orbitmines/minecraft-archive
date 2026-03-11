package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.O_o_Fadi_o_O.OMHub.NameFetcher;
import me.O_o_Fadi_o_O.OMHub.UUIDFetcher;
import me.O_o_Fadi_o_O.OMHub.NMS.PetChicken;
import me.O_o_Fadi_o_O.OMHub.NMS.PetCow;
import me.O_o_Fadi_o_O.OMHub.NMS.PetCreeper;
import me.O_o_Fadi_o_O.OMHub.NMS.PetMagmaCube;
import me.O_o_Fadi_o_O.OMHub.NMS.PetMushroomCow;
import me.O_o_Fadi_o_O.OMHub.NMS.PetOcelot;
import me.O_o_Fadi_o_O.OMHub.NMS.PetPig;
import me.O_o_Fadi_o_O.OMHub.NMS.PetSheep;
import me.O_o_Fadi_o_O.OMHub.NMS.PetSilverfish;
import me.O_o_Fadi_o_O.OMHub.NMS.PetSlime;
import me.O_o_Fadi_o_O.OMHub.NMS.PetSpider;
import me.O_o_Fadi_o_O.OMHub.NMS.PetSquid;
import me.O_o_Fadi_o_O.OMHub.NMS.PetWolf;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R2.AttributeInstance;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.NBTTagByte;
import net.minecraft.server.v1_8_R2.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

public class Utils {
	
	public static UUID getUUID(String playername){
		UUIDFetcher uuidf = new UUIDFetcher(Arrays.asList(playername));
		try{
			return uuidf.call().get(playername);
		}catch(Exception ex){
			return null;
		}
	}
	public static String getName(UUID uuid){
		NameFetcher namef = new NameFetcher(Arrays.asList(uuid));
		try{
			return namef.call().get(uuid);
		}catch(Exception ex){
			return null;
		}
	}
	
	public static void removeAllEntities(){
		for(World world : Bukkit.getWorlds()){
			for(Entity en : world.getEntities()){
				if(!(en instanceof Player)){
					en.remove();
				}
			}
		}
	}
	
	public static void removeEntities(World world){
		for(Entity en : world.getEntities()){
			if(!(en instanceof Player)){
				en.remove();
			}
		}
	}
	
	public static String statusString(boolean bl){
		if(bl == true){
			return "§a§lENABLED";
		}
		return "§c§lDISABLED";
	}
	public static short statusDurability(boolean bl){
		if(bl == true){
			return 5;
		}
		return 14;
	}
	
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
	
	public static List<Color> getWardrobeColors(){
		return Arrays.asList(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.NAVY, Color.ORANGE, Color.PURPLE, Color.RED, Color.TEAL, Color.WHITE, Color.YELLOW);
	}
	
	public static String getColor(Color color){
		if(color == Color.AQUA){ return "§b";}
		else if(color == Color.BLACK){ return "§0";}
		else if(color == Color.BLUE){ return "§9";}
		else if(color == Color.FUCHSIA){ return "§d";}
		else if(color == Color.GRAY){ return "§8";}
		else if(color == Color.GREEN){ return "§2";}
		else if(color == Color.LIME){ return "§a";}
		else if(color == Color.MAROON){ return "§4";}
		else if(color == Color.NAVY){ return "§1";}
		else if(color == Color.ORANGE){ return "§6";}
		else if(color == Color.PURPLE){ return "§5";}
		else if(color == Color.RED){ return "§c";}
		else if(color == Color.SILVER){ return "§7";}
		else if(color == Color.TEAL){ return "§3";}
		else if(color == Color.WHITE){ return "§f";}
		else if(color == Color.YELLOW){ return "§e";}
		else{ return "";}
	}
	
	public static String getColorName(Color color){
		if(color == Color.AQUA){ return "§bLight Blue";}
		else if(color == Color.BLACK){ return "§0Black";}
		else if(color == Color.BLUE){ return "§9Blue";}
		else if(color == Color.FUCHSIA){ return "§dPink";}
		else if(color == Color.GRAY){ return "§8Dark Gray";}
		else if(color == Color.GREEN){ return "§2Green";}
		else if(color == Color.LIME){ return "§aLight Green";}
		else if(color == Color.MAROON){ return "§4Dark Red";}
		else if(color == Color.NAVY){ return "§1Dark Blue";}
		else if(color == Color.ORANGE){ return "§6Orange";}
		else if(color == Color.PURPLE){ return "§5Purple";}
		else if(color == Color.RED){ return "§cRed";}
		else if(color == Color.SILVER){ return "§7Gray";}
		else if(color == Color.TEAL){ return "§3Cyan";}
		else if(color == Color.WHITE){ return "§fWhite";}
		else if(color == Color.YELLOW){ return "§eYellow";}
		else{ return "";}
	}
	
	public static int getColorPrice(Color color){
		return 250;
	}
	
	public static String getColorPriceName(Color color){
		return "§cPrice: §b" + getColorPrice(color) + " VIP Points";
	}
	
	public static short getColorDurability(Color color){
		if(color == Color.AQUA){ return 12;}
		else if(color == Color.BLACK){ return 0;}
		else if(color == Color.BLUE){ return 4;}
		else if(color == Color.FUCHSIA){ return 9;}
		else if(color == Color.GRAY){ return 8;}
		else if(color == Color.GREEN){ return 2;}
		else if(color == Color.LIME){ return 10;}
		else if(color == Color.MAROON){ return 0;} //REDSTONE
		else if(color == Color.NAVY){ return 12;}
		else if(color == Color.ORANGE){ return 14;}
		else if(color == Color.PURPLE){ return 5;}
		else if(color == Color.RED){ return 1;}
		else if(color == Color.SILVER){ return 7;}
		else if(color == Color.TEAL){ return 6;}
		else if(color == Color.WHITE){ return 15;}
		else if(color == Color.YELLOW){ return 11;}
		else{ return 0;}
	}
	
	public static Color getRandomColor(List<Color> colors){
		return colors.get(new Random().nextInt(colors.size()));
	}
	
	public static Color parseColor(String color){
		if(color.equals("AQUA")){ return Color.AQUA;}
		else if(color.equals("BLACK")){ return Color.BLACK;}
		else if(color.equals("BLUE")){ return Color.BLUE;}
		else if(color.equals("FUCHSIA")){ return Color.FUCHSIA;}
		else if(color.equals("GRAY")){ return Color.GRAY;}
		else if(color.equals("GREEN")){ return Color.GREEN;}
		else if(color.equals("LIME")){ return Color.LIME;}
		else if(color.equals("MAROON")){ return Color.MAROON;}
		else if(color.equals("NAVY")){ return Color.NAVY;}
		else if(color.equals("OLIVE")){ return Color.OLIVE;}
		else if(color.equals("ORANGE")){ return Color.ORANGE;}
		else if(color.equals("PURPLE")){ return Color.PURPLE;}
		else if(color.equals("RED")){ return Color.RED;}
		else if(color.equals("SILVER")){ return Color.SILVER;}
		else if(color.equals("TEAL")){ return Color.TEAL;}
		else if(color.equals("WHITE")){ return Color.WHITE;}
		else if(color.equals("YELLOW")){ return Color.YELLOW;}
		else{ return null;}
	}
	
	public static String parseString(Color color){
		if(color == Color.AQUA){ return "AQUA";}
		else if(color == Color.BLACK){ return "BLACK";}
		else if(color == Color.BLUE){ return "BLUE";}
		else if(color == Color.FUCHSIA){ return "FUCHSIA";}
		else if(color == Color.GRAY){ return "GRAY";}
		else if(color == Color.GREEN){ return "GREEN";}
		else if(color == Color.LIME){ return "LIME";}
		else if(color == Color.MAROON){ return "MAROON";}
		else if(color == Color.NAVY){ return "NAVY";}
		else if(color == Color.OLIVE){ return "OLIVE";}
		else if(color == Color.ORANGE){ return "ORANGE";}
		else if(color == Color.PURPLE){ return "PURPLE";}
		else if(color == Color.RED){ return "RED";}
		else if(color == Color.SILVER){ return "SILVER";}
		else if(color == Color.TEAL){ return "TEAL";}
		else if(color == Color.WHITE){ return "WHITE";}
		else if(color == Color.YELLOW){ return "YELLOW";}
		else{ return null;}
	}
	
	@SuppressWarnings("deprecation")
	public static DyeColor getNextDyeColor(Sheep s){
		int DyeInt = s.getColor().getDyeData();
		
		if(DyeInt == 15){
			DyeInt = 0;
		}
		else{
			DyeInt = DyeInt +1;
		}
		
		return DyeColor.getByDyeData((byte) DyeInt);
	}
	
	public static Material getTypeMaterial(Type type){
		switch(type){
			case BALL:
				return Material.FIREWORK_CHARGE;
			case BALL_LARGE:
				return Material.FIREBALL;
			case BURST:
				return Material.TNT;
			case CREEPER:
				return Material.SKULL_ITEM;
			case STAR:
				return Material.NETHER_STAR;
			default:
				return Material.FIREWORK_CHARGE;
		}
	}
	
	public static short getTypeDurability(Type type){
		switch(type){
			case BALL:
				return 2;
			case BALL_LARGE:
				return 0;
			case BURST:
				return 0;
			case CREEPER:
				return 4;
			case STAR:
				return 0;
			default:
				return 2;
		}
	}
	
	public static String getTypeString(Type type){
		switch(type){
			case BALL:
				return "§e§lSmall";
			case BALL_LARGE:
				return "§6§lLarge";
			case BURST:
				return "§c§lSpecial";
			case CREEPER:
				return "§a§lCreeper";
			case STAR:
				return "§f§lStar";
			default:
				return "§e§lSmall";
		}
	}
	
	public static String getDyeColorName(DyeColor dyecolor){
		switch(dyecolor){
			case BLACK:
				return "§0§lBlack";
			case BLUE:
				return "§1§lBlue";
			case BROWN:
				return "§f§lBrown";
			case CYAN:
				return "§3§lCyan";
			case GRAY:
				return "§8§lGray";
			case GREEN:
				return "§2§lGreen";
			case LIGHT_BLUE:
				return "§b§lLight Blue";
			case LIME:
				return "§a§lLight Green";
			case MAGENTA:
				return "§d§lMagenta";
			case ORANGE:
				return "§6§lOrange";
			case PINK:
				return "§d§lPink";
			case PURPLE:
				return "§5§lPurple";
			case RED:
				return "§c§lRed";
			case SILVER:
				return "§7§lLight Gray";
			case WHITE:
				return "§f§lWhite";
			case YELLOW:
				return "§e§lYellow";
			default:
				return null;
		}
	}
	
	public static ItemStack getSkull(String playername){
		ItemStack item = new ItemStack(Material.SKULL_ITEM);
		item.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(playername);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack addColor(ItemStack item, Color color){
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack setDisplayname(ItemStack item, String displayname){
		ItemMeta meta = (ItemMeta) item.getItemMeta();
		meta.setDisplayName(displayname);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack setDurability(ItemStack item, int durability){
		item.setDurability((short) durability);
		
		return item;
	}
	
	public static ItemStack addEnchantment(ItemStack item, Enchantment enchantment, int level){
		item.addUnsafeEnchantment(enchantment, level);
		
		return item;
	}
	
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
	
    public static List<Block> getBlocksBetween(Location l1, Location l2){
        List<Block> blocks = new ArrayList<Block>();
 
        int topBlockX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int bottomBlockX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
 
        int topBlockY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int bottomBlockY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
 
        int topBlockZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        int bottomBlockZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
 
        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++){
                for(int y = bottomBlockY; y <= topBlockY; y++){
                    Block block = l1.getWorld().getBlockAt(x, y, z);
                   
                    blocks.add(block);
                }
            }
        }
       
        return blocks;
    }
	
	public static class ComponentMessage {
		
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
		
		CONFIRM_PURCHASE,
		CHATCOLORS,
		COSMETIC_PERKS,
		DISGUISES,
		FIREWORKS,
		GADGETS,
		HATS,
		PETS,
		SERVER_SELECTOR,
		SETTINGS,
		TRAILS,
		WARDROBE;
		
	}
	
	public enum NPCType {

		NORMAL,
		LAPIS_PARKOUR,
		MINDCRAFT,
		ALPHA,
		SERVER_SELECTOR,
		SERVER_INFO_KITPVP,
		SERVER_INFO_PRISON,
		SERVER_INFO_CREATIVE,
		SERVER_INFO_SURVIVAL,
		SERVER_INFO_SKYBLOCK,
		SERVER_INFO_MINIGAMES,
		TOP_DONATOR;
		
	}
	
	public enum ChatColor {

		DARK_RED,
		LIGHT_GREEN,
		DARK_GRAY,
		RED,
		YELLOW,
		WHITE,
		LIGHT_BLUE,
		PINK,
		BLUE,
		DARK_BLUE,
		GRAY,
		ORANGE,
		PURPLE,
		CYAN,
		GREEN,
		BLACK;
		
		public String getColor(){
			switch(this){
				case BLACK:
					return "§0";
				case BLUE:
					return "§9";
				case CYAN:
					return "§3";
				case DARK_BLUE:
					return "§1";
				case DARK_GRAY:
					return "§8";
				case DARK_RED:
					return "§4";
				case GRAY:
					return "§7";
				case GREEN:
					return "§2";
				case LIGHT_BLUE:
					return "§b";
				case LIGHT_GREEN:
					return "§a";
				case ORANGE:
					return "§6";
				case PINK:
					return "§d";
				case PURPLE:
					return "§5";
				case RED:
					return "§c";
				case WHITE:
					return "§f";
				case YELLOW:
					return "§e";
				default:
					return null;
			}
		}
			
		public String getName(){
			switch(this){
				case BLACK:
					return "§0Black ChatColor";
				case BLUE:
					return "§9Blue ChatColor";
				case CYAN:
					return "§3Cyan ChatColor";
				case DARK_BLUE:
					return "§1Dark Blue ChatColor";
				case DARK_GRAY:
					return "§8Dark Gray ChatColor";
				case DARK_RED:
					return "§4Dark Red ChatColor";
				case GRAY:
					return "§7Gray ChatColor";
				case GREEN:
					return "§2Green ChatColor";
				case LIGHT_BLUE:
					return "§bLight Blue ChatColor";
				case LIGHT_GREEN:
					return "§aLight Green ChatColor";
				case ORANGE:
					return "§6Orange ChatColor";
				case PINK:
					return "§dPink ChatColor";
				case PURPLE:
					return "§5Purple ChatColor";
				case RED:
					return "§cRed ChatColor";
				case WHITE:
					return "§fWhite ChatColor";
				case YELLOW:
					return "§eYellow ChatColor";
				default:
					return null;
			}
		}
		
		public int getPrice(){
			switch(this){
				case BLACK:
					return 200;
				case BLUE:
					return 475;
				case CYAN:
					return 0;
				case DARK_BLUE:
					return 350;
				case DARK_GRAY:
					return 250;
				case DARK_RED:
					return 475;
				case GRAY:
					return 0;
				case GREEN:
					return 475;
				case LIGHT_BLUE:
					return 700;
				case LIGHT_GREEN:
					return 575;
				case ORANGE:
					return 0;
				case PINK:
					return 525;
				case PURPLE:
					return 0;
				case RED:
					return 650;
				case WHITE:
					return 500;
				case YELLOW:
					return 0;
				default:
					return 0;
			}
		}
		
		public String getPriceName(){
			switch(this){
				case CYAN:
					return "§cRequired: §b§lDiamond VIP";
				case ORANGE:
					return "§cRequired: §a§lEmerald VIP";
				case PURPLE:
					return "§cRequired: §7§lIron VIP";
				case YELLOW:
					return "§cRequired: §6§lGold VIP";
				default:
					return "§cPrice: §b" + getPrice() + " VIP Points";
			}
		}
		
		public boolean hasChatColor(OMPlayer omp){
			switch(this){
				case GRAY:
					return true;
				case CYAN:
					return omp.hasPerms(VIPRank.Diamond_VIP);
				case ORANGE:
					return omp.hasPerms(VIPRank.Emerald_VIP);
				case PURPLE:
					return omp.hasPerms(VIPRank.Iron_VIP);
				case YELLOW:
					return omp.hasPerms(VIPRank.Gold_VIP);
				default:
					return omp.hasChatColor(this);
			}
		}
		
		public Material getMaterial(){
			switch(this){
				case DARK_RED:
					return Material.REDSTONE;
				default:
					return Material.INK_SACK;
			}
		}
		
		public short getDurability(){
			switch(this){
				case BLACK:
					return 0;
				case BLUE:
					return 12;
				case CYAN:
					return 6;
				case DARK_BLUE:
					return 4;
				case DARK_GRAY:
					return 8;
				case DARK_RED:
					// Redstone
					return 0;
				case GRAY:
					return 7;
				case GREEN:
					return 2;
				case LIGHT_BLUE:
					return 12;
				case LIGHT_GREEN:
					return 10;
				case ORANGE:
					return 14;
				case PINK:
					return 9;
				case PURPLE:
					return 5;
				case RED:
					return 1;
				case WHITE:
					return 15;
				case YELLOW:
					return 11;
				default:
					return 0;
			}
		}
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
		CREEPER,
		PIG,
		BLAZE,
		ZOMBIE,
		VILLAGER;
		
		public String getName(){
			switch(this){
				case BAT:
					return "§8Bat Disguise";
				case BLAZE:
					return "§6Blaze Disguise";
				case CAVE_SPIDER:
					return "§3Cave Spider Disguise";
				case CHICKEN:
					return "§fChicken Disguise";
				case COW:
					return "§8Cow Disguise";
				case CREEPER:
					return "§aCreeper Disguise";
				case ENDERMAN:
					return "§8Enderman Disguise";
				case GHAST:
					return "§7Ghast Disguise";
				case HORSE:
					return "§6Horse Disguise";
				case IRON_GOLEM:
					return "§fIron Golem Disguise";
				case MAGMA_CUBE:
					return "§cMagma Cube Disguise";
				case MUSHROOM_COW:
					return "§4Mushroom Cow Disguise";
				case OCELOT:
					return "§eOcelot Disguise";
				case PIG:
					return "§dPig Disguise";
				case RABBIT:
					return "§6Rabbit Disguise";
				case SHEEP:
					return "§fSheep Disguise";
				case SILVERFISH:
					return "§7Silverfish Disguise";
				case SKELETON:
					return "§7Skeleton Disguise";
				case SLIME:
					return "§aSlime Disguise";
				case SNOWMAN:
					return "§fSnowman Disguise";
				case SPIDER:
					return "§8Spider Disguise";
				case SQUID:
					return "§9Squid Disguise";
				case VILLAGER:
					return "§6Villager Disguise";
				case WITCH:
					return "§2Witch Disguise";
				case WOLF:
					return "§7Wolf Disguise";
				case ZOMBIE:
					return "§2Zombie Disguise";
				case ZOMBIE_PIGMAN:
					return "§dZombie Pigman Disguise";
				default:
					return null;
			}
		}

		public int getPrice(){
			switch(this){
				case BAT:
					return 400;
				case BLAZE:
					return 0;
				case CAVE_SPIDER:
					return 400;
				case CHICKEN:
					return 325;
				case COW:
					return 350;
				case CREEPER:
					return 475;
				case ENDERMAN:
					return 500;
				case GHAST:
					return 500;
				case HORSE:
					return 475;
				case IRON_GOLEM:
					return 575;
				case MAGMA_CUBE:
					return 475;
				case MUSHROOM_COW:
					return 350;
				case OCELOT:
					return 375;
				case PIG:
					return 0;
				case RABBIT:
					return 650;
				case SHEEP:
					return 375;
				case SILVERFISH:
					return 475;
				case SKELETON:
					return 500;
				case SLIME:
					return 425;
				case SNOWMAN:
					return 0;
				case SPIDER:
					return 375;
				case SQUID:
					return 500;
				case VILLAGER:
					return 0;
				case WITCH:
					return 450;
				case WOLF:
					return 400;
				case ZOMBIE:
					return 0;
				case ZOMBIE_PIGMAN:
					return 400;
				default:
					return 0;
			}
		}
		
		public boolean hasDisguise(OMPlayer omp){
			switch(this){
				case BLAZE:
					return omp.hasPerms(VIPRank.Emerald_VIP);
				case PIG:
					return omp.hasPerms(VIPRank.Iron_VIP);
				case VILLAGER:
					return omp.hasPerms(VIPRank.Diamond_VIP);
				case ZOMBIE:
					return omp.hasPerms(VIPRank.Gold_VIP);
				default:
					return omp.hasDisguise(this);
			}
		}
		
		public String getPriceName(){
			switch(this){
				case BLAZE:
					return "§cRequired: §a§lEmerald VIP";
				case PIG:
					return "§cRequired: §7§lIron VIP";
				case SNOWMAN:
					return "§aAchieved at Christmas 2014";
				case VILLAGER:
					return "§cRequired: §b§lDiamond VIP";
				case ZOMBIE:
					return "§cRequired: §6§lGold VIP";
				default:
					return "§cPrice: §b" + getPrice() + " VIP Points";
			}
		}
		
		public Material getMaterial(){
			return Material.MONSTER_EGG;
		}
		
		public short getDurability(){
			switch(this){
				case BAT:
					return 65;
				case BLAZE:
					return 61;
				case CAVE_SPIDER:
					return 59;
				case CHICKEN:
					return 93;
				case COW:
					return 92;
				case CREEPER:
					return 50;
				case ENDERMAN:
					return 58;
				case GHAST:
					return 56;
				case HORSE:
					return 100;
				case IRON_GOLEM:
					return 99;
				case MAGMA_CUBE:
					return 62;
				case MUSHROOM_COW:
					return 96;
				case OCELOT:
					return 98;
				case PIG:
					return 90;
				case RABBIT:
					return 101;
				case SHEEP:
					return 91;
				case SILVERFISH:
					return 60;
				case SKELETON:
					return 51;
				case SLIME:
					return 55;
				case SNOWMAN:
					return 97;
				case SPIDER:
					return 52;
				case SQUID:
					return 94;
				case VILLAGER:
					return 120;
				case WITCH:
					return 66;
				case WOLF:
					return 95;
				case ZOMBIE:
					return 54;
				case ZOMBIE_PIGMAN:
					return 57;
				default:
					return 0;
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
		
		public String getName(){
			switch(this){
				case BOOK_EXPLOSION:
					return "§7§lBook Explosion";
				case CREEPER_LAUNCHER:
					return "§a§lCreeper Launcher";
				case FIREWORK_GUN:
					return "§c§lFirework Gun";
				case MAGMACUBE_SOCCER:
					return "§c§lMagmaCube Soccer";
				case PAINTBALLS:
					return "§f§lPaintballs";
				case PET_RIDE:
					return "§e§lPet Ride";
				case SNOWMAN_ATTACK:
					return "§6§lSnowman Attack";
				case STACKER:
					return "§6§lStacker";
				case SWAP_TELEPORTER:
					return "§2§lSwap Teleporter";
				default:
					return null;
			}
		}
		
		public int getPrice(){
			switch(this){
				case BOOK_EXPLOSION:
					return 475;
				case CREEPER_LAUNCHER:
					return 525;
				case FIREWORK_GUN:
					return 0;
				case MAGMACUBE_SOCCER:
					return 1000;
				case PAINTBALLS:
					return 700;
				case PET_RIDE:
					return 500;
				case SNOWMAN_ATTACK:
					return 1200;
				case STACKER:
					return 0;
				case SWAP_TELEPORTER:
					return 500;
				default:
					return 0;
			}
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
			switch(this){
				case BOOK_EXPLOSION:
					return Material.BOOK;
				case CREEPER_LAUNCHER:
					return Material.SKULL_ITEM;
				case FIREWORK_GUN:
					return Material.FIREBALL;
				case MAGMACUBE_SOCCER:
					return Material.MAGMA_CREAM;
				case PAINTBALLS:
					return Material.SNOW_BALL;
				case PET_RIDE:
					return Material.SADDLE;
				case SNOWMAN_ATTACK:
					return Material.PUMPKIN;
				case STACKER:
					return Material.LEASH;
				case SWAP_TELEPORTER:
					return Material.EYE_OF_ENDER;
				default:
					return null;
			}
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
		
		public boolean hasHat(OMPlayer omp){
			switch(this){
				case DIAMOND_BLOCK:
					return omp.hasPerms(VIPRank.Diamond_VIP);
				case DIAMOND_ORE:
					return omp.hasPerms(VIPRank.Diamond_VIP);
				case EMERALD_BLOCK:
					return omp.hasPerms(VIPRank.Emerald_VIP);
				case EMERALD_ORE:
					return omp.hasPerms(VIPRank.Emerald_VIP);
				case GOLD_BLOCK:
					return omp.hasPerms(VIPRank.Gold_VIP);
				case GOLD_ORE:
					return omp.hasPerms(VIPRank.Gold_VIP);
				case IRON_BLOCK:
					return omp.hasPerms(VIPRank.Iron_VIP);
				case IRON_ORE:
					return omp.hasPerms(VIPRank.Iron_VIP);
				default:
					return omp.hasHat(this);
			}
		}
		
		public String getPriceName(){
			switch(this){
				case DIAMOND_BLOCK:
					return "§cRequired: §b§lDiamond VIP";
				case DIAMOND_ORE:
					return "§cRequired: §b§lDiamond VIP";
				case EMERALD_BLOCK:
					return "§cRequired: §a§lEmerald VIP";
				case EMERALD_ORE:
					return "§cRequired: §a§lEmerald VIP";
				case GOLD_BLOCK:
					return "§cRequired: §6§lGold VIP";
				case GOLD_ORE:
					return "§cRequired: §6§lGold VIP";
				case IRON_BLOCK:
					return "§cRequired: §7§lIron VIP";
				case IRON_ORE:
					return "§cRequired: §7§lIron VIP";
				default:
					return "§cPrice: §b" + getPrice() + " VIP Points";
			}
		}
		
		public Material getMaterial(){
			switch(this){
				case ANDESITE:
					return Material.STONE;
				case BEDROCK:
					return Material.BEDROCK;
				case BLACK_GLASS:
					return Material.STAINED_GLASS;
				case BLUE_GLASS:
					return Material.STAINED_GLASS;
				case CACTUS:
					return Material.CACTUS;
				case CHEST:
					return Material.ENDER_CHEST;
				case COAL_BLOCK:
					return Material.COAL_BLOCK;
				case COAL_ORE:
					return Material.COAL_ORE;
				case DARK_PRISMARINE:
					return Material.PRISMARINE;
				case DIAMOND_BLOCK:
					return Material.DIAMOND_BLOCK;
				case DIAMOND_ORE:
					return Material.DIAMOND_ORE;
				case DIORITE:
					return Material.STONE;
				case EMERALD_BLOCK:
					return Material.EMERALD_BLOCK;
				case EMERALD_ORE:
					return Material.EMERALD_ORE;
				case FURNACE:
					return Material.FURNACE;
				case GLASS:
					return Material.GLASS;
				case GLOWSTONE:
					return Material.GLOWSTONE;
				case GOLD_BLOCK:
					return Material.GOLD_BLOCK;
				case GOLD_ORE:
					return Material.GOLD_ORE;
				case GRANITE:
					return Material.STONE;
				case GRASS:
					return Material.GRASS;
				case GREEN_GLASS:
					return Material.STAINED_GLASS;
				case HAY_BALE:
					return Material.HAY_BLOCK;
				case ICE:
					return Material.ICE;
				case IRON_BLOCK:
					return Material.IRON_BLOCK;
				case IRON_ORE:
					return Material.IRON_ORE;
				case LAPIS_BLOCK:
					return Material.LAPIS_BLOCK;
				case LAPIS_ORE:
					return Material.LAPIS_ORE;
				case LEAVES:
					return Material.LEAVES;
				case MAGENTA_GLASS:
					return Material.STAINED_GLASS;
				case MELON:
					return Material.MELON_BLOCK;
				case MYCELIUM:
					return Material.MYCEL;
				case ORANGE_GLASS:
					return Material.STAINED_GLASS;
				case PRISMARINE_BRICKS:
					return Material.PRISMARINE;
				case QUARTZ_BLOCK:
					return Material.QUARTZ_BLOCK;
				case QUARTZ_ORE:
					return Material.QUARTZ_ORE;
				case REDSTONE_BLOCK:
					return Material.REDSTONE_BLOCK;
				case REDSTONE_ORE:
					return Material.REDSTONE_ORE;
				case RED_GLASS:
					return Material.STAINED_GLASS;
				case SEA_LANTERN:
					return Material.SEA_LANTERN;
				case SLIME_BLOCK:
					return Material.SLIME_BLOCK;
				case SNOW:
					return Material.SNOW_BLOCK;
				case SPONGE:
					return Material.SPONGE;
				case STONE_BRICKS:
					return Material.SMOOTH_BRICK;
				case TNT:
					return Material.TNT;
				case WET_SPONGE:
					return Material.SPONGE;
				case WORKBENCH:
					return Material.WORKBENCH;
				case YELLOW_GLASS:
					return Material.STAINED_GLASS;
				default:
					return null;
			}
		}
		
		public short getDurability(){
			switch(this){
				case ANDESITE:
					return 6;
				case BLACK_GLASS:
					return 15;
				case BLUE_GLASS:
					return 11;
				case DARK_PRISMARINE:
					return 2;
				case DIORITE:
					return 4;
				case GRANITE:
					return 2;
				case GREEN_GLASS:
					return 5;
				case MAGENTA_GLASS:
					return 2;
				case MYCELIUM:
					return 4;
				case ORANGE_GLASS:
					return 1;
				case PRISMARINE_BRICKS:
					return 1;
				case RED_GLASS:
					return 14;
				case WET_SPONGE:
					return 1;
				case YELLOW_GLASS:
					return 4;
				default:
					return 0;
			}
		}
		
		public int getPrice(){
			switch(this){
				case ANDESITE:
					return 100;
				case BEDROCK:
					return 125;
				case BLACK_GLASS:
					return 125;
				case BLUE_GLASS:
					return 125;
				case CACTUS:
					return 100;
				case CHEST:
					return 175;
				case COAL_BLOCK:
					return 100;
				case COAL_ORE:
					return 100;
				case DARK_PRISMARINE:
					return 150;
				case DIAMOND_BLOCK:
					return 0; //Diamond VIP
				case DIAMOND_ORE:
					return 0; //Diamond VIP
				case DIORITE:
					return 100;
				case EMERALD_BLOCK:
					return 0; //Emerald VIP
				case EMERALD_ORE:
					return 0; //Emerald VIP
				case FURNACE:
					return 100;
				case GLASS:
					return 100;
				case GLOWSTONE:
					return 200;
				case GOLD_BLOCK:
					return 0; //Gold VIP
				case GOLD_ORE:
					return 0; //Gold VIP
				case GRANITE:
					return 100;
				case GRASS:
					return 100;
				case GREEN_GLASS:
					return 125;
				case HAY_BALE:
					return 100;
				case ICE:
					return 150;
				case IRON_BLOCK:
					return 0; //Iron VIP
				case IRON_ORE:
					return 0; //Iron VIP
				case LAPIS_BLOCK:
					return 100;
				case LAPIS_ORE:
					return 100;
				case LEAVES:
					return 125;
				case MAGENTA_GLASS:
					return 125;
				case MELON:
					return 100;
				case MYCELIUM:
					return 75;
				case ORANGE_GLASS:
					return 125;
				case PRISMARINE_BRICKS:
					return 150;
				case QUARTZ_BLOCK:
					return 75;
				case QUARTZ_ORE:
					return 100;
				case REDSTONE_BLOCK:
					return 125;
				case REDSTONE_ORE:
					return 125;
				case RED_GLASS:
					return 125;
				case SEA_LANTERN:
					return 225;
				case SLIME_BLOCK:
					return 200;
				case SNOW:
					return 75;
				case SPONGE:
					return 100;
				case STONE_BRICKS:
					return 75;
				case TNT:
					return 125;
				case WET_SPONGE:
					return 125;
				case WORKBENCH:
					return 100;
				case YELLOW_GLASS:
					return 125;
				default:
					return 0;
			}
		}
		
		public String getName(){
			switch(this){
				case ANDESITE:
					return "§7Polished Andesite Hat";
				case BEDROCK:
					return "§8Bedrock Hat";
				case BLACK_GLASS:
					return "§8Black Stained Glass Hat";
				case BLUE_GLASS:
					return "§1Blue Stained Glass Hat";
				case CACTUS:
					return "§2Cactus Hat";
				case CHEST:
					return "§3EnderChest Hat";
				case COAL_BLOCK:
					return "§0Coal Block Hat";
				case COAL_ORE:
					return "§8Coal Ore Hat";
				case DARK_PRISMARINE:
					return "§3Dark Prismarine Hat";
				case DIAMOND_BLOCK:
					return "§bDiamond Block Hat";
				case DIAMOND_ORE:
					return "§bDiamond Ore Hat";
				case DIORITE:
					return "§fPolished Diorite Hat";
				case EMERALD_BLOCK:
					return "§aEmerald Block Hat";
				case EMERALD_ORE:
					return "§aEmerald Ore Hat";
				case FURNACE:
					return "§7Furnace Hat";
				case GLASS:
					return "§fGlass Hat";
				case GLOWSTONE:
					return "§6Glowstone Hat";
				case GOLD_BLOCK:
					return "§6Gold Block Hat";
				case GOLD_ORE:
					return "§6Gold Ore Hat";
				case GRANITE:
					return "§cPolished Granite Hat";
				case GRASS:
					return "§aGrass Hat";
				case GREEN_GLASS:
					return "§aLime Stained Glass Hat";
				case HAY_BALE:
					return "§eHay Bale Hat";
				case ICE:
					return "§bIce Hat";
				case IRON_BLOCK:
					return "§7Iron Block Hat";
				case IRON_ORE:
					return "§7Iron Ore Hat";
				case LAPIS_BLOCK:
					return "§1Lapis Block Hat";
				case LAPIS_ORE:
					return "§1Lapis Ore Hat";
				case LEAVES:
					return "§2Leaves Hat";
				case MAGENTA_GLASS:
					return "§dMagenta Stained Glass Hat";
				case MELON:
					return "§2Melon Hat";
				case MYCELIUM:
					return "§7Mycelium Hat";
				case ORANGE_GLASS:
					return "§6Orange Stained Glass Hat";
				case PRISMARINE_BRICKS:
					return "§9Prismarine Bricks Hat";
				case QUARTZ_BLOCK:
					return "§fQuartz Block Hat";
				case QUARTZ_ORE:
					return "§cQuartz Ore Hat";
				case REDSTONE_BLOCK:
					return "§4Redstone Block Hat";
				case REDSTONE_ORE:
					return "§4Redstone Ore Hat";
				case RED_GLASS:
					return "§4Red Stained Glass Hat";
				case SEA_LANTERN:
					return "§fSea Lantern Hat";
				case SLIME_BLOCK:
					return "§aSlime Block Hat";
				case SNOW:
					return "§fSnow Block Hat";
				case SPONGE:
					return "§eSponge Hat";
				case STONE_BRICKS:
					return "§7Stone Bricks Hat";
				case TNT:
					return "§cTNT Hat";
				case WET_SPONGE:
					return "§eWet Sponge Hat";
				case WORKBENCH:
					return "§6Workbench Hat";
				case YELLOW_GLASS:
					return"§eYellow Stained Glass Hat";
				default:
					return null;
			}
		}
	}
	
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
			
		    net.minecraft.server.v1_8_R2.World nmsWorld = ((CraftWorld) l.getWorld()).getHandle();
			
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

					AttributeInstance currentSpeed = ((EntityInsentient) ((CraftLivingEntity) horse).getHandle()).getAttributeInstance(GenericAttributes.d);
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
	
	public enum Server {

		KITPVP, 
		PRISON, 
		CREATIVE, 
		HUB, 
		SURVIVAL, 
		SKYBLOCK, 
		MINIGAMES, 
		ALPHA;
		
		public String getStatusString(){
			if(isOnline()){
				return "§a§lOnline";
			}
			return "§4§lOffline";
		}
		
		public int getMaxPlayers(){
			switch(this){
				case HUB:
					return 1000;
				default:
					return 100;
			}
		}
		
		public boolean isOnline(){
			return ServerStorage.onlineplayers.get(this) != -1;
		}
		
		public int getOnlinePlayers(){
			return ServerStorage.onlineplayers.get(this);
		}
		
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
		
		public String getName(){
			switch(this){
				case ALPHA:
					return "§e§lAlpha";
				case CREATIVE:
					return "§d§lCreative";
				case HUB:
					return "§3§lHub";
				case KITPVP:
					return "§c§lKitPvP";
				case MINIGAMES:
					return "§f§lMiniGames";
				case PRISON:
					return "§4§lPrison";
				case SKYBLOCK:
					return "§5§lSkyBlock";
				case SURVIVAL:
					return "§a§lSurvival";
				default:
					return null;
			}
		}
		
		public String getVersion(){
			return "1.8";
		}
		
		public String getColor(){
			switch(this){
				case ALPHA:
					return "§e";
				case CREATIVE:
					return "§d";
				case HUB:
					return "§3";
				case KITPVP:
					return "§c";
				case MINIGAMES:
					return "§f";
				case PRISON:
					return "§4";
				case SKYBLOCK:
					return "§5";
				case SURVIVAL:
					return "§a";
				default:
					return null;
			}
		}
		
		public Material getMaterial(){
			switch(this){
				case ALPHA:
					return Material.REDSTONE_COMPARATOR;
				case CREATIVE:
					return Material.WOOD_AXE;
				case HUB:
					return Material.WATCH;
				case KITPVP:
					return Material.IRON_SWORD;
				case MINIGAMES:
					return Material.BOW;
				case PRISON:
					return Material.DIAMOND_PICKAXE;
				case SKYBLOCK:
					return Material.FISHING_ROD;
				case SURVIVAL:
					return Material.STONE_HOE;
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
		
		public String getChatPrefix(){
			switch(this){
				case Builder:
					return "§d§lBuilder §d";
				case Moderator:
					return "§b§lMod §b";
				case Owner:
					return "§4§lOwner §4";
				case User:
					return "§8";
				default:
					return "§8";
			}
		}
		
		public String getScoreboardPrefix(){
			switch(this){
				case Builder:
					return "§d§lBuilder §f";
				case Moderator:
					return "§b§lMod §f";
				case Owner:
					return "§4§lOwner §f";
				case User:
					return "§8";
				default:
					return "§8";
			}
		}
		
		public String getRankString(){
			switch(this){
				case Builder:
					return "§d§lBuilder";
				case Moderator:
					return "§b§lModerator";
				case Owner:
					return "§4§lOwner";
				case User:
					return "§fNo Rank";
				default:
					return "§fNo Rank";
			}
		}
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
		
		public String getName(){
			switch(this){
				case ANGRY_VILLAGER:
					return "§8Angry Villager Trail";
				case BUBBLE:
					return "§fBubble Trail";
				case CRIT:
					return "§bCrit Trail";
				case ENCHANTMENT_TABLE:
					return "§1Enchantment Table Trail";
				case FIREWORK_SPARK:
					return "§cFireWork Spark Trail";
				case HAPPY_VILLAGER:
					return "§aHappy Villager Trail";
				case HEART:
					return "§cHeart Trail";
				case LAVA:
					return "§6Lava Trail";
				case MAGIC:
					return "§3Magic Trail";
				case MOB_SPAWNER:
					return "§7Mob Spawner Trail";
				case MUSIC:
					return "§dMusic Trail";
				case RAINBOW:
					return "§4Rainbow Trail";
				case SLIME:
					return "§aSlime Trail";
				case SMOKE:
					return "§0Smoke Trail";
				case SNOW:
					return "§fSnow Trail";
				case TNT:
					return "§4TNT Trail";
				case WATER:
					return "§9Water Trail";
				case WITCH:
					return "§5Witch Trail";
				default:
					return null;
			}
		}
		
		public int getPrice(){
			switch(this){
				case ANGRY_VILLAGER:
					return 400;
				case BUBBLE:
					return 375;
				case CRIT:
					return 375;
				case ENCHANTMENT_TABLE:
					return 400;
				case FIREWORK_SPARK:
					return 400;
				case HAPPY_VILLAGER:
					return 0;
				case HEART:
					return 300;
				case LAVA:
					return 0;
				case MAGIC:
					return 0;
				case MOB_SPAWNER:
					return 525;
				case MUSIC:
					return 625;
				case RAINBOW:
					return 0;
				case SLIME:
					return 275;
				case SMOKE:
					return 325;
				case SNOW:
					return 475;
				case TNT:
					return 475;
				case WATER:
					return 425;
				case WITCH:
					return 350;
				default:
					return 0;
			}
		}
		
		public String getPriceName(){
			switch(this){
				case HAPPY_VILLAGER:
					return "§cRequired: §a§lEmerald VIP";
				case LAVA:
					return "§cRequired: §b§lDiamond VIP";
				case MAGIC:
					return "§cRequired: §7§lIron VIP";
				case RAINBOW:
					return "§cRequired: §6§lGold VIP";
				default:
					return "§cPrice: §b" + getPrice() + " VIP Points";
			}
		}
		
		public boolean hasTrail(OMPlayer omp){
			switch(this){
				case HAPPY_VILLAGER:
					return omp.hasPerms(VIPRank.Emerald_VIP);
				case LAVA:
					return omp.hasPerms(VIPRank.Diamond_VIP);
				case MAGIC:
					return omp.hasPerms(VIPRank.Iron_VIP);
				case RAINBOW:
					return omp.hasPerms(VIPRank.Gold_VIP);
				default:
					return omp.hasTrail(this);
			}
		}
		
		public Material getMaterial(){
			switch(this){
				case ANGRY_VILLAGER:
					return Material.COAL;
				case BUBBLE:
					return Material.WEB;
				case CRIT:
					return Material.DIAMOND_SWORD;
				case ENCHANTMENT_TABLE:
					return Material.ENCHANTMENT_TABLE;
				case FIREWORK_SPARK:
					return Material.FIREWORK;
				case HAPPY_VILLAGER:
					return Material.EMERALD;
				case HEART:
					return Material.NETHER_STALK;
				case LAVA:
					return Material.LAVA_BUCKET;
				case MAGIC:
					return Material.INK_SACK;
				case MOB_SPAWNER:
					return Material.MOB_SPAWNER;
				case MUSIC:
					return Material.NOTE_BLOCK;
				case RAINBOW:
					return Material.REDSTONE;
				case SLIME:
					return Material.SLIME_BALL;
				case SMOKE:
					return Material.INK_SACK;
				case SNOW:
					return Material.SNOW_BALL;
				case TNT:
					return Material.TNT;
				case WATER:
					return Material.WATER_BUCKET;
				case WITCH:
					return Material.INK_SACK;
				default:
					return null;
			}
		}
		
		public short getDurability(){
			switch(this){
				case MAGIC:
					return 6;
				case WITCH:
					return 5;
				default:
					return 0;
			}
		}
	}
	
	public enum TrailType {

		BASIC_TRAIL,
		GROUND_TRAIL,
		HEAD_TRAIL,
		BODY_TRAIL,
		BIG_TRAIL,
		VERTICAL_TRAIL;
		
		public String getName(){
			switch(this){
				case BASIC_TRAIL:
					return "§7§lBasic Trail";
				case BIG_TRAIL:
					return "§7§lBig Trail";
				case BODY_TRAIL:
					return "§7§lBody Trail";
				case GROUND_TRAIL:
					return "§7§lGround Trail";
				case HEAD_TRAIL:
					return "§7§lHead Trail";
				case VERTICAL_TRAIL:
					return "§7§lVertical Trail";
				default:
					return null;
			}
		}
		
		public int getPrice(){
			switch(this){
				case BASIC_TRAIL:
					return 0;
				case BIG_TRAIL:
					return 650;
				case BODY_TRAIL:
					return 400;
				case GROUND_TRAIL:
					return 600;
				case HEAD_TRAIL:
					return 400;
				case VERTICAL_TRAIL:
					return 500;
				default:
					return 0;
			}
		}
		
		public String getPriceString(){
			return "§cPrice: §b" + getPrice() + " VIP Points";
		}
		
		public boolean hasTrailType(OMPlayer omp){
			switch(this){
				case BASIC_TRAIL:
					return true;
				default:
					return omp.hasTrailType(this);
			}
		}
	}
	
	public enum VIPRank {

		User,
		Iron_VIP,
		Gold_VIP,
		Diamond_VIP,
		Emerald_VIP;
		
		public int getMonthlyBonus(){
				switch(this){
				case Diamond_VIP:
					return 1250;
				case Emerald_VIP:
					return 2500;
				case Gold_VIP:
					return 500;
				case Iron_VIP:
					return 250;
				default:
					return 0;
			}
		}
		
		public String getChatPrefix(){
			switch(this){
				case Diamond_VIP:
					return "§9§lDiamond §9";
				case Emerald_VIP:
					return "§a§lEmerald §a";
				case Gold_VIP:
					return "§6§lGold §6";
				case Iron_VIP:
					return "§7§lIron §7";
				case User:
					return "§8";
				default:
					return "§8";
			}
		}
		
		public String getScoreboardPrefix(){
			switch(this){
				case Diamond_VIP:
					return "§9§lDiamond §f";
				case Emerald_VIP:
					return "§a§lEmerald §f";
				case Gold_VIP:
					return "§6§lGold §f";
				case Iron_VIP:
					return "§7§lIron §f";
				case User:
					return "§f";
				default:
					return "§f";
			}
		}
		
		public String getRankString(){
			switch(this){
				case Diamond_VIP:
					return "§9§lDiamond";
				case Emerald_VIP:
					return "§a§lEmerald";
				case Gold_VIP:
					return "§6§lGold";
				case Iron_VIP:
					return "§7§lIron";
				case User:
					return "§fNo Rank";
				default:
					return "§fNo Rank";
			}
		}
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
}
