package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

/**
 * Created by Fadi on 1-10-2016.
 */
public class ToggleFlightEvent implements Listener {

    @EventHandler
    public void onToggle(PlayerToggleFlightEvent e){
        Player p = e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(arena != null && arena.getType() == MiniGameType.CHICKEN_FIGHT && p.getGameMode() != GameMode.CREATIVE){
            if(!omp.onCooldown(Cooldowns.NPC_INTERACT)){
                p.setFlying(false);
                p.setVelocity(p.getLocation().getDirection().multiply(1.15).setY(0.5));
                p.setAllowFlight(false);
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 1);

                omp.resetCooldown(Cooldowns.NPC_INTERACT);
            }
            else{
                p.setFlying(false);
                p.setAllowFlight(false);
                e.setCancelled(true);
            }
        }
    }
}
