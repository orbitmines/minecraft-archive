package fadidev.orbitmines.kitpvp.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 13-9-2016.
 */
public class CooldownMagic1 extends Cooldown {

    public CooldownMagic1() {
        super(30000, "§5§lMagic", "§bWeapon§5", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
