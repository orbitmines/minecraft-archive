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
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
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

public class GlobalPlayerArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Argument<S, P, OnlinePlayer, GlobalPlayerArgument> {

    private static final int LIMIT = 30;

    @Getter protected final String name;
    @Getter protected final boolean allowSelfSuggestion;

    public GlobalPlayerArgument(boolean allowSelfSuggestion) {
        this("player", allowSelfSuggestion);
    }

    public GlobalPlayerArgument(String name, boolean allowSelfSuggestion) {
        this.name = name;
        this.allowSelfSuggestion = allowSelfSuggestion;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.player.description");
    }

    @Override
    public OnlinePlayer getValue(P player, String string) {
        UUID uuid = UUIDUtils.getUUID(string);

        if (uuid == null) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.player.invalid", string);
            return null;
        }

        if (!allowSelfSuggestion && uuid.equals(player.getUUID())) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.player.self");
            return null;
        }

        OnlinePlayer value = OnlinePlayer.get(uuid);

        if (value == null || value.getServer().getRank() == null || !player.isEligible(value.getServer().getRank())) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.player.invalid", string);
            return null;
        }

        return value;
    }

    @Override
    public String invalidReason(P player, String string, OnlinePlayer value) {
        if (!allowSelfSuggestion && string.equalsIgnoreCase(player.getRawName()))
            return player.translate("spigot", "player.command.argument.player.self");

        return player.translate("spigot", "player.command.argument.player.invalid", string);
    }

    @Override
    public String getValidTooltip(P player, OnlinePlayer value) {
        return getTooltip(value).getString();
    }
    private Message getTooltip(OnlinePlayer player) {
        String name = player.getName(Name.RAW_PREFIXED);
        String server =  player.getServer().getDisplayName();

        return () -> name + "§r §7» " + server;
    }

    @Override
    public Set<String> getExamples(P p, int limit) {
        Set<String> examples = new HashSet<>();

        for (OnlinePlayer player : OnlinePlayer.getAll(p, limit)) {
            if (!allowSelfSuggestion && player.getRawName().equals(p.getRawName()))
                continue;

            examples.add(player.getName(Name.RAW_COLORED));
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(P p, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toLowerCase();

        Set<String> players = Server.getAllPlayers(p);

        int count = 0;
        for (String player : players) {
            if (!player.toLowerCase().startsWith(remaining))
                continue;

            if (!allowSelfSuggestion && p.getRawName().equals(player))
                continue;

            UUID uuid = UUIDUtils.getUUID(player);
            if (uuid == null)
                continue;

            OnlinePlayer onlinePlayer = OnlinePlayer.get(uuid);
            if (onlinePlayer == null)
                continue;

            Message tooltip = getTooltip(onlinePlayer);

            builder.suggest(player, tooltip);
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
    protected GlobalPlayerArgument getInstance() {
        return this;
    }
}
