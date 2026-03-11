package me.O_o_Fadi_o_O.MedievalRPGv2;

import me.O_o_Fadi_o_O.MedievalRPGv2.gfx.SpriteSheets;
import me.O_o_Fadi_o_O.MedievalRPGv2.managers.GameWindow;
import me.O_o_Fadi_o_O.MedievalRPGv2.runnables.GameRunnable;
import me.O_o_Fadi_o_O.MedievalRPGv2.settings.GFXSettings;
import me.O_o_Fadi_o_O.MedievalRPGv2.settings.GFXSettings.WindowType;

public class Start {

	public static void main(String[] args){
		new Start();
	}

	private static Start instance;
	private GameWindow gw;
	private SpriteSheets spritesheets;
	
	public Start(){
		instance = this;
		
		GameWindow gw = new GameWindow(WindowType.NORMAL);
		this.gw = gw;
		gw.setSettings(loadGFXSettings());
		
		spritesheets = new SpriteSheets();
		spritesheets.init();
		
		GameRunnable gr = new GameRunnable();
		gw.setRunnable(gr);
		gw.add(gr);
	}
	
	private GFXSettings loadGFXSettings(){
		GFXSettings gfx = new GFXSettings();
		gfx.setWindowType(WindowType.NORMAL);
		
		// TODO: Load Settings from config file
		return gfx;
	}
	
	public static Start getInstance(){
		return instance;
	}
	public GameWindow getGameWindow(){
		return gw;
	}
	public SpriteSheets getSpriteSheets(){
		return spritesheets;
	}
}
