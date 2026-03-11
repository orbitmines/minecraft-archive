package fadidev.orbitmines.kitpvp.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 13-9-2016.
 */
public class CooldownPaintballs1Usage extends Cooldown {

    public CooldownPaintballs1Usage() {
        super(2000, null, null, Action.OTHER);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
