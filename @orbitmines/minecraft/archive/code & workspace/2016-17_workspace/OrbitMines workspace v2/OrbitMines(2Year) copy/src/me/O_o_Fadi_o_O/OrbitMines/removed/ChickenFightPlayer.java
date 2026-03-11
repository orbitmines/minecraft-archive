package me.O_o_Fadi_o_O.OrbitMines.removed;

import me.O_o_Fadi_o_O.OrbitMines.removed.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils.TicketType;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class ChickenFightPlayer {

	public Hologram getNameHologram() {
		return namehologram;
	}
	public void setNameHologram(Hologram namehologram) {
		this.namehologram = namehologram;
	}
	public void updateHologram(){
		OMPlayer omp = OMPlayer.getOMPlayer(getPlayer());
		Arena arena = omp.getArena();
		
		if(arena != null){
			Location l = getPlayer().getLocation();
			if(getKit().getKitName().equals(TicketType.CHICKEN_MAMA_KIT.toString()) || getKit().getKitName().equals(TicketType.HOT_WING_KIT.toString())){
				l = new Location(l.getWorld(), l.getX(), l.getY() -1.25, l.getZ());
			}
			else if(getKit().getKitName().equals(TicketType.BABY_CHICKEN_KIT.toString())){
				l = new Location(l.getWorld(), l.getX(), l.getY() -1.5, l.getZ());
			}
			else if(getKit().getKitName().equals(TicketType.CHICKEN_WARLORD_KIT.toString())){
				l = new Location(l.getWorld(), l.getX(), l.getY() +0.5, l.getZ());
			}
			else if(getKit().getKitName().equals(TicketType.CHICKEN_KIT.toString())){
				l = new Location(l.getWorld(), l.getX(), l.getY() -0.75, l.getZ());
			}
			else{}
			
			if(getNameHologram() != null){
				if(arena.isSpectator(omp)){
					getNameHologram().delete();
					setNameHologram(null);
				}
				else{
					getNameHologram().setLocation(getPlayer().getLocation());
					for(ArmorStand as : getNameHologram().getArmorstands()){
						as.teleport(l);
					}
				}
			}
			else{
				if(!arena.isSpectator(omp)){
					Hologram h = Hologram.addHologram(l);
					h.addLine("§a" + getPlayer().getName());
					h.createHideFor(getPlayer());
					
					setNameHologram(h);
				}
			}
		}
	}
}
