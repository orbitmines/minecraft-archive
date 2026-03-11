package fadidev.orbitmines.api.handlers.cooldowns.gadget;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.perks.Gadget;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownBookExplosion extends Cooldown {

    public CooldownBookExplosion() {
        super(7000, Gadget.BOOK_EXPLOSION.getName(), Gadget.BOOK_EXPLOSION.getName(), Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
