package fadidev.bungeemsg.handlers;

import java.util.List;

public class Channel {

	private String name;
	private String permission;
	private List<String> startSymbols;
	private List<String> toggleSymbols;
	private String message;
	private String enabledMessage;
	private String disabledMessage;
	
	public Channel(String name, String permission, List<String> startSymbols, List<String> toggleSymbols, String message, String enabledMessage, String disabledMessage){
		this.name = name;
		this.permission = permission;
		this.startSymbols = startSymbols;
		this.toggleSymbols = toggleSymbols;
		this.message = message;
		this.enabledMessage = enabledMessage;
		this.disabledMessage = disabledMessage;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public List<String> getStartSymbols() {
		return startSymbols;
	}
	
	public List<String> getToggleSymbols() {
		return toggleSymbols;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getEnabledMessage() {
		return enabledMessage;
	}
	
	public String getDisabledMessage() {
		return disabledMessage;
	}
}
