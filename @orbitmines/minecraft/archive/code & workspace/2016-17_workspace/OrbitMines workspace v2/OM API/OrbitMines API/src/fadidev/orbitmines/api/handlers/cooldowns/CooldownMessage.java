package fadidev.orbitmines.api.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownMessage extends Cooldown {

    public CooldownMessage() {
        super(2000, null, null, Action.OTHER);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
