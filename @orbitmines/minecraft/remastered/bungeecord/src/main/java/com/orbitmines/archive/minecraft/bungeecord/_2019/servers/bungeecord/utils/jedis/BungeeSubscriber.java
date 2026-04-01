package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.jedis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft._2019.utils.jedis.Subscriber;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class BungeeSubscriber<E, S extends Serializer<E>> extends Subscriber<E, S> {

    public BungeeSubscriber(String channel, S serializer) {
        super(channel, serializer);
    }

    @Override
    protected void subscribeAsync(Runnable runnable) {
        Plugin plugin = Bungeecord.getInstance();

        plugin.getProxy().getScheduler().runAsync(plugin, runnable);
    }

    public static abstract class Simple<T> extends Subscriber.Simple<T> {

        public Simple(String channel, Class<T> clazz) {
            super(channel, clazz);
        }

        @Override
        protected void subscribeAsync(Runnable runnable) {
            Plugin plugin = Bungeecord.getInstance();

            plugin.getProxy().getScheduler().runAsync(plugin, runnable);
        }
    }
}
