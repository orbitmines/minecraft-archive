package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SignEvent implements Listener {

    @EventHandler
    public void onSign(SignChangeEvent e){
        Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);

        if(omp.isLoaded() && omp.hasPerms(StaffRank.BUILDER) && omp.inBuilderWorld()){
            e.setLine(0, ChatColor.translateAlternateColorCodes('&', e.getLine(0)));
            e.setLine(1, ChatColor.translateAlternateColorCodes('&', e.getLine(1)));
            e.setLine(2, ChatColor.translateAlternateColorCodes('&', e.getLine(2)));
            e.setLine(3, ChatColor.translateAlternateColorCodes('&', e.getLine(3)));
        }
    }
}
