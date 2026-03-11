package fadidev.centrumpvpbungee.handlers;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import fadidev.centrumpvpbungee.CentrumPvPBungee;
import fadidev.centrumpvpbungee.utils.enums.Config;

public class BungeePlayer {

	private CentrumPvPBungee msg;
	private ProxiedPlayer player;
	
	private long lastMSG;
	private Group group;
	
	public BungeePlayer(ProxiedPlayer player){
		this.msg = CentrumPvPBungee.getInstance();
		this.player = player;
		this.lastMSG = -1;
		
		String uuid = player.getUniqueId().toString();
		Configuration c = msg.getConfigManager().get(Config.PLAYERDATA);
		if(c.get("players." + uuid + ".Group") != null){
			String name = c.getString("players." + uuid + ".Group");
			
			for(Group group : msg.getGroups()){
				if(group.getName().equals(name)){
					this.group = group;
					break;
				}
			}
		}
		
		if(group == null){
			for(Group group : msg.getGroups()){
				if(group.isDefault()){
					this.group = group;
					break;
				}
			}
		}
	}
	
	public ProxiedPlayer getPlayer() {
		return player;
	}
	
	public long getLastMSG() {
		return lastMSG;
	}
	public void resetLastMSG(){
		this.lastMSG = System.currentTimeMillis();
	}
	public void removeLastMSG(){
		this.lastMSG = -1;
	}
	public boolean onCooldown(){
		if(this.lastMSG != -1){
			if(System.currentTimeMillis() - this.lastMSG >= (group.getDelay() * 1000)){
				return false;
			}
			return true;
		}
		return false;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Channel getChannel(){
		for(Channel channel : msg.getChannels()){
			if(channel.getServers().contains(getPlayer().getServer().getInfo())){
				return channel;
			}
		}
		return null;
	}
	
	public boolean isMuted(){
		return !getPlayer().hasPermission("CentrumPvPBungee.bypass.mute") && !getPlayer().hasPermission("CentrumPvPBungee.bypass.*") && (msg.getMutedUUIDs().contains(getPlayer().getUniqueId()));
	}
	
	public boolean hasPermission(String permission, String otherPermission){
		return getPlayer().hasPermission(permission) || (otherPermission == null || getPlayer().hasPermission(otherPermission));
	}
}
