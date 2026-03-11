package fadidev.orbitmines.minigames.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 15-9-2016.
 */
public class CooldownEggBomb extends Cooldown {

    public CooldownEggBomb() {
        super(7000, "§f§lEgg Bomb", "§f§lEgg Bomb", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
