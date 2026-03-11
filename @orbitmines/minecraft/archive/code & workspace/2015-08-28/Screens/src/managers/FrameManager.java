package EmelyPC.FadiDev.Screens.managers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;

import EmelyPC.FadiDev.Screens.Start;

public class FrameManager {

	private JFrame jframe;
	private Component lastcomponent;
	
	public void generateStartingScreen(){
		JFrame f = getNewJFrame();

		JLabel jl = new JLabel("Loading...");
		jl.setFont(jl.getFont().deriveFont(50.0f));
		jl.setHorizontalAlignment(JLabel.CENTER);
		f.add(jl);
		
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gs = ge.getDefaultScreenDevice();
	    gs.setFullScreenWindow(f);
	    f.validate();
	    
	    setLastComponent(jl);
	    
	    jframe = f;
	}
	
	public void generateUpdatingScreen(){
		JFrame f = getNewJFrame();

		JLabel jl = new JLabel("Updating...");
		jl.setFont(jl.getFont().deriveFont(50.0f));
		jl.setHorizontalAlignment(JLabel.CENTER);
		f.add(jl);
		
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gs = ge.getDefaultScreenDevice();
	    gs.setFullScreenWindow(f);
	    f.validate();
	    
	    setLastComponent(jl);
	    
	    jframe = f;
	}
	
	public void generateFullScreen(){
		JFrame f = getNewJFrame();

    	Component c = new Component() {
    		BufferedImage img = Start.getNextImage();
    		
    		@Override
    	    public void paint(Graphics g){
    	        super.paint(g);
        		Start.setLastImage(img);
    	        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    	    }
		};
    	
		setLastComponent(c);
		f.add(c);
	    
	    f.addKeyListener(new KeyAdapter(){
	    	public void keyPressed(KeyEvent e){
	    		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
	    			Start.closeScreen();
	    		}
	    		else if(e.getKeyCode() == KeyEvent.VK_U){
	    			generateUpdatingScreen();
	    			Start.updateImages();
	    		}
	    		else{}
	    	}
	    });

	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gs = ge.getDefaultScreenDevice();
	    gs.setFullScreenWindow(f);
	    f.validate();

		jframe = f;
	}
	
	private JFrame getNewJFrame(){
		if(getJFrame() == null){
			JFrame f = new JFrame();
			f.setAlwaysOnTop(true);
			f.setUndecorated(true);
			f.setBackground(Color.white);
			return f;
		}

		if(getLastComponent() != null){
			getJFrame().remove(getLastComponent());
		}
		return getJFrame();
	}

	public void setLastComponent(Component c){
		lastcomponent = c;
	}
	public Component getLastComponent(){
		return lastcomponent;
	}
	public JFrame getJFrame(){
		return jframe;
	}
}
