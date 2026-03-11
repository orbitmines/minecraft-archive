package om.fog.invs;

import java.util.List;

import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.fog.handlers.Swords;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Suit;
import om.fog.utils.enums.SwordLore;
import om.fog.utils.enums.SwordLore.SwordLoreLevel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SwordsInv extends InventoryInstance {
	
	private Suit suit;
	private Faction faction;
	
	public SwordsInv(Suit suit, Faction faction){
		if(faction != null){
			Inventory inventory = Bukkit.createInventory(null, 27, "§0§l" + suit.getName(faction) + " | Swords");
			this.inventory = inventory;
			this.suit = suit;
			this.faction = faction;
			this.inventory.setContents(getBaseContents());
		}
	}
	
	@Override
	public void open(Player player) {
		setSwordContents(player);
		player.openInventory(getInventory());
	}
	
	private void setSwordContents(Player player){
		FoGPlayer omp = FoGPlayer.getFoGPlayer(player);
		Swords swords = omp.getSwords(suit);
		SwordLoreLevel[] items = swords.getSwordItems();
		
		ItemStack lItem = new ItemStack(Material.BARRIER);
		ItemMeta lMeta = lItem.getItemMeta();
		lMeta.setDisplayName("§4§lEmpty");
		lItem.setItemMeta(lMeta);
		
		int slot = 0;
		for(int i = 0; i < 9; i++){
			if(items[i] != null){
				SwordLoreLevel sl = items[i];
				ItemStack item = sl.getLore().getEnchantment(sl.getLevel());
				ItemMeta meta = item.getItemMeta();
				if(sl.getLore() != SwordLore.DAMAGE){
					List<String> lore = meta.getLore();
					lore.add("");
					lore.add("§7Click to §cRemove Enchantment");
					meta.setLore(lore);
				}
				item.setItemMeta(meta);
				
				inventory.setItem(slot, item);
			}
			else{
				inventory.setItem(slot, lItem);
			}
			slot++;
		}
		
		ItemStack item = swords.getSword(omp, suit);
		swords.checkItems(player, item);

		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove("§fSoulbound");
		lore.add("§7Drag and drop Enchantments to apply.");
		meta.setLore(lore);
		item.setItemMeta(meta);
		inventory.setItem(22, ItemUtils.hideFlags(item, 2));
	}
	
	private ItemStack[] getBaseContents(){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(" ");
			item.setItemMeta(meta);
			item.setDurability((short) 15);
			
			contents[9] = item;
			contents[10] = item;
			contents[11] = item;
			contents[12] = item;
			contents[13] = item;
			contents[14] = item;
			contents[15] = item;
			contents[16] = item;
			contents[17] = item;
		}
		
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(" ");
			item.setItemMeta(meta);
			item.setDurability((short) 8);
			
			contents[18] = item;
			contents[19] = item;
			contents[20] = item;
			contents[21] = item;
			contents[23] = item;
			contents[24] = item;
			contents[25] = item;
			contents[26] = item;
		}
		
		return contents;
	}
}
