package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.settings;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerSettings;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;

import java.util.ArrayList;

public interface PlayerVisibility<S extends OMServer<S, P>, P extends OMPlayer<S, P> & PlayerVisibility<S, P>> {

    //TODO TO ALL LOBBIES

    P getInstance();

    default void updateVisibility() {
        P watcher = getInstance();

        SpigotServer.getInstance().runAsync(() -> {
            watcher.getSettings(false);
            for (P player : new ArrayList<>(watcher.server().getPlayers())) {
                player.getSettings(false);
            }

            SpigotServer.getInstance().runSync(() -> {
                for (P player : watcher.server().getPlayers()) {
                    updateVisibility(player);
                    player.updateVisibility(watcher);
                }
            });
        });
    }

    default void updateVisibility(P player) {
        P watcher = getInstance();

        switch (watcher.getSettings(PlayerSettings.Type.PLAYER_VISIBILITY, false).getLevel()) {

            case ENABLED:
                watcher.showPlayer(player);
                break;
            case ONLY_FRIENDS:
                if (watcher.isFriend(player))
                    watcher.showPlayer(player);
                else
                    tryHidePlayer(player);
                break;
            case ONLY_FAVORITE_FRIENDS:
                if (watcher.isFriend(player, true))
                    watcher.showPlayer(player);
                else
                    tryHidePlayer(player);
                break;
            case DISABLED:
                tryHidePlayer(player);
                break;
        }
    }

    default void tryHidePlayer(P player) {
        P watcher = getInstance();

        if (!player.isEligible(StaffRank.HELPER))
            watcher.hidePlayer(player);
        else
            watcher.showPlayer(player);
    }
}
