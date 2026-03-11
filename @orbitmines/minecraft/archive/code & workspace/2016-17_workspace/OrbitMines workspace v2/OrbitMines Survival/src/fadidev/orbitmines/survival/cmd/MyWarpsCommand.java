package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.handlers.Warp;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MyWarpsCommand extends Command {

    String[] alias = { "/mywarps" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        String warps = "";
        for(Warp warp : omp.getWarps()){
            if(warps.equals(""))
                warps = "§6" + warp.getName();
            else
                warps += "§7, §6" + warp.getName();
        }

        if(!warps.equals(""))
            p.sendMessage("§7" + SurvivalMessages.WORD_YOUR.get(omp) + " Warps: " + warps);
        else
            p.sendMessage(SurvivalMessages.CMD_MY_WARPS_NONE.get(omp));
    }
}
