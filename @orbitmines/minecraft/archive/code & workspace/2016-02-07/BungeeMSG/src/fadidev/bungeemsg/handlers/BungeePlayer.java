package fadidev.bungeemsg.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.managers.SpamManager;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.Config;
import fadidev.bungeemsg.utils.enums.Cooldown;
import fadidev.bungeemsg.utils.enums.LogReadType;
import fadidev.bungeemsg.utils.enums.Message;
import fadidev.bungeemsg.utils.enums.Variable;

public class BungeePlayer {

	private BungeeMSG msg;
	private ProxiedPlayer player;
	
	private BungeePlayer lastMSGTo;
	private String lastMSG;
	
	private List<UUID> ignored;
	private Map<Cooldown, Long> cooldowns;
	private int tooFastAmount;
	private Channel toggledChannel;
	private boolean spy;
	private boolean msgEnabled;

    private Rank rank;
	
	public BungeePlayer(ProxiedPlayer player){
		this.msg = BungeeMSG.getInstance();
		this.player = player;
		this.lastMSGTo = null;
		this.lastMSG = null;
		
		this.ignored = new ArrayList<UUID>();
		this.cooldowns = new HashMap<Cooldown, Long>();
		this.tooFastAmount = 0;
		this.toggledChannel = null;

		String uuid = player.getUniqueId().toString();
        Configuration c = msg.getConfigManager().get(Config.PLAYERDATA);
		if(c.get("players." + uuid + ".Ignored") != null){
			String[] ignoredUUIDs = c.getString("players." + uuid + ".Ignored").split("\\|");
			
			for(String ignoredUUID : ignoredUUIDs){
				try{
					this.ignored.add(UUID.fromString(ignoredUUID));
				}catch(IllegalArgumentException ex){
					Utils.warnConsole("Error while parsing '" + uuid + "' to an UUID.");
				}
			}
		}

        this.msgEnabled = true;
        if(c.get("players." + uuid + ".Toggle") != null){
            this.msgEnabled = c.getBoolean("players." + uuid + ".Toggle");
        }
		
		if(hasPermission("BungeeMSG.spy.on", null)){
			this.spy = true;
		}
		else{
			this.spy = false;
		}

        if(c.get("players." + uuid + ".Rank") != null){
            rank = Rank.getRank(c.getString("players." + uuid + ".Rank"));
        }
        else{
            rank = Rank.getRank("Default");
        }
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
	public void setIgnored(List<UUID> ignored) {
		this.ignored = ignored;
		
		String ignoredString = "";
		for(UUID uuid : ignored){
			if(!ignoredString.equals("")){
				ignoredString += "|";
			}
			ignoredString += uuid.toString();
		}
		
		msg.getConfigManager().get(Config.PLAYERDATA).set("players." + getPlayer().getUniqueId().toString() + ".Ignored", ignoredString);
		msg.getConfigManager().save(Config.PLAYERDATA);
	}
	public List<ProxiedPlayer> getNotIgnored() {
		List<ProxiedPlayer> notIgnored = new ArrayList<ProxiedPlayer>();
		for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
			if(!getIgnored().contains(player.getUniqueId()) && player != getPlayer()){
				notIgnored.add(player);
			}
		}
		return notIgnored;
	}
	public List<ProxiedPlayer> getNotIgnored(Collection<ProxiedPlayer> collection) {
		List<ProxiedPlayer> notIgnored = new ArrayList<ProxiedPlayer>();
		for(ProxiedPlayer player : collection){
			if(!getIgnored().contains(player.getUniqueId()) && player != getPlayer()){
				notIgnored.add(player);
			}
		}
		return notIgnored;
	}
	
