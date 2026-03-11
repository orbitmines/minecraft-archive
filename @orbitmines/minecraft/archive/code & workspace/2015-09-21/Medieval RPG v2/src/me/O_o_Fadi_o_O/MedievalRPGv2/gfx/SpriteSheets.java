package me.O_o_Fadi_o_O.MedievalRPGv2.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheets {

	SpriteSheet spriteSheet = new SpriteSheet();
	
	private BufferedImage stone_1;
	
	public void init(){
		spriteSheet.set(ImageLoader.loadImage(SpriteSheets.class, "spritesheet.png"));
	
		stone_1 = spriteSheet.getTile(0, 0, 16, 16);
	}
}
