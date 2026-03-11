package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.data.UHCData;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by Fadi on 1-10-2016.
 */
public class BreakEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e){
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
                            Material m = e.getBlock().getType();

                            if(m != Material.LEAVES && m != Material.LEAVES_2 && m != Material.LONG_GRASS && m != Material.DEAD_BUSH && m != Material.YELLOW_FLOWER && m != Material.RED_ROSE && m != Material.BROWN_MUSHROOM && m != Material.RED_MUSHROOM && m != Material.VINE && m != Material.DOUBLE_PLANT && m != Material.POTATO && m != Material.CARROT && m != Material.CROPS && m != Material.TORCH && m != Material.REDSTONE_TORCH_ON) {

                                if(m != Material.TORCH && m != Material.REDSTONE_TORCH_ON && m != Material.REDSTONE_TORCH_OFF){
                                    p.damage(3D);
                                    p.sendMessage(MiniGamesMessages.BLOCK_GLITCH.get(omp));
                                }

                                e.setCancelled(true);
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
                            Block b = e.getBlock();

                            if(b.getType() == Material.IRON_ORE){
                                UHCData data = (UHCData) arena.getData();
                                if((data.isDoubleIron() || omp.getUhcPlayer().isDoubleIron()) && !data.getIronOresPlaced().contains(b) && new Random().nextBoolean()){
                                    b.getWorld().dropItem(b.getLocation().add(0.5, 0, 0.5), new ItemStack(Material.IRON_ORE));
                                }
                            }
                            else if(b.getType() == Material.LAPIS_ORE){
                                if(omp.getUhcPlayer().isBlueGold() && new Random().nextBoolean()){
                                    b.getWorld().dropItem(b.getLocation().add(0.5, 0, 0.5), new ItemStack(Material.GOLD_ORE));
                                }
                            }
                            else if(b.getType() == Material.GLOWING_REDSTONE_ORE){
                                if(omp.getUhcPlayer().isRedGold() && new Random().nextBoolean()){
                                    b.getWorld().dropItem(b.getLocation().add(0.5, 0, 0.5), new ItemStack(Material.GOLD_ORE));
                                }
                            }
                        }
                        break;
                    case SKYWARS:
                        if(arena.getState() != State.IN_GAME)
                            e.setCancelled(true);

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
