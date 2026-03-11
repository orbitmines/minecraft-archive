package me.O_o_Fadi_o_O.MedievalRPG.managers;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import me.O_o_Fadi_o_O.MedievalRPG.Start;
import me.O_o_Fadi_o_O.MedievalRPG.graphics.SpriteSheet;
import me.O_o_Fadi_o_O.MedievalRPG.graphics.UpdateScreen;
import me.O_o_Fadi_o_O.MedievalRPG.graphics.maps.GameMap;
import me.O_o_Fadi_o_O.MedievalRPG.runnables.GameRunnable;
import me.O_o_Fadi_o_O.MedievalRPG.utils.InputHandler;

public class PixelManager {

	private Start start;
	private FrameManager fm;
	private boolean canrun;
	
	@SuppressWarnings("unused")
	private int ticks;
	private BufferedImage img;
	private int[] pixels;
	private int[] colours;
	
	private UpdateScreen screen;
	private InputHandler inputhandler;
	
	private GameMap gamemap;
	private int x = 0, y = 0;
	
	public synchronized void startRunnable(){
		start = Start.getInstance();
		fm = start.getFrameManager();
		canrun = true;
		img = new BufferedImage(fm.getWidth(), fm.getHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		colours = new int[6 * 6 * 6];
		
    	new Thread(new GameRunnable()).start();
	}

	@SuppressWarnings("deprecation")
	public synchronized void endRunnable(){
		canrun = false;
		
    	new Thread(new GameRunnable()).stop();
	}
	
	public void init(){
		int index = 0;
		for(int r = 0; r < 6; r++){
			for(int g = 0; g < 6; g++){
				for(int b = 0; b < 6; b++){
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					
					colours[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
		
		screen = new UpdateScreen(fm.getWidth(), fm.getHeight(), new SpriteSheet("/sprite_sheet.png"));
		inputhandler = new InputHandler(start);
		gamemap = new GameMap(500, 500);
	}
	
	public void tick(){
		ticks++;

		if(inputhandler.up.isPressed()){
			y--; 
		}
		if(inputhandler.down.isPressed()){
			y++; 
		}
		if(inputhandler.left.isPressed()){ 
			x--;
		}
		if(inputhandler.right.isPressed()){ 
			x++;
		}
		
		gamemap.tick();
	}
	
	public void render(){
		JFrame f = fm.getJFrame();
		BufferStrategy bs = f.getBufferStrategy();
		if(bs == null){
			f.createBufferStrategy(3);
			return;
		}
		
		int xOffset = x - (screen.width / 2);
		int yOffset = y - (screen.height / 2);
		gamemap.renderTiles(screen, xOffset, yOffset);
		
		for(int y = 0; y < screen.height; y++){
			for(int x = 0; x < screen.width; x++){
				int cc = screen.pixels[x + y * screen.width];
				
				if(cc < 255){ pixels[x + y * fm.getWidth()] = colours[cc]; }
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, fm.getWidth(), fm.getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	public boolean canRun(){
		return canrun;
	}
}
