package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeeSubscriber;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

public class PlayerKickSubscriber extends BungeeSubscriber.Simple<PlayerKickSubscriber.Message> {

    private final Bungeecord plugin;

    public PlayerKickSubscriber(Bungeecord plugin) {
        super("player_kick", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        UUID uuid = UUID.fromString(object.uuid);

        BungeePlayer player = plugin.getPlayerIfOnline(uuid);
        if (player == null)
            return;

        player.disconnect(new TextComponent(object.message));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String message;

    }
}
