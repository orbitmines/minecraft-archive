package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.api.utils.Utils;
import om.api.utils.enums.cp.TrailType;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrailSettingsInv extends InventoryInstance {
	
	public TrailSettingsInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lTrail Settings");
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
		
		contents[4] = ItemUtils.setDisplayname(new ItemStack(Material.COMPASS), "§7Trail Types");
		contents[9] = getItem(omp, TrailType.BASIC_TRAIL);
		contents[10] = getItem(omp, TrailType.GROUND_TRAIL);
		contents[11] = getItem(omp, TrailType.HEAD_TRAIL);
		contents[12] = getItem(omp, TrailType.BODY_TRAIL);
		contents[13] = getItem(omp, TrailType.BIG_TRAIL);
		contents[14] = getItem(omp, TrailType.VERTICAL_TRAIL);
		contents[15] = getItem(omp, TrailType.ORBIT_TRAIL);
		contents[16] = getItem(omp, TrailType.CYLINDER_TRAIL);
		contents[17] = getItem(omp, TrailType.SNAKE_TRAIL);
		
		{
			ItemStack item = ItemUtils.setDisplayname(new ItemStack(Material.STAINED_GLASS_PANE), "§fComing Soon...");
			
			contents[18] = item;
			contents[19] = item;
			contents[20] = item;
			contents[21] = item;
			contents[22] = item;
			contents[23] = item;
			contents[24] = item;
			contents[25] = item;
			contents[26] = item;
		}
		
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7§lSpecial Trail: " + Utils.statusString(omp.hasSpecialTrail()));
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(omp.hasUnlockedSpecialTrail()){
				itemlore.add("§a§lUnlocked");
			}
			else{
				itemlore.add("§cPrice: §b750 VIP Points");
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			item.setDurability(Utils.statusDurability(omp.hasSpecialTrail()));
			contents[37] = item;
		}
		{
			ItemStack item = new ItemStack(Material.NETHER_STAR, omp.getTrailPA());
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§7§lParticle Amount: §f§l" + omp.getTrailPA());
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			if(omp.hasPerms(VIPRank.Gold_VIP)){
				itemlore.add("§a§lUnlocked");
			}
			else{
				itemlore.add("§c§oRequired: §6§lGold VIP");
			}
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			contents[43] = item;
		}
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[49] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, TrailType trailtype){
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(trailtype.getName() + ": " + Utils.statusString(omp.getTrailType() == trailtype));
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		if(trailtype.hasTrailType(omp)){
			itemlore.add("§a§lUnlocked");
		}
		else{
			itemlore.add(trailtype.getPriceString());
		}
		itemlore.add("");
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		item.setDurability(Utils.statusDurability(omp.getTrailType() == trailtype));
		
		return item;
	}
}
