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
import org.bukkit.Location;
import org.bukkit.block.Biome;

import java.util.Date;

@AllArgsConstructor
public class SurvivalRegion extends OMMySQLModel<SurvivalRegion, SurvivalRegion.column> {

    public static MySQLTable TABLE = new MySQLTable("survival_regions", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private Location location;
    @Getter private Location beaconLocation;
    @Getter private int inventoryX;
    @Getter private int inventoryY;
    @Getter private boolean underWater;
    @Getter private Biome biome;
    @Getter private Date createdOn;

    public SurvivalRegion() {
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.INVENTORY_X, column.INVENTORY_Y)
        );

        super.insert();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.location = new LocationSerializer().deserialize(getJson(column.LOCATION));
        this.beaconLocation = new LocationSerializer().deserialize(getJson(column.BEACON_LOCATION));
        this.inventoryX = getInteger(column.INVENTORY_X);
        this.inventoryY = getInteger(column.INVENTORY_Y);
        this.underWater = getBoolean(column.UNDER_WATER);
        try {
            this.biome = getEnum(column.BIOME, Biome.class);
        } catch(IllegalArgumentException ex) {
            this.biome = location.getWorld().getBiome(location.getBlockX(), location.getBlockZ());
            update(column.BIOME);
        }
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
            case LOCATION:
                return stringify(new LocationSerializer().serialize(this.location));
            case BEACON_LOCATION:
                return stringify(new LocationSerializer().serialize(this.beaconLocation));
            case INVENTORY_X:
                return stringify(this.inventoryX);
            case INVENTORY_Y:
                return stringify(this.inventoryY);
            case UNDER_WATER:
                return stringify(this.underWater);
            case BIOME:
                return stringify(this.biome);
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
        LOCATION(new MySQLColumn("location", Column.Type.TEXT)),
        BEACON_LOCATION(new MySQLColumn("beacon_location", Column.Type.TEXT)),
        INVENTORY_X(new MySQLColumn("inventory_x", Column.Type.INT)),
        INVENTORY_Y(new MySQLColumn("inventory_y", Column.Type.INT)),
        UNDER_WATER(new MySQLColumn("under_water", Column.Type.TINYINT, 1)),
        BIOME(new MySQLColumn("biome", Column.Type.VARCHAR)),
        CREATED_ON(new MySQLColumn("created_on", Column.Type.DATETIME));

        @Getter private final MySQLColumnInstance column;
    }
}
