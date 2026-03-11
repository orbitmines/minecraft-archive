package fadidev.bungeemsg.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.utils.enums.Cooldown;

public class BungeePlayer {

	private BungeeMSG msg;
	private ProxiedPlayer player;
	
	private BungeePlayer lastMSGTo;
	private String lastMSG;
	
	private List<UUID> ignored;
	private Map<Cooldown, Long> cooldowns;
	private Channel toggledChannel;
	private boolean spy;
	private boolean msgEnabled;
	
	public BungeePlayer(ProxiedPlayer player){
		this.msg = BungeeMSG.getInstance();
		this.player = player;
		this.lastMSGTo = null;
		this.lastMSG = null;
		
		this.ignored = new ArrayList<UUID>(); //Load
		this.cooldowns = new HashMap<Cooldown, Long>();
		this.toggledChannel = null;
		
		if(hasPermission("BungeeMSG.spy.on", null)){
			this.spy = true;
		}
		else{
			this.spy = false;
		}
		this.msgEnabled = false;
	}
	
	public ProxiedPlayer getPlayer() {
		return player;
	}
	
	public BungeePlayer getLastMSGTo() {
		return lastMSGTo;
	}
	public void setLastMSGTo(BungeePlayer lastMSGTo) {
		this.lastMSGTo = lastMSGTo;
	}
	
	public String getLastMSG() {
		return lastMSG;
	}
	public void setLastMSG(String lastMSG) {
		this.lastMSG = lastMSG;
	}
	
	public List<UUID> getIgnored() {
		return ignored;
	}
	
	public Map<Cooldown, Long> getCooldowns() {
		return cooldowns;
	}
	public long getCooldown(Cooldown cooldown) {
		return cooldowns.get(cooldown);
	}
	
	public Channel getToggledChannel() {
		return toggledChannel;
	}
	public void setToggledChannel(Channel toggledChannel) {
		this.toggledChannel = toggledChannel;
	}
	
	public boolean isSpy() {
		return spy;
	}
	public void setSpy(boolean spy) {
		this.spy = spy;
	}
	
	public boolean hassMSGEnabled() {
		return msgEnabled;
	}
	public void setMSGEnabled(boolean msgEnabled) {
		this.msgEnabled = msgEnabled;
	}
	
	public boolean isMuted(){
		return msg.getMutedUUIDs().contains(getPlayer().getUniqueId());
	}
	
	public boolean hasPermission(String permission, String otherPermission){
		return getPlayer().hasPermission(permission) || (otherPermission == null || getPlayer().hasPermission(otherPermission));
	}
}
