package com.orbitmines.minecraft.spigot.servers.fog;

import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;

public class FoGChatHandler extends ChatHandler<FoG, FoGPlayer> {

    public FoGChatHandler(FoG server, Type type, PlayerInstance sender) {
        super(server, type, sender);
    }

    public FoGChatHandler(FoG server, Type type, PlayerInstance sender, String message) {
        super(server, type, sender, message);
    }
}
