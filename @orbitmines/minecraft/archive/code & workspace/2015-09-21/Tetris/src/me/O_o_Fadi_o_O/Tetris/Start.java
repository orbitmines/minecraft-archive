package me.O_o_Fadi_o_O.Tetris;

import javax.swing.JFrame;

import me.O_o_Fadi_o_O.Tetris.managers.Game;

public class Start {

	private static Start instance;
	private JFrame game;
	
	public static void main(String[] args){
		new Start();
	}
	
	public Start(){
		instance = this;
		
		game = new Game();
		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}
	
	public static Start getInstance(){
		return instance;
	}
	public JFrame getGame(){
		return game;
	}
}
