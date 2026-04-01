package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.pubsub.Subscriber;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;

public abstract class BungeeSubscriber<E, S extends Serializer<E>> extends Subscriber<E, S> {

    public BungeeSubscriber(String channel, S serializer) {
        super(channel, serializer);
    }

    public static abstract class Simple<T> extends Subscriber.Simple<T> {

        public Simple(String channel, Class<T> clazz) {
            super(channel, clazz);
        }
    }
}
