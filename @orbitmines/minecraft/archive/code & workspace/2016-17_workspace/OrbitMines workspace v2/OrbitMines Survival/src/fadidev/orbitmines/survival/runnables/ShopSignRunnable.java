package fadidev.orbitmines.survival.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.ShopSign;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class ShopSignRunnable extends OMRunnable {

    private OrbitMinesSurvival survival;

    public ShopSignRunnable() {
        super(TimeUnit.SECOND, 1);

        survival = OrbitMinesSurvival.getSurvival();
    }

    @Override
    public void run() {
        if(survival.getShopSigns().size() == 0)
            return;

        for(ShopSign sign : survival.getShopSigns()){
            sign.update();
        }
    }
}
