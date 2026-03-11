package fadidev.bungeemsg.handlers;

import java.util.List;

import fadidev.bungeemsg.utils.enums.CommandType;

public class Command {

	private boolean use;
	private CommandType type;
	private boolean usePermission;
	private String permission;
	private List<String> commands;
	private String wrongUsage;
	private String noPermission;
	
	public Command(boolean use, CommandType type, boolean usePermission, String permission, List<String> commands, String wrongUsage, String noPermission){
		this.use = use;
		this.type = type;
		this.usePermission = usePermission;
		this.permission = permission;
		this.commands = commands;
		this.wrongUsage = wrongUsage;
		this.noPermission = noPermission;
	}
	
	public boolean isUsed() {
		return use;
	}
	
	public CommandType getType() {
		return type;
	}
	
	public boolean usePermission() {
		return usePermission;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public List<String> getCommands() {
		return commands;
	}
	
	public String getWrongUsage() {
		return wrongUsage;
	}
	
	public String getNoPermission() {
		return noPermission;
	}
}
