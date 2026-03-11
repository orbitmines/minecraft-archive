package me.O_o_Fadi_o_O.Survival.events;

import me.O_o_Fadi_o_O.Survival.DisguisePlayer;
import me.O_o_Fadi_o_O.Survival.Start;
import me.O_o_Fadi_o_O.Survival.Inv.ChatColors;
import me.O_o_Fadi_o_O.Survival.Inv.CosmeticPerks;
import me.O_o_Fadi_o_O.Survival.Inv.Disguises;
import me.O_o_Fadi_o_O.Survival.Inv.OMTShop;
import me.O_o_Fadi_o_O.Survival.Inv.PetRenameGUI;
import me.O_o_Fadi_o_O.Survival.Inv.Pets;
import me.O_o_Fadi_o_O.Survival.Inv.Trails;
import me.O_o_Fadi_o_O.Survival.jobs.managers.JobManager;
import me.O_o_Fadi_o_O.Survival.jobs.managers.MerchantManager;
import me.O_o_Fadi_o_O.Survival.managers.ConfigManager;
import me.O_o_Fadi_o_O.Survival.managers.ConfirmManager;
import me.O_o_Fadi_o_O.Survival.managers.DatabaseManager;
import me.O_o_Fadi_o_O.Survival.managers.PlayerManager;
import me.O_o_Fadi_o_O.Survival.managers.RegionManager;
import me.O_o_Fadi_o_O.Survival.managers.StorageManager;
import me.O_o_Fadi_o_O.Survival.utils.ChatColor;
import me.O_o_Fadi_o_O.Survival.utils.Currency;
import me.O_o_Fadi_o_O.Survival.utils.Disguise;
import me.O_o_Fadi_o_O.Survival.utils.Pet;
import me.O_o_Fadi_o_O.Survival.utils.ReflectionUtil;
import me.O_o_Fadi_o_O.Survival.utils.Server;
import me.O_o_Fadi_o_O.Survival.utils.StaffRank;
import me.O_o_Fadi_o_O.Survival.utils.Trail;
import me.O_o_Fadi_o_O.Survival.utils.TrailType;
import me.O_o_Fadi_o_O.Survival.utils.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ClickEvent implements Listener {
	
	Start start = Start.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e){
		
		if(e.getWhoClicked() instanceof Player){
			
			final Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			try{
				if(e.getInventory().getName().equals("§0§lOMT Shop")){
					e.setCancelled(true);
					if(item.getType() == Material.STONE_HOE && item.getItemMeta().getDisplayName().equals("§8§l+100 Claimblocks")){
						if(StorageManager.orbitminestokens.get(p) >= 2){
							p.openInventory(ConfirmManager.getConfirmInv(item, Currency.ORBITMINES_TOKENS, 2));
						}
						else{
							PlayerManager.sendOMTRequiredMessage(p, 2);
						}
					}
					if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§2§l+400$")){
						if(StorageManager.orbitminestokens.get(p) >= 3){
							p.openInventory(ConfirmManager.getConfirmInv(item, Currency.ORBITMINES_TOKENS, 3));
						}
						else{
							PlayerManager.sendOMTRequiredMessage(p, 3);
						}
					}
					if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§7§lYour Skull")){
						if(p.getInventory().firstEmpty() != -1){
							if(StorageManager.orbitminestokens.get(p) >= 50){
								p.openInventory(ConfirmManager.getConfirmInv(item, Currency.ORBITMINES_TOKENS, 50));
							}
							else{
								PlayerManager.sendOMTRequiredMessage(p, 50);
							}
						}
						else{
							p.sendMessage("§eOMT Shop §8| §7Your inventory is full!");
						}
					}
					if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§lMerchant Announcements")){
						if(StorageManager.orbitminestokens.get(p) >= 100){
							p.openInventory(ConfirmManager.getConfirmInv(item, Currency.ORBITMINES_TOKENS, 100));
						}
						else{
							PlayerManager.sendOMTRequiredMessage(p, 100);
						}
					}
				}
				if(e.getInventory().getName().equals("§0§lRegion Teleporter")){
					e.setCancelled(true);
					if(item.getItemMeta().getDisplayName().startsWith("§7§lRegion")){
						int region = Integer.parseInt(item.getItemMeta().getDisplayName().substring(15));
						RegionManager.teleportToRegion(p, region);
					}
				}
		
				
						if(bitem.getItemMeta().getDisplayName().equals("§8§l+100 Claimblocks")){

							DatabaseManager.removeOMT(p, 2);
							
							PlayerManager.addClaimblocks(p, 100);
							p.openInventory(OMTShop.getOMTShop(p));
						}
						if(bitem.getItemMeta().getDisplayName().equals("§2§l+400$")){

							DatabaseManager.removeOMT(p, 3);
							
							JobManager.addMoney(p, 400);
							p.openInventory(OMTShop.getOMTShop(p));
						}
						if(bitem.getItemMeta().getDisplayName().equals("§7§lYour Skull")){

							DatabaseManager.removeOMT(p, 50);
							
							ItemStack i = new ItemStack(Material.SKULL_ITEM, 1);
							i.setDurability((short) 3);
							SkullMeta meta = (SkullMeta) i.getItemMeta();
							meta.setOwner(p.getName());
							i.setItemMeta(meta);
							p.getInventory().addItem(i);
							
							p.openInventory(OMTShop.getOMTShop(p));
						}
						
					if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§lCancel")){
						
						if(bitem.getItemMeta().getDisplayName().equals("§8§l+100 Claimblocks") || bitem.getItemMeta().getDisplayName().equals("§2§l+400$") || bitem.getItemMeta().getDisplayName().equals("§7§lYour Skull") || bitem.getItemMeta().getDisplayName().equals("§c§lMerchant Announcements")){
							p.openInventory(OMTShop.getOMTShop(p));
						}
						
					}
				}
				
				
				
			}catch(Exception ex){}
		}
	}
}
