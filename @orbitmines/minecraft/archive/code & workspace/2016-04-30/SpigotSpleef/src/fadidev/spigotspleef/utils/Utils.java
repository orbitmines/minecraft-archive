package fadidev.spigotspleef.utils;

import org.bukkit.Bukkit;

/**
 * Created by Fadi on 30-4-2016.
 */
public class Utils {

    public static void sendConsoleEmpty(){
        Bukkit.getLogger().info("");
    }

    public static void sendConsoleMSG(String msg){
        Bukkit.getLogger().info("[SpigotSpleef] " + msg);
    }

    public static void warnConsole(String msg){
        Bukkit.getLogger().warning("[SpigotSpleef] §c" + msg);
    }

    public static void successConsole(String msg){
        Bukkit.getLogger().info("[SpigotSpleef] §a" + msg);
    }

}
