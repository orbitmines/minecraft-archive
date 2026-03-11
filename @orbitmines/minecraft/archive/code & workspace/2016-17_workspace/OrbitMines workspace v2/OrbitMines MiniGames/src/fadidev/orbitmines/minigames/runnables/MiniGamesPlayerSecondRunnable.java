package fadidev.orbitmines.minigames.runnables;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 30-9-2016.
 */
public class MiniGamesPlayerSecondRunnable extends PlayerSecondRunnable {

    private OrbitMinesMiniGames miniGames;

    public MiniGamesPlayerSecondRunnable(){
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        MiniGamesPlayer omp = (MiniGamesPlayer) omPlayer;
        Player p = omp.getPlayer();
        Arena arena = omp.getArena();

        if(arena == null || arena.getState() != State.WAITING && arena.getState() != State.STARTING)
            return;

        if(!omp.isOpMode() && p.getWorld().getName().equals(miniGames.getLobby().getName())){
            Location spawn = arena.getLobby();

            if(p.getLocation().getY() <= 50 || p.getLocation().distance(spawn) >= 50)
                p.teleport(spawn);
        }

        if(arena.getState() == State.STARTING)
            return;

        if(omp.getPet() != null){
            if(p.getVehicle() != null && p.getVehicle() == omp.getPet()){
                omp.givePetInventory();
            }
        }
    }

    @Override
    protected void giveLobbyItems(OMPlayer omp) {

    }
}
