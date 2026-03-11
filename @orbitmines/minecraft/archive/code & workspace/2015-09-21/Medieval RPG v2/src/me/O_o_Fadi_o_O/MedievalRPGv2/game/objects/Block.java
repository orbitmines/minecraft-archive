package me.O_o_Fadi_o_O.MedievalRPGv2.game.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import me.O_o_Fadi_o_O.MedievalRPGv2.game.utils.Vector;

public class Block extends Rectangle {

	private Vector v;
	private int size = 32;
	
	public Block(Vector v){
		this.v = v;
	}
	
	public void tick(double d){
		
	}
	
	public void render(Graphics2D g){
		g.drawRect((int) v.getWorldLocation().xPos, (int) v.getWorldLocation().yPos, size, size);
	}
}
