package om.fog.events;

import om.fog.FoG;
import om.fog.handlers.map.FoGMap;
import om.fog.handlers.map.SaveZone;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class TargetEvent implements Listener {

	private FoG fog;
	
	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent e){
		this.fog = FoG.getInstance();
		Entity en = e.getEntity();
		Location l = en.getLocation();
		
		if(e.getTarget() instanceof Player && l.getWorld().getName().equals(fog.getGalaxyWorld().getName())){
			Player pT = (Player) e.getTarget();
			FoGPlayer ompT = FoGPlayer.getFoGPlayer(pT);

			for(FoGMap map : fog.getMaps()){
				if(map.inMap(l)){
					for(SaveZone sz : map.getSaveZones()){
						if(sz.inSaveZone(pT.getLocation())){
							if(sz.getPlayers().contains(ompT)){
								e.setCancelled(true);
							}
							break;
						}
					}
					break;
				}
			}
		}	
	}
}
