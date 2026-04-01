package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.ServerList;
import com.orbitmines.archive.minecraft._2019.libs.achievement.PersonalAchievement;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerSettings;
import com.orbitmines.archive.minecraft._2019.libs.database.models.TimePlayed;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.FriendInvite;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.LastVote;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.PendingVote;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.Achievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.StoredProgressAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.HubAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.PlayerAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.exceptions.ProcessVoteException;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram.PlayerHologramLeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerKickPublisher;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerServerConnectPublisher;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import com.orbitmines.archive.minecraft._2019.utils.jedis.JedisManager;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards.ScoreboardSet;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards.SpigotScoreboard;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.*;

public abstract class OMPlayer<S extends OMServer, P extends OMPlayer<S, P>> extends SpigotPlayer<S> implements Languageable, PlayerInstance {

    protected PlayerModel model;
    protected List<PeriodLootItem> periodLootItems;
    protected List<LootItem> lootItems;
    protected List<LastVote> lastVotes;
    protected List<MonthlyVotes> monthlyVotes;
    protected List<PlayerSettings> settings;
    protected List<Friend> friends;
    protected List<FriendInvite> incomingFriendInvites;
    protected List<FriendInvite> outgoingFriendInvites;
    protected List<PlayerAchievement> achievements;
    protected List<TimePlayed> timePlayed;
    protected DiscordUser discordUser;

    @Getter protected Date loginAt;

    protected String afk;
    @Getter @Setter protected boolean opMode;
    @Deprecated protected SpigotScoreboard<P> scoreboard;

    public OMPlayer(Player player, S server) {
        super(player, server);

        if (player != null) {
            this.scoreboard = new SpigotScoreboard<>(getInstance());

            /* Dummy Model until loaded from database */
            this.model = new PlayerModel(player.getUniqueId(), player.getName());
        }

        this.opMode = false;
        bukkit().setOp(false);
    }

    public abstract P getInstance();

    public abstract void onFirstLogin();

    @Override
    protected void register() {
        server.registerPlayer(getInstance());
    }

    @Override
    protected void unregister() {
        server.unregisterPlayer(getInstance());
    }

    @Override
    public boolean onJoin() {
        joinToRedis();

        try {
            /* Make sure our database connection is available, otherwise kick the player */
            DatabaseManager.getInstance().getDefault().checkConnection();
        } catch (Exception e) {
            e.printStackTrace();
            kick("§7§lOrbit§8§lMines\n§cWe're having issues connecting you to the server, check our Discord for more information.");
            return false;
        }

        this.model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(getUUID()));

        if (this.model == null) {
            new NullPointerException("Could not find player model for " + getUUID().toString()).printStackTrace();
            kick("§7§lOrbit§8§lMines\n§cWe're having issues connecting you to the server, check our Discord for more information.");
            return false;
        }

        broadcastJoinMessage();

        server.newTabListHandler(getInstance()).send();

        loginAt = DateUtils.now();

        if (isFirstLogin())
            onFirstLogin();

        /* TODO: Move to NMS */
        /* Send Command update after loading in player model & ranks */
        server.runSync(() -> MinecraftServer.getServer().getCommandDispatcher().a(((CraftPlayer) bukkit()).getHandle()));

        /* Following reloads required */
            /* Reload Settings */
            loadSettings();
            loadFriends();
            getAchievements(true);
            loadTimePlayed();
            /* Reload Loot, PeriodLoot & Votes for SpaceTurtle */
            loadLootItems();
            loadPeriodLootItems();
            handleVotes();


        /* Spawn Leaderboards */
        for (LeaderBoard leaderBoard : LeaderBoard.getLeaderBoards()) {
            if (leaderBoard instanceof PlayerHologramLeaderBoard)
                ((PlayerHologramLeaderBoard) leaderBoard).onLogin(this);
        }

        spawnPersonalizedNpcs();

