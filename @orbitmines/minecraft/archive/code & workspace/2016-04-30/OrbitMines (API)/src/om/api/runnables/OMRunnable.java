package om.api.runnables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import om.api.API;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class OMRunnable {

	private API api;
	private static Map<Duration, List<OMRunnable>> runnables = new HashMap<Duration, List<OMRunnable>>();
	
	private Duration duration;
	
	public OMRunnable(Duration duration){
		this.api = API.getInstance();
		this.duration = duration;
		
		if(runnables.containsKey(duration)){
			runnables.get(duration).add(this);
		}
		else{
			List<OMRunnable> runnableList = new ArrayList<OMRunnable>();
			runnableList.add(this);
			runnables.put(duration, runnableList);
			startRunnable();
		}
	}
	
	protected abstract void run();
	
	public Duration getDuration() {
		return duration;
	}
	
	private List<OMRunnable> getRunnables(Duration duration){
		return runnables.get(duration);
	}
	
	private void startRunnable(){
		new BukkitRunnable(){

			@Override
			public void run() {
				for(OMRunnable runnable : getRunnables(getDuration())){
					runnable.run();
				}
			}
			
		}.runTaskTimer(api, 100, getDuration().getPeriod());
	}
	
	public enum Duration {
		
		TENTH_SECOND(1),
		QUARTER_SECOND(5),
		HALF_SECOND(10),
		ONE_SECOND(20),
		FIVE_SECONDS(100),
		ONE_MINUTE(1200),
		TWO_MINUTES(2400),
		ONE_HOUR(72000);
		
		private long period;
		
		Duration(long period){
			this.period = period;
		}
		
		public long getPeriod() {
			return period;
		}
	}
}
