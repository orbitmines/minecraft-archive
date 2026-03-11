package me.O_o_Fadi_o_O.MedievalRPG.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.O_o_Fadi_o_O.MedievalRPG.Start;

public class InputHandler implements KeyListener {

	public InputHandler(Start start){
		start.getFrameManager().getJFrame().addKeyListener(this);
	}
	
	public class Key {
		
		public boolean pressed = false;
		
		public void toggle(boolean pressed){
			this.pressed = pressed;
		}
		
		public boolean isPressed(){
			return pressed;
		}
	}
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	
	public void keyPressed(KeyEvent e){
		toggle(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e){
		toggle(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void toggle(int keycode, boolean pressed){
		if(keycode == KeyEvent.VK_W){ 
			up.toggle(pressed); 
		}
		if(keycode == KeyEvent.VK_S){ 
			down.toggle(pressed); 
		}
		if(keycode == KeyEvent.VK_A){ 
			left.toggle(pressed); 
		}
		if(keycode == KeyEvent.VK_D){ 
			right.toggle(pressed); 
		}
	}
}
