package me.O_o_Fadi_o_O.MedievalRPGv2.game;

import java.awt.Graphics2D;

import me.O_o_Fadi_o_O.MedievalRPGv2.managers.GameStateManager;

public abstract class GameState {

	GameStateManager gsm;
	
	public GameState(GameStateManager gsm){
		this.gsm = gsm;
	}

	public abstract void init();
	public abstract void tick(double d);
	public abstract void render(Graphics2D g);
}
