package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesCooldowns;
import fadidev.orbitmines.minigames.handlers.data.ChickenFightData;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

/**
 * Created by Fadi on 1-10-2016.
 */
public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(omp.isLoaded()){
            if(arena == null || arena.getType() != MiniGameType.CHICKEN_FIGHT)
                return;

            if(arena.getState() == State.IN_GAME){
                ChickenFightData data = (ChickenFightData) arena.getData();

                if(!arena.isSpectator(omp)){
                    if(omp.getChickenFightPlayer().getKitSelected().getName().equals(TicketType.CHICKEN_KIT.toString())){
                        data.setChickenBlock(omp);
                    }

                    if(!p.getAllowFlight()){
                        Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

                        if(!b.isEmpty() && b.getType() != Material.AIR){
                            p.setAllowFlight(true);
                        }
                    }
                }

                MiniGamesPlayer ompD = data.getChickenBlockPlayer(p.getLocation().getBlock().getRelative(BlockFace.DOWN));
                if(ompD != null && omp != ompD && !arena.isSpectator(ompD) && !omp.onCooldown(MiniGamesCooldowns.WOOL_TRAIL)){
                    p.damage(Arrays.asList(0.5D, 1D).get(Utils.RANDOM.nextInt(2)), ompD.getPlayer());
                    omp.addPotionEffect(PotionEffectType.SLOW, 60, 2);

                    omp.resetCooldown(MiniGamesCooldowns.WOOL_TRAIL);
                }
            }
        }
    }
}
