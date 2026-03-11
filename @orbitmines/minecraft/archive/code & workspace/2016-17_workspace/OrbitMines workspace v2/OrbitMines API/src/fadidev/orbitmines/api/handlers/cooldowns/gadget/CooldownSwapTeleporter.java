package fadidev.orbitmines.api.handlers.cooldowns.gadget;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.perks.Gadget;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownSwapTeleporter extends Cooldown {

    public CooldownSwapTeleporter() {
        super(4000, Gadget.SWAP_TELEPORTER.getName(), Gadget.SWAP_TELEPORTER.getName(), Action.LEFT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
