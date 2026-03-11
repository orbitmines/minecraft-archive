package fadidev.bungeemsg.utils.enums;

public enum Config {

	ADVERTISE("advertise-cfg.yml"),
	ANNOUNCER("announcer-cfg.yml"),
	BANNEDWORDS("bannedwords-cfg.yml"),
	CONFIG("cfg.yml"),
	CHANNEL("channel-cfg.yml"),
	COMMAND("command-cfg.yml"),
	GROUP("group-cfg.yml"),
	LOG("log-cfg.yml"),
	MUTED("muted.yml"),
	PLAYERDATA("playerdata.yml"),
	SPAM("spam-cfg.yml");
	
	private String fileName;
	
	Config(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return fileName;
	}
}
