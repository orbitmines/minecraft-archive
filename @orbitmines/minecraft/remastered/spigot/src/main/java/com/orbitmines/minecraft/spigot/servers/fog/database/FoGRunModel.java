package com.orbitmines.minecraft.spigot.servers.fog.database;

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
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/** Spawn coordinates are persisted on the world itself via {@code World#setSpawnLocation}; not in the DB. */
public class FoGRunModel extends OMMySQLModel<FoGRunModel, FoGRunModel.column> {

    public static MySQLTable TABLE = new MySQLTable("fog_runs", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter @Setter private UUID ownerUuid;
    @Getter @Setter private String difficulty;
    @Getter @Setter private String worldFileName;
    @Getter @Setter private String state;
    @Getter private Date createdAt;

    public FoGRunModel() {
    }

    public FoGRunModel(UUID ownerUuid, String difficulty, String worldFileName, String state) {
        this.ownerUuid = ownerUuid;
        this.difficulty = difficulty;
        this.worldFileName = worldFileName;
        this.state = state;
        this.createdAt = DateUtils.now();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.ownerUuid = getUUID(column.OWNER_UUID);
        this.difficulty = getString(column.DIFFICULTY);
        this.worldFileName = getString(column.WORLD_FILE_NAME);
        this.state = getString(column.STATE);
        this.createdAt = getDate(column.CREATED_AT, DateUtils.DATE_TIME_FORMAT);
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(localIdentifiers(column.OWNER_UUID, column.WORLD_FILE_NAME));
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
        return column.CREATED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {
            case ID: return stringify(this.id);
            case OWNER_UUID: return stringify(this.ownerUuid);
            case DIFFICULTY: return this.difficulty;
            case WORLD_FILE_NAME: return this.worldFileName;
            case STATE: return this.state;
            case CREATED_AT: return stringify(this.createdAt, DateUtils.DATE_TIME_FORMAT);
            default: throw new UnsupportedOperationException();
        }
    }

    @Override
    protected column[] getColumns() {
        return column.values();
    }

    @AllArgsConstructor
    public enum column implements MySQLModelColumn {
        ID(new MySQLColumnKey("id", Column.Type.BIGINT, ColumnKey.Key.PRIMARY).autoIncrement()),
        OWNER_UUID(new MySQLColumn("owner_uuid", Column.Type.VARCHAR, 36).indexed()),
        DIFFICULTY(new MySQLColumn("difficulty", Column.Type.VARCHAR).notNull()),
        WORLD_FILE_NAME(new MySQLColumn("world_file_name", Column.Type.VARCHAR).notNull()),
        STATE(new MySQLColumn("state", Column.Type.VARCHAR).notNull()),
        CREATED_AT(new MySQLColumn("created_at", Column.Type.DATETIME).indexed());

        @Getter private final MySQLColumnInstance column;
    }
}
