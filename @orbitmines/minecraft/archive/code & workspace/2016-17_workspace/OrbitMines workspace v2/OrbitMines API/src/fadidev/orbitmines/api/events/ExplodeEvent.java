package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.util.Vector;

public class ExplodeEvent implements Listener {

	private OrbitMinesAPI api;

    public ExplodeEvent(){
        this.api = OrbitMinesAPI.getApi();
    }


	@EventHandler
    public void EntityExplodeEvent(ExplosionPrimeEvent e){
		e.setFire(false);

		if(!(e.getEntity() instanceof Creeper) || !api.getGadgets().getCreeperLaunched().contains(e.getEntity()))
		    return;

        if(api.getGadgets().getCreeperLaunched().contains(e.getEntity())){
            for(Entity en : e.getEntity().getNearbyEntities(3, 3, 3)){
                if(!(en instanceof Player))
                    continue;

                Player p = (Player) en;
                OMPlayer omp = OMPlayer.getOMPlayer(p);

                if(omp.canReceiveVelocity()){
                    Vector v = p.getLocation().getDirection();
                    p.setVelocity(v.multiply(-1).add(new Vector(0, 1.3, 0)));
                }
            }
        }
	}
	
	@EventHandler
	public void EntityExplodeEvent(EntityExplodeEvent e){
        if(e.getEntity() instanceof Creeper)
            e.blockList().clear();
	}
}
