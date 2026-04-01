package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.IPEntry;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.BungeeDiscordBot;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers.PlayerNameChangePublisher;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.URLUtils;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import lombok.Getter;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.score.Scoreboard;

import java.net.InetSocketAddress;
import java.util.*;

public class BungeePlayer implements Languageable, PlayerInstance {

    private final Bungeecord plugin;
    private final ProxiedPlayer bungee;

    @Getter private PlayerModel model;

    public BungeePlayer(Bungeecord plugin, ProxiedPlayer bungee) {
        this.plugin = plugin;
        this.bungee = bungee;
    }

    public ProxiedPlayer bungee() {
        return bungee;
    }

    public void processJoinEvent() {
        try {
            /* Make sure our database connection is available, otherwise kick the player */
            DatabaseManager.getInstance().getDefault().checkConnection();
        } catch (Exception e) {
            e.printStackTrace();
            bungee.disconnect(new TextComponent("§7§lOrbit§8§lMines\n§cWe're having issues connecting you to the server, check our Discord for more information."));
            return;
        }

        /* Setup IPEntry */
        IPEntry ipEntry = new IPEntry(getUUID(), getAddress().getHostString(), getPendingConnection().getVersion());
        ipEntry.insert();

        this.model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(getUUID()));

        if (model == null) {
            /* First Login */
            this.model = new PlayerModel(getUUID(), getRawName());
            this.model.insert();

            onFirstLogin();
        } else {
            /* Not First Login */
            checkNameChange();
        }

        /* Update Skin Library */
        plugin.getSkinLibrary().updateLibrary(getUUID());

        /* Kick if on unsupported version */
        if (getPendingConnection().getVersion() < IPEntry.ProtocolVersion.FIRST_SUPPORTED_VERSION)
            kickUnsupportedVersion(getPendingConnection().getVersion());

        plugin.registerPlayer(this);

        /* Send join message to friends */
        List<Friend> friends = Friend.getAll(Friend.class, Friend.column.FRIEND_UUID.is(getUUID()));//, Friend.column.FAVORITE.is(true));

        for (Friend friend : friends) {
            BungeePlayer player = plugin.getPlayerIfOnline(friend.getUuid());

            if (player == null)
                continue;

            player.sendMessage("Server", Color.INFO, "bungeecord", "connection.friend.has_come_online", getName(Name.RAW_COLORED) + "§7");
        }

        /* Update Discord Ranks */
        DiscordUser user = getDiscordUser();
        if (user == null)
            user = new DiscordUser(getUUID(), null);

