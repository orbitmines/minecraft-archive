package me.O_o_Fadi_o_O.MedievalRPGv2.runnables;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import me.O_o_Fadi_o_O.MedievalRPGv2.Start;
import me.O_o_Fadi_o_O.MedievalRPGv2.game.Game;
import me.O_o_Fadi_o_O.MedievalRPGv2.managers.GameStateManager;
import me.O_o_Fadi_o_O.MedievalRPGv2.managers.GameWindow;
import me.O_o_Fadi_o_O.MedievalRPGv2.settings.GFXSettings;

public class GameRunnable extends JPanel implements Runnable{

	private GameWindow gw;
	private Game game;
	private GameStateManager gsm;
	private GFXSettings gfx;
	private Thread thread;
	private boolean running;
	
	private int fps;
	private int tps;
	
	private int width;
	private int height;
	
	public Graphics2D g2D;
	private BufferedImage img;
	
	public GameRunnable(){
		gw = Start.getInstance().getGameWindow();
		game = gw.getGame();
		gfx = gw.getSettings();
		width = gw.getWidth();
		height = gw.getHeight();
		
		setPreferredSize(new Dimension(width, height));
		setFocusable(false);
		requestFocus();
	}
	
	@Override
	public void addNotify(){
		super.addNotify();
		
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run(){
		
		init();
		
		long last = System.nanoTime();
		double perTick = 1000000000D / gfx.getFPS();
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double d = 0;
		
		while(running){
			long now = System.nanoTime();
			d += (now - last) / perTick;
			last = now;
			
			boolean render = false;
			
			while(d >= 1){
				ticks++;
				tick(d);
				d -= 1;
				render = true;
			}
			
			if(render){
				frames++;
				render();
			}
			
			try{
				Thread.sleep(2);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				tps = frames;
				fps = ticks;
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void init(){
		game.setPos(game.getXOffset(), game.getYOffset());
		
		GameStateManager gsm = new GameStateManager();
		gw.setStateManager(gsm);
		gsm.init();
		this.gsm = gsm;
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g2D = (Graphics2D) img.getGraphics();
		
		running = true;
	}

	public void tick(double d){
		game.setPos(game.getXOffset(), game.getYOffset());
		gsm.tick(d);
	}

	public void render(){
		g2D.clearRect(0, 0, width, height);
		
		gsm.render(g2D);
		clear();
	}
	
	public void clear(){
		Graphics g = getGraphics();
		if(img != null){
			g.drawImage(img, 0, 0, null);
		}
		g.dispose();
	}
}
