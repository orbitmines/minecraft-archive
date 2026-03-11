package fadidev.bungeemsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import fadidev.bungeemsg.handlers.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.scheduler.BungeeScheduler;
import fadidev.bungeemsg.events.PlayerChatEvent;
import fadidev.bungeemsg.events.PlayerConnectEvent;
import fadidev.bungeemsg.events.PlayerTabCompleteEvent;
import fadidev.bungeemsg.managers.AdvertiseManager;
import fadidev.bungeemsg.managers.ConfigManager;
import fadidev.bungeemsg.managers.LogManager;
import fadidev.bungeemsg.managers.SpamManager;
import fadidev.bungeemsg.runnables.AutoAnnouncerRunnable;
import fadidev.bungeemsg.runnables.DayRunnable;
import fadidev.bungeemsg.utils.UpdateUtils;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.CommandType;
import fadidev.bungeemsg.utils.enums.Config;
import fadidev.bungeemsg.utils.enums.Cooldown;
import fadidev.bungeemsg.utils.enums.LogReadType;
import fadidev.bungeemsg.utils.enums.LogType;
import fadidev.bungeemsg.utils.enums.Message;
import fadidev.bungeemsg.utils.enums.WhitelistType;

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
	private List<Command> playerTabCommands;
	private List<Group> groups;
	private List<IPWhitelist> ipWhitelist;
    private List<Rank> ranks;
	private AdvertiseManager advertiseManager;
	private ConfigManager configManager;
	private LogManager logManager;
	private SpamManager spamManager;
	
	private boolean allMuted;
	private boolean replyInfo;
	private boolean tellPMDisabled;
	private boolean tellIgnored;
	private boolean useAutoGlobal;
	private boolean useGlobal;
	private boolean useChannels;
	private boolean useAnnouncer;
	private boolean useBannedWords;
	
	public void onEnable(){
		plugin = this;
		this.version = "v2.0.0";
		
		this.configManager = new ConfigManager();
		configManager.setup(Config.values());
		
		this.players = new HashMap<ProxiedPlayer, BungeePlayer>();
		this.serversMuted = new ArrayList<ServerInfo>();

		this.allMuted = false;
		this.replyInfo = false;
		this.tellPMDisabled = false;
		this.tellIgnored = false;
		this.useAutoGlobal = false;
		this.useGlobal = false;
		this.useChannels = false;
		this.useAnnouncer = false;
		this.useBannedWords = false;
		
		loadData(false);
		
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
	
	public void setAllMuted(boolean allMuted) {
		this.allMuted = allMuted;
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
	
	public List<Command> getPlayerTabCommands() {
		if(playerTabCommands == null){
			List<Command> playerTabCommands = new ArrayList<Command>();
			for(Command cmd : getCommands()){
				if(cmd.getType().hasPlayerTab()){
					playerTabCommands.add(cmd);
				}
			}
			this.playerTabCommands = playerTabCommands;
		}
		return playerTabCommands;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public List<IPWhitelist> getIPWhitelist() {
		return ipWhitelist;
	}
	
	public boolean isWhitelisted(String s) {
		for(IPWhitelist w : getIPWhitelist()){
			if(w.getWhitelisted().equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}

    public List<Rank> getRanks() {
        return ranks;
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
		this.getProxy().getPluginManager().registerListener(this, new PlayerConnectEvent());
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
	
	public void loadData(boolean reload){
		String version = UpdateUtils.getLatestVersion();
		if(!version.equals(version)){
			versionMessage(version);
		}
		
		for(Config config : Config.getCorrectOrder()){
			loadConfig(config, reload);
		}
		loadMessages();
	}
	
	private void loadMessages(){
		for(Message message : Message.values()){
			message.setMSGLoader(new MessageLoader(message));
		}
	}
	
	public void loadConfig(Config config, boolean reload){
		Configuration c = getConfigManager().get(config);
		switch(config){
			case ADVERTISE:
				{
					boolean use = c.getBoolean("Use");
					boolean kick = c.getBoolean("Kick");
					boolean cancelIPs = c.getBoolean("Cancel.IPs");
					boolean cancelDomains = c.getBoolean("Cancel.Domains");
					AdvertiseManager aM = new AdvertiseManager(use, cancelIPs, cancelDomains, kick);
					this.advertiseManager = aM;
					
					List<IPWhitelist> ipWhitelist = new ArrayList<IPWhitelist>();
					for(String ipW : c.getStringList("IPs")){
						ipWhitelist.add(new IPWhitelist(WhitelistType.IP, ipW));
					}
					for(String domainW : c.getStringList("DomainNames")){
						ipWhitelist.add(new IPWhitelist(WhitelistType.DOMAIN, domainW));
					}
					this.ipWhitelist = ipWhitelist;
				}
				break;
			case ANNOUNCER:
				{
					this.autoAnnouncers = new ArrayList<AutoAnnouncer>();
					this.useAnnouncer = c.getBoolean("Use");
					
					if(useAnnouncer){
						for(String announcerName : c.getSection("Announcers").getKeys()){
							String serverString = c.getString("Announcers." + announcerName + ".Servers");
							List<ServerInfo> servers = Utils.fromString(serverString);
							int delay = c.getInt("Announcers." + announcerName + ".Delay");
							
							List<MessageLoader> messages = new ArrayList<MessageLoader>();
							for(String message : c.getSection("Announcers." + announcerName + ".Messages").getKeys()){
								messages.add(new MessageLoader(config, "Announcers." + announcerName + ".Messages." + message));
							}
							
							AutoAnnouncer aa = new AutoAnnouncer(announcerName, servers, delay, messages);
							this.autoAnnouncers.add(aa);
						}
					}
				}
				break;
			case BANNEDWORDS:
				{
					this.bannedWords = new ArrayList<BannedWord>();
					this.useBannedWords = c.getBoolean("Use");
					
					if(useBannedWords){
						for(String bW : c.getStringList("BannedWords")){
							String[] bwParts = bW.split("\\|");
							String replacement = null;
							if(bwParts.length > 1){
								replacement = bwParts[1];
							}
							
							this.bannedWords.add(new BannedWord(bwParts[0], replacement));
						}
					}
				}
				break;
			case CHANNEL:
				{
					this.channels = new ArrayList<Channel>();
					this.useChannels = c.getBoolean("Use");
					
					if(useChannels){
						for(String channelName : c.getSection("Channels").getKeys()){
							boolean usePermission = c.getBoolean("Channels." + channelName + ".Permission.Use");
							String permission = c.getString("Channels." + channelName + ".Permission.Permission");
							List<String> startSymbols = c.getStringList("Channels." + channelName + ".Symbol.StartWith");
							List<String> toggleSymbols = c.getStringList("Channels." + channelName + ".Symbol.Toggle");
							
							MessageLoader message = new MessageLoader(config, "Channels." + channelName + ".Messages.Message");
							MessageLoader enabledMessage = new MessageLoader(config, "Channels." + channelName + ".Messages.Enable");
							MessageLoader disabledMessage = new MessageLoader(config, "Channels." + channelName + ".Messages.Disable");
							
							Channel channel = new Channel(channelName, usePermission, permission, startSymbols, toggleSymbols, message, enabledMessage, disabledMessage);
							this.channels.add(channel);
						}
					}
					
					getLogManager().loadLog(LogType.CHANNEL, !reload);
					getLogManager().loadLog(LogType.ALL_CHANNELS, !reload);
				}
				break;
			case COMMAND:
				{
					this.commands = new ArrayList<Command>();
					
					for(CommandType type : CommandType.values()){
						boolean use = c.getBoolean(type.getPath() + ".Use");
						boolean usePermission = c.getBoolean(type.getPath() + ".Permission.Use");
						String permission = c.getString(type.getPath() + ".Permission.Permission");
						List<String> commands = c.getStringList(type.getPath() + ".Commands");
						MessageLoader wrongUsage = new MessageLoader(config, type.getPath() + ".Messages.WrongUsage");
						MessageLoader noPermission = new MessageLoader(config, type.getPath() + ".Messages.NoPermission");
						
						Command cmd = new Command(use, type, usePermission, permission, commands, wrongUsage, noPermission);
						this.commands.add(cmd);
					}
				}
				break;
			case CONFIG:
				{
					this.serverNames = new HashMap<ServerInfo, String>();
					for(String server : c.getSection("Servers").getKeys()){
						ServerInfo info = ProxyServer.getInstance().getServerInfo(server);
						
						if(info != null){
							this.serverNames.put(info, c.getString("Servers." + server).replace("&", "§"));
						}
						else{
							Utils.warnConsole("Unknown Server: '" + server + "'.");
						}
					}
					
					for(ServerInfo info : ProxyServer.getInstance().getServers().values()){
						if(!this.serverNames.containsKey(info)){
							this.serverNames.put(info, info.getName());
						}
					}
					
					this.replyInfo = c.getBoolean("EnableReplyInfo");
					this.tellPMDisabled = c.getBoolean("TellSenderIfPrivateDisabled");
					this.tellIgnored = c.getBoolean("TellSenderIfIgnored");
					
					Cooldown.REPORT.setCooldown(c.getInt("Cooldowns.ReportCommand"));
					Cooldown.HELPOP.setCooldown(c.getInt("Cooldowns.HelpOpCommand"));
				}
				break;
			case GROUP:
				{
					this.groups = new ArrayList<Group>();
					this.useGlobal = c.getBoolean("Use");
					this.useAutoGlobal = c.getBoolean("Auto");
					
					if(useGlobal){
						for(String groupName : c.getSection("Groups").getKeys()){
							String serverString = c.getString("Groups." + groupName + ".Servers");
							List<ServerInfo> servers = Utils.fromString(serverString);
							
							MessageLoader msgL = new MessageLoader(config, "Groups." + groupName + ".Message");
							
							Group group = new Group(groupName, servers, msgL);
							this.groups.add(group);
						}
					}
					
					getLogManager().loadLog(LogType.GLOBAL, !reload);
					getLogManager().loadLog(LogType.ALL_GLOBALS, !reload);
				}
				break;
			case LOG:
				{
					boolean use = c.getBoolean("Use");
					boolean defaultLog = c.getBoolean("Advanced.DefaultLog");
					boolean perServerLog = c.getBoolean("Advanced.PerServerLog");
					boolean perChannelLog = c.getBoolean("Advanced.PerChannelLog");
					boolean perGlobalLog = c.getBoolean("Advanced.PerGlobalLog");
					boolean allChannelsLog = c.getBoolean("Advanced.AllChannelsLog");
					boolean allGlobalsLog = c.getBoolean("Advanced.AllGlobalsLog");
					boolean pmLog = c.getBoolean("Advanced.PrivateMessagesLog");
					
					List<LogReadType> read = new ArrayList<LogReadType>();
					for(LogReadType type : LogReadType.values()){
						if(c.getBoolean(type.getPath())){
							read.add(type);
						}
					}
					
					if(this.logManager != null){
						this.logManager.updateLogTypes(defaultLog, perServerLog, perChannelLog, perGlobalLog, allChannelsLog, allGlobalsLog, pmLog);
					}
					else{
						LogManager lM = new LogManager(use, read, defaultLog, perServerLog, perChannelLog, perGlobalLog, allChannelsLog, allGlobalsLog, pmLog);
						this.logManager = lM;
					}
					
					getLogManager().loadLog(LogType.DEFAULT, !reload);
					getLogManager().loadLog(LogType.SERVER, !reload);
					getLogManager().loadLog(LogType.PRIVATE_MESSAGES, !reload);
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
			case SPAM:
				{
					boolean use = c.getBoolean("Use");
					boolean cancelDuplicate = c.getBoolean("Duplicate.Cancel");
					int duplicateSensitivity = c.getInt("Duplicate.Sensitivity");
					boolean cancelTooFast = c.getBoolean("FastUsage.Cancel");
					int tooFastCheck = c.getInt("FastUsage.Timer");
					int tooFastMax = c.getInt("FastUsage.Max");
					boolean useCooldown = c.getBoolean("Cooldown.Use");
					Cooldown.LAST_MSG.setCooldown(c.getInt("Cooldown.Private"));
					Cooldown.LAST_GLOBAL.setCooldown(c.getInt("Cooldown.Global"));
					boolean cancelCaps = c.getBoolean("Caps.Cancel");
					int maxCaps = c.getInt("Caps.Max");
					SpamManager sM = new SpamManager(use, cancelDuplicate, duplicateSensitivity, cancelTooFast, tooFastCheck, tooFastMax, useCooldown, cancelCaps, maxCaps);
					this.spamManager = sM;
				}
				break;
			case RANKS:
			{
				this.ranks = new ArrayList<Rank>();
                boolean hasDefault = false;

                for(String rankName : c.getSection("Ranks").getKeys()){
                    List<String> permissions = c.getStringList("Ranks." + rankName + ".Perms");
                    List<String> rankPerms = c.getStringList("Ranks." + rankName + ".HasPerms");

                    Rank rank = new Rank(rankName, permissions, rankPerms);
                    this.ranks.add(rank);

                    if(rankName.equals("Default")){
                        hasDefault = true;
                    }
                }

                for(Rank rank : getRanks()){
                    rank.update();
                }

                if(!hasDefault){
                    Utils.warnConsole("You have no Rank called 'Default'! (ranks.yml)");
                }
			}
			break;
		}
		
		Log log = getLogManager().getLog(LogType.DEFAULT);
		if(log != null){
			log.getLog().info("Loaded " + config.getFileName() + ".");
		}
	}
	
	private void versionMessage(String version){
		Utils.sendConsoleEmpty();
		Utils.sendConsoleMSG("§eNew Version Available! (" + version + ")");
		Utils.sendConsoleMSG("§ehttp://www.spigotmc.org/resources/bungeemsg.4512/");
		Utils.sendConsoleEmpty();
	}
	
	private void newConfigPath(String version, String path){
		Utils.sendConsoleMSG("§eCreating new Configuration Path for " + version + "! (" + path + ")");
	}
	
	private void removeConfigPath(String version, String path){
		Utils.sendConsoleMSG("§4Removing old Configuration Path for " + version + "! (" + path + ")");
	}

    /* API */
    public static boolean hasPermission(ProxiedPlayer p, String permission){
        if(p == null || permission == null){
            throw new NullPointerException();
        }

        BungeePlayer bp = getInstance().getBungeePlayers().get(p);
        Rank rank = bp.getRank();
        if (rank != null) {
            return rank.getPermissions().contains(permission);
        }
        return false;
    }

    public static boolean hasPermission(ProxiedPlayer p, String permission, String otherPermission){
        if(p == null || permission == null || otherPermission == null){
            throw new NullPointerException();
        }

        BungeePlayer bp = getInstance().getBungeePlayers().get(p);
        Rank rank = bp.getRank();
        if(rank != null){
            return rank.getPermissions().contains(permission) || rank.getPermissions().contains(otherPermission);
        }
        return false;
    }
}
