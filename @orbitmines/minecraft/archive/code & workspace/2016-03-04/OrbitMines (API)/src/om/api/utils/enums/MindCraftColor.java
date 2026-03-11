package om.api.utils.enums;

public enum MindCraftColor {
	
	BLUE("§1§lBlue", 11),
	YELLOW("§e§lYellow", 4),
	GREEN("§a§lGreen", 5),
	RED("§c§lRed", 14),
	LIGHT_BLUE("§b§lLight Blue", 3),
	ORANGE("§6§lOrange", 1);
	
	private String name;
	private int data;
	
	MindCraftColor(String name, int data){
		this.name = name;
		this.data = data;
	}
	
	public String getName(){
		return name;
	}
	
	public byte getData(){
		return (byte) data;
	}
}

