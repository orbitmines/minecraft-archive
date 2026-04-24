package com.orbitmines.minecraft.spigot.servers.fog.database;

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
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

import java.util.UUID;

public class FoGPlayerModel extends OMMySQLModel<FoGPlayerModel, FoGPlayerModel.column> {

    public static MySQLTable TABLE = new MySQLTable("fog_players", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter @Setter private Long activeRunId;

    public FoGPlayerModel() {
    }

    public FoGPlayerModel(UUID uuid) {
        this.uuid = uuid;
        this.activeRunId = null;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.activeRunId = getLong(column.ACTIVE_RUN_ID);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID);
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
            case ACTIVE_RUN_ID:
                return stringify(this.activeRunId);
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
        UUID(new MySQLColumnKey("uuid", Column.Type.VARCHAR, ColumnKey.Key.PRIMARY, 36)),
        ACTIVE_RUN_ID(new MySQLColumn("active_run_id", Column.Type.BIGINT));

        @Getter private final MySQLColumnInstance column;
    }

    public static FoGPlayerModel findOrInitializeBy(UUID uuid) {
        FoGPlayerModel model = findBy(FoGPlayerModel.class, column.UUID.is(uuid));
        if (model != null)
            return model;
        return new FoGPlayerModel(uuid);
    }
}
