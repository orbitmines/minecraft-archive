package fadidev.orbitmines.kitpvp.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.ActiveBooster;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import org.bukkit.Bukkit;

/**
 * Created by Fadi on 13-9-2016.
 */
public class BoosterRunnable extends OMRunnable {

    private OrbitMinesKitPvP kitPvP;

    public BoosterRunnable() {
        super(TimeUnit.SECOND, 1);

        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @Override
    public void run() {
        ActiveBooster booster = kitPvP.getBooster();
        if(booster == null)
            return;

        booster.tickTimer();

        if(booster.getSeconds() != 0)
            return;

        if(booster.getMinutes() != 0){
            KitPvPMessages.BOOSTER_REMAINS_FOR.broadcast(booster.getPlayer(), booster.getBooster().getMultiplier() + "", booster.getMinutes() + "", booster.getSeconds() + "");
        }
        else{
            KitPvPMessages.BOOSTER_EXPIRED.broadcast(booster.getPlayer(), booster.getBooster().getMultiplier() + "");
            kitPvP.setBooster(null);
        }
    }
}
