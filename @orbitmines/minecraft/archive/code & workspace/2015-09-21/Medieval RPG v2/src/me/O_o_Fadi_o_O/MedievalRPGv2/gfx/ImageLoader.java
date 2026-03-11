package me.O_o_Fadi_o_O.MedievalRPGv2.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage loadImage(Class<?> classfile, String path){
		BufferedImage img = null;
		
		try{
			img = ImageIO.read(classfile.getResource(path));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return img;
	}
}
