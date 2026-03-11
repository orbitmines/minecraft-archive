package EmelyPC.FadiDev.Screens;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import EmelyPC.FadiDev.Screens.managers.FrameManager;
import EmelyPC.FadiDev.Screens.runnables.GalleryRunnable;

public class Start {

    // System.out.println("Hello World!");
	private static FrameManager frameManager = new FrameManager();
	private static List<String> fileNames = new ArrayList<String>();
	private static List<BufferedImage> images = new ArrayList<BufferedImage>();
	private static BufferedImage lastimage;
	private static GalleryRunnable galleryrunnable;
	
    public static void main(String[] args){
    	getFrameManager().generateStartingScreen();
    	fileNames = Arrays.asList("Screen1.png", "Screen2.png");
    	
    	updateImages();
    	updateScreen();
    }
    
    public static void updateImages(){
    	images.clear();
    	if(galleryrunnable != null){
    		galleryrunnable.cancel();
    	}
    	
    	for(String fileName : fileNames){
    		try{
				BufferedImage img = ImageIO.read(new URL("http://www.orbitmines.com/" + fileName));
				
				if(img != null){
					images.add(img);
				}
			}catch(IOException e){}
    	}
    	
    	startGalleryRunnable();
    }
    
    public static void closeScreen(){
    	getFrameManager().getJFrame().dispose();
    	galleryrunnable.cancel();
    }
    
    private static void updateScreen(){
    	getFrameManager().generateFullScreen();
    }
    
    private static void startGalleryRunnable(){
    	GalleryRunnable gr = new GalleryRunnable();
    	new Timer().schedule(gr, 5000, 5000);
    	galleryrunnable = gr;
    }
    
    public static FrameManager getFrameManager(){
    	return frameManager;
    }
    public static List<String> getFileNames(){
    	return fileNames;
    }
    public static List<BufferedImage> getImages(){
    	return images;
    }
    public static BufferedImage getLastImage(){
    	return lastimage;
    }
    public static void setLastImage(BufferedImage img){
    	lastimage = img;
    }
    public static BufferedImage getNextImage(){
    	if(getLastImage() == null){
    		return getImages().get(0);
    	}
    	
    	int index = getImages().indexOf(getLastImage());
    	if(getImages().size() == index +1){
    		return getImages().get(0);
    	}
    	return getImages().get(index +1);
    }
}
