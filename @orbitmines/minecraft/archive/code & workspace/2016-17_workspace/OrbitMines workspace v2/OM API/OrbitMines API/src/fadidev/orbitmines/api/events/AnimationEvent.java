package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;

public class AnimationEvent implements Listener {

	private OrbitMinesAPI api;

    public AnimationEvent(){
        this.api = OrbitMinesAPI.getApi();
    }

	@EventHandler
	public void onAnimation(PlayerAnimationEvent e){
		Player p = e.getPlayer();

		for(Entity en : p.getNearbyEntities(0.7, 0.7, 0.7)){
			if(en instanceof MagmaCube && api.getGadgets().getSoccerMagmaCubes().contains(en))
				en.setVelocity(p.getLocation().getDirection().multiply(1.2));
		}
	}
}
