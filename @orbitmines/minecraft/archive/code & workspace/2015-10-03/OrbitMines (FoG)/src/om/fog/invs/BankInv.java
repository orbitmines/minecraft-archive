package om.fog.invs;

import om.api.invs.InventoryInstance;
import om.fog.handlers.Bank;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BankInv extends InventoryInstance {
	
	private int page;
	
	public BankInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lBank");
		this.inventory = inventory;
		this.inventory.setContents(getBaseContents());
		this.page = 1;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	@Override
	public void open(Player player) {
		setBankContents(player);
		player.openInventory(getInventory());
	}
	
	private void setBankContents(Player player){
		FoGPlayer omp = FoGPlayer.getFoGPlayer(player);
		Bank bank = omp.getBank();
		ItemStack[] items = bank.getItems();
		
		ItemStack lItem = new ItemStack(Material.BARRIER);
		ItemMeta meta = lItem.getItemMeta();
		meta.setDisplayName("§4§lLocked");
		lItem.setItemMeta(meta);
		
		int index = 9 * (page -1);
		
		int slot = 0;
		for(int i = index; i < 36 + index; i++){
			if(bank.getSize() > i){
				inventory.setItem(slot, items[i]);
			}
			else{
				inventory.setItem(slot, lItem);
			}
			slot++;
		}
	}
	
	private ItemStack[] getBaseContents(){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(" ");
			item.setItemMeta(meta);
			item.setDurability((short) 15);
			
			contents[36] = item;
			contents[37] = item;
			contents[38] = item;
			contents[39] = item;
			contents[40] = item;
			contents[41] = item;
			contents[42] = item;
			contents[43] = item;
			contents[44] = item;
		}
		
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(" ");
			item.setItemMeta(meta);
			item.setDurability((short) 8);
			
			contents[46] = item;
			contents[47] = item;
			contents[48] = item;
			contents[50] = item;
			contents[51] = item;
			contents[52] = item;
		}
		
		{
			ItemStack item = new ItemStack(Material.EMPTY_MAP);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§e▲§l Scroll Up");
			item.setItemMeta(meta);
			
			contents[45] = item;
		}
		{
			ItemStack item = new ItemStack(Material.EMPTY_MAP);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§e▼§l Scroll Down");
			item.setItemMeta(meta);
			
			contents[53] = item;
		}
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§4§lDestroy Item");
			item.setItemMeta(meta);
			item.setDurability((short) 14);
			
			contents[49] = item;
		}
		
		return contents;
	}
}
