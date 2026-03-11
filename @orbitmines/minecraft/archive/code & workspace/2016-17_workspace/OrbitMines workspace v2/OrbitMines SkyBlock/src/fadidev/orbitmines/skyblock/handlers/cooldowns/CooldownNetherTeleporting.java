package fadidev.orbitmines.skyblock.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 15-9-2016.
 */
public class CooldownNetherTeleporting extends Cooldown {

    public CooldownNetherTeleporting() {
        super(8000, null, null, Action.OTHER);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
