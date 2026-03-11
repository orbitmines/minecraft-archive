package me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.Booster;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.KitPvPKit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Inventories {

	public static class BoosterInv {
		
		private Inventory inventory;
		
		public BoosterInv(){
			Inventory inventory = Bukkit.createInventory(null, 9, "§0§lBoosters");
			this.inventory = inventory;
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
			
			contents[1] = getItem(omp, Booster.IRON_BOOSTER);
			contents[3] = getItem(omp, Booster.GOLD_BOOSTER);
			contents[5] = getItem(omp, Booster.DIAMOND_BOOSTER);
			contents[7] = getItem(omp, Booster.EMERALD_BOOSTER);
			
			return contents;
		}
		
		private ItemStack getItem(OMPlayer omp, Booster booster){
			ItemStack item = new ItemStack(booster.getMaterial(), 1);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(booster.getName());
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			itemlore.add("§7Multiplier: §ax" + booster.getMultiplier());
			itemlore.add("§7Duration: §a30 Minutes");
			itemlore.add("");
			itemlore.add(booster.getPriceName(omp));
			itemlore.add("");
			itemlore.add(booster.getPriceName(omp));
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			
			return item;
		}
	}
	
	public static class KitSelectorInv {
		
		private Inventory inventory;
		
		public KitSelectorInv(){
			Inventory inventory = Bukkit.createInventory(null, 45, "§0§lKit Selector");
			this.inventory = inventory;
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
			
			contents[9] = getItem(omp, KitPvPKit.KNIGHT);
			contents[10] = getItem(omp, KitPvPKit.ARCHER);
			contents[11] = getItem(omp, KitPvPKit.SOLDIER);
			contents[12] = getItem(omp, KitPvPKit.WIZARD);
			contents[13] = getItem(omp, KitPvPKit.TANK);
			contents[14] = getItem(omp, KitPvPKit.DRUNK);
			contents[15] = getItem(omp, KitPvPKit.PYRO);
			contents[16] = getItem(omp, KitPvPKit.BUNNY);
			contents[17] = getItem(omp, KitPvPKit.NECROMANCER);
			contents[18] = getItem(omp, KitPvPKit.KING);
			contents[19] = getItem(omp, KitPvPKit.TREE);
			contents[20] = getItem(omp, KitPvPKit.BLAZE);
			contents[21] = getItem(omp, KitPvPKit.TNT);
			contents[22] = getItem(omp, KitPvPKit.FISHERMAN);
			contents[23] = getItem(omp, KitPvPKit.SNOWGOLEM);
			contents[24] = getItem(omp, KitPvPKit.LIBRARIAN);
			contents[25] = getItem(omp, KitPvPKit.SPIDER);
			contents[26] = getItem(omp, KitPvPKit.VILLAGER);
			contents[27] = getItem(omp, KitPvPKit.ASSASSIN);
			contents[28] = getItem(omp, KitPvPKit.LORD);
			contents[29] = getItem(omp, KitPvPKit.VAMPIRE);
			contents[30] = getItem(omp, KitPvPKit.DARKMAGE);
			contents[31] = getItem(omp, KitPvPKit.BEAST);
			contents[32] = getItem(omp, KitPvPKit.FISH);
			contents[33] = getItem(omp, KitPvPKit.HEAVY);
			contents[34] = getItem(omp, KitPvPKit.GRIMREAPER);
			contents[35] = getItem(omp, KitPvPKit.MINER);
			
			return contents;
		}
		
		private ItemStack getItem(OMPlayer omp, KitPvPKit kit){
			KitPvPPlayer kp = omp.getKitPvPPlayer();
			int kitlevel = kp.getUnlockedLevel(kit);
			
			ItemStack item = new ItemStack(kit.getMaterial(), kitlevel);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(kit.getKitName(kitlevel));
			itemmeta.setLore(kit.getKitLore(kitlevel));
			item.setItemMeta(itemmeta);
			if(kitlevel != 0){
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			}
			
			return Utils.hideFlags(item, 3);
		}
	}

	public static class TeleporterInv {
		
		private Inventory inventory;
		
		public TeleporterInv(){
			Inventory inventory = Bukkit.createInventory(null, 27, "§0§lTeleporter");
			this.inventory = inventory;
		}
		
		public Inventory getInventory(){
			return inventory;
		}
		public void setInventory(Inventory inventory){
			this.inventory = inventory;
		}
		
		public void open(Player player){
			update(player, inventory);
			player.openInventory(getInventory());
		}
		
		@SuppressWarnings("unused")
		private void update(Player player, Inventory inventory){
			OMPlayer omp = OMPlayer.getOMPlayer(player);

			List<OMPlayer> players = new ArrayList<OMPlayer>();
			inventory.clear();
			
			for(Player p : Bukkit.getOnlinePlayers()){
				OMPlayer omplayer = OMPlayer.getOMPlayer(p);
				KitPvPPlayer kp = omplayer.getKitPvPPlayer();
				
				if(kp.isPlayer() && kp.getKitSelected() != null){
					players.add(omplayer);
				}
			}

			if(players.size() <= 27){
				inventory.setMaxStackSize(27);
			}
			else if(players.size() > 27){
				inventory.setMaxStackSize(36);
			}
			else if(players.size() > 36){
				inventory.setMaxStackSize(45);
			}
			else if(players.size() > 45){
				inventory.setMaxStackSize(54);
			}
			else{}
			
			int index = 0;
			for(OMPlayer omplayer : players){
				if(index <= 53){
					getItem(omplayer);
				}
				
				index++;
			}
		}
		
		private ItemStack getItem(OMPlayer omp){
			KitPvPPlayer kp = omp.getKitPvPPlayer();
			
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
			SkullMeta itemmeta = (SkullMeta) item.getItemMeta();
			itemmeta.setOwner(kp.getPlayer().getName());
			itemmeta.setDisplayName(omp.getName());
			List<String> itemlore = new ArrayList<String>();
			itemlore.add("");
			itemlore.add("§7Kit: " + kp.getKitSelected().getSelectedKitName(kp.getKitLevelSelected()));
			itemlore.add("§cHealth: §f" + String.format("%.1f", ((CraftPlayer) omp.getPlayer()).getHealth() / 2).replaceAll(",", ".") + "/10.0");
			itemlore.add("§6Food: §f" + String.format("%.1f", (double) omp.getPlayer().getFoodLevel() / 2).replaceAll(",", ".") + "/10.0");
			itemlore.add("§9Current Streak: §f" + kp.getCurrentStreak());
			itemlore.add("");
			itemlore.add("§c§lKitPvP Stats:");
			itemlore.add("§cKills: §f" + kp.getKills());
			itemlore.add("§4Deaths: §f" + kp.getDeaths());
			itemlore.add("§eLevel: §f" + kp.getLevels());
			itemlore.add("§bBest Streak: §f" + kp.getBestStreak());
			itemlore.add("");
			itemlore.add("§e§lClick Here to Teleport");
			itemlore.add("");
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			item.setDurability((short) 3);
			
			return item;
		}
	}
}
