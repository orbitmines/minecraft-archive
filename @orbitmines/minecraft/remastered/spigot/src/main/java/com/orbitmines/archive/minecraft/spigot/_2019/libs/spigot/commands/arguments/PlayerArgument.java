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
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Argument<S, P, P, PlayerArgument> {

    private static final int LIMIT = 30;

    protected final S server;

    @Getter protected final String name;
    @Getter protected final boolean allowSelfSuggestion;

    public PlayerArgument(S server, boolean allowSelfSuggestion) {
        this(server, "player", allowSelfSuggestion);
    }

    public PlayerArgument(S server, String name, boolean allowSelfSuggestion) {
        this.server = server;
        this.name = name;
        this.allowSelfSuggestion = allowSelfSuggestion;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.player.description");
    }

    @Override
    public P getValue(P player, String string) {
        UUID uuid = UUIDUtils.getUUID(string);

        if (uuid == null) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.player.invalid", string);
            return null;
        }

        if (!allowSelfSuggestion && uuid.equals(player.getUUID())) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.player.self");
            return null;
        }

        P value = server.getPlayer(uuid);

        if (value == null) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.player.invalid", string);
            return null;
        }

        return value;
    }

    @Override
    public String invalidReason(P player, String string, P value) {
        if (!allowSelfSuggestion && string.equalsIgnoreCase(player.getRawName()))
            return player.translate("spigot", "player.command.argument.player.self");

        return player.translate("spigot", "player.command.argument.player.invalid", string);
    }

    @Override
    public String getValidTooltip(P player, P value) {
        return getTooltip(value).getString();
    }
    private Message getTooltip(P player) {
        String name = player.getName(Name.RAW_PREFIXED);
        String server = this.server.getType().getDisplayName();

        return () -> name + "§r §7» " + server;
    }

    @Override
    public Set<String> getExamples(P p, int limit) {
        Set<String> examples = new HashSet<>();

        int count = 0;
        for (P player : this.server.getPlayers()) {
            if (!allowSelfSuggestion && player.getRawName().equals(p.getRawName()))
                continue;

            examples.add(player.getName(Name.RAW_COLORED));
            count++;

            if (count == limit)
                break;
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(P p, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toLowerCase();

        int count = 0;
        for (P player : server.getPlayers()) {
            if (!player.getRawName().toLowerCase().startsWith(remaining))
                continue;

            if (!allowSelfSuggestion && p.getRawName().equals(player.getRawName()))
                continue;

            Message tooltip = getTooltip(player);

            builder.suggest(player.getRawName(), tooltip);
            count++;

            if (count == LIMIT)
                break;
        }

        return builder.buildFuture();
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected PlayerArgument getInstance() {
        return this;
    }
}
