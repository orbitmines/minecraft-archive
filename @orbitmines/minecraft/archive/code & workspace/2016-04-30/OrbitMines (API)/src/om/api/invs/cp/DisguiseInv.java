package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.enums.cp.Disguise;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DisguiseInv extends InventoryInstance {
	
	public DisguiseInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lDisguises");
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
		
		contents[9] = getItem(omp, Disguise.ENDERMAN);
		contents[10] = getItem(omp, Disguise.WITCH);
		contents[11] = getItem(omp, Disguise.BAT);
		contents[12] = getItem(omp, Disguise.CHICKEN);
		contents[13] = getItem(omp, Disguise.OCELOT);
		contents[14] = getItem(omp, Disguise.MUSHROOM_COW);
		contents[15] = getItem(omp, Disguise.SQUID);
		contents[16] = getItem(omp, Disguise.PIG);
		contents[17] = getItem(omp, Disguise.IRON_GOLEM);
		contents[18] = getItem(omp, Disguise.GHAST);
		contents[19] = getItem(omp, Disguise.BLAZE);
		contents[20] = getItem(omp, Disguise.SLIME);
		contents[21] = getItem(omp, Disguise.ZOMBIE_PIGMAN);
		contents[22] = getItem(omp, Disguise.MAGMA_CUBE);
		contents[23] = getItem(omp, Disguise.SKELETON);
		contents[24] = getItem(omp, Disguise.ZOMBIE);
		contents[25] = getItem(omp, Disguise.VILLAGER);
		contents[26] = getItem(omp, Disguise.HORSE);
		contents[27] = getItem(omp, Disguise.RABBIT);
		contents[28] = getItem(omp, Disguise.WOLF);
		contents[29] = getItem(omp, Disguise.SPIDER);
		contents[30] = getItem(omp, Disguise.SILVERFISH);
		contents[31] = getItem(omp, Disguise.SHEEP);
		contents[32] = getItem(omp, Disguise.CAVE_SPIDER);
		contents[33] = getItem(omp, Disguise.CREEPER);
		contents[34] = getItem(omp, Disguise.COW);
		contents[35] = getItem(omp, Disguise.SNOWMAN);
		
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
			itemmeta.setDisplayName("§4§nDisable Disguise");
			item.setItemMeta(itemmeta);
			contents[50] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Disguise disguise){
		ItemStack item = new ItemStack(disguise.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(disguise.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		if(!disguise.hasDisguise(omp)){
			itemlore.add(disguise.getPriceName());
		}
		else{
			itemlore.add("§a§lUnlocked");
		}
		itemlore.add("");
		item.setDurability(disguise.getDurability());
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		
		return item;
	}
}
