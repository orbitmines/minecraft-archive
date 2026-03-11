package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SpawnCommand extends Command {

    String[] alias = { "/spawn" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        omp.resetCooldown(Cooldowns.TELEPORTING);
        p.sendMessage("§7" + SurvivalMessages.WORD_TELEPORTING_TO.get(omp) + " §6Spawn§7...");
    }
}
