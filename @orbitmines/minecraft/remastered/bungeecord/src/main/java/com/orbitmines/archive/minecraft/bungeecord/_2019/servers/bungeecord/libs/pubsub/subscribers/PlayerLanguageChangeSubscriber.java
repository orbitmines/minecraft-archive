package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeeSubscriber;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerLanguageChangeSubscriber extends BungeeSubscriber.Simple<PlayerLanguageChangeSubscriber.Message> {

    private final Bungeecord plugin;

    public PlayerLanguageChangeSubscriber(Bungeecord plugin) {
        super("player_language_change", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        UUID uuid = UUID.fromString(object.uuid);

        BungeePlayer player = plugin.getPlayerIfOnline(uuid);
        if (player == null)
            return;

        Language language = Language.valueOf(object.language);

        player.setLanguage(language);
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String language;

    }
}
