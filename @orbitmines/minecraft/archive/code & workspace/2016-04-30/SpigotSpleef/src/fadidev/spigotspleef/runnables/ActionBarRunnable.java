package fadidev.spigotspleef.runnables;

import fadidev.spigotspleef.SpigotSpleef;
import fadidev.spigotspleef.handlers.ActionBar;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ActionBarRunnable extends BukkitRunnable {

	private SpigotSpleef ss;

	public ActionBarRunnable() {
		this.ss = SpigotSpleef.getInstance();
	}
	
	@Override
	public void run(){
		List<ActionBar> actionBars = new ArrayList<>();
		for(ActionBar ab : ss.getCurrentActionbars().values()){
			actionBars.add(ab);
		}
		for(ActionBar ab : actionBars){
            ab.check();
		}
	}
}
