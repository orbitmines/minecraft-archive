package fadidev.orbitmines.minigames.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 15-9-2016.
 */
public class CooldownFeatherAttack extends Cooldown {

    public CooldownFeatherAttack() {
        super(6000, "§f§lFeather Attack", "§f§lFeather Attack", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
