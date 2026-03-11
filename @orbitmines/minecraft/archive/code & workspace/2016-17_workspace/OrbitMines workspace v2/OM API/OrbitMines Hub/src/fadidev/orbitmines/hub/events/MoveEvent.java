package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.ServerPortal;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MoveEvent implements Listener {

    private OrbitMinesHub hub;

    public MoveEvent(){
        this.hub = OrbitMinesHub.getHub();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);

        if(omp.isLoaded()){
            if(!omp.canChat()){
                omp.setCanChat(true);
            }

            if(p.getWorld().getName().equals(hub.getLobby().getName())){
                for(Block b : hub.getMindCraft().getBlocksForTurn().get(0)){
                    if(p.getLocation().getY() >= 43 && p.getLocation().distance(new Location(b.getWorld(), b.getLocation().getX() + 0.5, b.getLocation().getY(), b.getLocation().getZ() + 0.5)) <= 1.5){
                        p.teleport(hub.getMindCraft().getLocation());
                    }
                }

                {
                    Block b = p.getWorld().getBlockAt(p.getLocation());

                    for(ServerPortal portal : hub.getServerPortals()){
                        if(!portal.getBlocks().contains(b))
                            continue;

                        if(!omp.onCooldown(Cooldowns.PORTAL_USAGE)){
                            omp.toServer(portal.getServer());
                            omp.resetCooldown(Cooldowns.PORTAL_USAGE);
                        }

                        break;
                    }
                }
            }
        }
    }
}
