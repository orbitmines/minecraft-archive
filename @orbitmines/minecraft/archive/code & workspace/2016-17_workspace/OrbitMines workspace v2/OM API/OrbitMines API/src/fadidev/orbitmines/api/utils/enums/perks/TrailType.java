package fadidev.orbitmines.api.utils.enums.perks;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Messages;

public enum TrailType {

	BASIC_TRAIL("§7§lBasic Trail", null, -1),
	GROUND_TRAIL("§7§lGround Trail", "TypeGround", 600),
	HEAD_TRAIL("§7§lHead Trail", "TypeHead", 400),
	BODY_TRAIL("§7§lBody Trail", "TypeBody", 400),
	BIG_TRAIL("§7§lBig Trail", "TypeBig", 650),
	VERTICAL_TRAIL("§7§lVertical Trail", "TypeVertical", 500),
	CYLINDER_TRAIL("§7§lCylinder Trail", "TypeCylinder", 1500),
	ORBIT_TRAIL("§7§lOrbit Trail", "TypeOrbit", 1750),
	SNAKE_TRAIL("§7§lSnake Trail", "TypeSnake", 1000);
	
	private String name;
	private String databaseName;
	private int price;
	
	TrailType(String name, String databaseName, int price){
		this.name = name;
		this.databaseName = databaseName;
		this.price = price;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDatabaseName(){
		return databaseName;
	}
	
	public int getPrice(){
		return price;
	}
	
	public String getPriceString(OMPlayer omp){
		return "§c" + Messages.WORD_PRICE.get(omp) + ": §b" + getPrice() + " VIP Points";
	}
	
	public boolean hasTrailType(OMPlayer omp){
		if(this == BASIC_TRAIL) return true;
		return omp.hasTrailType(this);
	}
}

