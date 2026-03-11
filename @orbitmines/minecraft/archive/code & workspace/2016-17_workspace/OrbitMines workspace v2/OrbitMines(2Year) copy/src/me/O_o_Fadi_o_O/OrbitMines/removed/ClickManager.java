package me.O_o_Fadi_o_O.OrbitMines.removed;

import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.*;
import me.O_o_Fadi_o_O.OrbitMines.removed.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.removed.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.ChatColor;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativeUtils.PlotType;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Inventories.*;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.ActiveBooster;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.Booster;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils.InvType;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils.TicketType;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Inventories.GoldShopInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Inventories.MineInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Inventories.VillagerGambleInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils.GambleType;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils.MineType;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils.Rank;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Inventories;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Inventories.ChallengesInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Inventories.IslandInfoInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Inventories.IslandMembersInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockUtils.ChallengeType;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Inventories.WarpEditorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Inventories.WarpInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Inventories.WarpItemEditorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Inventories.WarpRenameInv;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

public class ClickManager {

	private InventoryClickEvent e;
	private Player p;
	private OMPlayer omp;
	private ItemStack item;
	private InventoryAction a;
	
	public ClickManager(InventoryClickEvent e){
		this.e = e;
		this.p = (Player) e.getWhoClicked();
		this.omp = OMPlayer.getOMPlayer(p);
		this.item = e.getCurrentItem();
		this.a = e.getAction();
	}
	
	private boolean itemIsNull(){
		return !(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null);
	}

	public void handleAnvilInventory(){
		if(!itemIsNull()){
			if(ServerData.isServer(Server.HUB, Server.MINIGAMES) && p.getOpenInventory().getTopInventory() instanceof AnvilInventory && p.getOpenInventory().getBottomInventory() == e.getInventory() && item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
		}
	}


	public void handleConfirm(){
		if(e.getInventory().getName().equals(new ConfirmInv(null, null, 0).getInventory().getTitle())){
			e.setCancelled(true);

			if(!itemIsNull()){
				if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§a§lConfirm")){
					ItemStack bitem = e.getInventory().getItem(13);
					ItemStack pitem = e.getInventory().getItem(31);

					if(!bitem.getItemMeta().getDisplayName().endsWith("Booster") || !ServerData.isServer(Server.KITPVP)){
						p.sendMessage("§7Item Bought: " + bitem.getItemMeta().getDisplayName() + "§7.");
						p.sendMessage("§7Price: " + pitem.getItemMeta().getDisplayName().substring(9) + "§7.");
					}
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);

					if(ServerData.isServer(Server.PRISON) && bitem.getItemMeta().getDisplayName().equals("§a§l+5 Gamble Tickets")){
						omp.removeVIPPoints(250);
						omp.getPrisonPlayer().addGambleTickets(5);
						new VillagerGambleInv(false).open(p);
					}
					else if(ServerData.isServer(Server.PRISON) && bitem.getItemMeta().getDisplayName().equals("§0§a§l+1 Gamble Ticket")){
						omp.removeOrbitMinesTokens(5);
						omp.getPrisonPlayer().addGambleTickets(1);
						new VillagerGambleInv(false).open(p);
					}
					else if(ServerData.isServer(Server.PRISON) && bitem.getItemMeta().getDisplayName().equals("§a§l+1 Gamble Ticket")){
						omp.getPrisonPlayer().removeGold(10000);
						omp.getPrisonPlayer().addGambleTickets(1);
						new VillagerGambleInv(false).open(p);
					}
				}
				if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§lCancel")){
					ItemStack bitem = e.getInventory().getItem(13);

					p.sendMessage("§c§lCancelled §7purchase! (" + bitem.getItemMeta().getDisplayName() + "§7)");


					if(ServerData.isServer(Server.PRISON) && (bitem.getItemMeta().getDisplayName().equals("§a§l+5 Gamble Tickets") || bitem.getItemMeta().getDisplayName().equals("§a§l+1 Gamble Ticket") || bitem.getItemMeta().getDisplayName().equals("§0§a§l+1 Gamble Ticket"))){
						new VillagerGambleInv(false).open(p);
					}
				}
			}
		}
	}
	
	public void handleVillagerGamble(){
		if(e.getInventory().getName().equals(new VillagerGambleInv(false).getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				PrisonPlayer pp = omp.getPrisonPlayer();
				
				if(item.getType() == Material.IRON_BLOCK && item.getItemMeta().getDisplayName().equals("§7§lStart Iron Gamble Machine")){
					if(pp.hasGambleTickets(1)){
						pp.removeGambleTickets(1);
						VillagerGambleInv gambleinv = new VillagerGambleInv(true);
						gambleinv.update(p, GambleType.IRON_MACHINE);
					}
					else{
						pp.requiredGambleTickets(1);
					}
			    }
				else if(item.getType() == Material.GOLD_BLOCK && item.getItemMeta().getDisplayName().equals("§6§lStart Gold Gamble Machine")){
					if(pp.hasGambleTickets(3)){
						pp.removeGambleTickets(3);
						VillagerGambleInv gambleinv = new VillagerGambleInv(true);
						gambleinv.update(p, GambleType.GOLD_MACHINE);
					}
					else{
						pp.requiredGambleTickets(3);
					}
			    }
				else if(item.getType() == Material.DIAMOND_BLOCK && item.getItemMeta().getDisplayName().equals("§b§lStart Diamond Gamble Machine")){
					if(pp.hasGambleTickets(5)){
						pp.removeGambleTickets(5);
						VillagerGambleInv gambleinv = new VillagerGambleInv(true);
						gambleinv.update(p, GambleType.DIAMOND_MACHINE);
					}
					else{
						pp.requiredGambleTickets(5);
					}
			    }
				else if(item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().equals("§a§l+5 Gamble Tickets")){
					if(omp.hasVIPPoints(250)){
						new ConfirmInv(item, Currency.VIP_POINTS, 250).open(p);
					}
					else{
						omp.requiredVIPPoints(250);
					}
				}
				else if(item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().equals("§0§a§l+1 Gamble Ticket")){
					if(omp.hasOrbitMinesTokens(5)){
						new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 5).open(p);
					}
					else{
						omp.requiredOMT(5);
					}
				}
				else if(item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().equals("§a§l+1 Gamble Ticket")){
					if(omp.getPrisonPlayer().hasGold(10000)){
						new ConfirmInv(item, Currency.PRISON_GOLD, 10000).open(p);
					}
					else{
						omp.getPrisonPlayer().requiredGold(10000);
					}
				}
				else{}
			}
		}
	}
}
