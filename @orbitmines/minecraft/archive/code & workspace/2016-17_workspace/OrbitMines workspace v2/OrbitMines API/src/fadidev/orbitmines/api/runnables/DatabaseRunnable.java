package fadidev.orbitmines.api.runnables;

import fadidev.orbitmines.api.handlers.Database;
import org.bukkit.scheduler.BukkitRunnable;

public class DatabaseRunnable extends BukkitRunnable {
	
	@Override
	public void run(){
		Database.get().openConnection();
	}
}
