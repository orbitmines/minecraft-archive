package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.language.Language;

public class TimeUtils {

    public static String humanFriendlyTimer(Language language, long millis) {
        return humanFriendlyTimer(language, millis, false);
    }
    public static String humanFriendlyTimer(Language language, long millis, boolean millisecondDecimal) {
        long seconds = (millis / 1000L);

        long d = ((seconds - (seconds % (60L * 60L * 24L))) / (60L * 60L * 24L));
        long h = (seconds / (60L * 60L)) % 24L;
        long m = (seconds / 60L) % 60L;
        long s = seconds % 60L;

        StringBuilder stringBuilder = new StringBuilder();

        if (d != 0)
            stringBuilder.append(d).append(getShortTranslation(language, Unit.DAYS));

        if (h != 0)
            stringBuilder.append(h).append(getShortTranslation(language, Unit.HOURS));

        if (m != 0 && d == 0)
            stringBuilder.append(m).append(getShortTranslation(language, Unit.MINUTES));

        if (s != 0 && d == 0 && h == 0) {
            if (millisecondDecimal)
                stringBuilder.append(String.format("%.1f", (double) s + ((double) (millis % 1000L)) / 1000D));
            else
                stringBuilder.append(s);

            stringBuilder.append(getShortTranslation(language, Unit.SECONDS));
        }

        return stringBuilder.toString();
    }

    /* 20 days */
    public static String humanFriendlyTimeUnit(Language language, long millis) {
        return humanFriendlyTimeUnit(language, millis, EnumUtils.last(Unit.class));
    }
    public static String humanFriendlyTimeUnit(Language language, long millis, Unit maxUnit) {
        long s = (millis / 1000L);

        int M = months(s);
        int w = weeks(s);
        int d = days(s);
        int h = hours(s);
        int m = minutes(s);

        if (M != 0 && maxUnit.ordinal() >= Unit.MONTHS.ordinal())
            return NumberUtils.locale(M) + " " + getTranslation(language, Unit.MONTHS, M == 1);
        else if (w != 0 && maxUnit.ordinal() >= Unit.WEEKS.ordinal())
            return NumberUtils.locale(w) + " " + getTranslation(language, Unit.WEEKS, w == 1);
        else if (d != 0 && maxUnit.ordinal() >= Unit.DAYS.ordinal())
            return NumberUtils.locale(d) + " " + getTranslation(language, Unit.DAYS, d == 1);
        else if (h != 0 && maxUnit.ordinal() >= Unit.HOURS.ordinal())
            return NumberUtils.locale(h) + " " + getTranslation(language, Unit.HOURS, h == 1);
        else if (m != 0 && maxUnit.ordinal() >= Unit.MINUTES.ordinal())
            return NumberUtils.locale(m) + " " + getTranslation(language, Unit.MINUTES, m == 1);
        else if (s != 0 && maxUnit.ordinal() >= Unit.SECONDS.ordinal())
            return NumberUtils.locale(s) + " " + getTranslation(language, Unit.SECONDS, s == 1);
        else
            return "-";
    }

    public static String getTranslation(Language language, Unit unit, boolean singular) {
        return language.getString("global", "time." + unit.translationNamespace + "." + (singular ? "singular" : "plural"));
    }

    public static String getShortTranslation(Language language, Unit unit) {
        return language.getString("global", "time." + unit.translationNamespace + ".short");
    }

    public static int months(long seconds) {
        return (int) (seconds / (60L * 60L * 24L * 30L));
    }

    public static int weeks(long seconds) {
        return (int) (seconds / (60L * 60L * 24L * 7L));
    }

    public static int days(long seconds) {
        return (int) (seconds / (60L * 60L * 24L));
    }

    public static int hours(long seconds) {
        return (int) (seconds / (60L * 60L));
    }

    public static int minutes(long seconds) {
        return (int) (seconds / 60L);
    }

    public enum Unit {

        SECONDS("second"),
        MINUTES("minute"),
        HOURS("hour"),
        DAYS("day"),
        WEEKS("week"),
        MONTHS("month");

        private final String translationNamespace;

        Unit(String translationNamespace) {
            this.translationNamespace = translationNamespace;
        }

        public static Unit getFromShortTranslation(Language language, String shortTranslation) {
            for (Unit unit : Unit.values()) {
                if (getShortTranslation(language, unit).equals(shortTranslation))
                    return unit;
            }
            return null;
        }
    }
}
