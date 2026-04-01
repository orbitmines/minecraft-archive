package com.orbitmines.archive.minecraft._2019.utils.language;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public enum Language {

    DUTCH(new Locale("nl", "NL")),
    ENGLISH(Locale.US);

    private static String defaultNamespace;

    @Getter private final Locale locale;
    @Getter private Map<String, ResourceBundle> messages;

    static {
        initialize("global", false);
    }

    Language(Locale locale) {
        this.locale = locale;
        this.messages = new HashMap<>();
    }

    public String getTranslationOf(Language language) {
        return this.getString("global", "language." + language.toString().toLowerCase());
    }

    public String getString(String key) {
        return getString(defaultNamespace, key);
    }

    public String getString(String key, Object... args) {
        return getString(defaultNamespace, key, args);
    }

    public String getString(String namespace, String key) {
        return messages.get(namespace).getString(key);
    }

    public String getString(String namespace, String key, Object... args) {
        return String.format(messages.get(namespace).getString(key), args);
    }

    public String[] getStringArray(String namespace, String key) {
        return getString(namespace, key).split("\\|");
    }

    public String[] getStringArray(String namespace, String key, Object... args) {
        return getString(namespace, key, args).split("\\|");
    }

    public <O> O getObject(String namespace, String key) {
        return (O) messages.get(namespace).getObject(key);
    }

    public static void initialize(String namespace, boolean isDefault) {
        for (Language language : values()) {
            language.messages.put(namespace, ResourceBundle.getBundle(namespace, language.locale));
        }

        if (isDefault)
            defaultNamespace = namespace;
    }
}
