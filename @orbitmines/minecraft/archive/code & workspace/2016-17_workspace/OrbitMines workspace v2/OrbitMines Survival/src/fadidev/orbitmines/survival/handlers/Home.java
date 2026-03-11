package fadidev.orbitmines.survival.handlers;

import fadidev.orbitmines.api.handlers.Cooldowns;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Home {

	private SurvivalPlayer survivalplayer;
	private String name;
	private Location location;
	
	public Home(SurvivalPlayer survivalplayer, String name, Location location){
		this.survivalplayer = survivalplayer;
		this.name = name;
		this.location = location;
	}

	public SurvivalPlayer getSurvivalPlayer() {
		return survivalplayer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void teleport(){
		Player p = this.survivalplayer.getPlayer();
		SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(p);
		
		omp.resetCooldown(Cooldowns.TELEPORTING);
		survivalplayer.setHomeTeleporting(true);
		survivalplayer.setTeleportingTo(this);
		
		p.sendMessage("§7Teleporting to §6" + this.name + "§7...");
	}
	
	public void teleportInstantly(){
		this.survivalplayer.setBackLocation(this.survivalplayer.getPlayer().getLocation());
		this.survivalplayer.getPlayer().teleport(this.location);
	}
	
	public void delete(){
		this.survivalplayer.removeHome(this);
		
		this.survivalplayer.getPlayer().sendMessage("§7Removed your home! (§6" + this.name + "§7)");
	}
}
