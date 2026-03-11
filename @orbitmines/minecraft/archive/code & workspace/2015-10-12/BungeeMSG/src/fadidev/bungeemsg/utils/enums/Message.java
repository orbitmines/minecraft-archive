package fadidev.bungeemsg.utils.enums;

import java.util.List;

import fadidev.bungeemsg.handlers.ActionBar;
import fadidev.bungeemsg.handlers.Title;

public enum Message {
	
	HOVER_MESSAGE(Config.CONFIG, "PlayerNameSuggest.HoverMessage"),
	SUGGESTED_COMMAND(Config.CONFIG, "PlayerNameSuggest.SuggestedCommand"),
	SUGGESTED_PLAYER(""),
	
	PM_TO_RECEIVER(""),
	PM_TO_SENDER(""),
	PM_ENABLED(""),
	PM_DISABLED(""),
	PM_ENABLED_TO_SENDER(""),
	PM_DISABLED_TO_SENDER(""),
	PM_ENABLED_TO_PLAYER(""),
	PM_DISABLED_TO_PLAYER(""),
	PM_TOGGLED(""),
	REPLY_INFO(""),
	
	NOT_ONLINE(""),
	NO_RECEIVER(""),
	TO_THEMSELVES(""),
	
	SPY_PREFIX(""),
	SPY_MESSAGE(""),
	SPY_ENABLE(""),
	SPY_DISABLE(""),
	
	MUTE_TO_SENDER(""),
	MUTE_TO_PLAYER(""),
	UNMUTE_TO_SENDER(""),
	UNMUTE_TO_PLAYER(""),
	MUTE_ALL_TO_SENDER(""),
	MUTE_ALL_TO_PLAYER(""),
	UNMUTE_ALL_TO_SENDER(""),
	UNMUTE_ALL_TO_PLAYER(""),
	MUTE_SERVER_TO_SENDER(""),
	MUTE_SERVER_TO_PLAYER(""),
	MUTED(""),
	
	SPAM_DUPLICATE(""),
	SPAM_TOO_FAST(""),
	SPAM_COOLDOWN(""),
	
	SECOND_SINGLE(""),
	SECOND_MULTIPLE(""),
	
	ADVERTISE_KICK(""),
	ADVERTISE_MESSAGE(""),
	
	IP_VARIABLE(""),
	DOMAIN_VARIABLE(""),

	IGNORE_ENABLE(""),
	IGNORE_DISABLE(""),
	IGNORED(""),
	IGNORED_YOU(""),
	
	REPORT_ON_COOLDOWN(""),
	REPORTED_TO_SENDER(""),
	REPORTED_TO_STAFF(""),
	
	HELPOP_ON_COOLDOWN(""),
	HELPOP_TO_SENDER(""),
	HELPOP_TO_STAFF("");
	
	private Config config;
	private String message;
	private List<String> messageList;
	private Title title;
	private ActionBar actionBar;
	
	Message(Config config, String path){
	
	}
}
