package me.O_o_Fadi_o_O.SpigotSpleef.managers;

import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.utils.Message;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.MessageName;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.MessageType;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class MessageManager {

	public static void loadMessages(){
		load(MessageName.PLAYER_JOIN, "PlayerJoin");
		load(MessageName.PLAYER_QUIT, "PlayerQuit");
		load(MessageName.SPECTATOR_JOIN, "SpectatorJoin");
		load(MessageName.SPECTATOR_QUIT, "SpectatorQuit");
		load(MessageName.STARTING, "Starting");
		load(MessageName.STARTED, "Started");
		load(MessageName.GAME_STARTED, "GameStarted");
		load(MessageName.ENDING, "Ending");
		load(MessageName.ENDED, "Ended");
		load(MessageName.COMMAND_IN_GAME, "CommandInGame");
		load(MessageName.DEATHMESSAGE_TO_PLAYER, "DeathMessageToPlayer");
		load(MessageName.DEATHMESSAGE_TO_PLAYER_UNKNOWN_KILLER, "DeathMessageToPlayerUnknownKiller");
		load(MessageName.DEATHMESSAGE_TO_KILLER, "DeathMessageToKiller");
		load(MessageName.DEATHMESSAGE, "DeathMessage");
		load(MessageName.DEATHMESSAGE_UNKNOWN_KILLER, "DeathMessageUnknownKiller");
		
		loadMessage(MessageName.CHAT_PREFIX_IF_NOR_PER_ARENA, null, "OtherMessages." + "ChatPrefixIfNotPerArena");	
		loadMessage(MessageName.LOBBY_CHAT_PREFIX_IF_NOT_PER_ARENA, null, "OtherMessages." + "LobbyChatPrefixIfNotPerArena");
		loadMessages(MessageName.ANIMATED_RIGHT_CLICK, null, "OtherMessages." + "AnimatedRightClick");
		loadMessages(MessageName.ANIMATED_DOTS, null, "OtherMessages." + "AnimatedDots");
	}
	
	private static void load(MessageName messagename, String path){
		if(contains(path)){
			Sound sound = loadSound(path);
			if(contains(path + ".Message")){
				loadMessage(messagename, sound, "Messages." + path + ".Message");
			}
			else if(contains(path + ".Messages")){
				loadMessages(messagename, sound, "Messages." + path + ".Messages");
			}
			else if(contains(path + ".Title")){
				loadTitle(messagename, sound, "Messages." + path + ".Title");
			}
			else if(contains(path + ".ActionBar")){
				loadActionBar(messagename, sound, "Messages." + path + ".ActionBar");
			}
			else{
				logError("Messages." + path);
			}
		}else{
			logError("Messages." + path);
		}
	}
	
	private static void loadMessage(MessageName messagename, Sound sound, String path){
		String message = ConfigManager.messages.getString(path);
		Message m = new Message(messagename, MessageType.MESSAGE, message, sound);
		StorageManager.messages.add(m);
	}
	
	private static void loadMessages(MessageName messagename, Sound sound, String path){
		List<String> messages = ConfigManager.messages.getStringList(path);
		Message m = new Message(messagename, MessageType.MESSAGES, messages, sound);
		StorageManager.messages.add(m);
	}
	
	private static void loadTitle(MessageName messagename, Sound sound, String path){
		String title = ConfigManager.messages.getString(path + ".Title");
		String subtitle = ConfigManager.messages.getString(path + ".Subtitle");
		String[] settings = ConfigManager.messages.getString(path + ".Settings").split("\\|");
		int fadein = Integer.parseInt(settings[0]);
		int fadeout = Integer.parseInt(settings[1]);
		int stay = Integer.parseInt(settings[2]);
		Message m = new Message(messagename, MessageType.TITLE, title, subtitle, fadein, fadeout, stay, sound);
		StorageManager.messages.add(m);
	}
	
	private static void loadActionBar(MessageName messagename, Sound sound, String path){
		String message = ConfigManager.messages.getString(path);
		Message m = new Message(messagename, MessageType.ACTIONBAR, message, sound);
		StorageManager.messages.add(m);
	}
	
	private static Sound loadSound(String path){
		String sound = ConfigManager.messages.getString("Messages." + path + ".Sound");
		
		if(sound != null){
			try{
				return Sound.valueOf(sound);
			}catch(IllegalArgumentException ex){
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	private static boolean contains(String s){
		if(ConfigManager.messages.contains("Messages." + s)){
			return true;
		}
		return false;
	}
	
	private static void logError(String s){
		Bukkit.getLogger().warning("");
		Bukkit.getLogger().warning("[SpigotSpleef] Error while loading Config Path 'Messages." + s + "'");
		Bukkit.getLogger().warning("");
	}
}
