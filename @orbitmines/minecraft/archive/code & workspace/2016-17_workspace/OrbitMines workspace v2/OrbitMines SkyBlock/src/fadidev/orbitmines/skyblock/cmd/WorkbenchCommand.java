package fadidev.orbitmines.skyblock.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
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
        SkyBlockPlayer omp = (SkyBlockPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasPerms(VIPRank.GOLD_VIP))
            p.openWorkbench(null, true);
        else
            omp.requiredVIPRank(VIPRank.GOLD_VIP);
    }
}
