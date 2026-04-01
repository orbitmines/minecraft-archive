package com.orbitmines.archive.minecraft._2019.utils.jedis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.GsonBuilder;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import lombok.Getter;
import redis.clients.jedis.Jedis;

public abstract class Publisher<E, S extends Serializer<E>> {

    @Getter protected final String channel;
    @Getter protected final S serializer;

    public Publisher(String channel, S serializer) {
        this.channel = channel;
        this.serializer = serializer;
    }

    protected abstract void publishAsync(Runnable runnable);

    public void publish(E object) {
        publishAsync(() -> {
            beforePublish(object);

            String message = new GsonBuilder().create().toJson(serializer.serialize(object));

            if (Environment.get() == Environment.development)
                System.out.println("[PubSub] Publishing message on channel '" + this.channel + "': '" + message + "'");

            try (Jedis jedis = JedisManager.get()) {
                jedis.publish(this.channel, message);
            }

            afterPublish(object);
        });
    }

    protected void beforePublish(E object) {}
    protected void afterPublish(E object) {}

    public abstract static class Simple<T> {

        @Getter protected final String channel;
        @Getter protected final Class<T> clazz;

        public Simple(String channel, Class<T> clazz) {
            this.channel = channel;
            this.clazz = clazz;
        }

        protected abstract void publishAsync(Runnable runnable);

        public void publish(T object) {
            publishAsync(() -> {
                beforePublish(object);

                String message = new GsonBuilder().create().toJson(object, clazz);

                if (Environment.get() == Environment.development)
                    System.out.println("[PubSub] Publishing message on channel '" + this.channel + "': '" + message + "'");

                try (Jedis jedis = JedisManager.get()) {
                    jedis.publish(this.channel, message);
                }

                afterPublish(object);
            });
        }

        protected void beforePublish(T object) {}
        protected void afterPublish(T object) {}
    }
}
