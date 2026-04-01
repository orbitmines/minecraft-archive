package com.orbitmines.archive.minecraft._2019.utils.database.lib.froms;

/*
    Created By Robin Egberts On 2/27/2019
    Copyrighted By OrbitMines ©2019
*/


import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Constraint;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;

import java.util.List;

public class MySQLConstraint extends Constraint<MySQLColumnKey> {

    public MySQLConstraint(String name, Type type, MySQLColumnKey... keys) {
        super(name, type, keys);
    }

    @Override
    public Primary getPrimary() {
        return new Primary() {
            @Override
            public String toString(List<MySQLColumnKey> pks) {
                StringBuilder sb = new StringBuilder("CONSTRAINT ").append("`").append(getName()).append("`").append(" ");

                sb.append("PRIMARY KEY (");

                for (MySQLColumnKey key : pks)
                    sb.append("`").append(key.getName()).append("`").append(", ");

                sb = new StringBuilder(sb.substring(0, sb.length() - 2)).append(")");

                return sb.toString();
            }
        };
    }

    @Override
    public Foreign getForeign() {
        return new Foreign() {
            @Override
            public String toString(List<MySQLColumnKey> pks, List<MySQLColumnKey> fks) {
                StringBuilder sb = new StringBuilder("CONSTRAINT ").append("`").append(getName()).append("`").append(" ");

                sb.append("FOREIGN KEY (");

                //loop of foreign keys
                for (MySQLColumnKey fk : fks)
                    sb.append("`").append(fk.getName()).append("`").append(", ");

                sb = new StringBuilder(sb.substring(0, sb.length() - 2)).append(") ").append("REFERENCES ").append(Table.getTable(pks.get(0))).append(" (");

                //creation of primary keys
                for (ColumnKey pk : pks)
                    sb.append("`").append(pk.toString()).append("`").append(", ");

                sb = new StringBuilder(sb.substring(0, sb.length() - 2)).append(") ");

                //addition of actions to the constraint
                for (Action ac : getResponses().keySet())
                    sb.append(ac.toString()).append(" ").append(getResponses().get(ac).toString());

                return sb.toString();
            }
        };
    }

    @Override
    public Unique getUnique() {
        return new Unique() {
            @Override
            public String toString(List<MySQLColumnKey> uni) {
                StringBuilder sb = new StringBuilder("CONSTRAINT ").append(getName()).append(" ");

                sb.append("UNIQUE (");

                for (MySQLColumnKey key : uni)
                    sb.append("`").append(key.getName()).append("`").append(", ");

                sb = new StringBuilder(sb.substring(0, sb.length() - 1)).append(")");

                return sb.toString();
            }
        };
    }
}
