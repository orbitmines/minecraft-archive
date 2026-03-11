package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SetHomeCommand extends Command {

    String[] alias = { "/sethome", "/seth" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.getHomes().size() < omp.getHomesAllowed()){
            boolean canCreate = true;

            for(int i = 0; i < a[1].length(); i++){
                if(!Character.isAlphabetic(a[1].charAt(i)) && !Character.isDigit(a[1].charAt(i)))
                    canCreate = false;
            }

            if(canCreate)
                omp.setHome(a[1]);
            else
                p.sendMessage(SurvivalMessages.CMD_SET_HOME_ONLY_CHARACTERS.get(omp));
        }
        else{
            p.sendMessage(SurvivalMessages.CMD_SET_HOME_LIMIT_REACHED.get(omp, omp.getHomesAllowed() + ""));
        }
    }
}
