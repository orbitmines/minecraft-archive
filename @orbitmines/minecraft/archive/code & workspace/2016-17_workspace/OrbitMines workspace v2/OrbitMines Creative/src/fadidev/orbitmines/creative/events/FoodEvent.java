package fadidev.orbitmines.creative.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class FoodEvent implements Listener {

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e){
        e.setFoodLevel(20);
    }
}
