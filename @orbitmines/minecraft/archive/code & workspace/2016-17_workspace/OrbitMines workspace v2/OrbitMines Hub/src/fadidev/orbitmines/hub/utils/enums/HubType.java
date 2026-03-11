package fadidev.orbitmines.hub.utils.enums;

import org.bukkit.Material;

/**
 * Created by Fadi on 29-10-2016.
 */
public enum HubType {

    DEFAULT(Material.LAPIS_BLOCK, true),
    WINTER(Material.STAINED_CLAY, false),
    HALLOWEEN(Material.NETHER_BRICK, true);

    private Material lapisParkourBlock;
    private boolean waterfallEnabled;

    HubType(Material lapisParkourBlock, boolean waterfallEnabled){
        this.lapisParkourBlock = lapisParkourBlock;
        this.waterfallEnabled = waterfallEnabled;
    }

    public Material getLapisParkourBlock() {
        return lapisParkourBlock;
    }

    public boolean isWaterfallEnabled() {
        return waterfallEnabled;
    }
}
