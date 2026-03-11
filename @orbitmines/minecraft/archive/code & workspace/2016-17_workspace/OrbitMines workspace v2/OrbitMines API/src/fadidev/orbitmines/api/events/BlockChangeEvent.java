package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockChangeEvent implements Listener {

	private OrbitMinesAPI api;

    public BlockChangeEvent(){
        this.api = OrbitMinesAPI.getApi();
    }

	@EventHandler
	public void onChange(final EntityChangeBlockEvent e){
		Entity en = e.getEntity();
		
		if(en instanceof FallingBlock){
			FallingBlock b = (FallingBlock) en;
			
			if(b.getMaterial() == Material.WEB || b.getMaterial() == Material.FIRE){
				new BukkitRunnable(){
					public void run(){
						e.getBlock().setType(Material.AIR);
					}
				}.runTaskLater(api, b.getMaterial() == Material.WEB ? 100 : 40);
			}
			else{
				en.remove();
				e.setCancelled(true);
			}
		}
	}
}
