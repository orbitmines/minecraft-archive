package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Fadi on 10-9-2016.
 */
public class JoinEvent implements Listener {

    private OrbitMinesSkyBlock skyBlock;

    public JoinEvent(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        final SkyBlockPlayer omp = new SkyBlockPlayer(p, false);

        e.setJoinMessage(null);

        omp.load();

        new BukkitRunnable(){
            public void run(){
                if(!omp.hasIsland()){
                    p.teleport(skyBlock.getSpawn());
                }
                else{
                    if(omp.isOwner())
                        omp.getIsland().setMaxMembers(omp.getMaxMembers());
                }
            }
        }.runTaskLater(skyBlock, 5);
    }
}
