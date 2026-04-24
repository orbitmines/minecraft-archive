package com.orbitmines.minecraft.spigot.servers.fog.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/** Brigadier argument that autocompletes all {@link Choice} enum names. */
public class ChoiceArgument extends Argument<FoG, FoGPlayer, Choice, ChoiceArgument> {

    @Getter private final String name = "choice";

    @Override
    public String getDescription(FoGPlayer player) {
        return player.translate("fog", "player.command.choice.argument.description");
    }

    @Override
    public Choice getValue(FoGPlayer player, String string) {
        Choice value = Choice.parse(string);
        if (value == null) {
            player.sendMessage("FoG", com.orbitmines.archive.minecraft._2019.libs.Color.RED,
                    "fog", "player.command.choice.invalid", string);
        }
        return value;
    }

    @Override
    public String invalidReason(FoGPlayer player, String string, Choice value) {
        return player.translate("fog", "player.command.choice.invalid", string);
    }

    @Override
    public String getValidTooltip(FoGPlayer player, Choice value) {
        return value.getRarity().getColor().getCc() + value.getDisplayName(player);
    }

    @Override
    public Set<String> getExamples(FoGPlayer player, int limit) {
        Set<String> out = new LinkedHashSet<>();
        int n = 0;
        for (Choice c : Choice.values()) {
            out.add(c.name());
            if (++n >= limit) break;
        }
        return out;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(FoGPlayer player, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toUpperCase();
        for (Choice c : Choice.values()) {
            if (!c.name().startsWith(remaining)) continue;
            String display = c.getRarity().getColor().getCc() + c.getDisplayName(player);
            builder.suggest(c.name(), () -> display);
        }
        return builder.buildFuture();
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected ChoiceArgument getInstance() {
        return this;
    }
}
