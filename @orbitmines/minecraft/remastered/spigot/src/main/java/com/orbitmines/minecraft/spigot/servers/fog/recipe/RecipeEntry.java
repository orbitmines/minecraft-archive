package com.orbitmines.minecraft.spigot.servers.fog.recipe;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import lombok.Getter;
import org.bukkit.Material;

/** One entry in the codex. Text comes from {@code fog/recipe.<id>.{name,description}}. */
public class RecipeEntry {

    @Getter private final String id;
    @Getter private final Material icon;
    @Getter private final Choice unlockChoice; // null = always available

    public RecipeEntry(String id, Material icon, Choice unlockChoice) {
        this.id = id;
        this.icon = icon;
        this.unlockChoice = unlockChoice;
    }

    public String nameKey()        { return "recipe." + id + ".name"; }
    public String descriptionKey() { return "recipe." + id + ".description"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
    public String getDescription(Languageable viewer) { return viewer.translate("fog", descriptionKey()); }
}
