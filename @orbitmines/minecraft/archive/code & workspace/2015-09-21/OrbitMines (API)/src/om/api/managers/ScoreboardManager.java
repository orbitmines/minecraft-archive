package om.api.managers;

import org.bukkit.entity.Player;

public abstract class ScoreboardManager {

	private int titleIndex;
	
	public ScoreboardManager(){
		titleIndex = 0;
	}
	
	public int getTitleIndex(){
		return titleIndex;
	}
	
	public void setTitleIndex(int titleIndex){
		this.titleIndex = titleIndex;
	}
	
	abstract void requestUpdate(Player p);
	abstract void nextTitle();
	
}
