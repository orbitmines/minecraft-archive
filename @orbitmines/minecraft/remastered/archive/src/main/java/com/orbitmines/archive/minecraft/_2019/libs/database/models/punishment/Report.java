package com.orbitmines.archive.minecraft._2019.libs.database.models.punishment;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
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

public class Report extends OMMySQLModel<Report, Report.column> {

    public static MySQLTable TABLE = new MySQLTable("reports", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private Server server;
    @Getter private UUID uuid;
    @Getter private UUID reportedBy;
    @Getter private Date reportedAt;
    @Getter private String reason;

    public Report() {
    }

    public Report(Server server, UUID uuid, UUID reportedBy, String reason) {
        this.server = server;
        this.uuid = uuid;
        this.reportedBy = reportedBy;
        this.reportedAt = DateUtils.now();
        this.reason = reason;
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.server = getEnum(column.SERVER, Server.class);
        this.uuid = getUUID(column.UUID);
        this.reportedBy = getUUID(column.REPORTED_BY);
        this.reportedAt = getDate(column.REPORTED_AT, DateUtils.DATE_TIME_FORMAT);
        this.reason = getString(column.REASON);
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.SERVER, column.UUID, column.REPORTED_BY)
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
        return column.REPORTED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case SERVER:
                return stringify(this.server);
            case UUID:
                return stringify(this.uuid);
            case REPORTED_BY:
                return stringify(this.reportedBy);
            case REPORTED_AT:
                return stringify(this.reportedAt, DateUtils.DATE_TIME_FORMAT);
            case REASON:
                return stringify(this.reason);
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
        SERVER(new MySQLColumn("server", Column.Type.VARCHAR)),
        UUID(new MySQLColumn("uuid", Column.Type.VARCHAR, 36).indexed()),
        REPORTED_BY(new MySQLColumn("reported_by", Column.Type.VARCHAR, 36).indexed()),
        REPORTED_AT(new MySQLColumn("reported_at", Column.Type.DATETIME).notNull().indexed()),
        REASON(new MySQLColumn("reason", Column.Type.TEXT).notNull());

        @Getter private final MySQLColumnInstance column;
    }
}
