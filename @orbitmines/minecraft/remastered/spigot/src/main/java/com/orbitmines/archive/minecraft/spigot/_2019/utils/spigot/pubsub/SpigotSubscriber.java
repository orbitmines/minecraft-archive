package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.pubsub.Subscriber;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;

public abstract class SpigotSubscriber<E, S extends Serializer<E>> extends Subscriber<E, S> {

    public SpigotSubscriber(String channel, S serializer) {
        super(channel, serializer);
    }

    public static abstract class Simple<T> extends Subscriber.Simple<T> {

        public Simple(String channel, Class<T> clazz) {
            super(channel, clazz);
        }
    }
}
