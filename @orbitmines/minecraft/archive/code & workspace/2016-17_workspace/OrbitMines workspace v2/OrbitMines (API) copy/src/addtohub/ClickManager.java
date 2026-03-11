package addtohub;

import addtohub.API;
import addtohub.OMPlayer;
import om.api.invs.cp.ChatColorInv;
import om.api.invs.cp.CosmeticPerksInv;
import om.api.invs.cp.DisguiseInv;
import om.api.invs.cp.FireworkInv;
import om.api.invs.cp.GadgetInv;
import om.api.invs.cp.HatInv;
import om.api.invs.cp.PetInv;
import om.api.invs.cp.PetRenameGUI;
import om.api.invs.cp.TrailInv;
import om.api.invs.cp.TrailSettingsInv;
import om.api.invs.cp.WardrobeInv;
import om.api.invs.others.ConfirmInv;
import om.api.invs.others.InventorySeeInv;
import noteinclude.Currency;
import om.api.utils.enums.cp.ChatColor;
import om.api.utils.enums.cp.CosmeticPerk;
import om.api.utils.enums.cp.Disguise;
import om.api.utils.enums.cp.Gadget;
import om.api.utils.enums.cp.Hat;
import om.api.utils.enums.cp.Pet;
import om.api.utils.enums.cp.Trail;
import om.api.utils.enums.cp.TrailType;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ClickManager {
	
	protected API api;
	protected InventoryClickEvent e;
	protected Player p;
	protected OMPlayer omp;
	protected ItemStack item;
	protected InventoryAction a;
	
	public ClickManager(InventoryClickEvent e){
		this.api = API.getInstance();
		this.e = e;
		this.p = (Player) e.getWhoClicked();
		this.omp = OMPlayer.getOMPlayer(p);
		this.item = e.getCurrentItem();
		this.a = e.getAction();
	}
	
	public boolean handleChestShopViewer(){
		if(e.getInventory().getName().equals("§0§lChest Shop Viewer")){
			e.setCancelled(true);
			
			return true;
		}
		return false;
	}
	
	public boolean handleEnchantmentTable(){
		if(e.getClickedInventory() instanceof EnchantingInventory){
			if(item != null && item.getType() == Material.INK_SACK && item.getDurability() == 4){
				e.setCancelled(true);
			}
			
			return true;
		}
		return false;
	}
}