        return true;
    }

    @Override
    public void beforeQuitSync() {

    }

    @Override
    public void afterQuitAsync() {
        quitToRedis();

        broadcastQuitMessage();

        /* Destroy Leaderboards */
        for (LeaderBoard leaderBoard : LeaderBoard.getLeaderBoards()) {
            if (leaderBoard instanceof PlayerHologramLeaderBoard)
                ((PlayerHologramLeaderBoard) leaderBoard).onLogout(this);
        }

        if (loginAt == null)
            return;

        Date logoutAt = DateUtils.now();
        long playTimeInSeconds = (logoutAt.getTime() - loginAt.getTime()) / 1000;

        TimePlayed played = getTimePlayed(server.getType(), true);
        played.addSeconds(playTimeInSeconds);
        played.insertOrUpdate(TimePlayed.column.SECONDS);
    }

    private void joinToRedis() {
        try (Jedis jedis = JedisManager.get()) {
            Pipeline p = jedis.pipelined();
            p.sadd("server:" + server.getType().getPluginName() + ":players", getName(Name.RAW));
            p.hset("player:" + player.getUniqueId().toString(), "server", server.getType().toString());

            p.sync();
        }
    }

    private void quitToRedis() {
        try (Jedis jedis = JedisManager.get()) {
            jedis.srem("server:" + server.getType().getPluginName() + ":players", getName(Name.RAW));
        }
    }

    public void onVote(int votes) {
        int prisms = 250 * votes;

        LootItem item = new LootItem(getUUID(), LootItem.Type.PRISMS, Rarity.COMMON, prisms, "§9§l§oVote Rewards");
        item.insert();

        this.lootItems.add(item);

        String reward = "§9§l" + NumberUtils.locale(prisms) + " Prisms§7";

        sendRawMessage("");
        sendMessage("Vote", Color.BLUE, "spigot", "player.on_vote", reward, "§2/loot§7");
        playSound(Sound.ENTITY_ARROW_HIT_PLAYER);

        OMServer.BroadcastExclusion<P> exclusion = p -> !p.getUUID().equals(getUUID());
        server.broadcast(exclusion, "Vote", Color.BLUE, "spigot", "player.vote_broadcast", getName(Name.RAW_COLORED) + "§7", "§9/vote§7", reward);

        /*
            Monthly Vote Achievement
         */
        int votesThisMonth = getVotesThisMonth(false).getVotes();
        for (PersonalAchievement achievement : PersonalAchievement.MONTHLY_ACHIEVEMENT_VOTES) {
            if (votesThisMonth >= achievement.getVotes() && (votesThisMonth - votes) < achievement.getVotes()) {
                String tierString = NumberUtils.toRoman(achievement.getTier());

                if (achievement.getPrisms() != 0)
                    new LootItem(getUUID(), LootItem.Type.PRISMS, Rarity.UNCOMMON, achievement.getPrisms(), "§a§l§oMonthly Achievement " + tierString + " " + DateUtils.humanFriendlyMonth() + " " + DateUtils.getYear()).insert();

                if (achievement.getSolars() != 0)
                    new LootItem(getUUID(), LootItem.Type.SOLARS, Rarity.RARE, achievement.getPrisms(), "§a§l§oMonthly Achievement " + tierString + " " + DateUtils.humanFriendlyMonth() + " " + DateUtils.getYear()).insert();
            }
        }

        StoredProgressAchievement handler = (StoredProgressAchievement) HubAchievement.ORBITMINES_SUPPORTER.getHandler();
        if (handler.hasCompleted(this))
            handler.complete(this, true);
    }

    @Override
    public boolean hasScoreboard() {
        return getSettings(PlayerSettings.Type.SCOREBOARD_VISIBILITY, false).getLevel() == PlayerSettings.Level.ENABLED;
    }

    protected void broadcastJoinMessage() {
        TextBuilder<P> builder = new TextBuilder<>();
        builder.add(Color.LIME, player -> " » ");
        server.newChatHandler(this, ChatHandler.Type.NORMAL, null).appendSender(builder);//TODO, Move this to a general class
        builder.add(Color.LIME, player -> " " + player.translate("spigot", "player.join_message"));

        builder.send(server.getPlayers());

        SpigotDiscordBot bot = server.getDiscordBot();
        bot.withPlayerEmote(getUUID(), getName(Name.RAW), false, (emote) -> {
            bot.getTextChannel().sendMessage(
                bot.getRole(CustomRole.JOIN).getAsMention() + " " + bot.getPlayerDisplay(OMPlayer.this, emote, getName(Name.RAW) + " has joined.")
            ).queue();
        });
    }

    protected void broadcastQuitMessage() {
        TextBuilder<P> builder = new TextBuilder<>();
        builder.add(Color.RED, player -> " « ");
        server.newChatHandler(this, ChatHandler.Type.NORMAL, null).appendSender(builder);
        builder.add(Color.RED, player -> " " + player.translate("spigot", "player.quit_message"));

        builder.send(server.getPlayers());

        SpigotDiscordBot bot = server.getDiscordBot();
        bot.withPlayerEmote(getUUID(), getName(Name.RAW), false, (emote) -> {
            bot.getTextChannel().sendMessage(
                bot.getRole(CustomRole.LEAVE).getAsMention() + " " + bot.getPlayerDisplay(OMPlayer.this, emote, getName(Name.RAW) + " has left.")
            ).queue();
        });
    }

    /*

        Models

     */

    public List<PeriodLootItem> getPeriodLootItems(boolean reload) {
        if (periodLootItems == null || reload)
            loadPeriodLootItems();

        return periodLootItems;
    }

    public PeriodLootItem getPeriodLootItem(PeriodLootItem.Type type, boolean reload) {
        for (PeriodLootItem periodLootItem : getPeriodLootItems(reload)) {
            if (periodLootItem.getType() == type)
                return periodLootItem;
        }

        return new PeriodLootItem(getUUID(), type, null);
    }

    public void loadPeriodLootItems() {
        ArrayList<PeriodLootItem> periodLootItems = PeriodLootItem.getAll(PeriodLootItem.class, PeriodLootItem.column.UUID.is(getUUID()));

        loop:
        for (PeriodLootItem.Type type : PeriodLootItem.Type.values()) {
            for (PeriodLootItem lootItem : periodLootItems) {
                if (lootItem.getType() == type)
                    continue loop;
            }

            periodLootItems.add(new PeriodLootItem(getUUID(), type, null));
        }

        this.periodLootItems = periodLootItems;
    }

    public void loadLootItems() {
        this.lootItems = LootItem.getAll(LootItem.class, LootItem.column.UUID.is(getUUID()));
    }

    public List<LootItem> getLootItems(boolean reload) {
        if (lootItems == null || reload)
            loadLootItems();

        return this.lootItems;
    }

    public void handleVotes() {
        int count = 0;

        try {
            loadLastVotes();
            loadMonthlyVotes();

            for (PendingVote vote : PendingVote.getAll(PendingVote.class, PendingVote.column.UUID.is(getUUID()))) {
                count++;
                vote.delete();
            }

            if (count != 0)
                onVote(count);

        } catch(Exception ex) {
            new ProcessVoteException(getUUID(), count, ex).printStackTrace();
        }
    }

    public void loadLastVotes() {
        this.lastVotes = LastVote.getAll(LastVote.class, LastVote.column.UUID.is(getUUID())); //TODO ACTIVE ONLY?
    }

    public List<LastVote> getLastVotes(boolean reload) {
        if (lastVotes == null || reload)
            loadLastVotes();

        return this.lastVotes;
    }

    public LastVote getLastVote(ServerList serverList, boolean reload) {
        for (LastVote vote : getLastVotes(reload)) {
            if (vote.getServerList() == serverList)
                return vote;
        }

        LastVote lastVote = new LastVote(getUUID(), serverList, null);
        this.lastVotes.add(lastVote);

        return lastVote;
    }

    public void loadMonthlyVotes() {
        this.monthlyVotes = MonthlyVotes.getAll(MonthlyVotes.class, MonthlyVotes.column.UUID.is(getUUID()));
    }

    public List<MonthlyVotes> getMonthlyVotes(boolean reload) {
        if (monthlyVotes == null || reload)
            loadMonthlyVotes();

        return this.monthlyVotes;
    }

    public MonthlyVotes getVotesThisMonth(boolean reload) {
        for (MonthlyVotes votes : getMonthlyVotes(reload)) {
            if (votes.getMonth() == DateUtils.getMonth())
                return votes;
        }

        MonthlyVotes monthlyVotes = new MonthlyVotes(getUUID(), DateUtils.getMonth(), DateUtils.getYear(), 0);
        this.monthlyVotes.add(monthlyVotes);

        return monthlyVotes;
    }

    public void loadSettings() {
        this.settings = PlayerSettings.getAll(PlayerSettings.class, PlayerSettings.column.UUID.is(getUUID()));

        for (PlayerSettings.Type type : PlayerSettings.Type.values()) {
            for (PlayerSettings setting : new ArrayList<>(this.settings)) {
                if (setting.getType() == type)
                    continue;

                PlayerSettings settings = new PlayerSettings(getUUID(), type);
                this.settings.add(settings);
            }
        }
    }

    public List<PlayerSettings> getSettings(boolean reload) {
        if (settings == null || reload)
            loadSettings();

        return this.settings;
    }

    public PlayerSettings getSettings(PlayerSettings.Type type, boolean reload) {
        for (PlayerSettings settings : getSettings(reload)) {
            if (settings.getType() == type)
                return settings;
        }

        PlayerSettings settings = new PlayerSettings(getUUID(), type);
        this.settings.add(settings);

        return settings;

    }

    public void loadFriends() {
        this.friends = Friend.getAll(Friend.class, Friend.column.UUID.is(getUUID()), Friend.column.FAVORITE.ordered(Order.DESC));
    }

    /* Ordered by: Favorites, Non favorites */
    public List<Friend> getFriends(boolean reload) {
        if (friends == null || reload)
            loadFriends();

        return this.friends;
    }

    public List<Friend> getFavoriteFriends(boolean reload) {
        List<Friend> favorites = new ArrayList<>();
        for (Friend friend : getFriends(reload)) {
            if (friend.isFavorite())
                favorites.add(friend);
        }

        return favorites;
    }

    public boolean isFriend(OMPlayer player) {
        return isFriend(player.getUUID());
    }

    public boolean isFriend(OMPlayer player, Boolean favorite) {
        return isFriend(player.getUUID(), favorite);
    }

    public boolean isFriend(UUID uuid) {
        return isFriend(uuid, null);
    }

    public boolean isFriend(UUID uuid, Boolean favorite) {
        for (Friend friend : getFriends(false)) {
            if (friend.getFriendUuid().equals(uuid) && (favorite == null || friend.getFavorite() == favorite))
                return true;
        }

        return false;
    }

    public void loadIncomingFriendInvites() {
        this.incomingFriendInvites = FriendInvite.getAll(FriendInvite.class, FriendInvite.column.FRIEND_UUID.is(getUUID()));
    }

    public List<FriendInvite> getIncomingFriendInvites(boolean reload) {
        if (incomingFriendInvites == null || reload)
            loadIncomingFriendInvites();

        return this.incomingFriendInvites;
    }

    public FriendInvite getIncomingFriendInvite(UUID uuid, boolean reload) {
        for (FriendInvite invite : getIncomingFriendInvites(reload)) {
            if (invite.getUuid().equals(uuid))
                return invite;
        }
        return null;
    }

    public void loadOutgoingFriendInvites() {
        this.outgoingFriendInvites = FriendInvite.getAll(FriendInvite.class, FriendInvite.column.UUID.is(getUUID()));
    }

    public List<FriendInvite> getOutgoingFriendInvites(boolean reload) {
        if (outgoingFriendInvites == null || reload)
            loadOutgoingFriendInvites();

        return this.outgoingFriendInvites;
    }

    public FriendInvite getOutgoingFriendInvite(UUID uuid, boolean reload) {
        for (FriendInvite invite : getOutgoingFriendInvites(reload)) {
            if (invite.getFriendUuid().equals(uuid))
                return invite;
        }
        return null;
    }

    public List<PlayerAchievement> getAchievements(boolean reload) {
        if (!reload && this.achievements != null && !this.achievements.isEmpty())
            return this.achievements;

        Collection<Server> servers = Server.getAllowed(this);

        this.achievements = PlayerAchievement.getAll(PlayerAchievement.class,
            PlayerAchievement.column.UUID.is(getUUID()),
            PlayerAchievement.column.SERVER.is(servers)
        );

        /* Add absent achievements */
        for (Server server : servers) {
            if (server == Server.BUILD)
                continue;

            loop:
            for (Achievement achievement : PlayerAchievement.byServer(server)) {
                for (PlayerAchievement a : this.achievements) {
                    if (a.getAchievement().equals(achievement))
                        continue loop;
                }

                this.achievements.add(new PlayerAchievement(getUUID(), achievement));
            }
        }

        return this.achievements;
    }

    public List<PlayerAchievement> getAchievements(Server server, boolean reload) {
        List<PlayerAchievement> achievements = new ArrayList<>();

        for (PlayerAchievement a : getAchievements(reload)) {
            if (a.getAchievement().getServer() == server)
                achievements.add(a);
        }

        return achievements;
    }

    public void loadTimePlayed() {
        this.timePlayed = TimePlayed.getAll(TimePlayed.class, TimePlayed.column.UUID.is(getUUID()));
    }

    public List<TimePlayed> getTimePlayed(boolean reload) {
        if (reload || this.timePlayed == null)
            loadTimePlayed();

        return timePlayed;
    }

    public TimePlayed getTimePlayed(Server server, boolean reload) {
        for (TimePlayed timePlayed : getTimePlayed(reload)) {
            if (timePlayed.getServer() == server)
                return timePlayed;
        }

        TimePlayed timePlayed = new TimePlayed(getUUID(), server);
        this.timePlayed.add(timePlayed);

        return timePlayed;
    }

    /*

        Afk

     */

    public boolean isAfk() {
        return afk != null;
    }

    public String getAfk() {
        return afk;
    }

    public void setAfk() {
        setAfk("null");
    }

    public void noLongerAfk() {
        setAfk(null);
    }

    public void setAfk(String afk) {
        String playerName = getName(Name.NICK_COLORED) + "§7";

        if (afk != null) {
            if (afk.equals("null"))
                server.broadcast("Afk", Color.BLUE, "spigot", "player.command.afk.now_afk", playerName);
            else
                server.broadcast("Afk", Color.BLUE, "spigot", "player.command.afk.now_afk_reason", playerName, "§6" + afk + "§7");
        } else {
            if (this.afk.equals("null"))
                server.broadcast("Afk", Color.BLUE, "spigot", "player.command.afk.no_longer_afk", playerName);
            else
                server.broadcast("Afk", Color.BLUE, "spigot", "player.command.afk.no_longer_afk_reason", playerName, "§6" + this.afk + "§7");
        }
        this.afk = afk;
    }

    /*

        Scoreboard

     */

    @Deprecated
    public SpigotScoreboard<P> getSpigotScoreboard() {
        return scoreboard;
    }

    @Deprecated
    public void resetScoreboard() {
        scoreboard.set(null);
    }

    @Deprecated
    public void clearScoreboard() {
        scoreboard.clear();
    }

    @Deprecated
    public void setScoreboard(ScoreboardSet set) {
        scoreboard.set(set);
    }

    /*

        PlayerInstance

     */

    @Override
    public UUID getUUID() {
        return getUniqueId();
    }

    @Override
    public String getRawName() {
        return player.getName();
    }

    public void reloadDiscordUser() {
        discordUser = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(getUUID()));
    }

    @Override
    public DiscordUser getDiscordUser() {
        if (discordUser != null)
            return discordUser;

        reloadDiscordUser();

        return discordUser;
    }

    /*

        Connection

     */
    public void fallback(boolean notify) {
        connect(null, notify);
    }
    public void connect(Server server, boolean notify) {
        new PlayerServerConnectPublisher().publish(this, server, notify);
    }

    public void kick(String message) {
        new PlayerKickPublisher().publish(this, message);
    }

    /*

        PlayerModel Delegates

     */

    public boolean isFirstLogin() {
        return getTimePlayed(server.getType(), false).getSeconds() == 0L;
    }

    public void hidePlayer(OMPlayer player) {
        hidePlayer(player.bukkit());
    }

    public void showPlayer(OMPlayer player) {
        showPlayer(player.bukkit());
    }

    public void hidePlayer(Player player) {
        hidePlayer(server, player);
    }

    public void showPlayer(Player player) {
        showPlayer(server, player);
    }

    public void setStaffRank(StaffRank staffRank) {
        model.setStaffRank(staffRank);
    }

    public void setVipRank(VipRank vipRank) {
        model.setVipRank(vipRank);
    }

    @Override
    public String getNickName() {
        return model.getNickName();
    }

    public void setNickName(String nickName) {
        model.setNickName(nickName);
    }

    @Override
    public StaffRank getStaffRank() {
        return model.getStaffRank();
    }

    @Override
    public VipRank getVipRank() {
        return model.getVipRank();
    }

    @Override
    public Language getLanguage() {
        return model.getLanguage();
    }

    public void setLanguage(Language language) {
        model.setLanguage(language);
    }

    public int getSolars() {
        return model.getSolars();
    }

    public int getPrisms() {
        return model.getPrisms();
    }

    public Date getFirstLoginAt() {
        return model.getFirstLoginAt();
    }

    public void addSolars(int amount) {
        model.addSolars(amount);
    }

    public void removeSolars(int amount) {
        model.removeSolars(amount);
    }

    public void addPrisms(int amount) {
        model.addPrisms(amount);
    }

    public void removePrisms(int amount) {
        model.removePrisms(amount);
    }

    public void update(PlayerModel.column... columns) {
        model.update(columns);
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
        sendMessage(Message.format(prefix, color, message));
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

    @Deprecated /* Use #getName(Name) */
    public String getName() {
        throw new IllegalStateException();
    }

    @Deprecated /* Use #getName(Name) */
    public String getDisplayName() {
        throw new IllegalStateException();
    }

    @Deprecated /* Use #getName(Name) */
    public void setDisplayName(String s) {
        throw new IllegalStateException();
    }

    public boolean isInventoryFull() {
        return player.getInventory().firstEmpty() == -1;
    }

    public void clearFullInventory() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
    }

    public void updateInventory() {
        new BukkitRunnable() {
            public void run() {
                player.updateInventory();
            }
        }.runTaskLater(server, 1);
    }

    public void clearExperience() {
        player.setLevel(0);
        player.setExp(0f);
    }

    public void clearPotionEffects() {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }
}
