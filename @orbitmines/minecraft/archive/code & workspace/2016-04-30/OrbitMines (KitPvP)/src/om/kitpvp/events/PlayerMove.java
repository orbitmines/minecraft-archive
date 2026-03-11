package om.kitpvp.events;

import java.util.List;

import om.api.utils.enums.Cooldown;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.utils.enums.ArmorType;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PlayerMove implements Listener {
	
	private KitPvP kitpvp;
	
	@EventHandler
    public void onMove(final PlayerMoveEvent e) {
    	this.kitpvp = KitPvP.getInstance();
    	Player p = e.getPlayer();
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		if(omp.isLoaded()){
			omp.checkLastLocation();
			
			if(omp.isAFK()){
				omp.noLongerAFK();
			}
			
			if(omp.getKitSelected() != null || omp.isSpectator()){
				if(p.getLocation().getY() >= kitpvp.getCurrentMap().getMaxY()){
					p.setVelocity(new Vector(0, 0, 0));
				    Location l = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() -2, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
				    p.teleport(l);
				    p.sendMessage("§7§lStay in the Arena!");
				    p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
				}
			}
				
			// Play Armor Enchantments \\
			for(ItemStack item : p.getInventory().getArmorContents()){
				if(item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null){
					List<String> itemlore = item.getItemMeta().getLore();
					
					for(ArmorType type : ArmorType.getArmorTypes(omp.getKitSelected())){
						if((type == ArmorType.FIRE_TRAIL_I || type == ArmorType.LIGHT_I) && itemlore.contains(ArmorType.FIRE_TRAIL_I.getName())){
							type.playEnchantment(omp, null);
						}
					}
				}
			}
			
			// Paintballs I \\
			Block b = p.getWorld().getBlockAt(p.getLocation().subtract(0, 1, 0));
			if(kitpvp.getPaintballBlocks().containsKey(b)){
				if(!omp.onCooldown(Cooldown.PAINTBALLS_I_USAGE)){
					int color = kitpvp.getPaintballBlocks().get(b);
					Player pD = kitpvp.getPaintballBlockPlayers().get(b);
					if(color == 14){
						if(pD != null){
							p.damage(10, pD);
						}
						else{
							p.damage(10);
						}
					}
					else if(color == 3){
						omp.addPotionEffect(PotionEffectType.SPEED, 5, 1);
					}
					else if(color == 15){
						omp.addPotionEffect(PotionEffectType.BLINDNESS, 5, 0);
					}
					else if(color == 8){
						omp.addPotionEffect(PotionEffectType.SLOW, 5, 4);
					}
					else if(color == 5){
						if(p.getHealth() + 2 > p.getMaxHealth()){
							p.setHealth(p.getMaxHealth());
						}
						else{
							p.setHealth(p.getHealth() +2);
						}
					}
					else{}
					
					omp.resetCooldown(Cooldown.PAINTBALLS_I_USAGE);
				}
			}
		}
    }
}
