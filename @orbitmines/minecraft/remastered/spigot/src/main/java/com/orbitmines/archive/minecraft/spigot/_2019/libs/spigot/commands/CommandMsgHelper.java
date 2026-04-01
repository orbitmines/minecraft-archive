package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerSettings;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.jedis.exception.PlayerNoLongerOnlineException;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;

import java.util.UUID;

public interface CommandMsgHelper {

    default void setLastMessage(UUID uuid, UUID uuid2) {
        StateProvider.getInstance().setPlayerField(uuid, "last_private_message", uuid2.toString());
        StateProvider.getInstance().setPlayerField(uuid2, "last_private_message", uuid.toString());
    }

    default OnlinePlayer getLastPrivateMessage(OMPlayer player) throws PlayerNoLongerOnlineException {
        String uuid = StateProvider.getInstance().getPlayerField(player.getUUID(), "last_private_message");

        if (uuid == null)
            return null;

        return OnlinePlayer.get(UUID.fromString(uuid));
    }

    default boolean allowedBySettings(OMPlayer player, OnlinePlayer target) {
        if (player.isEligible(StaffRank.HELPER))
            return true;

        PlayerSettings settings = PlayerSettings.findBy(PlayerSettings.class,
                PlayerSettings.column.UUID.is(target.getUUID()),
                PlayerSettings.column.TYPE.is(PlayerSettings.Type.PRIVATE_MESSAGES)
        );

        PlayerSettings.Level level = settings != null ? settings.getLevel() : PlayerSettings.Type.PRIVATE_MESSAGES.getDefaultLevel();

        switch (level) {

            case ENABLED:
                return true;
            case ONLY_FRIENDS: {
                boolean friend = player.isFriend(target.getUUID());

                if (!friend)
                    player.sendMessage("Msg", Color.RED, "spigot", "player.command.msg.only_friends", target.getName(Name.RAW_COLORED) + "§7");

                return friend;
            }
            case ONLY_FAVORITE_FRIENDS:
                Friend friend = Friend.findBy(Friend.class,
                        Friend.column.UUID.is(target.getUUID()),
                        Friend.column.FRIEND_UUID.is(player.getUUID()),
                        Friend.column.FAVORITE.is(true)
                );

                if (friend == null)
                    player.sendMessage("Msg", Color.RED, "spigot", "player.command.msg.only_favorite_friends", target.getName(Name.RAW_COLORED) + "§7");

                return friend != null;
            case DISABLED:
                player.sendMessage("Msg", Color.RED, "spigot", "player.command.msg.disabled", target.getName(Name.RAW_COLORED) + "§7");
                return false;
        }

        return true;
    }
}
