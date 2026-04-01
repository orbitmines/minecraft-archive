package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.Donation;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.jedis.BungeePublisher;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerDonationPublisher extends BungeePublisher.Simple<PlayerDonationPublisher.Message> {

    public PlayerDonationPublisher() {
        super("player_donation", Message.class);
    }

    public void publish(UUID uuid, Donation donation) {
        publish(new Message(
            uuid.toString(),
            donation.getId()
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        Long id;

    }
}
