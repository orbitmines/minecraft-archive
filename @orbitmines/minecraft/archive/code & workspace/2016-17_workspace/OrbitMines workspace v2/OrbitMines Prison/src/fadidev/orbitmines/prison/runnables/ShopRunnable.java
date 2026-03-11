package fadidev.orbitmines.prison.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.Shop;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class ShopRunnable extends OMRunnable {

    private OrbitMinesPrison prison;

    public ShopRunnable() {
        super(TimeUnit.MINUTE, 1);

        prison = OrbitMinesPrison.getPrison();
    }

    @Override
    public void run() {
        for(Shop shop : prison.getShops()){
            if(!shop.canRent() && shop.expired())
                shop.expire();
        }
    }
}
