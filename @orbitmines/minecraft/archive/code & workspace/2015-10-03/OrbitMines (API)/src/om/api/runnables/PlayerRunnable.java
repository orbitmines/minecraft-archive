package om.api.runnables;

import om.api.handlers.players.OMPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class PlayerRunnable extends OMRunnable {

	public PlayerRunnable(Duration duration) {
		super(duration);
	}
	
	protected abstract void run(Player p);

	@Override
	protected void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			OMPlayer omp = OMPlayer.getOMPlayer(p);
			omp.setScoreboard();
			omp.checkLastLocation();
			
			run(p);
		}
	}
}
