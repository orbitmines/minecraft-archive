package fadidev.orbitmines.creative.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.creative.handlers.CreativeMessages;

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
    public void dispatch(OMPlayer omp, String[] a) {
        omp.resetCooldown(Cooldowns.TELEPORTING);
        omp.getPlayer().sendMessage(CreativeMessages.SPAWN_TELEPORTING.get(omp));
    }
}
