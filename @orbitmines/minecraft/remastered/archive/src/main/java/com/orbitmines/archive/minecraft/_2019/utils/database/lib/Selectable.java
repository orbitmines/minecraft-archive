package com.orbitmines.archive.minecraft._2019.utils.database.lib;

/*
    Created By Robin Egberts On 2/25/2019
    Copyrighted By OrbitMines ©2019
*/
@FunctionalInterface
public interface Selectable {

    String getQuery();

    static String getName(Selectable selectable) {
        if (selectable instanceof Column)
            return selectable.getQuery();

        String[] queries = selectable.getQuery().split("AS");

        if (queries.length == 1)
            return queries[0];

        return queries[queries.length - 1].replaceAll("'", "").trim();
    }
}
