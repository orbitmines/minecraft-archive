package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ClickEvent implements Listener {

    private OrbitMinesHub hub;

    public ClickEvent(){
        hub = OrbitMinesHub.getHub();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            final Player p = (Player) e.getWhoClicked();
            HubPlayer omp = HubPlayer.getHubPlayer(p);

            if(omp.isLoaded()){
                if(!omp.isOpMode() && p.getWorld().getName().equals(hub.getLobby().getName())){
                    e.setResult(Event.Result.DENY);
                    omp.updateInventory();
                }

                hub.getOmServer().getClickManager().handleOMInventories(e);
            }
        }
    }
}
