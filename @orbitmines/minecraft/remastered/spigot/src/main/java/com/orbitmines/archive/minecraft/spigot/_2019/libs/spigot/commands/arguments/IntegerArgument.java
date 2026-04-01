package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.ArgumentWithoutSuggestions;
import lombok.Getter;

public class IntegerArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends ArgumentWithoutSuggestions<S, P, Integer, IntegerArgument> {

    @Getter protected final String name;

    public IntegerArgument(String name) {
        this.name = name;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.integer.description");
    }

    @Override
    public Integer getValue(P player, String string) {
        try {
            return Integer.parseInt(string);
        } catch(NumberFormatException ex) {
            player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.integer.invalid", string);
            return null;
        }
    }

    @Override
    public String invalidReason(P player, String string, Integer value) {
        return player.translate("spigot", "player.command.argument.integer.invalid", string);
    }

    @Override
    public String getValidTooltip(P player, Integer integer) {
        return getTooltip(integer).getString();
    }

    private Message getTooltip(Integer integer) {
        return () -> "§7" + integer;
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected IntegerArgument getInstance() {
        return this;
    }
}
