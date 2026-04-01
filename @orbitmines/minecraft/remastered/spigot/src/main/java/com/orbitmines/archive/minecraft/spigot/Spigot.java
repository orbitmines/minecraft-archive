package com.orbitmines.archive.minecraft.spigot;

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.Hub;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.plugin.java.JavaPlugin;

public class Spigot extends JavaPlugin {

    private SpigotServer<?> server;

    @Override
    public void onLoad() {
        Server type = Server.valueOf(Environment.get("OM_SERVER_TYPE", "HUB").toUpperCase());

        this.server = switch (type) {
            case SURVIVAL -> new Survival(this);
            case HUB -> new Hub(this);
            case KITPVP -> new KitPvP(this);
            case BUILD -> new Build(this);
            default -> throw new IllegalArgumentException("Unsupported server type: " + type);
        };

        server.onLoad();
    }

    @Override
    public void onEnable() {
        server.onEnable();
    }

    @Override
    public void onDisable() {
        server.onDisable();
    }

}
