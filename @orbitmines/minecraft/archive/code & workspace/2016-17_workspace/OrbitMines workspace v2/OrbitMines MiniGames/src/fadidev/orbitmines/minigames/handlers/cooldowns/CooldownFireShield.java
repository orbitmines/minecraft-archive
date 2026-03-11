package fadidev.orbitmines.minigames.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 15-9-2016.
 */
public class CooldownFireShield extends Cooldown {

    public CooldownFireShield() {
        super(12000, "§f§lFire Shield", "§f§lFire Shield", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
