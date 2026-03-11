package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.enums.cp.Gadget;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GadgetInv extends InventoryInstance {

	public GadgetInv(){
		Inventory inventory = Bukkit.createInventory(null, 45, "§0§lGadgets");
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
		
		contents[10] = getItem(omp, Gadget.STACKER);
		contents[11] = getItem(omp, Gadget.PAINTBALLS);
		contents[12] = getItem(omp, Gadget.CREEPER_LAUNCHER);
		contents[13] = getItem(omp, Gadget.PET_RIDE);
		contents[14] = getItem(omp, Gadget.BOOK_EXPLOSION);
		contents[15] = getItem(omp, Gadget.SWAP_TELEPORTER);
		contents[16] = getItem(omp, Gadget.MAGMACUBE_SOCCER);
		contents[19] = getItem(omp, Gadget.SNOWMAN_ATTACK);
		contents[20] = getItem(omp, Gadget.FLAME_THROWER);
		contents[21] = getItem(omp, Gadget.GRAPPLING_HOOK);
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[39] = item;
		}
		
		try{
			ItemStack itemInInv = omp.getPlayer().getInventory().getItem(5);
			ItemStack item = new ItemStack(itemInInv.getType());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(itemInInv.getItemMeta().getDisplayName());
			List<String> lore = new ArrayList<String>();
			lore.add("§c§lDISABLE §bGadget");
			meta.setLore(lore);
			item.setItemMeta(meta);
			contents[41] = item;
		}catch(NullPointerException ex){}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Gadget gadget){
		ItemStack item = new ItemStack(gadget.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(gadget.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		if(!gadget.hasGadget(omp)){
			itemlore.add(gadget.getPriceName(omp));
		}
		else{
			itemlore.add("§a§lUnlocked");
		}
		itemlore.add("");
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		item.setDurability(gadget.getDurability());
		
		return item;
	}
}

