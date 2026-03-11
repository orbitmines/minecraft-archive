package me.O_o_Fadi_o_O.MedievalRPGv2.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;

import me.O_o_Fadi_o_O.MedievalRPGv2.game.objects.Block;

public class TileManager {

	public ArrayList<Block> blocks;
	
	public TileManager(){
		blocks = new ArrayList<Block>();
	}
	
	public void tick(double d){
		for(Block b : blocks){
			b.tick(d);
		}
	}
	
	public void render(Graphics2D g){
		for(Block b : blocks){
			b.render(g);
		}
	}
}
