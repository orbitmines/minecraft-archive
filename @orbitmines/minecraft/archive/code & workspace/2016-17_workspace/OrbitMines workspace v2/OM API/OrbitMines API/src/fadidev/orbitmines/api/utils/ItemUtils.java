package fadidev.orbitmines.api.utils;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class ItemUtils {

	public static boolean isNull(ItemStack item){
		return item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null;
	}

	public static ItemStack getSkull(String playerName){
		ItemStack item = new ItemStack(Material.SKULL_ITEM);
		item.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(playerName);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack getSkull(String playerName, String displayName){
		ItemStack item = new ItemStack(Material.SKULL_ITEM);
		item.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setOwner(playerName);
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

	public static ItemStack addEffect(ItemStack item, PotionEffectType type, int duration, int amplifier, boolean hide){
		if(item.getType() == Material.POTION || item.getType() == Material.SPLASH_POTION){
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			meta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
			item.setItemMeta(meta);
		}

		return hide ? OrbitMinesAPI.getApi().getNms().customItem().hideFlags(item, 2, 32) : item;
	}
	
	public static ItemStack itemstack(Material material, int amount, String displayName){
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
		
		return item;
	}

    public static ItemStack itemstack(Material material, int amount, String displayName, int durability){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        item.setDurability((short) durability);

        return item;
    }

    public static ItemStack itemstack(Material material, int amount, int durability){
        ItemStack item = new ItemStack(material, amount);
        item.setDurability((short) durability);

        return item;
    }

    public static ItemStack itemstack(Material material, int amount, String displayName, int durability, String... lore){
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        item.setDurability((short) durability);

        return item;
    }
	
	public static ItemStack addEnchantment(ItemStack item, Enchantment enchantment, int level){
		item.addUnsafeEnchantment(enchantment, level);
		
		return item;
	}
}
