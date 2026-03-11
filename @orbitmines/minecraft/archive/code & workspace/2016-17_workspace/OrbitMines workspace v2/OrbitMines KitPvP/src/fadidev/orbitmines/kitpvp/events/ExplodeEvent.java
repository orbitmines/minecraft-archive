package fadidev.orbitmines.kitpvp.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplodeEvent implements Listener {

	@EventHandler
	public void EntityExplodeEvent(EntityExplodeEvent e){
		e.blockList().clear();
	}
}
