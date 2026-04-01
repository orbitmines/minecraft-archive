package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.friends;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.IPEntry;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import org.bukkit.Material;

import java.util.*;

public class FriendGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PaginatableGUI<P, P, Friend> implements FriendGUIInstance<S, P> {

    private Map<Friend, OnlinePlayer> onlineFriends;
    private Type type;

    public FriendGUI(P viewer, P key) {
        super(54, "§0§lFriends", viewer, key, 9, 3);

        this.onlineFriends = new HashMap<>();
        this.type = Type.NORMAL;

        set(0, 1, new Item<P, MutableItemBuilder>(() -> {
            int incoming = key.getIncomingFriendInvites(false).size();
            int outgoing = key.getOutgoingFriendInvites(false).size();

            ItemBuilder builder = new ItemBuilder(Material.BOOK, 1, "§7§l" + viewer.translate("spigot", "player.friends.pending_requests"));
            builder.addLore("§7  " + viewer.translate("spigot", "player.friends.pending_requests.incoming") + " (§b§l" + incoming + "§7)");
            builder.addLore("§7  " + viewer.translate("spigot", "player.friends.pending_requests.outgoing") + " (§b§l" + outgoing + "§7)");

            if (incoming != 0 || outgoing != 0)
                builder.glow();

            return builder;
        }, event -> {
            int incoming = key.getIncomingFriendInvites(false).size();
            int outgoing = key.getOutgoingFriendInvites(false).size();

            if (incoming == 0 && outgoing == 0)
                return;

            new FriendInviteGUI<>(viewer, key).open();
        }));

        set(0, 3, new Item<P, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.WRITABLE_BOOK, 1, "§b§l" + viewer.translate("spigot", "player.friends.send_friend_request"));
        }, event -> {
            openFriendRequestGUI();
        }));

        set(0, 5, new Item<P, MutableItemBuilder>(() -> {
            ItemBuilder builder = new ItemBuilder(Material.DIAMOND, 1, "§6§l" + viewer.translate("spigot", "player.friends.add_to_favorites"));

            if (type == Type.FAVORITE)
                builder.glow();

            return builder;
        }, event -> {
            if (type == Type.FAVORITE)
                type = Type.NORMAL;
            else
                type = Type.FAVORITE;

            update();
        }));

        set(0, 7, new Item<P, MutableItemBuilder>(() -> {
            ItemBuilder builder = new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("spigot", "player.friends.remove_friend"));

            if (type == Type.REMOVE)
                builder.glow();

            return builder;
        }, event -> {
            if (type == Type.REMOVE)
                type = Type.NORMAL;
            else
                type = Type.REMOVE;

            update();
        }));

        paginate(2, 0);

        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Light Blue Arrow Left", SkullTexture.LIGHT_BLUE_ARROW_LEFT, 1, "§7« " + viewer.translate("spigot", "player.friends.paginate")));
        setPreviousPage(5, 8, () -> new PlayerSkullBuilder("Light Blue Arrow Right", SkullTexture.LIGHT_BLUE_ARROW_RIGHT, 1, "§7" + viewer.translate("spigot", "player.friends.paginate") + " »"));
    }

    @Override
    public GUI<P> getInstance() {
        return this;
    }

    @Override
    public P getPlayer() {
        return this.key;
    }

    @Override
    public void beforeUpdateAsync() {
        updateOnlineFriends();
    }

    @Override
    public Item<P, ?> getItem(PageItem<Friend> pageItem) {
        return new Item<P, MutableItemBuilder>(() -> {
            Friend f = pageItem.getObject();

            if (f == null)
                return null;

            OnlinePlayer onlineFriend = getOnlineFriend(f);
            PlayerInstance friend = onlineFriend != null ? onlineFriend : OfflinePlayer.get(f.getFriendUuid());

            ItemBuilderInstance builder;
            if (onlineFriend != null)
                builder = new PlayerSkullBuilder(friend.getUUID(), 1, friend.getName(Name.RAW_COLORED));
            else
                builder = new ItemBuilder(Material.WITHER_SKELETON_SKULL, 1, friend.getName(Name.RAW_COLORED));

            if (f.isFavorite())
                builder.addLore("§6§l" + viewer.translate("spigot", "player.friends.favorite_friend"));

            builder.addLore("");

            switch (this.type) {

                case NORMAL:
                    if (onlineFriend != null) {
                        builder.addLore("§7Status: " + Server.Status.ONLINE.getDisplayName());
                        builder.addLore("§7Server: " + onlineFriend.getServer().getDisplayName());
                        builder.addLore("");
                        builder.addLore("§a" + viewer.translate("spigot", "player.friends.hover"));
                    } else {
                        builder.addLore("§7Status: " + Server.Status.OFFLINE.getDisplayName());

                        IPEntry entry = friend.getLastIPEntry();
                        Date lastSeen = entry.getLogoutAt() != null ? entry.getLogoutAt() : entry.getLoginAt();

                        builder.addLore("§7" + viewer.translate("spigot", "player.friends.last_seen", "§b§l" + TimeUtils.humanFriendlyTimer(viewer.getLanguage(), System.currentTimeMillis() - lastSeen.getTime()) + "§7"));
                    }
                    break;
                case REMOVE:
                    builder.addLore("§c" + viewer.translate("spigot", "player.friends.hover.remove"));
                    break;
                case FAVORITE:
                    if (f.isFavorite())
                        builder.addLore("§c" + viewer.translate("spigot", "player.friends.hover.remove_favorite"));
                    else
                        builder.addLore("§6" + viewer.translate("spigot", "player.friends.hover.add_favorite"));
                    break;
            }

            return builder;
        }, event -> {
            Friend friend = pageItem.getObject();

            if (friend == null)
                return;

            OnlinePlayer onlineFriend = getOnlineFriend(friend);

            switch (this.type) {

                case NORMAL:
                    if (onlineFriend != null)
                        viewer.connect(onlineFriend.getServer(), true);

                    break;
                case REMOVE:
                    removeFriend(friend);

                    update();
                    break;
                case FAVORITE:
                    friend.setFavorite(!friend.isFavorite());
                    friend.update(Friend.column.FAVORITE);

                    update();
                    break;
            }
        });
    }

    @Override
    public List<Friend> getCollection() {
        List<Friend> sorted = new ArrayList<>(this.onlineFriends.keySet());

        for (Friend friend : key.getFriends(false)) {
            if (!sorted.contains(friend))
                sorted.add(friend);
        }
        return sorted;
    }

    @Override
    public Class<Friend> getTypeClass() {
        return Friend.class;
    }

    private OnlinePlayer getOnlineFriend(Friend friend) {
        return this.onlineFriends.get(friend);
    }

    private void updateOnlineFriends() {
        Friend[][] friends = getPage();

        List<Friend> pageFriends = new ArrayList<>();
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                Friend friend = friends[row][column];

                if (friend != null)
                    pageFriends.add(friend);
            }
        }

        this.onlineFriends = OnlinePlayer.getFromFriendList(pageFriends);
    }

    public enum Type {

        NORMAL,
        REMOVE,
        FAVORITE;

    }
}
