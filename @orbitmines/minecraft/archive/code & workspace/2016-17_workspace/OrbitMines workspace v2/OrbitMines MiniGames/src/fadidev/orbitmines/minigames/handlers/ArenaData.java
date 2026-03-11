package fadidev.orbitmines.minigames.handlers;

import fadidev.orbitmines.minigames.handlers.players.ArenaPlayer;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.State;

/**
 * Created by Fadi on 30-9-2016.
 */
public abstract class ArenaData {

    protected Arena arena;

    public ArenaData(Arena arena){
        this.arena = arena;
    }

    public abstract void restart();
    public abstract void ending();
    public abstract void start();
    public abstract void warmup();
    public abstract void waiting();
    public abstract void rewardPlayers();
    public abstract void teleportToArena();
    public abstract void leave(MiniGamesPlayer omp);
    public abstract void leaveSpectator(MiniGamesPlayer omp);
    public abstract void starting(MiniGamesPlayer omp);
    public abstract void run(MiniGamesPlayer omp);
    public abstract void run(State state);
    public abstract void updateScoreboard(MiniGamesScoreboard scoreboard);
    public abstract ArenaPlayer getArenaPlayer(MiniGamesPlayer omp);
    public abstract void spawnNpcs();

    public Arena getArena() {
        return arena;
    }
}
