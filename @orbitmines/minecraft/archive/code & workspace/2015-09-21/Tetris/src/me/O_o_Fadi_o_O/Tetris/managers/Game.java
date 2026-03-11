package me.O_o_Fadi_o_O.Tetris.managers;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Game extends JFrame {

	private JLabel jlabel;
	
	public Game(){
		jlabel = new JLabel(" 0");
		add(jlabel, BorderLayout.CENTER);
		
		GameManager gm = new GameManager(this);
		add(gm);
		gm.start();
		
		setSize(200, 400);
		setTitle("Game: Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JLabel getLabel(){
		return jlabel;
	}
}
