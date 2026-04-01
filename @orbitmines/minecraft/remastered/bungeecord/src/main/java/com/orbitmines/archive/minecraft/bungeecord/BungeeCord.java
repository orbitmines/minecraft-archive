package com.orbitmines.archive.minecraft.bungeecord;

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.MinecraftServer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord.commands.ServerCommand;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.InetSocketAddress;

public class BungeeCord extends Plugin {

    private Bungeecord bungeecord;

    @Override
    public void onLoad() {
        bungeecord = new Bungeecord(this);
        bungeecord.onLoad();
    }

    @Override
    public void onEnable() {
        bungeecord.onEnable();

        MinecraftServer server = new MinecraftServer(Server.HUB, "26.1", "1G", 25566);
//        server.delete();
        server.run();
        this.registerServer(server);
    }

    @Override
    public void onDisable() {
        bungeecord.onDisable();
    }

    public void registerServer(MinecraftServer server) {
        this.getProxy().getServers().put(server.getName(), this.getProxy().constructServerInfo(server.getName(), new InetSocketAddress(server.getIp(), server.getPort()), "0", true));
        getProxy().getPluginManager().registerCommand(this, new ServerCommand(server));
    }

}
