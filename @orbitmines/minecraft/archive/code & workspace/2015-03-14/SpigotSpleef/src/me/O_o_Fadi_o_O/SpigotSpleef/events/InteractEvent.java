package me.O_o_Fadi_o_O.SpigotSpleef.events;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.AbilityManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ArenaManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.MapManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Ability;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Arena;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.ArenaSign;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.ItemType;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Kit;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.KitItemStack;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.LobbyItemStack;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpectatorItemStack;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefStatus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Action a = e.getAction();
		Block b = e.getClickedBlock();
		ItemStack i = p.getItemInHand();
		
		if(b != null && b.getType() == Material.SIGN_POST || b != null && b.getType() == Material.WALL_SIGN){
			if(StorageManager.spleefplayer.containsKey(p)){
				SpleefPlayer sp = StorageManager.spleefplayer.get(p);
				
				if(!sp.isInArena()){
					if(ArenaSign.isSignArenaSign(b.getLocation())){
						e.setCancelled(true);
						Arena arena = ArenaSign.getArenaSign(b.getLocation()).getArena();
						
						if(arena.isStatus(SpleefStatus.WAITING) || arena.isStatus(SpleefStatus.STARTING)){
							if(!arena.isFull()){
								sp.joinSpleef(arena);
							}
							else{
								//TODO IS FULL
							}
						}
						else{
							if(!arena.isStatus(SpleefStatus.RESTARTING)){
								sp.joinSpleef(arena);
							}
						}
					}
				}
			}
		}
		
		if(StorageManager.mapsetup.containsKey(p)){
			if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
				MapManager.openSetupInventory(p, StorageManager.mapsetup.get(p));
			}
		}
		if(StorageManager.arenasetup.containsKey(p)){
			if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
				
				if(b != null && b.getType() == Material.SIGN_POST || b != null && b.getType() == Material.WALL_SIGN){
					e.setCancelled(true);
					ArenaManager.setArenaSign(p, b);
				}
				else{
					ArenaManager.openSetupInventory(p, StorageManager.arenasetup.get(p));
				}
			}
		}
		
		if(StorageManager.spleefplayer.containsKey(p)){
			SpleefPlayer sp = StorageManager.spleefplayer.get(p);
			
			if(sp.isInArena()){
				if(sp.isPlayer()){
					if(sp.getArena().isStatus(SpleefStatus.INGAME)){
						if(a == Action.LEFT_CLICK_BLOCK){
							sp.getArena().breakBlock(sp, e.getClickedBlock());
						}

						if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){
							if(i != null && i.getType() != Material.AIR){
								Kit kit = sp.getKit();
								int index = p.getInventory().first(i);
								
								KitItemStack item = kit.getItems().get(index);
								if(item.containsAbility(Ability.JUMP)){
									AbilityManager.checkJump(sp, item);
								}
							}
						}
						
						if(a == Action.RIGHT_CLICK_BLOCK && b != null){
							Material m = b.getType();
							if(m == Material.CHEST || m == Material.ENDER_CHEST || m == Material.TRAPPED_CHEST || m == Material.FURNACE || m == Material.WORKBENCH || m == Material.BREWING_STAND || m == Material.ENCHANTMENT_TABLE || m == Material.ANVIL || m == Material.HOPPER){
								e.setCancelled(true);
							}
						}
					}
					else{
						e.setCancelled(true);
						
						if(i != null){
							if(sp.getArena().isStatus(SpleefStatus.WAITING) || sp.getArena().isStatus(SpleefStatus.STARTING)){
								if(i.getItemMeta() != null && i.getItemMeta().getDisplayName() != null){
									int index = p.getInventory().first(i);
									
									LobbyItemStack item = StorageManager.lobbyinv.get(index);
									if(item.getItemType() == ItemType.LEAVE_GAME){
										sp.quitSpleef();
									}
								}
							}
						}
					}
				}
				else{
					e.setCancelled(true);
					
					if(i != null && i.getItemMeta() != null && i.getItemMeta().getDisplayName() != null){
						int index = p.getInventory().first(i);
						
						SpectatorItemStack item = StorageManager.spectatorinv.get(index);
						if(item.getItemType() == ItemType.LEAVE_GAME){
							sp.quitSpleef();
						}
					}
				}
			}
		}
	}
}
