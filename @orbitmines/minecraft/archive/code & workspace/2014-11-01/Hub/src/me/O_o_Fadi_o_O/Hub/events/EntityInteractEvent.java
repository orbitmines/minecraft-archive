package me.O_o_Fadi_o_O.Hub.events;

import me.O_o_Fadi_o_O.Hub.managers.StorageManager;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityInteractEvent implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerEntityInteract(PlayerInteractEntityEvent e){
		
		Player p = e.getPlayer();
		Entity en = e.getRightClicked();
		ItemStack h = p.getItemInHand();
		if(StorageManager.owners.containsKey(en.getUniqueId())){
			e.setCancelled(true);
		}
		p.updateInventory();
		
		try{
			if(h != null && h.getType() == Material.SADDLE && h.getItemMeta().getDisplayName().equals("§e§nPet Ride")){
				if (en instanceof Pig || en instanceof MushroomCow || en instanceof Wolf || en instanceof Sheep || en instanceof Horse || en instanceof MagmaCube || en instanceof Slime || en instanceof Cow || en instanceof Silverfish || en instanceof Ocelot){
					if(StorageManager.ownerpets.get(p) == en.getUniqueId()){
						
						en.setPassenger(p);
					}
				}
			}
		}catch(Exception ex){
			
		}
	}
}
