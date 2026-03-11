package fadidev.spigotbridge.utils.enums;

public enum Config {

	CONFIG("cfg.yml");
	
	private static Config[] correctOrder = { CONFIG };
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
