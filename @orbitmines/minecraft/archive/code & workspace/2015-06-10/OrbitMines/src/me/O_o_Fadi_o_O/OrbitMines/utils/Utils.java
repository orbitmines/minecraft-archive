package me.O_o_Fadi_o_O.OrbitMines.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.NameFetcher;
import me.O_o_Fadi_o_O.OrbitMines.UUIDFetcher;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetChicken;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetCow;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetCreeper;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetMagmaCube;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetMushroomCow;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetOcelot;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetPig;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSheep;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSilverfish;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSlime;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSpider;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSquid;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetWolf;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Map;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R2.AttributeInstance;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EnumParticle;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.NBTTagByte;
import net.minecraft.server.v1_8_R2.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
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
import org.bukkit.potion.PotionEffectType;
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
		NameFetcher namef = new NameFetcher(uuid);
		try{
			String name = namef.call().get(uuid).get(namef.call().get(uuid).size() -1);
			String[] nameparts = name.split(" ", 1);
			return nameparts[0];
		}catch(Exception ex){
			return null;
		}
	}
	public static String getNameDate(UUID uuid){
		NameFetcher namef = new NameFetcher(uuid);
		try{
			String name = namef.call().get(uuid).get(namef.call().get(uuid).size() -1);
			String[] nameparts = name.split(" ", 1);
			return nameparts[1];
		}catch(Exception ex){
			return null;
		}
	}
	public static HashMap<String, String> getNames(UUID uuid){
		NameFetcher namef = new NameFetcher(uuid);
		try{
			HashMap<String, String> names = new HashMap<String, String>();
			for(String name : namef.call().get(uuid)){
				String[] nameparts = name.split(" ", 1);
				if(nameparts.length > 1){
					names.put(nameparts[0], nameparts[1]);
				}
				else{
					names.put(nameparts[0], null);
				}
			}
			
			return names;
		}catch(Exception ex){
			return null;
		}
	}
	
	public static String getStringFromLocation(Location l){
		if(l != null){
			return l.getWorld().getName() + "|" + l.getX() + "|" + l.getY() + "|" + l.getZ() + "|" + l.getYaw() + "|" + l.getPitch();
		}
		return null;
	}
	public static Location getLocationFromString(String s){
		if(s != null){
			String[] data = s.split("\\|");
			return new Location(Bukkit.getWorld(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]));
		}
		return null;
	}
	public static List<UUID> getUUIDList(List<String> uuidstringlist){
		List<UUID> uuids = new ArrayList<UUID>();
		for(String uuid : uuidstringlist){
			uuids.add(UUID.fromString(uuid));
		}
		return uuids;
	}
	public static List<String> getStringList(List<UUID> uuidlist){
		List<String> uuids = new ArrayList<String>();
		for(UUID uuid : uuidlist){
			uuids.add(uuid.toString());
		}
		return uuids;
	}
	public static List<Location> getLocationList(List<String> locationstringlist){
		List<Location> locations = new ArrayList<Location>();
		if(locationstringlist != null){
			for(String location : locationstringlist){
				locations.add(getLocationFromString(location));
			}
		}
		return locations;
	}
	public static String getStringFromItemStack(ItemStack item){
		// TYPE|AMOUNT|DURABILITY|Enchantments:ENCHANTMENT(LEVEL):ENCHANTMENT(LEVEL)|DISPLAYNAME|LORE;LORE
		if(item != null){
			String enchantmentsstring = "Enchantments";
			java.util.Map<Enchantment, Integer> enchantments = item.getEnchantments();
			for(Enchantment ench : enchantments.keySet()){
				if(enchantments.get(ench) > 0){
					enchantmentsstring = enchantmentsstring + ":" + ench.getName() + "(" + enchantments.get(ench) + ")";
				}
			}
			
			String itemlorestring = "null";
			if(item.getItemMeta().getLore() != null){
				for(String line : item.getItemMeta().getLore()){
					if(itemlorestring.equals("null")){
						itemlorestring = line;
					}
					else{
						itemlorestring += ";" + line;
					}
				}
			}
			
			return item.getType().toString() + "|" + item.getAmount() + "|" + item.getDurability() + "|" + enchantmentsstring + "|" + item.getItemMeta().getDisplayName() + "|" + itemlorestring;
		}
		return null;
	}
	public static ItemStack getItemStackFromString(String itemstackstring){
		if(itemstackstring != null){
			String[] data = itemstackstring.split("\\|");
			
			ItemStack item = new ItemStack(Material.valueOf(data[0]), Integer.parseInt(data[1]));
			item.setDurability(Short.parseShort(data[2]));
	
			if(data[3].contains(":")){
				String[] enchdata = data[3].split("\\:");
				for(String ench : enchdata){
					if(!ench.equals("Enchantments")){
						String ench2 = ench.replace("(", "|").replace(")", "");
						String[] enchdata2 = ench2.split("\\|");
						
						item.addUnsafeEnchantment(Enchantment.getByName(enchdata2[0]), Integer.parseInt(enchdata2[1]));
					}
				}
			}
			
			ItemMeta meta = item.getItemMeta();
			if(!data[4].equals("null")){
				meta.setDisplayName(data[4]);
			}
			if(!data[5].equals("null")){
				meta.setLore(Arrays.asList(data[5].split("\\;")));
			}
			item.setItemMeta(meta);
			
			return item;
		}
		return null;
	}
	
	public static Player getPlayer(String playername){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(playername)){
				return player;
			}
		}
		return null;
	}
	public static Player getPlayer(UUID uuid){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getUniqueId().toString().equals(uuid.toString())){
				return player;
			}
		}
		return null;
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
	
	public static List<String> asNewStringList(List<String> list){
		List<String> newlist = new ArrayList<String>();
		for(String s : list){
			newlist.add(s);
		}
		return newlist;
	}
	public static List<Map> asNewMapList(List<Map> list){
		List<Map> newlist = new ArrayList<Map>();
		for(Map m : list){
			newlist.add(m);
		}
		return newlist;
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
	
	public static boolean getRandomBoolean(){
		return new Random().nextBoolean();
	}
	
    public static int getRandom(int lower, int upper){
        return new Random().nextInt((upper - lower) + 1) + lower;
    }
	
	public static Vector getRandomVelocity(){
        float x = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        float y = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        float z = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
        int iInt = new Random().nextInt(4);
        	
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
      	else{
      		return null;
      	}
	}
	
	public static boolean isGameMode(GameMode gamemode, String a[]){
		switch(gamemode){
			case ADVENTURE:
				return a[1].equalsIgnoreCase("a") || a[1].equalsIgnoreCase("2") || a[1].equalsIgnoreCase("adventure");
			case CREATIVE:
				return a[1].equalsIgnoreCase("c") || a[1].equalsIgnoreCase("1") || a[1].equalsIgnoreCase("creative");
			case SPECTATOR:
				return a[1].equalsIgnoreCase("spec") || a[1].equalsIgnoreCase("3") || a[1].equalsIgnoreCase("spectate");
			case SURVIVAL:
				return a[1].equalsIgnoreCase("s") || a[1].equalsIgnoreCase("0") || a[1].equalsIgnoreCase("survival");
			default:
				return false;
		}
	}
	
	public static List<Location> getPaintballLocations(Location l){
		final Location l1 = new Location(l.getWorld(), l.getX() +0, l.getY() -1, l.getZ() +0);
		final Location l2 = new Location(l.getWorld(), l.getX() +0, l.getY() +0, l.getZ() +0);
		final Location l3 = new Location(l.getWorld(), l.getX() +1, l.getY() -1, l.getZ() +0);
		final Location l4 = new Location(l.getWorld(), l.getX() +0, l.getY() -1, l.getZ() +1);
		final Location l5 = new Location(l.getWorld(), l.getX() -1, l.getY() -1, l.getZ() +0);
		final Location l6 = new Location(l.getWorld(), l.getX() +0, l.getY() -2, l.getZ() +0);
		final Location l7 = new Location(l.getWorld(), l.getX() +0, l.getY() -1, l.getZ() -1);
		
		final Location l8 = new Location(l.getWorld(), l.getX() +0, l.getY() +1, l.getZ() +0);
		final Location l9 = new Location(l.getWorld(), l.getX() +1, l.getY() +0, l.getZ() +0);
		final Location l10 = new Location(l.getWorld(), l.getX() -1, l.getY() +0, l.getZ() +0);
		final Location l11 = new Location(l.getWorld(), l.getX() +0, l.getY() +0, l.getZ() +1);
		final Location l12 = new Location(l.getWorld(), l.getX() +0, l.getY() +0, l.getZ() -1);
		final Location l13 = new Location(l.getWorld(), l.getX() +2, l.getY() -1, l.getZ() +0);
		final Location l14 = new Location(l.getWorld(), l.getX() +0, l.getY() -1, l.getZ() +2);
		final Location l15 = new Location(l.getWorld(), l.getX() -2, l.getY() -1, l.getZ() +0);
		final Location l16 = new Location(l.getWorld(), l.getX() +0, l.getY() -1, l.getZ() -2);
		final Location l17 = new Location(l.getWorld(), l.getX() +1, l.getY() -1, l.getZ() +1);
		final Location l18 = new Location(l.getWorld(), l.getX() +1, l.getY() -1, l.getZ() -1);
		final Location l19 = new Location(l.getWorld(), l.getX() -1, l.getY() -1, l.getZ() +1);
		final Location l20 = new Location(l.getWorld(), l.getX() -1, l.getY() -1, l.getZ() -1);
		
		final Location l21 = new Location(l.getWorld(), l.getX() +0, l.getY() -3, l.getZ() +0);
		final Location l22 = new Location(l.getWorld(), l.getX() +1, l.getY() -2, l.getZ() +0);
		final Location l23 = new Location(l.getWorld(), l.getX() -1, l.getY() -2, l.getZ() +0);
		final Location l24 = new Location(l.getWorld(), l.getX() +0, l.getY() -2, l.getZ() +1);
		final Location l25 = new Location(l.getWorld(), l.getX() +0, l.getY() -2, l.getZ() -1);
		
		return Arrays.asList(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25);
	}
	
	public static String getPotionName(PotionEffectType type){
		if(type == PotionEffectType.ABSORPTION){return "Absorption";}
		else if(type == PotionEffectType.BLINDNESS){return "Blindness";}
		else if(type == PotionEffectType.CONFUSION){return "Nausea";}
		else if(type == PotionEffectType.DAMAGE_RESISTANCE){return "Resistance";}
		else if(type == PotionEffectType.FAST_DIGGING){return "Haste";}
		else if(type == PotionEffectType.FIRE_RESISTANCE){return "Fire Resistance";}
		else if(type == PotionEffectType.HARM){return "Harming";}
		else if(type == PotionEffectType.HEAL){return "Health";}
		else if(type == PotionEffectType.HEALTH_BOOST){return "Health Boost";}
		else if(type == PotionEffectType.HUNGER){return "Hunger";}
		else if(type == PotionEffectType.INCREASE_DAMAGE){return "Strength";}
		else if(type == PotionEffectType.INVISIBILITY){return "Invisibility";}
		else if(type == PotionEffectType.INVISIBILITY){return "Invisibility";}
		else if(type == PotionEffectType.JUMP){return "Jump Boost";}
		else if(type == PotionEffectType.NIGHT_VISION){return "Night Vision";}
		else if(type == PotionEffectType.POISON){return "Poison";}
		else if(type == PotionEffectType.REGENERATION){return "Regeneration";}
		else if(type == PotionEffectType.SATURATION){return "Saturation";}
		else if(type == PotionEffectType.SLOW){return "Slowness";}
		else if(type == PotionEffectType.SLOW_DIGGING){return "Mining Fatigue";}
		else if(type == PotionEffectType.SPEED){return "Speed";}
		else if(type == PotionEffectType.WATER_BREATHING){return "Water Breathing";}
		else if(type == PotionEffectType.WEAKNESS){return "Weakness";}
		else if(type == PotionEffectType.WITHER){return "Wither";}
		else{return null;}
	}
	
	public static List<Color> colorValues(){
		return Arrays.asList(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW);
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
	
	public static String getColorDatabaseName(Color color){
		if(color == Color.AQUA){ return "LightBlue";}
		else if(color == Color.BLACK){ return "Black";}
		else if(color == Color.BLUE){ return "Blue";}
		else if(color == Color.FUCHSIA){ return "Pink";}
		else if(color == Color.GRAY){ return "Gray";}
		else if(color == Color.GREEN){ return "Green";}
		else if(color == Color.LIME){ return "LightGreen";}
		else if(color == Color.MAROON){ return "DarkRed";}
		else if(color == Color.NAVY){ return "DarkBlue";}
		else if(color == Color.ORANGE){ return "Orange";}
		else if(color == Color.PURPLE){ return "Purple";}
		else if(color == Color.RED){ return "Red";}
		else if(color == Color.SILVER){ return "LightGray";}
		else if(color == Color.TEAL){ return "Cyan";}
		else if(color == Color.WHITE){ return "White";}
		else if(color == Color.YELLOW){ return "Yellow";}
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
	
	public static ItemStack setLore(ItemStack item, List<String> itemlore){
		ItemMeta meta = (ItemMeta) item.getItemMeta();
		meta.setLore(itemlore);
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
			TextComponent[] tcs = new TextComponent[this.tcs.size()];
			int index = 0;
			for(TextComponent tc : this.tcs){
				tcs[index] = tc;
				index++;
			}
			
			TextComponent tc = new TextComponent(tcs);
			for(Player player : players){
				player.spigot().sendMessage(tc);
			}
		}
	}
	
	public enum Cooldown {
		
		PORTAL_USAGE,
		NPC_INTERACT,
		SGA_USAGE,
		PET_KITTY_CANNON_USAGE,
		PET_LEAP_USAGE,
		PET_SILVERFISH_BOMB_USAGE,
		PET_MILK_EXPLOSION,
		PET_BARK,
		PET_JUMP,
		PET_BABY_FIREWORK,
		PET_WEBS,
		PET_SPIDER_LAUNCHER,
		PET_INK_BOMB,
		SWAP_TELEPORTER,
		CREEPER_LAUNCHER,
		BOOK_EXPLOSION,
		
		TELEPORTING,
		
		// KitPvP Cooldowns \\
		FIRE_SPELL_I,
		FIRE_SPELL_II,
		POTION_LAUNCHER_I,
		WITHER_I,
		HEALING_I,
		HEALING_II,
		BARRIER_I,
		BARRIER_II,
		TNT_I,
		MAGIC_I,
		FISH_ATTACK_I,
		SHIELD_I,
		SHIELD_II,
		BLOCK_EXPLOSION_I,
		UNDEATH_SUMMON_I,
		PAINTBALLS_I,
		PAINTBALLS_I_USAGE,
		
		// Creative Cooldowns \\
		MESSAGE,
		BROADCAST;
		
		public int getCooldown(){
			switch(this){
				case BOOK_EXPLOSION:
					return 7 * 1000;
				case CREEPER_LAUNCHER:
					return 6 * 1000;
				case PET_BARK:
					return 3 * 1000;
				case PET_BABY_FIREWORK:
					return 1 * 1000;
				case PET_INK_BOMB:
					return 3 * 1000;
				case PET_JUMP:
					return 5 * 1000;
				case PET_KITTY_CANNON_USAGE:
					return 1 * 1000;
				case PET_LEAP_USAGE:
					return 4 * 1000;
				case PET_MILK_EXPLOSION:
					return 4 * 1000;
				case PET_SILVERFISH_BOMB_USAGE:
					return 6 * 1000;
				case PET_SPIDER_LAUNCHER:
					return 2 * 1000;
				case PET_WEBS:
					return 4 * 1000;
				case NPC_INTERACT:
					return 1 * 1000;
				case PORTAL_USAGE:
					return 3 * 1000;
				case SGA_USAGE:
					return 180 * 1000;
				case SWAP_TELEPORTER:
					return 4 * 1000;
				case BARRIER_I:
					return 120 * 1000;
				case BARRIER_II:
					return 100 * 1000;
				case FIRE_SPELL_I:
					return 1 * 1000;
				case FIRE_SPELL_II:
					return 1 * 1000;
				case FISH_ATTACK_I:
					return 5 * 1000;
				case HEALING_I:
					return 20 * 1000;
				case HEALING_II:
					return 20 * 1000;
				case MAGIC_I:
					return 30 * 1000;
				case POTION_LAUNCHER_I:
					return 5 * 1000;
				case SHIELD_I:
					return 40 * 1000;
				case SHIELD_II:
					return 40 * 1000;
				case TNT_I:
					return 7 * 1000;
				case WITHER_I:
					return 5 * 1000;
				case BLOCK_EXPLOSION_I:
					return 25 * 1000;
				case UNDEATH_SUMMON_I:
					return 7 * 1000;
				case PAINTBALLS_I:
					return 1 * 1000;
				case PAINTBALLS_I_USAGE:
					return 2 * 1000;
				case MESSAGE:
					return 2 * 1000;
				case BROADCAST:
					return 300 * 1000;
				default:
					return 0;
			}
		}
		
		public String getName(){
			switch(this){
				case BOOK_EXPLOSION:
					return Gadget.BOOK_EXPLOSION.getName();
				case CREEPER_LAUNCHER:
					return Gadget.CREEPER_LAUNCHER.getName();
				case NPC_INTERACT:
					return "";
				case PET_BABY_FIREWORK:
					return "§c§lBaby Firework";
				case PET_BARK:
					return "§6§lBark";
				case PET_INK_BOMB:
					return "§8§lInk Bomb";
				case PET_JUMP:
					return "§6§lSuper Jump";
				case PET_KITTY_CANNON_USAGE:
					return "§e§lKitty Cannon";
				case PET_LEAP_USAGE:
					return "§8§lLeap";
				case PET_MILK_EXPLOSION:
					return "§f§lMilk Explosion";
				case PET_SILVERFISH_BOMB_USAGE:
					return "§7§lSilverfish Bomb";
				case PET_SPIDER_LAUNCHER:
					return "§5§lSpider Launcher";
				case PET_WEBS:
					return "§f§lWebs";
				case PORTAL_USAGE:
					return "";
				case SGA_USAGE:
					return Gadget.SNOWMAN_ATTACK.getName();
				case SWAP_TELEPORTER:
					return Gadget.SWAP_TELEPORTER.getName();
				case BARRIER_I:
					return "§d§lBarrier";
				case BARRIER_II:
					return "§d§lBarrier";
				case FIRE_SPELL_I:
					return "§c§lFire Spell";
				case FIRE_SPELL_II:
					return "§c§lFire Spell";
				case FISH_ATTACK_I:
					return "§9§lFish Attack";
				case HEALING_I:
					return "§a§lHealing";
				case HEALING_II:
					return "§a§lHealing";
				case MAGIC_I:
					return "§5§lMagic";
				case POTION_LAUNCHER_I:
					return "§e§lPotion Launcher";
				case SHIELD_I:
					return "§7§lShield";
				case SHIELD_II:
					return "§7§lShield";
				case TNT_I:
					return "§4§lTNT Launcher";
				case WITHER_I:
					return "§8§lNecromancer's Staff";
				case BLOCK_EXPLOSION_I:
					return "§e§lBlock Explosion";
				case UNDEATH_SUMMON_I:
					return "§d§lSummon the Undeath";
				case PAINTBALLS_I:
					return "§f§lPaintballs";
				default:
					return "";
			}
		}
		
		public String getItemName(){
			switch(this){
				case BOOK_EXPLOSION:
					return Gadget.BOOK_EXPLOSION.getName();
				case CREEPER_LAUNCHER:
					return Gadget.CREEPER_LAUNCHER.getName();
				case NPC_INTERACT:
					return "";
				case PET_BABY_FIREWORK:
					return "§c§lBaby Firework";
				case PET_BARK:
					return "§6§lBark";
				case PET_INK_BOMB:
					return "§8§lInk Bomb";
				case PET_JUMP:
					return "§6§lSuper Jump";
				case PET_KITTY_CANNON_USAGE:
					return "§e§lKitty Cannon";
				case PET_LEAP_USAGE:
					return "§8§lLeap";
				case PET_MILK_EXPLOSION:
					return "§f§lMilk Explosion";
				case PET_SILVERFISH_BOMB_USAGE:
					return "§7§lSilverfish Bomb";
				case PET_SPIDER_LAUNCHER:
					return "§5§lSpider Launcher";
				case PET_WEBS:
					return "§f§lWebs";
				case PORTAL_USAGE:
					return "";
				case SGA_USAGE:
					return Gadget.SNOWMAN_ATTACK.getName();
				case SWAP_TELEPORTER:
					return Gadget.SWAP_TELEPORTER.getName();
				case BARRIER_I:
					return "§dBarrier";
				case BARRIER_II:
					return "§dBarrier";
				case FIRE_SPELL_I:
					return "§cFireWand";
				case FIRE_SPELL_II:
					return "§cFireWand";
				case FISH_ATTACK_I:
					return "§9Fish Attack";
				case HEALING_I:
					return "§bWeapon§a";
				case HEALING_II:
					return "§bWeapon§a";
				case MAGIC_I:
					return "§bWeapon§5";
				case POTION_LAUNCHER_I:
					return "§ePotion Launcher";
				case SHIELD_I:
					return "§7Shield";
				case SHIELD_II:
					return "§7Shield";
				case TNT_I:
					return "§4TNT Launcher";
				case WITHER_I:
					return "§8Necromancer's Staff";
				case BLOCK_EXPLOSION_I:
					return "§eBlock Explosion";
				case UNDEATH_SUMMON_I:
					return "§dSummon the Undeath";
				case PAINTBALLS_I:
					return "§bWeapon§f";
				default:
					return "";
			}
		}
		
		public static List<Cooldown> getKitPvPCooldowns(){
			return Arrays.asList(FIRE_SPELL_I, FIRE_SPELL_II, POTION_LAUNCHER_I, WITHER_I, HEALING_I, HEALING_II, BARRIER_I, BARRIER_II, TNT_I, MAGIC_I, FISH_ATTACK_I, SHIELD_I, SHIELD_II, BLOCK_EXPLOSION_I, PAINTBALLS_I);
		}
		
		public static List<Cooldown> getCreativeCooldowns(){
			return Arrays.asList(MESSAGE, BROADCAST);
		}
		
		public String getAction(){
			switch(this){
				case SWAP_TELEPORTER:
					return "§e§lLeft Click";
				default:
					return "§e§lRight Click";
			}
		}
		
		public static HashMap<OMPlayer, Double> prevdouble = new HashMap<OMPlayer, Double>();
		public void updateActionBar(OMPlayer omp){
			ItemStack item = omp.getPlayer().getItemInHand();
			
			if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && !getName().equals("")){
				boolean equals = false;
				
				if(item.getItemMeta().getDisplayName().endsWith(getName().replace("§l", "§n"))){
					equals = true;
				}
				else if(item.getItemMeta().getDisplayName().endsWith(getItemName())){
					equals = true;
				}
				else{}
				
				if(equals && omp.onCooldown(this)){
					double cooldown = getCooldown() / 1000;
					double left = cooldown - ((System.currentTimeMillis() - omp.getCooldown(this)) / 1000);
					
					String format = "ss,S";
					if(left < 10){
						format = "s,S";
					}
					if(left > 60){
						format = "m: ss,S";
					}
					if(left > 600){
						format = "mm: ss,S";
					}
					String leftstring = (new SimpleDateFormat(format).format(new Date(getCooldown() - (System.currentTimeMillis() - omp.getCooldown(this))))).replace(":", "m");
					leftstring = leftstring.substring(0, leftstring.indexOf(",") +2) + "s";
					
					String bar = "";
					if(leftstring.contains("m")){
						left = (Integer.parseInt(leftstring.substring(0, leftstring.indexOf("m"))) * 60) + Integer.parseInt(leftstring.substring(leftstring.indexOf("m") +2, leftstring.indexOf(","))) + (Double.parseDouble(leftstring.substring(leftstring.indexOf(",") +1, leftstring.indexOf(",") +2)) / 10);
					}
					else{	
						left = Integer.parseInt(leftstring.substring(0, leftstring.indexOf(","))) + (Double.parseDouble(leftstring.substring(leftstring.indexOf(",") +1, leftstring.indexOf(",") +2)) / 10);
					}
					double red = 40 - (((left / cooldown) * 100) / 2.5) + 2;
					
					/*
					 * Fix incorrect numbers;
					 */
					if(prevdouble.containsKey(omp) && (prevdouble.get(omp) - red) >= 1.1){
						red = prevdouble.get(omp);
					}
					else{
						prevdouble.put(omp, red);
					}
					
					bar += "§a|||||||||||||||||||||||||||||||||||||||| §8| §f" + leftstring + " §8| " + getName();
					bar = bar.substring(0, (int) red) + "§c" + bar.substring((int) red);
					
					ActionBar actionbar = new ActionBar(bar);
					actionbar.send(omp.getPlayer());
				}
				else{
					if(equals){
						ActionBar actionbar = new ActionBar(getAction() + " §8| " + getName());
						actionbar.send(omp.getPlayer());
					}
				}
			}
		}
	}
	
	public enum MindCraftColor {
		
		BLUE,
		YELLOW,
		GREEN,
		RED,
		LIGHT_BLUE,
		ORANGE;
		
		public String getName(){
			switch(this){
				case BLUE:
					return "§1§lBlue";
				case GREEN:
					return "§a§lGreen";
				case LIGHT_BLUE:
					return "§b§lLight Blue";
				case ORANGE:
					return "§6§lOrange";
				case RED:
					return "§c§lRed";
				case YELLOW:
					return "§e§lYellow";
				default:
					return null;
			}
		}
		
		public byte getData(){
			switch(this){
				case BLUE:
					return 11;
				case GREEN:
					return 5;
				case LIGHT_BLUE:
					return 3;
				case ORANGE:
					return 1;
				case RED:
					return 14;
				case YELLOW:
					return 4;
				default:
					return 0;
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
		SERVER_SELECTOR,
		OMT_SHOP,
		
		// Hub \\
		LAPIS_PARKOUR,
		MINDCRAFT,
		ALPHA,
		SERVER_INFO_KITPVP,
		SERVER_INFO_PRISON,
		SERVER_INFO_CREATIVE,
		SERVER_INFO_SURVIVAL,
		SERVER_INFO_SKYBLOCK,
		SERVER_INFO_MINIGAMES,
		TOP_DONATOR,
		
		// KitPvP \\
		KIT_SELECTOR,
		SPECTATE,
		MASTERIES,
		CRATES,
		
		// Creative \\
		PLOT_INFO,
		PLOT_KIT_SELECTOR;
		
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

		public String getDatabaseName(){
			switch(this){
				case BLACK:
					return "Black";
				case BLUE:
					return "Blue";
				case DARK_BLUE:
					return "DarkBlue";
				case DARK_GRAY:
					return "DarkGray";
				case DARK_RED:
					return "DarkRed";
				case GREEN:
					return "Green";
				case LIGHT_BLUE:
					return "LightBlue";
				case LIGHT_GREEN:
					return "LightGreen";
				case PINK:
					return "Pink";
				case RED:
					return "Red";
				case WHITE:
					return "White";
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
		
		public VIPRank getVIPRank(){
			switch(this){
				case CYAN:
					return VIPRank.Diamond_VIP;
				case ORANGE:
					return VIPRank.Emerald_VIP;
				case PURPLE:
					return VIPRank.Iron_VIP;
				case YELLOW:
					return VIPRank.Gold_VIP;
				default:
					return null;
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
				case BLUE:
					return 12;
				case CYAN:
					return 6;
				case DARK_BLUE:
					return 4;
				case DARK_GRAY:
					return 8;
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
		MINIGAME_POINTS,
		KITPVP_MONEY;
	
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
		
		public String getDatabaseName(){
			switch(this){
				case BAT:
					return "Bat";
				case CAVE_SPIDER:
					return "CaveSpider";
				case CHICKEN:
					return "Chicken";
				case COW:
					return "Cow";
				case CREEPER:
					return "Creeper";
				case ENDERMAN:
					return "Enderman";
				case GHAST:
					return "Ghast";
				case HORSE:
					return "Horse";
				case IRON_GOLEM:
					return "IronGolem";
				case MAGMA_CUBE:
					return "MagmaCube";
				case MUSHROOM_COW:
					return "MushroomCow";
				case OCELOT:
					return "Ocelot";
				case RABBIT:
					return "Rabbit";
				case SHEEP:
					return "Sheep";
				case SILVERFISH:
					return "Silverfish";
				case SKELETON:
					return "Skeleton";
				case SLIME:
					return "Slime";
				case SNOWMAN:
					return "Snowman";
				case SPIDER:
					return "Spider";
				case SQUID:
					return "Squid";
				case WITCH:
					return "Witch";
				case WOLF:
					return "Wolf";
				case ZOMBIE_PIGMAN:
					return "ZombiePigman";
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
		
		public VIPRank getVIPRank(){
				switch(this){
				case BLAZE:
					return VIPRank.Emerald_VIP;
				case PIG:
					return VIPRank.Iron_VIP;
				case VILLAGER:
					return VIPRank.Diamond_VIP;
				case ZOMBIE:
					return VIPRank.Gold_VIP;
				default:
					return null;
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
		
		public EntityType getEntityType(){
			switch(this){
				case BAT:
					return EntityType.BAT;
				case BLAZE:
					return EntityType.BLAZE;
				case CAVE_SPIDER:
					return EntityType.CAVE_SPIDER;
				case CHICKEN:
					return EntityType.CHICKEN;
				case COW:
					return EntityType.COW;
				case CREEPER:
					return EntityType.CREEPER;
				case ENDERMAN:
					return EntityType.ENDERMAN;
				case GHAST:
					return EntityType.GHAST;
				case HORSE:
					return EntityType.HORSE;
				case IRON_GOLEM:
					return EntityType.IRON_GOLEM;
				case MAGMA_CUBE:
					return EntityType.MAGMA_CUBE;
				case MUSHROOM_COW:
					return EntityType.MUSHROOM_COW;
				case OCELOT:
					return EntityType.OCELOT;
				case PIG:
					return EntityType.PIG;
				case RABBIT:
					return EntityType.RABBIT;
				case SHEEP:
					return EntityType.SHEEP;
				case SILVERFISH:
					return EntityType.SILVERFISH;
				case SKELETON:
					return EntityType.SKELETON;
				case SLIME:
					return EntityType.SLIME;
				case SNOWMAN:
					return EntityType.SNOWMAN;
				case SPIDER:
					return EntityType.SPIDER;
				case SQUID:
					return EntityType.SQUID;
				case VILLAGER:
					return EntityType.VILLAGER;
				case WITCH:
					return EntityType.WITCH;
				case WOLF:
					return EntityType.WOLF;
				case ZOMBIE:
					return EntityType.ZOMBIE;
				case ZOMBIE_PIGMAN:
					return EntityType.PIG_ZOMBIE;
				default:
					return null;
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
		SNOWMAN_ATTACK,
		FLAME_THROWER,
		GRAPPLING_HOOK;
		
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
				case FLAME_THROWER:
					return "§e§lFlame Thrower";
				case GRAPPLING_HOOK:
					return "§7§lGrappling Hook";
				default:
					return null;
			}
		}
		
		public String getDatabaseName(){
			switch(this){
				case BOOK_EXPLOSION:
					return "BookExplosion";
				case CREEPER_LAUNCHER:
					return "CreeperLauncher";
				case MAGMACUBE_SOCCER:
					return "MagmaCubeSoccer";
				case PAINTBALLS:
					return "Paintballs";
				case PET_RIDE:
					return "PetRide";
				case SNOWMAN_ATTACK:
					return "SnowmanAttack";
				case SWAP_TELEPORTER:
					return "SwapTeleporter";
				case FLAME_THROWER:
					return "FlameThrower";
				case GRAPPLING_HOOK:
					return "GrapplingHook";
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
				case FLAME_THROWER:
					return 575;
				case GRAPPLING_HOOK:
					return 1250;
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
				case FLAME_THROWER:
					return Material.BLAZE_POWDER;
				case GRAPPLING_HOOK:
					return Material.FISHING_ROD;
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
		
		ACACIA_WOOD,
		RED_WOOL,
		BROWN_GLASS,
		SOUL_SAND,
		CHISELLED_STONE_BRICKS,
		BOOKSHELF,
		NETHERRACK,
		CYAN_GLASS,
		
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
		
		public VIPRank getVIPRank(){
			switch(this){
				case DIAMOND_BLOCK:
					return VIPRank.Diamond_VIP;
				case DIAMOND_ORE:
					return VIPRank.Diamond_VIP;
				case EMERALD_BLOCK:
					return VIPRank.Emerald_VIP;
				case EMERALD_ORE:
					return VIPRank.Emerald_VIP;
				case GOLD_BLOCK:
					return VIPRank.Gold_VIP;
				case GOLD_ORE:
					return VIPRank.Gold_VIP;
				case IRON_BLOCK:
					return VIPRank.Iron_VIP;
				case IRON_ORE:
					return VIPRank.Iron_VIP;
				default:
					return null;
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
				case ACACIA_WOOD:
					return Material.WOOD;
				case RED_WOOL:
					return Material.WOOL;
				case BROWN_GLASS:
					return Material.STAINED_GLASS;
				case SOUL_SAND:
					return Material.SOUL_SAND;
				case CHISELLED_STONE_BRICKS:
					return Material.SMOOTH_BRICK;
				case BOOKSHELF:
					return Material.BOOKSHELF;
				case NETHERRACK:
					return Material.NETHERRACK;
				case CYAN_GLASS:
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
				case ACACIA_WOOD:
					return 4;
				case RED_WOOL:
					return 14;
				case BROWN_GLASS:
					return 12;
				case CHISELLED_STONE_BRICKS:
					return 3;
				case CYAN_GLASS:
					return 9;
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
				case ACACIA_WOOD:
					return 75;
				case RED_WOOL:
					return 100;
				case BROWN_GLASS:
					return 125;
				case SOUL_SAND:
					return 100;
				case CHISELLED_STONE_BRICKS:
					return 100;
				case BOOKSHELF:
					return 100;
				case NETHERRACK:
					return 75;
				case CYAN_GLASS:
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
					return "§eYellow Stained Glass Hat";
				case ACACIA_WOOD:
					return "§6Acacia Wood Hat";
				case RED_WOOL:
					return"§cRed Wool Hat";
				case BROWN_GLASS:
					return "§fBrown Stained Glass Hat";
				case SOUL_SAND:
					return "§eSoul Sand Hat";
				case CHISELLED_STONE_BRICKS:
					return "§7Chiselled Stone Bricks Hat";
				case BOOKSHELF:
					return "§eBookshelf Hat";
				case NETHERRACK:
					return "§cNetherrack Hat";
				case CYAN_GLASS:
					return "§3Cyan Stained Glass Hat";
				default:
					return null;
			}
		}
		
		public String getDatabaseName(){
			switch(this){
				case ANDESITE:
					return "Andesite";
				case BEDROCK:
					return "Bedrock";
				case BLACK_GLASS:
					return "BlackGlass";
				case BLUE_GLASS:
					return "BlueGlass";
				case CACTUS:
					return "Cactus";
				case CHEST:
					return "Chest";
				case COAL_BLOCK:
					return "CoalBlock";
				case COAL_ORE:
					return "CoalOre";
				case DARK_PRISMARINE:
					return "DarkPrismarine";
				case DIORITE:
					return "Diorite";
				case FURNACE:
					return "Furnace";
				case GLASS:
					return "Glass";
				case GLOWSTONE:
					return "Glowstone";
				case GRANITE:
					return "Granite";
				case GRASS:
					return "Grass";
				case GREEN_GLASS:
					return "GreenGlass";
				case HAY_BALE:
					return "HayBale";
				case ICE:
					return "Ice";
				case LAPIS_BLOCK:
					return "LapisBlock";
				case LAPIS_ORE:
					return "LapisOre";
				case LEAVES:
					return "Leaves";
				case MAGENTA_GLASS:
					return "MagentaGlass";
				case MELON:
					return "Melon";
				case MYCELIUM:
					return "Mycelium";
				case ORANGE_GLASS:
					return "OrangeGlass";
				case PRISMARINE_BRICKS:
					return "PrismarineBricks";
				case QUARTZ_BLOCK:
					return "QuartzBlock";
				case QUARTZ_ORE:
					return "QuartzOre";
				case REDSTONE_BLOCK:
					return "RedstoneBlock";
				case REDSTONE_ORE:
					return "RedstoneOre";
				case RED_GLASS:
					return "RedGlass";
				case SEA_LANTERN:
					return "SeaLantern";
				case SLIME_BLOCK:
					return "SlimeBlock";
				case SNOW:
					return "Snow";
				case SPONGE:
					return "Sponge";
				case STONE_BRICKS:
					return "StoneBricks";
				case TNT:
					return "TNT";
				case WET_SPONGE:
					return "WetSponge";
				case WORKBENCH:
					return "Workbench";
				case YELLOW_GLASS:
					return "YellowGlass";
				case ACACIA_WOOD:
					return "AcaciaWood";
				case RED_WOOL:
					return "RedWool";
				case BROWN_GLASS:
					return "BrownGlass";
				case SOUL_SAND:
					return "SoulSand";
				case CHISELLED_STONE_BRICKS:
					return "ChiselledStoneBricks";
				case BOOKSHELF:
					return "Bookshelf";
				case NETHERRACK:
					return "Netherrack";
				case CYAN_GLASS:
					return "CyanGlass";
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
		
		public void updateNPC(Entity entity){
			if(!isOnline()){
				entity.setCustomName("§4§lOffline");
			}
			else{
				entity.setCustomName("§7Players in " + getName() + "§7: §6§l" + getOnlinePlayers() + "§7/§6§l" + getMaxPlayers());
			}
			entity.setCustomNameVisible(true);
		}
		
		public boolean isOnline(){
			if(ServerStorage.onlineplayers.containsKey(this)){
				return ServerStorage.onlineplayers.get(this) != -1;
			}
			return false;
		}
		
		public int getOnlinePlayers(){
			if(ServerStorage.onlineplayers.containsKey(this)){
				return ServerStorage.onlineplayers.get(this);
			}
			return -1;
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
		
		public List<String> getVoteRewardsMessages(){
			switch(this){//TODO
				case ALPHA:
					return null;
				case CREATIVE:
					return null;
				case HUB:
					return Arrays.asList("§e§l1 OrbitMines Token");
				case KITPVP:
					return null;
				case MINIGAMES:
					return null;
				case PRISON:
					return null;
				case SKYBLOCK:
					return null;
				case SURVIVAL:
					return null;
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
		
		public String getColor(){
			switch(this){
				case Builder:
					return "§d";
				case Moderator:
					return "§b";
				case Owner:
					return "§4";
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
					return "§7AngryVillager Trail";
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
		
		public String getDatabaseName(){
			switch(this){
			case ANGRY_VILLAGER:
				return "AngryVillager";
			case BUBBLE:
				return "Bubble";
			case CRIT:
				return "Crit";
			case ENCHANTMENT_TABLE:
				return "ETable";
			case FIREWORK_SPARK:
				return "Firework";
			case HEART:
				return "Hearts";
			case MOB_SPAWNER:
				return "MobSpawner";
			case MUSIC:
				return "Music";
			case SLIME:
				return "Slime";
			case SMOKE:
				return "Smoke";
			case SNOW:
				return "Snow";
			case TNT:
				return "Explode";
			case WATER:
				return "Water";
			case WITCH:
				return "Witch";
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
		
		public VIPRank getVIPRank(){
			switch(this){
				case HAPPY_VILLAGER:
					return VIPRank.Emerald_VIP;
				case LAVA:
					return VIPRank.Diamond_VIP;
				case MAGIC:
					return VIPRank.Iron_VIP;
				case RAINBOW:
					return VIPRank.Gold_VIP;
				default:
					return null;
			}
		}
		
		public EnumParticle getEnumParticle(){
			switch(this){
				case ANGRY_VILLAGER:
					return EnumParticle.VILLAGER_ANGRY;
				case BUBBLE:
					return EnumParticle.SPELL;
				case CRIT:
					return EnumParticle.CRIT;
				case ENCHANTMENT_TABLE:
					return EnumParticle.ENCHANTMENT_TABLE;
				case FIREWORK_SPARK:
					return EnumParticle.FIREWORKS_SPARK;
				case HAPPY_VILLAGER:
					return EnumParticle.VILLAGER_HAPPY;
				case HEART:
					return EnumParticle.HEART;
				case LAVA:
					return EnumParticle.LAVA;
				case MAGIC:
					return EnumParticle.CRIT_MAGIC;
				case MOB_SPAWNER:
					return EnumParticle.FLAME;
				case MUSIC:
					return EnumParticle.NOTE;
				case RAINBOW:
					return EnumParticle.REDSTONE;
				case SLIME:
					return EnumParticle.SLIME;
				case SMOKE:
					return EnumParticle.SMOKE_LARGE;
				case SNOW:
					return EnumParticle.SNOW_SHOVEL;
				case TNT:
					return EnumParticle.EXPLOSION_NORMAL;
				case WATER:
					return EnumParticle.WATER_SPLASH;
				case WITCH:
					return EnumParticle.SPELL_WITCH;
				default:
					return null;
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
		VERTICAL_TRAIL,
		CYLINDER_TRAIL,
		ORBIT_TRAIL,
		SNAKE_TRAIL;
		
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
				case CYLINDER_TRAIL:
					return "§7§lCylinder Trail";
				case ORBIT_TRAIL:
					return "§7§lOrbit Trail";
				case SNAKE_TRAIL:
					return "§7§lSnake Trail";
				default:
					return null;
			}
		}
		
		public String getDatabaseName(){
			switch(this){
				case BIG_TRAIL:
					return "TypeBig";
				case BODY_TRAIL:
					return "TypeBody";
				case GROUND_TRAIL:
					return "TypeGround";
				case HEAD_TRAIL:
					return "TypeHead";
				case VERTICAL_TRAIL:
					return "TypeVertical";
				case CYLINDER_TRAIL:
					return "TypeCylinder";
				case ORBIT_TRAIL:
					return "TypeOrbit";
				case SNAKE_TRAIL:
					return "TypeSnake";
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
				case CYLINDER_TRAIL:
					return 1500;
				case ORBIT_TRAIL:
					return 1750;
				case SNAKE_TRAIL:
					return 1000;
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
				default:
					return "§8";
			}
		}
		
		public String getColor(){
			switch(this){
				case Diamond_VIP:
					return "§9";
				case Emerald_VIP:
					return "§a";
				case Gold_VIP:
					return "§6";
				case Iron_VIP:
					return "§7";
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
