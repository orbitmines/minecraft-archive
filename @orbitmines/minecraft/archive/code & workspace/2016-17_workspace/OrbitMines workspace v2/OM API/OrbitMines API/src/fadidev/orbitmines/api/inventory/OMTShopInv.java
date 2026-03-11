package fadidev.orbitmines.api.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class OMTShopInv extends OMInventory {
	
	public OMTShopInv(){
		setInventory(Bukkit.createInventory(null, 27, "§0§lOMT Shop"));
	}
	
	protected abstract ItemStack[] getContents(Player player);
	
	@Override
	public void open(Player player){
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());

		registerLast(player);
	}
}
