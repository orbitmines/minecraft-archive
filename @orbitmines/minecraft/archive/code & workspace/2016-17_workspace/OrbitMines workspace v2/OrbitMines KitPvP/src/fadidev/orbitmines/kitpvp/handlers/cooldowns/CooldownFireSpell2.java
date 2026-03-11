package fadidev.orbitmines.kitpvp.handlers.cooldowns;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 13-9-2016.
 */
public class CooldownFireSpell2 extends Cooldown {

    public CooldownFireSpell2() {
        super(1000, "§c§lFire Spell", "§cFireWand", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
