package com.orbitmines.archive.minecraft._2019.utils.database.lib.froms;

/*
    Created By Robin Egberts On 4/7/2019
    Copyrighted By OrbitMines ©2019
*/

public class MySQLColumn<T> extends MySQLColumnInstance<T, MySQLColumn> {

    public MySQLColumn(String name, Type type, int... args) {
        super(name, type, args);
    }

    @Override
    protected MySQLColumn getInstance() {
        return this;
    }

    @Override
    public MySQLColumn defaultValue(T value) {
        return super.defaultValue(value);
    }

    @Override
    public MySQLColumn defaultStatement(T value) {
        return super.defaultStatement(value);
    }

    @Override
    public MySQLColumn notNull() {
        return super.notNull();
    }

    @Override
    public MySQLColumn indexed() {
        return super.indexed();
    }

    @Override
    public MySQLColumn autoIncrement() {
        return super.autoIncrement();
    }
}