        user.updateDiscordRanks(plugin.getDiscordBot());
    }

    public void processQuitEvent(PlayerDisconnectEvent event) {
        /* End IPEntry */
        IPEntry ipEntry = getLastIPEntry();
        ipEntry.setLogoutAt(DateUtils.now());
        ipEntry.update(IPEntry.column.LOGOUT_AT);

        plugin.unregisterPlayer(this);
    }

    public ServerInfo getFallbackServer() {
        ServerInfo fallbackServer = null;
        Server currentServer = getServer() != null ? plugin.getServer(getServer().getInfo()) : null;

        /* Motd of server is the connection priority of the server, select the server with the highest priority */
        for (ServerInfo info : plugin.getProxy().getServers().values()) {
            if (fallbackServer == null || Integer.parseInt(fallbackServer.getMotd()) > Integer.parseInt(fallbackServer.getMotd())) {
                Server server = plugin.getServer(info);

                /* We want to fallback to a server, so the current server does not apply */
                if (server == currentServer)
                    continue;

                if (!isEligible(server.getRank()))
                    continue;

                if (server.getStatus() != Server.Status.ONLINE)
                    continue;

                fallbackServer = info;
            }
        }

        return fallbackServer;
    }

    private void onFirstLogin() {
        /* Welcome to OrbitMines loot */
        LootItem item = new LootItem(getUUID(), LootItem.Type.SOLARS, Rarity.RARE, 250, "§a§l§oWelcome to §8§l§oOrbit§7§l§oMines§a§l§o!");
        item.insert();

        /* New Players Mention */
        OMDiscordBot bot = plugin.getDiscordBot();
        bot.withPlayerEmote(getUUID(), getName(Name.RAW), false, (emote) -> {
            bot.getTextChannel(CustomChannel.NEW_PLAYERS).sendMessage(
                bot.getPlayerDisplay(BungeePlayer.this, emote, getName(Name.RAW)) + " has joined OrbitMines for the first time!"
            ).queue();
        });
    }

    private void checkNameChange() {
        String previousName = this.model.getName(Name.RAW);
        String newName = getName(Name.RAW);

        if (previousName.equals(newName))
            return;

        /* Check any other player's with the new name against the database, just so we don't get duplicate names */
        for (PlayerModel model : PlayerModel.getAll(PlayerModel.class, PlayerModel.column.NAME.is(newName))) {
            String actualName = UUIDUtils.getName(model.getUUID());

            if (actualName == null) {
                new NullPointerException("Could not retrieve player name for " + model.getUUID().toString() + ", please update manually.").printStackTrace();
                continue;
            }

            handleNameChange(model, model.getRawName(), actualName);
        }

        handleNameChange(this.model, previousName, newName);
    }

    private void handleNameChange(PlayerModel model, String previousName, String newName) {
        /* Update Name Change */
        model.setName(newName);
        model.update(PlayerModel.column.NAME);

        /* Update DiscordSquad Category */
        DiscordSquad squad = DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.UUID.is(getUUID()));
        if (squad != null)
            squad.onNameChange(plugin.getDiscordBot());

        /* Clear Name Cache */
        UUIDUtils.clearCacheFor(model.getUUID());
        UUIDUtils.clearCacheFor(previousName);
        UUIDUtils.clearCacheFor(newName);

        /* Notify servers to clear name cache */
        new PlayerNameChangePublisher().publish(model.getUUID(), previousName, newName);

        /* Notify Discord of Name Change */
        BungeeDiscordBot bot = plugin.getDiscordBot();
        bot.withPlayerEmote(model.getUUID(), newName, false, (emote) -> {
            bot.getTextChannel(CustomChannel.NAME_CHANGE).sendMessage(
                bot.getPlayerDisplay(model, emote, previousName) + " » " + bot.getPlayerDisplay(model, emote, newName)
            ).queue();
        });
    }

    private void kickUnsupportedVersion(int version) {
        String serverVersion = IPEntry.ProtocolVersion.humanReadableVersion(IPEntry.ProtocolVersion.FIRST_SUPPORTED_VERSION);
        String clientVersion = IPEntry.ProtocolVersion.humanReadableVersion(version);

        String client = clientVersion != null ? "§lMinecraft " + clientVersion + "§9" : translate("bungeecord", "connection.unsupported_version.unknown");

        disconnect(new TextComponent(
            "§7§lOrbit§8§lMines\n" +
            "§7" + serverVersion + "+\n" +
            "\n" +
            "§7" + translate("bungeecord", "connection.unsupported_version.welcome", getName(Name.RAW)) + "\n" +
            "\n" +
            "§9" + translate("bungeecord", "connection.unsupported_version.version_line", client, "§lMinecraft " + serverVersion + "+§9") + "\n" +
            "§9" + translate("bungeecord", "connection.unsupported_version.info_line") + "\n" +
            "\n" +
            "§7" + translate("bungeecord", "connection.unsupported_version.goodbye") + "\n" +
            "\n" +
            "§7§o" + translate("bungeecord", "connection.unsupported_version.footer", "§6§o" + URLUtils.humanReadableLink(Environment.get("OM_WEBSITE_LINK", "https://www.orbitmines.com")) + "§7")
        ));
    }

    /*

        PlayerModel Delegates

     */

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
        getModel().setLanguage(language);
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

    public void setVipRank(VipRank vipRank) {
        model.setVipRank(vipRank);
    }

    public void setStaffRank(StaffRank staffRank) {
        model.setStaffRank(staffRank);
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
        return bungee.getName();
    }

    @Override
    public DiscordUser getDiscordUser() {
        return DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(getUUID()));
    }

    /*

        Connection

     */
    public void fallback(boolean notify) {
        ServerInfo fallBack = getFallbackServer();

        if (fallBack == null) {
            disconnect(new TextComponent(
                "§8§lOrbit§7§lMines\n" +
                    "§71.14.1\n" +
                    "\n" +
                    "§7" + translate("bungeecord", "connection.no_fallback_server")
            ));
            return;
        }

        connect(plugin.getServer(fallBack), notify);
    }

    public void connect(com.orbitmines.archive.minecraft._2019.libs.Server server, boolean notify) {
        ServerInfo serverInfo = plugin.getServerInfo(server);

        if (server.getStatus() == Server.Status.OFFLINE || server.getRank() != null && !isEligible(server.getRank())) {
            sendMessage("Server", Color.ERROR, "bungeecord", "connection.server.offline", server.getDisplayName() + "§7", Server.Status.OFFLINE.getDisplayName() + "§7");
            return;
        } else if (serverInfo == null) {
            sendMessage("Server", Color.ERROR, "bungeecord", "connection.server.error", server.getDisplayName() + "§7");
            return;
        } else if (plugin.getServer(bungee.getServer().getInfo()) == server) {
            sendMessage("Server", Color.ERROR, "bungeecord", "connection.server.already_connected", server.getDisplayName() + "§7");
            return;
        } else if (server.getMaxPlayers() - serverInfo.getPlayers().size() < 1) {
            sendMessage("Server", Color.ERROR, "bungeecord", "connection.server.full", server.getDisplayName() + "§7");
            return;
        } else if (!isEligible(StaffRank.DEVELOPER) && server.getStatus() == Server.Status.MAINTENANCE) {
            sendMessage("Server", Server.Status.MAINTENANCE.getColor(), "bungeecord", "connection.server.maintenance", server.getDisplayName() + "§7");
            return;
        }

        bungee.connect(serverInfo);

        if (notify)
            sendMessage("Server", Color.ERROR, "bungeecord", "connection.server.connecting", server.getDisplayName() + "§7");
    }

    /*

        Language

     */
    @Override
    public void sendMessage(String namespace, String key) {
        sendMessage(TextComponent.fromLegacyText(
            translate(namespace, key)
        ));
    }

    @Override
    public void sendMessage(String namespace, String key, Object... args) {
        sendMessage(TextComponent.fromLegacyText(
            translate(namespace, key, args)
        ));
    }

    @Override
    public void sendMessage(String prefix, Color color, String key) {
        sendMessage(TextComponent.fromLegacyText(Message.format(prefix, color,
            translate(key)
        )));
    }

    @Override
    public void sendRawMessage(String prefix, Color color, String message) {
        sendMessage(TextComponent.fromLegacyText(
            Message.format(prefix, color, message)
        ));
    }

    @Override
    public void sendMessage(String prefix, Color color, String key, Object... args) {
        sendMessage(TextComponent.fromLegacyText(Message.format(prefix, color,
            translate(key, args)
        )));
    }

    @Override
    public void sendMessage(String prefix, Color color, String namespace, String key) {
        sendMessage(TextComponent.fromLegacyText(Message.format(prefix, color,
            translate(namespace, key)
        )));
    }

    @Override
    public void sendMessage(String prefix, Color color, String namespace, String key, Object... args) {
        sendMessage(TextComponent.fromLegacyText(Message.format(prefix, color,
            translate(namespace, key, args)
        )));
    }

    /*
    
        ProxiedPlayer delegated methods
    
     */

    @Deprecated /* Use #getName(Name) */
    public String getDisplayName() {
        throw new IllegalStateException();
    }

    @Deprecated /* Use #getName(Name) */
    public void setDisplayName(String s) {
        throw new IllegalStateException();
    }

    public void sendMessage(ChatMessageType chatMessageType, BaseComponent... baseComponents) {
        bungee.sendMessage(chatMessageType, baseComponents);
    }

    public void sendMessage(ChatMessageType chatMessageType, BaseComponent baseComponent) {
        bungee.sendMessage(chatMessageType, baseComponent);
    }

    public void connect(ServerInfo serverInfo) {
        bungee.connect(serverInfo);
    }

    public void connect(ServerInfo serverInfo, ServerConnectEvent.Reason reason) {
        bungee.connect(serverInfo, reason);
    }

    public void connect(ServerInfo serverInfo, Callback<Boolean> callback) {
        bungee.connect(serverInfo, callback);
    }

    public void connect(ServerInfo serverInfo, Callback<Boolean> callback, ServerConnectEvent.Reason reason) {
        bungee.connect(serverInfo, callback, reason);
    }

    public void connect(ServerConnectRequest serverConnectRequest) {
        bungee.connect(serverConnectRequest);
    }

    public net.md_5.bungee.api.connection.Server getServer() {
        return bungee.getServer();
    }

    public int getPing() {
        return bungee.getPing();
    }

    public void sendData(String s, byte[] bytes) {
        bungee.sendData(s, bytes);
    }

    public PendingConnection getPendingConnection() {
        return bungee.getPendingConnection();
    }

    public void chat(String s) {
        bungee.chat(s);
    }

    public ServerInfo getReconnectServer() {
        return bungee.getReconnectServer();
    }

    public void setReconnectServer(ServerInfo serverInfo) {
        bungee.setReconnectServer(serverInfo);
    }

    public UUID getUniqueId() {
        return bungee.getUniqueId();
    }

    public Locale getLocale() {
        return bungee.getLocale();
    }

    public byte getViewDistance() {
        return bungee.getViewDistance();
    }

    public ProxiedPlayer.ChatMode getChatMode() {
        return bungee.getChatMode();
    }

    public boolean hasChatColors() {
        return bungee.hasChatColors();
    }

    public SkinConfiguration getSkinParts() {
        return bungee.getSkinParts();
    }

    public ProxiedPlayer.MainHand getMainHand() {
        return bungee.getMainHand();
    }

    public void setTabHeader(BaseComponent baseComponent, BaseComponent baseComponent1) {
        bungee.setTabHeader(baseComponent, baseComponent1);
    }

    public void setTabHeader(BaseComponent[] baseComponents, BaseComponent[] baseComponents1) {
        bungee.setTabHeader(baseComponents, baseComponents1);
    }

    public void resetTabHeader() {
        bungee.resetTabHeader();
    }

    public void sendTitle(Title title) {
        bungee.sendTitle(title);
    }

    public boolean isForgeUser() {
        return bungee.isForgeUser();
    }

    public Map<String, String> getModList() {
        return bungee.getModList();
    }

    public Scoreboard getScoreboard() {
        return bungee.getScoreboard();
    }

    public InetSocketAddress getAddress() {
        return bungee.getAddress();
    }

    public void disconnect(BaseComponent... baseComponents) {
        bungee.disconnect(baseComponents);
    }

    public void disconnect(BaseComponent baseComponent) {
        bungee.disconnect(baseComponent);
    }

    public boolean isConnected() {
        return bungee.isConnected();
    }

    public Connection.Unsafe unsafe() {
        return bungee.unsafe();
    }

    @Deprecated /* Use #getName(Name) */
    public String getName() {
        throw new IllegalStateException();
    }

    public void sendMessage(BaseComponent... baseComponents) {
        bungee.sendMessage(baseComponents);
    }

    public void sendMessage(BaseComponent baseComponent) {
        bungee.sendMessage(baseComponent);
    }

    public Collection<String> getGroups() {
        return bungee.getGroups();
    }

    public void addGroups(String... strings) {
        bungee.addGroups(strings);
    }

    public void removeGroups(String... strings) {
        bungee.removeGroups(strings);
    }

    public boolean hasPermission(String s) {
        return bungee.hasPermission(s);
    }

    public void setPermission(String s, boolean b) {
        bungee.setPermission(s, b);
    }

    public Collection<String> getPermissions() {
        return bungee.getPermissions();
    }
    
    public static TextComponent getBanMessage(PlayerModel model, Punishment punishment) {
        OfflinePlayer bannedBy = OfflinePlayer.get(punishment.getPunishedBy());
        Language language = model.getLanguage();

        return new TextComponent(
            "§8§lOrbit§7§lMines§r\n" +
            "§7" + language.getString("bungeecord", "ban.header", "§c§l" + language.getString("bungeecord", "ban.banned") + "§7") + "\n" +
            "\n" +
            (punishment.getDurationType() == Punishment.Duration.PERMANENT ?
                    "§c§l" + language.getString("bungeecord", "ban.permanent") + "§r\n" :
                    "§c" + TimeUtils.humanFriendlyTimer(language, punishment.getMillisLeft()) + "\n"
            ) +
            "\n" +
            "§7" + language.getString("bungeecord", "ban.reason", "§c" + punishment.getReason() + "§7") + "\n" +
            "§7" + language.getString("bungeecord", "ban.banned_by", bannedBy.getName(Name.RAW_PREFIXED) + "§7") + "\n" +
            "\n" +
            "§7" + language.getString("bungeecord", "ban.banned_on", "§c" + DateUtils.format(punishment.getPunishedAt(), DateUtils.DATE_FORMAT) + "§7") + "\n" +
            (punishment.getDurationType() == Punishment.Duration.PERMANENT ?
                    "" :
                    "§7" + language.getString("bungeecord", "ban.banned_until", "§c" + DateUtils.format(punishment.getPunishedUntil(), DateUtils.DATE_FORMAT) + "§7")
            )
        );
    }
}
