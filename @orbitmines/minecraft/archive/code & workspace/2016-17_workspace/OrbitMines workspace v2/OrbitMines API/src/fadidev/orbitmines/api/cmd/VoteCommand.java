package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class VoteCommand extends Command {

	OrbitMinesAPI api;
	String[] alias = { "/vote" };
	
	public VoteCommand() {
		api = OrbitMinesAPI.getApi();
	}
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();

		p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
		p.sendMessage("");
        p.sendMessage(Messages.CMD_VOTE_FOR.get(omp));
        p.sendMessage(Messages.CMD_VOTE_SERVER.get(omp, api.getServerType().getName()));
		p.sendMessage("§b§lVote §8| §7");
		for(String s : api.getServerType().getVoteRewardMessages()){
			p.sendMessage("§b§lVote §8| §7  - " + s);
		}
		p.sendMessage("§b§lVote §8| §7");
        p.sendMessage(Messages.CMD_VOTE_AT.get(omp));
        p.sendMessage(Messages.CMD_VOTE_TOTAL.get(omp, omp.getVotes() + ""));
	}
}
