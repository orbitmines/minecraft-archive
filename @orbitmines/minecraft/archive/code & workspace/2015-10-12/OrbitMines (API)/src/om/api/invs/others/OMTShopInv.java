package om.api.invs.others;

import om.api.invs.InventoryInstance;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class OMTShopInv extends InventoryInstance {
	
	public OMTShopInv(){
		Inventory inventory = Bukkit.createInventory(null, 27, "§0§lOMT Shop");
		this.inventory = inventory;
	}
	
	protected abstract ItemStack[] getContects(Player player);
	
	@Override
	public void open(Player player){
		inventory.setContents(getContects(player));
		player.openInventory(getInventory());
	}
}
