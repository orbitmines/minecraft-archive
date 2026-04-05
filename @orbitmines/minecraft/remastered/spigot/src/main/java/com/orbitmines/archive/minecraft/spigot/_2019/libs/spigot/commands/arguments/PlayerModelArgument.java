package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments;

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
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Where;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PlayerModelArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Argument<S, P, PlayerModel, PlayerModelArgument> {

    private static final int LIMIT = 5;

    @Getter protected final String name;
    @Getter protected final boolean allowSelfSuggestion;

    public PlayerModelArgument(boolean allowSelfSuggestion) {
        this("player", allowSelfSuggestion);
    }

    public PlayerModelArgument(String name, boolean allowSelfSuggestion) {
        this.name = name;
        this.allowSelfSuggestion = allowSelfSuggestion;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.player_model.description");
    }

    @Override
    public PlayerModel getValue(P player, String string) {
        PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.NAME.is(string));

        if (model == null) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.player_model.invalid", string);
            return null;
        }

        if (!allowSelfSuggestion && model.getUUID().equals(player.getUUID())) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.player.self");
            return null;
        }

        return model;
    }

    @Override
    public String invalidReason(P player, String string, PlayerModel value) {
        if (!allowSelfSuggestion && string.equalsIgnoreCase(player.getRawName()))
            return player.translate("spigot", "player.command.argument.player.self");

        return player.translate("spigot", "player.command.argument.player_model.invalid", string);
    }

    @Override
    public String getValidTooltip(P player, PlayerModel value) {
        return getTooltip(value).getString();
    }
    private Message getTooltip(PlayerModel player) {
        String name = player.getName(Name.RAW_PREFIXED);
        return () -> name;
    }

    @Override
    public Set<String> getExamples(P p, int limit) {
        Set<String> examples = new HashSet<>();

        for (PlayerModel model : PlayerModel.getAll(PlayerModel.class, PlayerModel.limitedTo(LIMIT))) {
            if (!allowSelfSuggestion && model.getRawName().equals(p.getRawName()))
                continue;

            examples.add(model.getName(Name.RAW_COLORED));
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(P p, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toLowerCase();

        return CompletableFuture.supplyAsync(() -> {
            for (PlayerModel player : PlayerModel.getAll(PlayerModel.class,
                PlayerModel.column.NAME.is(Where.LIKE, remaining + "%"),
                PlayerModel.limitedTo(LIMIT))
            ) {
                if (!allowSelfSuggestion && p.getRawName().equals(player.getRawName()))
                    continue;

                Message tooltip = getTooltip(player);

                builder.suggest(player.getRawName(), tooltip);
            }

            return builder.build();
        });
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected PlayerModelArgument getInstance() {
        return this;
    }
}
