package com.orbitmines.minecraft.spigot.servers.fog.util;

import com.orbitmines.archive.minecraft._2019.utils.language.Language;

/**
 * Translation helpers for contexts where there is no specific viewer (entity custom
 * names, global boss bars, log messages). Resolves against the server's default
 * Language ({@link Language#ENGLISH}).
 */
public final class Fogi18n {

    private Fogi18n() {}

    public static final String NAMESPACE = "fog";

    public static String defaultText(String key) {
        return Language.ENGLISH.getString(NAMESPACE, key);
    }

    public static String defaultText(String key, Object... args) {
        return Language.ENGLISH.getString(NAMESPACE, key, args);
    }
}
