package com.orbitmines.archive.minecraft._2019.libs.player;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;

public interface Languageable {

    Language getLanguage();

    void sendMessage(String namespace, String key);

    void sendMessage(String namespace, String key, Object... args);

    void sendRawMessage(String prefix, Color color, String message);

    void sendMessage(String prefix, Color color, String key);

    void sendMessage(String prefix, Color color, String key, Object... args);

    void sendMessage(String prefix, Color color, String namespace, String key);

    void sendMessage(String prefix, Color color, String namespace, String key, Object... args);

    default String translate(String key) {
        return getLanguage().getString(key);
    }

    default String translate(String key, Object... args) {
        return getLanguage().getString(key, args);
    }

    default String translate(String namespace, String key) {
        return getLanguage().getString(namespace, key);
    }

    default String translate(String namespace, String key, Object... args) {
        return getLanguage().getString(namespace, key, args);
    }
}
