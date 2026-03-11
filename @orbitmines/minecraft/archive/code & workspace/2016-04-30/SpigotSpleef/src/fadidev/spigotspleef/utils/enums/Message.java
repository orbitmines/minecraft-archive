package fadidev.spigotspleef.utils.enums;

import fadidev.spigotspleef.handlers.MessageLoader;
import fadidev.spigotspleef.handlers.MessageParser;
import fadidev.spigotspleef.handlers.SpleefPlayer;

public enum Message {
	
	//HOVER_MESSAGE(Config.CONFIG, "PlayerNameSuggest.HoverMessage", false);
	
	private Config config;
	private String path;
	private MessageLoader msgL;
	private boolean normalLoad;
	
	Message(Config config, String path, boolean normalLoad){
		this.config = config;
		this.path = path;
		this.normalLoad = normalLoad;
	}
	
	public Config getConfig() {
		return config;
	}
	
	public String getPath() {
		return path;
	}
	
	public MessageLoader getMSGLoader() {
		return msgL;
	}
	
	public void setMSGLoader(MessageLoader msgL) {
		this.msgL = msgL;
	}
	
	public MessageParser getParser(SpleefPlayer spleefPlayer){
		return msgL.getParser(spleefPlayer);
	}
	
	public boolean canLoadNormal() {
		return normalLoad;
	}
}
