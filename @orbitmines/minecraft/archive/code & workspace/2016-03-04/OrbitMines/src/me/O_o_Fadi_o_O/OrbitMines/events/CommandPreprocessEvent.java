package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.managers.CommandManager.Command;
import me.O_o_Fadi_o_O.OrbitMines.utils.Language;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class CommandPreprocessEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPreCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
	    String[] a = e.getMessage().split(" ");
        Language la = omp.getLanguage();
	    
		if(omp.isLoaded()){
			if(ServerData.isServer(Server.CREATIVE) && omp.getCreativePlayer().isInPvPPlot() && !omp.isOpMode()){
				e.setCancelled(true);
				Command.dispatchInPvPPlot(omp, a);
			}
			else if(ServerData.isServer(Server.CREATIVE) && (a[0].equalsIgnoreCase("//set") || a[0].equalsIgnoreCase("//walls") || a[0].equalsIgnoreCase("//line") || a[0].equalsIgnoreCase("//replace"))){
				e.setCancelled(!Command.dispatchWECommand(omp, a));
			}
			else if(ServerData.isServer(Server.SURVIVAL) && omp.getCooldowns().containsKey(Cooldown.PVP_CONFIRM) && !omp.isOpMode()){
				e.setCancelled(true);
				
				if(a[0].equalsIgnoreCase("/confirm")){
					Command.dispatchConfirm(omp, a);
				}
				else{
                    Language.Message.WHILE_JOINING_PVP.send(la, p);
				}
			}
			else if(ServerData.isServer(Server.SURVIVAL) && omp.getSurvivalPlayer().isInPvP() && !omp.isOpMode()){
				e.setCancelled(true);
				
				if(a[0].equalsIgnoreCase("/spawn")){
					Command.dispatchSpawn(omp, a);
				}
				else{
                    Language.Message.WHILE_IN_PVP.send(la, p);
				}
			}
			else if(a[0].toLowerCase().startsWith("/mv") && !omp.isOpMode() || a[0].toLowerCase().startsWith("/bossbar")){
				e.setCancelled(true);
				omp.unknownCommand(a[0]);
			}
			else{
				boolean found = false;
				
				for(Command command : Command.values()){
					if(command.getName() != null && a[0].equalsIgnoreCase(command.getName())){
						e.setCancelled(true);
						found = true;
						command.dispatch(omp, a);
					}
				}
				
				if(!found){
					HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(a[0]);
					if(topic == null && !Command.isCommand(a[0]) || a[0].toLowerCase().equals("/me")){
						if(!a[0].toLowerCase().startsWith("/bukkit:")){
							e.setCancelled(true);
							omp.unknownCommand(a[0]);
						}
						else{
							e.setCancelled(true);
                            Language.Message.WHILE_IN_PVP.send(la, p, a[0].toLowerCase());
						}
					}
				}
			}
		}
		else{
			e.setCancelled(true);
			omp.notLoaded();
		}
	}
}
