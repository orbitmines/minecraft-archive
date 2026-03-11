package om.api.utils.enums.cp;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;

public enum TrailType {

	BASIC_TRAIL,
	GROUND_TRAIL,
	HEAD_TRAIL,
	BODY_TRAIL,
	BIG_TRAIL,
	VERTICAL_TRAIL,
	CYLINDER_TRAIL,
	ORBIT_TRAIL,
	SNAKE_TRAIL;
	
	public String getName(){
		switch(this){
			case BASIC_TRAIL:
				return "§7§lBasic Trail";
			case BIG_TRAIL:
				return "§7§lBig Trail";
			case BODY_TRAIL:
				return "§7§lBody Trail";
			case GROUND_TRAIL:
				return "§7§lGround Trail";
			case HEAD_TRAIL:
				return "§7§lHead Trail";
			case VERTICAL_TRAIL:
				return "§7§lVertical Trail";
			case CYLINDER_TRAIL:
				return "§7§lCylinder Trail";
			case ORBIT_TRAIL:
				return "§7§lOrbit Trail";
			case SNAKE_TRAIL:
				return "§7§lSnake Trail";
			default:
				return null;
		}
	}
	
	public String getDatabaseName(){
		switch(this){
			case BIG_TRAIL:
				return "TypeBig";
			case BODY_TRAIL:
				return "TypeBody";
			case GROUND_TRAIL:
				return "TypeGround";
			case HEAD_TRAIL:
				return "TypeHead";
			case VERTICAL_TRAIL:
				return "TypeVertical";
			case CYLINDER_TRAIL:
				return "TypeCylinder";
			case ORBIT_TRAIL:
				return "TypeOrbit";
			case SNAKE_TRAIL:
				return "TypeSnake";
			default:
				return null;
		}
	}
	
	public int getPrice(){
		switch(this){
			case BASIC_TRAIL:
				return 0;
			case BIG_TRAIL:
				return 650;
			case BODY_TRAIL:
				return 400;
			case GROUND_TRAIL:
				return 600;
			case HEAD_TRAIL:
				return 400;
			case VERTICAL_TRAIL:
				return 500;
			case CYLINDER_TRAIL:
				return 1500;
			case ORBIT_TRAIL:
				return 1750;
			case SNAKE_TRAIL:
				return 1000;
			default:
				return 0;
		}
	}
	
	public String getPriceString(){
		return "§cPrice: §b" + getPrice() + " VIP Points";
	}
	
	public boolean hasTrailType(OMPlayer omp){
		switch(this){
			case BASIC_TRAIL:
				return true;
			default:
				return omp.hasTrailType(this);
		}
	}
}

