package com.orbitmines.archive.minecraft._2019.libs.database.models.vote;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.ServerList;
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
import java.util.concurrent.TimeUnit;

public class LastVote extends OMMySQLModel<LastVote, LastVote.column> {

    public static MySQLTable TABLE = new MySQLTable("last_votes", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private ServerList serverList;
    @Getter @Setter private Date lastVoteAt;

    public LastVote() {
    }

    public LastVote(UUID uuid, ServerList serverList, Date lastVoteAt) {
        this.uuid = uuid;
        this.serverList = serverList;
        this.lastVoteAt = lastVoteAt;
    }

    public boolean canVote() {
        if (lastVoteAt == null)
            return true;

        return System.currentTimeMillis() - lastVoteAt.getTime() >= TimeUnit.DAYS.toMillis(1);
    }

    public long getMillisLeft() {
        if (lastVoteAt == null)
            return 0L;

        return Math.max(0L, TimeUnit.DAYS.toMillis(1) - (System.currentTimeMillis() - lastVoteAt.getTime()));
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.serverList = getEnum(column.SERVER_LIST, ServerList.class);
        this.lastVoteAt = getDate(column.LAST_VOTE_AT, DateUtils.DATE_TIME_FORMAT);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.SERVER_LIST);
    }

    @Override
    protected column getSortedIdentifier() {
        return column.LAST_VOTE_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case UUID:
                return stringify(this.uuid);
            case SERVER_LIST:
                return stringify(this.serverList);
            case LAST_VOTE_AT:
                return stringify(this.lastVoteAt, DateUtils.DATE_TIME_FORMAT);
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
        SERVER_LIST(new MySQLColumn("server_list", Column.Type.VARCHAR).indexed()),
        LAST_VOTE_AT(new MySQLColumn("last_vote_at", Column.Type.DATETIME).indexed());

        @Getter private final MySQLColumnInstance column;
    }

    public static LastVote findOrInitializeBy(UUID uuid, ServerList serverList) {
        LastVote lastVote = findBy(LastVote.class, column.UUID.is(uuid), column.SERVER_LIST.is(serverList));

        if (lastVote != null)
            return lastVote;

        lastVote = new LastVote(uuid, serverList, null);
        return lastVote;
    }
}
