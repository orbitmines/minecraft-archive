package me.O_o_Fadi_o_O.OrbitMines.events;

import om.api.managers.VoteManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteEvent implements Listener{
	
	@EventHandler
	public void onVote(VotifierEvent e){
		new VoteManager().registerVote(e.getVote().getUsername());
	}
}
