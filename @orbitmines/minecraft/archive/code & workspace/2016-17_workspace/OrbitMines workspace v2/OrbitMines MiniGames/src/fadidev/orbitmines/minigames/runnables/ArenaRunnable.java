package fadidev.orbitmines.minigames.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.ArenaData;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.data.GhostAttackData;
import fadidev.orbitmines.minigames.handlers.data.SurvivalGamesData;
import fadidev.orbitmines.minigames.handlers.data.UHCData;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ArenaRunnable extends OMRunnable {

    private OrbitMinesMiniGames miniGames;

    public ArenaRunnable() {
        super(TimeUnit.SECOND, 1);

        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @Override
    public void run() {
        for(Arena arena : miniGames.getArenas()){
            arena.sendData();

            final ArenaData data = arena.getData();

            if(arena.getState() == State.WAITING && arena.getPlayers().size() > 2 || arena.getState() != State.WAITING && arena.getState() != State.RESTARTING){
                arena.tickTimer();
            }

            if(arena.getState() == State.WAITING || arena.getState() == State.STARTING){
                if(arena.getPlayers().size() < 2){
                    if(arena.getMinutes() == 0 && arena.getSeconds() <= 30){
                        arena.setSeconds(30);
                    }
                }

                if(arena.getMinutes() == 0){
                    if(arena.getSeconds() == 0){
                        data.warmup();
                    }
                    else if(arena.getSeconds() <= 10){
                        if(arena.getSeconds() == 10){
                            arena.starting();
                        }

                        arena.playSound(Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                        arena.sendMessage(MiniGamesMessages.STARTING_IN, arena.getSeconds() + "");
                    }
                }

                data.run(arena.getState());
            }
            else if(arena.getState() == State.WARMUP){
                if(arena.getMinutes() == 0 && arena.getSeconds() == 0){
                    data.start();
                }
                else{
                    data.run(State.WARMUP);
                }

                if(arena.getMinutes() == 0 && arena.getSeconds() <= 10){
                    arena.updateWarmup();
                }
            }
            else if(arena.getState() == State.IN_GAME){
                if(arena.getPlayers().size() <= 1 || arena.getType() == MiniGameType.GHOST_ATTACK && ((GhostAttackData) data).isGhostDead()){
                    data.ending();
                }

                arena.getTeleporterInv().update();
                data.run(State.IN_GAME);

                if(arena.getMinutes() == 0 && arena.getSeconds() == 0){
                    switch(arena.getType()){
                        case SURVIVAL_GAMES:
                            SurvivalGamesData sgData = (SurvivalGamesData) data;
                            if(!sgData.isInDeathMatch())
                                sgData.deathmatch();
                            else
                                sgData.ending();
                            break;
                        case ULTRA_HARD_CORE:
                            UHCData uhcData = (UHCData) data;
                            if(!uhcData.isInPvP())
                                uhcData.pvp();
                            else
                                uhcData.ending();
                            break;
                        default:
                            data.ending();
                    }
                }
            }
            else if(arena.getState() == State.ENDING){
                if(arena.getSeconds() == 10){
                    data.rewardPlayers();
                }
                else if(arena.getSeconds() == 0){
                    for(MiniGamesPlayer omp : arena.getAllPlayers()){
                        arena.leave(omp);
                    }

                    new BukkitRunnable(){
                        public void run(){
                            data.restart();
                        }
                    }.runTaskLater(miniGames, 500);
                }
                else if(arena.getSeconds() <= 5){
                    arena.playSound(Sound.UI_BUTTON_CLICK, 5, 1);
                    arena.sendMessage(MiniGamesMessages.RESTARTING_IN, arena.getSeconds() + "");
                }

                data.run(State.ENDING);
            }
        }
    }
}
