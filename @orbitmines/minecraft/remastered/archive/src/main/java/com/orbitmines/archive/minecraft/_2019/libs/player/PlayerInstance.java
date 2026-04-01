package com.orbitmines.archive.minecraft._2019.libs.player;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.IPEntry;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;

import java.util.UUID;

public interface PlayerInstance {

    UUID getUUID();

    String getRawName();

    String getNickName();

    default boolean hasNickName() {
        return getNickName() != null;
    }

    StaffRank getStaffRank();

    VipRank getVipRank();

    DiscordUser getDiscordUser();

    default Rank getRank() {
        StaffRank staffRank = getStaffRank();

        return staffRank.isNone() ? getVipRank() : staffRank;
    }

    default OnlinePlayer asOnlinePlayer() {
        return OnlinePlayer.get(getUUID());
    }

    default String getName(Name name) {
        switch (name) {
            case RAW:
                return getRawName();
            case RAW_COLORED:
                return getRankColor().getCc() + getRawName();
            case RAW_PREFIXED:
                return getRankPrefix() + getRawName();
            case NICK:
                return (hasNickName() ? "*" + getNickName() + "*" : getRawName());
            case NICK_COLORED:
                return getRankColor().getCc() + (hasNickName() ? "*" + getNickName() + "*" : getRawName());
            case NICK_PREFIXED:
                return getRankPrefix() + (hasNickName() ? "*" + getNickName() + "*" : getRawName());
            default:
                throw new IllegalArgumentException();
        }
    }

    default Color getRankColor() {
        return getRank().getPrefixColor();
    }

    default String getRankPrefix() {
        return getRank().getAsPrefix();
    }

    default String getRankPrefix(Color color) {
        return getRank().getAsPrefix(color);
    }

    default boolean isEligible(Rank rank) {
        if (rank instanceof StaffRank)
            return isEligible((StaffRank) rank);
        else if (rank instanceof VipRank)
            return isEligible((VipRank) rank);
        else
            return true;
    }

    default boolean isEligible(StaffRank staffRank) {
        return getStaffRank().ordinal() >= staffRank.ordinal();
    }

    default boolean isEligible(VipRank vipRank) {
        return getVipRank().ordinal() >= vipRank.ordinal() || isEligible(StaffRank.ADMIN);
    }

    default IPEntry getLastIPEntry() {
        return IPEntry.getLast(IPEntry.class, IPEntry.column.UUID.is(getUUID().toString()));
    }
}
