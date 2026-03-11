package fadidev.orbitmines.prison.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
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
        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        omp.resetCooldown(Cooldowns.TELEPORTING);
        p.sendMessage("§7" + PrisonMessages.WORD_TELEPORTING_TO.get(omp) + " §6Spawn§7...");
    }
}
