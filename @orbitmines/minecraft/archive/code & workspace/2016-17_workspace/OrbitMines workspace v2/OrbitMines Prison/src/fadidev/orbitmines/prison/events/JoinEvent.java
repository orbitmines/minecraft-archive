package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.handlers.PrisonPlayer;
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
        PrisonPlayer omp = new PrisonPlayer(p, false);

        e.setJoinMessage(null);

        omp.load();
    }
}
