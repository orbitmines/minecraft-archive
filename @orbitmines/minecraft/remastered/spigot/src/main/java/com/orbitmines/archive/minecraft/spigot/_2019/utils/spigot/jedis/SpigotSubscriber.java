package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.jedis.Subscriber;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SpigotSubscriber<E, S extends Serializer<E>> extends Subscriber<E, S> {

    public SpigotSubscriber(String channel, S serializer) {
        super(channel, serializer);
    }

    @Override
    protected void subscribeAsync(Runnable runnable) {
        JavaPlugin plugin = SpigotServer.getInstance();

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static abstract class Simple<T> extends Subscriber.Simple<T> {

        public Simple(String channel, Class<T> clazz) {
            super(channel, clazz);
        }

        @Override
        protected void subscribeAsync(Runnable runnable) {
            JavaPlugin plugin = SpigotServer.getInstance();

            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
        }
    }
}
