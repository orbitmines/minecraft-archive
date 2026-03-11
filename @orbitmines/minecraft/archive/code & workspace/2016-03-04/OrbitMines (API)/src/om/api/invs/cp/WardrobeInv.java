package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.ColorUtils;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class WardrobeInv extends InventoryInstance {
	
	public WardrobeInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lWardrobe");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player){
		inventory.setContents(getContects(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContects(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		contents[10] = getItem(omp, Color.WHITE);
		contents[11] = getItem(omp, Color.BLUE);
		contents[12] = getItem(omp, Color.GREEN);
		contents[13] = getItem(omp, Color.BLACK);
		contents[14] = getItem(omp, Color.AQUA);
		contents[15] = getItem(omp, Color.FUCHSIA);
		contents[16] = getItem(omp, Color.LIME);
		contents[19] = getItem(omp, Color.NAVY);
		contents[20] = getItem(omp, Color.PURPLE);
		contents[21] = getItem(omp, Color.ORANGE);
		contents[22] = getItem(omp, Color.RED);
		contents[23] = getItem(omp, Color.TEAL);
		contents[24] = getItem(omp, Color.YELLOW);
		contents[25] = getItem(omp, Color.GRAY);
		{
			ItemStack item = new ItemStack(Material.IRON_CHESTPLATE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Iron Armor");
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(!omp.hasPerms(VIPRank.Iron_VIP)){
				itemlore.add("§cRequired: §7§lIron VIP");
			}
			else{
				itemlore.add("§a§lUnlocked");
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			contents[29] = item;
		}
		{
			ItemStack item = new ItemStack(Material.GOLD_CHESTPLATE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§6Gold Armor");
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(!omp.hasPerms(VIPRank.Gold_VIP)){
				itemlore.add("§cRequired: §6§lGold VIP");
			}
			else{
				itemlore.add("§a§lUnlocked");
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			contents[30] = item;
		}
		{
			ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§bDiamond Armor");
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(!omp.hasPerms(VIPRank.Diamond_VIP)){
				itemlore.add("§cRequired: §b§lDiamond VIP");
			}
			else{
				itemlore.add("§a§lUnlocked");
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			contents[32] = item;
		}
		{
			ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Chainmail Armor");
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(!omp.hasPerms(VIPRank.Emerald_VIP)){
				itemlore.add("§cRequired: §a§lEmerald VIP");
			}
			else{
				itemlore.add("§a§lUnlocked");
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			contents[33] = item;
		}
		
		contents[31] = setDiscoItem(this.inventory, omp);
		
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[48] = item;
		}
		{
			ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§4§nRemove Current Armor");
			item.setItemMeta(itemmeta);
			contents[50] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Color color){
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
		itemmeta.setDisplayName(ColorUtils.getName(color) + " Armor");
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		if(!omp.hasWardrobe(color)){
			itemlore.add("§cPrice: §b250 VIP Points");
		}
		else{
			itemlore.add("§a§lUnlocked");
		}
		itemlore.add("");
		itemmeta.setColor(color);
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		
		return item;
	}
	
	public static ItemStack setDiscoItem(Inventory inv, OMPlayer omp){
		Color color = ColorUtils.random(ColorUtils.getWardrobe());
		
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
		itemmeta.setDisplayName(ColorUtils.getColor(color) + "Disco Armor");
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("§7§o(Uses your unlocked Armor)");
		itemlore.add("");
		
		if(omp.getWardrobe().size() >= 2){
			if(!omp.hasUnlockedWardrobeDisco()){
				itemlore.add("§cPrice: §b500 VIP Points");
			}
			else{
				itemlore.add("§a§lUnlocked");
			}
		}
		else{
			itemlore.add("§cPrice: §b500 VIP Points");
			itemlore.add("§cRequired: " + ColorUtils.getColor(color) + "2 Armor Sets");
		}
		itemlore.add("");
		itemmeta.setColor(color);
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		inv.setItem(31, item);
		return item;
	}
}
