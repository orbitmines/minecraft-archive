package fadidev.orbitmines.creative.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 13-9-2016.
 */
public class CooldownBroadcast extends Cooldown {

    public CooldownBroadcast() {
        super(300000, null, null, Action.OTHER);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
