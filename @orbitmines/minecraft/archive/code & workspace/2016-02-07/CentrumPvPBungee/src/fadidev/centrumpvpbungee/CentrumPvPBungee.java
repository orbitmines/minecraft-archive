package fadidev.centrumpvpbungee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import fadidev.centrumpvpbungee.events.PlayerChatEvent;
import fadidev.centrumpvpbungee.events.PlayerConnectEvent;
import fadidev.centrumpvpbungee.handlers.BungeePlayer;
import fadidev.centrumpvpbungee.handlers.Channel;
import fadidev.centrumpvpbungee.handlers.Group;
import fadidev.centrumpvpbungee.managers.ConfigManager;
import fadidev.centrumpvpbungee.utils.Utils;
import fadidev.centrumpvpbungee.utils.enums.Config;

public class CentrumPvPBungee extends Plugin {

	public static CentrumPvPBungee plugin;
	
	private Map<ProxiedPlayer, BungeePlayer> players;
	private List<UUID> mutedUUIDs;
	private List<Channel> channels;
	private List<Group> groups;
	private ConfigManager configManager;
	
	public void onEnable(){
		plugin = this;
		
		this.configManager = new ConfigManager();
		configManager.setup(Config.values());
		
		this.players = new HashMap<ProxiedPlayer, BungeePlayer>();
		
		loadData();
		
		registerEvents();
	}
	
	public Map<ProxiedPlayer, BungeePlayer> getBungeePlayers() {
		return players;
	}
	
	public List<UUID> getMutedUUIDs() {
		return mutedUUIDs;
	}
	
	public List<Channel> getChannels() {
		return channels;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public static CentrumPvPBungee getInstance(){
		return plugin;
	}
	
	private void registerEvents(){
		this.getProxy().getPluginManager().registerListener(this, new PlayerChatEvent());
		this.getProxy().getPluginManager().registerListener(this, new PlayerConnectEvent());
	}
		
	
	public void loadData(){
		for(Config config : Config.getCorrectOrder()){
			loadConfig(config);
		}
	}
	
	public void loadConfig(Config config){
		Configuration c = getConfigManager().get(config);
		switch(config){
			case CONFIG:
				{
					this.channels = new ArrayList<Channel>();
					this.groups = new ArrayList<Group>();
					for(String channelName : c.getSection("Chat").getKeys()){
						List<ServerInfo> servers = Utils.fromString(c.getString("Chat." + channelName));
						
						this.channels.add(new Channel(channelName, servers));
					}
					
					for(String groupName : c.getSection("Groups").getKeys()){
						boolean isDefault = c.getBoolean("Groups." + groupName + ".Default");
						String format = c.getString("Groups." + groupName + ".Format");
						int delay = c.getInt("Groups." + groupName + ".Delay");
						
						this.groups.add(new Group(groupName, isDefault, format, delay));
					}
				}
				break;
			case MUTED:
				{
					this.mutedUUIDs = new ArrayList<UUID>();
					for(String uuid : c.getStringList("MutedUUIDs")){
						try{
							this.mutedUUIDs.add(UUID.fromString(uuid));
						}catch(IllegalArgumentException ex){
							Utils.warnConsole("Error while parsing '" + uuid + "' to an UUID.");
						}
					}
				}
				break;
			case PLAYERDATA:
				{
					this.players = new HashMap<ProxiedPlayer, BungeePlayer>();
					for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
						BungeePlayer bp = new BungeePlayer(p);
						this.players.put(p, bp);
					}
				}
				break;
		}
	}
}
