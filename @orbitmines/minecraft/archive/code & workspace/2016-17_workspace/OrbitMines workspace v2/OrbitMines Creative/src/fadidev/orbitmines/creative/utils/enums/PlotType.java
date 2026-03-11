package fadidev.orbitmines.creative.utils.enums;

import org.bukkit.Material;

/**
 * Created by Fadi on 14-9-2016.
 */
public enum PlotType {

    NORMAL("§d§lBuild Mode", Material.WOOD_AXE),
    PVP("§c§lPvP Mode", Material.STONE_SWORD);

    private String name;
    private Material material;

    PlotType(String name, Material material){
        this.name = name;
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }
}
