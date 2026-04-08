package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.chat;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;

public class CreativeChatHandler extends ChatHandler<Creative, CreativePlayer> {

    public CreativeChatHandler(Creative server, Type type, PlayerInstance sender) {
        super(server, type, sender);
    }

    public CreativeChatHandler(Creative server, Type type, PlayerInstance sender, String message) {
        super(server, type, sender, message);
    }
}
