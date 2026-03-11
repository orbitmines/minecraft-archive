package om.api.invs.others;

import java.util.ArrayList;
import java.util.List;

import om.api.API;
import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.api.utils.enums.Server;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServerSelectorInv extends InventoryInstance {
	
	private API api;
	
	public ServerSelectorInv(){
		Inventory inventory = Bukkit.createInventory(null, 27, "§0§lServer Selector");
		this.inventory = inventory;
		this.api = API.getInstance();
	}
	
	@Override
	public void open(Player player){
		player.openInventory(getInventory());
	}
	
	public void update(){
		this.inventory.setItem(10, getItem(Server.KITPVP));
		this.inventory.setItem(11, getItem(Server.PRISON));
		this.inventory.setItem(12, getItem(Server.CREATIVE));
		this.inventory.setItem(13, getItem(Server.HUB));
		this.inventory.setItem(14, getItem(Server.SURVIVAL));
		this.inventory.setItem(15, getItem(Server.SKYBLOCK));
		this.inventory.setItem(16, getItem(Server.MINIGAMES));
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getName() != null && p.getOpenInventory().getTopInventory().getName().equals(getInventory().getName())){
				p.openInventory(getInventory());
			}
		}
	}
	
	private ItemStack getItem(Server server){
		ItemStack item = new ItemStack(server.getMaterial(), 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(server.getName() + " §8» §7§l" + server.getVersion());
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(" §7Status: " + server.statusString() + " ");
		if(server.isOnline()){
			lore.add(" §7Players: §a§l" + api.getOnlinePlayers(server) + "§7/§a§l" + server.getMaxPlayers() + " ");
		}
		lore.add("");
		if(server.isOnline()){
			if(!api.isServer(server)){
				lore.add(" §7§oClick Here to Connect ");
			}
			else{
				lore.add(" §7§oCurrently Connected ");
			}
			lore.add("");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
      
		if(api.isServer(server)){
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			item = ItemUtils.hideFlags(item, 1, 2);
		}
		else{
			item = ItemUtils.hideFlags(item, 2);
		}
		
		return item;
	}
}
