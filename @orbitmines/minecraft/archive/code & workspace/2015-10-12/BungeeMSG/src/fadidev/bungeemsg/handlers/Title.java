package fadidev.bungeemsg.handlers;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Title {

	private String title;
	private String subTitle;
	private int fadeIn;
	private int stay;
	private int fadeOut;
	
	public Title(String title, String subTitle, int fadeIn, int stay, int fadeOut){
		this.title = title;
		this.subTitle = subTitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}
	
	public void send(ProxiedPlayer player){
		//TODO Parser
		net.md_5.bungee.api.Title t = ProxyServer.getInstance().createTitle();
		t.fadeIn(fadeIn * 20);
		t.stay(stay * 20);
		t.fadeOut(fadeOut * 20);
		t.title(new TextComponent(title));
		t.subTitle(new TextComponent(subTitle));
		t.send(player);
	}
}
