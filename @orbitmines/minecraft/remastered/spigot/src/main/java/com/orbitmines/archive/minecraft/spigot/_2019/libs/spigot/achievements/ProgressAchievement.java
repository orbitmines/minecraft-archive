package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import lombok.Getter;

@Deprecated
public abstract class ProgressAchievement extends AchievementHandler {

    @Getter protected long total;

    public ProgressAchievement(Achievement achievement, long total) {
        super(achievement);

        this.total = total;
    }

    public abstract long getCurrent(OMPlayer omp);

    @Override
    public boolean hasCompleted(OMPlayer omp) {
        return getProgress(omp) >= 1;
    }

    @Override
    public String getName(OMPlayer omp) {
        return super.getName(omp) + " §d§l" + NumberUtils.locale(getCurrent(omp)) + " §7§l/ " + NumberUtils.locale(total) + " (§d§l" + ((int) (getProgress(omp) * 100)) + "%§7§l)";
    }

    /* Return float between 0 and 1 */
    public float getProgress(OMPlayer omp) {
        return (float) getCurrent(omp) / (float) total;
    }
}
