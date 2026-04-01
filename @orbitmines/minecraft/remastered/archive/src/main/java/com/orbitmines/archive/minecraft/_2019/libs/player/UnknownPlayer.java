package com.orbitmines.archive.minecraft._2019.libs.player;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import lombok.Getter;

import java.util.UUID;

public class UnknownPlayer implements PlayerInstance {

    @Getter private final String rawName;

    private UnknownPlayer(String rawName) {
        this.rawName = rawName;
    }

    @Override
    public UUID getUUID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNickName() {
        return null;
    }

    @Override
    public StaffRank getStaffRank() {
        return StaffRank.NONE;
    }

    @Override
    public VipRank getVipRank() {
        return VipRank.NONE;
    }

    @Override
    public DiscordUser getDiscordUser() {
        return null;
    }

    public static UnknownPlayer get(String playerName) {
        return new UnknownPlayer(playerName);
    }
}
