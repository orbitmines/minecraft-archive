package com.orbitmines.archive.minecraft._2019.libs.jedis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft._2019.libs.jedis.exception.PlayerNoLongerOnlineException;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.pubsub.publishers.PlayerMessagePublisher;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft._2019.utils.jedis.JedisManager;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.*;

@AllArgsConstructor
public class OnlinePlayer implements PlayerInstance, Languageable {

    private final UUID uuid;
    @Getter private String rawName;
    @Getter private String nickName;
    @Getter private Server server;
    @Getter private StaffRank staffRank;
    @Getter private VipRank vipRank;
    @Getter private Language language;
    @Getter private UUID lastPrivateMessage;

    public OnlinePlayer(UUID uuid, Map<String, String> map) {
        this.uuid = uuid;
        update(map);
    }

    public void update() throws PlayerNoLongerOnlineException {
        try (Jedis jedis = JedisManager.get()) {
            Map<String, String> map = jedis.hgetAll("player:" + this.uuid.toString());

            if (map == null || !map.containsKey("server") || !map.containsKey("name"))
                throw new PlayerNoLongerOnlineException();

            update(map);
        }
    }

    private void update(Map<String, String> map) {
        this.rawName = map.get("name");
        this.nickName = map.getOrDefault("nick_name", null);
        this.server = Server.valueOf(map.get("server"));
        this.staffRank = StaffRank.valueOf(map.getOrDefault("staff_rank", StaffRank.NONE.toString()));
        this.vipRank = VipRank.valueOf(map.getOrDefault("vip_rank", VipRank.NONE.toString()));
        this.language = Language.valueOf(map.getOrDefault("language", Language.ENGLISH.toString()));
        this.lastPrivateMessage = map.containsKey("last_private_message") ? UUID.fromString(map.get("last_private_message")) : null;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public DiscordUser getDiscordUser() {
        return DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(getUUID()));
    }

    @Override
    public String getName(Name name) {
        return PlayerInstance.super.getName(name);
    }

    @Override
    public Color getRankColor() {
        return PlayerInstance.super.getRankColor();
    }

    @Override
    public String getRankPrefix() {
        return PlayerInstance.super.getRankPrefix();
    }

    @Override
    public String getRankPrefix(Color color) {
        return PlayerInstance.super.getRankPrefix(color);
    }

    @Override
    public boolean isEligible(Rank rank) {
        return PlayerInstance.super.isEligible(rank);
    }

    @Override
    public boolean isEligible(StaffRank staffRank) {
        return PlayerInstance.super.isEligible(staffRank);
    }

    @Override
    public boolean isEligible(VipRank vipRank) {
        return PlayerInstance.super.isEligible(vipRank);
    }


    /*

        Language

     */
    @Override
    public void sendMessage(String namespace, String key) {
        sendMessage(
            translate(namespace, key)
        );
    }

    @Override
    public void sendMessage(String namespace, String key, Object... args) {
        sendMessage(
            translate(namespace, key, args)
        );
    }

    @Override
    public void sendMessage(String prefix, Color color, String key) {
        sendMessage(Message.format(prefix, color,
            translate(key)
        ));
    }

    @Override
    public void sendRawMessage(String prefix, Color color, String message) {
        sendMessage(
            Message.format(prefix, color, message)
        );
    }

    @Override
    public void sendMessage(String prefix, Color color, String key, Object... args) {
        sendMessage(Message.format(prefix, color,
            translate(key, args)
        ));
    }

    @Override
    public void sendMessage(String prefix, Color color, String namespace, String key) {
        sendMessage(Message.format(prefix, color,
            translate(namespace, key)
        ));
    }

    @Override
    public void sendMessage(String prefix, Color color, String namespace, String key, Object... args) {
        sendMessage(Message.format(prefix, color,
            translate(namespace, key, args)
        ));
    }

    private void sendMessage(String message) {
        new PlayerMessagePublisher().publish(this.uuid, message);
    }

