package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.builds.chat.text;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.ColorUtils;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.mutable.MutablePlayerString;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class TextComponent {

    private final TextBuilder builder;

    @Getter private final ChatColor chatColor;
    @Getter private final MutablePlayerString mutableString;

    @Getter private ClickEvent.Action clickAction;
    @Getter private MutablePlayerString clickEvent;

    @Getter private HoverEvent.Action hoverAction;
    @Getter private MutablePlayerString hoverEvent;

    @Getter private boolean bold;
    @Getter private boolean italic;
    @Getter private boolean obfuscated;
    @Getter private boolean strikethrough;
    @Getter private boolean underlined;

    public TextComponent(Color color, MutablePlayerString mutableString) {
        this(null, color, mutableString);
    }

    public TextComponent(ChatColor chatColor, MutablePlayerString mutableString) {
        this(null, chatColor, mutableString);
    }

    public TextComponent(TextBuilder builder, Color color, MutablePlayerString mutableString) {
        this(builder, ColorUtils.toChatColor(color), mutableString);
    }

    public TextComponent(TextBuilder builder, ChatColor chatColor, MutablePlayerString mutableString) {
        this.builder = builder;
        this.chatColor = chatColor;
        this.mutableString = mutableString;
        this.bold = false;
        this.italic = false;
        this.obfuscated = false;
        this.strikethrough = false;
        this.underlined = false;
    }

    public TextComponent(TextBuilder builder, TextComponent textComponent) {
        this.builder = builder;
        this.chatColor = textComponent.chatColor;
        this.mutableString = textComponent.mutableString;
        this.clickAction = textComponent.clickAction;
        this.clickEvent = textComponent.clickEvent;
        this.hoverAction = textComponent.hoverAction;
        this.hoverEvent = textComponent.hoverEvent;
        this.bold = textComponent.bold;
        this.italic = textComponent.italic;
        this.obfuscated = textComponent.obfuscated;
        this.strikethrough = textComponent.strikethrough;
        this.underlined = textComponent.underlined;
    }

    public TextComponent space() {
        if (builder == null)
            throw new UnsupportedOperationException();

        return builder.space();
    }
    public TextComponent add(Color color, MutablePlayerString mutableString) {
        if (builder == null)
            throw new UnsupportedOperationException();

        return builder.add(color, mutableString);
    }
    public TextComponent add(ChatColor chatColor, MutablePlayerString mutableString) {
        if (builder == null)
            throw new UnsupportedOperationException();

        return builder.add(chatColor, mutableString);
    }

    public TextBuilder builder() {
        return builder;
    }

    public TextComponent click(ClickEvent.Action clickAction, MutablePlayerString clickEvent) {
        this.clickAction = clickAction;
        this.clickEvent = clickEvent;

        return this;
    }

    public TextComponent hover(HoverEvent.Action hoverAction, MutablePlayerString hoverEvent) {
        this.hoverAction = hoverAction;
        this.hoverEvent = hoverEvent;

        return this;
    }

    public TextComponent bold() {
        this.bold = true;
        return this;
    }

    public TextComponent italic() {
        this.italic = true;
        return this;
    }

    public TextComponent obfuscated() {
        this.obfuscated = true;
        return this;
    }

    public TextComponent strikethrough() {
        this.strikethrough = true;
        return this;
    }

    public TextComponent underlined() {
        this.underlined = true;
        return this;
    }

    public TextComponent bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public TextComponent italic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public TextComponent obfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    public TextComponent strikethrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public TextComponent underlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public net.md_5.bungee.api.chat.TextComponent toBungee(BungeePlayer player) {
        net.md_5.bungee.api.chat.TextComponent component = new net.md_5.bungee.api.chat.TextComponent(this.mutableString.toString(player));
        component.setColor(this.chatColor);

        if (clickAction != null)
            component.setClickEvent(new ClickEvent(this.clickAction, this.clickEvent.toString(player)));
        if (hoverAction != null)
            component.setHoverEvent(new HoverEvent(this.hoverAction, new ComponentBuilder(this.hoverEvent.toString(player)).create()));

        component.setBold(this.bold);
        component.setItalic(this.italic);
        component.setObfuscated(this.obfuscated);
        component.setStrikethrough(this.strikethrough);
        component.setUnderlined(this.underlined);

        return component;
    }

    public TextComponent copyTo(TextBuilder builder) {
        return new TextComponent(builder, this);
    }
}
