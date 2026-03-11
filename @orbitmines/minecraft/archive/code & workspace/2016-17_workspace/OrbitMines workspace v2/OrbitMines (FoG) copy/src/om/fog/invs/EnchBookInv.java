package om.fog.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.invs.InventoryInstance;
import om.fog.utils.enums.Rarity;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchBookInv extends InventoryInstance {
	
	public EnchBookInv(){
		Inventory inventory = Bukkit.createInventory(null, 9, "§0§lBuy Enchantment Books");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player) {
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContents(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		int slot = 1;
		for(Rarity rarity : Rarity.values()){
			ItemStack item = new ItemStack(Material.BOOK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Buy " + rarity.getName() + " Enchantment §7Book");
			List<String> lore = new ArrayList<String>();
			lore.add("§7Price: §l" + rarity.getPrice() + " Silver");
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			contents[slot] = item;
			
			slot += 2;
		}
		
		return contents;
	}
}
