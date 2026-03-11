package om.api.invs.others;

import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class TeleporterInv extends InventoryInstance {
	
	public TeleporterInv(){
		Inventory inventory = Bukkit.createInventory(null, 27, "§0§lTeleporter");
		this.inventory = inventory;
	}
	
	protected abstract List<OMPlayer> getPlayers();
	protected abstract ItemStack getItem(OMPlayer omp);
	
	@Override
	public void open(Player player){
		player.openInventory(getInventory());
	}
	
	public void update(){
		List<OMPlayer> players = getPlayers();
		
		if(players.size() > 45){
			inventory = Bukkit.createInventory(null, 54, "§0§lTeleporter");
		}
		else if(players.size() > 36){
			inventory = Bukkit.createInventory(null, 45, "§0§lTeleporter");
		}
		else if(players.size() > 27){
			inventory = Bukkit.createInventory(null, 36, "§0§lTeleporter");
		}
		else if(players.size() > 18){
			inventory = Bukkit.createInventory(null, 27, "§0§lTeleporter");
		}
		else if(players.size() > 9){
			inventory = Bukkit.createInventory(null, 18, "§0§lTeleporter");
		}
		else{
			inventory = Bukkit.createInventory(null, 9, "§0§lTeleporter");
		}
		
		int index = 0;
		for(OMPlayer omplayer : players){
			if(index <= 53){
				inventory.setItem(index, getItem(omplayer));
			}
			
			index++;
		}
	}
}

