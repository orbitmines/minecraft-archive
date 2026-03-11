package om.fog.invs;

import java.util.List;

import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.fog.handlers.Shields;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.ShieldLore.ArmorType;
import om.fog.utils.enums.ShieldLore.ShieldLoreLevel;
import om.fog.utils.enums.Suit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShieldsInv extends InventoryInstance {
	
	private Suit suit;
	private Faction faction;
	
	public ShieldsInv(Suit suit, Faction faction){
		if(faction != null){
			Inventory inventory = Bukkit.createInventory(null, 54, "§0§l" + suit.getName(faction) + " | Shields");
			this.inventory = inventory;
			this.suit = suit;
			this.faction = faction;
			this.inventory.setContents(getBaseContents());
		}
	}
	
	@Override
	public void open(Player player) {
		setShieldContents(player);
		player.openInventory(getInventory());
	}
	
	private void setShieldContents(Player player){
		FoGPlayer omp = FoGPlayer.getFoGPlayer(player);
		Shields shields = omp.getShields(suit);
		ShieldLoreLevel[][] items = shields.getShieldItems();
		
		ItemStack[] armorContents = new ItemStack[4];
		
		ItemStack lItem = new ItemStack(Material.BARRIER);
		ItemMeta lMeta = lItem.getItemMeta();
		lMeta.setDisplayName("§4§lEmpty");
		lItem.setItemMeta(lMeta);
		
		int iA2 = 0;
		for(int iA = 3; iA >= 0; iA--){
			int i = 0;
			for(int slot = 1 + (iA * 9); slot < 9 + (iA * 9); slot++){
				if(items[iA2][i] != null){
					ShieldLoreLevel sl = items[iA2][i];
					ItemStack item = sl.getLore().getEnchantment(sl.getLevel());
					ItemMeta meta = item.getItemMeta();
					List<String> lore = meta.getLore();
					lore.add("");
					lore.add("§7Click to §cRemove Enchantment");
					meta.setLore(lore);
					item.setItemMeta(meta);
					
					inventory.setItem(slot, item);
				}
				else{
					inventory.setItem(slot, lItem);
				}
				i++;
			}
			
			ArmorType type = ArmorType.getByIndex(iA2);
			ItemStack item = shields.getShield(omp, type, suit);
			shields.checkItems(omp, type, item);

			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			lore.remove("§fSoulbound");
			lore.add("§7Drag and drop Enchantments to apply.");
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			armorContents[iA2] = item;
			iA2++;
		}
		
		inventory.setItem(47, ItemUtils.hideFlags(armorContents[3], 2));
		inventory.setItem(48, ItemUtils.hideFlags(armorContents[2], 2));
		inventory.setItem(50, ItemUtils.hideFlags(armorContents[1], 2));
		inventory.setItem(51, ItemUtils.hideFlags(armorContents[0], 2));
	}
	
	private ItemStack[] getBaseContents(){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		int iA = 3;
		for(ItemStack item : suit.getArmorContents(faction)){
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(faction.getColor() + ArmorType.getByIndex(iA).getName());
			item.setItemMeta(meta);
			contents[iA * 9] = item;
			
			iA--;
		}
		
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
			
			contents[45] = item;
			contents[46] = item;
			contents[49] = item;
			contents[52] = item;
			contents[53] = item;
		}
		
		return contents;
	}
}
