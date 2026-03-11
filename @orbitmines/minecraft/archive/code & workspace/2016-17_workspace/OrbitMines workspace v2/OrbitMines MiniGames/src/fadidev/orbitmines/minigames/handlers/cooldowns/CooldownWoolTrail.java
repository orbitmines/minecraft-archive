package fadidev.orbitmines.minigames.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 15-9-2016.
 */
public class CooldownWoolTrail extends Cooldown {

    public CooldownWoolTrail() {
        super(1000, null, null, Action.OTHER);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
