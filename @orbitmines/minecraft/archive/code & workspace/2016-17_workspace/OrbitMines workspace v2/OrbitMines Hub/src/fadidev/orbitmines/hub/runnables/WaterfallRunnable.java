package fadidev.orbitmines.hub.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.hub.OrbitMinesHub;

/**
 * Created by Fadi on 10-9-2016.
 */
public class WaterfallRunnable extends OMRunnable {

    private OrbitMinesHub hub;

    public WaterfallRunnable() {
        super(TimeUnit.TICK, 1);

        hub = OrbitMinesHub.getHub();
    }

    @Override
    public void run() {
        if(hub.getType().isWaterfallEnabled())
            hub.updateWaterfalls();
    }
}
