package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.jedis.BungeeSubscriber;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

public class PlayerMessageSubscriber extends BungeeSubscriber.Simple<PlayerMessageSubscriber.Message> {

    private final Bungeecord plugin;

    public PlayerMessageSubscriber(Bungeecord plugin) {
        super("player_message", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        UUID uuid = UUID.fromString(object.uuid);

        BungeePlayer player = plugin.getPlayerIfOnline(uuid);
        if (player == null)
            return;

        player.sendMessage(new TextComponent(object.message));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String message;

    }
}
