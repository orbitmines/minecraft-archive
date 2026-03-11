package fadidev.centrumpvpbungee.handlers;

import java.util.List;

import net.md_5.bungee.api.config.ServerInfo;

public class Channel {

	private String name;
	private List<ServerInfo> servers;
	
	public Channel(String name, List<ServerInfo> servers){
		this.name = name;
		this.servers = servers;
	}
	
	public String getName() {
		return name;
	}
	
	public List<ServerInfo> getServers() {
		return servers;
	}
}
