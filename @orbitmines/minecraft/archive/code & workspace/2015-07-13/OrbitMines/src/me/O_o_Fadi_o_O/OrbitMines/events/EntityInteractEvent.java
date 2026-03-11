package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.OMTShopInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.NPC;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.CreativeServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.SkyBlockServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.NPCType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.KitSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityInteractEvent implements Listener{
	
	@EventHandler
	public void onPlayerEntityInteract(PlayerInteractEntityEvent e){
		final Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		Entity en = e.getRightClicked();
		ItemStack item = p.getItemInHand();
		
		if(omp.isLoaded()){
			if(!omp.isOpMode() && p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
				e.setCancelled(true);
				omp.updateInventory();
			}
			
			if(ServerStorage.pets.contains(en)){
				e.setCancelled(true);
				omp.updateInventory();
			}
			
			if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
				if(omp.getSoccerMagmaCube() == en){
					e.setCancelled(true);
					omp.disableSoccerMagmaCube();
					omp.updateInventory();
				}
				if(omp.getPet() == en && item != null && item.getType() == Material.SADDLE && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals("§e§nPet Ride")){
					e.setCancelled(true);
					en.setPassenger(p);
					omp.updateInventory();
				}
				if(item != null){
					if(item.getType() == Material.MONSTER_EGG || item.getType() == Material.EGG){
						e.setCancelled(true);
						omp.updateInventory();
					}
				}
			}
			else if(ServerData.isServer(Server.CREATIVE)){
				if(!omp.isOpMode()){
					CreativeServer creative = ServerData.getCreative();
					CreativePlayer cp = omp.getCreativePlayer();
				
					if(p.getWorld().getName().equals(creative.getPlotWorld().getName())){
						if(!(en instanceof Player) && !cp.isOnPlot(en.getLocation())){
							e.setCancelled(true);
						}
					}
					else if(p.getWorld().getName().equals(creative.getCreativeWorld().getName())){
						e.setCancelled(true);
					}
					else{}
					
					if(item != null && item.getType() == Material.MONSTER_EGG){
						e.setCancelled(true);
						omp.updateInventory();
					}
				}
			}
			else if(ServerData.isServer(Server.SKYBLOCK)){
				if(!omp.isOpMode()){
					SkyBlockServer skyblock = ServerData.getSkyBlock();
					SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
					
					if(p.getWorld().getName().equals(skyblock.getSkyblockWorld().getName()) || p.getWorld().getName().equals(skyblock.getSkyblockNetherWorld().getName())){
						if(sbp.hasIsland()){
							if(!sbp.onIsland(en.getLocation(), true)){
								e.setCancelled(true);
							}
						}
						else{
							e.setCancelled(true);
						}
					}
					else if(p.getWorld().getName().equals(skyblock.getLobbyWorld().getName())){
						e.setCancelled(true);
					}
					else{}
				}
			}
			else{}
			
			NPC npc = NPC.getNPC(en);
			if(npc != null){
				e.setCancelled(true);
				omp.updateInventory();
				
				if(!omp.onCooldown(Cooldown.NPC_INTERACT)){
					if(npc.getNPCType() == NPCType.ALPHA){
						omp.toServer(Server.ALPHA);
					}
					else if(npc.getNPCType() == NPCType.MINDCRAFT){
						if(omp.isInMindCraft()){
							omp.getMCPlayer().leave();
						}
						else{
							omp.getMCPlayer().join();
						}
					}
					else if(npc.getNPCType() == NPCType.LAPIS_PARKOUR){
						if(omp.isInLapisParkour()){
							omp.leaveLapisParkour();
						}
						else{
							omp.joinLapisParkour();
						}
					}
					else if(npc.getNPCType() == NPCType.OMT_SHOP){
						new OMTShopInv().open(p);
					}
					else if(npc.getNPCType() == NPCType.KIT_SELECTOR){
						new KitSelectorInv().open(p);
					}
					else if(npc.getNPCType() == NPCType.SPECTATE){
						if(omp.getPet() != null){
							omp.disablePet();
						}
						if(omp.hasTrailEnabled()){
							omp.disableTrail();
						}
						
						KitPvPPlayer kp = omp.getKitPvPPlayer();
						kp.setSpectator();
						kp.teleportToMap();
					}
					else if(npc.getNPCType() == NPCType.TUTORIALS){
						p.teleport(ServerData.getSurvival().getTutorials());
					}
					else if(npc.getNPCType() == NPCType.SPAWN){
						p.teleport(ServerData.getSurvival().getSpawn());
					}
					else{}
					
					omp.resetCooldown(Cooldown.NPC_INTERACT);
				}
			}
		}
		else{
			e.setCancelled(true);
			omp.updateInventory();
			omp.notLoaded();
		}
	}
}
