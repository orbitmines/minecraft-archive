package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.runnables.OMRunnable;

public abstract class PlayerRunnable extends OMRunnable {

	private OrbitMinesAPI api;

	public PlayerRunnable() {
		super(TimeUnit.TICK, 5);

        api = OrbitMinesAPI.getApi();
	}

	protected abstract void run(OMPlayer omp);

	@Override
	public void run() {
		for(OMPlayer omp : api.getOMPlayers()){
			omp.updateScoreboard();
			omp.checkLastLocation();
			
			run(omp);
		}
	}
}
