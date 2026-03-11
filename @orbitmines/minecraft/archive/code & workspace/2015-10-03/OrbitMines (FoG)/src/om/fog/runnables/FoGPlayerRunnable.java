package om.fog.runnables;

import om.api.runnables.PlayerRunnable;
import om.fog.FoG;
import om.fog.handlers.FoGMap;
import om.fog.handlers.SpawnArea;
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
		
		for(FoGMap map : fog.getMaps()){
			map.setPlayers(0);
		}
		
		omp.tickCombatTimer();
		omp.updateShieldBar();
		omp.updateMap();
		
		for(SpawnArea area : fog.getSpawnAreas()){
			area.checkSpawns();
		}
	}
}
