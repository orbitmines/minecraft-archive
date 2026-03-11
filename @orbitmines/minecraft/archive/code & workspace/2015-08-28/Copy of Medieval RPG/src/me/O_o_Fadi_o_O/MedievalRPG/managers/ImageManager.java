package me.O_o_Fadi_o_O.MedievalRPG.managers;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import me.O_o_Fadi_o_O.MedievalRPG.Start;

public class ImageManager {

	private Start start;
	private Map<ImageType, BufferedImage> images;
	
	public ImageManager(){
		start = Start.getInstance();
		images = new HashMap<ImageType, BufferedImage>();
		
		for(ImageType type : ImageType.values()){
			try{
				images.put(type, ImageIO.read(Start.class.getResource("images/" + type.getPath())));
			}catch(IOException | IllegalArgumentException e){
				System.out.println("Error while loading " + Start.class.getResource("images/" + type.getPath()));
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("serial")
	public void draw(List<ImageToDraw> imagestodraw){
		FrameManager fm = start.getFrameManager();
		
    	Component c = new Component(){
    		
    		@Override
    	    public void paint(Graphics g){
    	        super.paint(g);
    	        
    	        for(ImageToDraw imgtd : imagestodraw){
    	    		BufferedImage img = images.get(imgtd.getType());
        	        g.drawImage(img, imgtd.getX(), imgtd.getY(), img.getWidth() / imgtd.getScaledBy(), img.getHeight() / imgtd.getScaledBy(), this);
    	        }
    	    }
		};
		fm.getJFrame().add(c);
	}
	
	public Map<ImageType, BufferedImage> getImages(){
		return images;
	}
	
	public static enum ImageType {
		
		CLOSE_BUTTON("Close-Button.jpg"),
		MINIMIZE_BUTTON("Minimize-Button.jpg");
		
		private String path;
		private BufferedImage img;
		
		ImageType(String path){
			this.path = path;
		}
		
		public int getWidth(){
			checkImage();
			return img.getWidth();
		}
		public int getHeight(){
			checkImage();
			return img.getHeight();
		}
		
		public int getWidth(int scaledby){
			checkImage();
			return img.getWidth() / scaledby;
		}
		public int getHeight(int scaledby){
			checkImage();
			return img.getHeight() / scaledby;
		}
		
		private void checkImage(){
			if(img == null){
				this.img = Start.getInstance().getImageManager().getImages().get(this);
			}
		}
		
		public String getPath(){
			return path;
		}
	}
	
	public static class ImageToDraw {
		
		private ImageType type;
		private int x;
		private int y;
		private int scaledby;
		
		public ImageToDraw(ImageType type, int x, int y, int scaledby){
			this.type = type;
			this.x = x;
			this.y = y;
			this.scaledby = scaledby;
		}

		public ImageType getType() {
			return type;
		}
		public void setType(ImageType type) {
			this.type = type;
		}

		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}

		public int getScaledBy() {
			return scaledby;
		}
		public void setScaledBy(int scaledby) {
			this.scaledby = scaledby;
		}
	}
}
