package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;

@Deprecated /* TODO: REFACTOR */
public interface Achievement {

    int getId();

    default String getName(OMPlayer player) {
        return player.translate("spigot", getNamespace() + ".name");
    }

    Server getServer();

    LootItem[] getRewards();

    default String[] getDescription(OMPlayer player) {
        return player.getLanguage().getStringArray("spigot", getNamespace() + ".description");
    }

    AchievementHandler getHandler();

    String toString();

    default String getNamespace() {
        return "player.achievement." + getServer().toString().toLowerCase() + "." + toString().toLowerCase();
    }

}
