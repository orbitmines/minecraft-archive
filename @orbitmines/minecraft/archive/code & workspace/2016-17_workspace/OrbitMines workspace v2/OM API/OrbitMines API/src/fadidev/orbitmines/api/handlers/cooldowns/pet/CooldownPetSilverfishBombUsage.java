package fadidev.orbitmines.api.handlers.cooldowns.pet;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownPetSilverfishBombUsage extends Cooldown {

    public CooldownPetSilverfishBombUsage() {
        super(6000, "§7§lSilverfish Bomb", "§7§lSilverfish Bomb", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
