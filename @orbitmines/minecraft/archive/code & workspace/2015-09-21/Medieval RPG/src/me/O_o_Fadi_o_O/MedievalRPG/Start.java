package me.O_o_Fadi_o_O.MedievalRPG;

import me.O_o_Fadi_o_O.MedievalRPG.managers.FrameManager;
import me.O_o_Fadi_o_O.MedievalRPG.managers.PixelManager;
import me.O_o_Fadi_o_O.MedievalRPG.utils.Menu;

public class Start {

	private static Start instance;
	private FrameManager framemanager;
	private PixelManager pixelmanager;
	
	public static void main(String[] args){
		new Start();
	}
	
	public Start(){
		instance = this;
		framemanager = new FrameManager();
		pixelmanager = new PixelManager();
		
		toMenu(Menu.UPDATE);
		getPixelManager().startRunnable();
	}
	
	private void toMenu(Menu menu){
		getFrameManager().generateFrame(menu);
	}
	
	public static Start getInstance(){
		return instance;
	}
	public FrameManager getFrameManager(){
		return framemanager;
	}
	public PixelManager getPixelManager(){
		return pixelmanager;
	}
}
