package fadidev.orbitmines.kitpvp.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.inventories.KitSelectorInv;
import fadidev.orbitmines.kitpvp.utils.enums.KitPvPKit;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class KitCommand extends Command {

    String[] alias = { "/kit" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        KitPvPPlayer omp = (KitPvPPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(a.length > 1 && omp.hasPerms(StaffRank.OWNER)){
            if(a.length == 3){
                try{
                    KitPvPKit kitpvpkit = KitPvPKit.valueOf(a[1].toUpperCase());

                    try{
                        int level = Integer.parseInt(a[2]);

                        if(level > 0 && level <= kitpvpkit.getMaxLevel()){
                            Kit kit = kitpvpkit.getKit(level);

                            kit.setItems(p);
                            omp.setKitSelected(kitpvpkit);
                            omp.setKitLevelSelected(level);
                            p.sendMessage(KitPvPMessages.SELECT_KIT.get(omp, kitpvpkit.getName(), "" + level));
                        }
                        else{
                            p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Level!");
                        }
                    }catch(Exception ex){
                        p.sendMessage(Messages.CMD_NOT_NUMBER.get(omp, a[2]));
                    }
                }catch(IllegalArgumentException ex){
                    p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Kit!");
                }
            }
            else{
                p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6/kit <kit> <level>§7.");
            }
        }
        else{
            if(omp.getKitSelected() == null){
                if(omp.isPlayer()){
                    new KitSelectorInv().open(p);
                }
                else{
                    p.sendMessage(KitPvPMessages.CMD_KIT_WHILE_SPECTATING.get(omp));
                }
            }
            else{
                p.sendMessage(KitPvPMessages.CMD_KIT_WHILE_PLAYING.get(omp));
            }
        }
    }
}
