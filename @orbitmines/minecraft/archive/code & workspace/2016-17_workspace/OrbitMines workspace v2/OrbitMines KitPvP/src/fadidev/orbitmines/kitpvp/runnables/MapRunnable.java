package fadidev.orbitmines.kitpvp.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMap;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.Sound;

/**
 * Created by Fadi on 13-9-2016.
 */
public class MapRunnable extends OMRunnable {

    private OrbitMinesKitPvP kitPvP;

    public MapRunnable() {
        super(TimeUnit.SECOND, 1);

        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @Override
    public void run() {
        KitPvPMap map = kitPvP.getCurrentMap();
        map.tickTimer();

        if(map.getSeconds() <= 10 && map.getSeconds() != 0 && map.getMinutes() == 0){
            for(KitPvPPlayer omp : kitPvP.getKitPvPPlayers()){
                omp.getPlayer().sendMessage(KitPvPMessages.SWITCHING_MAPS.get(omp, map.getSeconds() + ""));
                omp.getPlayer().playSound(omp.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
            }
        }
        else if(map.getSeconds() == 0 && map.getMinutes() == 0){
            kitPvP.setNextMap();

            for(KitPvPPlayer omp : kitPvP.getKitPvPPlayers()){
                if(omp.isSpectator() || omp.getKitSelected() != null)
                    omp.teleportToMap();

                omp.getPlayer().sendMessage(KitPvPMessages.MAPS_SWITCHED.get(omp));
            }
        }
    }
}
