package me.O_o_Fadi_o_O.MedievalRPGv2.managers;

import javax.swing.JFrame;

import me.O_o_Fadi_o_O.MedievalRPGv2.game.Game;
import me.O_o_Fadi_o_O.MedievalRPGv2.runnables.GameRunnable;
import me.O_o_Fadi_o_O.MedievalRPGv2.settings.GFXSettings;
import me.O_o_Fadi_o_O.MedievalRPGv2.settings.GFXSettings.WindowType;

public class GameWindow extends JFrame {

	private Game game;
	private GameStateManager gsm;
	private GameRunnable gr;
	private GFXSettings gfx;
	private WindowType type;
	
	public GameWindow(WindowType type){
		game = new Game();
		
		setTitle("Medieval RPG");
		updateSize(type);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public Game getGame(){
		return game;
	}

	public GameStateManager getStateManager(){
		return gsm;
	}
	public void setStateManager(GameStateManager gsm){
		this.gsm = gsm;
	}
	
	public GameRunnable getRunnable(){
		return gr;
	}
	public void setRunnable(GameRunnable gr){
		this.gr = gr;
	}
	
	public GFXSettings getSettings(){
		return gfx;
	}
	public void setSettings(GFXSettings gfx){
		this.gfx = gfx;
	}
	
	private void updateSize(WindowType type){
		if(this.type != type){
			if(type != WindowType.FULL_SCREEN){
				setSize(type.getWidth(), type.getHeight());
			}
			else{
				dispose();
				setExtendedState(JFrame.MAXIMIZED_BOTH);
				setUndecorated(true);
			}
			setVisible(true);
			
			this.type = type;
		}
	}
	
	public void updateSettings(){
		if(gfx != null){
			updateSize(gfx.getWindowType());
			
			return;
		}
		
		throw new NullPointerException("Error while loading GFX Settings.");
	}
}
