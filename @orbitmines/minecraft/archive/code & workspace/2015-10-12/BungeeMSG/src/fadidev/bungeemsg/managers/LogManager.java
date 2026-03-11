package fadidev.bungeemsg.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.handlers.Channel;
import fadidev.bungeemsg.handlers.Group;
import fadidev.bungeemsg.handlers.Log;
import fadidev.bungeemsg.utils.enums.LogReadType;
import fadidev.bungeemsg.utils.enums.LogType;

public class LogManager {

	private BungeeMSG msg;
	private boolean use;
	private Map<LogReadType, Boolean> read;
	private List<Log> logs;
	private List<LogType> logTypes;
	
	public LogManager(boolean use, Map<LogReadType, Boolean> read, boolean defaultLog, boolean perServerLog, boolean perChannelLog, boolean perGlobalLog, boolean allChannelsLog, boolean allGlobalsLog, boolean pmLog){
		this.msg = BungeeMSG.getInstance();
		this.use = use;
		this.read = read;
		
		updateLogTypes(defaultLog, perServerLog, perChannelLog, perGlobalLog, allChannelsLog, allGlobalsLog, pmLog);

		File f = new File(msg.getDataFolder().getPath() + "/logs");
		if(!f.exists()){
			f.mkdir();
		}
		
		loadLogs();
	}
	
	public void updateLogTypes(boolean defaultLog, boolean perServerLog, boolean perChannelLog, boolean perGlobalLog, boolean allChannelsLog, boolean allGlobalsLog, boolean pmLog){
		if(defaultLog) logTypes.add(LogType.DEFAULT);
		if(perServerLog) logTypes.add(LogType.SERVER);
		if(perChannelLog) logTypes.add(LogType.CHANNEL);
		if(perGlobalLog) logTypes.add(LogType.GLOBAL);
		if(allChannelsLog) logTypes.add(LogType.ALL_CHANNELS);
		if(allGlobalsLog) logTypes.add(LogType.ALL_GLOBALS);
		if(pmLog) logTypes.add(LogType.PRIVATE_MESSAGES);
	}
	
	public void loadLogs(){
		List<Log> logs = new ArrayList<Log>();

		if(isUsed()){
			for(LogType type : logTypes){
				switch(type){
					case CHANNEL:
						for(Channel channel : msg.getChannels()){
							Log log = getLog(channel);
							if(log == null){
								log = new Log(this, type, channel);
							}
							log.createNew();
							logs.add(log);
						}
						break;
					case GLOBAL:
						for(Group group : msg.getGroups()){
							Log log = getLog(group);
							if(log == null){
								log = new Log(this, type, group);
							}
							log.createNew();
							logs.add(log);
						}
						break;
					case SERVER:
						for(ServerInfo server : ProxyServer.getInstance().getServers().values()){
							Log log = getLog(server);
							if(log == null){
								log = new Log(this, type, server);
							}
							log.createNew();
							logs.add(log);
						}
						break;
					default:
						Log log = getLog(type);
						if(log == null){
							log = new Log(this, type);
						}
						log.createNew();
						logs.add(log);
						break;
				}
			}
		}
		
		this.logs.clear();
		this.logs.addAll(logs);
	}
	
	public boolean isUsed() {
		return use;
	}
	
	public Map<LogReadType, Boolean> getRead() {
		return read;
	}
	
	public boolean isRead(LogReadType type) {
		return read.get(type);
	}
	
	public Log getLog(Channel channel){
		for(Log log : this.logs){
			if(log.getChannel() == channel){
				return log;
			}
		}
		return null;
	}
	
	public Log getLog(Group group){
		for(Log log : this.logs){
			if(log.getGroup() == group){
				return log;
			}
		}
		return null;
	}
	
	public Log getLog(ServerInfo server){
		for(Log log : this.logs){
			if(log.getServer() == server){
				return log;
			}
		}
		return null;
	}
	
	public Log getLog(LogType type){
		for(Log log : this.logs){
			if(log.getType() == type){
				return log;
			}
		}
		return null;
	}
}
