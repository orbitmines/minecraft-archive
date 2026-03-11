package om.fog.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.invs.InventoryInstance;
import om.fog.utils.enums.Faction;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FactionSelectInv extends InventoryInstance {
	
	public FactionSelectInv(){
		Inventory inventory = Bukkit.createInventory(null, 9, "§0§lSelect a Faction");
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
			String s = Faction.ALPHA.getColor();
			ItemStack item = new ItemStack(Material.STAINED_CLAY);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Join §l" + Faction.ALPHA.getName());
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(s + "§oThe Alphas are highly developed");
			lore.add(s + "§ohumans who are known for their");
			lore.add(s + "§otechnical expertise.");
			lore.add(s + "§oTheir homebase is located at the");
			lore.add(s + "§oCrystal Galaxies.");
			meta.setLore(lore);
			item.setItemMeta(meta);
			item.setDurability((short) 4);
			
			contents[2] = item;
		}
		{
			String s = Faction.BETA.getColor();
			ItemStack item = new ItemStack(Material.STAINED_CLAY);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Join §l" + Faction.BETA.getName());
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(s + "§oThe Betas are extraordinary");
			lore.add(s + "§ohumans who with recent technology");
			lore.add(s + "§omanaged to travel to different");
			lore.add(s + "§ogalaxies. ");
			lore.add(s + "§oThey built their homebase at");
			lore.add(s + "§othe Dark Galaxies.");
			meta.setLore(lore);
			item.setItemMeta(meta);
			item.setDurability((short) 3);
			
			contents[4] = item;
		}
		{
			String s = Faction.OMEGA.getColor();
			ItemStack item = new ItemStack(Material.STAINED_CLAY);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Join §l" + Faction.OMEGA.getName());
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add(s + "§oThe Omegas are creatures");
			lore.add(s + "§ofrom an acient race that");
			lore.add(s + "§ohave spread terror throughout");
			lore.add(s + "§oall galaxies.");
			lore.add(s + "§oTheir homebase is by recent");
			lore.add(s + "§oactivity known to be at the");
			lore.add(s + "§oInsectum Galaxies.");
			meta.setLore(lore);
			item.setItemMeta(meta);
			item.setDurability((short) 14);
			
			contents[6] = item;
		}
		
		return contents;
	}
}
