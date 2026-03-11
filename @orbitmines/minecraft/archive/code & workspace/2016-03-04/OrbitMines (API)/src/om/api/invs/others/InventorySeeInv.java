package om.api.invs.others;

import om.api.invs.InventoryInstance;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventorySeeInv extends InventoryInstance {
	
	public InventorySeeInv(Player player){
		if(player != null){
			Inventory inventory = Bukkit.createInventory(null, 45, "§0§l" + player.getName() + "'s Inventory");
			this.inventory = inventory;
			this.inventory.setContents(getContects(player));
		}
		else{
			Inventory inventory = Bukkit.createInventory(null, 45, "'s Inventory");
			this.inventory = inventory;
		}
	}
	
	@Override
	public void open(Player player){
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContects(Player player){
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
