package com.orbitmines.archive.minecraft._2019.utils.database.lib.froms;

/*
    Created By Robin Egberts On 4/7/2019
    Copyrighted By OrbitMines ©2019
*/

import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;

public class MySQLColumnKey<T> extends MySQLColumnInstance<T, MySQLColumnKey> implements ColumnKey {

    private Key key;

    public MySQLColumnKey(String name, Type type, Key key, int... args) {
        super(name, type, args);

        this.key = key;
    }

    @Override
    protected MySQLColumnKey getInstance() {
        return this;
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public MySQLColumnKey defaultValue(T value) {
        return super.defaultValue(value);
    }

    @Override
    public MySQLColumnKey defaultStatement(T value) {
        return super.defaultStatement(value);
    }

    @Override
    public MySQLColumnKey notNull() {
        return super.notNull();
    }

    @Override
    public MySQLColumnKey indexed() {
        return super.indexed();
    }

    @Override
    public MySQLColumnKey autoIncrement() {
        return super.autoIncrement();
    }
}
