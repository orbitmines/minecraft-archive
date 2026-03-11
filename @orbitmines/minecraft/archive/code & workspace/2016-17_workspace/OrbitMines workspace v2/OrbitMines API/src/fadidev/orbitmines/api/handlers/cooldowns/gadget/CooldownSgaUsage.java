package fadidev.orbitmines.api.handlers.cooldowns.gadget;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.perks.Gadget;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownSgaUsage extends Cooldown {

    public CooldownSgaUsage() {
        super(180000, Gadget.SNOWMAN_ATTACK.getName(), Gadget.SNOWMAN_ATTACK.getName(), Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
