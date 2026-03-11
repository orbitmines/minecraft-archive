package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Fadi on 20-9-2016.
 */
public class InteractEntityEvent implements Listener {

    private OrbitMinesSkyBlock skyBlock;

    public InteractEntityEvent(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);

        if(!omp.isLoaded() || omp.isOpMode())
            return;

        if(p.getWorld().getName().equals(skyBlock.getSkyBlockWorld().getName()) || p.getWorld().getName().equals(skyBlock.getSkyBlockNetherWorld().getName())){
            if(omp.hasIsland()){
                if(!omp.onIsland(e.getRightClicked().getLocation(), true))
                    e.setCancelled(true);
            }
            else{
                e.setCancelled(true);
            }
        }
        else if(p.getWorld().getName().equals(skyBlock.getLobby().getName())){
            e.setCancelled(true);
        }
    }
}
