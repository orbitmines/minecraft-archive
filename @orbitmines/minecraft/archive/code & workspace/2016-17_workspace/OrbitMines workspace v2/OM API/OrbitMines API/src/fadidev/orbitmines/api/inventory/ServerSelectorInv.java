package fadidev.orbitmines.api.inventory;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.enums.Server;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ServerSelectorInv extends OMInventory {
	
	private OrbitMinesAPI api;
	
	public ServerSelectorInv(){
		setInventory(Bukkit.createInventory(null, 27, "§0§lServer Selector"));

		this.api = OrbitMinesAPI.getApi();
	}
	
	@Override
	public void open(Player player){
		player.openInventory(getInventory());

		registerLast(player);
	}

	@Override
	public void onClick(OMPlayer omp, InventoryClickEvent e) {
		e.setCancelled(true);

		ItemStack item = e.getCurrentItem();

		if(ItemUtils.isNull(item))
		    return;

        for(Server server : Server.values()){
            if(item.getType() == server.getMaterial()){
                omp.toServer(server);
                break;
            }
        }
	}

	public void update(){
		getInventory().setItem(10, getItem(Server.KITPVP));
		getInventory().setItem(11, getItem(Server.PRISON));
		getInventory().setItem(12, getItem(Server.CREATIVE));
		getInventory().setItem(13, getItem(Server.HUB));
		getInventory().setItem(14, getItem(Server.SURVIVAL));
		getInventory().setItem(15, getItem(Server.SKYBLOCK));
		getInventory().setItem(16, getItem(Server.MINIGAMES));
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getName() != null && p.getOpenInventory().getTopInventory().getName().equals(getInventory().getName()))
				p.openInventory(getInventory());
		}
	}
	
	private ItemStack getItem(Server server){
		ItemStack item = new ItemStack(server.getMaterial(), 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(server.getName() + " §8§ §7§l" + server.getVersion());
		List<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(" §7Status: " + server.statusString() + " ");
		if(server.isOnline())
			lore.add(" §7Players: §a§l" + api.getOnlinePlayers(server) + "§7/§a§l" + server.getMaxPlayers() + " ");

		lore.add("");
		if(server.isOnline()){
			if(api.getServerType() != server)
				lore.add(" §7§oKlik hier om te verbinden ");
			else
				lore.add(" §7§oVerbonden ");

			lore.add("");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);

		if(api.getServerType() == server){
			item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			item = api.getNms().customItem().hideFlags(item, 1, 2);
		}
		else{
			item = api.getNms().customItem().hideFlags(item, 2);
		}
		
		return item;
	}
}
