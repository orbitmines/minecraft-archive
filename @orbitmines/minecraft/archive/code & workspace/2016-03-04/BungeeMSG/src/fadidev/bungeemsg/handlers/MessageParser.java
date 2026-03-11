package fadidev.bungeemsg.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.managers.AdvertiseManager;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.LogReadType;
import fadidev.bungeemsg.utils.enums.Message;
import fadidev.bungeemsg.utils.enums.Variable;
import fadidev.bungeemsg.utils.enums.WhitelistType;

public class MessageParser {

	private BungeeMSG msg;
	private BungeePlayer bp;
	private boolean cancelled;
	private String message;
	private List<String> messageList;
	private Title title;
	private ActionBar actionBar;
	
	public MessageParser(BungeePlayer bp, MessageLoader msgL){
		this.msg = BungeeMSG.getInstance();
		this.bp = bp;
		this.cancelled = false;
		
		this.message = msgL.getMessage();
		
		if(msgL.getMessageList() != null){
			List<String> messageList = new ArrayList<String>();
			messageList.addAll(msgL.getMessageList());
			this.messageList = messageList;
		}
		
		if(msgL.getTitle() != null) this.title = msgL.getTitle().copy();
		if(msgL.getActionBar() != null) this.actionBar = msgL.getActionBar().copy();
	}
	
	public MessageParser(BungeePlayer bp, String message){
		this.msg = BungeeMSG.getInstance();
		this.bp = bp;
		this.cancelled = false;
		
		this.message = message;
	}
	
	public void parseVariable(Variable variable, String replacement){
		if(variable == Variable.REASON || variable == Variable.MSG){
			checkAll(replacement);
		}
		
		if(!cancelled){
			if(message != null) message = message.replace(variable.getVariable(), replacement);
				
			if(messageList != null){
				int index = 0;
				for(String s : messageList){
					messageList.set(index, s.replace(variable.getVariable(), replacement));
					index++;
				}
			}
			
			if(title != null){
				title.setTitle(title.getTitle().replace(variable.getVariable(), replacement));
				title.setSubTitle(title.getSubTitle().replace(variable.getVariable(), replacement));
			}
			
			if(actionBar != null) actionBar.setMessage(actionBar.getMessage().replace(variable.getVariable(), replacement));
		}
	}

	public void send(ProxiedPlayer p){
		if(!cancelled){
			if(message != null){
				this.message = Utils.checkforColors(p, message);
				p.sendMessage(message);
			}
			
			if(messageList != null){
				int index = 0;
				for(String s : messageList){
					messageList.set(index, Utils.checkforColors(p, s));
					index++;
					p.sendMessage(s);
				}
			}
			
			if(title != null){
				title.setTitle(Utils.checkforColors(p, title.getTitle()));
				title.setSubTitle(Utils.checkforColors(p, title.getSubTitle()));
				title.send(p);
			}
			
			if(actionBar != null){
				actionBar.setMessage(Utils.checkforColors(p, actionBar.getMessage()));
				actionBar.send(p);
			}
		}
	}
	
	public void send(ProxiedPlayer p, ProxiedPlayer sender){
		if(!cancelled){
			BungeePlayer bp = msg.getBungeePlayers().get(p);
			if(message != null){
				this.message = Utils.checkforColors(p, message);
				bp.sendMessage(message, sender);
			}
			
			if(messageList != null){
				int index = 0;
				for(String s : messageList){
					messageList.set(index, Utils.checkforColors(p, s));
					index++;
					bp.sendMessage(s, sender);
				}
			}
			
			if(title != null){
				title.setTitle(Utils.checkforColors(p, title.getTitle()));
				title.setSubTitle(Utils.checkforColors(p, title.getSubTitle()));
				title.send(p);
			}
			
			if(actionBar != null){
				actionBar.setMessage(Utils.checkforColors(p, actionBar.getMessage()));
				actionBar.send(p);
			}
		}
	}

