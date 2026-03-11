package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.DoubleBoolean;
import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class CommandPreprocessEvent implements Listener {

	private OrbitMinesAPI api;

    public CommandPreprocessEvent(){
        this.api = OrbitMinesAPI.getApi();
    }

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPreCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
	    String[] a = e.getMessage().split(" ");
	    
		if(omp.isLoaded()){
		    /* Cancelled by OrbitMines plugins -> to block commands */
		    if(e.isCancelled())
		        return;

			if(a[0].toLowerCase().startsWith("/mv") && !omp.isOpMode()){
				e.setCancelled(true);
				omp.unknownCommand(a[0]);
			}
			else{
				DoubleBoolean found = api.dispatchCommand(omp, a);
				
				if(found == null){
					HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(a[0]);
					if(topic == null || a[0].toLowerCase().equals("/me")){
						e.setCancelled(true);
						omp.unknownCommand(a[0]);
					}
				}
				else{
					if(found.isB2())
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
