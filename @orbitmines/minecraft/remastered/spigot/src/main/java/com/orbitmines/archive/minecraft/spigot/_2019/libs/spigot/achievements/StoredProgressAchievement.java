package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.PlayerAchievement;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;

@Deprecated
public class StoredProgressAchievement extends ProgressAchievement {

    public StoredProgressAchievement(Achievement achievement, long total) {
        super(achievement, total);
    }

    /* Override this if it is stored elsewhere */
    @Override
    public long getCurrent(OMPlayer omp) {
        return getPlayerAchievement(omp).getProgress();
    }

    public void progress(OMPlayer omp, long count, boolean notifyOnComplete) {
        PlayerAchievement playerAchievement = getPlayerAchievement(omp);
        playerAchievement.progress(count);

        afterProgressUpdate(playerAchievement, omp, notifyOnComplete);
    }

    public void setHighest(OMPlayer omp, long count, boolean notifyOnComplete) {
        PlayerAchievement playerAchievement = getPlayerAchievement(omp);

        if (count <= playerAchievement.getProgress())
            return;

        playerAchievement.setProgress(count);

        afterProgressUpdate(playerAchievement, omp, notifyOnComplete);
    }

    private void afterProgressUpdate(PlayerAchievement playerAchievement, OMPlayer omp, boolean notifyOnComplete) {
        if (!playerAchievement.isCompleted() && hasCompleted(omp)) {
            playerAchievement.complete();

            if (!notifyOnComplete)
                return;

            // TODO notify
            return;
        }

        playerAchievement.update(PlayerAchievement.column.PROGRESS);
    }

    @Override
    public ItemBuilder getItemBuilder(OMPlayer omp) {
        return super.getItemBuilder(omp).addLore("").addLore("§d§l" + NumberUtils.locale(getCurrent(omp)) + " §7§l/ " + NumberUtils.locale(total));
    }
}
