package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent<S extends OMServer<S, P>, P extends OMPlayer<S, P>> implements Listener {

    private final S server;

    public PlayerChatEvent(S server) {
        this.server = server;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        OMPlayer player = server.getPlayer(event.getPlayer());

        event.setCancelled(true);

        System.out.println("[CHAT] (" + player.getUUID().toString() + ") " + player.getRawName() + ": " + event.getMessage());

        if (Punishment.getActive(player.getUUID(), Punishment.Type.MUTE) != null) {
            player.sendMessage("Mute", Color.ERROR, "spigot", "player.muted");
            return;
        }

        String message = event.getMessage();

        if (message.startsWith("@") && player.isEligible(StaffRank.PROVISIONAL_BUILDER)) {
            if (event.getMessage().length() == 1) {
                player.sendMessage("Staff", Color.ERROR, "spigot", "player.group_chat", "§9@<message>§7");
                return;
            }

            server.newChatHandler(player, ChatHandler.Type.STAFF_CHAT, message.substring(1)).handleChatMessage();
            return;
        } else if (message.startsWith("!")) {
            if (server.getDiscordBot() == null) {
                player.sendMessage("Discord", Color.ERROR, "spigot", "discord.not_enabled");
                return;
            }

            if (event.getMessage().length() == 1) {
                player.sendMessage("Discord", Color.ERROR, "spigot", "player.group_chat", "§9!<message>§7");
                return;
            }

            DiscordSquad squad = DiscordSquad.getSelected(player.getUUID());

            if (squad == null) {
                player.sendMessage("Discord", Color.ERROR, "spigot", "player.discord_squad.none_selected");
                return;
            }

            ChatHandler chatHandler = server.newChatHandler(player, ChatHandler.Type.DISCORD_SQUAD, message.substring(1));
            chatHandler.setSquad(squad);
            chatHandler.handleChatMessage();
            return;
        }

        server.newChatHandler(player, ChatHandler.Type.NORMAL, event.getMessage()).handleChatMessage();
    }
}
