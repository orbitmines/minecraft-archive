package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Fadi on 19-9-2016.
 */
public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        if(omp.isLoaded()){
            if(!omp.isOpMode() && omp.isInPvP()){
                if(p.getAllowFlight()){
                    p.setFlying(false);
                    p.setAllowFlight(false);
                    p.sendMessage(Messages.CMD_TOGGLE_FLY.get(omp) + " (§c§lPvP Area§7)");
                }
            }

            if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
                Location l = omp.getTpLocation();
                if(p.getLocation().getX() == l.getX() && p.getLocation().getY() == l.getY() && p.getLocation().getZ() == l.getZ())
                    return;

                omp.removeCooldown(Cooldowns.TELEPORTING);

                if(omp.getTeleportingTo() == null) {
                    Title t = new Title("", PrisonMessages.CANCELLED_TELEPORTATION.get(omp, "§6Spawn"), 0, 40, 20);
                    t.send(p);
                }
                else{
                    Title t = new Title("", PrisonMessages.CANCELLED_TELEPORTATION.get(omp, "§cCell"), 0, 40, 20);
                    t.send(p);
                    omp.setTeleportingTo(null);
                }
            }
        }
    }
}
