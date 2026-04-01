package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.arguments.StringArgumentType;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.ArgumentWithoutSuggestions;
import lombok.Getter;

public class MessageArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends ArgumentWithoutSuggestions<S, P, String, MessageArgument> {

    @Getter protected final String name;

    public MessageArgument() {
        this("message");
    }

    public MessageArgument(String name) {
        this.name = name;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.message.description");
    }

    @Override
    public String getValue(P player, String string) {
        return string;
    }

    @Override
    public String invalidReason(P player, String string, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValidTooltip(P player, String value) {
        return null;
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.GREEDY_PHRASE;
    }

    @Override
    protected MessageArgument getInstance() {
        return this;
    }
}
