package fadidev.spigotspleef.handlers;

import fadidev.spigotspleef.SpigotSpleef;
import fadidev.spigotspleef.managers.ConfigManager;
import fadidev.spigotspleef.utils.Utils;
import fadidev.spigotspleef.utils.enums.Config;
import fadidev.spigotspleef.utils.enums.Message;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MessageLoader {

	private SpigotSpleef ss;
	private ConfigManager cfgM;
	private Config config;
	private Message MSG;
	private String path;
	private String message;
	private List<String> messageList;
	private Title title;
	private ActionBar actionBar;
	
	public MessageLoader(Config config, String path){
		this.ss = SpigotSpleef.getInstance();
		this.cfgM = ss.getConfigManager();
		this.config = config;
		this.path = path;

		load();
	}
	
	public MessageLoader(Message message){
		this.ss = SpigotSpleef.getInstance();
		this.cfgM = ss.getConfigManager();
		this.config = message.getConfig();
		this.MSG = message;
		this.path = message.getPath();

		load();
	}
	
	public void load(){
		FileConfiguration c = cfgM.get(this.config);

		if(c.get(getPath()) != null){
			if(MSG == null || MSG.canLoadNormal()){
				if(c.get(getPath() + ".Message") != null){
					this.message = c.getString(getPath() + ".Message").replace("&", "§");
				}
				if(c.get(getPath() + ".MessageList") != null){
					this.messageList = new ArrayList<>();
					for(String s : c.getStringList(getPath() + ".MessageList")){
						messageList.add(s.replace("&", "§"));
					}
				}
				if(c.get(getPath() + ".Title") != null){
					int fadeIn = c.getInt(getPath() + ".Title.FadeIn");
					int stay = c.getInt(getPath() + ".Title.Stay");
					int fadeOut = c.getInt(getPath() + ".Title.FadeOut");
					String title = c.getString(getPath() + ".Title.Title").replace("&", "§");
					String subTitle = c.getString(getPath() + ".Title.Subtitle").replace("&", "§");

					this.title = new Title(title, subTitle, fadeIn, stay, fadeOut);
				}
				if(c.get(getPath() + ".ActionBar") != null){
					this.actionBar = new ActionBar(null, c.getString(getPath() + ".ActionBar.ActionBar").replace("&", "§"), c.getInt(getPath() + ".ActionBar.Stay"));
				}
			}
			else{
				this.message = c.getString(getPath()).replace("&", "§");
			}
		}
		else{
			Utils.warnConsole("Cannot find " + getPath() + " in " + getConfig().getFileName() + ".");
		}
	}
	
	public Config getConfig() {
		return config;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<String> getMessageList() {
		return messageList;
	}
	
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	
	public Title getTitle() {
		return title;
	}
	
	public void setTitle(Title title) {
		this.title = title;
	}
	
	public ActionBar getActionBar() {
		return actionBar;
	}
	
	public void setActionBar(ActionBar actionBar) {
		this.actionBar = actionBar;
	}
	
	public MessageParser getParser(SpleefPlayer spleefPlayer){
		return new MessageParser(spleefPlayer, this);
	}
}
