package com.orbitmines.archive.minecraft._2019.utils.database.lib;

/*
    Created By Robin Egberts On 3/11/2019
    Copyrighted By OrbitMines ©2019
*/

import lombok.Getter;

public class Index {

    @Getter private String name;
    @Getter private Column[] columns;

    public Index(String name, Column[] columns) {
        this.name = name;
        this.columns = columns;
    }
}
