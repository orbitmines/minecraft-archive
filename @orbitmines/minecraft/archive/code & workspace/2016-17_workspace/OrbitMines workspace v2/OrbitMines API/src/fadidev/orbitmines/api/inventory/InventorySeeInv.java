package fadidev.orbitmines.api.inventory;

import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventorySeeInv extends OMInventory {
	
	public InventorySeeInv(Player player){
		if(player != null){
			setInventory(Bukkit.createInventory(null, 45, "§0§l" + player.getName() + "'s Inventory"));

			getInventory().setContents(getContents(player));
		}
		else{
			setInventory(Bukkit.createInventory(null, 45, "'s Inventory"));
		}
	}
	
	@Override
	public void open(Player player){
		player.openInventory(getInventory());

		registerLast(player);
	}

	@Override
	public void onClick(OMPlayer omp, InventoryClickEvent e) {
		e.setCancelled(true);
	}
	
	private ItemStack[] getContents(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		PlayerInventory inv = player.getInventory();
		for(int i = 0; i < 27; i++){
			contents[i] = inv.getItem(i + 9);
		}
		for(int i = 27; i < 36; i++){
			contents[i] = inv.getItem(i - 27);
		}
		contents[36] = inv.getHelmet();
		contents[37] = inv.getChestplate();
		contents[38] = inv.getLeggings();
		contents[39] = inv.getBoots();
		
		return contents;
	}
}
