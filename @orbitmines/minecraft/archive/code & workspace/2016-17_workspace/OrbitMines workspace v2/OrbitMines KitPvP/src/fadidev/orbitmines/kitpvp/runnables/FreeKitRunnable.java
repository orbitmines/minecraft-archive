package fadidev.orbitmines.kitpvp.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;

import java.util.Calendar;

/**
 * Created by Fadi on 13-9-2016.
 */
public class FreeKitRunnable extends OMRunnable {

    private OrbitMinesKitPvP kitPvP;

    public FreeKitRunnable() {
        super(TimeUnit.HOUR, 1);

        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @Override
    public void run() {
        kitPvP.setFreeKitEnabled(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
    }
}
