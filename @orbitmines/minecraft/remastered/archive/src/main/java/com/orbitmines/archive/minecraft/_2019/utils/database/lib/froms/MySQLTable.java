package com.orbitmines.archive.minecraft._2019.utils.database.lib.froms;

/*
    Created By Robin Egberts On 4/7/2019
    Copyrighted By OrbitMines ©2019
*/

import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Constraint;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;

import java.util.ArrayList;
import java.util.List;

public class MySQLTable extends Table {

    public MySQLTable(String name, MySQLColumnInstance... columns) {
        super(name, columns);

        List<MySQLColumnKey> keys = new ArrayList<>();

        for (MySQLColumnInstance c : columns) {
            if (c instanceof MySQLColumnKey) {
                MySQLColumnKey column = (MySQLColumnKey) c;
                if (column.getKey() == ColumnKey.Key.PRIMARY)
                    keys.add(column);
            }
        }

        if (keys.size() == 0)
            return;

        this.addConstraint(new MySQLConstraint(name, Constraint.Type.PRIMARY, keys.toArray(new MySQLColumnKey[0])));
    }
}
