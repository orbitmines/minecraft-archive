package fadidev.orbitmines.prison.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class ChestShopTutorialRunnable extends OMRunnable {

    private OrbitMinesPrison prison;
    private int index;

    public ChestShopTutorialRunnable() {
        super(TimeUnit.SECOND, 10);

        prison = OrbitMinesPrison.getPrison();
        index = 0;
    }

    @Override
    public void run() {
        World w = prison.getLobby();

        if(index == 0){
            index++;

            for(PrisonPlayer omp : prison.getPrisonPlayers()){
                Player p = omp.getPlayer();

                if(p.getWorld().getName().equals(w.getName()) && p.getLocation().distance(new Location(w, 0, 71, -4)) <= 16){
                    String currency = " G";

                    String[] lines1 = {"Shop:Sell", "276:0", "1 : 100" + currency, ""};
                    String[] lines3 = {"§2§lSell", "Diamond Sword", "1 : 100" + currency, p.getName()};

                    p.sendSignChange(new Location(w, -1, 71, -4), lines1);
                    p.sendSignChange(new Location(w, 1, 71, -4), lines3);
                }
            }

            return;
        }

        index = 0;

        for(PrisonPlayer omp : prison.getPrisonPlayers()){
            Player p = omp.getPlayer();

            if(p.getWorld().getName().equals(w.getName()) && p.getLocation().distance(new Location(w, 0, 71, -4)) <= 16){
                String currency = " G";

                String[] lines1 = {"Shop:Buy", "4:0", "64 : 10" + currency, ""};
                String[] lines3 = {"§2§lBuy", "Cobblestone", "64 : 10" + currency, p.getName()};

                p.sendSignChange(new Location(w, -1, 71, -4), lines1);
                p.sendSignChange(new Location(w, 1, 71, -4), lines3);
            }
        }
    }
}
