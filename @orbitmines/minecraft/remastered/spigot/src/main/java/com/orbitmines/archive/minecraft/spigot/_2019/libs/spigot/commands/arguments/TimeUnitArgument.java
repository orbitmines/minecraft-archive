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
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TimeUnitArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Argument<S, P, TimeUtils.Unit, TimeUnitArgument> {

    @Getter protected final String name;
    @Getter private final TimeUtils.Unit[] units;

    public TimeUnitArgument(TimeUtils.Unit... units) {
        this("unit", units);
    }

    public TimeUnitArgument(String name, TimeUtils.Unit... units) {
        this.name = name;
        this.units = units;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.time_unit.description");
    }

    @Override
    public TimeUtils.Unit getValue(P player, String string) {
        TimeUtils.Unit unit = TimeUtils.Unit.getFromShortTranslation(player.getLanguage(), string);

        if (unit != null)
            return unit;

        player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.time_unit.invalid", string);
        return null;
    }

    @Override
    public String invalidReason(P player, String string, TimeUtils.Unit value) {
        return player.translate("spigot", "player.command.argument.time_unit.invalid", string);
    }

    @Override
    public String getValidTooltip(P player, TimeUtils.Unit unit) {
        return getTooltip(player, unit).getString();
    }
    private Message getTooltip(P player, TimeUtils.Unit unit) {
        return () -> "§7" + TimeUtils.getTranslation(player.getLanguage(), unit, false);
    }

    @Override
    public Set<String> getExamples(P player, int limit) {
        Set<String> examples = new HashSet<>();

        for (int i = 0; i < units.length && i < limit; i++) {
            examples.add(TimeUtils.getShortTranslation(player.getLanguage(), units[i]));
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(P player, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        for (TimeUtils.Unit unit : this.units) {
            builder.suggest(TimeUtils.getShortTranslation(player.getLanguage(), unit), getTooltip(player, unit));
        }

        return builder.buildFuture();
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected TimeUnitArgument getInstance() {
        return this;
    }
}
