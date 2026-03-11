package me.O_o_Fadi_o_O.OrbitMines.utils.hub;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.ChatColor;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Currency;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Disguise;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Gadget;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.GameState;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Hat;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.InventoryEnum;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Pet;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Trail;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.TrailType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Arena;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.ChickenFightPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.SkywarsPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.SurvivalGamesPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.UHCPlayer;
import om.api.handlers.AnvilGUI;
import om.api.utils.enums.cp.FireworkSettings;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Inventories {
	
	public static class SettingsInv {
		
		private Inventory inventory;
		
		public SettingsInv(Player player){
			Inventory inventory = Bukkit.createInventory(null, 27, "§0§lSettings (" + player.getName() + ")");
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
			
			if(omp.hasPlayersEnabled()){
				contents[11] = Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.EYE_OF_ENDER), "§3§lPlayers §a§lENABLED"), Enchantment.DURABILITY, 1), 1);
			}
			else{
				contents[11] = Utils.setDisplayname(new ItemStack(Material.EYE_OF_ENDER), "§3§lPlayers §c§lDISABLED");
			}
			
			if(omp.hasScoreboardEnabled()){
				contents[13] = Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.PAPER), "§f§lScoreboard §a§lENABLED"), Enchantment.DURABILITY, 1), 1);
			}
			else{
				contents[13] = Utils.setDisplayname(new ItemStack(Material.PAPER), "§f§lScoreboard §c§lDISABLED");
			}
			
			if(omp.hasStackerEnabled()){
				contents[15] = Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEASH), "§6§lStacker §a§lENABLED"), Enchantment.DURABILITY, 1), 1);
			}
			else{
				contents[15] = Utils.setDisplayname(new ItemStack(Material.LEASH), "§6§lStacker §c§lDISABLED");
			}
					
			return contents;
		}
	}
