package me.O_o_Fadi_o_O.OMHub.events;

import me.O_o_Fadi_o_O.OMHub.Start;
import me.O_o_Fadi_o_O.OMHub.managers.VoteManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteEvent implements Listener{
	
	Start hub = Start.getInstance();
	
	@EventHandler
	public void onVote(VotifierEvent e){
		Vote vote = e.getVote();
		
		VoteManager.registerVote(vote.getUsername());
	}
}
