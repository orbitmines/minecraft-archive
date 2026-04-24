package com.orbitmines.minecraft.spigot.servers.fog.recipe;

import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Greenbook-style codex of every recipe/mechanic the player has encountered. */
public class RecipeBook {

    public static final List<RecipeEntry> ENTRIES;

    static {
        List<RecipeEntry> entries = new ArrayList<>();
        entries.add(new RecipeEntry("ore_suit",            Material.IRON_CHESTPLATE, Choice.RECIPE_ORE_SUIT));
        entries.add(new RecipeEntry("pickaxe_upgrade",     Material.DIAMOND_PICKAXE, Choice.RECIPE_PICKAXE_UP));
        entries.add(new RecipeEntry("backpack",            Material.BUNDLE,          Choice.RECIPE_BACKPACK));
        entries.add(new RecipeEntry("drone_factory_intro", Material.SMITHING_TABLE,  null));
        entries.add(new RecipeEntry("crate_schedule",      Material.CHEST,           Choice.STRUCTURE_CRATES));
        entries.add(new RecipeEntry("factions",            Material.ENDER_EYE,       null));
        ENTRIES = Collections.unmodifiableList(entries);
    }
}
