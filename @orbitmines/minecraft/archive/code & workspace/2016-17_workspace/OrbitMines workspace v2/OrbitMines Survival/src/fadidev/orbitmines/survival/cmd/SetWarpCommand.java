package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.inventories.WarpRenameGUI;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SetWarpCommand extends Command {

    private OrbitMinesSurvival survival;
    String[] alias = { "/setwarp" };

    public SetWarpCommand(){
        survival = OrbitMinesSurvival.getSurvival();
    }

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.getWarps().size() < omp.getWarpsAllowed()){
            if(p.getWorld().getName().equals(survival.getSurvivalWorld().getName())){
                new WarpRenameGUI(omp, null).open();
            }
            else{
                p.sendMessage(SurvivalMessages.CMD_SET_WARP_OVERWORLD.get(omp));
            }
        }
        else{
            if(omp.getWarps().size() == 0){
                p.sendMessage(SurvivalMessages.CMD_SET_WARP_CANT_CREATE.get(omp));
            }
            else{
                p.sendMessage(SurvivalMessages.CMD_SET_WARP_LIMIT_REACHED.get(omp, omp.getWarpsAllowed() + ""));
            }
        }
    }
}