	public Map<Cooldown, Long> getCooldowns() {
		return cooldowns;
	}
	public long getCooldown(Cooldown cooldown) {
        if(this.cooldowns.containsKey(cooldown)){
            return cooldowns.get(cooldown);
        }
        return -1;
	}
	public void resetCooldown(Cooldown cooldown){
		this.cooldowns.put(cooldown, System.currentTimeMillis());
	}
	public void removeCooldown(Cooldown cooldown){
		this.cooldowns.remove(cooldown);
	}
	public boolean onCooldown(Cooldown cooldown){
		if(this.cooldowns.containsKey(cooldown)){
			if(System.currentTimeMillis() - this.cooldowns.get(cooldown) >= cooldown.getCooldown()){
				return false;
			}
			return true;
		}
		return false;
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
	
	public boolean hasMSGEnabled() {
		return msgEnabled;
	}
	public void setMSGEnabled(boolean msgEnabled) {
		this.msgEnabled = msgEnabled;

        Configuration c = msg.getConfigManager().get(Config.PLAYERDATA);
        c.set("players." + player.getUniqueId().toString() + ".Toggle", msgEnabled);
        msg.getConfigManager().save(Config.PLAYERDATA);
	}
	
	public boolean isMuted(){
		return !getPlayer().hasPermission("BungeeMSG.bypass.mute") && !getPlayer().hasPermission("BungeeMSG.bypass.*") && (msg.getMutedUUIDs().contains(getPlayer().getUniqueId()) || msg.isAllMuted() || msg.getServersMuted().contains(getPlayer().getServer().getInfo()));
	}

    public boolean hasPermission(String permission){
        if(getRank() != null){
            return getRank().getPermissions().contains(permission);
        }
        return false;
    }
	
	public boolean hasPermission(String permission, String otherPermission){
        if(getRank() != null){
            return getRank().getPermissions().contains(permission) || getRank().getPermissions().contains(otherPermission);
        }
        return false;
	}

    public Rank getRank() {
        return rank;
    }
    public void setRank(Rank rank) {
        this.rank = rank;

        Configuration c = msg.getConfigManager().get(Config.PLAYERDATA);
        c.set("players." + player.getUniqueId().toString() + ".Rank", rank.getName());
        msg.getConfigManager().save(Config.PLAYERDATA);
    }

    public void sendMessage(String message, ProxiedPlayer sender){
		if(message.contains("%suggest-player%")) {
			String before = message.substring(0, message.indexOf("%suggest-player%"));
			String after = message.substring(message.indexOf("%suggest-player%") + 16);

			MessageParser mp1 = Message.SUGGESTED_PLAYER.getParser(this);
			mp1.parseVariable(Variable.RECEIVER, sender.getName());

			MessageParser mp2 = Message.SUGGESTED_COMMAND.getParser(this);
			mp2.parseVariable(Variable.RECEIVER, sender.getName());

			MessageParser mp3 = Message.HOVER_MESSAGE.getParser(this);
			mp3.parseVariable(Variable.RECEIVER, sender.getName());

			ComponentMessage cm = new ComponentMessage();
			cm.addPart(before, null, null, null, null);
			cm.addPart(mp1.getMessage(), ClickEvent.Action.SUGGEST_COMMAND, mp2.getMessage(), HoverEvent.Action.SHOW_TEXT, mp3.getMessage());
			cm.addPart(after, null, null, null, null);
			cm.send(getPlayer());
		}
		else{
			getPlayer().sendMessage(message);
		}
	}
	
	public boolean canMessage(String message, Cooldown cooldown){
		return !msgOnCooldown(message, cooldown) && !isDuplicated(message) && !isTooFast(message);
	}
	
	public boolean msgOnCooldown(String message, Cooldown msgCooldown){
		SpamManager sM = msg.getSpamManager();
		
		if(sM.isUsed() && sM.useCooldown() && !getPlayer().hasPermission("BungeeMSG.bypass.cooldown") && !getPlayer().hasPermission("BungeeMSG.bypass.*")){
			if(!onCooldown(msgCooldown)){
				resetCooldown(msgCooldown);
			}
			else{
                int timeleft = (int) ((getCooldown(msgCooldown) / 1000) - ((System.currentTimeMillis() - msgCooldown.getCooldown()) / 1000));
				MessageParser mP = Message.SPAM_COOLDOWN.getParser(this);
				mP.parseVariable(Variable.LEFT, "" + timeleft);
				
				if(timeleft == 1){
					mP.parseVariable(Variable.SECOND_GRAMMER, Message.SECOND_SINGLE.getParser(this).getMessage());
				}
				else{
					mP.parseVariable(Variable.SECOND_GRAMMER, Message.SECOND_MULTIPLE.getParser(this).getMessage());
				}
				mP.send(getPlayer());
				
				msg.getLogManager().info(LogReadType.SPAM, getPlayer().getServer().getInfo(), "[SPAM] Muted '" + message + "' for " + getPlayer().getName() + ". (Cooldown)");
				
				return true;
			}
		}
		return false;
	}
	
	public boolean isDuplicated(String message){
		SpamManager sM = msg.getSpamManager();
		
		if(sM.isUsed() && sM.canCancelDuplicate() && !getPlayer().hasPermission("BungeeMSG.bypass.duplicate") && !getPlayer().hasPermission("BungeeMSG.bypass.*")){
			if(getLastMSG() != null){
				String lastmessage = getLastMSG();
				
				if(lastmessage.length() > 2){
					String newstring = lastmessage.substring(0, lastmessage.length() - sM.getDuplicateSensitivity());
					if(newstring.length() > 1){
						lastmessage = newstring;
					}
				}
				
				if(sM.getDuplicateSensitivity() != 0 && message.toLowerCase().startsWith(lastmessage.toLowerCase()) || message.toLowerCase().equals(lastmessage.toLowerCase())){
                    MessageParser mP = Message.SPAM_DUPLICATE.getParser(this);
                    mP.send(getPlayer());

                    msg.getLogManager().info(LogReadType.SPAM, getPlayer().getServer().getInfo(), "[SPAM] Muted '" + message + "' for " + getPlayer().getName() + ". (Duplicate)");

                    return true;
				}
			}
		}
		return false;
	}
	
	public boolean isTooFast(String message){
		SpamManager sM = msg.getSpamManager();
		
		if(sM.isUsed() && sM.canCancelTooFast() && !getPlayer().hasPermission("BungeeMSG.bypass.toofast") && !getPlayer().hasPermission("BungeeMSG.bypass.*")){
			if(tooFastAmount != 0){
				if(!onCooldown(Cooldown.TOO_FAST_STARTED)){
					removeCooldown(Cooldown.TOO_FAST_STARTED);
					tooFastAmount = 0;
				}
				else{
					if(tooFastAmount >= sM.getTooFastMax()){
						MessageParser mP = Message.SPAM_TOO_FAST.getParser(this);
						mP.send(getPlayer());
						
						msg.getLogManager().info(LogReadType.SPAM, getPlayer().getServer().getInfo(), "[SPAM] Muted '" + message + "' for " + getPlayer().getName() + ". (TooFast)");	

                        return true;
					}
					else{
						tooFastAmount++;
					}
				}
			}
			else{
				resetCooldown(Cooldown.TOO_FAST_STARTED);
				tooFastAmount = 1;
			}
		}
		return false;
	}
	
	public boolean hasIgnored(BungeePlayer bp2, MessageParser tosend, String message){
		if(!getPlayer().hasPermission("BungeeMSG.bypass.ignore") && !getPlayer().hasPermission("BungeeMSG.bypass.*")){
			if(getIgnored().contains(bp2.getPlayer().getUniqueId())){
				MessageParser mP = Message.IGNORED.getParser(this);
				mP.parseVariable(Variable.RECEIVER, bp2.getPlayer().getName());
				mP.send(getPlayer());
			
				return true;
			}
			else{
				if(bp2.getIgnored().contains(getPlayer().getUniqueId())){
					if(msg.tellIgnored()){
						MessageParser mP = Message.IGNORED_YOU.getParser(this);
						mP.parseVariable(Variable.RECEIVER, bp2.getPlayer().getName());
						mP.send(getPlayer());
					}
					else{
						tosend.send(getPlayer());
					}
					
					msg.getLogManager().info(LogReadType.IGNORES, getPlayer().getServer().getInfo(), "[IGNORED] " + getPlayer().getName() + " > " + bp2.getPlayer().getName() + ": " + message);
					
					return true;
				}
			}
		}
		return false;
	}
}
