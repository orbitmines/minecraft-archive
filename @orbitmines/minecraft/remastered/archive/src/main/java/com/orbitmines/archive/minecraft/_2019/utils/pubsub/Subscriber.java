package com.orbitmines.archive.minecraft._2019.utils.pubsub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import lombok.Getter;

public abstract class Subscriber<E, S extends Serializer<E>> implements SubscriberInstance {

    @Getter protected final String channel;
    @Getter protected final S serializer;

    public Subscriber(String channel, S serializer) {
        this.channel = channel;
        this.serializer = serializer;
    }

    protected abstract void onMessage(E object);

    @Override
    public void subscribe() {
        PubSubBroker.getInstance().registerHandler(this.channel, (ch, message) -> {
            E object = this.serializer.deserialize(new JsonParser().parse(message));

            if (Environment.get() == Environment.development)
                System.out.println("[PubSub] Received message in channel '" + ch + "': '" + message + "'");

            onMessage(object);
        });
    }

    public static abstract class Simple<T> implements SubscriberInstance {

        @Getter protected final String channel;
        @Getter protected final Class<T> clazz;

        public Simple(String channel, Class<T> clazz) {
            this.channel = channel;
            this.clazz = clazz;
        }

        protected abstract void onMessage(T object);

        @Override
        public void subscribe() {
            PubSubBroker.getInstance().registerHandler(this.channel, (ch, message) -> {
                T object = new Gson().fromJson(message, clazz);

                if (Environment.get() == Environment.development)
                    System.out.println("[PubSub] Received message in channel '" + ch + "': '" + message + "'");

                onMessage(object);
            });
        }
    }
}
