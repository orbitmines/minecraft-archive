package me.O_o_Fadi_o_O.MedievalRPGv2.game.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.O_o_Fadi_o_O.MedievalRPGv2.game.utils.Vector;
import me.O_o_Fadi_o_O.MedievalRPGv2.gfx.ImageLoader;
import me.O_o_Fadi_o_O.MedievalRPGv2.gfx.SpriteSheets;
import me.O_o_Fadi_o_O.MedievalRPGv2.managers.TileManager;

public class Map {

	private TileManager tm = new TileManager();
	
	public Map(){
		
	}
	
	public void init(){
		BufferedImage img = ImageLoader.loadImage(SpriteSheets.class, "map.png");

		for(int y = 0; y < 100; y++){
			for(int x = 0; x < 100; x++){
				int c = img.getRGB(x, y);
				
				switch(c & 0xFFFFFF){
					case 0x808080:
						tm.blocks.add(new Block(new Vector(x * 32, y * 32)));
						break;
				}
			}	
		}
	}
	
	public void tick(double d){
		tm.tick(d);
	}
	
	public void render(Graphics2D g){
		tm.render(g);
	}
}
