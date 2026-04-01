package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeeSubscriber;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerVipGainedSubscriber extends BungeeSubscriber.Simple<PlayerVipGainedSubscriber.Message> {

    private final Bungeecord plugin;

    public PlayerVipGainedSubscriber(Bungeecord plugin) {
        super("player_vip_gained", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        UUID uuid = UUID.fromString(object.uuid);
        VipRank vipRank = VipRank.valueOf(object.vipRank);

        //TODO

        BungeePlayer player = plugin.getPlayerIfOnline(uuid);
        if (player == null)
            return;

        player.setVipRank(vipRank);
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String vipRank;

    }
}
