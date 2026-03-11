package me.O_o_Fadi_o_O.SpigotSpleef.managers;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.utils.Arena;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.ArenaSelector;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.ArenaSelectorItemStack;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.ItemType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArenaSelectorManager {

	public static void loadArenaSelector(){
		String path = "Settings.NPCInventory.ArenaSelector";
		
		int size = ConfigManager.config.getInt(path + ".Size");
		String name = ConfigManager.config.getString(path + ".Name");
		Inventory inv = Bukkit.createInventory(null, size, name.replace("&", "§"));
		List<ArenaSelectorItemStack> itemstacks = new ArrayList<ArenaSelectorItemStack>();
		
		for(int slot = 1; slot <= size; slot++){
			if(ConfigManager.config.contains(path + ".Inventory." + slot)){
				ItemType type = ItemType.valueOf(ConfigManager.config.getString(path + ".Inventory." + slot + ".ItemType"));
				
				if(type == ItemType.NORMAL){
					String displayname = ConfigManager.config.getString(path + ".Inventory." + slot + ".Name");
					List<String> lore = ConfigManager.config.getStringList(path + ".Inventory." + slot + ".Lore");
					Material material = Material.valueOf(ConfigManager.config.getString(path + ".Inventory." + slot + ".Type"));
					short durability = (short) ConfigManager.config.getInt(path + ".Inventory." + slot + ".Durability");
					int amount = ConfigManager.config.getInt(path + ".Inventory." + slot + ".Amount");
					
					ItemStack item = new ItemStack(material, amount);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(displayname);
					meta.setLore(lore);
					item.setItemMeta(meta);
					item.setDurability(durability);
					itemstacks.add(new ArenaSelectorItemStack(item, displayname, lore, ItemType.NORMAL));
				}
				else if(type == ItemType.ARENA){
					int arenaid = ConfigManager.config.getInt(path + ".Inventory." + slot + ".ArenaID");
					
					itemstacks.add(new ArenaSelectorItemStack(ItemType.ARENA, arenaid, Arena.getArenaFromID(arenaid)));
				}
				else{
					itemstacks.add(null);
				}
			}
			else{
				itemstacks.add(null);
			}
		}
		
		ArenaSelector arenaselector = new ArenaSelector(inv, size, name, itemstacks);
		StorageManager.arenaselector = arenaselector;
	}
}
