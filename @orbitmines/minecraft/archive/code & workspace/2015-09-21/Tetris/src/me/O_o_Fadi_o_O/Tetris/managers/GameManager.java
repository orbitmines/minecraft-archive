package me.O_o_Fadi_o_O.Tetris.managers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import me.O_o_Fadi_o_O.Tetris.gfx.Shape;
import me.O_o_Fadi_o_O.Tetris.gfx.Shape.ShapeType;

@SuppressWarnings("serial")
public class GameManager extends JPanel implements ActionListener {

	private final int GAME_WIDTH = 10;
	private final int GAME_HEIGHT = 22;
	
	private Timer t;
	private boolean onGround = false;
	private boolean hasStarted = false;
	private boolean isPaused = false;

	private int removedLines = 0;
	private int cX = 0;
	private int cY = 0;
	
	private JLabel jlabel;
	private Shape cShape;
	private ShapeType[] allShapes;
	
	private Color colors[] = { 
			new Color(0, 0, 0), new Color(204, 102, 102), 
			new Color(102, 204, 102), new Color(102, 102, 204), 
			new Color(204, 204, 102), new Color(204, 102, 204), 
			new Color(102, 204, 204), new Color(218, 170, 0)
		};
	
	public GameManager(Game game){
		setFocusable(true);
		cShape = new Shape();
		t = new Timer(400, this);
		t.start();
		
		jlabel = game.getLabel();
		allShapes = new ShapeType[GAME_WIDTH * GAME_HEIGHT];
		
		addKeyListener(new Adapter());
		clearGame();
	}
	
	public void actionPerformed(ActionEvent e){
		if(onGround){
			onGround = false;
			newShape();
		}
		else{
			lineDown();
		}
	}
	
	private int getBlockWidth(){
		return (int) getSize().getWidth() / GAME_WIDTH;
	}
	private int getBlockHeight(){
		return (int) getSize().getHeight() / GAME_HEIGHT;
	}
	private ShapeType getShapeAt(int x, int y){
		return allShapes[(y * GAME_WIDTH) + x];
	}
	
	public void start(){
		if(isPaused){
			return;
		}
		
		hasStarted = true;
		onGround = false;
		removedLines = 0;
		
		clearGame();
		t.start();
	}
	public void pause(){
		if(!hasStarted){
			return;
		}
		
		isPaused = !isPaused;
		if(isPaused){
			t.stop();
			jlabel.setText("Paused");
		}
		else{
			t.start();
			jlabel.setText("" + removedLines);
		}
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		Dimension d = getSize();
		int top = (int) d.getHeight() - GAME_HEIGHT * getBlockHeight();
		
		for(int i = 0; i < GAME_HEIGHT; i++){
			for(int x = 0; x < GAME_WIDTH; x++){
				ShapeType type = getShapeAt(x, GAME_HEIGHT - i - 1);
				
				if(type != ShapeType.NONE){
					drawBlock(g, x * getBlockWidth(), top + i * getBlockHeight(), type);
				}
			}
		}
		
		if(cShape.getType() != ShapeType.NONE){
			for(int i = 0; i < 4; i++){
				int x = cX + cShape.getX(i);
				int y = cY + cShape.getY(i);
				drawBlock(g, x * getBlockWidth(), top + (GAME_HEIGHT - y - 1) * getBlockHeight(), cShape.getType());
			}
		}
	}
	
	private void down(){
		int y = cY;
		while(y > 0){
			if(!tryMove(cShape, cX, cY -1)){
				break;
			}
			y--;
		}
		drop();
	}
	
	private void lineDown(){
		if(!tryMove(cShape, cX, cY -1)){
			drop();
		}
	}
	
	private void clearGame(){
		for(int i = 0; i < GAME_HEIGHT * GAME_WIDTH; i++){
			allShapes[i] = ShapeType.NONE;
		}
	}
	
	private void drop(){
		for(int i = 0; i < 4; i++){
			int x = cX + cShape.getX(i);
			int y = cY + cShape.getY(i);
			allShapes[(y * GAME_WIDTH) + x] = cShape.getType();
		}
		
		removeLines();
		
		if(!onGround){
			newShape();
		}
	}
	
	private void newShape(){
		cShape.setRandomType();
		cX = GAME_WIDTH / 2 + 1;
		cY = GAME_HEIGHT - 1 + cShape.getMinY();
		
		if(!tryMove(cShape, cX, cY)){
			cShape.setType(ShapeType.NONE);
			
			hasStarted = false;
			t.stop();
			
			jlabel.setText("Game Over");
		}
	}
	
	private boolean tryMove(Shape shape, int x, int y){
		for(int i = 0; i < 4; i++){
			int newX = x + shape.getX(i);
			int newY = y - shape.getY(i);
			
			if(newX < 0 || newX >= GAME_WIDTH || newY < 0 || newY >= GAME_HEIGHT){
				return false;
			}
			if(getShapeAt(newX, newY) != ShapeType.NONE){
				return false;
			}
		}
		
		cShape = shape;
		cX = x;
		cY = y;
		repaint();
		
		return true;
	}
	
	private void removeLines(){
		int full = 0;
		
		for(int i = GAME_HEIGHT -1; i >= 0; i--){
			boolean isFull = true;
			
			for(int x = 0; x < GAME_WIDTH; x++){
				if(getShapeAt(x, i) == ShapeType.NONE){
					isFull = false;
					break;
				}
			}
			
			if(isFull){
				full++;
				
				for(int y = i; y < GAME_HEIGHT -1; y++){
					for(int x = 0; x < GAME_WIDTH; x++){
						allShapes[(y * GAME_WIDTH) + x] = getShapeAt(x, y +1);
					}
				}
			}
		}
		
		if(full > 0){
			removedLines += full;
			jlabel.setText("" + removedLines);
			
			onGround = true;
			cShape.setType(ShapeType.NONE);
			repaint();
		}
	}
	
	private void drawBlock(Graphics g, int x, int y, ShapeType type){
		Color c = colors[type.ordinal()];
		
		g.setColor(c);
		g.fillRect(x +1, y + 1, getBlockWidth() -2, getBlockHeight() -2);
		
		g.setColor(c.brighter());
		g.drawLine(x, y + getBlockHeight() -1, x, y);
		g.drawLine(x, y, x + getBlockWidth() -1, y);
		
		g.setColor(c.darker());
		g.drawLine(x +1, y + getBlockHeight() -1, x + getBlockWidth() -1, y + getBlockHeight() -1);
		g.drawLine(x + getBlockWidth() -1, y + getBlockHeight() -1, x + getBlockWidth() -1, y +1);
	}
	
	class Adapter extends KeyAdapter {
		
		public void keyPressed(KeyEvent e){
			if(!hasStarted || cShape.getType() == ShapeType.NONE){
				return;
			}
			
			int key = e.getKeyCode();
			if(key == 'p' || key == 'P'){
				pause();
				return;
			}
			
			if(isPaused){
				return;
			}
			
			switch(key){
				case KeyEvent.VK_LEFT:
					tryMove(cShape, cX -1, cY);
					break;
				case KeyEvent.VK_RIGHT:
					tryMove(cShape, cX +1, cY);
					break;
				case KeyEvent.VK_DOWN:
					tryMove(cShape.getRightRotated(), cX, cY);
					break;
				case KeyEvent.VK_UP:
					tryMove(cShape.getLeftRotated(), cX, cY);
					break;
				case KeyEvent.VK_SPACE:
					down();
					break;
				case 'd':
					lineDown();
					break;
				case 'D':
					lineDown();
					break;
			}
		}
	}
}
