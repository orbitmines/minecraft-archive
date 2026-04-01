package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PingEvent implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();
        ping.setDescriptionComponent(new TextComponent(getMotd()));
        ping.getPlayers().setMax(getMaxPlayers());
    }

    private String getMotd() {
        String motd = StateProvider.getInstance().getString("motd");

        if (motd != null)
            return Color.translateAlternateColorCodes('&', motd);

        /* Fallback motd */
        return "§8§lOrbit§7§lMines §8- §71.14.1\n§7In a galaxy far, far away.";
    }

    private int getMaxPlayers() {
        return Environment.get("OM_MAX_PLAYERS", 300);
    }
}
