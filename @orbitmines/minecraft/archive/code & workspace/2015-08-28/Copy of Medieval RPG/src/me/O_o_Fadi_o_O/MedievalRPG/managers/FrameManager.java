package me.O_o_Fadi_o_O.MedievalRPG.managers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import me.O_o_Fadi_o_O.MedievalRPG.Start;
import me.O_o_Fadi_o_O.MedievalRPG.managers.ImageManager.ImageType;
import me.O_o_Fadi_o_O.MedievalRPG.utils.Menu;

public class FrameManager {

	private JFrame jframe;
	private int max_width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private int max_height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public void generateFrame(Menu menu){
		JFrame f = getNewJFrame();
		jframe = f;
		ImageManager im = Start.getInstance().getImageManager();
		
		switch(menu){
			case UPDATE:
				int width = (max_width / 5) * 3;
				int height = (max_height / 5) * 3;
				
				f.setTitle("Medieval RPG Launcher");
				f.setPreferredSize(new Dimension(width, height));
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setLayout(new BorderLayout());
				f.setBackground(Color.gray);
				
				List<ImageToDraw>
				im.draw(ImageType.CLOSE_BUTTON, width - ImageType.CLOSE_BUTTON.getWidth(5), 0, 5);
				im.draw(ImageType.MINIMIZE_BUTTON, width - ImageType.MINIMIZE_BUTTON.getWidth(5)- ImageType.CLOSE_BUTTON.getWidth(5), 0, 5);

				f.pack();
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				break;
			case CLASS:
				break;
			case LOBBY:
				break;
			case LOGIN:
				break;
			case REGISTER:
				break;
			case WORLD:
				break;
		}
	}
	
	private JFrame getNewJFrame(){
		if(getJFrame() != null){
			return getJFrame();
		}
		
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setBackground(Color.WHITE);
		return f;
	}
	
	public JFrame getJFrame(){
		return jframe;
	}
}
