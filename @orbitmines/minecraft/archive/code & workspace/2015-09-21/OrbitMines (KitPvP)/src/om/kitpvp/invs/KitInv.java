package om.kitpvp.invs;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPKit;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.OMPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitInv {
	
	private Inventory inventory;
	private KitPvPKit kitpvpkit;
	private int level;
	
	public KitInv(KitPvPKit kit, int level){
		Inventory inventory = Bukkit.createInventory(null, 54, "§0§l" + kit.getName() + " (Level " + level + ")");
		this.inventory = inventory;
		this.kitpvpkit = kit;
		this.level = level;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	public void setInventory(Inventory inventory){
		this.inventory = inventory;
	}
	
	public void open(Player player){
		inventory.setContents(getContects(player));
		player.openInventory(getInventory());
	}
	
	private ItemStack[] getContects(Player player){
		OMPlayer omp = OMPlayer.getOMPlayer(player);
		ItemStack[] contents = new ItemStack[getInventory().getSize()];
		
		Kit kit = kitpvpkit.getKit(this.level);
		int items = kit.contentItems();
		
		contents[45] = Utils.setDisplayname(new ItemStack(Material.REDSTONE_BLOCK, 1), "§4§l§o<< Back");
		contents[48] = getItem(omp, 1);
		contents[49] = getItem(omp, 2);
		contents[50] = getItem(omp, 3);
		
		contents[4] = kit.getHelmet();
		contents[13] = kit.getChestplate();
		contents[22] = kit.getLeggings();
		contents[31] = kit.getBoots();
		
		if(level == omp.getKitPvPPlayer().getUnlockedLevel(kitpvpkit) +1){
			ItemStack item = new ItemStack(Material.EMERALD_BLOCK, 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName("§2§l§oBuy " + kitpvpkit.getName() + " §7§o(§a§oLvL " + level + "§7§o)");
			List<String> itemlore = new ArrayList<String>();
			if(kitpvpkit.getVIPRank() == null){
				itemlore.add("");
				if(kitpvpkit.getCurrency() == Currency.ORBITMINES_TOKENS){
					itemlore.add("§7Price: §e" + kitpvpkit.getPrice(level) + " OMT");
				}
				else{
					itemlore.add("§7Price: §6" + kitpvpkit.getPrice(level) + " Coins");
				}
				itemlore.add("");
			}
			else{
				if(!omp.hasPerms(kitpvpkit.getVIPRank())){
					itemmeta.setDisplayName("§7Required: " + kitpvpkit.getVIPRank().getRankString() + " §lVIP");
				}
				else{
					item = null;
				}
			}
			
			if(item != null){
				itemmeta.setLore(itemlore);
				item.setItemMeta(itemmeta);
				contents[53] = item;
			}
		}
		
		if(items == 7 || items == 8){
			contents[2] = getItem(omp, kit.getItem(0));
			contents[6] = getItem(omp, kit.getItem(1));
			contents[11] = getItem(omp, kit.getItem(2));
			contents[15] = getItem(omp, kit.getItem(3));
			contents[20] = getItem(omp, kit.getItem(4));
			contents[24] = getItem(omp, kit.getItem(5));
			contents[29] = getItem(omp, kit.getItem(6));
			
			if(items == 8){
				contents[33] = getItem(omp, kit.getItem(7));
			}
		}
		else{
			if(items >= 1){
				contents[11] = getItem(omp, kit.getItem(0));
			}
			if(items >= 2){
				contents[15] = getItem(omp, kit.getItem(1));
			}
			if(items >= 3){
				contents[20] = getItem(omp, kit.getItem(2));
			}
			if(items >= 4){
				contents[24] = getItem(omp, kit.getItem(3));
			}
			if(items >= 5){
				contents[29] = getItem(omp, kit.getItem(4));
			}
			if(items == 6){
				contents[33] = getItem(omp, kit.getItem(5));
			}
		}
		
		PotionEffect effect = kit.getPotionEffect();
		if(effect != null){
			ItemStack item = getItem(omp, effect.getType(), effect.getAmplifier() +1);
			contents[18] = item;
			contents[26] = item;
		}
		
		return contents;
	}
	
	private ItemStack getItem(OMPlayer omp, PotionEffectType type, int level){
		String levelname = "";
		for(int i = 0; i < level; i++){
			levelname += "I";
		}
		levelname.replace("IIIIIII", "VII");
		levelname.replace("IIIIII", "VI");
		levelname.replace("IIIII", "V");
		levelname.replace("IIII", "IV");
		
		ItemStack item = new ItemStack(Material.POTION);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§b§l§o" + Utils.getPotionName(type) + " " + levelname);
		item.setItemMeta(meta);
		
		return item;
	}
	
	private ItemStack getItem(OMPlayer omp, ItemStack item){
		if(item != null && item.getType() == Material.ARROW){
			ItemMeta meta = item.getItemMeta();
			List<String> itemlore = new ArrayList<String>();
			itemlore.add(" §c+1 Arrow: §6Every " + kitpvpkit.getNextArrow() + " seconds");
			itemlore.add(" §cMaximum: §6" + item.getAmount() + " Arrows");
			meta.setLore(itemlore);
			item.setItemMeta(meta);
		}
		
		return item;
	}
	
	private ItemStack getItem(OMPlayer omp, int level){
		if(kitpvpkit.getHighestLevel() >= level){
			return Utils.setDisplayname(new ItemStack(Material.NETHER_STAR, level), "§b§l" + kitpvpkit.getName() + " §7§o(§a§oLvL " + level + "§7§o)");
		}
		return Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.INK_SACK, level), "§4§l§oUnavailable"), 1);
	}
}

