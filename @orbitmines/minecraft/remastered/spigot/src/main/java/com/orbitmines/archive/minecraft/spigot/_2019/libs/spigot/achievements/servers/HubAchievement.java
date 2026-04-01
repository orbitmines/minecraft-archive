package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.Achievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.AchievementHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.EmptyAchievementHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.StoredProgressAchievement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Deprecated
public enum HubAchievement implements Achievement {

    DISCORD_LINK(
        new LootItem(LootItem.Type.PRISMS, Rarity.COMMON, 1250)
    ),
    ORBITMINES_SUPPORTER(
        new LootItem(LootItem.Type.SOLARS, Rarity.EPIC, 1000)
    );

    static {
        DISCORD_LINK.handler = new EmptyAchievementHandler(DISCORD_LINK);

        ORBITMINES_SUPPORTER.handler = new StoredProgressAchievement(ORBITMINES_SUPPORTER, 250) {
            @Override
            public long getCurrent(OMPlayer player) {
                long count = 0;

                List<MonthlyVotes> votes = player.getMonthlyVotes(true);
                for (MonthlyVotes vote : votes) {
                    count += vote.getVotes();
                }

                return count;
            }
        };
    }

    @Getter private LootItem[] rewards;
    @Getter @Setter private AchievementHandler handler;

    HubAchievement(LootItem... rewards) {
        this.rewards = rewards;
    }

    @Override
    public int getId() {
        return ordinal();
    }

    @Override
    public Server getServer() {
        return Server.HUB;
    }
}
