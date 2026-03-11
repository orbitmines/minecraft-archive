package fadidev.orbitmines.skyblock.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class EnderchestCommand extends Command {

    String[] alias = { "/enderchest" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SkyBlockPlayer omp = (SkyBlockPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasPerms(VIPRank.EMERALD_VIP))
            p.openInventory(p.getEnderChest());
        else
            omp.requiredVIPRank(VIPRank.EMERALD_VIP);
    }
}
