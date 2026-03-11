package fadidev.orbitmines.kitpvp.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;

/**
 * Created by Fadi on 13-9-2016.
 */
public class TeleporterRunnable extends OMRunnable {

    private OrbitMinesKitPvP kitPvP;

    public TeleporterRunnable() {
        super(TimeUnit.SECOND, 1);

        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @Override
    public void run() {
        kitPvP.getTeleporterInv().update();
    }
}
