package com.orbitmines.archive.minecraft.spigot._2019.servers.creative;

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
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

public class CreativeWorldMember extends OMMySQLModel<CreativeWorldMember, CreativeWorldMember.column> {

    public static MySQLTable TABLE = new MySQLTable("creative_world_members", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private Long mapId;
    @Getter private UUID uuid;
    @Getter private Date createdAt;

    public CreativeWorldMember() {
    }

    public CreativeWorldMember(Long mapId, UUID uuid) {
        this.mapId = mapId;
        this.uuid = uuid;
        this.createdAt = DateUtils.now();
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.MAP_ID, column.UUID)
        );

        super.insert();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.mapId = getLong(column.MAP_ID);
        this.uuid = getUUID(column.UUID);
        this.createdAt = getDate(column.CREATED_AT, DateUtils.DATE_TIME_FORMAT);
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
        return column.CREATED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case MAP_ID:
                return stringify(this.mapId);
            case UUID:
                return stringify(this.uuid);
            case CREATED_AT:
                return stringify(this.createdAt, DateUtils.DATE_TIME_FORMAT);
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
        MAP_ID(new MySQLColumn("map_id", Column.Type.BIGINT).indexed()),
        UUID(new MySQLColumn("uuid", Column.Type.VARCHAR, 36).indexed()),
        CREATED_AT(new MySQLColumn("created_at", Column.Type.DATETIME));

        @Getter private final MySQLColumnInstance column;
    }
}
