package fadidev.orbitmines.survival.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class ChestShopTutorialRunnable extends OMRunnable {

    private OrbitMinesSurvival survival;
    private int index;

    public ChestShopTutorialRunnable() {
        super(TimeUnit.SECOND, 10);

        survival = OrbitMinesSurvival.getSurvival();
        index = 0;
    }

    @Override
    public void run() {
        World w = survival.getLobby();

        if(index == 0){
            index++;

            for(SurvivalPlayer omp : survival.getSurvivalPlayers()){
                Player p = omp.getPlayer();

                if(p.getWorld().getName().equals(w.getName()) && p.getLocation().distance(new Location(w, -42, 79, -5)) <= 16){
                    String currency = "$";

                    String[] lines1 = {"Shop:Sell", "276:0", "1 : 100" + currency, ""};
                    String[] lines3 = {"§2§lSell", "Diamond Sword", "1 : 100" + currency, p.getName()};

                    p.sendSignChange(new Location(w, -43, 79, -6), lines1);
                    p.sendSignChange(new Location(w, -43, 79, -4), lines3);
                }
            }

            return;
        }

        index = 0;

        for(SurvivalPlayer omp : survival.getSurvivalPlayers()){
            Player p = omp.getPlayer();

            if(p.getWorld().getName().equals(w.getName()) && p.getLocation().distance(new Location(w, -42, 79, -5)) <= 16){
                String currency = "$";

                String[] lines1 = {"Shop:Buy", "4:0", "64 : 10" + currency, ""};
                String[] lines3 = {"§2§lBuy", "Cobblestone", "64 : 10" + currency, p.getName()};

                p.sendSignChange(new Location(w, -43, 79, -6), lines1);
                p.sendSignChange(new Location(w, -43, 79, -4), lines3);
            }
        }
    }
}
