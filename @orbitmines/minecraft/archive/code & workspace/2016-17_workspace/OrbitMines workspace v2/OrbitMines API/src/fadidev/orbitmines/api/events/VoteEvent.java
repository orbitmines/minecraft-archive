package fadidev.orbitmines.api.events;

import com.vexsoftware.votifier.model.VotifierEvent;
import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VoteEvent implements Listener {

	private OrbitMinesAPI api;

	public VoteEvent(){
		api = OrbitMinesAPI.getApi();
	}
	
	@EventHandler
	public void onVote(VotifierEvent e){
		if(e.getVote().getUsername().equalsIgnoreCase("O_o_Fadi_o_O"))
			return;

		api.getVoteManager().registerVote(e.getVote().getUsername());
	}
}
