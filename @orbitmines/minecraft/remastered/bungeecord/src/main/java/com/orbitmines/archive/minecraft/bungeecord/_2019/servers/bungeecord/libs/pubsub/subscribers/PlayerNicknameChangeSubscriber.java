package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeeSubscriber;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerNicknameChangeSubscriber extends BungeeSubscriber.Simple<PlayerNicknameChangeSubscriber.Message> {

    private final Bungeecord plugin;

    public PlayerNicknameChangeSubscriber(Bungeecord plugin) {
        super("player_nickname_change", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        UUID uuid = UUID.fromString(object.uuid);

        BungeePlayer player = plugin.getPlayerIfOnline(uuid);
        if (player == null)
            return;

        player.setNickName(object.nickName);
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String nickName;

    }
}
