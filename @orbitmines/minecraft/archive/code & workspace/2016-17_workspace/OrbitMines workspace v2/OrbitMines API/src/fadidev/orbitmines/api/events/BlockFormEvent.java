package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.Material;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockFormEvent implements Listener {

	private OrbitMinesAPI api;

	public BlockFormEvent(){
		this.api = OrbitMinesAPI.getApi();
	}

	@EventHandler
	public void onForm(final EntityBlockFormEvent e){
		if(e.getEntity() instanceof Snowman){
			new BukkitRunnable(){
				public void run(){
		            if(e.getBlock().getState().getType() == Material.SNOW)
		                e.getBlock().setType(Material.AIR);
				}
			}.runTaskLater(api, 100);
		}
	}
}
