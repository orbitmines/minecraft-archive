package om.kitpvp.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.invs.others.OMTShopInv;
import om.kitpvp.handlers.players.KitPvPPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitPvPOMTShopInv extends OMTShopInv {

	@Override
	protected ItemStack[] getContects(Player player) {
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		contents[10] = getItem(omp, 200, 1);
		contents[12] = getItem(omp, 1000, 4);
		contents[14] = getItem(omp, 2500, 9);
		contents[16] = getItem(omp, 5000, 16);
		
		return contents;
	}
	
	private ItemStack getItem(KitPvPPlayer omp, int coins, int price){
		ItemStack item = new ItemStack(Material.GOLD_NUGGET, (int) coins / 100);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6§l+" + coins + " Coins");
		List<String> lore = new ArrayList<String>();
		lore.add("");
		if(price == 1){
			lore.add("§cPrice: §e" + price + " OrbitMines Token");
		}
		else{
			lore.add("§cPrice: §e" + price + " OrbitMines Tokens");
		}
		lore.add("");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
}
