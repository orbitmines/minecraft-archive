package me.O_o_Fadi_o_O.Hub.events;

import me.O_o_Fadi_o_O.Hub.Hub;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener{
	Hub plugin;
	 
	public PlayerChat(Hub instance) {
	plugin = instance;
	}
	
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
			
		String s = e.getMessage();
		e.setMessage("§" + Hub.chatcolor.get(p) + s);
	}
}
