package om.kitpvp.invs;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;
import om.api.invs.others.TeleporterInv;
import om.api.utils.ItemUtils;
import om.kitpvp.handlers.players.KitPvPPlayer;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitPvPTeleporterInv extends TeleporterInv {

	@Override
	protected ItemStack getItem(OMPlayer omp) {
		KitPvPPlayer kp = KitPvPPlayer.getKitPvPPlayer(omp.getPlayer());
		
		ItemStack item = ItemUtils.getSkull(omp.getPlayer().getName());
		ItemMeta itemmeta = (ItemMeta) item.getItemMeta();
		itemmeta.setDisplayName(omp.getName());
		List<String> itemlore = new ArrayList<String>();
		itemlore.add("");
		itemlore.add("§7Kit: " + kp.getKitSelected().getSelectedKitName(kp.getKitLevelSelected()));
		itemlore.add("§cHealth: §f" + String.format("%.1f", ((CraftPlayer) omp.getPlayer()).getHealth() / 2).replaceAll(",", ".") + "/10.0");
		itemlore.add("§6Food: §f" + String.format("%.1f", (double) omp.getPlayer().getFoodLevel() / 2).replaceAll(",", ".") + "/10.0");
		itemlore.add("§9Current Streak: §f" + kp.getCurrentStreak());
		itemlore.add("");
		itemlore.add("§c§lKitPvP Stats:");
		itemlore.add("§cKills: §f" + kp.getKills());
		itemlore.add("§4Deaths: §f" + kp.getDeaths());
		itemlore.add("§eLevel: §f" + kp.getLevels());
		itemlore.add("§bBest Streak: §f" + kp.getBestStreak());
		itemlore.add("");
		itemlore.add("§e§lClick Here to Teleport");
		itemlore.add("");
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		
		return item;
	}

	@Override
	protected List<OMPlayer> getPlayers() {
		List<OMPlayer> players = new ArrayList<OMPlayer>();
		for(Player p : Bukkit.getOnlinePlayers()){
			KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
			
			if(omp.isPlayer() && omp.getKitSelected() != null){
				players.add(omp);
			}
		}
		return players;
	}
}
