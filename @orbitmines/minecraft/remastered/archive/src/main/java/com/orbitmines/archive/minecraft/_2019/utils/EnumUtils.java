package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class EnumUtils {

    public static <E extends Enum<E>> boolean isFirst(Class<E> clazz, E constant) {
        return first(clazz) == constant;
    }

    public static <E extends Enum<E>> E first(Class<E> clazz) {
        return clazz.getEnumConstants()[0];
    }

    public static <E extends Enum<E>> E last(Class<E> clazz) {
        E[] constants = clazz.getEnumConstants();
        return constants[constants.length - 1];
    }

    public static <E extends Enum<E>> E valueOf(Class<E> clazz, String value) {
        for (E constant : clazz.getEnumConstants()) {
            if (value.equals(constant.toString()))
                return constant;
        }
        throw new IllegalArgumentException();
    }

    public static <E extends Enum<E>> E byOrdinal(Class<E> clazz, int ordinal) {
        return byOrdinal(clazz, ordinal, first(clazz));
    }

    public static <E extends Enum<E>> E byOrdinal(Class<E> clazz, int ordinal, E defaultConstant) {
        try {
            return clazz.getEnumConstants()[ordinal];
        } catch(IndexOutOfBoundsException ex) {
            return defaultConstant;
        }
    }

    public static <E extends Enum<E>> E random(Class<E> clazz) {
        return RandomUtils.randomFrom(clazz.getEnumConstants());
    }

    public static <E extends Enum<E>> E random(Class<E> clazz, E... excluding) {
        ArrayList<E> valuesList = new ArrayList<>(Arrays.asList(clazz.getEnumConstants()));
        valuesList.removeAll(Arrays.asList(excluding));

        return RandomUtils.randomFrom(valuesList);
    }

    public static <E extends Enum<E>> E next(Class<E> clazz, E current) {
        E[] constants = clazz.getEnumConstants();
        return constants[current.ordinal() == constants.length - 1 ? 0 : current.ordinal() + 1];
    }

    public static <E extends Enum<E>> E next(Class<E> clazz, E[] only, E current) {
        return next(clazz, Arrays.asList(only), current);
    }

    public static <E extends Enum<E>> E next(Class<E> clazz, Collection<E> only, E current) {
        E[] constants = clazz.getEnumConstants();

        while (true) {
            E next = constants[current.ordinal() == constants.length - 1 ? 0 : current.ordinal() + 1];

            if (only.contains(next))
                return next;

            current = next;
        }
    }
}
