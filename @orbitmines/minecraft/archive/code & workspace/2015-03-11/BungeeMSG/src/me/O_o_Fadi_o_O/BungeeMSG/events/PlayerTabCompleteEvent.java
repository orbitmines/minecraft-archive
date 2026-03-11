package me.O_o_Fadi_o_O.BungeeMSG.events;

import me.O_o_Fadi_o_O.BungeeMSG.managers.StorageManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerTabCompleteEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTab(TabCompleteEvent  e){
        if(!e.getSuggestions().isEmpty()){
            return;
        }
        String[] a = e.getCursor().split(" ");

        if(StorageManager.messagecommand.contains(a[0].toLowerCase()) || StorageManager.replycommand.contains(a[0].toLowerCase()) || StorageManager.togglecommand.contains(a[0].toLowerCase()) || StorageManager.mutecommand.contains(a[0].toLowerCase()) || StorageManager.globalcommand.contains(a[0].toLowerCase())){
	        if(a.length > 1){
	        	final String checked = (a.length > 0 ? a[a.length - 1] : e.getCursor()).toLowerCase();
		        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
		            if(player.getName().toLowerCase().startsWith(checked)){
		                e.getSuggestions().add(player.getName());
		            }
		        }
	        }
	        else{
	        	if(e.getCursor().substring(a[0].length()).startsWith(" ")){
	        		for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
		        		e.getSuggestions().add(player.getName());
		        	}
	        	}
	        }
        }
	}
}
