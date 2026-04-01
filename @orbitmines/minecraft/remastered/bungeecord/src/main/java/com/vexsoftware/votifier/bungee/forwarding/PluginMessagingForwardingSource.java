package com.vexsoftware.votifier.bungee.forwarding;

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.vexsoftware.votifier.bungee.forwarding.cache.VoteCache;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class PluginMessagingForwardingSource extends AbstractPluginMessagingForwardingSource implements Listener {

    public PluginMessagingForwardingSource(String channel, List<String> ignoredServers, Bungeecord nuVotifier, VoteCache cache) {
        super(channel, ignoredServers, nuVotifier, cache);
        ProxyServer.getInstance().getPluginManager().registerListener(nuVotifier.getPlugin(), this);
    }

    protected PluginMessagingForwardingSource(String channel, Bungeecord nuVotifier, VoteCache voteCache) {
        super(channel, nuVotifier, voteCache);
        ProxyServer.getInstance().getPluginManager().registerListener(nuVotifier.getPlugin(), this);
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if (e.getTag().equals(channel)) e.setCancelled(true);
    }

    @EventHandler
    public void onServerConnected(ServerConnectedEvent e) {
        handleServerConnected(e);
    }
}
