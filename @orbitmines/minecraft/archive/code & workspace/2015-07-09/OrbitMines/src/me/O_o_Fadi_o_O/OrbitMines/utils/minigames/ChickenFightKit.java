package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

public enum ChickenFightKit {

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
	
	public int getPrice(){
		switch(this){
			case BABY_CHICKEN:
				return 1000;
			case CHICKEN_MAMA:
				return 0;
			case CHICKEN_WARRIOR:
				return 1000;
			case HOT_WING:
				return 1000;
			default:
				return 0;
		}
	}
}
