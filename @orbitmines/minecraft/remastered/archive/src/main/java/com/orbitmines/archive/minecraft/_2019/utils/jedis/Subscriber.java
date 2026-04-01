package com.orbitmines.archive.minecraft._2019.utils.jedis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public abstract class Subscriber<E, S extends Serializer<E>> extends JedisPubSub implements SubscriberInstance {

    @Getter protected final String channel;
    @Getter protected final S serializer;

    public Subscriber(String channel, S serializer) {
        this.channel = channel;
        this.serializer = serializer;
    }

    protected abstract void onMessage(E object);

    protected abstract void subscribeAsync(Runnable runnable);

    @Override
    public void subscribe() {
        subscribeAsync(() -> {
            try (Jedis jedis = JedisManager.get()) {
                jedis.subscribe(this, this.channel);
            }
        });
    }

    @Override
    public void onMessage(String channel, String message) {
        E object = this.serializer.deserialize(new JsonParser().parse(message));

        if (Environment.get() == Environment.development)
            System.out.println("[PubSub] Received message in channel '" + channel + "': '" + message + "'");

        onMessage(object);
    }

    public static abstract class Simple<T> extends JedisPubSub implements SubscriberInstance {

        @Getter protected final String channel;
        @Getter protected final Class<T> clazz;

        public Simple(String channel, Class<T> clazz) {
            this.channel = channel;
            this.clazz = clazz;
        }

        protected abstract void onMessage(T object);

        protected abstract void subscribeAsync(Runnable runnable);

        @Override
        public void subscribe() {
            subscribeAsync(() -> {
                try (Jedis jedis = JedisManager.get()) {
                    jedis.subscribe(this, this.channel);
                }
            });
        }

        @Override
        public void onMessage(String channel, String message) {
            T object = new Gson().fromJson(message, clazz);

            if (Environment.get() == Environment.development)
                System.out.println("[PubSub] Received message in channel '" + channel + "': '" + message + "'");

            onMessage(object);
        }
    }
}
