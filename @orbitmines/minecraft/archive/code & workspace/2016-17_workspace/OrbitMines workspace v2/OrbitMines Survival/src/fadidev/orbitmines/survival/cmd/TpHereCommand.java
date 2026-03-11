package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class TpHereCommand extends Command {

    String[] alias = { "/tphere" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasPerms(VIPRank.EMERALD_VIP)){
            if(a.length == 2){
                Player p2 = PlayerUtils.getPlayer(a[1]);

                if(p2 != null){
                    if(p2 != p){
                        SurvivalPlayer omp2 = SurvivalPlayer.getSurvivalPlayer(p2);

                        omp2.setTpRequest(null);
                        omp2.setTphereRequest(omp);
                        p.sendMessage(SurvivalMessages.CMD_TP_HERE.get(omp, omp2.getName()));
                        p2.sendMessage(SurvivalMessages.CMD_TP_HERE_PLAYER.get(omp2, omp.getName()));
                    }
                    else{
                        p.sendMessage(Messages.CMD_TP_TO_SELF.get(omp));
                    }
                }
                else{
                    p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
                }
            }
            else{
                p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6/tphere <player>§7.");
            }
        }
        else{
            omp.requiredVIPRank(VIPRank.EMERALD_VIP);
        }
    }
}
