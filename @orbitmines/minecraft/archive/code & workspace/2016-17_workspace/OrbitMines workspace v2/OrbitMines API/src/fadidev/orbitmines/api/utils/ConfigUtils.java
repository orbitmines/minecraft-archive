package fadidev.orbitmines.api.utils;

import fadidev.orbitmines.api.handlers.StringInt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ConfigUtils {
	
	public static String parseString(Location l){
		if(l != null){
			return l.getWorld().getName() + "|" + l.getX() + "|" + l.getY() + "|" + l.getZ() + "|" + l.getYaw() + "|" + l.getPitch();
		}
		return null;
	}
	
	public static Location parseLocation(String s){
		if(s != null){
			String[] data = s.split("\\|");
			return new Location(Bukkit.getWorld(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]));
		}
		return null;
	}
	
	public static List<String> parseStringIntList(List<StringInt> stringIntList){
		List<String> stringlist = new ArrayList<>();
		for(StringInt si : stringIntList){
			stringlist.add(si.getString() + "|" + si.getInteger());
		}
		return stringlist;
	}
	
	public static List<UUID> parseUUIDList(List<String> uuidStringList){
		List<UUID> uuids = new ArrayList<>();
		for(String uuid : uuidStringList){
			uuids.add(UUID.fromString(uuid));
		}
		return uuids;
	}
	
	public static List<String> parseStringList(List<UUID> uuidList){
		List<String> uuids = new ArrayList<String>();
		for(UUID uuid : uuidList){
			uuids.add(uuid.toString());
		}
		return uuids;
	}
	
	public static List<Location> parseLocationList(List<String> locationStringList){
		List<Location> locations = new ArrayList<Location>();
		if(locationStringList != null){
			for(String location : locationStringList){
				locations.add(parseLocation(location));
			}
		}
		return locations;
	}
	
	public static  String parseString(ItemStack item){
		// TYPE|AMOUNT|DURABILITY|Enchantments:ENCHANTMENT(LEVEL):ENCHANTMENT(LEVEL)|DISPLAYNAME|LORE;LORE
		if(item != null){
			String enchantmentsString = "Enchantments";
			java.util.Map<Enchantment, Integer> enchantments = item.getEnchantments();
			for(Enchantment ench : enchantments.keySet()){
				if(enchantments.get(ench) > 0){
					enchantmentsString = enchantmentsString + ":" + ench.getName() + "(" + enchantments.get(ench) + ")";
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
			
			return item.getType().toString() + "|" + item.getAmount() + "|" + item.getDurability() + "|" + enchantmentsString + "|" + item.getItemMeta().getDisplayName() + "|" + itemlorestring;
		}
		return null;
	}
	
	public static ItemStack parseItemStack(String itemStackString){
		if(itemStackString != null){
			String[] data = itemStackString.split("\\|");
			
			ItemStack item = new ItemStack(Material.valueOf(data[0]), Integer.parseInt(data[1]));
			item.setDurability(Short.parseShort(data[2]));
	
			if(data[3].contains(":")){
				String[] enchData = data[3].split("\\:");
				for(String ench : enchData){
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
}
