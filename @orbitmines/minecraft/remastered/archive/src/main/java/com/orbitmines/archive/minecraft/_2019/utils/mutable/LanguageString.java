package com.orbitmines.archive.minecraft._2019.utils.mutable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.language.Language;

@FunctionalInterface
public interface LanguageString {

    String getKey();

    default String getString(Language language) {
        return language.getString(getKey());
    }

    default String getString(Language language, Object... args) {
        return language.getString(getKey(), args);
    }

    default String getString(Language language, String namespace) {
        return language.getString(namespace, getKey());
    }

    default String getString(Language language, String namespace, Object... args) {
        return language.getString(namespace, getKey(), args);
    }
}
