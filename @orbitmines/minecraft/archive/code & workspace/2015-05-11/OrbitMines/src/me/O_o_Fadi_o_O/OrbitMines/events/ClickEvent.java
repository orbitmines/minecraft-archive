package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.managers.ClickManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {
	
	Start hub = Start.getInstance();
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player){
			final Player p = (Player) e.getWhoClicked();
			OMPlayer omp = OMPlayer.getOMPlayer(p);
			ClickManager manager = new ClickManager(e);
			ItemStack item = e.getCurrentItem();
			
			if(!ServerData.isServer(Server.SURVIVAL, Server.PRISON, Server.CREATIVE) && p.getWorld().getName().equals(ServerData.getLobbyWorld().getName()) && omp.isLoaded() && !omp.isOpMode()){
				e.setResult(Result.DENY);
				omp.updateInventory();
			}
			
			if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
				manager.handleServerSelector();
				manager.handleSettings();
				manager.handleCosmeticPerks();
				manager.handleAnvilInventory();
				manager.handleFireworks();
				manager.handleHats();
				manager.handleGadgets();
				manager.handleTrails();
				manager.handleWardrobe();
				manager.handleChatColors();
				manager.handleDisguises();
				manager.handlePets();
				manager.handleTrailSettings();
				manager.handleConfirm();
				manager.handleOMTShop();
			}
		}
	}
}
