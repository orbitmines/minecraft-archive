package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by Fadi on 1-10-2016.
 */
public class PickupEvent implements Listener {

    private OrbitMinesMiniGames miniGames;

    public PickupEvent(){
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

         if(arena == null)
             return;

        if(arena.getType() == MiniGameType.SURVIVAL_GAMES || arena.getType() == MiniGameType.ULTRA_HARD_CORE || arena.getType() == MiniGameType.SKYWARS){
            if(arena.getState() != State.IN_GAME || arena.isSpectator(omp)){
                e.setCancelled(true);
            }
        }
        else{
            e.setCancelled(true);
        }
    }
}
