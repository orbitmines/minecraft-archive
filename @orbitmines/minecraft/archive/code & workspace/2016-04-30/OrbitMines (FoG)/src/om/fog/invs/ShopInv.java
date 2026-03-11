package om.fog.invs;

import om.api.invs.InventoryInstance;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopInv extends InventoryInstance {
	
	public ShopInv(){
		Inventory inventory = Bukkit.createInventory(null, 9, "§0§lShop");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player) {
		FoGPlayer omp = FoGPlayer.getFoGPlayer(player);
		if(omp.isInTutorial() && omp.getTutorial().getStage() == 7){
			omp.getTutorial().toNextStage();
		}
		
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContents(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		{
			ItemStack item = new ItemStack(Material.INK_SACK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Sell Ores");
			item.setItemMeta(meta);
			item.setDurability((short) 1);
			
			contents[2] = item;
		}
		{
			ItemStack item = new ItemStack(Material.BOOK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Buy Enchantment Books");
			item.setItemMeta(meta);
			
			contents[6] = item;
		}
		
		return contents;
	}
}
