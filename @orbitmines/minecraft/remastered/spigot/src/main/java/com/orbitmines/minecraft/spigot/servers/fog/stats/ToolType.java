package com.orbitmines.minecraft.spigot.servers.fog.stats;

import org.bukkit.Material;

public enum ToolType {

    PICKAXE,
    AXE,
    SHOVEL,
    HOE,
    SWORD,
    FISHING_ROD,
    SHEARS,
    HAND;

    public static ToolType fromItem(Material mat) {
        if (mat == null) return HAND;
        String n = mat.name();
        if (n.endsWith("_PICKAXE")) return PICKAXE;
        if (n.endsWith("_AXE")) return AXE;
        if (n.endsWith("_SHOVEL")) return SHOVEL;
        if (n.endsWith("_HOE")) return HOE;
        if (n.endsWith("_SWORD")) return SWORD;
        if (n.equals("FISHING_ROD")) return FISHING_ROD;
        if (n.equals("SHEARS")) return SHEARS;
        return HAND;
    }
}
