package om.fog.events;

import om.api.API;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class CommandPreprocessEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPreCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
	    String[] a = e.getMessage().split(" ");
	    
		if(omp.isLoaded()){
			if(a[0].toLowerCase().startsWith("/mv") && !omp.isOpMode() || a[0].toLowerCase().startsWith("/bossbar")){
				e.setCancelled(true);
				omp.unknownCommand(a[0]);
			}
			else{
				boolean found = API.getInstance().dispatchCommands(omp, a);
				
				if(!found){
					HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(a[0]);
					if(topic == null || a[0].toLowerCase().equals("/me")){
						if(!a[0].toLowerCase().startsWith("/bukkit:")){
							e.setCancelled(true);
							omp.unknownCommand(a[0]);
						}
						else{
							e.setCancelled(true);
							p.sendMessage("§7You don't have permission to use §6" + a[0].toLowerCase() + "§7!");
						}
					}
				}
				else{
					e.setCancelled(true);
				}
			}
		}
		else{
			e.setCancelled(true);
			omp.notLoaded();
		}
	}
}
