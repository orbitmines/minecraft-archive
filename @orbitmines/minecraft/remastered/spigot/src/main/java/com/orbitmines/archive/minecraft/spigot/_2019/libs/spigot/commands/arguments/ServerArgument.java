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
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ServerArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Argument<S, P, Server, ServerArgument> {

    @Getter protected final String name;

    public ServerArgument() {
        this("server");
    }

    public ServerArgument(String name) {
        this.name = name;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.server.description");
    }

    @Override
    public Server getValue(P player, String string) {
        try {
            Server server = Server.valueOf(ChatColor.stripColor(string.replace(" ", "").replace("»", "")).toUpperCase());

            if (server.getRank() != null && player.isEligible(server.getRank()))
                return server;

            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.server.invalid", string);
            return null;
        } catch(IllegalArgumentException ex) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.server.invalid", string);
            return null;
        }
    }

    @Override
    public String invalidReason(P player, String string, Server value) {
        return player.translate("spigot", "player.command.argument.server.invalid", string);
    }

    @Override
    public String getValidTooltip(P player, Server server) {
        return getTooltip(server).getString();
    }
    private Message getTooltip(Server server) {
        return () -> server.getDisplayName() + "§r §7» " + server.getColor().getCc() + "§l" + server.getOnline() + " §7§l/ " + server.getMaxPlayers();
    }

    @Override
    public Set<String> getExamples(P player, int limit) {
        Set<String> examples = new HashSet<>();
        for (Server server : Server.getAllowed(player, limit)) {
            examples.add(server.getName().toLowerCase());
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(P player, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        for (Server server : Server.getAllowed(player)) {
            if (server.getName().toLowerCase().startsWith(builder.getRemaining().toLowerCase()))
                builder.suggest(server.getName().toLowerCase(), getTooltip(server));
        }

        return builder.buildFuture();
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected ServerArgument getInstance() {
        return this;
    }
}
