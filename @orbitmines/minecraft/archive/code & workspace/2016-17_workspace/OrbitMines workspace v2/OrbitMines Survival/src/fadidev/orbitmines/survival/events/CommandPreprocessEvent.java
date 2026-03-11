package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.survival.handlers.SurvivalCooldowns;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreprocessEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPreCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(p);
	    String[] a = e.getMessage().split(" ");

		if(omp.getCooldowns().containsKey(SurvivalCooldowns.PVP_CONFIRM)){
            if(!omp.isOpMode())
			    e.setCancelled(true);

			if(a[0].equalsIgnoreCase("/confirm")){
                omp.removeCooldown(SurvivalCooldowns.PVP_CONFIRM);
                omp.teleportToPvPArea();

                Title t = new Title("", SurvivalMessages.JOINED_PVP_AREA.get(omp), 0, 40, 20);
                t.send(p);
			}
			else{
				p.sendMessage(SurvivalMessages.CANNOT_PERFORM_COMMANDS_JOINING.get(omp));
			}
		}
		else if(omp.isInPvP()){
            if(!a[0].equalsIgnoreCase("/spawn")){
                if(!omp.isOpMode())
                    e.setCancelled(true);

                p.sendMessage(SurvivalMessages.CANNOT_PERFORM_COMMANDS.get(omp));
            }
        }
	}
}
