package me.O_o_Fadi_o_O.MedievalRPGv2.settings;

public class GFXSettings {

	public enum WindowType { 
		NORMAL, FULL_SCREEN;
	
		public int getWidth(){
			return 1080;
		}
		public int getHeight(){
			return 720;
		}
	};
	
	private WindowType type;
	private double fps;
	
	public GFXSettings(){
		type = WindowType.NORMAL;
		fps = 100D;
	}

	public WindowType getWindowType() {
		return type;
	}
	public void setWindowType(WindowType type) {
		this.type = type;
	}
	
	public double getFPS(){
		return fps;
	}
	public void setFPS(double fps){
		this.fps = fps;
	}
}
