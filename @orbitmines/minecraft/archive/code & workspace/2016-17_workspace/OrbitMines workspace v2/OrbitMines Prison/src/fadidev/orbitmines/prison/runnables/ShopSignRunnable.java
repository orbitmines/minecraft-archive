package fadidev.orbitmines.prison.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.Shop;
import fadidev.orbitmines.prison.handlers.ShopSign;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class ShopSignRunnable extends OMRunnable {

    private OrbitMinesPrison prison;

    public ShopSignRunnable() {
        super(TimeUnit.SECOND, 1);

        prison = OrbitMinesPrison.getPrison();
    }

    @Override
    public void run() {
        for(Shop shop : prison.getShops()){
            shop.updateSign();
        }

        if(prison.getShopSigns().size() == 0)
            return;

        for(ShopSign sign : prison.getShopSigns()){
            sign.update();
        }
    }
}
