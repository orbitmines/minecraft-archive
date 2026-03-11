package fadidev.spigotbridge.handlers.plugins.essentials.variables;

import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.handlers.plugins.essentials.EssentialsHandler;
import fadidev.spigotbridge.utils.enums.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class VanishVariable extends Variable {

    @Override
    public String getVariable() {
        return "vanish";
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
        return "" + EssentialsHandler.essentials.getUser(p).isHidden();
    }

    @Override
    public Collection<? extends Player> getPlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
