package me.O_o_Fadi_o_O.MedievalRPG.graphics.maps.tiles;

import me.O_o_Fadi_o_O.MedievalRPG.graphics.UpdateScreen;
import me.O_o_Fadi_o_O.MedievalRPG.graphics.maps.GameMap;
import me.O_o_Fadi_o_O.MedievalRPG.graphics.maps.Tile;

public class BasicTile extends Tile {

	protected int tileId;
	protected int tileColor;
	
	public BasicTile(int id, int x, int y, int tileColor) {
		super(id, false, false);
		this.tileId = x + y;
		this.tileColor = tileColor;
	}

	public void render(UpdateScreen screen, GameMap map, int x, int y) {
		screen.render(x, y, tileId, tileColor);
	}
}
