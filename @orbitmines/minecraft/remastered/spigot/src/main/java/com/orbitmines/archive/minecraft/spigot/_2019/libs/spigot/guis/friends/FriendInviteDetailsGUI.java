package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.friends;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.FriendInvite;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.settings.PlayerVisibility;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;

public class FriendInviteDetailsGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends GUI<P> implements FriendGUIInstance<S, P> {

    private final P key;

    public FriendInviteDetailsGUI(P viewer, P key, FriendInvite invite, PlayerModel friend) {
        super(27, "§0§lFriend - " + friend.getName(Name.RAW), viewer);

        this.key = key;

        set(1, 1, new Item<P, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.BOOK, 1, "§a§l" + viewer.translate("spigot", "player.friends.invites.accept_request"));
        }, event -> {
            acceptInvite(invite, friend);

            if (anyRequests())
                new FriendInviteGUI<>(viewer, key).open();
            else
                new FriendGUI<>(viewer, key).open();

            if (key instanceof PlayerVisibility)
                ((PlayerVisibility) key).updateVisibility();
        }));

        set(1, 4, new Item<P, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder(invite.getFriendUuid(), 1, "§b« " + viewer.translate("spigot", "player.friends.invites.back_to_friends"));
        }, event -> {
            new FriendInviteGUI<>(viewer, key).open();
        }));

        set(1, 7, new Item<P, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("spigot", "player.friends.invites.reject_request"));
        }, event -> {
            rejectInvite(invite, friend);

            if (anyRequests())
                new FriendInviteGUI<>(viewer, key).open();
            else
                new FriendGUI<>(viewer, key).open();

            if (key instanceof PlayerVisibility)
                ((PlayerVisibility) key).updateVisibility();
        }));
    }

    @Override
    public GUI<P> getInstance() {
        return this;
    }

    @Override
    public P getPlayer() {
        return this.key;
    }
}
