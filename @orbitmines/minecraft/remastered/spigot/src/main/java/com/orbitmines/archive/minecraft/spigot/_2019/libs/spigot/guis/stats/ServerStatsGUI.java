package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.stats;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.PlayerAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUICenterHelper;

public class ServerStatsGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends GUI<P> implements GUICenterHelper<P, PlayerAchievement> {

    public ServerStatsGUI(S server, P viewer, P key, Server serverType) {
        super(54, "§0§lStats (" + key.getName(Name.RAW) + ")", viewer);

        set(0, 0, new Item<P, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Lime Arrow Left", SkullTexture.LIME_ARROW_LEFT, 1, "§a« " + viewer.translate("spigot", "player.stats.back_to_stats"));
        }).click(event -> {
            new StatsGUI<>(server, viewer, key).open();
        }));

        set(1, 4, StatsGUI.getItem(viewer, key, serverType, false));

        center(3, () -> key.getAchievements(serverType, false));
    }

    @Override
    public GUI<P> getInstance() {
        return this;
    }

    @Override
    public Item<P, MutableItemBuilder> getItem(PlayerAchievement object) {
        return new Item<>(() -> object.getAchievement().getHandler().getItemBuilder(this.viewer));
    }
}
