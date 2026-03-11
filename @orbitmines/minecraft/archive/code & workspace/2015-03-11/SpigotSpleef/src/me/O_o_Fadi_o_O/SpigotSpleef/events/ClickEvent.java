package me.O_o_Fadi_o_O.SpigotSpleef.events;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.ArenaManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.MapManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		HumanEntity he = e.getWhoClicked();
		
		if(he instanceof Player){
			final Player p = (Player) he;
			ItemStack c = e.getCurrentItem();	
			
			try{
				if(c != null){
					//Map Setup
					if(e.getInventory().getName().startsWith("§0§lMap") && StorageManager.mapsetup.containsKey(p)){
						e.setCancelled(true);
						
						if(c.getType() == Material.NAME_TAG && c.getItemMeta().getDisplayName().equals("§e§lSet Map Name")){
							MapManager.openMapNameEditor(p, StorageManager.mapsetup.get(p).getMapID());
						}
						if(c.getType() == Material.ENDER_PEARL && c.getItemMeta().getDisplayName().equals("§3§lSet Next Spawnpoint")){
							MapManager.setNextSpawnpoint(p);
						}
						if(c.getType() == Material.EYE_OF_ENDER && c.getItemMeta().getDisplayName().equals("§a§lSet Spectator Spawnpoint")){
							MapManager.setSpectatorSpawnpoint(p);
						}
						if(c.getType() == Material.STAINED_GLASS_PANE && c.getItemMeta().getDisplayName().equals("§2§lFinish Setup") && c.getItemMeta().getLore().get(0).equals(" §7Setup Complete: §a§l✔ ")){
							MapManager.finishSetup(p);
						}
					}
					//Arena Setup
					if(e.getInventory().getName().startsWith("§0§lArena") && StorageManager.arenasetup.containsKey(p)){
						e.setCancelled(true);
						
						if(c.getType() == Material.ENDER_PEARL && c.getItemMeta().getDisplayName().equals("§3§lSet Lobby Spawnpoint")){
							ArenaManager.setLobbySpawnpoint(p);
						}
						if(c.getType() == Material.STAINED_GLASS_PANE && c.getItemMeta().getDisplayName().equals("§2§lFinish Setup") && c.getItemMeta().getLore().get(0).equals(" §7Setup Complete: §a§l✔ ")){
							ArenaManager.finishSetup(p);
						}
					}
				}
			}catch(NullPointerException ex){ex.printStackTrace();}
		}
	}
}
