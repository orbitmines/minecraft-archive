package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Fadi on 10-9-2016.
 */
public class JoinEvent implements Listener {

    private OrbitMinesKitPvP kitPvP;

    public JoinEvent(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        KitPvPPlayer kitPvPPlayer = new KitPvPPlayer(p, false);

        e.setJoinMessage(null);

        kitPvPPlayer.load();

        new BukkitRunnable(){
            public void run(){
                p.teleport(kitPvP.getSpawn());
            }
        }.runTaskLater(kitPvP, 5);
    }
}
