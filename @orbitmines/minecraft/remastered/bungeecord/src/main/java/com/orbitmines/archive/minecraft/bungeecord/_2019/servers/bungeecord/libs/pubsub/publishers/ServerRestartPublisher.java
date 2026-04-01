package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.jedis.BungeePublisher;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import lombok.AllArgsConstructor;

import java.util.Date;

public class ServerRestartPublisher extends BungeePublisher.Simple<ServerRestartPublisher.Message> {

    private Bungeecord bungee;

    public ServerRestartPublisher(Bungeecord bungee) {
        super("server_restart", Message.class);

        this.bungee = bungee;
    }

    public void publish(String message, Date date) {
        publish(new Message(
            message,
            DateUtils.format(date, DateUtils.DATE_TIME_FORMAT)
        ));
    }

    @Override
    protected void afterPublish(Message object) {
        /* Shutdown Proxy */
        bungee.restart(object.message + " at " + object.date);
    }

    @AllArgsConstructor
    public class Message {

        String message;
        String date;

    }
}
