package om.fog.events;

import om.api.utils.ItemUtils;
import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.SwordLore;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {
	
	private FoG fog;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		this.fog = FoG.getInstance();
		final Player p = e.getPlayer();
		
		new FoGPlayer(p, false);
		e.setJoinMessage(null);
		
		new BukkitRunnable(){
			public void run(){
				p.teleport(fog.getSpawn());
				{
					ItemStack item = new ItemStack(Material.WOOD_SWORD, 1);
					SwordLore.DAMAGE.add(item, 4);
					//SwordLore.FIRE_DAMAGE.add(item, 2);
					SwordLore.SHIELD_BUSTER.add(item, 3);
					SwordLore.BLINDNESS.add(item, 3);
					SwordLore.STRENGTH.add(item, 2);
					p.getInventory().addItem(ItemUtils.setUnbreakable(item));
				}
				{
					ItemStack item = new ItemStack(Material.WOOD_SWORD, 1);
					SwordLore.DAMAGE.add(item, 1);
					p.getInventory().addItem(ItemUtils.setUnbreakable(item));
				}
				p.updateInventory();
			}
		}.runTaskLater(fog, 5);
	}
}
