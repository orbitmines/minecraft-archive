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
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;

import java.util.List;

public class AchievementsGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PaginatableGUI<P, P, PlayerAchievement> {

    public AchievementsGUI(S server, P viewer, P key) {
        super(54, "§0§lStats (" + key.getName(Name.RAW) + ")", viewer, key, 9, 1);

        set(0, 0, new Item<P, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Lime Arrow Left", SkullTexture.LIME_ARROW_LEFT, 1, "§a« " + viewer.translate("spigot", "player.stats.back_to_stats"));
        }, event -> {
            new StatsGUI<>(server, viewer, key).open();
        }));

        set(1, 4, StatsGUI.getItem(server, viewer, key, false));

        paginate(2, 0);

        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Magenta Arrow Left", SkullTexture.MAGENTA_ARROW_LEFT, 1, "§7« " + viewer.translate("spigot", "player.stats.achievements.paginate")));
        setNextPage(5, 8, () -> new PlayerSkullBuilder("Magenta Arrow Right", SkullTexture.MAGENTA_ARROW_RIGHT, 1, "§7" + viewer.translate("spigot", "player.stats.achievements.paginate") + " »"));
    }

    @Override
    public Item<P, ?> getItem(PageItem<PlayerAchievement> pageItem) {
        return new Item<P, MutableItemBuilder>(() -> {
            PlayerAchievement achievement = pageItem.getObject();

            if (achievement == null)
                return null;

            ItemBuilder builder = achievement.getAchievement().getHandler().getItemBuilder(this.key);
            builder.setDisplayName(builder.getDisplayName() + (achievement.getServer() != Server.HUB ? " §7(" + achievement.getServer().getDisplayName() + "§7)" : ""));

            return builder;
        });
    }

    @Override
    public List<PlayerAchievement> getCollection() {
        return this.key.getAchievements(false);
    }

    @Override
    public Class<PlayerAchievement> getTypeClass() {
        return PlayerAchievement.class;
    }
}
