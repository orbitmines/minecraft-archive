package fadidev.orbitmines.api.inventory;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class TeleporterInv extends OMInventory {
	
	public TeleporterInv(){
		setInventory(Bukkit.createInventory(null, 27, "§0§lTeleporter"));
	}
	
	protected abstract List<OMPlayer> getPlayers();
	protected abstract ItemStack getItem(OMPlayer omp);
	
	@Override
	public void open(Player player){
		player.openInventory(getInventory());

		registerLast(player);
	}

    @Override
    public void onClick(OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if (ItemUtils.isNull(item))
            return;

        Player p = omp.getPlayer();

        if(item.getType() != Material.SKULL_ITEM)
            return;

        Player player = null;
        if(item.getItemMeta().getDisplayName().split(" ").length > 1)
            player = Bukkit.getPlayer(item.getItemMeta().getDisplayName().split(" ")[1].substring(2));
        else
            player = Bukkit.getPlayer(item.getItemMeta().getDisplayName().substring(2));

        p.closeInventory();
        p.teleport(player);
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
        p.sendMessage(Messages.INV_TELEPORTING_TO.get(omp, item.getItemMeta().getDisplayName()));
    }
	
	public void update(){
		List<OMPlayer> players = getPlayers();

		if(players.size() > 45)
			setInventory(Bukkit.createInventory(null, 54, "§0§lTeleporter"));
		else if(players.size() > 36)
			setInventory(Bukkit.createInventory(null, 45, "§0§lTeleporter"));
		else if(players.size() > 27)
			setInventory(Bukkit.createInventory(null, 36, "§0§lTeleporter"));
		else if(players.size() > 18)
			setInventory(Bukkit.createInventory(null, 27, "§0§lTeleporter"));
		else if(players.size() > 9)
			setInventory(Bukkit.createInventory(null, 18, "§0§lTeleporter"));
		else
			setInventory(Bukkit.createInventory(null, 9, "§0§lTeleporter"));
		
		int index = 0;
		for(OMPlayer omplayer : players){
			if(index <= 53)
				getInventory().setItem(index, getItem(omplayer));
			
			index++;
		}
	}
}

