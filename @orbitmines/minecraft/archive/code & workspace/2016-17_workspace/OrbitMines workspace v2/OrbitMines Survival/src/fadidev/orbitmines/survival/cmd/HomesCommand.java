package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.handlers.Home;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class HomesCommand extends Command {

    String[] alias = { "/homes" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        String homes = "";
        for(Home home : omp.getHomes()){
            if(homes.equals(""))
                homes = "§6" + home.getName();
            else
                homes += "§7, §6" + home.getName();
        }

        if(!homes.equals(""))
            p.sendMessage("§7" + SurvivalMessages.WORD_YOUR.get(omp) + " Homes: " + homes);
        else
            p.sendMessage(SurvivalMessages.CMD_NO_HOMES.get(omp));
    }
}
