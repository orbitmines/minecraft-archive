package om.api.cmd;

import java.util.List;

import om.api.API;
import om.api.handlers.Command;
import om.api.handlers.StringInt;
import om.api.handlers.players.OMPlayer;

import org.bukkit.entity.Player;

public class TopVotersCommand extends Command {

	API api;
	String[] alias = { "/topvoters" };
	
	public TopVotersCommand() {
		api = API.getInstance();
	}
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		List<StringInt> voters = api.getPendingVoters();
		
		p.sendMessage("");
		p.sendMessage("§b§lTop 5 Voters§7:");
		sendTopVoterMessage(p, "§6§l1.§6", voters.get(0).getString(), voters.get(0).getInteger());
		sendTopVoterMessage(p, "§7§l2.§7", voters.get(1).getString(), voters.get(1).getInteger());
		sendTopVoterMessage(p, "§c§l3.§c", voters.get(2).getString(), voters.get(2).getInteger());
		sendTopVoterMessage(p, "§8§l4.§8", voters.get(3).getString(), voters.get(3).getInteger());
		sendTopVoterMessage(p, "§8§l5.§8", voters.get(4).getString(), voters.get(4).getInteger());
	}
	
	private void sendTopVoterMessage(Player p, String placement, String player, int votes){
		if(votes == 1){
			p.sendMessage("  " + placement + " " + player + " §7| §b" + votes + " Vote");
		}
		else{
			if(player == null){
				p.sendMessage("  " + placement + " ");
			}
			else{
				p.sendMessage("  " + placement + " " + player + " §7| §b" + votes + " Votes");
			}
		}
	}
}
