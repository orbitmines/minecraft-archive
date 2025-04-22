package com.orbitmines.archive.minecraft.bungeecord;

import com.orbitmines.archive.minecraft.MinecraftServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;

public class BungeeCord extends Plugin implements Listener {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

        MinecraftServer server = new MinecraftServer("archive", "1.21.5", "1G", 25566);
//        server.delete();
        server.run();
        this.registerServer(server);

        getProxy().getPluginManager().registerListener(this, this);

    }

    @Override
    public void onDisable() {

    }

    public void registerServer(MinecraftServer server) {
        this.getProxy().getServers().put(server.getName(), this.getProxy().constructServerInfo(server.getName(), new InetSocketAddress(server.getIp(), server.getPort()), "0", true));
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
    }

}
