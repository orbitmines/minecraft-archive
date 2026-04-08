package com.orbitmines.archive.minecraft.bungeecord;

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.MinecraftServer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord.commands.ServerCommand;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BungeeCord extends Plugin {

    private Bungeecord bungeecord;

    @Override
    public void onLoad() {
        Environment.BUNGEECORD = true;

        bungeecord = new Bungeecord(this);
        bungeecord.onLoad();
    }

    @Override
    public void onEnable() {
        bungeecord.onEnable();

        for (Server type : List.of(Server.HUB, Server.KITPVP, Server.SURVIVAL, Server.CREATIVE)) {
            MinecraftServer server = new MinecraftServer(type, "26.1.1", Environment.get("OM_RAM_" + type.toString(), Environment.get("OM_RAM_DEFAULT", "2G")), findAvailablePort());
            server.run();
            this.registerServer(server);
        }
    }

    @Override
    public void onDisable() {
        bungeecord.onDisable();
    }

    public void registerServer(MinecraftServer server) {
        this.getProxy().getServers().put(server.getName(), this.getProxy().constructServerInfo(server.getName(), new InetSocketAddress(server.getIp(), server.getPort()), server.getType().ordinal() + "", false));
        getProxy().getPluginManager().registerCommand(this, new ServerCommand(server));
    }

    private static int findAvailablePort() {
        for (int i = 0; i < 100; i++) {
            int port = ThreadLocalRandom.current().nextInt(25566, 30001);
            try (ServerSocket socket = new ServerSocket(port)) {
                return port;
            } catch (IOException ignored) {
            }
        }
        throw new RuntimeException("Could not find an available port in range 25566-30000");
    }

}
