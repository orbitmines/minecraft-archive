package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.InventorySeeInv;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class InvAcceptCommand extends Command {

    String[] alias = { "/invaccept" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasInvseeRequest()){
            SurvivalPlayer omp2 = omp.getInvseeRequest();
            Player p2 = PlayerUtils.getPlayer(omp2.getUUID());

            if(p2 != null){
                new InventorySeeInv(p).open(p2);
                p.sendMessage(SurvivalMessages.CMD_INVSEE_ACCEPT.get(omp, omp2.getName()));
                p2.sendMessage(SurvivalMessages.CMD_ACCEPTED.get(omp2, omp.getName()));
                p2.playSound(p2.getLocation(), Sound.BLOCK_CHEST_OPEN, 5, 1);
            }
            else{
                p.sendMessage(SurvivalMessages.CMD_ACCEPT_NOT_ONLINE.get(omp, UUIDUtils.getName(omp2.getUUID())));
            }

            omp.setInvseeRequest(null);
        }
        else{
            omp.unknownCommand(a[0]);
        }
    }
}
