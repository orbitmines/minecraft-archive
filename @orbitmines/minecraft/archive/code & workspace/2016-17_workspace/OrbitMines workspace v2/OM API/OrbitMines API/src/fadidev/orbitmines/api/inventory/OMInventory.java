package fadidev.orbitmines.api.inventory;

import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class OMInventory {

	private Inventory inventory;

	public abstract void open(Player player);
    public abstract void onClick(OMPlayer omp, InventoryClickEvent event);

	public Inventory getInventory(){
		return inventory;
	}

	public void setInventory(Inventory inventory){
		this.inventory = inventory;
	}

	public void registerLast(Player player){
        OMPlayer.getOMPlayer(player).setLastInventory(this);
    }
}
