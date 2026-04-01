package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.friends;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.FriendInvite;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerFriendsUpdatePublisher;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerMessagePublisher;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public interface FriendGUIInstance<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

    GUI<P> getInstance();
    
    P getViewer();
    
    P getPlayer();
    
    default void requestFriend(PlayerModel model) {
        if (model.getUUID().equals(getPlayer().getUUID())) {
            getViewer().sendMessage(getPrefix(), Color.RED, "spigot", "player.friends.self");
            return;
        }

        FriendInvite outgoing = getPlayer().getOutgoingFriendInvite(model.getUUID(), true);

        if (outgoing != null) {
            getViewer().sendMessage(getPrefix(), Color.RED, "spigot", "player.friends.already_invited", model.getName(Name.RAW_COLORED) + "§7");
            return;
        }

        if (getPlayer().isFriend(model.getUUID())) {
            getViewer().sendMessage(getPrefix(), Color.RED, "spigot", "player.friends.already_friends", model.getName(Name.RAW_COLORED) + "§7");
            return;
        }

        FriendInvite incoming = getPlayer().getIncomingFriendInvite(model.getUUID(), true);

        if (incoming != null) {
            acceptInvite(incoming, model);
            return;
        }

        outgoing = new FriendInvite(getPlayer().getUUID(), model.getUUID());
        getPlayer().getOutgoingFriendInvites(false).add(outgoing);
        outgoing.insert();

        getViewer().sendMessage(getPrefix(), Color.SUCCESS, "spigot", "player.friends.sent_friend_request", model.getName(Name.RAW_COLORED) + "§7");

        new PlayerFriendsUpdatePublisher().publish(model.getUUID());
        new PlayerMessagePublisher().publish(
            model.getUUID(),
            Message.format(getPrefix(), Color.SUCCESS, model.getLanguage().getString("spigot", "player.friends.received_friend_request", getPlayer().getName(Name.RAW_COLORED) + "§7"))
        );
    }

    default void removeFriend(Friend friend) {
        if (friend.isDestroyed())
            return;

        getPlayer().getFriends(false).remove(friend);
        friend.delete();
        friend.deleteRelative();

        PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(friend.getFriendUuid()));
        getViewer().sendMessage(getPrefix(), Color.SUCCESS, "spigot", "player.friends.no_longer_friends", model.getName(Name.RAW_COLORED));

        new PlayerFriendsUpdatePublisher().publish(friend.getFriendUuid());
        new PlayerMessagePublisher().publish(
                friend.getFriendUuid(),
                Message.format(getPrefix(), Color.SUCCESS, model.getLanguage().getString("spigot", "player.friends.no_longer_friends", getPlayer().getName(Name.RAW_COLORED) + "§7"))
        );
    }

    default void acceptInvite(FriendInvite invite, PlayerModel friend) {
        invite.delete();
        Friend friend1 = new Friend(invite.getUuid(), invite.getFriendUuid(), false);
        Friend friend2 = new Friend(invite.getFriendUuid(), invite.getUuid(), false);

        if (isOutgoing(invite)) {
            getPlayer().getFriends(false).add(friend1);
            getPlayer().getOutgoingFriendInvites(false).remove(invite);
        } else {
            getPlayer().getFriends(false).add(friend2);
            getPlayer().getIncomingFriendInvites(false).remove(invite);
        }
        friend1.insert();
        friend2.insert();

        getViewer().sendMessage(getPrefix(), Color.SUCCESS, "spigot", "player.friends.now_friends", friend.getName(Name.RAW_COLORED) + "§7");

        new PlayerFriendsUpdatePublisher().publish(friend.getUUID());
        new PlayerMessagePublisher().publish(
            friend.getUUID(),
            Message.format(getPrefix(), Color.SUCCESS, friend.getLanguage().getString("spigot", "player.friends.now_friends", getPlayer().getName(Name.RAW_COLORED) + "§7"))
        );
    }

    default void rejectInvite(FriendInvite invite, PlayerModel friend) {
        getPlayer().getIncomingFriendInvites(false).remove(invite);
        invite.delete();

        getViewer().sendMessage(getPrefix(), Color.SUCCESS, "spigot", "player.friends.invites.on_reject", friend.getName(Name.RAW_COLORED) + "§7");

        new PlayerFriendsUpdatePublisher().publish(friend.getUUID());
        new PlayerMessagePublisher().publish(
            friend.getUUID(),
            Message.format(getPrefix(), Color.RED, friend.getLanguage().getString("spigot", "player.friends.invites.rejected", getPlayer().getName(Name.RAW_COLORED) + "§7"))
        );
    }

    default void revertInvite(FriendInvite invite, PlayerModel friend) {
        getPlayer().getOutgoingFriendInvites(false).remove(invite);
        invite.delete();

        getViewer().sendMessage(getPrefix(), Color.SUCCESS, "spigot", "player.friends.invites.on_revert", friend.getName(Name.RAW_COLORED) + "§7");

        new PlayerFriendsUpdatePublisher().publish(friend.getUUID());
        new PlayerMessagePublisher().publish(
            friend.getUUID(),
            Message.format(getPrefix(), Color.RED, friend.getLanguage().getString("spigot", "player.friends.invites.reverted", getPlayer().getName(Name.RAW_COLORED) + "§7"))
        );
    }

    default void openFriendRequestGUI() {
        AnvilNms anvil = OMServer.getInstance().getNms().anvilGui(getViewer().bukkit(), (event) -> {
            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT) {
                return;
            }

            String playerName = event.getName();
            UUID uuid = UUIDUtils.getUUID(playerName);

            if (uuid == null) {
                getViewer().sendMessage(getPrefix(), Color.RED, "spigot", "player.anvil_gui.unknown_player");
                return;
            }

            PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(uuid));

            if (model == null) {
                getViewer().sendMessage(getPrefix(), Color.RED, "spigot", "player.anvil_gui.unknown_player");
                return;
            }

            event.getAnvilNms().destroy();
            getInstance().close();

            requestFriend(model);
        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new FriendGUI<>(getViewer(), getPlayer()).open();
                    }
                }.runTaskLater(OMServer.getInstance(), 1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new PlayerSkullBuilder(getPlayer().getUUID(), 1, getViewer().translate("spigot", "player.friends.anvil_gui.name")).build());

        SpigotServer.getInstance().runSync(anvil::open);
    }

    default boolean anyRequests() {
        return getPlayer().getOutgoingFriendInvites(false).size() != 0 || getPlayer().getIncomingFriendInvites(false).size() != 0;
    }

    default boolean isOutgoing(FriendInvite invite) {
        return invite.getUuid().equals(getPlayer().getUUID());
    }

    default String getPrefix() {
        return getViewer().translate("spigot", "word.friend.plural");
    }
}
