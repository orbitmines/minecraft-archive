package om.kitpvp.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.kitpvp.enums.Booster;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BoosterInv extends InventoryInstance {
	
	public BoosterInv(){
		Inventory inventory = Bukkit.createInventory(null, 9, "§0§lBoosters");
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
		
		contents[1] = getItem(omp, Booster.IRON_BOOSTER);
		contents[3] = getItem(omp, Booster.GOLD_BOOSTER);
		contents[5] = getItem(omp, Booster.DIAMOND_BOOSTER);
		contents[7] = getItem(omp, Booster.EMERALD_BOOSTER);
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Booster booster){
		ItemStack item = new ItemStack(booster.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(booster.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		itemlore.add("§7Multiplier: §ax" + booster.getMultiplier());
		itemlore.add("§7Duration: §a30 Minutes");
		itemlore.add("");
		itemlore.add(booster.getPriceName(omp));
		itemlore.add("");
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		
		return item;
	}
}

