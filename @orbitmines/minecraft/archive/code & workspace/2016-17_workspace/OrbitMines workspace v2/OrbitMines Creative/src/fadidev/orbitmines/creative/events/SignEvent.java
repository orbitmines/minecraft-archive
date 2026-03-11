package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
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
        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

        if(omp.isLoaded() && omp.hasPerms(VIPRank.EMERALD_VIP)){
            e.setLine(0, ChatColor.translateAlternateColorCodes('&', e.getLine(0)));
            e.setLine(1, ChatColor.translateAlternateColorCodes('&', e.getLine(1)));
            e.setLine(2, ChatColor.translateAlternateColorCodes('&', e.getLine(2)));
            e.setLine(3, ChatColor.translateAlternateColorCodes('&', e.getLine(3)));
        }
    }
}
