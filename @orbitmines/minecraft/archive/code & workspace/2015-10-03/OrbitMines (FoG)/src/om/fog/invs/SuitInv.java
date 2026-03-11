package om.fog.invs;

import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Suit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SuitInv extends InventoryInstance {
	
	public SuitInv(Suit suit, Faction faction){
		Inventory inventory = Bukkit.createInventory(null, 9, "§0§l" + suit.getName(faction));
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player) {
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContents(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		{
			ItemStack item = new ItemStack(Material.STONE_SWORD);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Swords");
			item.setItemMeta(meta);
			
			contents[1] = ItemUtils.hideFlags(item, 2);
		}
		{
			ItemStack item = new ItemStack(Material.IRON_FENCE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Shields");
			item.setItemMeta(meta);
			
			contents[3] = item;
		}
		{
			ItemStack item = new ItemStack(Material.WOOD_PICKAXE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§6Tools");
			item.setItemMeta(meta);
			
			contents[5] = ItemUtils.hideFlags(item, 2);
		}
		{
			ItemStack item = new ItemStack(Material.BOW);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§6Bows");
			item.setItemMeta(meta);
			
			contents[7] = item;
		}
		
		return contents;
	}
}
