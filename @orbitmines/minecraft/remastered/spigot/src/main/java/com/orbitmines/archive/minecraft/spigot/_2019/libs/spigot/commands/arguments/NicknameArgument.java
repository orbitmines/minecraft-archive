package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.arguments.StringArgumentType;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.ArgumentWithoutSuggestions;
import lombok.Getter;

public class NicknameArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends ArgumentWithoutSuggestions<S, P, String, NicknameArgument> {

    private static int MAX_CHARACTERS = 30;

    @Getter protected final String name;

    public NicknameArgument() {
        this("name");
    }

    public NicknameArgument(String name) {
        this.name = name;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.nick.description");
    }

    @Override
    public String getValue(P player, String string) {
        if (string.length() <= MAX_CHARACTERS)
            return string;

        player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.nick.too_long", MAX_CHARACTERS + "");
        return null;
    }

    @Override
    public String invalidReason(P player, String string, String value) {
        return player.translate("spigot", "player.command.argument.nick.too_long", MAX_CHARACTERS + "");
    }

    @Override
    public String getValidTooltip(P player, String value) {
        return null;
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.SINGLE_WORD;
    }

    @Override
    protected NicknameArgument getInstance() {
        return this;
    }
}
