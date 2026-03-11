package fadidev.orbitmines.hub.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.MiniGameArena;
import fadidev.orbitmines.hub.utils.enums.MiniGameType;
import fadidev.orbitmines.hub.utils.enums.State;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MiniGameRunnable extends OMRunnable {

    private OrbitMinesHub hub;

    public MiniGameRunnable() {
        super(TimeUnit.SECOND, 1);

        hub = OrbitMinesHub.getHub();
    }

    @Override
    public void run() {
        HashMap<MiniGameType, List<MiniGameArena>> arenas = new HashMap<>();

        for(MiniGameArena arena : hub.getMiniGameArenas()){
            if(!arenas.containsKey(arena.getType()))
                arenas.put(arena.getType(), new ArrayList<MiniGameArena>());
            arenas.get(arena.getType()).add(arena);

            if(arena.getState() == State.ENDING || arena.getState() == State.RESTARTING){
                arena.setSecondsRestarting(arena.getSecondsRestarting() +1);

                if(arena.getSecondsRestarting() == 15){
                    arena.setSecondsRestarting(0);
                    arena.setLastResponse(0);
                    arena.setPlayers(0);
                    arena.setState(State.WAITING);
                    arena.setMinutes(0);
                    arena.setSeconds(0);
                }
            }
            else{
                arena.setLastResponse(arena.getLastResponse() +1);

                if(arena.getState() != State.CLOSED && arena.getLastResponse() == 3){
                    arena.setLastResponse(0);
                    arena.setPlayers(0);
                    arena.setState(State.WAITING);
                    arena.setMinutes(0);
                    arena.setSeconds(0);
                }
            }

            arena.updateSign();
        }

        for(MiniGameType type : arenas.keySet()){
            Location l = hub.getMiniGameSigns().get(type);
            int lobbies = 0;
            int players = 0;

            for(MiniGameArena arena : arenas.get(type)){
                if(arena.getState() != State.CLOSED)
                    lobbies++;
                players += arena.getPlayers();
            }

            String[] lines = new String[4];
            lines[0] = "§l" + type.getName();
            lines[1] = "";
            lines[2] = "Lobbies: " + lobbies;
            lines[3] = "Players: " + players;

            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.getWorld().getName().equals(l.getWorld().getName()) && p.getLocation().distance(l) <= 16)
                    p.sendSignChange(l, lines);
            }
        }
    }
}
