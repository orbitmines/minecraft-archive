package fadidev.orbitmines.api.handlers.chat;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ComponentMessage {
	
	private List<TextComponent> tcs;
	
	public ComponentMessage(){
		tcs = new ArrayList<TextComponent>();
	}
	
	public void addPart(String part, ClickEvent.Action clickAction, String clickEvent, HoverEvent.Action hoverAction, String hoverEvent){
		TextComponent tc = new TextComponent(part);
		if(clickAction != null)
			tc.setClickEvent(new ClickEvent(clickAction, clickEvent));
		if(hoverAction != null)
			tc.setHoverEvent(new HoverEvent(hoverAction, new ComponentBuilder(hoverEvent).create()));
		
		tcs.add(tc);
	}

	public void addPart(String part, ClickEvent.Action clickAction, String clickEvent){
		TextComponent tc = new TextComponent(part);
		if(clickAction != null)
			tc.setClickEvent(new ClickEvent(clickAction, clickEvent));

		tcs.add(tc);
	}

	public void addPart(String part, HoverEvent.Action hoverAction, String hoverEvent){
		TextComponent tc = new TextComponent(part);
		if(hoverAction != null)
			tc.setHoverEvent(new HoverEvent(hoverAction, new ComponentBuilder(hoverEvent).create()));

		tcs.add(tc);
	}

	public void addPart(String part){
		TextComponent tc = new TextComponent(part);
		tcs.add(tc);
	}
	
	public void send(Player... players){
		TextComponent[] tcs = new TextComponent[this.tcs.size()];
		int index = 0;
		for(TextComponent tc : this.tcs){
			tcs[index] = tc;
			index++;
		}
		
		TextComponent tc = new TextComponent(tcs);
		for(Player player : players){
			player.spigot().sendMessage(tc);
		}
	}
}
