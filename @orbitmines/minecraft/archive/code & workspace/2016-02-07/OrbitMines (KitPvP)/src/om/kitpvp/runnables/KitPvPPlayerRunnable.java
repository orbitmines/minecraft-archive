package om.kitpvp.runnables;

import om.api.runnables.PlayerRunnable;
import om.api.utils.ItemUtils;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.utils.enums.KitPvPKit;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitPvPPlayerRunnable extends PlayerRunnable {

	private KitPvP kitpvp;
	
	public KitPvPPlayerRunnable(Duration duration) {
		super(duration);

		this.kitpvp = KitPvP.getInstance();
	}

	@Override
	protected void run(Player p) {
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		if(!p.getWorld().getName().equals(kitpvp.getLobbyWorld().getName())){
			if(!omp.isOpMode() && omp.getKitSelected() == null && omp.isPlayer()){
				p.teleport(kitpvp.getSpawn());
			}
			
			if(omp.isSpectator()){
				if(p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getName() != null && p.getOpenInventory().getTopInventory().getName().equals(kitpvp.getTeleporterInv().getInventory().getName())){
					kitpvp.getTeleporterInv().open(p);
				}
				
				p.setGameMode(GameMode.SPECTATOR);
			}
			else{
				KitPvPKit kit = omp.getKitSelected();
				if(kit != null && kit.getNextArrow() != 0){
					int arrows = 0;
					for(ItemStack i : p.getInventory().getContents()){
						if(i != null && i.getType() == Material.ARROW){
							arrows++;
						}
					}
					
					if(arrows != kit.getMaxArrows(omp.getKitLevelSelected()) && p.getInventory().contains(Material.BOW)){
						if(omp.getArrowSeconds() == -1){
							omp.setArrowSeconds(kit.getNextArrow());
						}
						
						omp.tickArrowTimer();
						if(omp.getArrowSeconds() == 0){
							p.getInventory().addItem(ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 1), "§b§l" + kit.getName() + " §a§lLvL " + omp.getKitLevelSelected() + "§8 || §bArrow"));
							
							omp.setArrowSeconds(kit.getNextArrow());
						}
					}
				}
				
				if(!omp.isOpMode()){
					p.setGameMode(GameMode.SURVIVAL);
				}
			}
		}
		else{
			if(p.getLocation().getY() <= 50 || p.getLocation().distance(kitpvp.getSpawn()) >= 50){
				p.teleport(kitpvp.getSpawn());
				
				if(!omp.isOpMode()){
					p.setGameMode(GameMode.SURVIVAL);
				}
			}
		}
	}
}
