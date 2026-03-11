package om.api.runnables;

import om.api.handlers.players.OMPlayer;
import om.api.utils.WorldUtils;
import om.api.utils.enums.Cooldown;
import om.api.utils.enums.cp.Trail;
import om.api.utils.enums.cp.TrailType;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlayerDataRunnable extends OMRunnable {

	public PlayerDataRunnable(Duration duration) {
		super(duration);
	}

	@Override
	protected void run() {
		for(Player player : Bukkit.getOnlinePlayers()){
			OMPlayer omplayer = OMPlayer.getOMPlayer(player);
			
			if(omplayer.isLoaded()){
				if(omplayer != null && player.isOp() && omplayer.getStaffRank() != StaffRank.Owner && !omplayer.isOpMode()){
					player.setOp(false);
				}
				
				if(omplayer.getCooldowns().containsKey(Cooldown.TELEPORTING)){
					if(omplayer.onCooldown(Cooldown.TELEPORTING)){
						omplayer.parseTrail(Trail.FIREWORK_SPARK, TrailType.CYLINDER_TRAIL, 1, false);
					}
				}
				else{
					omplayer.playTrail();
				}
				omplayer.updateCooldownActionBar();

				Entity petentity = omplayer.getPet();
				if(petentity != null && petentity instanceof LivingEntity){
					if(petentity.getWorld().getName().equals(player.getWorld().getName())){
						LivingEntity pet = (LivingEntity) petentity;
						
						Location l = player.getLocation();
						
						if(WorldUtils.getDistance(l, pet.getLocation()) < 20){
							WorldUtils.navigate(pet, l, 1.2);
						}
						else{
							pet.teleport(l);
						}
					}
					else{
						omplayer.disablePet();
					}
				}
			}
		}
	}
}
