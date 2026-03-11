package fadidev.orbitmines.prison.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class PayCommand extends Command {

    String[] alias = { "/pay" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(a.length == 3){
            Player p2 = PlayerUtils.getPlayer(a[1]);

            if(p2 != null){
                if(p2 != p){
                    PrisonPlayer omp2 = PrisonPlayer.getPrisonPlayer(p2);

                    boolean numeric = true;

                    for(int i = 0; i < a[2].length(); i++){
                        if(!Character.isDigit(a[2].charAt(i))){
                            numeric = false;
                            break;
                        }
                    }

                    if(numeric){
                        int amount = Integer.parseInt(a[2]);

                        if(omp.hasGold(amount)){
                            p.sendMessage(PrisonMessages.CMD_PAY.get(omp, omp2.getName(), amount + ""));
                            p2.sendMessage(PrisonMessages.CMD_PAY_PLAYER.get(omp2, omp.getName(), amount + ""));
                            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
                            p2.playSound(p2.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

                            omp.removeGold(amount);
                            omp2.addGold(amount);
                        }
                        else{
                            omp.requiredGold(amount);
                        }
                    }
                    else{
                        p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " " + Messages.WORD_AMOUNT.get(omp) +".");
                    }
                }
                else{
                    p.sendMessage(PrisonMessages.CMD_PAY_YOURSELF.get(omp));
                }
            }
            else{
                p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
            }
        }
        else{
            p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6/pay <player> <amount>§7.");
        }
    }
}
