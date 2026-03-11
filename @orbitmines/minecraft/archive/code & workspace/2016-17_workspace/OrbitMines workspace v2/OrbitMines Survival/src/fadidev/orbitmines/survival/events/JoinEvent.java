package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        SurvivalPlayer omp = new SurvivalPlayer(p, false);

        e.setJoinMessage(null);

        omp.load();
    }
}
