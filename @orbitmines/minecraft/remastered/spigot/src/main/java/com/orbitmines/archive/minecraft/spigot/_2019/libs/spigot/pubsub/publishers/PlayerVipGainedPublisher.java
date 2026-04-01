package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotPublisher;
import lombok.AllArgsConstructor;

public class PlayerVipGainedPublisher extends SpigotPublisher.Simple<PlayerVipGainedPublisher.Message> {

    public PlayerVipGainedPublisher() {
        super("player_vip_gained", Message.class);
    }

    public void publish(OMPlayer player, VipRank vipRank) {
        publish(new Message(
            player.getUniqueId().toString(),
            vipRank.toString()
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String vipRank;

    }
}
