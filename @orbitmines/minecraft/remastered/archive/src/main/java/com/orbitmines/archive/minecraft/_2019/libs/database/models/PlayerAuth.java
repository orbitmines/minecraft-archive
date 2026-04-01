package com.orbitmines.archive.minecraft._2019.libs.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLBaseQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

public class PlayerAuth extends OMMySQLModel<PlayerAuth, PlayerAuth.column> {

    public static MySQLTable TABLE = new MySQLTable("player_auth", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private String secret; //TODO encrypt

    public PlayerAuth() {
    }

    public PlayerAuth(UUID uuid, String secret) {
        this.uuid = uuid;
        this.secret = secret;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.secret = getString(column.SECRET);
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
            case SECRET:
                return stringify(this.secret);
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
        SECRET(new MySQLColumn("secret", Column.Type.TEXT).notNull());

        @Getter private final MySQLColumnInstance column;
    }
}
