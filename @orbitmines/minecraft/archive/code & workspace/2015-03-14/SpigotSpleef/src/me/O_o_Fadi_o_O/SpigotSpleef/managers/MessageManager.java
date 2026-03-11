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
		load(MessageName.JUMP_USAGE, "JumpUsage");
		load(MessageName.JUMP_ON_COOLDOWN, "JumpOnCooldown");
		
		load(MessageName.HELP_MESSAGE, "HelpMessage");
		
		load(MessageName.WRONG_USAGE_SETUP_MAP, "WrongUsageSetupMap");
		load(MessageName.WRONG_USAGE_SETUP_ARENA, "WrongUsageSetupArena");
		load(MessageName.WRONG_USAGE_STATS, "WrongUsageStats");
		load(MessageName.WRONG_USAGE_TOKENS_SHOW, "WrongUsageShowTokens");
		load(MessageName.WRONG_USAGE_TOKENS_ADD, "WrongUsageAddTokens");
		load(MessageName.WRONG_USAGE_TOKENS_REMOVE, "WrongUsageRemoveTokens");
		load(MessageName.WRONG_USAGE_TOKENS, "WrongUsageTokens");
		
		load(MessageName.NOT_ONLINE, "NotOnline");
		load(MessageName.NOT_A_NUMBER, "NotANumber");
		load(MessageName.THROUGH_CONSOLE, "ThroughConsole");
		
		load(MessageName.SET_LOBBY, "SetLobby");
		load(MessageName.SPAWN_ARENA_SELECTOR, "SpawnArenaSelector");
		load(MessageName.REMOVE_ARENA_SELECTOR, "RemoveArenaSelector");
		load(MessageName.NO_NEARBY_ARENA_SELECTOR, "NoNearbyArenaSelector");
		load(MessageName.TOKENS_SHOW, "ShowTokens");
		load(MessageName.TOKENS_ADD_TO_SENDER, "AddTokensToSender");
		load(MessageName.TOKENS_ADD_TO_RECEIVER, "AddTokensToReceiver");
		load(MessageName.TOKENS_REMOVE_TO_SENDER, "RemoveTokensToSender");
		load(MessageName.TOKENS_REMOVE_TO_RECEIVER, "RemoveTokensToReceiver");
		load(MessageName.STATS, "Stats");
		
		load(MessageName.NO_PERM_HELP, "NoPermissionHelp");
		load(MessageName.NO_PERM_SET_LOBBY, "NoPermissionSetLobby");
		load(MessageName.NO_PERM_ADD_ARENA_SELECTOR, "NoPermissionAddArenaSelector");
		load(MessageName.NO_PERM_EDIT_ARENA_SELECTOR, "NoPermissionEditArenaSelector");
		load(MessageName.NO_PERM_REMOVE_ARENA_SELECTOR, "NoPermissionRemoveArenaSelector");
		load(MessageName.NO_PERM_SETUP_MAP, "NoPermissionSetupMap");
		load(MessageName.NO_PERM_SETUP_ARENA, "NoPermissionSetupArena");
		load(MessageName.NO_PERM_STATS, "NoPermissionStats");
		load(MessageName.NO_PERM_TOKENS_SHOW, "NoPermissionShowTokens");
		load(MessageName.NO_PERM_TOKENS_ADD, "NoPermissionAddTokens");
		load(MessageName.NO_PERM_TOKENS_REMOVE, "NoPermissionRemoveTokens");
		
		load(MessageName.NO_MAP_AVAILABLE, "NoMapAvailable");
		load(MessageName.ENTER_ARENA_SETUP, "EnterArenaSetup");
		load(MessageName.OPEN_ARENA_SETUP, "OpenArenaSetup");
		load(MessageName.SET_ARENA_LOBBY_SPAWNPOINT, "SetArenaLobbySpawnpoint");
		load(MessageName.NEW_ARENA_SIGN, "NewArenaSign");
		load(MessageName.ALREADY_ARENA_SIGN, "AlreadyArenaSign");
		load(MessageName.FINISH_ARENA_SETUP, "FinishArenaSetup");
		
		load(MessageName.MAP_CURRENTLY_IN_USE, "MapCurrentlyInUse");
		load(MessageName.ENTER_MAP_SETUP, "EnterMapSetup");
		load(MessageName.OPEN_MAP_SETUP, "OpenMapSetup");
		load(MessageName.OPEN_MAP_SETUP_NAME_EDITOR, "OpenMapSetupNameEditor");
		load(MessageName.SET_MAP_NAME, "SetMapName");
		load(MessageName.SET_NEXT_MAP_SPAWNPOINT, "SetNextMapSpawnpoint");
		load(MessageName.SET_MAP_SPECTATOR_SPAWNPOINT, "SetMapSpectatorSpawnpoint");
		load(MessageName.FINISH_MAP_SETUP, "FinishMapSetup");
		
		load(MessageName.ALREADY_IN_SETUP_MODE, "AlreadyInSetupMode");
		
		load(MessageName.OPEN_NPC_EDITOR, "OpenNPCEditor");
		load(MessageName.OPEN_NPC_NAME_EDITOR, "OpenNPCNameEditor");
		load(MessageName.OPEN_NPC_MOB_EDITOR, "OpenNPCMobEditor");
		load(MessageName.SET_NPC_NAME, "SetNPCName");
		load(MessageName.SET_NPC_MOB, "SetNPCMob");
		load(MessageName.SAVE_NPC_EDITOR, "SaveNPCEditor");
		
		loadMessage(MessageName.CHAT_PREFIX_IF_NOR_PER_ARENA, null, "OtherMessages." + "ChatPrefixIfNotPerArena");	
		loadMessage(MessageName.LOBBY_CHAT_PREFIX_IF_NOT_PER_ARENA, null, "OtherMessages." + "LobbyChatPrefixIfNotPerArena");
		loadMessages(MessageName.ANIMATED_RIGHT_CLICK, null, "OtherMessages." + "AnimatedRightClick");
		loadMessage(MessageName.ANIMATED_RIGHT_CLICK_ON_COOLDOWN, null, "OtherMessages." + "AnimatedRightClickOnCooldown");
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
