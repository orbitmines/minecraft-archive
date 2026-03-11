package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class AcceptCommand extends Command {

    String[] alias = { "/accept" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasTPRequest()){
            SurvivalPlayer omp2 = omp.getTpRequest();
            Player p2 = PlayerUtils.getPlayer(omp2.getUUID());

            if(p2 != null){
                omp2.setBackLocation(p2.getLocation());
                p2.teleport(p);
                p.sendMessage(SurvivalMessages.CMD_ACCEPT_TO_YOU.get(omp, omp2.getName()));
                p2.sendMessage(SurvivalMessages.CMD_ACCEPTED.get(omp2, omp.getName()));
                p2.playSound(p2.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
            }
            else{
                p.sendMessage(SurvivalMessages.CMD_ACCEPT_NOT_ONLINE.get(omp, UUIDUtils.getName(omp2.getUUID())));
            }

            omp.setTpRequest(null);
        }
        else if(omp.hasTPHereRequest()){
            SurvivalPlayer omp2 = omp.getTphereRequest();
            Player p2 = PlayerUtils.getPlayer(omp2.getUUID());

            if(p2 != null){
                omp.setBackLocation(p.getLocation());
                p.teleport(p2);
                p.sendMessage(SurvivalMessages.CMD_ACCEPT_TO_THEM.get(omp, omp2.getName()));
                p2.sendMessage(SurvivalMessages.CMD_ACCEPTED.get(omp2, omp.getName()));
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
            }
            else{
                p.sendMessage(SurvivalMessages.CMD_ACCEPT_NOT_ONLINE.get(omp, UUIDUtils.getName(omp2.getUUID())));
            }

            omp.setTphereRequest(null);
        }
        else{
            omp.unknownCommand(a[0]);
        }
    }
}
