package om.api.invs;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class InventoryInstance {

	protected Inventory inventory;
	
	protected abstract void open(Player player);
	
	public Inventory getInventory(){
		return inventory;
	}
	public void setInventory(Inventory inventory){
		this.inventory = inventory;
	}
}
