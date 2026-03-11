package fadidev.orbitmines.api.runnables;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Fadi on 6-9-2016.
 */
public abstract class OMRunnable {

    private OrbitMinesAPI api;
    private Time time;
    private BukkitTask task;

    public OMRunnable(TimeUnit timeUnit, int amount){
        this.api = OrbitMinesAPI.getApi();
        this.time = new Time(timeUnit, amount);

        start();
    }

    public abstract void run();

    public TimeUnit getTimeUnit() {
        return time.getTimeUnit();
    }

    public int getAmount() {
        return time.getAmount();
    }

    public void cancel(){
        if(task != null && api.getRunnables().get(time.getTicks()).size() > 1){
            task.cancel();
            api.getRunnables().remove(time.getTicks());
        }

        api.getRunnables().get(time.getTicks()).remove(this);
    }

    private void start(){
        long ticks = time.getTicks();

        if(api.getRunnables().containsKey(ticks)){
            api.getRunnables().get(ticks).add(this);
            return;
        }

        api.getRunnables().put(ticks, new ArrayList<>(Collections.singletonList(this)));

        this.task = new BukkitRunnable(){
            @Override
            public void run() {
                for(OMRunnable runnable : api.getRunnables().get(time.getTicks())){
                    runnable.run();
                }
            }
        }.runTaskTimer(api, 100, time.getTicks());
    }

    public class Time {

        private TimeUnit timeUnit;
        private int amount;

        public Time(TimeUnit timeUnit, int amount){
            this.timeUnit = timeUnit;
            this.amount = amount;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public int getAmount() {
            return amount;
        }

        public long getTicks(){
            return getTimeUnit().getTicks() * getAmount();
        }

        public boolean equals(Time time){
            return getTicks() == time.getTicks();
        }
    }

    public enum TimeUnit {

        TICK(1),
        SECOND(20),
        MINUTE(1200),
        HOUR(72000);

        private long ticks;

        TimeUnit(long ticks){
            this.ticks = ticks;
        }

        public long getTicks() {
            return ticks;
        }
    }
}