    public static OnlinePlayer get(UUID uuid) {
        try (Jedis jedis = JedisManager.get()) {
            Map<String, String> map = jedis.hgetAll("player:" + uuid.toString());

            if (map == null || !map.containsKey("server"))
                return null;

            return new OnlinePlayer(uuid, map);
        }
    }

    public static Map<Server, List<OnlinePlayer>> getAllByServer(PlayerInstance playerInstance) {
        Map<Server, List<OnlinePlayer>> players = new HashMap<>();

        for (OnlinePlayer player : getAll(playerInstance)) {
            players.computeIfAbsent(player.getServer(), key -> new ArrayList<>()).add(player);
        }

        /* Also show empty servers */
        for (Server server : (playerInstance != null ? Server.getAllowed(playerInstance) : Server.playable())) {
            if (!players.containsKey(server))
                players.put(server, new ArrayList<>());
        }

        return players;
    }

    public static List<OnlinePlayer> getAll(PlayerInstance player) {
        return getAll(player, null);
    }

    public static List<OnlinePlayer> getAll(PlayerInstance player, Integer limit) {
        Set<String> keys = JedisManager.scanAll("player:*", limit);

        Map<UUID, Response<Map<String, String>>> response = new HashMap<>();

        try (Jedis jedis = JedisManager.get()) {
            Pipeline pipeline = jedis.pipelined();

            for (String key : keys) {
                UUID uuid = UUID.fromString(key.split(":")[1]);

                response.put(uuid, pipeline.hgetAll("player:" + uuid.toString()));
            }

            pipeline.sync();
        }

        return fromResponse(player, response);
    }

    public static Map<Friend, OnlinePlayer> getFromFriendList(List<Friend> friends) {
        Map<Friend, Response<Map<String, String>>> response = new HashMap<>();

        /* Get Player server status from Redis through Pipeline */
        try (Jedis jedis = JedisManager.get()) {
            Pipeline pipeline = jedis.pipelined();

            for (Friend friend : friends) {
                response.put(friend, pipeline.hgetAll("player:" + friend.getFriendUuid().toString()));
            }

            pipeline.sync();
        }

        Map<Friend, OnlinePlayer> players = new HashMap<>();

        for (Friend friend : response.keySet()) {
            Map<String, String> map = response.get(friend).get();

            if (map == null || !map.containsKey("server") || !map.containsKey("name"))
                continue;

            players.put(friend, new OnlinePlayer(friend.getFriendUuid(), map));
        }

        return players;
    }

    public static Map<UUID, OnlinePlayer> getFromUUIDList(List<UUID> friends) {
        Map<UUID, Response<Map<String, String>>> response = new HashMap<>();

        /* Get Player server status from Redis through Pipeline */
        try (Jedis jedis = JedisManager.get()) {
            Pipeline pipeline = jedis.pipelined();

            for (UUID uuid : friends) {
                response.put(uuid, pipeline.hgetAll("player:" + uuid.toString()));
            }

            pipeline.sync();
        }

        Map<UUID, OnlinePlayer> players = new HashMap<>();

        for (UUID uuid : response.keySet()) {
            Map<String, String> map = response.get(uuid).get();

            if (map == null || !map.containsKey("server") || !map.containsKey("name"))
                continue;

            players.put(uuid, new OnlinePlayer(uuid, map));
        }

        return players;
    }

    private static List<OnlinePlayer> fromResponse(PlayerInstance player, Map<UUID, Response<Map<String, String>>> response) {
        List<OnlinePlayer> players = new ArrayList<>();

        for (UUID uuid : response.keySet()) {
            Map<String, String> map = response.get(uuid).get();

            if (map == null || !map.containsKey("server") || !map.containsKey("name"))
                continue;

            players.add(new OnlinePlayer(uuid, map));
        }

        if (player == null)
            return players;

        Collection<Server> allowed = Server.getAllowed(player);

        players.removeIf(onlinePlayer -> !allowed.contains(onlinePlayer.server));

        return players;
    }
}
