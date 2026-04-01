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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

public class SurvivalClaimMember extends OMMySQLModel<SurvivalClaimMember, SurvivalClaimMember.column> {

    public static MySQLTable TABLE = new MySQLTable("survival_claim_members", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private Long claimId;
    @Getter private UUID uuid;
    @Getter @Setter private SurvivalClaim.Permission permission;
    @Getter private Date createdAt;

    public SurvivalClaimMember() {
    }

    public SurvivalClaimMember(SurvivalClaim claim, UUID uuid, SurvivalClaim.Permission permission) {
        this.claimId = claim.getId();
        this.uuid = uuid;
        this.permission = permission;
        this.createdAt = DateUtils.now();
    }

    public boolean hasPermission(SurvivalClaim.Permission permission) {
        return this.permission.ordinal() >= permission.ordinal();
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.CLAIM_ID, column.UUID)
        );

        super.insert();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.claimId = getLong(column.CLAIM_ID);
        this.uuid = getUUID(column.UUID);
        this.permission = getEnum(column.PERMISSION, SurvivalClaim.Permission.class);
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
            case CLAIM_ID:
                return stringify(this.claimId);
            case UUID:
                return stringify(this.uuid);
            case PERMISSION:
                return stringify(this.permission);
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
        CLAIM_ID(new MySQLColumn("claim_id", Column.Type.BIGINT).indexed()),
        UUID(new MySQLColumn("uuid", Column.Type.VARCHAR, 36).indexed()),
        PERMISSION(new MySQLColumn("permission", Column.Type.VARCHAR)),
        CREATED_AT(new MySQLColumn("created_at", Column.Type.DATETIME));

        @Getter private final MySQLColumnInstance column;
    }
}
