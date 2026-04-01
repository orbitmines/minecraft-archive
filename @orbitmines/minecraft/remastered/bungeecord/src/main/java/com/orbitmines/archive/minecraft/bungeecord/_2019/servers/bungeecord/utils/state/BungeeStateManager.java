package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.state;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.Gson;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.PubSubBroker;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BungeeStateManager extends StateProvider {

    private static final String STATE_SYNC_CHANNEL = "state_sync";
    private static final Gson GSON = new Gson();

    private final Map<UUID, Map<String, String>> playerData = new ConcurrentHashMap<>();
    private final Map<String, String> serverStatus = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> serverPlayers = new ConcurrentHashMap<>();
    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();
    private final Map<String, String> keyValueStore = new ConcurrentHashMap<>();

    public BungeeStateManager() {
    }

    /* Player data */

    @Override
    public Map<String, String> getPlayerData(UUID uuid) {
        Map<String, String> data = playerData.get(uuid);
        return data != null ? new HashMap<>(data) : null;
    }

    @Override
    public Set<UUID> getAllPlayerUUIDs() {
        return new HashSet<>(playerData.keySet());
    }

    @Override
    public void setPlayerData(UUID uuid, Map<String, String> data) {
        playerData.put(uuid, new ConcurrentHashMap<>(data));
        broadcastPlayerUpdate(uuid, data);
    }

    @Override
    public void setPlayerField(UUID uuid, String field, String value) {
        Map<String, String> data = playerData.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>());
        data.put(field, value);
        broadcastPlayerUpdate(uuid, data);
    }

    @Override
    public void removePlayerData(UUID uuid) {
        playerData.remove(uuid);
        broadcastPlayerRemove(uuid);
    }

    @Override
    public String getPlayerField(UUID uuid, String field) {
        Map<String, String> data = playerData.get(uuid);
        return data != null ? data.get(field) : null;
    }

    /* Server state */

    @Override
    public long getServerPlayerCount(String pluginName) {
        Set<String> players = serverPlayers.get(pluginName);
        return players != null ? players.size() : 0;
    }

    @Override
    public String getServerStatus(String pluginName) {
        return serverStatus.getOrDefault(pluginName, null);
    }

    @Override
    public void setServerStatus(String pluginName, String status) {
        serverStatus.put(pluginName, status);
        broadcastServerStatus(pluginName, status);
    }

    @Override
    public Set<String> getServerPlayers(String pluginName) {
        Set<String> players = serverPlayers.get(pluginName);
        return players != null ? new HashSet<>(players) : new HashSet<>();
    }

    @Override
    public void addServerPlayer(String pluginName, String playerName) {
        serverPlayers.computeIfAbsent(pluginName, k -> ConcurrentHashMap.newKeySet()).add(playerName);
    }

    @Override
    public void removeServerPlayer(String pluginName, String playerName) {
        Set<String> players = serverPlayers.get(pluginName);
        if (players != null)
            players.remove(playerName);
    }

    @Override
    public void clearServerPlayers(String pluginName) {
        serverPlayers.remove(pluginName);
    }

    @Override
    public boolean isServerBlacklisted(String serverName) {
        return blacklist.contains(serverName);
    }

    /* Batch operations */

    @Override
    public Map<String, String> getServerStatusBatch(Collection<String> pluginNames) {
        Map<String, String> result = new HashMap<>();
        for (String name : pluginNames) {
            String status = serverStatus.get(name);
            if (status != null)
                result.put(name, status);
        }
        return result;
    }

    @Override
    public Map<UUID, Map<String, String>> getPlayerDataBatch(Collection<UUID> uuids) {
        Map<UUID, Map<String, String>> result = new HashMap<>();
        for (UUID uuid : uuids) {
            Map<String, String> data = playerData.get(uuid);
            if (data != null)
                result.put(uuid, new HashMap<>(data));
        }
        return result;
    }

    /* Generic key-value storage */

    @Override
    public String getString(String key) {
        return keyValueStore.get(key);
    }

    @Override
    public void setString(String key, String value) {
        keyValueStore.put(key, value);
    }

    @Override
    public void deleteString(String key) {
        keyValueStore.remove(key);
    }

    /* Cleanup */

    public void clearAllPlayerData() {
        playerData.clear();
    }

    public void clearAllServerData() {
        serverStatus.clear();
        serverPlayers.clear();
    }

    /* State sync broadcasting - pushes state changes to all Spigot servers */

    private void broadcastPlayerUpdate(UUID uuid, Map<String, String> data) {
        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        Map<String, Object> message = new HashMap<>();
        message.put("type", "PLAYER_UPDATE");
        message.put("uuid", uuid.toString());
        message.put("data", data);

        broker.send(STATE_SYNC_CHANNEL, GSON.toJson(message));
    }

    private void broadcastPlayerRemove(UUID uuid) {
        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        Map<String, Object> message = new HashMap<>();
        message.put("type", "PLAYER_REMOVE");
        message.put("uuid", uuid.toString());

        broker.send(STATE_SYNC_CHANNEL, GSON.toJson(message));
    }

    private void broadcastServerStatus(String pluginName, String status) {
        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        Map<String, Object> message = new HashMap<>();
        message.put("type", "SERVER_STATUS");
        message.put("pluginName", pluginName);
        message.put("status", status);

        broker.send(STATE_SYNC_CHANNEL, GSON.toJson(message));
    }

    public void broadcastFullSync() {
        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        Map<String, Object> message = new HashMap<>();
        message.put("type", "FULL_SYNC");

        /* Serialize all player data */
        Map<String, Map<String, String>> serializedPlayers = new HashMap<>();
        for (Map.Entry<UUID, Map<String, String>> entry : playerData.entrySet()) {
            serializedPlayers.put(entry.getKey().toString(), entry.getValue());
        }
        message.put("players", serializedPlayers);
        message.put("serverStatus", new HashMap<>(serverStatus));

        /* Serialize server players */
        Map<String, List<String>> serializedServerPlayers = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : serverPlayers.entrySet()) {
            serializedServerPlayers.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        message.put("serverPlayers", serializedServerPlayers);
        message.put("blacklist", new ArrayList<>(blacklist));

        broker.send(STATE_SYNC_CHANNEL, GSON.toJson(message));
    }

}
