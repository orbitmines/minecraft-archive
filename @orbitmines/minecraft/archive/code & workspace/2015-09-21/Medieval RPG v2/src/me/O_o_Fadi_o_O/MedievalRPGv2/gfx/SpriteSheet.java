package me.O_o_Fadi_o_O.MedievalRPGv2.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage spritesheet;
	
	public SpriteSheet(){
		
	}
	
	public void set(BufferedImage spritesheet){
		this.spritesheet = spritesheet;
	}
	
	public BufferedImage getTile(int xTile, int yTile, int width, int height){
		return spritesheet.getSubimage(xTile, yTile, width, height);
	}
}
