package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models;

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
import lombok.Setter;

import java.util.UUID;

public class LobbyPreference extends OMMySQLModel<LobbyPreference, LobbyPreference.column> {

    public static MySQLTable TABLE = new MySQLTable("lobby_preferences", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter @Setter private Server server;
    @Getter @Setter private String worldFileName;

    public LobbyPreference() {
    }

    public LobbyPreference(UUID uuid, Server server, String worldFileName) {
        this.uuid = uuid;
        this.server = server;
        this.worldFileName = worldFileName;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.server = getEnum(column.SERVER, Server.class);
        this.worldFileName = getString(column.WORLD_FILE_NAME);
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
            case WORLD_FILE_NAME:
                return this.worldFileName;
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
        WORLD_FILE_NAME(new MySQLColumn("world_file_name", Column.Type.VARCHAR));

        @Getter private final MySQLColumnInstance column;
    }

    public static LobbyPreference findByPlayerAndServer(UUID uuid, Server server) {
        return findBy(LobbyPreference.class,
            column.UUID.is(uuid),
            column.SERVER.is(server)
        );
    }
}
