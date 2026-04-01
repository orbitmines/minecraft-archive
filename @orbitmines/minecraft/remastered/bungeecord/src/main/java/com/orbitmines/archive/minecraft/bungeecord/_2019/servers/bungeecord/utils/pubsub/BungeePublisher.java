package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.Publisher;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePublisher<E, S extends Serializer<E>> extends Publisher<E, S> {

    public BungeePublisher(String channel, S serializer) {
        super(channel, serializer);
    }

    @Override
    protected void publishAsync(Runnable runnable) {
        Plugin plugin = Bungeecord.getInstance();

        plugin.getProxy().getScheduler().runAsync(plugin, runnable);
    }

    public static class Simple<T> extends Publisher.Simple<T> {

        public Simple(String channel, Class<T> clazz) {
            super(channel, clazz);
        }

        @Override
        protected void publishAsync(Runnable runnable) {
            Plugin plugin = Bungeecord.getInstance();

            plugin.getProxy().getScheduler().runAsync(plugin, runnable);
        }
    }
}
