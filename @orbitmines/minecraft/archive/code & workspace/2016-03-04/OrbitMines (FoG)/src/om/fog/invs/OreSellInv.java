package om.fog.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.invs.InventoryInstance;
import om.fog.utils.enums.Ore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OreSellInv extends InventoryInstance {
	
	public OreSellInv(){
		Inventory inventory = Bukkit.createInventory(null, 9, "§0§lSell Ores");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player) {
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContents(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		for(Ore ore : Ore.values()){
			ItemStack item = new ItemStack(Material.INK_SACK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Sell " + ore.getName());
			List<String> lore = new ArrayList<String>();
			lore.add("§7Reward: §l" + ore.getSellPrice() + " Silver");
			meta.setLore(lore);
			item.setItemMeta(meta);
			item.setDurability(ore.getDurability());
			
			contents[ore.getSlot()] = item;
		}
		
		return contents;
	}
}
