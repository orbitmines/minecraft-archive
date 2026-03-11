package fadidev.bungeemsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.scheduler.BungeeScheduler;
import fadidev.bungeemsg.handlers.AutoAnnouncer;
import fadidev.bungeemsg.handlers.BannedWord;
import fadidev.bungeemsg.handlers.BungeePlayer;
import fadidev.bungeemsg.handlers.Channel;
import fadidev.bungeemsg.handlers.Command;
import fadidev.bungeemsg.handlers.Group;
import fadidev.bungeemsg.handlers.IPWhitelist;
import fadidev.bungeemsg.handlers.Log;
import fadidev.bungeemsg.managers.AdvertiseManager;
import fadidev.bungeemsg.managers.ConfigManager;
import fadidev.bungeemsg.managers.LogManager;
import fadidev.bungeemsg.managers.SpamManager;
import fadidev.bungeemsg.runnables.AutoAnnouncerRunnable;
import fadidev.bungeemsg.runnables.DayRunnable;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.Config;

public class BungeeMSG extends Plugin {

	public static BungeeMSG plugin;
	private String version;
	
	private Map<ProxiedPlayer, BungeePlayer> players;
	private Map<ServerInfo, String> serverNames;
	private List<UUID> mutedUUIDs;
	private List<ServerInfo> serversMuted;
	private List<BannedWord> bannedWords;
	private List<AutoAnnouncer> autoAnnouncers;
	private List<Channel> channels;
	private List<Command> commands;
	private List<Group> groups;
	private List<IPWhitelist> ipWhitelist;
	private AdvertiseManager advertiseManager;
	private ConfigManager configManager;
	private LogManager logManager;
	private SpamManager spamManager;
	
	private boolean allMuted;
	private boolean replyInfo;
	private boolean tellPMDisabled;
	private boolean tellIgnored;
	private boolean useAutoGlobal;
	
	public void onEnable(){
		plugin = this;
		this.version = "v2.0.0";
		
		this.configManager = new ConfigManager();
		configManager.setup(Config.values());
		
		this.players = new HashMap<ProxiedPlayer, BungeePlayer>();
		this.serverNames = new HashMap<ServerInfo, String>(); //Load
		this.mutedUUIDs = new ArrayList<UUID>();//Load
		this.serversMuted = new ArrayList<ServerInfo>();
		this.bannedWords = new ArrayList<BannedWord>();//Load
		this.autoAnnouncers = new ArrayList<AutoAnnouncer>();//Load
		this.channels = new ArrayList<Channel>();//Load
		this.commands = new ArrayList<Command>();//Load
		this.groups = new ArrayList<Group>();//Load
		this.ipWhitelist = new ArrayList<IPWhitelist>();//Load

		this.allMuted = false;
		this.replyInfo = false; //Load
		this.tellPMDisabled = false; //Load
		this.tellIgnored = false; //Load
		this.useAutoGlobal = false; //Load
		
		/*Load Congfig Data*/
	
		//this.logManager = new LogManager(use, defaultLog, perServerLog, perChannelLog, perGlobalLog, allChannelsLog, allGlobalsLog, pmLog); //Load
		//this.advertiseManager = new AdvertiseManager(use, cancelIPs, cancelDomains, kick);//Load
		//this.spamManager = new SpamManager(use, cancelDuplicate, duplicateSensitivity, cancelTooFast, tooFastCheck, tooFastMax, useCooldown, cancelCaps, maxCaps); //Load
		
		registerEvents();
		startRunnables();
		loadMetrics();
	}
	
	public Map<ProxiedPlayer, BungeePlayer> getBungeePlayers() {
		return players;
	}
	
	public Map<ServerInfo, String> getServerNames() {
		return serverNames;
	}
	
	public List<UUID> getMutedUUIDs() {
		return mutedUUIDs;
	}
	
	public List<ServerInfo> getServersMuted() {
		return serversMuted;
	}
	
	public List<BannedWord> getBannedWords() {
		return bannedWords;
	}
	
	public boolean isAllMuted() {
		return allMuted;
	}
	
	public boolean hasReplyInfo() {
		return replyInfo;
	}
	
	public boolean tellPMDisabled() {
		return tellPMDisabled;
	}
	
	public boolean tellIgnored() {
		return tellIgnored;
	}
	
	public boolean useAutoGlobal() {
		return useAutoGlobal;
	}
	
	public List<AutoAnnouncer> getAutoAnnouncers() {
		return autoAnnouncers;
	}
	
	public List<Channel> getChannels() {
		return channels;
	}
	
	public List<Command> getCommands() {
		return commands;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public List<IPWhitelist> getIPWhitelist() {
		return ipWhitelist;
	}
	
	public LogManager getLogManager() {
		return logManager;
	}
	
	public AdvertiseManager getAdvertiseManager() {
		return advertiseManager;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public SpamManager getSpamManager() {
		return spamManager;
	}
	
	public static BungeeMSG getInstance(){
		return plugin;
	}
	
	public String getVersion() {
		return version;
	}
	
	private void registerEvents(){
		this.getProxy().getPluginManager().registerListener(this, new PlayerChatEvent());
		this.getProxy().getPluginManager().registerListener(this, new PlayerTabCompleteEvent());
	}
	
	private void startRunnables(){
		new BungeeScheduler(){}.schedule(this, new DayRunnable(), 0, 15, TimeUnit.MINUTES);
		new BungeeScheduler(){}.schedule(this, new AutoAnnouncerRunnable(), 0, 1, TimeUnit.SECONDS);
	}
	
	private void loadMetrics(){
	    try{
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    }catch(IOException ex){
	    	Utils.warnConsole("Error while connecting to mcstats.org.");
	    }
	}
}
