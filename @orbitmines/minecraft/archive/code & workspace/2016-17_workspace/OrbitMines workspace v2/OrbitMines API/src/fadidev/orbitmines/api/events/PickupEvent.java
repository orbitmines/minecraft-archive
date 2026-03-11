package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PickupEvent implements Listener {

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e){
		Item item = e.getItem();
		ItemStack i = item.getItemStack();
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);

        if(i.getItemMeta() == null || i.getItemMeta().getDisplayName() == null)
            return;

		if(i.getType() == Material.PAPER && i.getItemMeta().getDisplayName().startsWith("Paper ") && omp.canReceiveVelocity()){
			e.setCancelled(true);

			p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);

			p.removePotionEffect(PotionEffectType.SPEED);
			p.removePotionEffect(PotionEffectType.JUMP);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 4));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 5, 4));

			item.remove();
		}
		else if(i.getType() == Material.BONE && i.getItemMeta().getDisplayName().startsWith("Bark ")){
            e.setCancelled(true);
        }
	}
}
