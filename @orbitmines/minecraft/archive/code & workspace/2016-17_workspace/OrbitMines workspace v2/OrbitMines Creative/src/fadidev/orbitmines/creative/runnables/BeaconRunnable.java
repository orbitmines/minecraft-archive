package fadidev.orbitmines.creative.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import org.bukkit.block.Block;

/**
 * Created by Fadi on 14-9-2016.
 */
public class BeaconRunnable extends OMRunnable {

    private OrbitMinesCreative creative;

    public BeaconRunnable() {
        super(TimeUnit.SECOND, 1);

        creative = OrbitMinesCreative.getCreative();
    }

    @Override
    public void run() {
        byte data = (byte) Utils.RANDOM.nextInt(16);
        for(Block b : creative.getBeacons()){
            b.setData(data);
        }
    }
}
