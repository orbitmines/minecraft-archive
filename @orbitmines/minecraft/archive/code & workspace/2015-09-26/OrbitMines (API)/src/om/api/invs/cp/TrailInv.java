package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.api.utils.enums.cp.Trail;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrailInv extends InventoryInstance {
	
	public TrailInv(){
		Inventory inventory = Bukkit.createInventory(null, 45, "§0§lTrails");
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
		
		contents[9] = getItem(omp, Trail.FIREWORK_SPARK);
		contents[10] = getItem(omp, Trail.HAPPY_VILLAGER);
		contents[11] = getItem(omp, Trail.HEART);
		contents[12] = getItem(omp, Trail.TNT);
		contents[13] = getItem(omp, Trail.MAGIC);
		contents[14] = getItem(omp, Trail.ANGRY_VILLAGER);
		contents[15] = getItem(omp, Trail.LAVA);
		contents[16] = getItem(omp, Trail.SLIME);
		contents[17] = getItem(omp, Trail.SMOKE);
		contents[18] = getItem(omp, Trail.WITCH);
		contents[19] = getItem(omp, Trail.CRIT);
		contents[20] = getItem(omp, Trail.WATER);
		contents[21] = getItem(omp, Trail.MUSIC);
		contents[22] = getItem(omp, Trail.SNOW);
		contents[23] = getItem(omp, Trail.ENCHANTMENT_TABLE);
		contents[24] = getItem(omp, Trail.RAINBOW);
		contents[25] = getItem(omp, Trail.MOB_SPAWNER);
		contents[26] = getItem(omp, Trail.BUBBLE);
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[39] = item;
		}
		{
			ItemStack item = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§f§nTrail Settings");	
			item.setItemMeta(itemmeta);
			contents[40] = item;
		}
		{
			ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§4§nRemove Current Trail");
			item.setItemMeta(itemmeta);
			contents[41] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Trail trail){
		ItemStack item = new ItemStack(trail.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(trail.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		if(!trail.hasTrail(omp)){
			itemlore.add(trail.getPriceName());
		}
		else{
			itemlore.add("§a§lUnlocked");
		}
		itemlore.add("");
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		item.setDurability(trail.getDurability());
		
		if(trail == Trail.CRIT){
			item = ItemUtils.hideFlags(item, 2);
		}
		
		return item;
	}
}
