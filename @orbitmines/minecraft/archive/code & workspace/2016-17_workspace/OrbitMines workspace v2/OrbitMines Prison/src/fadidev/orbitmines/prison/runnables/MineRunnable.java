package fadidev.orbitmines.prison.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.Mine;
import fadidev.orbitmines.prison.utils.enums.MineType;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class MineRunnable extends OMRunnable {

    private OrbitMinesPrison prison;

    public MineRunnable() {
        super(TimeUnit.SECOND, 1);

        prison = OrbitMinesPrison.getPrison();
    }

    @Override
    public void run() {
        for(Mine mine : prison.getMines()){
            if(mine.getType() != MineType.NORMAL)
                continue;

            mine.tickTimer();
            mine.updateTimer();
            mine.updateSigns();

            if(mine.getMinutes() == 0 && mine.getSeconds() == 0)
                mine.reset(null);
        }
    }
}
