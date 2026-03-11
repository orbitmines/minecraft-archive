package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class EntityInteractEvent implements Listener {

    private OrbitMinesMiniGames miniGames;

    public EntityInteractEvent(){
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent e){
        final Player p = e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);

        if(!omp.isOpMode() && p.getWorld().getName().equals(miniGames.getLobby().getName())){
            e.setCancelled(true);
            omp.updateInventory();
        }
    }
}
