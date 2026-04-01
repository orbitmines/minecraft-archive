package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers.LocationSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Date;
import java.util.UUID;

public class SurvivalHome extends OMMySQLModel<SurvivalHome, SurvivalHome.column> {

    public static MySQLTable TABLE = new MySQLTable("survival_homes", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private UUID uuid;
    @Getter @Setter private String name;
    @Getter @Setter private Location location;
    @Getter @Setter private Date lastUsageOn;
    @Getter private int timesUsed;
    @Getter private Date createdOn;

    public SurvivalHome() {
    }

    public SurvivalHome(UUID uuid, String name, Location location) {
        this.uuid = uuid;
        this.name = name;
        this.location = location;
        this.createdOn = DateUtils.now();
    }

    public void addTimesUsed() {
        this.timesUsed++;
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.UUID, column.NAME)
        );

        super.insert();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.uuid = getUUID(column.UUID);
        this.name = getString(column.NAME);
        this.location = new LocationSerializer().deserialize(getJson(column.LOCATION));
        this.lastUsageOn = getDate(column.LAST_USAGE_ON, DateUtils.DATE_TIME_FORMAT);
        this.timesUsed = getInteger(column.TIMES_USED);
        this.createdOn = getDate(column.CREATED_ON, DateUtils.DATE_TIME_FORMAT);
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
        return column.CREATED_ON;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case UUID:
                return stringify(this.uuid);
            case NAME:
                return stringify(this.name);
            case LOCATION:
                return stringify(new LocationSerializer().serialize(this.location));
            case LAST_USAGE_ON:
                return stringify(this.lastUsageOn, DateUtils.DATE_TIME_FORMAT);
            case TIMES_USED:
                return stringify(this.timesUsed);
            case CREATED_ON:
                return stringify(this.createdOn, DateUtils.DATE_TIME_FORMAT);
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
        NAME(new MySQLColumn("name", Column.Type.VARCHAR)),
        LOCATION(new MySQLColumn("location", Column.Type.TEXT)),
        LAST_USAGE_ON(new MySQLColumn("last_usage_on", Column.Type.DATETIME)),
        TIMES_USED(new MySQLColumn("times_used", Column.Type.INT)),
        CREATED_ON(new MySQLColumn("created_on", Column.Type.DATETIME));

        @Getter private final MySQLColumnInstance column;
    }
}
