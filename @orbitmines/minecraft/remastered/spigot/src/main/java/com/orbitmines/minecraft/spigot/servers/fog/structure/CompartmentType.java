package com.orbitmines.minecraft.spigot.servers.fog.structure;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import lombok.Getter;
import org.bukkit.Material;

public enum CompartmentType {

    FARM         (Material.HAY_BLOCK,       12, 6,  Choice.STRUCTURE_FARM),
    ENCHANTING   (Material.ENCHANTING_TABLE,10, 8,  Choice.STRUCTURE_ENCHANT),
    CRATES       (Material.CHEST,           8,  6,  Choice.STRUCTURE_CRATES),
    DRONE_FACTORY(Material.SMITHING_TABLE,  14, 10, Choice.DRONE_FACTORY);

    @Getter private final Material marker;
    @Getter private final int radius;
    @Getter private final int height;
    @Getter private final Choice unlockChoice;

    CompartmentType(Material marker, int radius, int height, Choice unlockChoice) {
        this.marker = marker;
        this.radius = radius;
        this.height = height;
        this.unlockChoice = unlockChoice;
    }

    public String nameKey() { return "compartment." + name().toLowerCase() + ".name"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
}
