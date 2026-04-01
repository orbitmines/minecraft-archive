package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.EmptyAchievementHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.HubAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotSubscriber;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerDiscordLinkSubscriber extends SpigotSubscriber.Simple<PlayerDiscordLinkSubscriber.Message> {

    private final OMServer<?, ? extends OMPlayer> plugin;

    public PlayerDiscordLinkSubscriber(OMServer<?, ? extends OMPlayer> plugin) {
        super("player_discord_link", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        OMPlayer player = plugin.getPlayer(UUID.fromString(object.uuid));

        if (player == null)
            return;

        EmptyAchievementHandler handler = (EmptyAchievementHandler) HubAchievement.DISCORD_LINK.getHandler();
        handler.complete(player, true);

        player.reloadDiscordUser();
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        Long discord_user_id;

    }
}
