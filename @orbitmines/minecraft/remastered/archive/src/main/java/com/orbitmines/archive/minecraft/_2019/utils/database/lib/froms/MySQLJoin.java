package com.orbitmines.archive.minecraft._2019.utils.database.lib.froms;

/*
    Created By Robin Egberts On 2/27/2019
    Copyrighted By OrbitMines ©2019
*/

import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Join;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;

public class MySQLJoin extends Join<MySQLJoin, MySQLColumnInstance> {

    public MySQLJoin(Type type, MySQLColumnInstance leftColumn, MySQLColumnInstance rightColumn) {
        super(type, leftColumn, rightColumn);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getLeftTable().toString()).append(" ")
                .append(getType().toString()).append(" ")
                .append(getRightTable().toString());

        if (getType() != Type.CROSS_JOIN)
            sb.append(String.format(" ON (%s = %s)", getLeftColumn(), getRightColumn()));

        sb.append(" ").append(getChild());

        return sb.toString().trim();
    }

    private String getChild() {
        StringBuilder sb = new StringBuilder();

        for (Join join : getChildren()) {

            Table table = this.getCommon(join);
            Table unused = this.getUncommon(table);

            Column commonColumn = table.containsColumn(join.getLeftColumn()) ? join.getLeftColumn() : join.getRightColumn();
            Column unusedColumn = !commonColumn.equals(getLeftColumn()) ? join.getRightColumn() : join.getLeftColumn();

            sb.append(join.getType().toString()).append(" ")
                    .append(unused.toString());

            if (join.getType() != Type.CROSS_JOIN)
                sb.append(String.format(" ON (%s = %s)", commonColumn.toString(), unusedColumn.toString()));

            sb.append(" ").append(join.getChildren()).trimToSize();
        }
        return sb.toString();
    }
}
