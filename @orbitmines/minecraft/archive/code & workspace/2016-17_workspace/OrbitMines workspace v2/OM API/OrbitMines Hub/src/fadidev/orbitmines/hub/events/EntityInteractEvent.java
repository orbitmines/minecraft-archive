package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class EntityInteractEvent implements Listener {

    private OrbitMinesHub hub;

    public EntityInteractEvent(){
        hub = OrbitMinesHub.getHub();
    }

    @EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent e){
        final Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);

        if(!omp.isOpMode() && p.getWorld().getName().equals(hub.getLobby().getName())){
            e.setCancelled(true);
            omp.updateInventory();
        }
    }
}
