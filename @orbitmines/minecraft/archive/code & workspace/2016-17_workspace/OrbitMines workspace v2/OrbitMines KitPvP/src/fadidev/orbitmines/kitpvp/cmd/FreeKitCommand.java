package fadidev.orbitmines.kitpvp.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;

/**
 * Created by Fadi on 10-9-2016.
 */
public class FreeKitCommand extends Command {

    private OrbitMinesKitPvP kitPvP;
    String[] alias = { "/freekits" };

    public FreeKitCommand(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omp, String[] a) {
        if(omp.hasPerms(StaffRank.OWNER)){
            kitPvP.setFreeKitEnabled(!kitPvP.isFreeKitEnabled());
            KitPvPMessages.TOGGLE_FREE_KITS.broadcast(omp.getName());
        }
        else{
            omp.unknownCommand(a[0]);
        }
    }
}
