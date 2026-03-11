package fadidev.orbitmines.api.handlers.cooldowns.gadget;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.perks.Gadget;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownCreeperLauncher extends Cooldown {

    public CooldownCreeperLauncher() {
        super(6000, Gadget.CREEPER_LAUNCHER.getName(), Gadget.CREEPER_LAUNCHER.getName(), Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
