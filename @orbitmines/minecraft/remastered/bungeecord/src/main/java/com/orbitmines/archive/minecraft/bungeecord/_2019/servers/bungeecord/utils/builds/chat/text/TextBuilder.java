package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.builds.chat.text;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.mutable.MutablePlayerString;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TextBuilder {

    private final List<TextComponent> textComponents;

    public TextBuilder() {
        this(new ArrayList<>());
    }

    public TextBuilder(TextComponent... components) {
        this(Arrays.asList(components));
    }

    public TextBuilder(Collection<TextComponent> components) {
        this.textComponents = components == null ? new ArrayList<>() : new ArrayList<>(components);
    }

    public void add(TextComponent... textComponents) {
        this.textComponents.addAll(Arrays.asList(textComponents));
    }

    public void add(Collection<TextComponent> textComponents) {
        this.textComponents.addAll(textComponents);
    }

    public TextComponent space() {
        TextComponent component = new TextComponent(this, ChatColor.WHITE, (p) -> " ");
        this.textComponents.add(component);

        return component;
    }
    public TextComponent add(Color color, MutablePlayerString mutableString) {
        TextComponent component = new TextComponent(this, color, mutableString);
        this.textComponents.add(component);

        return component;
    }
    public TextComponent add(ChatColor chatColor, MutablePlayerString mutableString) {
        TextComponent component = new TextComponent(this, chatColor, mutableString);
        this.textComponents.add(component);

        return component;
    }

    public net.md_5.bungee.api.chat.TextComponent build(BungeePlayer player) {
        net.md_5.bungee.api.chat.TextComponent component = new net.md_5.bungee.api.chat.TextComponent();
        for (TextComponent c : this.textComponents) {
            component.addExtra(c.toBungee(player));
        }

        return component;
    }

    public net.md_5.bungee.api.chat.TextComponent[] buildArray(BungeePlayer player) {
        net.md_5.bungee.api.chat.TextComponent[] components = new net.md_5.bungee.api.chat.TextComponent[this.textComponents.size()];

        int i = 0;
        for (TextComponent c : this.textComponents) {
            components[i] = c.toBungee(player);
            i++;
        }

        return components;
    }

    public void send(BungeePlayer player) {
        player.sendMessage(build(player));
    }

    public void send(BungeePlayer... players) {
        for (BungeePlayer player : players) {
            send(player);
        }
    }

    public void send(Collection<BungeePlayer> players) {
        for (BungeePlayer player : players) {
            send(player);
        }
    }

    public TextBuilder copy() {
        TextBuilder builder = new TextBuilder();
        for (TextComponent component : this.textComponents) {
            builder.add(component.copyTo(builder));
        }
        return builder;
    }
}
