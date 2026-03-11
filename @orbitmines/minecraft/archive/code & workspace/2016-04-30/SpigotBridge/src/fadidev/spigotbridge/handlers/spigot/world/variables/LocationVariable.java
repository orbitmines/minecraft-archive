package fadidev.spigotbridge.handlers.spigot.world.variables;

import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.utils.enums.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class LocationVariable extends Variable {

    @Override
    public String getVariable() {
        return "location";
    }

    @Override
    public VariableType getType() {
        return VariableType.PLAYERDATA;
    }

    @Override
    public String getReplacement() {
        return null;
    }

    @Override
    public String getReplacement(Player p) {
        Location l = p.getLocation();
        return l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ();
    }

    @Override
    public Collection<? extends Player> getPlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
