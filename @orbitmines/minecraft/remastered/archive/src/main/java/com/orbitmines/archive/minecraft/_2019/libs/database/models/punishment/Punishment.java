package com.orbitmines.archive.minecraft._2019.libs.database.models.punishment;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Where;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class Punishment extends OMMySQLModel<Punishment, Punishment.column> {

    public static MySQLTable TABLE = new MySQLTable("punishments", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private UUID uuid;
    @Getter private Type type;
    @Getter private UUID punishedBy;
    @Getter private Date punishedAt;
    @Getter private Date punishedUntil;
    @Getter private Duration durationType;
    @Getter private Integer durationCount;
    @Getter private String reason;
    @Getter @Setter private Boolean pardoned;

    public Punishment() {
    }

    public Punishment(UUID uuid, Type type, UUID punishedBy, Date punishedAt, Date punishedUntil, Duration durationType, Integer durationCount, String reason) {
        this.uuid = uuid;
        this.type = type;
        this.punishedBy = punishedBy;
        this.punishedAt = punishedAt;
        this.punishedUntil = punishedUntil;
        this.durationType = durationType;
        this.durationCount = durationCount;
        this.reason = reason;
        this.pardoned = false;
    }

    public boolean isPardoned() {
        return this.pardoned;
    }

    public long getMillisLeft() {
        return punishedUntil.getTime() - System.currentTimeMillis();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.uuid = getUUID(column.UUID);
        this.type = getEnum(column.TYPE, Type.class);
        this.punishedBy = getUUID(column.PUNISHED_BY);
        this.punishedAt = getDate(column.PUNISHED_AT, DateUtils.DATE_TIME_FORMAT);
        this.punishedUntil = getDate(column.PUNISHED_UNTIL, DateUtils.DATE_TIME_FORMAT);
        this.durationType = getEnum(column.DURATION_TYPE, Duration.class);
        this.durationCount = getInteger(column.DURATION_COUNT);
        this.reason = getString(column.REASON);
        this.pardoned = getBoolean(column.PARDONED);
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.UUID, column.TYPE, column.PUNISHED_BY)
        );

        super.insert();
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.ID);
    }

    @Override
    protected column getSortedIdentifier() {
        return column.PUNISHED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case UUID:
                return stringify(this.uuid);
            case TYPE:
                return stringify(this.type);
            case PUNISHED_BY:
                return stringify(this.punishedBy);
            case PUNISHED_AT:
                return stringify(this.punishedAt, DateUtils.DATE_TIME_FORMAT);
            case PUNISHED_UNTIL:
                return stringify(this.punishedUntil, DateUtils.DATE_TIME_FORMAT);
            case DURATION_TYPE:
                return stringify(this.durationType);
            case DURATION_COUNT:
                return stringify(this.durationCount);
            case REASON:
                return stringify(this.reason);
            case PARDONED:
                return stringify(this.pardoned);
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

        ID(new MySQLColumnKey("id", Column.Type.BIGINT, ColumnKey.Key.PRIMARY).autoIncrement()),
        UUID(new MySQLColumn("uuid", Column.Type.VARCHAR, 36).indexed()),
        TYPE(new MySQLColumn("type", Column.Type.VARCHAR).indexed()),
        PUNISHED_BY(new MySQLColumn("punished_by", Column.Type.VARCHAR, 36).indexed()),
        PUNISHED_AT(new MySQLColumn("punished_at", Column.Type.DATETIME).notNull().indexed()),
        PUNISHED_UNTIL(new MySQLColumn("punished_until", Column.Type.DATETIME).indexed()),
        DURATION_TYPE(new MySQLColumn("duration_type", Column.Type.VARCHAR)),
        DURATION_COUNT(new MySQLColumn("duration_count", Column.Type.INT)),
        REASON(new MySQLColumn("reason", Column.Type.TEXT).notNull()),
        PARDONED(new MySQLColumn("pardoned", Column.Type.TINYINT, 1).notNull().indexed());

        @Getter private final MySQLColumnInstance column;
    }

    @AllArgsConstructor
    public enum Type {

        BAN(Color.MAROON),
        MUTE(Color.RED),
        WARNING(Color.GRAY);

        @Getter private final Color color;

    }

    @AllArgsConstructor
    public enum Duration {

        MINUTES(Calendar.MINUTE),
        HOURS(Calendar.HOUR),
        DAYS(Calendar.DATE),
        PERMANENT(0);

        @Getter private final int calendar;

    }

    public static Punishment getActive(UUID uuid, Type type) {
        if (type == Type.WARNING)
            throw new UnsupportedOperationException();

        return Punishment.findBy(Punishment.class,
            Punishment.column.UUID.is(uuid),
            Punishment.column.TYPE.is(type),
            Punishment.column.PUNISHED_UNTIL.is(Where.GREATER_THAN, DateUtils.format(DateUtils.now(), DateUtils.DATE_TIME_FORMAT)),
            Punishment.column.PARDONED.is(false)
        );
    }
}
