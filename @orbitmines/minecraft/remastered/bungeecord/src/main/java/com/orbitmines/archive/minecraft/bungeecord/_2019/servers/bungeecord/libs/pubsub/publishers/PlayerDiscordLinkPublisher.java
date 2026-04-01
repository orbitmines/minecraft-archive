package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.jedis.BungeePublisher;
import lombok.AllArgsConstructor;

public class PlayerDiscordLinkPublisher extends BungeePublisher.Simple<PlayerDiscordLinkPublisher.Message> {

    public PlayerDiscordLinkPublisher() {
        super("player_discord_link", Message.class);
    }

    public void publish(DiscordUser user) {
        publish(new Message(
            user.getUuid().toString(),
            user.getDiscordUserId()
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        Long discord_user_id;

    }
}
