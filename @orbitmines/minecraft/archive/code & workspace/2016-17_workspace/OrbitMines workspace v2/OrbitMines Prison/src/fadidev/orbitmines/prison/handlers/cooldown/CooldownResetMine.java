package fadidev.orbitmines.prison.handlers.cooldown;

import fadidev.orbitmines.api.handlers.Cooldown;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;

/**
 * Created by Fadi on 15-9-2016.
 */
public class CooldownResetMine extends Cooldown {

    public CooldownResetMine() {
        super(600000, null, null, Action.OTHER);
    }

    @Override
    public long getCooldown(OMPlayer omp) {
        if(omp.hasPerms(VIPRank.DIAMOND_VIP))
            return getCooldown() / 2;
        return getCooldown();
    }
}
