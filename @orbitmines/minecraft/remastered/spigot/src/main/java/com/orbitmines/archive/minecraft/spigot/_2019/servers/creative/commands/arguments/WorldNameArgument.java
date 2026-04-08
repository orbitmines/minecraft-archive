package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.commands.arguments;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldNameArgument extends Argument<Creative, CreativePlayer, String, WorldNameArgument> {

    @Getter protected final String name = "world";

    private final Creative creative;

    public WorldNameArgument(Creative creative) {
        this.creative = creative;
    }

    @Override
    public String getDescription(CreativePlayer player) {
        return "World name";
    }

    @Override
    public String getValue(CreativePlayer player, String string) {
        return string;
    }

    @Override
    public String invalidReason(CreativePlayer player, String string, String value) {
        return "Invalid world name";
    }

    @Override
    public String getValidTooltip(CreativePlayer player, String value) {
        return null;
    }

    @Override
    public Set<String> getExamples(CreativePlayer player, int limit) {
        Set<String> examples = new HashSet<>();
        for (CreativeWorld world : player.getWorlds()) {
            examples.add(world.getName());
            if (examples.size() >= limit)
                break;
        }
        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CreativePlayer player, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toLowerCase();

        return CompletableFuture.supplyAsync(() -> {
            try {
                String playerName = StringArgumentType.getString(context, "player");
                PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.NAME.is(playerName));
                if (model == null)
                    return builder.build();

                List<CreativeWorld> worlds = creative.loadPlayerWorlds(model.getUUID());
                for (CreativeWorld world : worlds) {
                    if (world.getName().toLowerCase().startsWith(remaining))
                        builder.suggest(world.getName());
                }
            } catch (Exception ignored) {
            }

            return builder.build();
        });
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.GREEDY_PHRASE;
    }

    @Override
    protected WorldNameArgument getInstance() {
        return this;
    }
}
