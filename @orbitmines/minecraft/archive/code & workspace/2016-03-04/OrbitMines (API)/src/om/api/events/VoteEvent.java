package om.api.events;

import om.api.API;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteEvent implements Listener{
	
	@EventHandler
	public void onVote(VotifierEvent e){
		API.getInstance().getVoteManager().registerVote(e.getVote().getUsername());
	}
}
