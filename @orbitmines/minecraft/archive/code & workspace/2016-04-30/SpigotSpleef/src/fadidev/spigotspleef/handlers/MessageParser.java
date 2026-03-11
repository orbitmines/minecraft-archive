package fadidev.spigotspleef.handlers;

import fadidev.spigotspleef.SpigotSpleef;
import fadidev.spigotspleef.utils.enums.Variable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageParser {

	private SpigotSpleef ss;
	private SpleefPlayer sp;
	private String message;
	private List<String> messageList;
	private Title title;
	private ActionBar actionBar;
	
	public MessageParser(SpleefPlayer sp, MessageLoader msgL){
		this.ss = SpigotSpleef.getInstance();
		this.sp = sp;
		
		this.message = msgL.getMessage();
		
		if(msgL.getMessageList() != null){
			List<String> messageList = new ArrayList<>();
			messageList.addAll(msgL.getMessageList());
			this.messageList = messageList;
		}
		
		if(msgL.getTitle() != null) this.title = msgL.getTitle().copy();
		if(msgL.getActionBar() != null) this.actionBar = msgL.getActionBar().copy();
	}
	
	public MessageParser(SpleefPlayer sp, String message){
		this.ss = SpigotSpleef.getInstance();
		this.sp = sp;
		
		this.message = message;
	}
	
	public void parseVariable(Variable variable, String replacement){
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

	public void parseVariable(String variable, String replacement){
		if(message != null) message = message.replace(variable, replacement);

		if(messageList != null){
			int index = 0;
			for(String s : messageList){
				messageList.set(index, s.replace(variable, replacement));
				index++;
			}
		}

		if(title != null){
			title.setTitle(title.getTitle().replace(variable, replacement));
			title.setSubTitle(title.getSubTitle().replace(variable, replacement));
		}

		if(actionBar != null) actionBar.setMessage(actionBar.getMessage().replace(variable, replacement));
	}

	public void send(Player p){
		if(message != null){
			p.sendMessage(message);
		}

		if(messageList != null){
			int index = 0;
			for(String s : messageList){
				index++;
				p.sendMessage(s);
			}
		}

		if(title != null){
			title.setTitle(title.getTitle());
			title.setSubTitle(title.getSubTitle());
			ss.getNms().title().send(sp, title);
		}

		if(actionBar != null){
			actionBar.setPlayer(p);
			actionBar.setMessage(actionBar.getMessage());
			actionBar.send();
			actionBar.start();
		}
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
}
