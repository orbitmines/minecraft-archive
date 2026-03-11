package me.O_o_Fadi_o_O.MedievalRPGv2.game;

public class Game {

	private float xPos;
	private float yPos;
	
	private float xOffset;
	private float yOffset;
	
	public Game(){
		
	}
	
	public float getX(){
		return xPos;
	}
	public void setX(float xPos){
		this.xPos = xPos;
	}
	
	public float getY(){
		return yPos;
	}
	public void setY(float yPos){
		this.yPos = yPos;
	}
	
	public void setPos(float xPos, float yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public float getXOffset(){
		return xOffset;
	}
	public void setXOffset(float xOffset){
		this.xOffset = xOffset;
	}

	public float getYOffset() {
		return yOffset;
	}
	public void setYOffset(float yOffset){
		this.yOffset = yOffset;
	}
}
