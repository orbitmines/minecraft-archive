package com.orbitmines.archive.minecraft._2019.libs.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.jedis.Publisher;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerMessagePublisher extends Publisher.Simple<PlayerMessagePublisher.Message> {

    public PlayerMessagePublisher() {
        super("player_message", Message.class);
    }

    public void publish(UUID uuid, String message) {
        publish(new Message(
            uuid.toString(),
            message
        ));
    }

    @Override
    protected void publishAsync(Runnable runnable) {
        new Thread(runnable).start();
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String message;

    }
}
