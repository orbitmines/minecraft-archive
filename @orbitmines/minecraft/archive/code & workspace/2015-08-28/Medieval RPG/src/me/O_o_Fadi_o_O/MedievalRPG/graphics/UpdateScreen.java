package me.O_o_Fadi_o_O.MedievalRPG.graphics;

public class UpdateScreen {

	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH -1;
	
	public int[] pixels;
	
	public int xOffset = 0;
	public int yOffset = 0;
	
	public int width;
	public int height;
	
	public SpriteSheet ss;
	
	public UpdateScreen(int width, int height, SpriteSheet ss){
		this.width = width;
		this.height = height;
		this.ss = ss;
		
		pixels = new int[width * height];
	}
	
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void render(int xPos, int yPos, int tile, int color){
		render(xPos, yPos, tile, color, false, false);
	}
	
	public void render(int xPos, int yPos, int tile, int color, boolean mirrorX, boolean mirrorY){
		xPos -= xOffset;
		yPos -= yOffset;
		
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile << 3) + (yTile << 3) * ss.width;
		
		for(int y = 0; y < 8; y++){
			int ySheet = y;
			if(mirrorY){ ySheet = 7 - y; }
			if(y + yPos < 0 || y + yPos >= height){ continue; }
			
			for(int x = 0; x < 8; x++){
				int xSheet = x;
				if(mirrorX){ xSheet = 7 - x; }

				if(x + xPos < 0 || x + xPos >= width){ continue; }
				int col = (color >> (ss.pixels[xSheet + ySheet * ss.width + tileOffset] * 8)) & 255;
				if(col < 255){ pixels[(x + xPos) + (y + yPos) * width] = col; }
			}
		}
	}
}
