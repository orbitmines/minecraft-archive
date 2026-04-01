package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotSubscriber;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerNameChangeSubscriber extends SpigotSubscriber.Simple<PlayerNameChangeSubscriber.Message> {

    public PlayerNameChangeSubscriber() {
        super("player_name_change", Message.class);

    }

    @Override
    protected void onMessage(Message object) {
        UUIDUtils.clearCacheFor(UUID.fromString(object.uuid));
        UUIDUtils.clearCacheFor(object.previous_name);
        UUIDUtils.clearCacheFor(object.new_name);
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String previous_name;
        String new_name;

    }
}
