package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.Achievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.HubAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.KitPvPAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.SurvivalAchievement;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
public class PlayerAchievement extends OMMySQLModel<PlayerAchievement, PlayerAchievement.column> {

    public static MySQLTable TABLE = new MySQLTable("player_achievements", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private Server server;
    @Getter private Achievement achievement;
    @Getter private Boolean completed;
    @Getter @Setter private long progress;

    public PlayerAchievement() {
    }

    public PlayerAchievement(UUID uuid, Achievement achievement) {
        this.uuid = uuid;
        this.server = achievement.getServer();
        this.achievement = achievement;
        this.completed = false;
        this.progress = 0;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void complete() {
        this.completed = true;

        insertOrUpdate(
            column.COMPLETED,
            column.PROGRESS
        );
    }

    public void progress(long count) {
        progress += count;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.server = getEnum(column.SERVER, Server.class);
        this.achievement = byServer(this.server)[getInteger(column.ACHIEVEMENT)];
        this.completed = getBoolean(column.COMPLETED);
        this.progress = getLong(column.PROGRESS);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.SERVER, column.ACHIEVEMENT);
    }

    @Override
    protected column getSortedIdentifier() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case UUID:
                return stringify(this.uuid);
            case SERVER:
                return stringify(this.server);
            case ACHIEVEMENT:
                return stringify(this.achievement.getId());
            case COMPLETED:
                return stringify(this.completed);
            case PROGRESS:
                return stringify(this.progress);
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    protected column[] getColumns() {
        return column.values();
    }

    @AllArgsConstructor
    public enum column implements MySQLModelColumn {

        UUID(new MySQLColumn("uuid", Column.Type.VARCHAR, 36).indexed()),
        SERVER(new MySQLColumn("server", Column.Type.VARCHAR).notNull()),
        ACHIEVEMENT(new MySQLColumn("achievement", Column.Type.INT)),
        COMPLETED(new MySQLColumn("completed", Column.Type.TINYINT, 1)),
        PROGRESS(new MySQLColumn("progress", Column.Type.BIGINT));

        @Getter private final MySQLColumnInstance column;
    }

    public static Achievement[] byServer(Server server) {
        switch (server) {

            case HUB:
                return HubAchievement.values();
            case SURVIVAL:
                return SurvivalAchievement.values();
            case KITPVP:
                return KitPvPAchievement.values();
            default:
                return new Achievement[0];
        }
    }
}
