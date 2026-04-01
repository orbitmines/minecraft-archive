package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class ArgumentWithoutSuggestions<S extends OMServer<S, P>, P extends OMPlayer<S, P>, V, A extends Argument> extends Argument<S, P, V, A> {

    @Override
    public Set<String> getExamples(P p, int limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(P p, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasSuggestions() {
        return false;
    }
}
