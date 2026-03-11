package fadidev.orbitmines.skyblock.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.skyblock.handlers.SkyBlockMessages;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
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
        SkyBlockPlayer omp = (SkyBlockPlayer) omPlayer;
        Player p = omp.getPlayer();

        omp.resetCooldown(Cooldowns.TELEPORTING);
        p.sendMessage("§7" + SkyBlockMessages.WORD_TELEPORTING_TO.get(omp) + " §6Spawn§7...");
    }
}
