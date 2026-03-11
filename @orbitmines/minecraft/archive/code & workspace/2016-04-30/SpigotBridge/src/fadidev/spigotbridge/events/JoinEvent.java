package fadidev.spigotbridge.events;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.utils.enums.VariableType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class JoinEvent implements Listener {

    private SpigotBridge sb;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        sb = SpigotBridge.getInstance();
        final Player p = e.getPlayer();

        sb.clearData(p);

        new BukkitRunnable(){
            @Override
            public void run(){
                sb.updateData(p);
            }
        }.runTaskLater(sb, 40);
    }
}
