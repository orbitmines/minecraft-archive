package com.orbitmines.archive.minecraft._2019.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordBot;
import net.dv8tion.jda.api.entities.User;

public enum Environment {

    production,
    staging,
    development;

    public static boolean isProduction() {
        return Environment.production == get();
    }

    public static Environment get() {
        return Environment.valueOf(get("OM_ENV", Environment.development.toString()));
    }

    public static String get(String environmentVariable, String defaultValue) {
        String value = System.getenv(environmentVariable);

        return value == null ? defaultValue : value;
    }

    public static int get(String environmentVariable, int defaultValue) {
        String value = System.getenv(environmentVariable);

        return value == null ? defaultValue : Integer.parseInt(value);
    }

    public static long get(String environmentVariable, long defaultValue) {
        String value = System.getenv(environmentVariable);

        return value == null ? defaultValue : Long.parseLong(value);
    }

    public static double get(String environmentVariable, double defaultValue) {
        String value = System.getenv(environmentVariable);

        return value == null ? defaultValue : Double.parseDouble(value);
    }

    public static boolean get(String environmentVariable, boolean defaultValue) {
        String value = System.getenv(environmentVariable);

        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }

    public static String getEveryoneOrDev(DiscordBot bot) {
        String mentions;
        switch (Environment.get()) {
            case development:
                User user;
                try {
                    user = bot.getUserById(Environment.get("DEV_DISCORD_USER", null));
                } catch(NumberFormatException ex) {
                    user = null;
                }

                mentions = user != null ? user.getAsMention() : "**PLEASE SET YOUR `DEV_DISCORD_USER` ID IN YOUR `.env` file!**";
                break;
            default:
                mentions = bot.getRole(CustomRole.FIX_IT_FADI).getAsMention();
                break;
        }

        return mentions;
    }
}
