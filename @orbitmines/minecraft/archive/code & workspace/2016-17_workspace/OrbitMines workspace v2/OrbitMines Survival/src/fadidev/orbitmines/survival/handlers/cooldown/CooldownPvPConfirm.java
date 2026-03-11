package fadidev.orbitmines.survival.handlers.cooldown;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 15-9-2016.
 */
public class CooldownPvPConfirm extends Cooldown {

    public CooldownPvPConfirm() {
        super(15000, null, null, Action.OTHER);
    }

    @Override
    public long getCooldown(OMPlayer omPlayer) {
        return getCooldown();
    }
}
