package me.O_o_Fadi_o_O.SpigotSpleef.managers;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.utils.AnvilGUI;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Message;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.MessageName;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NPCManager {
	
	public static void openNPCInventory(Player p, NPC npc){
		Inventory inv = Bukkit.createInventory(null, 27, "§0§lEdit NPC");
	
		{
			ItemStack item = new ItemStack(Material.NAME_TAG, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§e§lSet Map Name");
			List<String> lore = new ArrayList<String>();
			lore.add(" §7Current Name: §a" + npc.getDisplayname().replace("&", "§") + " ");
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(10, item);
		}
		{
			ItemStack item = new ItemStack(Material.MONSTER_EGG, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§6§lChange Mob");
			item.setItemMeta(meta);
			item.setDurability(npc.getSpawnEggID());
			inv.setItem(13, item);
		}
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§2§lSave");
			item.setItemMeta(meta);
			item.setDurability((short) 13);
			inv.setItem(16, item);
		}
		
		/*
		 * 
		 * 
		 * INVENTORY VERDER MAKEN (Name Editor + Mob Editor)
		 * CLICK EVENT VERDER MAKEN
		 * ArenaManager.saveArenaSelectors
		 * 
		 * Daarna Kit Buy / Selectors
		 * 
		 * 
		 */
		
		p.openInventory(inv);
	}
	
	public static void openNPCNameEditor(final Player p, final int mapid){
		Message m = Message.getMessage(MessageName.OPEN_MAP_SETUP_NAME_EDITOR);
		m.replace("&", "§");
		m.send(p);
		
		final AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler(){
			@Override
			public void onAnvilClick(AnvilGUI.AnvilClickEvent e){
				if(e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
					String s = e.getName();
					e.setWillClose(true);
					e.setWillDestroy(true);
					
					Message m = Message.getMessage(MessageName.SET_MAP_NAME);
					m.replace("&", "§");
					m.replace("%map-id%", "" + mapid);
					m.replace("%map-name%", s);
					m.send(p);
					StorageManager.mapsetup.get(p).setName(s);
				}
				else{
					e.setWillClose(false);
					e.setWillDestroy(false);
				}
			}
		});
		
		{
			ItemStack item = new ItemStack(Material.NAME_TAG, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("Insert Mapname");
			item.setItemMeta(meta);
			gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, item);
		}
	
		gui.open();
	}
}
