package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.state;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.Gson;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.PubSubBroker;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;

import java.util.*;

public class BungeeStateManager extends StateProvider {

    private static final String STATE_SYNC_CHANNEL = "state_sync";
    private static final Gson GSON = new Gson();

    @Override
    public void setPlayerData(UUID uuid, Map<String, String> data) {
        super.setPlayerData(uuid, data);
        broadcastStateChange("PLAYER_UPDATE", uuid.toString());
    }

    @Override
    public void setPlayerField(UUID uuid, String field, String value) {
        super.setPlayerField(uuid, field, value);
        broadcastStateChange("PLAYER_UPDATE", uuid.toString());
    }

    @Override
    public void removePlayerData(UUID uuid) {
        super.removePlayerData(uuid);
        broadcastStateChange("PLAYER_REMOVE", uuid.toString());
    }

    @Override
    public void setServerStatus(String pluginName, String status) {
        super.setServerStatus(pluginName, status);

        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        Map<String, Object> message = new HashMap<>();
        message.put("type", "SERVER_STATUS");
        message.put("pluginName", pluginName);
        message.put("status", status);
        broker.send(STATE_SYNC_CHANNEL, GSON.toJson(message));
    }

    /* State sync broadcasting — notifies Spigot servers of state changes */

    private void broadcastStateChange(String type, String uuid) {
        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        Map<String, Object> message = new HashMap<>();
        message.put("type", type);
        message.put("uuid", uuid);
        broker.send(STATE_SYNC_CHANNEL, GSON.toJson(message));
    }

    public void broadcastFullSync() {
        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        Map<String, Object> message = new HashMap<>();
        message.put("type", "FULL_SYNC");
        broker.send(STATE_SYNC_CHANNEL, GSON.toJson(message));
    }
}
