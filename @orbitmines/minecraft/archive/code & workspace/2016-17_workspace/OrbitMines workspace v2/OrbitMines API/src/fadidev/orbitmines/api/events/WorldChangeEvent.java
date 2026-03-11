package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.perks.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldChangeEvent implements Listener {

    private OrbitMinesAPI api;

    public WorldChangeEvent(){
        api = OrbitMinesAPI.getApi();
    }

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e){
		Player p = e.getPlayer();
		final OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(omp.isLoaded()){
			if(omp.isDisguised()){
				final Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
				if(omp.getDisguiseEntityType() != null){
					final EntityType type = omp.getDisguiseEntityType();
					
					new BukkitRunnable(){
						public void run(){
							omp.disguiseAsMob(type, omp.isDisguisedBaby(), players);
						}
					}.runTaskLater(api, 1);
				}
				else{
					final int id = omp.getDisguiseBlockId();
					
					new BukkitRunnable(){
						public void run(){
							omp.disguiseAsBlock(id, players);
						}
					}.runTaskLater(api, 1);
				}
			}
			
			if(omp.hasPetEnabled()){
				final Pet pet = omp.getPetEnabled();
				
				omp.disablePet();
					
				new BukkitRunnable(){
					public void run(){
						omp.spawnPet(pet);
					}
				}.runTaskLater(api, 1);
			}
		}
	}
}
