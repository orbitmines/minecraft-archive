package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.events;

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/*
* OrbitMines - @author Fadi Shawki - 29-7-2017
*/
public class PlayerChatEvent implements Listener {

    private final Bungeecord bungeecord;

    public PlayerChatEvent(Bungeecord bungeecord) {
        this.bungeecord = bungeecord;
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer))
            return;

//        String[] a = event.getMessage().split(" ");
    }
}
