package fadidev.centrumpvpbungee.utils.enums;

public enum Config {

	CONFIG("config.yml"),
	MUTED("muted.yml"),
	PLAYERDATA("playerdata.yml");
	
	private static Config[] correctOrder = { CONFIG, MUTED, PLAYERDATA };
	private String fileName;
	
	Config(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public static Config[] getCorrectOrder() {
		return correctOrder;
	}
}
