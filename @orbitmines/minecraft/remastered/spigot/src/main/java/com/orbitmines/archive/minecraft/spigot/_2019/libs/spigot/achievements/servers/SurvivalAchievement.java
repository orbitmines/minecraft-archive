package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.Achievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.AchievementHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.StoredProgressAchievement;
import lombok.Getter;
import lombok.Setter;

@Deprecated
public enum SurvivalAchievement implements Achievement {

    DIAMONDS(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 3000),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 75)
    ),
    LOADS_OF_EXPERIENCE(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 5000),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 100)
    ),
    SALESMAN(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 7000),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 100)
    ),
    TIME_WITHERED_AWAY(
        new LootItem(LootItem.Type.PRISMS, Rarity.EPIC, 10000),
        new LootItem(LootItem.Type.SOLARS, Rarity.EPIC, 150)
    ),
    CROWDED(
        new LootItem(LootItem.Type.PRISMS, Rarity.EPIC, 15000),
        new LootItem(LootItem.Type.SOLARS, Rarity.EPIC, 175)
    );

    static {
        DIAMONDS.handler = new StoredProgressAchievement(DIAMONDS, 250);
        LOADS_OF_EXPERIENCE.handler = new StoredProgressAchievement(LOADS_OF_EXPERIENCE, 100);
        SALESMAN.handler = new StoredProgressAchievement(SALESMAN, 100000);
        TIME_WITHERED_AWAY.handler = new StoredProgressAchievement(TIME_WITHERED_AWAY, 15);
        CROWDED.handler = new StoredProgressAchievement(CROWDED, 10);
    }

    @Getter private LootItem[] rewards;
    @Getter @Setter private AchievementHandler handler;

    SurvivalAchievement(LootItem... rewards) {
        this.rewards = rewards;
    }

    @Override
    public int getId() {
        return ordinal();
    }

    @Override
    public Server getServer() {
        return Server.SURVIVAL;
    }
}
