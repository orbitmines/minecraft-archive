package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.NPC;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.NPCType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

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
			if(p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
				if(!omp.isOpMode()){
					e.setCancelled(true);
					omp.updateInventory();
				}
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
