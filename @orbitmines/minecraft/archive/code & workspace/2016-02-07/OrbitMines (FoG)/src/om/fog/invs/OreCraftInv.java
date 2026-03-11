package om.fog.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.invs.InventoryInstance;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Ore;
import om.fog.utils.enums.Ore.CraftOre;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OreCraftInv extends InventoryInstance {
	
	public OreCraftInv(){
		Inventory inventory = Bukkit.createInventory(null, 45, "§0§lCraft Ores");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player) {
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContents(Player player){
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		FoGPlayer omp = FoGPlayer.getFoGPlayer(player);
		
		for(Ore ore : Ore.values()){
			ItemStack item = new ItemStack(Material.INK_SACK);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ore.getName() + "§7: " + omp.getAmount(Material.INK_SACK, ore.getDurability()));
			if(ore.isCraftAble()){
				List<String> lore = new ArrayList<String>();
				lore.add("");
				lore.add("§7Craft with:");
				for(CraftOre cOre : ore.getCraftOres()){
					lore.add(" " + cOre.getOre().getColor() + cOre.getAmount() + " " + cOre.getOre().getName());
				}
				meta.setLore(lore);
			}
			item.setItemMeta(meta);
			item.setDurability(ore.getDurability());
			
			contents[ore.getCraftSlot()] = item;
		}
		
		return contents;
	}
}
