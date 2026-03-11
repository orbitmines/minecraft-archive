package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.data.SkywarsData;
import fadidev.orbitmines.minigames.handlers.data.UHCData;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Fadi on 1-10-2016.
 */
public class PlaceEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(omp.isLoaded()){
            if(omp.isOpMode() || arena == null)
                return;

            if(arena.isSpectator(omp)){
                e.setCancelled(true);
            }
            else{
                switch(arena.getType()){
                    case SURVIVAL_GAMES:
                        if(arena.getState() == State.IN_GAME){
                            Block b = e.getBlockPlaced();
                            Material m = b.getType();

                            if(m == Material.TNT){
                                b.setType(Material.AIR);
                                TNTPrimed tnt = (TNTPrimed) b.getWorld().spawnEntity(b.getLocation().add(0, 1, 0), EntityType.PRIMED_TNT);
                                tnt.setFuseTicks((int) (20 * 1.5));
                            }
                            else{
                                if(m != Material.CAKE_BLOCK && m != Material.FIRE){
                                    p.damage(3D);
                                    p.sendMessage(MiniGamesMessages.BLOCK_GLITCH.get(omp));
                                    e.setCancelled(true);
                                }
                            }
                        }
                        else{
                            e.setCancelled(true);
                        }
                         break;
                    case ULTRA_HARD_CORE:
                        if(arena.getState() != State.IN_GAME){
                            e.setCancelled(true);
                        }
                        else{
                            Block b = e.getBlockPlaced();

                            if(b.getType() == Material.IRON_ORE){
                                UHCData data = (UHCData) arena.getData();
                                if(!data.getIronOresPlaced().contains(b)){
                                    data.getIronOresPlaced().add(b);
                                }
                            }
                        }
                        break;
                    case SKYWARS:
                        if(arena.getState() != State.IN_GAME){
                            e.setCancelled(true);
                        }
                        else{
                            Block b = e.getBlockPlaced();

                            if(b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST){
                                ((SkywarsData) arena.getData()).getPlacedChests().add(b);
                            }
                        }
                        break;
                    default:
                        e.setCancelled(true);
                        break;
                }
            }
        }
        else{
            e.setCancelled(true);
            omp.updateInventory();
        }
    }
}
