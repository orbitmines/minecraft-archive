package com.orbitmines.archive.minecraft._2019.utils.pubsub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class PubSubBroker {

    private static PubSubBroker instance;

    private final Map<String, List<MessageHandler>> handlers = new ConcurrentHashMap<>();

    public static void initialize(PubSubBroker broker) {
        instance = broker;
    }

    public static PubSubBroker getInstance() {
        return instance;
    }

    public abstract void send(String channel, String jsonMessage);

    public void registerHandler(String channel, MessageHandler handler) {
        handlers.computeIfAbsent(channel, k -> new CopyOnWriteArrayList<>()).add(handler);
    }

    public void dispatch(String channel, String jsonMessage) {
        List<MessageHandler> list = handlers.get(channel);

        if (list != null)
            for (MessageHandler h : list)
                h.onMessage(channel, jsonMessage);
    }

    @FunctionalInterface
    public interface MessageHandler {

        void onMessage(String channel, String message);

    }
}
