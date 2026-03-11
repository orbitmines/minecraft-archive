package fadidev.orbitmines.prison.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class WorkbenchCommand extends Command {

    String[] alias = { "/workbench" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasPerms(VIPRank.DIAMOND_VIP))
            p.openWorkbench(null, true);
        else
            omp.requiredVIPRank(VIPRank.DIAMOND_VIP);
    }
}
