package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.inventories.WarpInv;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class WarpsCommand extends Command {

    String[] alias = { "/warps", "/warp" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omp, String[] a) {
        Player p = omp.getPlayer();
        new WarpInv(0, false).open(p);
    }
}
