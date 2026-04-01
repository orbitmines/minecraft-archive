package com.orbitmines.archive.minecraft._2019.libs.database.models.punishment;

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

public class Pardon extends OMMySQLModel<Pardon, Pardon.column> {

    public static MySQLTable TABLE = new MySQLTable("pardons", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private Long punishmentId;
    @Getter private UUID pardonedBy;
    @Getter private Date pardonedAt;
    @Getter private String reason;

    public Pardon() {
    }

    public Pardon(Long punishmentId, UUID pardonedBy, String reason) {
        this.punishmentId = punishmentId;
        this.pardonedBy = pardonedBy;
        this.pardonedAt = DateUtils.now();
        this.reason = reason;
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.id = getLong(column.PUNISHMENT_ID);
        this.pardonedBy = getUUID(column.PARDONED_BY);
        this.pardonedAt = getDate(column.PARDONED_AT, DateUtils.DATE_TIME_FORMAT);
        this.reason = getString(column.REASON);
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.PUNISHMENT_ID)
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
        return column.PARDONED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case PUNISHMENT_ID:
                return stringify(this.punishmentId);
            case PARDONED_BY:
                return stringify(this.pardonedBy);
            case PARDONED_AT:
                return stringify(this.pardonedAt, DateUtils.DATE_TIME_FORMAT);
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
        PUNISHMENT_ID(new MySQLColumn("punishment_id", Column.Type.BIGINT).notNull().indexed()),
        PARDONED_BY(new MySQLColumn("pardoned_by", Column.Type.VARCHAR, 36).indexed()),
        PARDONED_AT(new MySQLColumn("pardoned_at", Column.Type.DATETIME).notNull().indexed()),
        REASON(new MySQLColumn("reason", Column.Type.TEXT).notNull());

        @Getter private final MySQLColumnInstance column;
    }
}
