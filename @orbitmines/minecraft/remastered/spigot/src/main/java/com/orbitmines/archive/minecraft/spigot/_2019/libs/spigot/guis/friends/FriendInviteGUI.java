package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.friends;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.FriendInvite;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class FriendInviteGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PaginatableGUI<P, P, FriendInvite> implements FriendGUIInstance<S, P> {

    public FriendInviteGUI(P viewer, P key) {
        super(54, "§0§lFriend Requests", viewer, key, 9, 3);

        set(0, 4, new Item<P, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder(this.key.getUUID(), 1, "§b« " + viewer.translate("spigot", "player.friends.invites.back_to_friends"));
        }, event -> {
            new FriendGUI<>(viewer, key).open();
        }));

        paginate(2, 0);

        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Light Blue Arrow Left", SkullTexture.LIGHT_BLUE_ARROW_LEFT, 1, "§7« " + viewer.translate("spigot", "player.friends.invites.paginate")));
        setPreviousPage(5, 8, () -> new PlayerSkullBuilder("Light Blue Arrow Right", SkullTexture.LIGHT_BLUE_ARROW_RIGHT, 1, "§7" + viewer.translate("spigot", "player.friends.invites.paginate") + " »"));
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
    public Item<P, MutableItemBuilder> getItem(PageItem<FriendInvite> pageItem) {
        return new Item<>(() -> {
            FriendInvite invite = pageItem.getObject();

            if (invite == null)
                return null;

            boolean outgoing = isOutgoing(invite);

            OfflinePlayer friend = outgoing ? OfflinePlayer.get(invite.getFriendUuid()) : OfflinePlayer.get(invite.getUuid());

            ItemBuilder item = new ItemBuilder(outgoing ? Material.MAP : Material.PAPER, 1, friend.getName(Name.RAW_COLORED));

            if (outgoing) {
                item.addLore("§7§o" + viewer.translate("spigot", "player.friends.invites.outgoing"));
                item.addLore("");
                item.addLore("§c" + viewer.translate("spigot", "player.friends.invites.outgoing.hover"));
                return item;
            }

            item.addLore("§7§o" + viewer.translate("spigot", "player.friends.invites.incoming"));
            item.addLore("");
            item.addLore("§a" + viewer.translate("spigot", "player.friends.invites.incoming.hover"));
            return item;
        }, event -> {
            FriendInvite invite = pageItem.getObject();

            if (invite == null)
                return;

            boolean outgoing = isOutgoing(invite);

            if (outgoing) {
                PlayerModel friend = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(invite.getFriendUuid()));

                revertInvite(invite, friend);

                if (anyRequests())
                    update();
                else
                    new FriendGUI<>(viewer, key).open();

                return;
            }

            PlayerModel friend = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(invite.getUuid()));

            new FriendInviteDetailsGUI<>(viewer, key, invite, friend).open();
        });
    }

    @Override
    public List<FriendInvite> getCollection() {
        List<FriendInvite> invites = new ArrayList<>();
        invites.addAll(this.key.getOutgoingFriendInvites(false));
        invites.addAll(this.key.getIncomingFriendInvites(false));

        return invites;
    }

    @Override
    public Class<FriendInvite> getTypeClass() {
        return FriendInvite.class;
    }
}
