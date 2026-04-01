package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.UUID;

public class BungeePlayerEvents implements Listener {

    private final Bungeecord plugin;

    public BungeePlayerEvents(Bungeecord plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(PreLoginEvent event) {
        String name = event.getConnection().getName();
        UUID uuid = UUIDUtils.getUUID(name);

        if (uuid == null)
            return;

        /* Check if player is banned */
        Punishment activeBan = Punishment.getActive(uuid, Punishment.Type.BAN);
        if (activeBan == null)
            return;

        PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(uuid));

        event.setCancelled(true);
        event.setCancelReason(BungeePlayer.getBanMessage(model, activeBan));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPostLogin(PostLoginEvent event) {
        plugin.triggerJoinEvent(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerDisconnectEvent event) {
        BungeePlayer player = plugin.getPlayer(event.getPlayer());
        player.processQuitEvent(event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onConnect(ServerConnectEvent event) {
        BungeePlayer player = plugin.getPlayer(event.getPlayer());

        if (player.getServer() != null) {
            /* When switching server */
            return;
        }

        /* Failed to connect, try to connect to another server */
        ServerInfo fallbackServer = player.getFallbackServer();
        if (fallbackServer == null) {
            player.disconnect(TextComponent.fromLegacyText(
                    "§8§lOrbit§7§lMines\n§7" + player.getLanguage().getString("connection.no_fallback_server")
            ));
            return;
        }

        /* Redirect to fallback server */
        event.setTarget(fallbackServer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKick(ServerKickEvent event) {
        BungeePlayer player = plugin.getPlayer(event.getPlayer());

        /* Server crashed, send to fallback server if possible */
        ServerInfo fallbackServer = player.getFallbackServer();
        if (fallbackServer == null) {
            player.disconnect(TextComponent.fromLegacyText(
                    "§8§lOrbit§7§lMines\n§7" + player.getLanguage().getString("connection.no_fallback_server")
            ));
            return;
        }

        /* Redirect to fallback server */
        event.setCancelled(true);
        event.setCancelServer(fallbackServer);
    }
}
