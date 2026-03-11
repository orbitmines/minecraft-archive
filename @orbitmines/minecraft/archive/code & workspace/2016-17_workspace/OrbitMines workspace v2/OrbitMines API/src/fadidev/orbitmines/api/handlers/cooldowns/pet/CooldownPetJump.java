package fadidev.orbitmines.api.handlers.cooldowns.pet;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownPetJump extends Cooldown {

    public CooldownPetJump() {
        super(5000, "§6§lSuper Jump", "§6§lSuper Jump", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
