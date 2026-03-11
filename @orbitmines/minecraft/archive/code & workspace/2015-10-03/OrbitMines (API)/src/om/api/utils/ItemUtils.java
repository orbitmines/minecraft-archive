package om.api.utils;

import java.util.List;

import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

	public static ItemStack getSkull(String playername){
		ItemStack item = new ItemStack(Material.SKULL_ITEM);
		item.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(playername);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack getSkull(String playername, String displayname){
		ItemStack item = new ItemStack(Material.SKULL_ITEM);
		item.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setDisplayName(displayname);
		meta.setOwner(playername);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack addColor(ItemStack item, Color color){
		if(item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_BOOTS){
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(color);
			item.setItemMeta(meta);
		}
		
		return item;
	}
	
	public static ItemStack setItem(ItemStack item, String displayname, int durability){
		ItemMeta meta = (ItemMeta) item.getItemMeta();
		meta.setDisplayName(displayname);
		item.setItemMeta(meta);
		item.setDurability((short) durability);
		
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
	        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
	
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
    		net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

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
