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
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class Friend extends OMMySQLModel<Friend, Friend.column> {

    public static MySQLTable TABLE = new MySQLTable("friends", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private UUID friendUuid;
    @Getter @Setter private Boolean favorite;
    @Getter private Date friendsSince;

    public Friend() {
    }

    public Friend(UUID uuid, UUID friendUuid, boolean favorite) {
        this.uuid = uuid;
        this.friendUuid = friendUuid;
        this.favorite = favorite;
        this.friendsSince = DateUtils.now();
    }

    public boolean isFavorite() {
        return this.favorite;
    }

    public void deleteRelative() {
        Friend friend = findBy(Friend.class, column.UUID.is(this.friendUuid), column.FRIEND_UUID.is(this.uuid));
        friend.delete();
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.friendUuid = getUUID(column.FRIEND_UUID);
        this.favorite = getBoolean(column.FAVORITE);
        this.friendsSince = getDate(column.FRIENDS_SINCE, DateUtils.DATE_TIME_FORMAT);
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
        return column.FRIENDS_SINCE;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case UUID:
                return stringify(this.uuid);
            case FRIEND_UUID:
                return stringify(this.friendUuid);
            case FAVORITE:
                return stringify(this.favorite);
            case FRIENDS_SINCE:
                return stringify(this.friendsSince, DateUtils.DATE_TIME_FORMAT);
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
        FAVORITE(new MySQLColumn("favorite", Column.Type.TINYINT, 1).indexed()),
        FRIENDS_SINCE(new MySQLColumn("friends_since", Column.Type.DATETIME).indexed());

        @Getter private final MySQLColumnInstance column;
    }
}
