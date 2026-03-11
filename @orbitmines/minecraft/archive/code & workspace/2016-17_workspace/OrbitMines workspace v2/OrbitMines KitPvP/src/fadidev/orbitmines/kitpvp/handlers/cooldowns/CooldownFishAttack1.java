package fadidev.orbitmines.kitpvp.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 13-9-2016.
 */
public class CooldownFishAttack1 extends Cooldown {

    public CooldownFishAttack1() {
        super(5000, "§9§lFish Attack", "§9Fish Attack", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
