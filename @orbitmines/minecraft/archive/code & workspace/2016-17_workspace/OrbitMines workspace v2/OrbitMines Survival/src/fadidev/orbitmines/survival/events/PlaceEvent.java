package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.Region;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class PlaceEvent implements Listener {

    private OrbitMinesSurvival survival;

    public PlaceEvent(){
        survival = OrbitMinesSurvival.getSurvival();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(p);

        if(omp.isLoaded()){
            if(omp.isOpMode())
                return;

            if(p.getWorld().getName().equals(survival.getLobby().getName())){
                e.setCancelled(true);
            }
            else if(p.getWorld().getName().equals(survival.getSurvivalWorld().getName())){
                if(Region.isInRegion(omp, e.getBlock().getLocation())) {
                    e.setCancelled(true);
                    omp.updateInventory();
                }
            }
        }
        else{
            omp.notLoaded();
            e.setCancelled(true);
        }
    }
}
