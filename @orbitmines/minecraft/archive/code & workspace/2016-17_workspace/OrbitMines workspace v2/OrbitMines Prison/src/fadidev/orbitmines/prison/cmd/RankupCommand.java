package fadidev.orbitmines.prison.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class RankupCommand extends Command {

    String[] alias = { "/rankup" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.getRank().getNextRank() != null){
            if(omp.canRankup())
                omp.rankup();
            else
                omp.requiredGold(omp.getRank().getRankupPrice());
        }
        else{
            p.sendMessage(PrisonMessages.CMD_RANKUP_HIGHEST.get(omp));
        }
    }
}
