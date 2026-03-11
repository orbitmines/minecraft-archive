package fadidev.orbitmines.minigames.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 15-9-2016.
 */
public class CooldownIronFist extends Cooldown {

    public CooldownIronFist() {
        super(20000, "§f§lIron Fist", "§f§lIron Fist", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
