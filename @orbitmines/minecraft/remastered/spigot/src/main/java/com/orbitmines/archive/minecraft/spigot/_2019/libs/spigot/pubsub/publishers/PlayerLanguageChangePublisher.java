package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotPublisher;
import lombok.AllArgsConstructor;

public class PlayerLanguageChangePublisher extends SpigotPublisher.Simple<PlayerLanguageChangePublisher.Message> {

    public PlayerLanguageChangePublisher() {
        super("player_language_change", Message.class);
    }

    public void publish(OMPlayer player, Language language) {
        publish(new Message(
            player.getUUID().toString(),
            language.toString()
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String language;

    }
}
