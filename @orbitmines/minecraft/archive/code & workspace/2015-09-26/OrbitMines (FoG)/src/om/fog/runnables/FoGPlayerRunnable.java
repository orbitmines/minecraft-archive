package om.fog.runnables;

import om.api.runnables.PlayerRunnable;
import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;

public class FoGPlayerRunnable extends PlayerRunnable {

	private FoG fog;
	
	public FoGPlayerRunnable(Duration duration) {
		super(duration);

		this.fog = FoG.getInstance();
	}

	@Override
	protected void run(Player p) {
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		
	}
}
