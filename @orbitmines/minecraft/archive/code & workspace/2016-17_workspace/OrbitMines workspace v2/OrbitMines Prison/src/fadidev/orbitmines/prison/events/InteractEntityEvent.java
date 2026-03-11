package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Fadi on 19-9-2016.
 */
public class InteractEntityEvent implements Listener {

    private OrbitMinesPrison prison;

    public InteractEntityEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        if(omp.isLoaded()){
            if(omp.isOpMode())
                return;

            if(p.getWorld().getName().equals(prison.getLobby().getName())) {
                if(e.getRightClicked() != null && (omp.getShop() == null || !omp.getShop().isInShop(e.getRightClicked().getLocation())))
                    e.setCancelled(true);
            }
            else if(p.getWorld().getName().equals(prison.getCellWorld().getName())) {
                if(!(e.getRightClicked() instanceof Player) && !omp.isOnCell(e.getRightClicked().getLocation()))
                    e.setCancelled(true);
            }
        }
        else{
            e.setCancelled(true);
            omp.updateInventory();
            omp.notLoaded();
        }
    }
}
