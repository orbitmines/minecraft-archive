package fadidev.orbitmines.api.handlers.cooldowns.pet;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownPetLeapUsage extends Cooldown {

    public CooldownPetLeapUsage() {
        super(4000, "§8§lLeap", "§8§lLeap", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
