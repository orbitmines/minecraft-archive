package fadidev.orbitmines.api.handlers.cooldowns.pet;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownPetBark extends Cooldown {

    public CooldownPetBark() {
        super(3000, "§6§lBark", "§6§lBark", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
