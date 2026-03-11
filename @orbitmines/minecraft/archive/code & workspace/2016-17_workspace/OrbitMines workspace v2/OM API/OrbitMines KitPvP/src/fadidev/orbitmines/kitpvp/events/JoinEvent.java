package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
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
        KitPvPPlayer kitPvPPlayer = new KitPvPPlayer(p, false);

        e.setJoinMessage(null);

        kitPvPPlayer.load();
    }
}
