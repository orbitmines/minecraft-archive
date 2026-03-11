package om.api.invs.cp;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.InventoryInstance;
import om.api.utils.enums.cp.Pet;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PetInv extends InventoryInstance {
	
	public PetInv(){
		Inventory inventory = Bukkit.createInventory(null, 45, "§0§lPets");
		this.inventory = inventory;
	}
	
	public void open(Player player){
		inventory.setContents(getContects(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContects(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		contents[10] = getItem(omp, Pet.SQUID);
		contents[11] = getItem(omp, Pet.MUSHROOM_COW);
		contents[12] = getItem(omp, Pet.PIG);
		contents[13] = getItem(omp, Pet.WOLF);
		contents[14] = getItem(omp, Pet.SHEEP);
		contents[15] = getItem(omp, Pet.HORSE);
		contents[16] = getItem(omp, Pet.CREEPER);
		contents[19] = getItem(omp, Pet.SPIDER);
		contents[20] = getItem(omp, Pet.MAGMA_CUBE);
		contents[21] = getItem(omp, Pet.SLIME);
		contents[22] = getItem(omp, Pet.COW);
		contents[23] = getItem(omp, Pet.SILVERFISH);
		contents[24] = getItem(omp, Pet.OCELOT);
		contents[25] = getItem(omp, Pet.CHICKEN);
		{
			ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§9§nCosmetic Perks");
			item.setItemMeta(itemmeta);
			contents[39] = item;
		}
		{
			ItemStack item = new ItemStack(Material.NAME_TAG, 1);
			ItemMeta itemmeta = item.getItemMeta();
			if(omp.getPetEnabled() != null){
				itemmeta.setDisplayName("§fRename your " + omp.getPetEnabled().getName());
			}
			else{
				itemmeta.setDisplayName("§fRename §fPet");
			}
			
			List<String> lore = new ArrayList<String>();
			lore.add("");
			lore.add("§cPrice: §b10 VIP Points");
			lore.add("");
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			contents[40] = item;
		}
		{
			ItemStack item = new ItemStack(Material.LAVA_BUCKET, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§4§nDisable Pet");
			item.setItemMeta(itemmeta);
			contents[41] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, Pet pet){
		ItemStack item = new ItemStack(pet.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(pet.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		if(!pet.hasPet(omp)){
			itemlore.add(pet.getPriceName());
		}
		else{
			itemlore.add("§a§lUnlocked");
		}
		itemlore.add("");
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		item.setDurability(pet.getDurability());
		
		return item;
	}
}
