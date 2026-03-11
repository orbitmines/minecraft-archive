package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MoveEvent implements Listener {

    private OrbitMinesAPI api;

    public MoveEvent(){
        this.api = OrbitMinesAPI.getApi();
    }

	@EventHandler
    public void onMove(final PlayerMoveEvent e) {
    	Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(omp.isLoaded()){
			omp.checkLastLocation();
			
			if(omp.isAfk())
				omp.noLongerAfk();
			
			if(omp.canReceiveVelocity() && omp.hasUnlockedHatsBlockTrail() && omp.hasHatsBlockTrail() && omp.hasHatEnabled()){
				final Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
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
					}.runTaskLater(api, 40);
				}
			}
		}
    }
}
