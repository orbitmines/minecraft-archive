package om.fog.runnables;

import om.api.runnables.PlayerRunnable;
import om.fog.FoG;
import om.fog.handlers.map.FoGMap;
import om.fog.handlers.map.SaveZone;
import om.fog.handlers.map.SpawnArea;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Suit;

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
			
			for(SaveZone sz : map.getSaveZones()){
				sz.getPlayers().clear();
			}
		}
		
		omp.tickCombatTimer();
		omp.updateMap();
		
		if(omp.isMining()){
			omp.tickMiningTimer();
			
			if(!omp.isMining()){
				omp.updateMining();
			}
		}
		else{
			omp.updateMining();
		}
		
		omp.updateShield();
		omp.updateActionBar();
		
		if(omp.isInTutorial() && omp.getTutorial().getStage() == 2){
			if(p.getWorld().getName().equals(fog.getGalaxyWorld().getName()) && p.getLocation().distance(omp.getFaction().getSuitNPCs().get(Suit.TRAINING_SUIT).getLocation()) <= 11){
				omp.getTutorial().toNextStage();
			}
		}
		
		for(SpawnArea area : fog.getSpawnAreas()){
			area.checkSpawns();
		}
	}
}
