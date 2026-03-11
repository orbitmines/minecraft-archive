package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.FireworkSettings;
import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.ColorUtils;
import om.api.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FireworkInv extends InventoryInstance{
	
	public FireworkInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lFireworks");
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
		
		FireworkSettings fwsettings = omp.getFWSettings();
		{
			ItemStack item = new ItemStack(Material.INK_SACK, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Color 1: §l" + ColorUtils.getName(fwsettings.getColor1()));
			item.setItemMeta(itemmeta);
			item.setDurability((short) ColorUtils.getDurability(fwsettings.getColor1()));
			if(fwsettings.getColor1() == Color.MAROON){
				item.setType(Material.REDSTONE);
			}
			contents[10] = item;
		}
		{
			Color color2 = fwsettings.getColor2();
			if(color2 != null){
				ItemStack item = new ItemStack(Material.INK_SACK, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Color 2: §l" + ColorUtils.getName(color2));
				item.setItemMeta(itemmeta);
				item.setDurability((short) ColorUtils.getDurability(color2));
				if(color2 == Color.MAROON){
					item.setType(Material.REDSTONE);
				}
				contents[28] = item;
			}
			else{
				ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Color 2: §c§lDISABLED");
				item.setItemMeta(itemmeta);
				item.setDurability((short) 14);
				contents[28] = item;
			}
		}
		{
			Color fade1 = fwsettings.getFade1();
			if(fade1 != null){
				ItemStack item = new ItemStack(Material.INK_SACK, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Fade 1: §l" + ColorUtils.getName(fade1));
				item.setItemMeta(itemmeta);
				item.setDurability((short) ColorUtils.getDurability(fade1));
				if(fade1 == Color.MAROON){
					item.setType(Material.REDSTONE);
				}
				contents[12] = item;
			}
			else{
				ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Fade 1: §c§lDISABLED");
				item.setItemMeta(itemmeta);
				item.setDurability((short) 14);
				contents[12] = item;
			}
		}
		{
			Color fade2 = fwsettings.getFade2();
			if(fade2 != null){
				ItemStack item = new ItemStack(Material.INK_SACK, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Fade 2: §l" + ColorUtils.getName(fade2));
				item.setItemMeta(itemmeta);
				item.setDurability((short) ColorUtils.getDurability(fade2));
				if(fade2 == Color.MAROON){
					item.setType(Material.REDSTONE);
				}
				contents[30] = item;
			}
			else{
				ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§7Fade 2: §c§lDISABLED");
				item.setItemMeta(itemmeta);
				item.setDurability((short) 14);
				contents[30] = item;
			}
		}

		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Trail: " + Utils.statusString(fwsettings.hasTrail()));
			item.setItemMeta(itemmeta);
			item.setDurability((short) Utils.statusDurability(fwsettings.hasTrail()));
			contents[14] = item;
		}
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Flicker: " + Utils.statusString(fwsettings.hasFlicker()));
			item.setItemMeta(itemmeta);
			item.setDurability((short) Utils.statusDurability(fwsettings.hasFlicker()));
			contents[32] = item;
		}
		{
			ItemStack item = new ItemStack(ColorUtils.getMaterial(fwsettings.getType()), 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7Type: " + ColorUtils.getName(fwsettings.getType()));
			item.setItemMeta(itemmeta);
			item.setDurability((short) ColorUtils.getDurability(fwsettings.getType()));
			contents[25] = item;
		}
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[48] = item;
		}
		{
			ItemStack item = new ItemStack(Material.ANVIL, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§e§nCreate Firework");
			item.setItemMeta(itemmeta);
			contents[49] = item;
		}
		{
			ItemStack item = new ItemStack(Material.EMPTY_MAP, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§c§nFirework Passes:§r §6§n" + omp.getFireworkPasses());
			item.setItemMeta(itemmeta);
			contents[50] = item;
		}
		{
			ItemStack item = new ItemStack(Material.EMPTY_MAP, 5);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§6§l+5 Firework Passes");
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("§cPrice: §b2 VIP Points");
			lore.add("");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			contents[52] = item;
		}
		{
			ItemStack item = new ItemStack(Material.EMPTY_MAP, 25);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§6§l+25 Firework Passes");
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("§cPrice: §b10 VIP Points");
			lore.add("");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			contents[53] = item;
		}
		
		return contents;
	}
}

