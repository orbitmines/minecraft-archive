package EmelyPC.FadiDev.Screens.runnables;

import java.util.TimerTask;

import EmelyPC.FadiDev.Screens.Start;

public class GalleryRunnable extends TimerTask {

	@Override
	public void run() {
		Start.getFrameManager().generateFullScreen();
	}
}
