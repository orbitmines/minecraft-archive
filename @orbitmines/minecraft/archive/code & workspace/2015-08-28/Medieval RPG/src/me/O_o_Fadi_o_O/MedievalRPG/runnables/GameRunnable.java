package me.O_o_Fadi_o_O.MedievalRPG.runnables;

import me.O_o_Fadi_o_O.MedievalRPG.Start;
import me.O_o_Fadi_o_O.MedievalRPG.managers.PixelManager;

public class GameRunnable implements Runnable {

	private Start start = Start.getInstance();
	private PixelManager pmanager = start.getPixelManager();
	
	public void run(){
		long last = System.nanoTime();
		double pertick = 1000000000 / 60D;
		int ticks = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		double d = 0;
		
		pmanager.init();
		
		while(pmanager.canRun()){
			long now = System.nanoTime();
			d += (now - last) / pertick;
			last = now;
			boolean render = true;
			
			while(d >= 1){
				ticks++;
				pmanager.tick();
				d -= 1;
				render = true;
			}
			
			try{
				Thread.sleep(2);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
			
			if(render){
				frames++;
				pmanager.render();
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				timer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}
}
