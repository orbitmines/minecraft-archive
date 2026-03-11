package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.Utils;
import om.api.utils.enums.cp.ChatColor;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChatColorInv extends InventoryInstance {
	
	public ChatColorInv(){
		Inventory inventory = Bukkit.createInventory(null, 45, "§0§lChatColors");
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
		
		contents[9] = getItem(omp, ChatColor.DARK_RED);
		contents[10] = getItem(omp, ChatColor.LIGHT_GREEN);
		contents[11] = getItem(omp, ChatColor.DARK_GRAY);
		contents[12] = getItem(omp, ChatColor.RED);
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(omp.getChatColor().getColor() + "§lBold ChatColor: " + Utils.statusString(omp.isBold()));
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(omp.hasPerms(VIPRank.Emerald_VIP)){
				if(!omp.hasUnlockedBold()){
					itemlore.add("§cPrice: §b3000 VIP Points");
				}
				else{
					itemlore.add("§a§lUnlocked");
				}
			}
			else{
				itemlore.add("§cPrice: §b3000 VIP Points");
				itemlore.add("§cRequired: §a§lEmerald VIP");
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			item.setDurability(Utils.statusDurability(omp.isBold()));
			contents[13] = item;
		}
		contents[14] = getItem(omp, ChatColor.YELLOW);
		contents[15] = getItem(omp, ChatColor.WHITE);
		contents[16] = getItem(omp, ChatColor.LIGHT_BLUE);
		contents[17] = getItem(omp, ChatColor.PINK);
		contents[18] = getItem(omp, ChatColor.BLUE);
		contents[19] = getItem(omp, ChatColor.DARK_BLUE);
		contents[20] = getItem(omp, ChatColor.GRAY);
		contents[21] = getItem(omp, ChatColor.ORANGE);
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(omp.getChatColor().getColor() + "§oCursive ChatColor: " + Utils.statusString(omp.isCursive()));
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(!omp.hasPerms(VIPRank.Diamond_VIP)){
				itemlore.add("§cPrice: §b2000 VIP Points");
				itemlore.add("§cRequired: §b§lDiamond VIP");
			}
			else{
				if(!omp.hasUnlockedCursive()){
					itemlore.add("§cPrice: §b2000 VIP Points");
				}
				else{
					itemlore.add("§a§lUnlocked");
				}
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			item.setDurability(Utils.statusDurability(omp.isCursive()));
			contents[22] = item;
		}
		contents[23] = getItem(omp, ChatColor.PURPLE);
		contents[24] = getItem(omp, ChatColor.CYAN);
		contents[25] = getItem(omp, ChatColor.GREEN);
		contents[26] = getItem(omp, ChatColor.BLACK);
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[39] = item;
		}
		{		
			ChatColor cc = omp.getChatColor();
			ItemStack item = new ItemStack(cc.getMaterial(), 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(cc.getName());
			item.setDurability(cc.getDurability());
			item.setItemMeta(itemmeta);
			contents[41] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, ChatColor chatcolor){
		ItemStack item = new ItemStack(chatcolor.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(chatcolor.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		if(!chatcolor.hasChatColor(omp)){
			itemlore.add(chatcolor.getPriceName());
		}
		else{
			itemlore.add("§a§lUnlocked");
		}
		itemlore.add("");
		itemmeta.setLore(itemlore);
		item.setDurability(chatcolor.getDurability());
		item.setItemMeta(itemmeta);
		
		return item;
	}
}

