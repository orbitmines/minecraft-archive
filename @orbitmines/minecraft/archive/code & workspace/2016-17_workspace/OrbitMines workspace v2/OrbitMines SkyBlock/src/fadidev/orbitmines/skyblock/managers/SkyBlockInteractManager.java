package fadidev.orbitmines.skyblock.managers;

import fadidev.orbitmines.api.managers.InteractManager;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SkyBlockInteractManager extends InteractManager {

    /*
     * TODO
     * Not the best code: (Old Code)
     * Should edit this shortly.
     */

    private OrbitMinesSkyBlock skyBlock;
    private SkyBlockPlayer omp;

    public SkyBlockInteractManager(PlayerInteractEvent e) {
        super(e);

        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
        omp = SkyBlockPlayer.getSkyBlockPlayer(e.getPlayer());
    }

    public void handleIsland(){
        if(p.getWorld().getName().equals(skyBlock.getSkyBlockWorld().getName()) || p.getWorld().getName().equals(skyBlock.getSkyBlockNetherWorld().getName())){
            if(omp.hasIsland()){
                if(a != Action.LEFT_CLICK_AIR && a != Action.RIGHT_CLICK_AIR && !omp.onIsland(e.getClickedBlock().getLocation(), true))
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
