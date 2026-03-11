package om.kitpvp.runnables;

import java.util.Calendar;

import om.api.runnables.OMRunnable;
import om.kitpvp.KitPvP;

public class FreeKitRunnable extends OMRunnable {

	private KitPvP kitpvp;
	
	public FreeKitRunnable(Duration duration) {
		super(duration);
		
		this.kitpvp = KitPvP.getInstance();
	}

	@Override
	protected void run() {
        if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
        	kitpvp.setFreeKitEnabled(true);
        }
        else{
        	kitpvp.setFreeKitEnabled(false);
        }
	}
}
