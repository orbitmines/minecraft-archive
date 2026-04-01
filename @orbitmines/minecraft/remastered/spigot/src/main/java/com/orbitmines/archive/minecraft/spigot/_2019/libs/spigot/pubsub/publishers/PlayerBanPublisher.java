package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotPublisher;
import lombok.AllArgsConstructor;

public class PlayerBanPublisher extends SpigotPublisher.Simple<PlayerBanPublisher.Message> {

    public PlayerBanPublisher() {
        super("player_ban", Message.class);
    }

    public void publish(Punishment punishment) {
        publish(new Message(
            punishment.getUuid().toString(),
            punishment.getId()
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        Long punishmentId;

    }
}
