package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

public enum ChickenFightKitEnum {

	CHICKEN_MAMA,
	BABY_CHICKEN,
	HOT_WING,
	CHICKEN_WARRIOR;
	
	@Override
	public String toString(){
		switch(this){
			case CHICKEN_MAMA:
				return "Chicken Mama";
			case BABY_CHICKEN:
				return "Baby Chicken";
			case HOT_WING:
				return "Hot Wing";
			case CHICKEN_WARRIOR:
				return "Chicken Warrior";
			default:
				return null;
		}
	}
}
