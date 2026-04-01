package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft._2019.utils.jedis.JedisManager;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

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
        String key = "server:" + this.server.getPluginName() + ":status";

        try (Jedis jedis = JedisManager.get()) {
            Pipeline pipeline = jedis.pipelined();
            jedis.set(key, plugin.getStatus().toString());
            jedis.expire(key, 10);

            pipeline.sync();
        }
    }
}
