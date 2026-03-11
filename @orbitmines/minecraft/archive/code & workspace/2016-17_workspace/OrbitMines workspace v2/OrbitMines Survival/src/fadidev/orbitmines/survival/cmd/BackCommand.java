package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class BackCommand extends Command {

    String[] alias = { "/back" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasPerms(VIPRank.EMERALD_VIP)){
            if(omp.hasBackLocation()){
                Location l = p.getLocation();
                p.teleport(omp.getBackLocation());
                omp.setBackLocation(l);
                p.sendMessage(SurvivalMessages.CMD_BACK_TELEPORTED.get(omp));
            }
            else{
                p.sendMessage(SurvivalMessages.CMD_BACK_CANNOT.get(omp));
            }
        }
        else{
            omp.requiredVIPRank(VIPRank.EMERALD_VIP);
        }
    }
}
