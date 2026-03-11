package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.KitPvPServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerMove implements Listener{
	
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onMove(final PlayerMoveEvent e) {
    	Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(omp.isLoaded()){
			omp.checkLastLocation();
			
			if(omp.isAFK()){
				omp.noLongerAFK();
			}
			
			if(ServerData.isServer(Server.HUB)){
				if(!omp.canChat()){
					omp.setCanChat(true);
				}
			
				if(p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
					for(Block b : ServerData.getHub().getMCBlocksForTurn().get(0)){
						if(p.getLocation().getY() >= 43 && p.getLocation().distance(new Location(b.getWorld(), b.getLocation().getX() + 0.5, b.getLocation().getY(), b.getLocation().getZ() + 0.5)) <= 1.5){
							p.teleport(ServerData.getHub().getMindCraftLocation());
						}
					}
				
					{
						/*
						 * Server Portals..
						 */
						Block b = p.getWorld().getBlockAt(p.getLocation());
					
						for(Server server : Server.values()){
							if(ServerData.getHub().getServerPortals().get(server) != null && ServerData.getHub().getServerPortals().get(server).contains(b)){
								if(!omp.onCooldown(Cooldown.PORTAL_USAGE)){
									omp.toServer(server);
									omp.resetCooldown(Cooldown.PORTAL_USAGE);
								}
							}
						}
					}
				}
			}
			else if(ServerData.isServer(Server.KITPVP)){
				KitPvPServer kitpvp = ServerData.getKitPvP();
				KitPvPPlayer kp = omp.getKitPvPPlayer();
				
				if(kp.getKitSelected() != null || kp.isSpectator()){
					if(p.getLocation().getY() >= kitpvp.getCurrentMap().getMaxY()){
						p.setVelocity(new Vector(0, 0, 0));
					    Location l = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() -2, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
					    p.teleport(l);
					    p.sendMessage("§7§lStay in the Arena!");
					    p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
					}
				}
			}
			else{}
			
			if(omp.hasUnlockedHatsBlockTrail() && omp.hasHatsBlockTrail() && omp.hasHatEnabled()){	
				final Block b = p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() -1, p.getLocation().getZ()));
				Material mb = b.getType();
				
				if(!b.isEmpty() && mb.getId() != 175 && mb.getId() != 31 && mb != Material.SKULL && mb != Material.LAPIS_BLOCK && mb != Material.STEP && mb != Material.STEP && mb != Material.WATER && mb != Material.LAVA && mb != Material.WALL_SIGN && mb != Material.SIGN_POST && mb != Material.SNOW && mb != Material.STATIONARY_LAVA && mb != Material.STATIONARY_WATER && mb != Material.FENCE && mb != Material.TORCH && mb != Material.TRAP_DOOR){
					Material m = p.getInventory().getHelmet().getType();
					byte mB = (byte) p.getInventory().getHelmet().getDurability();
					
					for(Player player : Bukkit.getOnlinePlayers()){
						player.sendBlockChange(b.getLocation(), m, mB);
					}
					
					new BukkitRunnable(){
						public void run(){
							for(Player player : Bukkit.getOnlinePlayers()){
								player.sendBlockChange(b.getLocation(), b.getType(), b.getData());
							}
						}
					}.runTaskLater(Start.getInstance(), 40);
				}
			}
		}
    }
}
