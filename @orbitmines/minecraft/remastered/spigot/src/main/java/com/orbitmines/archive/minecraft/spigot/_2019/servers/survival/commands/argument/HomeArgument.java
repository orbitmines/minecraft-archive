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
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Home;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HomeArgument extends Argument<Survival, SurvivalPlayer, Home, HomeArgument> {

    private static final int LIMIT = 30;

    @Getter protected final String name;
    private final boolean allowNonexistant;

    public HomeArgument() {
        this(false);
    }
    public HomeArgument(boolean allowNonexistant) {
        this.name = "name";
        this.allowNonexistant = allowNonexistant;
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.argument.home.description");
    }

    @Override
    public Home getValue(SurvivalPlayer player, String string) {
        Home home = player.getHome(string);

        if (!allowNonexistant && home == null) {
            player.sendMessage("Commands", Color.ERROR, "survival", "player.command.argument.home.invalid", string);
            return null;
        } else if (home == null) {
            return new Home(player.getUUID(), string, player.getLocation());
        }

        return home;
    }

    @Override
    public String invalidReason(SurvivalPlayer player, String string, Home value) {
        return player.translate("survival", "player.command.argument.home.invalid", string);
    }

    @Override
    public String getValidTooltip(SurvivalPlayer player, Home value) {
        return getTooltip(player, value).getString();
    }
    private Message getTooltip(SurvivalPlayer player, Home home) {
        return () -> "§7XZ: " + "§6§l" + NumberUtils.locale(home.getLocation().getBlockX()) + "§7 / §6§l" +  NumberUtils.locale(home.getLocation().getBlockZ());
    }

    @Override
    public Set<String> getExamples(SurvivalPlayer player, int limit) {
        Set<String> examples = new HashSet<>();

        int count = 0;
        for (Home home : player.getHomes(false)) {
            examples.add("§6" + home.getName());
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
        for (Home home : player.getHomes(false)) {
            if (!home.getName().toLowerCase().startsWith(remaining))
                continue;

            Message tooltip = getTooltip(player, home);

            builder.suggest(home.getName(), tooltip);
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
    protected HomeArgument getInstance() {
        return this;
    }
}
