package addtohub;

import om.api.utils.enums.cp.ChatColor;
import om.api.utils.enums.cp.*;
import om.api.utils.enums.ranks.StaffRank;
import om.api.utils.enums.ranks.VIPRank;
import om.api.utils.others.ComponentMessage;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import static com.sun.xml.internal.ws.util.JAXWSUtils.getUUID;

public abstract class OMPlayer {


	public void updateTracker(List<OMPlayer> players){
		List<OMPlayer> newplayers = new ArrayList<OMPlayer>();
		newplayers.addAll(players);
		newplayers.remove(this);

		if(newplayers.size() != 0){
			double distance = 100000;
			OMPlayer nearest = null;

			for(OMPlayer omp : newplayers){
				Player p = omp.getPlayer();

				if(getPlayer().getWorld().getName().equals(p.getWorld().getName())){
					double pdistnace = getPlayer().getLocation().distance(p.getLocation());
					if(pdistnace <= distance){
						distance = pdistnace;
						nearest = omp;
					}
				}
			}

			if(nearest != null){
				for(ItemStack item : getPlayer().getInventory().getContents()){
					if(item != null && item.getType() == Material.COMPASS){
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName("§6§lTracking: §f§l" + nearest.getPlayer().getName() + " §6§lDistance: §f§l" + String.format("%.1f", distance));
						item.setItemMeta(meta);
					}
				}
				getPlayer().setCompassTarget(nearest.getPlayer().getLocation());
			}
		}
	}


	

	


	

}
