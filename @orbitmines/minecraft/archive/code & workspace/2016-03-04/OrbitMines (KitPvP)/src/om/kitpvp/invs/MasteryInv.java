package om.kitpvp.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.kitpvp.handlers.Masteries;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.utils.enums.Mastery;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MasteryInv extends InventoryInstance {
	
	public MasteryInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lMasteries");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player){
		inventory.setContents(getContects(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContects(Player player){
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		contents[1] = getUpgradeItem(omp, Mastery.MELEE);
		contents[3] = getUpgradeItem(omp, Mastery.MELEE_PROTECTION);
		contents[5] = getUpgradeItem(omp, Mastery.RANGE);
		contents[7] = getUpgradeItem(omp, Mastery.RANGE_PROTECTION);
		
		contents[19] = getItem(omp, Mastery.MELEE);
		contents[21] = getItem(omp, Mastery.MELEE_PROTECTION);
		contents[23] = getItem(omp, Mastery.RANGE);
		contents[25] = getItem(omp, Mastery.RANGE_PROTECTION);
		
		contents[37] = getDowngradeItem(omp, Mastery.MELEE);
		contents[39] = getDowngradeItem(omp, Mastery.MELEE_PROTECTION);
		contents[41] = getDowngradeItem(omp, Mastery.RANGE);
		contents[43] = getDowngradeItem(omp, Mastery.RANGE_PROTECTION);
		
		{
			int points = omp.getMasteries().getPoints();
			ItemStack item = new ItemStack(Material.EXP_BOTTLE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Mastery Points: §c" + points);
			item.setItemMeta(meta);
			if(points > 64){
				item.setAmount(64);
			}
			else{
				item.setAmount(points);
			}
			contents[49] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(KitPvPPlayer omp, Mastery mastery){
		Masteries masteries = omp.getMasteries();
		
		ItemStack item = new ItemStack(mastery.getMaterial(), masteries.getMasteryLevel(mastery));
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(mastery.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add(" §7Level: §c" + masteries.getMasteryLevel(mastery));
		itemlore.add(" §7Effect: §c" + (int) ((masteries.getMasteryEffect(mastery) +1) * 100) + "% " + mastery.getEffectName());
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		item = ItemUtils.hideFlags(item, 2);
		
		return item;
	}
	
	private ItemStack getUpgradeItem(KitPvPPlayer kp, Mastery mastery){
		Masteries masteries = kp.getMasteries();
		
		if(masteries.getPoints() > 0){
			int nextlevel = masteries.getMasteryLevel(mastery) +1;
			if(nextlevel > 64){
				nextlevel = 64;
			}
			
			ItemStack item = new ItemStack(Material.EMERALD, nextlevel);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(mastery.getColor() + (int) (mastery.getMultiplier() * 100) + "% " + mastery.getEffectName());
			List<String> itemlore = new ArrayList<String>();
			itemlore.add(" §7Price: §c§l1 Mastery Point");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);			
			
			return item;
		}
		return null;
	}
	
	private ItemStack getDowngradeItem(KitPvPPlayer kp, Mastery mastery){
		Masteries masteries = kp.getMasteries();
		
		if(masteries.getMasteryLevel(mastery) > 0){
			int nextlevel = masteries.getMasteryLevel(mastery) -1;
			if(nextlevel > 64){
				nextlevel = 64;
			}
			
			ItemStack item = new ItemStack(Material.BLAZE_POWDER, nextlevel);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(mastery.getOppositeColor() + (int) (mastery.getMultiplier() * 100) + "% " + mastery.getEffectName());
			List<String> itemlore = new ArrayList<String>();
			itemlore.add(" §7Price: §b§l20 VIP Points");
			itemlore.add(" §7(+ 1 Mastery Point)");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);			
			
			return item;
		}
		return null;
	}
}

