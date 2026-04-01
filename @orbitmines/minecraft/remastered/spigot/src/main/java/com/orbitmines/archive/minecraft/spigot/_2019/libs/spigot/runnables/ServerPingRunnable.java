package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;

public class ServerPingRunnable extends SpigotRunnable<OMServer> {

    private final Server server;

    public ServerPingRunnable(OMServer plugin, Server server) {
        super(plugin, Interval.of(TimeUnit.SECOND, 2));

        this.server = server;
    }

    @Override
    public void run() {
        pingStatus();
    }

    private void pingStatus() {
        StateProvider.getInstance().setServerStatus(this.server.getPluginName(), super.server.getStatus().toString());
    }
}
