package me.O_o_Fadi_o_O.MedievalRPGv2.game.states;

import java.awt.Graphics2D;

import me.O_o_Fadi_o_O.MedievalRPGv2.game.GameState;
import me.O_o_Fadi_o_O.MedievalRPGv2.game.objects.Map;
import me.O_o_Fadi_o_O.MedievalRPGv2.managers.GameStateManager;

public class LevelLoader extends GameState {
	
	Map map;
	
	public LevelLoader(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		map = new Map();
		map.init();
	}

	public void tick(double d) {
		map.tick(d);
	}

	public void render(Graphics2D g) {
		g.drawString("Hello World", 200, 200);
		map.render(g);
	}
}
