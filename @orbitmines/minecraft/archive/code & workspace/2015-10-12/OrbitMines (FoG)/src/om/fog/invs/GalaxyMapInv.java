package om.fog.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.invs.InventoryInstance;
import om.api.utils.ItemUtils;
import om.fog.handlers.map.FoGMap;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Faction;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GalaxyMapInv extends InventoryInstance {
	
	public GalaxyMapInv(){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§lGalaxy System");
		this.inventory = inventory;
	}
	
	@Override
	public void open(Player player) {
		getInventory().setContents(getContents(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContents(Player player){
		FoGPlayer omp = FoGPlayer.getFoGPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];

		contents[4] = getMapItem(omp, "B-1");
		contents[13] = getMapItem(omp, "B-2");
		contents[18] = getMapItem(omp, "A-1");
		contents[21] = getMapItem(omp, "B-3");
		contents[23] = getMapItem(omp, "B-4");
		contents[26] = getMapItem(omp, "O-1");
		contents[28] = getMapItem(omp, "A-2");
		contents[34] = getMapItem(omp, "O-2");
		contents[36] = getMapItem(omp, "A-3");
		contents[38] = getMapItem(omp, "A-4");
		contents[40] = getMapItem(omp, "M-2");
		contents[42] = getMapItem(omp, "O-3");
		contents[44] = getMapItem(omp, "O-4");
		
		return contents;
	}
	
	private ItemStack getMapItem(FoGPlayer omp, String mapname){
		FoGMap map = FoGMap.getMap(mapname);

		int amount = Integer.parseInt(mapname.substring(mapname.indexOf("-") +1));
		if(map != null){
			Faction faction = map.getFaction();
			String c = "§f";
			ItemStack item = new ItemStack(Material.STAINED_GLASS, amount);
			if(omp.getExploredMaps().contains(map)){
				item.setType(Material.STAINED_CLAY);
			}
			if(map.getFaction() != null){
				c = faction.getColor();
				item.setDurability(map.getFaction().getDurability());
			}
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(c + mapname);
			List<String> lore = new ArrayList<String>();
			
			if(omp.getExploredMaps().contains(map)){
				if(faction == null || faction == omp.getFaction()){
					lore.add(" §7Players: " + c + map.getPlayers() + " ");
					//lore.add("");
				}
				
				//lore.add(" §7Teleport Price: " + c + ".. ");
				//lore.add("");
				//lore.add(" §7Click to Teleport ");
			}
			else{
				lore.add(" §7You have not yet ");
				lore.add(" §7explored this galaxy. ");
			}
		
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			if(omp.getMap() == map){
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				item = ItemUtils.hideFlags(item, 1);
			}
			
			return item;
		}
		else{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, amount);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§8This galaxy has not yet been");
			List<String> lore = new ArrayList<String>();
			lore.add("§8explored, but they call it " + mapname + ".");
			meta.setLore(lore);
			item.setItemMeta(meta);
			item.setDurability((short) 15);
			
			return item;
		}
	}
}
