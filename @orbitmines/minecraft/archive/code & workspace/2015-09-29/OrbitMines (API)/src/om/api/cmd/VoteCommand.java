package om.api.cmd;

import om.api.API;
import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class VoteCommand extends Command {

	API api;
	String[] alias = { "/vote" };
	
	public VoteCommand() {
		api = API.getInstance();
	}
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();

		p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		p.sendMessage("");
		p.sendMessage("§b§lVote §8| §7Vote for §b§lRewards§7!");
		p.sendMessage("§b§lVote §8| §7Reward in the " + api.server().getName() + "§7 Server:");
		p.sendMessage("§b§lVote §8| §7");
		for(String s : api.server().getVoteRewardMessages()){
			p.sendMessage("§b§lVote §8| §7  - " + s);
		}
		p.sendMessage("§b§lVote §8| §7");
		p.sendMessage("§b§lVote §8| §7Vote at §b§lwww.orbitmines.com");
		p.sendMessage("§b§lVote §8| §7Your total Votes this Month: §b§l" + omp.getVotes());
	}
}
