package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;

@Deprecated
public class EmptyAchievementHandler extends AchievementHandler {

    public EmptyAchievementHandler(Achievement achievement) {
        super(achievement);
    }

    @Override
    public boolean hasCompleted(OMPlayer omp) {
        return false;
    }
}
