package fadidev.centrumpvpbungee.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class Utils {

	public static void sendConsoleEmpty(){
		ProxyServer.getInstance().getLogger().info("");
	}
	
	public static void sendConsoleMSG(String msg){
		ProxyServer.getInstance().getLogger().info("[CentrumPvPBungee] " + msg);
	}
	
	public static void warnConsole(String msg){
		ProxyServer.getInstance().getLogger().warning("[CentrumPvPBungee] §c" + msg);
	}
	
	public static void successConsole(String msg){
		ProxyServer.getInstance().getLogger().warning("[CentrumPvPBungee] §a" + msg);
	}
	
	public static List<String> parseStringList(List<UUID> uuidList){
		List<String> stringList = new ArrayList<String>();
		for(UUID uuid : uuidList){
			stringList.add(uuid.toString());
		}
		return stringList;
	}
	
	public static List<ServerInfo> fromString(String serverString){
		List<ServerInfo> servers = new ArrayList<ServerInfo>();

		String[] serverParts = serverString.split("\\|");
		for(String server : serverParts){
			ServerInfo info = ProxyServer.getInstance().getServerInfo(server);
			
			if(info != null){
				servers.add(info);
			}
			else{
				warnConsole("Unknown Server: '" + server + "'.");
			}
		}
		
		return servers;
	}
}
