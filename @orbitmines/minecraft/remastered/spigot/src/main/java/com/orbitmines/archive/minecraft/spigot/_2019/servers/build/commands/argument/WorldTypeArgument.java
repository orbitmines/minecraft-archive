package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands.argument;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldTypeArgument extends Argument<Build, BuildPlayer, DefaultWorldType, WorldTypeArgument> {

    @Getter protected final String name;

    public WorldTypeArgument() {
        this("world_type");
    }

    public WorldTypeArgument(String name) {
        this.name = name;
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.argument.world_type.description");
    }

    @Override
    public DefaultWorldType getValue(BuildPlayer player, String string) {
        try {
            return DefaultWorldType.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException ex) {
            player.sendMessage("Commands", Color.ERROR, "build", "player.command.argument.world_type.invalid", string);
            return null;
        }
    }

    @Override
    public String invalidReason(BuildPlayer player, String string, DefaultWorldType value) {
        return player.translate("build", "player.command.argument.world_type.invalid", string);
    }

    @Override
    public String getValidTooltip(BuildPlayer player, DefaultWorldType worldType) {
        return getTooltip(player, worldType).getString();
    }
    private Message getTooltip(BuildPlayer player, DefaultWorldType worldType) {
        return () -> "§7" + worldType.toString().toLowerCase();
    }

    @Override
    public Set<String> getExamples(BuildPlayer player, int limit) {
        Set<String> examples = new HashSet<>();

        DefaultWorldType[] values = DefaultWorldType.values();
        for (int i = 0; i < values.length && i < limit; i++) {
            examples.add(values[i].toString().toLowerCase());
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(BuildPlayer player, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toLowerCase();

        for (DefaultWorldType worldType : DefaultWorldType.values()) {
            String name = worldType.toString().toLowerCase();

            if (!name.toLowerCase().startsWith(remaining))
                continue;

            builder.suggest(name, getTooltip(player, worldType));
        }

        return builder.buildFuture();
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected WorldTypeArgument getInstance() {
        return this;
    }
}
