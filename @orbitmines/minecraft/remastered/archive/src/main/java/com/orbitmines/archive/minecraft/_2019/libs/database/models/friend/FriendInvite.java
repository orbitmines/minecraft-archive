package com.orbitmines.archive.minecraft._2019.libs.database.models.friend;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

public class FriendInvite extends OMMySQLModel<FriendInvite, FriendInvite.column> {

    public static MySQLTable TABLE = new MySQLTable("friend_invites", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private UUID friendUuid;
    @Getter private Date invitedAt;

    public FriendInvite() {
    }

    public FriendInvite(UUID uuid, UUID friendUuid) {
        this.uuid = uuid;
        this.friendUuid = friendUuid;
        this.invitedAt = DateUtils.now();
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.friendUuid = getUUID(column.FRIEND_UUID);
        this.invitedAt = getDate(column.INVITED_AT, DateUtils.DATE_TIME_FORMAT);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.FRIEND_UUID);
    }

    @Override
    protected column getSortedIdentifier() {
        return column.INVITED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case UUID:
                return stringify(this.uuid);
            case FRIEND_UUID:
                return stringify(this.friendUuid);
            case INVITED_AT:
                return stringify(this.invitedAt, DateUtils.DATE_TIME_FORMAT);
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
        FRIEND_UUID(new MySQLColumn("friend_uuid", Column.Type.VARCHAR, 36).indexed()),
        INVITED_AT(new MySQLColumn("invited_at", Column.Type.DATETIME).indexed());

        @Getter private final MySQLColumnInstance column;
    }
}
