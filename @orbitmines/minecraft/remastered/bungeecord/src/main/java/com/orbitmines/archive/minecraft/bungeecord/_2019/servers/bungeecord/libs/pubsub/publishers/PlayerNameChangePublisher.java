package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.jedis.BungeePublisher;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerNameChangePublisher extends BungeePublisher.Simple<PlayerNameChangePublisher.Message> {

    public PlayerNameChangePublisher() {
        super("player_name_change", Message.class);
    }

    public void publish(UUID uuid, String previousName, String newName) {
        publish(new Message(
            uuid.toString(),
            previousName,
            newName
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String previous_name;
        String new_name;

    }
}
