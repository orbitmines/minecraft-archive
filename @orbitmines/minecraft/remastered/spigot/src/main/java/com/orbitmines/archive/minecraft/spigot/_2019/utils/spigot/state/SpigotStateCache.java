package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.state;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orbitmines.archive.minecraft._2019.libs.database.models.StateEntry;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.PubSubBroker;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SpigotStateCache extends StateProvider {

    private static final String STATE_SYNC_CHANNEL = "state_sync";
    private static final Gson GSON = new Gson();

    private final Map<UUID, Map<String, String>> playerData = new ConcurrentHashMap<>();
    private final Map<String, String> serverStatus = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> serverPlayers = new ConcurrentHashMap<>();
    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    /* Register handler for state sync messages from BungeeCord */
    public void registerStateSyncHandler() {
        PubSubBroker broker = PubSubBroker.getInstance();
        if (broker == null)
            return;

        broker.registerHandler(STATE_SYNC_CHANNEL, (channel, jsonMessage) -> {
            Map<String, Object> message = GSON.fromJson(jsonMessage, new TypeToken<Map<String, Object>>(){}.getType());
            String type = (String) message.get("type");

            switch (type) {
                case "PLAYER_UPDATE": {
                    UUID uuid = UUID.fromString((String) message.get("uuid"));
                    @SuppressWarnings("unchecked")
                    Map<String, String> data = (Map<String, String>) message.get("data");
                    setPlayerData(uuid, data);
                    break;
                }
                case "PLAYER_REMOVE": {
                    UUID uuid = UUID.fromString((String) message.get("uuid"));
                    removePlayerData(uuid);
                    break;
                }
                case "SERVER_STATUS": {
                    String pluginName = (String) message.get("pluginName");
                    String status = (String) message.get("status");
                    setServerStatus(pluginName, status);
                    break;
                }
                case "FULL_SYNC": {
                    handleFullSync(message);
                    break;
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void handleFullSync(Map<String, Object> message) {
        /* Sync player data */
        Map<String, Map<String, String>> players = (Map<String, Map<String, String>>) message.get("players");
        if (players != null) {
            for (Map.Entry<String, Map<String, String>> entry : players.entrySet()) {
                setPlayerData(UUID.fromString(entry.getKey()), entry.getValue());
            }
        }

        /* Sync server statuses */
        Map<String, String> statuses = (Map<String, String>) message.get("serverStatus");
        if (statuses != null) {
            for (Map.Entry<String, String> entry : statuses.entrySet()) {
                setServerStatus(entry.getKey(), entry.getValue());
            }
        }

        /* Sync server players */
        Map<String, List<String>> serverPlayersList = (Map<String, List<String>>) message.get("serverPlayers");
        if (serverPlayersList != null) {
            for (Map.Entry<String, List<String>> entry : serverPlayersList.entrySet()) {
                clearServerPlayers(entry.getKey());
                for (String player : entry.getValue()) {
                    addServerPlayer(entry.getKey(), player);
                }
            }
        }
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
    }

    @Override
    public void setPlayerField(UUID uuid, String field, String value) {
        playerData.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>()).put(field, value);
    }

    @Override
    public void removePlayerData(UUID uuid) {
        playerData.remove(uuid);
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

    /* Generic key-value storage - database-backed via StateEntry */

    @Override
    public String getString(String key) {
        return StateEntry.get(key);
    }

    @Override
    public void setString(String key, String value) {
        StateEntry.set(key, value);
    }

    @Override
    public void deleteString(String key) {
        StateEntry.remove(key);
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
}
