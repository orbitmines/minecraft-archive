package fadidev.spigotbridge.managers;

import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.handlers.spigot.world.WorldHandler;
import fadidev.spigotbridge.handlers.spigot.world.variables.LocationVariable;
import fadidev.spigotbridge.handlers.spigot.world.variables.WorldNameVariable;
import fadidev.spigotbridge.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi-Laptop on 6-4-2016.
 */
public class WorldManager {

    private FileConfiguration c;

    public WorldManager(FileConfiguration c){
        this.c = c;
    }

    public void loadWorld(List<PluginHandler> pluginHandlers){
        /* World */
        boolean useWorld = c.getBoolean("Spigot.World.Use");
        if(useWorld){
            Utils.sendConsoleMSG("Enabling World Feature...");

            long interval = (long) c.getInt("Spigot.World.Interval");
            boolean useLocation = c.getBoolean("Spigot.World.Variables.Location");
            boolean useWorldName = c.getBoolean("Spigot.World.Variables.WorldName");

            List<Variable> variables = new ArrayList<>();
            if(useLocation){
                variables.add(new LocationVariable());
            }
            if(useWorldName){
                variables.add(new WorldNameVariable());
            }

            pluginHandlers.add(new WorldHandler(interval, variables.toArray(new Variable[variables.size()])));
        }
        else{
            Utils.sendConsoleMSG("Disabling World Feature...");
        }
    }

}
