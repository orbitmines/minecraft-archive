package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TextBuilder<P extends SpigotPlayer> {

    private final List<TextComponent<P>> textComponents;

    public TextBuilder() {
        this(new ArrayList<>());
    }

    public TextBuilder(TextComponent<P>... components) {
        this(Arrays.asList(components));
    }

    public TextBuilder(Collection<TextComponent<P>> components) {
        this.textComponents = components == null ? new ArrayList<>() : new ArrayList<>(components);
    }

    public void add(TextComponent<P>... textComponents) {
        this.textComponents.addAll(Arrays.asList(textComponents));
    }

    public void add(Collection<TextComponent<P>> textComponents) {
        this.textComponents.addAll(textComponents);
    }

    public TextComponent<P> space() {
        TextComponent<P> component = new TextComponent<>(this, ChatColor.WHITE, (p) -> " ");
        this.textComponents.add(component);

        return component;
    }
    public TextComponent<P> add(Color color, MutablePlayerString<P> mutableString) {
        TextComponent<P> component = new TextComponent<>(this, color, mutableString);
        this.textComponents.add(component);

        return component;
    }
    public TextComponent<P> add(ChatColor chatColor, MutablePlayerString<P> mutableString) {
        TextComponent<P> component = new TextComponent<>(this, chatColor, mutableString);
        this.textComponents.add(component);

        return component;
    }

    public net.md_5.bungee.api.chat.TextComponent build(P player) {
        net.md_5.bungee.api.chat.TextComponent component = new net.md_5.bungee.api.chat.TextComponent();
        for (TextComponent<P> c : this.textComponents) {
            component.addExtra(c.toBungee(player));
        }

        return component;
    }

    public net.md_5.bungee.api.chat.TextComponent[] buildArray(P player) {
        net.md_5.bungee.api.chat.TextComponent[] components = new net.md_5.bungee.api.chat.TextComponent[this.textComponents.size()];

        int i = 0;
        for (TextComponent c : this.textComponents) {
            components[i] = c.toBungee(player);
            i++;
        }

        return components;
    }

    public void send(P player) {
        player.spigot().sendMessage(build(player));
    }

    public void send(P... players) {
        for (P player : players) {
            send(player);
        }
    }

    public void send(Collection<P> players) {
        for (P player : players) {
            send(player);
        }
    }

    public TextBuilder<P> copy() {
        TextBuilder<P> builder = new TextBuilder<>();
        for (TextComponent<P> component : this.textComponents) {
            builder.add(component.copyTo(builder));
        }
        return builder;
    }
}
