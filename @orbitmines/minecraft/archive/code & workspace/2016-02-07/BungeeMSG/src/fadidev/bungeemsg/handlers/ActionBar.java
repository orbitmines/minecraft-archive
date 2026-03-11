package fadidev.bungeemsg.handlers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ActionBar {

	private String message;
	
	public ActionBar(String message){
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void send(ProxiedPlayer player){
		TextComponent tc1 = new TextComponent(message);
		player.sendMessage(ChatMessageType.ACTION_BAR, tc1);
	}
	
	public ActionBar copy(){
		return new ActionBar(message);
	}
}