	public void checkAdvertising(String replacement){
		ProxiedPlayer p = bp.getPlayer();
		AdvertiseManager aM = msg.getAdvertiseManager();
		
		if(aM.isUsed()){
	    	if(aM.canCancelIPs()){
				if(!p.hasPermission("BungeeMSG.bypass.ips") && !p.hasPermission("BungeeMSG.bypass.*")){
		    		String tocheck = replacement.replace("dot", " ").replace(".", " ");
		    		StringBuilder sb = new StringBuilder();
		    		for(int i = 0; i < tocheck.length(); i++){
			    		if(Character.isDigit(tocheck.charAt(i)) || Character.isSpace(tocheck.charAt(i)) && !sb.toString().endsWith(" ") && !sb.toString().equals("")){
			    			sb.append(tocheck.charAt(i));
			    		}
		    		}
		    		
		    		if(!msg.isWhitelisted(sb.toString().replace(" ", "."))){
			    		Matcher m = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])[\\. ]" + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])[\\. ]" + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])[\\. ]" + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$").matcher(sb.toString());
			    		
			    		if(m.find() == true){
			    			if(aM.canKick()){
			    				MessageParser mP = Message.ADVERTISE_KICK.getParser(bp);
			    				mP.parseVariable(Variable.TYPE, Message.IP_VARIABLE.getParser(bp).getMessage());
			    				p.disconnect(mP.getMessage());
			    				
			    				msg.getLogManager().info(LogReadType.ADVERTISING, p.getServer().getInfo(), "[ADVERTISING] " + p.getName() + " tried to send the IP '" + sb.toString() + "'. (Kick)");
			    			}
			    			else{
			    				MessageParser mP = Message.ADVERTISE_MESSAGE.getParser(bp);
			    				mP.parseVariable(Variable.TYPE, Message.IP_VARIABLE.getParser(bp).getMessage());
			    				mP.send(p);
			    				
			    				msg.getLogManager().info(LogReadType.ADVERTISING, p.getServer().getInfo(), "[ADVERTISING] " + p.getName() + " tried to send the IP '" + sb.toString() + "'. (Message)");
			    			}
			    			this.cancelled = true;
			    			return;
			    		}
		    		}
				}
	    	}
	    	
	    	if(aM.canCancelDomains()){
				if(!p.hasPermission("BungeeMSG.bypass.domainnames") && !p.hasPermission("BungeeMSG.bypass.*")){
					String lowercasemsg = replacement.toLowerCase();

					lowercasemsg.replace(" dot ", ".");
					lowercasemsg.replace(" dot", ".");
					lowercasemsg.replace("dot ", ".");
					lowercasemsg.replace("dot", ".");
					
					for(IPWhitelist w : msg.getIPWhitelist()){
						if(w.getType() == WhitelistType.DOMAIN){
							lowercasemsg = lowercasemsg.replace("http://" + w.getWhitelisted(), "").replace("https://" + w.getWhitelisted(), "").replace("http://www." + w.getWhitelisted(), "").replace("https://www." + w.getWhitelisted(), "").replace("www." + w.getWhitelisted(), "").replace(w.getWhitelisted(), "");
						}
					}
					
		    		if(lowercasemsg.contains("www.") || lowercasemsg.contains(".net") || lowercasemsg.contains(".com") || lowercasemsg.contains(".co.uk") || lowercasemsg.contains("http://") || lowercasemsg.contains("https://") || lowercasemsg.contains(".de") || lowercasemsg.contains(".nl") || lowercasemsg.contains(".be") || lowercasemsg.contains(".fr") || lowercasemsg.contains(".org") || lowercasemsg.contains(".us")){
		    			if(aM.canKick()){
		    				MessageParser mP = Message.ADVERTISE_KICK.getParser(bp);
		    				mP.parseVariable(Variable.TYPE, Message.DOMAIN_VARIABLE.getParser(bp).getMessage());
		    				p.disconnect(mP.getMessage());
		    				
		    				msg.getLogManager().info(LogReadType.ADVERTISING, p.getServer().getInfo(), "[ADVERTISING] " + p.getName() + " tried to send a Website. (Message: " + replacement + ")");
		    			}
		    			else{
		    				MessageParser mP = Message.ADVERTISE_MESSAGE.getParser(bp);
		    				mP.parseVariable(Variable.TYPE, Message.DOMAIN_VARIABLE.getParser(bp).getMessage());
		    				mP.send(p);
		    				
		    				msg.getLogManager().info(LogReadType.ADVERTISING, p.getServer().getInfo(), "[ADVERTISING] " + p.getName() + " tried to send a Website. (Message: " + replacement + ")");
		    			}
		    			this.cancelled = true;
		    		}
				}
	    	}
		}
	}
	
	private String replaceBannedWords(String message){
		ProxiedPlayer p = bp.getPlayer();
		
		if(msg.getBannedWords().size() > 0 && !p.hasPermission("BungeeMSG.bypass.bannedwords") && !p.hasPermission("BungeeMSG.bypass.*")){
			for(String word : message.split(" ")){
				String messagenow = message;
				
				StringBuilder replacement = new StringBuilder();
			    for(int i = 1; i <= word.length(); i++) {
			    	replacement.append("*");
			    }
			    
			    for(BannedWord bw : msg.getBannedWords()){
					String bannedword = bw.getBannedWord();
			    	
			    	StringBuilder sb = new StringBuilder();
					for(int i = 0; i < bannedword.length(); i++){
						if(i != 0){
							sb.append("+");
						}
						sb.append(bannedword.charAt(i));
					}
					
					String sbstring = sb.toString();
					sbstring = sbstring.replace("*", "");
					
					Matcher m = Pattern.compile("\\b(?i)" + sbstring.replaceAll("\\$", "[s\\$]").replaceAll("(?i)s", "[s\\$]").replace("a", "[a*]").replace("e", "[e*]").replace("o", "[o*]").replace("u", "[u*]").replace("c+k", "c*k") + "+" + "\\b").matcher(message);
	
					if(bw.getReplacement() != null){
						message = m.replaceAll(replacement.toString());
					}
					else{
						message = m.replaceAll(bw.getReplacement());
					}
			    }
			    
			    if(!messagenow.equals(message)){
					msg.getLogManager().info(LogReadType.BANNED_WORDS, p.getServer().getInfo(), "[BANNED WORDS] " + bp.getPlayer().getName() + " tried to use a banned word: '" + word + "'.");
			    }
			}
		}
		
		return message;
	}
	
	public String replaceCaps(String message){
		ProxiedPlayer p = bp.getPlayer();
		
		if(msg.getSpamManager().isUsed() && msg.getSpamManager().canCancelCaps()){
			if(!p.hasPermission("BungeeMSG.bypass.caps") && !p.hasPermission("BungeeMSG.bypass.*")){
				int caps = 0;
				for(int i = 0; i < message.length(); i++){
					if(Character.isUpperCase(message.charAt(i))){
						caps++;
					}
				}
				
				if(caps > msg.getSpamManager().getMaxCaps()){
					message = message.toLowerCase();
					
					msg.getLogManager().info(LogReadType.SPAM, p.getServer().getInfo(), "[SPAM] Replaced all uppercases to lowercases in '" + message + "' for " + p.getName() + ". (Caps)");
				}
			}
		}
		
		return message;
	}
	
	public String checkAll(String replacement){
		checkAdvertising(replacement);
		
		if(!cancelled){
			replacement = replaceBannedWords(replacement);
			replacement = replaceCaps(replacement);
		}
		
		return replacement;
	}
	
	public String getMessage() {
		return message;
	}
	
	public List<String> getMessageList() {
		return messageList;
	}
	
	public Title getTitle() {
		return title;
	}
	
	public ActionBar getActionBar() {
		return actionBar;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
}
