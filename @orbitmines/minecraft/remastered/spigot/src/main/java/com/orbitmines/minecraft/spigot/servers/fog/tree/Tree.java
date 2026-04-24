package com.orbitmines.minecraft.spigot.servers.fog.tree;

import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import lombok.Getter;
import org.bukkit.Material;

public enum Tree {

    /* Vanilla */
    OAK(null, Material.OAK_LOG, 1),
    BIRCH(null, Material.BIRCH_LOG, 1),
    SPRUCE(null, Material.SPRUCE_LOG, 2),
    DARK_OAK(null, Material.DARK_OAK_LOG, 3),
    ACACIA(null, Material.ACACIA_LOG, 2),
    JUNGLE(null, Material.JUNGLE_LOG, 3),
    MANGROVE(null, Material.MANGROVE_LOG, 3),
    CHERRY(null, Material.CHERRY_LOG, 4),

    /* Choice-unlocked */
    TAR(Choice.TREE_TAR, Material.COAL_BLOCK, 5),
    GLOWWOOD(Choice.TREE_GLOWWOOD, Material.GLOWSTONE, 8),
    VOIDOAK(Choice.TREE_VOIDOAK, Material.SCULK, 15);

    @Getter private final Choice unlockChoice;
    @Getter private final Material logMaterial;
    @Getter private final int value;

    Tree(Choice unlockChoice, Material logMaterial, int value) {
        this.unlockChoice = unlockChoice;
        this.logMaterial = logMaterial;
        this.value = value;
    }

    public boolean alwaysAvailable() { return unlockChoice == null; }
}
