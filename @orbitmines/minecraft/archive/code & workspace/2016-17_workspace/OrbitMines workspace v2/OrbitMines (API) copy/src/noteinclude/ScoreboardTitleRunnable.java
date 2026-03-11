package noteinclude;

import addtohub.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public class ScoreboardTitleRunnable extends OMRunnable {

	private API api;
	
	public ScoreboardTitleRunnable(Duration duration) {
		super(duration);
	
		this.api = API.getInstance();
	}

	@Override//-> index in ScoreboardSet instance - instead of ScoreboardManager
	protected void run() {
		api.getScoreboardManager().nextTitle();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getScoreboard() != null && p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null){
				p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(api.getScoreboardManager().getTitle());
			}
		}
	}
}
