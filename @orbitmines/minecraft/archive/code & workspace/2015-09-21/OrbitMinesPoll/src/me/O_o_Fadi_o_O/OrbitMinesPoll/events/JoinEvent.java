package me.O_o_Fadi_o_O.OrbitMinesPoll.events;

import me.O_o_Fadi_o_O.OrbitMinesPoll.Poll;
import me.O_o_Fadi_o_O.OrbitMinesPoll.Start;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Player p = e.getPlayer();
		final Poll poll = Start.getInstance().getPoll();
		
		if(!poll.hasVoted(p.getUniqueId())){
			new BukkitRunnable(){
				public void run(){
					poll.sendMessage(p);
				}
			}.runTaskLater(Start.getInstance(), 40 * 2);
		}
	}
}
