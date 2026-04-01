package com.orbitmines.archive.minecraft._2019.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft._2019.utils.jedis.JedisManager;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.*;

public enum Server {

    /* Sorted on connection priority */
    BUNGEECORD(Color.BLUE, "Bungeecord", "bungeecord", -1, null),
    HUB(Color.TEAL, "Hub", "hub", 100, VipRank.NONE),
    SURVIVAL(Color.LIME, "Survival", "survival", 100, VipRank.NONE),
    KITPVP(Color.RED, "KitPvP", "kitpvp", 100, VipRank.NONE),
    BUILD(Color.FUCHSIA, "Build", "build", 100, StaffRank.PROVISIONAL_BUILDER);
//    PRISON(Color.MAROON, "Prison", "prison", 100),
//    CREATIVE(Color.FUCHSIA, "Creative", "creative", 100),
//    SKYBLOCK(Color.PURPLE, "SkyBlock", "skyblock", 100),
//    FOG(Color.YELLOW, "Fractals of the Galaxy", "fog", 100),
//    MINIGAMES(Color.WHITE, "Minigames", "minigames", 100),
//    UHSURVIVAL(Color.GREEN, "UHSurvival", "uhsurvival", 100);

    public static final String SERVER_DISPLAY_NAME = "§8§lOrbit§7§lMines";

    @Getter private final Color color;
    @Getter private final String name;
    @Getter private final String pluginName;
    @Getter private final int maxPlayers;
    @Getter private Rank rank;

    Server(Color color, String name, String pluginName, int maxPlayers) {
        this(color, name, pluginName, maxPlayers, VipRank.NONE);
    }

    Server(Color color, String name, String pluginName, int maxPlayers, Rank rank) {
        this.color = color;
        this.name = name;
        this.pluginName = pluginName;
        this.maxPlayers = maxPlayers;
        this.rank = rank;
    }

    public String getDisplayName() {
        return this.color.getCc() + "§l" + this.name;
    }

    public long getOnline() {
        try (Jedis jedis = JedisManager.get()) {
            return jedis.scard(jedisNamespace() + ":players");
        }
    }

    public Status getStatus() {
        try (Jedis jedis = JedisManager.get()) {
            String status = jedis.get(jedisNamespace() + ":status");

            return status != null ? Status.valueOf(status) : Status.OFFLINE;
        }
    }

    public void setStatus(Status status) {
        try (Jedis jedis = JedisManager.get()) {
            jedis.set(jedisNamespace() + ":status", status.toString());
        }
    }

    /* Returns list of players currently online */
    public Set<String> getPlayers() {
        try (Jedis jedis = JedisManager.get()) {
            return jedis.smembers(jedisNamespace() + ":players");
        }
    }

    public long getPlayerCount() {
        try (Jedis jedis = JedisManager.get()) {
            Long players = jedis.scard(jedisNamespace() + ":players");

            return players != null ? players : 0;
        }
    }

    public boolean isBlacklisted() {
        try (Jedis jedis = JedisManager.get()) {
            Boolean isMember = jedis.sismember("server:blacklist", toString());

            return isMember != null && isMember;
        }
    }

    private String jedisNamespace() {
        return "server:" + this.pluginName;
    }

    public static Server getFromPluginName(String pluginName) {
        for (Server server : Server.values()) {
            if (server.getPluginName().equals(pluginName))
                return server;
        }
        return null;
    }

    public static Server getPlayingOn(UUID uuid) {
        return getPlayingOn(UUIDUtils.getName(uuid));
    }

    public static Server getPlayingOn(String playerName) {
        try (Jedis jedis = JedisManager.get()) {
            String server = jedis.hget("player:" + playerName, "server");

            return server != null ? Server.valueOf(server) : null;
        }
    }

    /* Returns list of Servers which are currently active */
    public static Collection<Server> getAllowed(PlayerInstance player) {
        List<Server> active = new ArrayList<>();
        for (Server server : values()) {
            if (server.getRank() != null && player.isEligible(server.getRank()))
                active.add(server);
        }
        return active;
    }
    public static Collection<Server> getAllowed(PlayerInstance player, int limit) {
        List<Server> active = new ArrayList<>();
        for (Server server : values()) {
            if (server.getRank() != null && player.isEligible(server.getRank()))
                active.add(server);

            if (active.size() == limit)
                break;
        }
        return active;
    }
    private static Collection<Server> stringCollectionToServer(Collection<String> stringedServers) {
        List<Server> servers = new ArrayList<>();
        for (String server : stringedServers) {
            servers.add(Server.valueOf(server));
        }

        return servers;
    }

