package fadidev.orbitmines.api.handlers.cooldowns.pet;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;

/**
 * Created by Fadi on 5-9-2016.
 */
public class CooldownPetKittyCannonUsage extends Cooldown {

    public CooldownPetKittyCannonUsage() {
        super(1000, "§e§lKitty Cannon", "§e§lKitty Cannon", Action.RIGHT_CLICK);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        return getCooldown();
    }
}
