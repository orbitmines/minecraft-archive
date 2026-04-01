package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class NumberUtils {

    private static Map<Integer, String> romanSteps = new LinkedHashMap<>();

    static {
        romanSteps.put(1000, "M");
        romanSteps.put(900, "CM");
        romanSteps.put(500, "D");
        romanSteps.put(400, "CD");
        romanSteps.put(100, "C");
        romanSteps.put(90, "XC");
        romanSteps.put(50, "L");
        romanSteps.put(40, "XL");
        romanSteps.put(10, "X");
        romanSteps.put(9, "IX");
        romanSteps.put(5, "V");
        romanSteps.put(4, "IV");
        romanSteps.put(1, "I");
    }

    public static String toRoman(int number) {
        if (number <= 0)
            return "" + number;

        StringBuilder roman = new StringBuilder();

        for (Map.Entry<Integer, String> entry : romanSteps.entrySet()) {
            int key = entry.getKey();
            if (number / key != 0) {
                for (int i = 0; i < (number / key); i++) {
                    roman.append(romanSteps.get(key));
                }
                number = number % key;
            }
        }
        return roman.toString();
    }

    public static int parseInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public static double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public static int clamp(int x, int min, int max){
        return max < x ? max : x < min ? min : x;
    }

    public static String locale(long number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    public static String locale(double number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }
}
