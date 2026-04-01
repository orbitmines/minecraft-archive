package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public final static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Date now() {
        return new Date(Calendar.getInstance().getTimeInMillis());
    }

    public static Date parse(String string, SimpleDateFormat format) {
        try {
            return format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String format(Date date, SimpleDateFormat format) {
        return date != null ? format.format(date) : null;
    }

    public static Month getMonth() {
        return Month.of(Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    public static Month getPrevMonth(int offset) {
        return getMonth().minus(offset);
    }

    public static Month getNextMonth(int offset) {
        return getMonth().plus(offset);
    }

    public static String humanFriendlyMonth() {
        return humanFriendlyMonth(getMonth());
    }

    public static String humanFriendlyMonth(Month month) {
        return month.toString().substring(0, 1).toUpperCase() + month.toString().substring(1).toLowerCase();
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
