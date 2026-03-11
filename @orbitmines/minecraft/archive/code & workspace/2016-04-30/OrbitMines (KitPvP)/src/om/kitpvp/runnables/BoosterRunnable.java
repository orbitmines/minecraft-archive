package om.kitpvp.runnables;

import om.api.runnables.OMRunnable;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.ActiveBooster;

import org.bukkit.Bukkit;

public class BoosterRunnable extends OMRunnable {

	private KitPvP kitpvp;
	
	public BoosterRunnable(Duration duration) {
		super(duration);
		
		this.kitpvp = KitPvP.getInstance();
	}

	@Override
	protected void run() {
		ActiveBooster booster = kitpvp.getBooster();
		if(booster != null){
			booster.tickTimer();
			
			if(booster.getSeconds() == 0){
				if(booster.getMinutes() != 0){
					Bukkit.broadcastMessage("§a" + booster.getPlayer() + "'s Booster§7 (§ax" + booster.getBooster().getMultiplier() +"§7) remains for §a" + booster.getMinutes() + "m " + booster.getSeconds() + "s§7.");
				}
				else{
					Bukkit.broadcastMessage("§a" + booster.getPlayer() + "'s Booster§7 (§ax" + booster.getBooster().getMultiplier() +"§7) has been expired.");
					kitpvp.setBooster(null);
				}
			}
		}
	}
}
