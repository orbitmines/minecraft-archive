package om.api.invs.cp;

import om.api.API;
import om.api.invs.InventoryInstance;
import om.api.utils.enums.Server;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CosmeticPerksInv extends InventoryInstance {
	
	private API api;
	
	public CosmeticPerksInv(){
		Inventory inventory = Bukkit.createInventory(null, 36, "§0§lCosmetic Perks");
		this.inventory = inventory;
		this.api = API.getInstance();
	}
	
	@Override
	public void open(Player player){
		inventory.setContents(getContects(player));
		player.openInventory(getInventory());
		player.playSound(player.getLocation(), Sound.CHEST_OPEN, 5, 1);
	}
	
	private ItemStack[] getContects(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		{
			ItemStack item = new ItemStack(Material.MONSTER_EGG, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Pets");
			item.setDurability((short) 95);
			item.setItemMeta(itemmeta);
			contents[10] = item;
		}
		{
			ItemStack item = new ItemStack(Material.INK_SACK, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§4ChatColors");
			item.setDurability((short) 1);
			item.setItemMeta(itemmeta);
			contents[12] = item;
		}
		{
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§2Disguises");
			if(api.isServer(Server.KITPVP)){
				itemmeta.setDisplayName("§2Disguises §8| §c§lDISABLED");
			}
			item.setDurability((short) 2);
			item.setItemMeta(itemmeta);
			contents[14] = item;
		}
		{
			ItemStack item = new ItemStack(Material.COMPASS, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§bGadgets");
			if(!api.isServer(Server.HUB) && !api.isServer(Server.MINIGAMES)){
				itemmeta.setDisplayName("§bGadgets §8| §c§lDISABLED");
			}
			item.setItemMeta(itemmeta);
			contents[16] = item;
		}
		{
			ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta itemmeta = (LeatherArmorMeta) item.getItemMeta();
			itemmeta.setDisplayName("§1Wardrobe");
			if(!api.isServer(Server.HUB) && !api.isServer(Server.MINIGAMES) && !api.isServer(Server.CREATIVE)){
				itemmeta.setDisplayName("§1Wardrobe §8| §c§lDISABLED");
			}
			itemmeta.setColor(Color.fromBGR(204, 100, 2));
			item.setItemMeta(itemmeta);
			contents[19] = item;
		}
		{
			ItemStack item = new ItemStack(Material.STRING, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§fTrails");
			item.setItemMeta(itemmeta);
			contents[21] = item;
		}
		{
			ItemStack item = new ItemStack(Material.PUMPKIN, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§6Hats");
			if(!api.isServer(Server.HUB) && !api.isServer(Server.MINIGAMES) && !api.isServer(Server.CREATIVE)){
				itemmeta.setDisplayName("§6Hats §8| §c§lDISABLED");
			}
			item.setItemMeta(itemmeta);
			contents[23] = item;
		}
		{
			ItemStack item = new ItemStack(Material.FIREWORK, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§cFireworks");
			if(!api.isServer(Server.HUB) && !api.isServer(Server.MINIGAMES)){
				itemmeta.setDisplayName("§cFireworks §8| §c§lDISABLED");
			}
			item.setItemMeta(itemmeta);
			contents[25] = item;
		}
		
		return contents;
	}
}
