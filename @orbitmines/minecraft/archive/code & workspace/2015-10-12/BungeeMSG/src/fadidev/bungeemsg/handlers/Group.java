package fadidev.bungeemsg.handlers;

import java.util.List;

import net.md_5.bungee.api.config.ServerInfo;

public class Group {

	private String name;
	private List<ServerInfo> servers;
	private String message;
	
	public Group(String name, List<ServerInfo> servers, String message){
		this.name = name;
		this.servers = servers;
		this.message = message;
	}
	
	public String getName() {
		return name;
	}
	
	public List<ServerInfo> getServers() {
		return servers;
	}
	
	public String getMessage() {
		return message;
	}
}
