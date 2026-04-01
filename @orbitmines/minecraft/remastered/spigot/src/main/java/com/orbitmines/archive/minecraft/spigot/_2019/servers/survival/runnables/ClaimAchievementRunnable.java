package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.StoredProgressAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.SurvivalAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClaimAchievementRunnable extends PassiveRunnable<Survival> {

    private final Survival survival;

    private final Map<String, Integer> map;

    public ClaimAchievementRunnable(Survival survival) {
        super(survival, Interval.of(TimeUnit.SECOND, 10));

        this.survival = survival;

        this.map = new HashMap<>();
    }

    @Override
    public void onRun() {
        map.clear();

        for (SurvivalPlayer omp : survival.getPlayers()) {
            Claim claim = survival.getClaimHandler().getClaimAt(omp.getLocation(), true, omp.getLastClaim());

            if (claim == null)
                continue;

            omp.setLastClaim(claim);

            if (claim.getOwner() == null)
                continue;

            String uuid = claim.getOwner().toString();

            if (!map.containsKey(uuid))
                map.put(uuid, 1);
            else
                map.put(uuid, map.get(uuid) + 1);
        }

        for (String uuid : map.keySet()) {
            SurvivalPlayer owner = survival.getPlayer(UUID.fromString(uuid));

            if (owner == null)
                continue;

            StoredProgressAchievement handler = (StoredProgressAchievement) SurvivalAchievement.CROWDED.getHandler();
            handler.setHighest(owner, map.get(uuid), true);
        }
    }
}
