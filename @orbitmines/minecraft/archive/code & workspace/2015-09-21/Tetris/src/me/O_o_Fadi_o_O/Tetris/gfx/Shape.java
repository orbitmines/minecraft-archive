package me.O_o_Fadi_o_O.Tetris.gfx;

import java.util.Random;

public class Shape {

	public enum ShapeType { NONE, Z, S, LINE, T, SQUARE, L, MIRRL };
	
	private ShapeType type;
	private int cords[][];
	private int[][][] cordsT;
	
	public Shape(){
		cords = new int[4][2];
		setType(ShapeType.NONE);
	}
	
	public Shape(ShapeType type){
		cords = new int[4][2];
		setType(type);
	}
	
	public ShapeType getType(){
		return type;
	}
	public void setType(ShapeType type){
		cordsT = new int[][][] {
            {{ 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }},
            {{ 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 }},
            {{ 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 }},
            {{ 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 }},
            {{ -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 }},
            {{ 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }},
            {{ -1, -1 },{ 0, -1 }, { 0, 0 }, { 0, 1 }},
            {{ 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }}
        };
		
		for(int i = 0; i < 4; i++){
			for(int p = 0; p < 2; p++){
				cords[i][p] = cordsT[type.ordinal()][i][p];
			}
		}
		
		this.type = type;
	}
	public void setRandomType(){
		setType(ShapeType.values()[new Random().nextInt(ShapeType.values().length -1) +1]);
	}

	public int getX(int index){
		return cords[index][0];
	}
	private void setX(int index, int x){
		cords[index][0] = x;
	}
	public int getMinX(){
		int x = cords[0][0];
		for(int i = 0; i < 4; i++){
			x = Math.min(x, cords[i][0]);
		}
		return x;
	}
	public int getY(int index){
		return cords[index][1];
	}
	private void setY(int index, int y){
		cords[index][1] = y;
	}
	public int getMinY(){
		int y = cords[0][1];
		for(int i = 0; i < 4; i++){
			y = Math.min(y, cords[i][1]);
		}
		return y;
	}
	
	public Shape getRightRotated(){
		if(type == ShapeType.SQUARE){
			return this;
		}
		
		Shape s = new Shape(type);
		
		for(int i = 0; i < 4; i++){
			s.setX(i, -getY(i));
			s.setY(i, getX(i));
		}
		return s;
	}
	
	public Shape getLeftRotated(){
		if(type == ShapeType.SQUARE){
			return this;
		}
		
		Shape s = new Shape(type);
		
		for(int i = 0; i < 4; i++){
			s.setX(i, getY(i));
			s.setY(i, -getX(i));
		}
		return s;
	}
}
