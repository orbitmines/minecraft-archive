package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * Created by Fadi on 1-10-2016.
 */
public class ProjHitEvent implements Listener {

    @EventHandler
    public void onProjHit(ProjectileHitEvent e){
        if(!(e.getEntity() instanceof Egg) || !(e.getEntity().getShooter() instanceof Player))
            return;

        Egg egg = (Egg) e.getEntity();
        Player p = (Player) egg.getShooter();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(arena != null && arena.getType() == MiniGameType.CHICKEN_FIGHT && arena.getState() == State.IN_GAME){
            egg.getWorld().createExplosion(egg.getLocation().getX(), egg.getLocation().getY(), egg.getLocation().getZ(), 1.1F, false, false);
            egg.remove();
        }
    }
}
