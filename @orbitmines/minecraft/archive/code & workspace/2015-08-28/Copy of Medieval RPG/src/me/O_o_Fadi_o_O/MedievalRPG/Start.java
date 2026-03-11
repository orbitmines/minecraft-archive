package me.O_o_Fadi_o_O.MedievalRPG;

import me.O_o_Fadi_o_O.MedievalRPG.managers.FrameManager;
import me.O_o_Fadi_o_O.MedievalRPG.managers.ImageManager;
import me.O_o_Fadi_o_O.MedievalRPG.utils.Menu;

public class Start {

	private static Start instance;
	private FrameManager framemanager;
	private ImageManager imagemanager;
	
	public static void main(String[] args){
		new Start();
	}
	
	public Start(){
		instance = this;
		framemanager = new FrameManager();
		imagemanager = new ImageManager();
		
		toMenu(Menu.UPDATE);
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
	public ImageManager getImageManager(){
		return imagemanager;
	}
}
