package fadidev.spigotbridge.utils;

import org.bukkit.Bukkit;

public class Utils {

	public static void sendConsoleEmpty(){
		Bukkit.getLogger().info("");
	}
	
	public static void sendConsoleMSG(String msg){
		Bukkit.getConsoleSender().sendMessage("[SpigotBridge] " + msg);
	}
	
	public static void warnConsole(String msg){
		Bukkit.getConsoleSender().sendMessage("[SpigotBridge] §c" + msg);
	}
	
	public static void successConsole(String msg){
		Bukkit.getConsoleSender().sendMessage("[SpigotBridge] §a" + msg);
	}	
}
