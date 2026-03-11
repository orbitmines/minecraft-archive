package me.O_o_Fadi_o_O.MedievalRPGv2.game.utils;

import me.O_o_Fadi_o_O.MedievalRPGv2.Start;
import me.O_o_Fadi_o_O.MedievalRPGv2.game.Game;

public class Vector {

	public float xPos;
	public float yPos;
	
	public Vector(){
		xPos = 0.0F;
		yPos = 0.0F;
	}
	
	public Vector(float xPos, float yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void normalize(){
		double length = Math.sqrt(xPos * xPos + yPos * yPos);
		
		if(length != 0.0){
			float f = 1.0f / (float) length;
			xPos *= f;
			yPos *= f;
		}
	}
	
	public boolean equals(Vector v){
		return (xPos == v.xPos && yPos == v.yPos);
	}
	
	public Vector copy(Vector v){
		xPos = v.xPos;
		yPos = v.yPos;
		
		return new Vector(xPos, yPos);
	}
	
	public Vector add(Vector v){
		xPos += v.xPos;
		yPos += v.yPos;
		
		return new Vector(xPos, yPos);
	}
	
	public Vector getView(){
		return new Vector(xPos, yPos);
	}
	public Vector getWorldLocation(){
		Game game = Start.getInstance().getGameWindow().getGame();
		return new Vector(xPos - game.getX(), yPos - game.getY());
	}
	
	public Vector zero(){
		return new Vector();
	}
	
	public static double distance(Vector v1, Vector v2){
		float f1 = v1.xPos - v2.xPos;
		float f2 = v1.yPos - v2.yPos;
		
		return Math.sqrt(f1 * f1 + f2 * f2);
	}
	
	public double distanceWorld(Vector v){
		Vector vw1 = getWorldLocation();
		Vector vw2 = v.getWorldLocation();
		float dx = Math.abs(vw1.xPos - vw2.xPos);
		float dy = Math.abs(vw1.yPos - vw2.yPos);
		
		return Math.abs(dx * dx - dy * dy);
	}
}
