package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.pubsub.Publisher;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPublisher<E, S extends Serializer<E>> extends Publisher<E, S> {

    public SpigotPublisher(String channel, S serializer) {
        super(channel, serializer);
    }

    @Override
    protected void publishAsync(Runnable runnable) {
        JavaPlugin plugin = SpigotServer.getInstance().getPlugin();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static class Simple<T> extends Publisher.Simple<T> {

        public Simple(String channel, Class<T> clazz) {
            super(channel, clazz);
        }

        @Override
        protected void publishAsync(Runnable runnable) {
            JavaPlugin plugin = SpigotServer.getInstance().getPlugin();

            Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
        }
    }
}
