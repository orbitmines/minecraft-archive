package com.orbitmines.archive.minecraft._2019.libs.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class TimePlayed extends OMMySQLModel<TimePlayed, TimePlayed.column> {

    public static MySQLTable TABLE = new MySQLTable("time_played", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private Server server;
    @Getter private long seconds;

    public TimePlayed() {
    }

    public TimePlayed(UUID uuid, Server server) {
        this.uuid = uuid;
        this.server = server;
        this.seconds = 0L;
    }

    public void addSeconds(long seconds) {
        this.seconds += seconds;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.server = getEnum(column.SERVER, Server.class);
        this.seconds = getLong(column.SECONDS);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.SERVER);
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
            case SECONDS:
                return stringify(this.seconds);
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
        SERVER(new MySQLColumn("server", Column.Type.VARCHAR).indexed()),
        SECONDS(new MySQLColumn("seconds", Column.Type.BIGINT));

        @Getter private final MySQLColumnInstance column;
    }
}
