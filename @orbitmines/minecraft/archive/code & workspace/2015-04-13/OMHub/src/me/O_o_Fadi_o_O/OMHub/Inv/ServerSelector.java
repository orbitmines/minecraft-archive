package me.O_o_Fadi_o_O.OMHub.Inv;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OMHub.CustomItem;
import me.O_o_Fadi_o_O.OMHub.Hub;
import me.O_o_Fadi_o_O.OMHub.managers.StorageManager;
import me.O_o_Fadi_o_O.OMHub.runnables.ServerSelectorRunnable;
import me.O_o_Fadi_o_O.OMHub.utils.Server;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServerSelector {
	
	public static Inventory serverSelector = Bukkit.createInventory(null, 27, "§0§lServer Selector");
	
	Hub hub = Hub.getInstance();
	
	public static void setAllServerSelectorItems(){
		    
		setServerSelectorItem(Server.KITPVP, "§c§lKitPvP §8>> §7§l1.8", Material.IRON_SWORD, 10, "c");
		setServerSelectorItem(Server.PRISON, "§4§lPrison §8>> §7§l1.8", Material.DIAMOND_PICKAXE, 11, "4");
		setServerSelectorItem(Server.CREATIVE, "§d§lCreative §8>> §7§l1.8", Material.WOOD_AXE, 12, "d");
		setServerSelectorItem(Server.HUB, "§3§lHub §8>> §7§l1.8", Material.WATCH, 13, "3");
		setServerSelectorItem(Server.SURVIVAL, "§a§lSurvival §8>> §7§l1.8", Material.STONE_HOE, 14, "a");
		setServerSelectorItem(Server.SKYBLOCK, "§5§lSkyBlock §8>> §7§l1.8", Material.FISHING_ROD, 15, "5");
		setServerSelectorItem(Server.MINIGAMES, "§f§lMiniGames §8>> §7§l1.8", Material.BOW, 16, "f");
    
	}
  
	public static void setServerSelectorItem(Server server, String displayName, Material m, int slot, String color){
    
		ItemStack item = new ItemStack(m, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		List<String> lore = new ArrayList<String>();
		lore.add("");
		if(StorageManager.onlineplayers.get(server) != -1){
			lore.add(" §7Status: §a§lOnline ");
 
			if(server != Server.HUB){
				lore.add(" §7Players: §a§l" + StorageManager.onlineplayers.get(server) + "§7/§a§l100 ");
			}
			else{
				lore.add(" §7Players: §a§l" + StorageManager.onlineplayers.get(server) + "§7/§a§l1000 ");
			}
		}
		else{
			lore.add(" §7Status: §4§lOffline ");
		}
		lore.add("");
		if(StorageManager.onlineplayers.get(server) != -1){
			if(StorageManager.thisserver != server){
				if(ServerSelectorRunnable.i == 1){
					lore.add(" §" + color +"§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 2){
					lore.add(" §7§oC"
							+ "§" + color +"§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 3){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§" + color +"§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 4){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§" + color +"§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 5){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§" + color +"§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 6){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§" + color +"§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 7){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§" + color +"§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 8){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§" + color +"§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 9){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§" + color +"§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 10){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§" + color +"§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 11){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§" + color +"§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 12){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§" + color +"§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 13){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§" + color +"§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 14){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§" + color +"§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 15){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§" + color +"§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 16){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§" + color +"§oe"
							+ "§7§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 17){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§" + color +"§oc"
							+ "§7§ot"
							+ "§r ");
				}
				else if(ServerSelectorRunnable.i == 18){
					lore.add(" §7§oC"
							+ "§7§ol"
							+ "§7§oi"
							+ "§7§oc"
							+ "§7§ok"
							+ " "
							+ "§7§oH"
							+ "§7§oe"
							+ "§7§or"
							+ "§7§oe"
							+ " "
							+ "§7§ot"
							+ "§7§oo"
							+ " "
							+ "§7§oC"
							+ "§7§oo"
							+ "§7§on"
							+ "§7§on"
							+ "§7§oe"
							+ "§7§oc"
							+ "§" + color +"§ot"
							+ "§r ");
				}
			}
			else{
				lore.add(" §7§oCurrently Connected ");
			}
			lore.add("");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
      
		if(server == StorageManager.thisserver){
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			item = CustomItem.hideFlags(item, 1, 2);
		}
		else{
			item = CustomItem.hideFlags(item, 2);
		}
		serverSelector.setItem(slot, item);
	}
}
