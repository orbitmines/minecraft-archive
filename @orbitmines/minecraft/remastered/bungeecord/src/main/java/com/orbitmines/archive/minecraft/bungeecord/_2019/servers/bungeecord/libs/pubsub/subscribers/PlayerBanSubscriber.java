package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeeSubscriber;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerBanSubscriber extends BungeeSubscriber.Simple<PlayerBanSubscriber.Message> {

    private final Bungeecord plugin;

    public PlayerBanSubscriber(Bungeecord plugin) {
        super("player_ban", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        UUID uuid = UUID.fromString(object.uuid);

        BungeePlayer player = plugin.getPlayerIfOnline(uuid);
        if (player == null)
            return;

        Punishment punishment = Punishment.findBy(Punishment.class, Punishment.column.ID.is(object.punishmentId));
        player.disconnect(BungeePlayer.getBanMessage(player.getModel(), punishment));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        Long punishmentId;

    }
}
