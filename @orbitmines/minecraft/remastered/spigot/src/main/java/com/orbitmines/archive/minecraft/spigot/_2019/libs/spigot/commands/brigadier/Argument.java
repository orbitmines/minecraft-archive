package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.ArgumentBuilder;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/*
    P: Player / Languageable
    V: Value
    A: Argument
 */
public abstract class Argument<S extends OMServer<S, P>, P extends OMPlayer<S, P>, V, A extends Argument> extends ArgumentBuilder<S, P, A> {

    public abstract String getName();

    public abstract String getDescription(P player);

    public abstract V getValue(P player, String string);

    /* Override if you have another valid indicator */
    public boolean isValid(V value) {
        return value != null;
    }

    public abstract String invalidReason(P player, String string, V value);

    public abstract String getValidTooltip(P player, V value);

    public abstract Set<String> getExamples(P player, int limit);

    public abstract CompletableFuture<Suggestions> getSuggestions(P player, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException;

    public abstract StringArgumentType.StringType getType();

    public boolean hasSuggestions() {
        return true;
    }

    public String getString(CommandContext context) {
        return StringArgumentType.getString(context, getName());
    }
}
