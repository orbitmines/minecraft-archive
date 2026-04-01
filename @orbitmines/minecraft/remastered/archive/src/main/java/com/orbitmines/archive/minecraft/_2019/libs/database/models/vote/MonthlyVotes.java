package com.orbitmines.archive.minecraft._2019.libs.database.models.vote;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Month;
import java.util.UUID;

public class MonthlyVotes extends OMMySQLModel<MonthlyVotes, MonthlyVotes.column> {

    public static MySQLTable TABLE = new MySQLTable("monthly_votes", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private Month month;
    @Getter private int year;
    @Getter private int votes;

    public MonthlyVotes() {
    }

    public MonthlyVotes(UUID uuid, Month month, int year, int votes) {
        this.uuid = uuid;
        this.month = month;
        this.year = year;
        this.votes = votes;
    }

    public void addVote() {
        votes++;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.month = getEnum(column.MONTH, Month.class);
        this.year = getInteger(column.YEAR);
        this.votes = getInteger(column.VOTES);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.MONTH, column.YEAR);
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
            case MONTH:
                return stringify(this.month);
            case YEAR:
                return stringify(this.year);
            case VOTES:
                return stringify(this.votes);
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
        MONTH(new MySQLColumn("month", Column.Type.VARCHAR).indexed()),
        YEAR(new MySQLColumn("year", Column.Type.INT).indexed()),
        VOTES(new MySQLColumn("votes", Column.Type.INT));

        @Getter private final MySQLColumnInstance column;
    }

    public static MonthlyVotes findOrInitializeBy(UUID uuid, Month month, int year) {
        MonthlyVotes votes = findBy(MonthlyVotes.class, column.UUID.is(uuid), column.MONTH.is(month), column.YEAR.is(year));

        if (votes != null)
            return votes;

        votes = new MonthlyVotes(uuid, month, year, 0);
        return votes;
    }
}
