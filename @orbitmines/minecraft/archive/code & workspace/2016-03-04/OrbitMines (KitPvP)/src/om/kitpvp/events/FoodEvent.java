package om.kitpvp.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodEvent implements Listener {
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e){
		e.setFoodLevel(20);
	}
}
