package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.Mine;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.utils.enums.MineType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Fadi on 19-9-2016.
 */
public class PlaceEvent implements Listener {

    private OrbitMinesPrison prison;

    public PlaceEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        if(omp.isLoaded()){
            if(omp.isOpMode())
                return;

            if(p.getWorld().getName().equals(prison.getPrisonWorld().getName())){
                Mine mine = omp.inMine(e.getBlockPlaced().getLocation(), true);
                if(mine == null || mine.getType() != MineType.NORMAL)
                    e.setCancelled(true);
            }
            else if(p.getWorld().getName().equals(prison.getCellWorld().getName())){
                e.setCancelled(!omp.isOnCell(e.getBlockPlaced().getLocation()));
            }
            else if(p.getWorld().getName().equals(prison.getLobby().getName())){
                if(omp.getShop() == null || !omp.getShop().isInShop(e.getBlockPlaced().getLocation()))
                    e.setCancelled(true);
            }
        }
        else{
            e.setCancelled(true);
            omp.notLoaded();
        }
    }
}
