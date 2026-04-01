package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotPublisher;
import lombok.AllArgsConstructor;

public class PlayerNicknameChangePublisher extends SpigotPublisher.Simple<PlayerNicknameChangePublisher.Message> {

    public PlayerNicknameChangePublisher() {
        super("player_nickname_change", Message.class);
    }

    public void publish(OMPlayer player, String nickName) {
        publish(new Message(
            player.getUUID().toString(),
            nickName
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String nickName;

    }
}
