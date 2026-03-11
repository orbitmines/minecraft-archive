package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Fadi on 1-10-2016.
 */
public class DropEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(omp.isLoaded()){
            if(omp.isOpMode() || arena == null)
                return;

            if(arena.getType() != MiniGameType.SURVIVAL_GAMES && arena.getType() != MiniGameType.ULTRA_HARD_CORE && arena.getType() != MiniGameType.SKYWARS || (arena.getState() != State.IN_GAME || !arena.getPlayers().contains(omp))){
                e.setCancelled(true);
                omp.updateInventory();
            }
        }
        else{
            e.setCancelled(true);
            omp.updateInventory();
        }
    }
}
