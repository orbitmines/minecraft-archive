package com.orbitmines.archive.minecraft._2019.utils.database.lib;

/*
    Created By Robin Egberts On 2/27/2019
    Copyrighted By OrbitMines ©2019
*/

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class Constraint<C extends ColumnKey> {

    private C[] keys;
    private String name;

    @Getter
    private HashMap<Action, Response> responses;
    @Getter
    private Type type;

    //TODO: CHANGE THE PARAMETERS TO BE ABLE TO ADD THE CHECK CONSTRAINT
    @SafeVarargs
    public Constraint(String name, Type type, C... keys) {
        this.keys = keys;
        this.type = type;
        this.name = name;

        this.responses = new HashMap<>();

//        if (keys.length <= 0)
//            throw new IllegalArgumentException("SQLServerConstraint doesn't contain any keys!");
    }

    /* SETTER */
    public final void addResponses(Action action, Response response) {
        if (type != Type.FOREIGN)
            throw new IllegalStateException("Cannot add action and response to primary constraint!");

        this.responses.put(action, response);
    }

    /* GETTER */
    public final String getName() {
        return type.getName() + name;
    }

    /* ABSTRACT - GETTERS */
    protected abstract Primary getPrimary();

    protected abstract Foreign getForeign();

    protected abstract Unique getUnique();

    /* OVERRIDABLE */
    @Override
    public final String toString() {
        switch (type) {
            case PRIMARY: {
                List<C> pks = new ArrayList<>();
                Arrays.stream(keys).filter(ColumnKey::isPrimary).forEach(pks::add);

                return getPrimary().toString(pks);
            }
            case FOREIGN: {
                List<C> pks = new ArrayList<>();
                Arrays.stream(keys).filter(ColumnKey::isPrimary).forEach(pks::add);

                List<C> fks = new ArrayList<>();
                Arrays.stream(keys).filter(ColumnKey::isForeign).forEach(fks::add);

//                if (!isSameType(pks, fks))
//                    return ""; Fook this

                return getForeign().toString(pks, fks);
            }
            case UNIQUE: {
                List<C> uni = new ArrayList<>();
                Arrays.stream(keys).filter(ColumnKey::isCandicate).forEach(uni::add);

                return getUnique().toString(uni);
            }
        }
        return null;
    }

    /* FOREIGN KEY METHODS */
    private boolean isSameType(List<ColumnKey> pks, List<ColumnKey> fks) {
        if (pks.size() != fks.size())
            throw new IllegalStateException(String.format("Missing some Keys in %s constraint!", name));

        for (int i = 0; i < pks.size(); i++) {
            ColumnKey pk = pks.get(i);
            ColumnKey fk = fks.get(i);

            if (pk.getKey() != fk.getKey())
                throw new IllegalStateException(String.format("The column: %s and %s aren't the same type in %s", pk.toString(), fk.toString(), name));

            if (!pk.equals(fk))
                throw new IllegalStateException(String.format("The column: %s and %s don't have the same arguments in %s", pk.toString(), fk.toString(), name));
        }

        return true;
    }

    /* SUB - CLASSES */
    public abstract class Primary {

        public abstract String toString(List<C> pks);

    }

    public abstract class Foreign {

        public abstract String toString(List<C> pks, List<C> fks);

    }

    public abstract class Check {

        public abstract String toString();

    }

    public abstract class Unique {

        public abstract String toString(List<C> uni);

    }


    /* SUB ENUM */
    public enum Type {

        PRIMARY("PK"),
        FOREIGN("FK"),
        UNIQUE("UQ");
//        CHECK("CK");

        private String shortName;

        Type(String shortName) {
            this.shortName = shortName;
        }

        public String getName() {
            return shortName;
        }
    }

    public enum Action {

        ON_DELETE("ON DELETE"),
        ON_UPDATE("ON UPDATE");

        private String query;

        Action(String query) {
            this.query = query;
        }

        @Override
        public String toString() {
            return query;
        }

    }

    public enum Response {

        NO_ACTION("NO ACTION"),
        RESTRICT("RESTRICT "),
        SET_DEFAULT("SET DEFAULT"),
        CASCADE("CASCADE"),
        SET_NULL("SET NULL");

        private String query;

        Response(String query) {
            this.query = query;
        }

        @Override
        public String toString() {
            return query;
        }
    }
}
