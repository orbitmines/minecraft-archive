package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreprocessEvent implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPreCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		CreativePlayer omp = CreativePlayer.getCreativePlayer(p);
	    String[] a = e.getMessage().split(" ");

		if(omp.isInPvPPlot()){
			if(omp.hasPerms(StaffRank.OWNER))
				return;

			e.setCancelled(true);

			if(a.length > 1 && a[1].equalsIgnoreCase("leave")){
				omp.resetCooldown(Cooldowns.TELEPORTING);
				p.sendMessage(CreativeMessages.CMD_PLOT_LEAVING.get(omp));
			}
			else{
				p.sendMessage(CreativeMessages.CMD_PLOT_ON_PVP_PLOT.get(omp));
			}
		}
	}
}
