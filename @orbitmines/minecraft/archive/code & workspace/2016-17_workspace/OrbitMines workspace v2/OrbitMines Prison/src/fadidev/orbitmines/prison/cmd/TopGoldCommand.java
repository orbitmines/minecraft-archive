package fadidev.orbitmines.prison.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.StringInt;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Fadi on 10-9-2016.
 */
public class TopGoldCommand extends Command {

    private OrbitMinesPrison prison;
    String[] alias = { "/topgold" };

    public TopGoldCommand(){
        prison = OrbitMinesPrison.getPrison();
    }

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        List<StringInt> players = prison.getTopGold();

        p.sendMessage("");
        p.sendMessage(PrisonMessages.CMD_TOP_GOLD_RICHEST.get(omp));
        sendTopMoneyMessage(p, "§6§l1.§6", players.get(0).getString(), players.get(0).getInteger());
        sendTopMoneyMessage(p, "§7§l2.§7", players.get(1).getString(), players.get(1).getInteger());
        sendTopMoneyMessage(p, "§c§l3.§c", players.get(2).getString(), players.get(2).getInteger());
        sendTopMoneyMessage(p, "§8§l4.§8", players.get(3).getString(), players.get(3).getInteger());
        sendTopMoneyMessage(p, "§8§l5.§8", players.get(4).getString(), players.get(4).getInteger());
        sendTopMoneyMessage(p, "§8§l6.§8", players.get(5).getString(), players.get(5).getInteger());
        sendTopMoneyMessage(p, "§8§l7.§8", players.get(6).getString(), players.get(6).getInteger());
        sendTopMoneyMessage(p, "§8§l8.§8", players.get(7).getString(), players.get(7).getInteger());
        sendTopMoneyMessage(p, "§8§l9.§8", players.get(8).getString(), players.get(8).getInteger());
        sendTopMoneyMessage(p, "§8§l10.§8", players.get(9).getString(), players.get(9).getInteger());
    }

    private void sendTopMoneyMessage(Player p, String placement, String player, int money){
        if(player == null)
            p.sendMessage("  " + placement + " ");
        else
            p.sendMessage("  " + placement + " " + player + " §7| §6" + money + " Gold");
    }
}