    /* Returns list of servers with the given status */
    public static Collection<Server> getWith(PlayerInstance player, Status... statuses) {
        try (Jedis jedis = JedisManager.get()) {
            Pipeline pipeline = jedis.pipelined();
            Map<Server, Response<String>> responses = new HashMap<>();

            for (Server server : getAllowed(player)) {
                responses.put(server, pipeline.get(server.jedisNamespace() + ":status"));
            }
            pipeline.sync();

            List<Server> servers = new ArrayList<>();
            for (Server server : responses.keySet()) {
                String statusString = responses.get(server).get();
                Status status = statusString != null ? Status.valueOf(statusString) : Status.OFFLINE;

                for (Status s : statuses) {
                    if (status != s)
                        continue;

                    servers.add(server);
                    break;
                }
            }

            return servers;
        }
    }

    /* Returns list of player names that are currently online */
    public static Set<String> getAllPlayers(PlayerInstance player) {
        Collection<Server> servers = getWith(player, Status.ONLINE, Status.MAINTENANCE);

        try (Jedis jedis = JedisManager.get()) {
            Pipeline pipeline = jedis.pipelined();
            List<Response<Set<String>>> responses = new ArrayList<>();

            for (Server server : servers) {
                responses.add(pipeline.smembers(server.jedisNamespace() + ":players"));
            }
            pipeline.sync();

            Set<String> players = new HashSet<>();
            for (Response<Set<String>> response : responses) {
                players.addAll(response.get());
            }

            return players;
        }
    }

    public static Set<Server> playable() {
        Set<Server> servers = new HashSet<>(Arrays.asList(values()));
        servers.remove(BUNGEECORD);
        servers.remove(BUILD);

        return servers;
    }

    //
//    HUB("Hub", Color.TEAL, HubAchievements.class, TableTimePlayed.HUB),
//    SURVIVAL("Survival", Color.LIME, SurvivalAchievements.class, TableTimePlayed.SURVIVAL),
//    KITPVP("KitPvP", Color.RED, KitPvPAchievements.class, TableTimePlayed.KITPVP),
//
//    PRISON("Prison", Color.MAROON, PrisonAchievements.class, TableTimePlayed.PRISON),
//    CREATIVE("Creative", Color.FUCHSIA, CreativeAchievements.class, TableTimePlayed.CREATIVE),
//    SKYBLOCK("SkyBlock", Color.PURPLE, SkyBlockAchievements.class, TableTimePlayed.SKYBLOCK),
//    FOG("FoG", Color.YELLOW, FoGAchievements.class, TableTimePlayed.FOG),
//    MINIGAMES("MiniGames", Color.WHITE, MiniGamesAchievements.class, TableTimePlayed.MINIGAMES),
//    UHSURVIVAL("UHSurvival", Color.GREEN, UHSurvivalAchievements.class, TableTimePlayed.UHSURVIVAL);
//
//    @Getter private final String name;
//    @Getter private final Color color;
//    @Getter private final Class<? extends Enum> achievementClass;
////    private final Column playTimeColumn;
//
//    Server(String name, Color color, Class<? extends Enum> achievementClass/*, Column playTimeColumn*/) {
//        this.name = name;
//        this.color = color;
//        this.achievementClass = achievementClass;
////        this.playTimeColumn = playTimeColumn;
//    }
//
//    public String getDisplayName() {
//        return color.getCc() + "§l" + name;
//    }
//
//    public Achievement achievement(String value) {
//        return (Achievement) Enum.valueOf(achievementClass, value);
//    }
//
//    public Achievement[] achievements() {
//        return (Achievement[]) achievementClass.getEnumConstants();
//    }
//
    public enum Status {

        ONLINE("Online", Color.LIME),
        OFFLINE("Offline", Color.RED),
        MAINTENANCE("Maintenance", Color.FUCHSIA),
        RESTARTING("Restarting", Color.GRAY);

        @Getter private final String name;
        @Getter private final Color color;

        Status(String name, Color color) {
            this.name = name;
            this.color = color;
        }

        public String getDisplayName() {
            return color.getCc() + "§l" + name;
        }
    }
}
