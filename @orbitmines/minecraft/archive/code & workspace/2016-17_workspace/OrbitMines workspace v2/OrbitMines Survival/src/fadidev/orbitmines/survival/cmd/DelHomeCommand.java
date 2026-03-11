package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.handlers.Home;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class DelHomeCommand extends Command {

    String[] alias = { "/delhome", "/delh" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.getHomes().size() > 0){
            if(a.length == 2){
                Home home = omp.getHome(a[1]);

                if(home != null){
                    omp.removeHome(home);
                    p.sendMessage(SurvivalMessages.CMD_DEL_HOME.get(omp, home.getName()));
                }
                else{
                    p.sendMessage(SurvivalMessages.CMD_HOME_NO_HOME_NAMED.get(omp, a[1]));
                }
            }
            else{
                p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6" + a[0].toLowerCase() + " <name>§7.");
            }
        }
        else{
            p.sendMessage(SurvivalMessages.CMD_HOME_NONE.get(omp));
        }
    }
}
