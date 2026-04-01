package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.argument;

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
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Warp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WarpArgument extends Argument<Survival, SurvivalPlayer, Warp, WarpArgument> {

    private static final int LIMIT = 30;

    protected final Survival server;

    @Getter protected final String name;

    public WarpArgument(Survival server) {
        this.server = server;
        this.name = "warp";
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.argument.warp.description");
    }

    @Override
    public Warp getValue(SurvivalPlayer player, String string) {
        Warp warp = server.getWarp(string);

        if (warp == null) {
            player.sendMessage("Commands", Color.ERROR, "survival", "player.command.argument.warp.invalid");
            return null;
        } else if (!warp.isEnabled()) {
            player.sendMessage("Commands", Color.ERROR, "survival", "player.command.argument.warp.not_enabled");
            return null;
        }

        return warp;
    }

    @Override
    public String invalidReason(SurvivalPlayer player, String string, Warp value) {
        Warp warp = server.getWarp(string);
        if (warp != null && !warp.isEnabled())
            return player.translate("survival", "player.command.argument.warp.not_enabled");

        return player.translate("survival", "player.command.argument.warp.invalid");
    }

    @Override
    public String getValidTooltip(SurvivalPlayer player, Warp value) {
        return getTooltip(player, value).getString();
    }
    private Message getTooltip(SurvivalPlayer player, Warp warp) {
        OfflinePlayer owner = OfflinePlayer.get(warp.getOwner());

        return () ->
            "§7§lWarp §3§l" + warp.getName() + "\n" +
            " §7" + player.translate("survival", "player.warp.owner") + ": " + owner.getName(Name.RAW_COLORED) + "\n" +
            " §7XZ: " + (warp.getLocation() == null ? "§c§l" + player.translate("survival", "player.warp.not_set") : "§a§l" + NumberUtils.locale(warp.getLocation().getBlockX()) + "§7 / §a§l" +  NumberUtils.locale(warp.getLocation().getBlockZ()));
    }

    @Override
    public Set<String> getExamples(SurvivalPlayer player, int limit) {
        Set<String> examples = new HashSet<>();

        int count = 0;
        for (Warp warp : server.getWarps()) {
            examples.add("§3" + warp.getName());
            count++;

            if (count == limit)
                break;
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(SurvivalPlayer player, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toLowerCase();

        int count = 0;
        for (Warp warp : server.getWarps()) {
            if (!warp.getName().toLowerCase().startsWith(remaining))
                continue;

            Message tooltip = getTooltip(player, warp);

            builder.suggest(warp.getName(), tooltip);
            count++;

            if (count == LIMIT)
                break;
        }

        return builder.buildFuture();
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.GREEDY_PHRASE;
    }

    @Override
    protected WarpArgument getInstance() {
        return this;
    }
}
