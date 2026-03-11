package fadidev.centrumpvpbungee.handlers;

import net.md_5.bungee.api.connection.ProxiedPlayer;


public class Group {

	private String name;
	private boolean isDefault;
	private String format;
	private int delay;
	
	public Group(String name, boolean isDefault, String format, int delay){
		this.name = name;
		this.isDefault = isDefault;
		this.format = format.replace("&", "§");
		this.delay = delay;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isDefault() {
		return isDefault;
	}
	
	public String getFormat() {
		return format;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public String getFormat(ProxiedPlayer p, String message){
		return this.format.replace("%player%", p.getName()).replace("%message%", message);
	}
}
