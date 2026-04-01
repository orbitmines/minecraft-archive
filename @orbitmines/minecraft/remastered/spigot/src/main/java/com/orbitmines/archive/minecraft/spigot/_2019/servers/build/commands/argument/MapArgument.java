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
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.database.models.BuildMap;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MapArgument extends Argument<Build, BuildPlayer, BuildMap, MapArgument> {

    private final Build build;
    @Getter protected final String name;

    public MapArgument(Build build) {
        this(build, "map");
    }

    public MapArgument(Build build, String name) {
        this.build = build;
        this.name = name;
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.argument.map.description");
    }

    @Override
    public BuildMap getValue(BuildPlayer player, String string) {
        BuildMap map = build.getMap(string);
        
        if (map == null) {
            player.sendMessage("Commands", Color.ERROR, "build", "player.command.argument.map.invalid", string);
            return null;
        }
        
        return map;
    }

    @Override
    public String invalidReason(BuildPlayer player, String string, BuildMap value) {
        return player.translate("build", "player.command.argument.map.invalid", string);
    }

    @Override
    public String getValidTooltip(BuildPlayer player, BuildMap map) {
        return getTooltip(player, map).getString();
    }
    private Message getTooltip(BuildPlayer player, BuildMap map) {
        return () -> "§7" + map.getName();
    }

    @Override
    public Set<String> getExamples(BuildPlayer player, int limit) {
        Set<String> examples = new HashSet<>();

        for (int i = 0; i < build.getMaps().size() && i < limit; i++) {
            examples.add(build.getMaps().get(i).getName());
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(BuildPlayer player, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toLowerCase();

        for (BuildMap map : build.getMaps()) {
            String name = map.getName();

            if (!name.toLowerCase().startsWith(remaining))
                continue;

            builder.suggest(name, getTooltip(player, map));
        }

        return builder.buildFuture();
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected MapArgument getInstance() {
        return this;
    }
}
