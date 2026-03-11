package fadidev.orbitmines.prison.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.inventory.MineInv;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MinesCommand extends Command {

    String[] alias = { "/mines" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(!omp.isInPvP()){
            new MineInv().open(p);
        }
        else{
            p.sendMessage(PrisonMessages.COMMAND_IN_PVP_AREA.get(omp));
        }
    }
}
