package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Bukkit;

public class ConsoleUtils {

    public static void empty() {
        Bukkit.getLogger().info("");
    }

    public static void msg(String msg) {
        Bukkit.getConsoleSender().sendMessage(String.format("[%s] %s", SpigotServer.getInstance().getPluginName(), msg));
    }

    public static void warn(String msg) {
        Bukkit.getConsoleSender().sendMessage(String.format("[%s] §c%s", SpigotServer.getInstance().getPluginName(), msg));
    }

    public static void success(String msg) {
        Bukkit.getConsoleSender().sendMessage(String.format("[%s] §a%s", SpigotServer.getInstance().getPluginName(), msg));
    }
}
