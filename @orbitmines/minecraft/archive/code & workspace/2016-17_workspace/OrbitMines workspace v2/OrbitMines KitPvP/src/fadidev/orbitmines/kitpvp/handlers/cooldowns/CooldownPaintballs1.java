package fadidev.orbitmines.kitpvp.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 13-9-2016.
 */
public class CooldownPaintballs1 extends Cooldown {

    public CooldownPaintballs1() {
        super(1000, "§f§lPaintballs", "§bWeapon§f", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
