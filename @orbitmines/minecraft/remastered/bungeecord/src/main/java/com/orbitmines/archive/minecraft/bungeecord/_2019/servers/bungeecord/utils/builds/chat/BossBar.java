package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.builds.chat;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class BossBar {

    private static final AtomicInteger id = new AtomicInteger(1);

    private final UUID bossUUID;

    @Getter private String title;
    private String serializedTitle;

    @Getter private float progress;
    @Getter private Color color;
    @Getter private Style style;

    @Getter private boolean visible;

    private Set<ProxiedPlayer> players = new HashSet<>();

    public BossBar(String title, Color color, Style style) {
        this.bossUUID = UUID.nameUUIDFromBytes(("BBB:" + id.getAndIncrement()).getBytes(Charset.forName("UTF-8")));
        this.title = title;
        this.serializedTitle = ComponentSerializer.toString(new TextComponent(this.title));
        this.progress = 1F;
        this.color = color;
        this.style = style;
        this.visible = true;
    }

    public void setTitle(String title) {
        this.title = title;
        this.serializedTitle = ComponentSerializer.toString(new TextComponent(this.title));

        if (!visible)
            return;

        net.md_5.bungee.protocol.packet.BossBar packet = new net.md_5.bungee.protocol.packet.BossBar(bossUUID, 4);
        packet.setColor(this.color.ordinal());
        packet.setDivision(this.style.ordinal());
        this.players.forEach(player -> player.unsafe().sendPacket(packet));
    }

    public void setStyle(Style style) {
        this.style = style;

        if (!visible)
            return;

        net.md_5.bungee.protocol.packet.BossBar packet = new net.md_5.bungee.protocol.packet.BossBar(bossUUID, 4);
        packet.setColor(this.color.ordinal());
        packet.setDivision(this.style.ordinal());
        this.players.forEach(player -> player.unsafe().sendPacket(packet));
    }

    public void setProgress(float progress) {
        this.progress = progress;

        if (!visible)
            return;

        net.md_5.bungee.protocol.packet.BossBar packet = new net.md_5.bungee.protocol.packet.BossBar(bossUUID, 2);
        packet.setHealth(this.progress);
        this.players.forEach(player -> player.unsafe().sendPacket(packet));
    }

    public boolean hasPlayer(ProxiedPlayer player) {
        return this.players.contains(player);
    }

    public void addPlayer(ProxiedPlayer player) {
        this.players.add(player);

        if (visible && player.isConnected())
            player.unsafe().sendPacket(add());
    }

    public void removePlayer(ProxiedPlayer player) {
        this.players.remove(player);

        if (visible && player.isConnected())
            player.unsafe().sendPacket(remove());
    }

    public void removeAll() {
        new HashSet<>(this.players).forEach(this::removePlayer);
    }

    public void setVisible(boolean visible) {
        if (this.visible == visible)
            return;

        this.visible = visible;

        net.md_5.bungee.protocol.packet.BossBar packet = visible ? add() : remove();
        this.players.forEach(player -> player.unsafe().sendPacket(packet));
    }

    private net.md_5.bungee.protocol.packet.BossBar add() {
        net.md_5.bungee.protocol.packet.BossBar packet = new net.md_5.bungee.protocol.packet.BossBar(bossUUID, 0);
        packet.setTitle(new net.md_5.bungee.api.chat.TextComponent(this.serializedTitle));
        packet.setColor(this.color.ordinal());
        packet.setDivision(this.style.ordinal());
        packet.setHealth(this.progress);
        return packet;
    }

    private net.md_5.bungee.protocol.packet.BossBar remove() {
        return new net.md_5.bungee.protocol.packet.BossBar(bossUUID, 1);
    }

    public enum Color {

        PINK,
        BLUE,
        RED,
        GREEN,
        YELLOW,
        PURPLE,
        WHITE;

    }

    public enum Style {

        SOLID,
        SEGMENT_6,
        SEGMENT_10,
        SEGMENT_12,
        SEGMENT_20;

    }
}
