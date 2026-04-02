package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class KitPvPPlayerKitModel extends OMMySQLModel<KitPvPPlayerKitModel, KitPvPPlayerKitModel.column> {

    public static MySQLTable TABLE = new MySQLTable("kitpvp_player_kits", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private long kitId;
    @Getter @Setter private int unlockedLevel;
    @Getter @Setter private volatile int kills;
    @Getter @Setter private volatile int deaths;
    @Getter @Setter private int bestStreak;
    @Getter @Setter private Date bestStreakAt;
    @Getter @Setter private double damageDealt;
    @Getter @Setter private int timesUsed;

    public KitPvPPlayerKitModel() {
    }

    public KitPvPPlayerKitModel(UUID uuid, long kitId, int unlockedLevel) {
        this.uuid = uuid;
        this.kitId = kitId;
        this.unlockedLevel = unlockedLevel;
        this.kills = 0;
        this.deaths = 0;
        this.bestStreak = 0;
        this.damageDealt = 0D;
        this.timesUsed = 0;
    }

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }

    public void addTimesUsed() {
        this.timesUsed++;
    }

    public void addDamageDealt(double amount) {
        this.damageDealt += amount;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.kitId = getLong(column.KIT_ID);
        this.unlockedLevel = getInteger(column.UNLOCKED_LEVEL);
        this.kills = getInteger(column.KILLS);
        this.deaths = getInteger(column.DEATHS);
        this.bestStreak = getInteger(column.BEST_STREAK);
        this.bestStreakAt = getDate(column.BEST_STREAK_AT, DateUtils.DATE_TIME_FORMAT);
        this.damageDealt = getDouble(column.DAMAGE_DEALT);
        this.timesUsed = getInteger(column.TIMES_USED);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.KIT_ID);
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
            case KIT_ID:
                return stringify(this.kitId);
            case UNLOCKED_LEVEL:
                return stringify(this.unlockedLevel);
            case KILLS:
                return stringify(this.kills);
            case DEATHS:
                return stringify(this.deaths);
            case BEST_STREAK:
                return stringify(this.bestStreak);
            case BEST_STREAK_AT:
                return stringify(this.bestStreakAt, DateUtils.DATE_TIME_FORMAT);
            case DAMAGE_DEALT:
                return stringify(this.damageDealt);
            case TIMES_USED:
                return stringify(this.timesUsed);
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

        UUID(new MySQLColumn("uuid", Column.Type.VARCHAR, 36)),
        KIT_ID(new MySQLColumn("kit_id", Column.Type.BIGINT)),
        UNLOCKED_LEVEL(new MySQLColumn("unlocked_level", Column.Type.INT)),
        KILLS(new MySQLColumn("kills", Column.Type.INT)),
        DEATHS(new MySQLColumn("deaths", Column.Type.INT)),
        BEST_STREAK(new MySQLColumn("best_streak", Column.Type.INT)),
        BEST_STREAK_AT(new MySQLColumn("best_streak_at", Column.Type.DATETIME)),
        DAMAGE_DEALT(new MySQLColumn("damage_dealt", Column.Type.DOUBLE, 16, 2)),
        TIMES_USED(new MySQLColumn("times_used", Column.Type.INT));

        @Getter private final MySQLColumnInstance column;
    }
}
