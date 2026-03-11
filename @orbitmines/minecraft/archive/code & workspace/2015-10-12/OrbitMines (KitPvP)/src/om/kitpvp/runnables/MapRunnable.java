package om.kitpvp.runnables;

import om.api.runnables.OMRunnable;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.KitPvPMap;
import om.kitpvp.handlers.players.KitPvPPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MapRunnable extends OMRunnable {

	private KitPvP kitpvp;
	
	public MapRunnable(Duration duration) {
		super(duration);
		
		this.kitpvp = KitPvP.getInstance();
	}

	@Override
	protected void run() {
		KitPvPMap map = kitpvp.getCurrentMap();
		map.tickTimer();
		
		kitpvp.getTeleporterInv().update();
		
		if(map.getSeconds() <= 10 && map.getSeconds() != 0 && map.getMinutes() == 0){
			Bukkit.broadcastMessage("§7Switching Maps in §6§l" + map.getSeconds() + "§7...");
			
			for(Player p : Bukkit.getOnlinePlayers()){
				p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
			}
		}
		else if(map.getSeconds() == 0 && map.getMinutes() == 0){
			kitpvp.setNextMap();
			
			for(Player p : Bukkit.getOnlinePlayers()){
				KitPvPPlayer kp = KitPvPPlayer.getKitPvPPlayer(p);
				
				if(kp.isSpectator() || kp.getKitSelected() != null){
					kp.teleportToMap();
				}
				p.sendMessage("§7§lMaps Switched!");
			}
		}
		else{}
	}
}
