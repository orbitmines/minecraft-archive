package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.handlers.Warp;
import fadidev.orbitmines.survival.inventories.WarpEditorInv;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class EditWarpCommand extends Command {

    String[] alias = { "/editwarp" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(a.length == 2){
            Warp warp = Warp.getWarp(a[1]);

            if(warp != null && omp.getWarps().contains(warp)){
                new WarpEditorInv(warp).open(p);
            }
            else{
                p.sendMessage(SurvivalMessages.CMD_EDIT_WARP_NOT_ALLOWED.get(omp, a[1]));
            }
        }
        else{
            p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6/editwarp <warp>§7.");
        }
    }
}
