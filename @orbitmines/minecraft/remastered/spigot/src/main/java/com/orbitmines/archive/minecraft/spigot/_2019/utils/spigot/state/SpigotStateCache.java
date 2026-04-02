package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.state;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.PubSubBroker;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;

import java.util.*;

public class SpigotStateCache extends StateProvider {

    private static final String STATE_SYNC_CHANNEL = "state_sync";
    private static final Gson GSON = new Gson();

    /* Register handler for state sync notifications from BungeeCord */
    public void registerStateSyncHandler() {
        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        broker.registerHandler(STATE_SYNC_CHANNEL, (channel, jsonMessage) -> {
            /* State changes are already persisted in the shared database by BungeeStateManager.
               PubSub notifications can be used for cache invalidation if needed in the future. */
        });
    }
}
