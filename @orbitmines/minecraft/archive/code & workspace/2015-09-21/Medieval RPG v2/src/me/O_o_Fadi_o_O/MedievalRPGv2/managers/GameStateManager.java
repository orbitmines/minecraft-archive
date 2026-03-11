package me.O_o_Fadi_o_O.MedievalRPGv2.managers;

import java.awt.Graphics2D;
import java.util.Stack;

import me.O_o_Fadi_o_O.MedievalRPGv2.game.GameState;
import me.O_o_Fadi_o_O.MedievalRPGv2.game.states.LevelLoader;

public class GameStateManager {

	public static Stack<GameState> states;
	
	public GameStateManager(){
		states = new Stack<GameState>();
		states.push(new LevelLoader(this));
	}

	public void init(){
		states.peek().init();
	}
	
	public void tick(double d){
		states.peek().tick(d);
	}
	
	public void render(Graphics2D g){
		states.peek().render(g);
	}
}
