package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.craftbukkit.util.CraftChatMessage;

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
        boolean hasItemHover = this.textComponents.stream().anyMatch(TextComponent::hasItemHover);

        if (!hasItemHover) {
            player.spigot().sendMessage(build(player));
            return;
        }

        /* Build via NMS to properly handle item hover events with full component data */
        sendNms(player);
    }

    private void sendNms(P player) {
        net.minecraft.network.chat.MutableComponent root = net.minecraft.network.chat.Component.empty();

        for (TextComponent<P> c : this.textComponents) {
            net.md_5.bungee.api.chat.TextComponent bungee = c.toBungee(player);
            String json = CraftChatMessage.getBungee().toString(bungee);
            net.minecraft.network.chat.MutableComponent nmsChild = (net.minecraft.network.chat.MutableComponent) CraftChatMessage.fromJSON(json);

            /* Apply NMS-level item hover if present */
            c.applyNmsHover(nmsChild);

            root.append(nmsChild);
        }

        org.bukkit.craftbukkit.entity.CraftPlayer craftPlayer = (org.bukkit.craftbukkit.entity.CraftPlayer) player.bukkit();
        craftPlayer.getHandle().sendSystemMessage(root);
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
