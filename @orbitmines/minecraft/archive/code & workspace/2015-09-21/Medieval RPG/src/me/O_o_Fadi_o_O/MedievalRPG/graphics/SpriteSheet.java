package me.O_o_Fadi_o_O.MedievalRPG.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public String path;
	public int width;
	public int height;
	public int[] pixels;
	
	public SpriteSheet(String path){
		BufferedImage img = null;
		try{
			img = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		if(img == null){
			return;
		}
		
		this.path = path;
		this.width = img.getWidth();
		this.height = img.getHeight();
		pixels = img.getRGB(0, 0, width, height, null, 0, width);
		
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = (pixels[i] & 0xff) / 64;
		}
	}
	
}
