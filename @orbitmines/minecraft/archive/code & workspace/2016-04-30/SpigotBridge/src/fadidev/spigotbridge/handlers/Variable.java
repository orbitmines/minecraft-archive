package fadidev.spigotbridge.handlers;

import fadidev.spigotbridge.utils.enums.VariableType;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public abstract class Variable {

    public abstract String getVariable();
    public abstract VariableType getType();
    public abstract String getReplacement();
    public abstract String getReplacement(Player p);
    public abstract Collection<? extends Player> getPlayers();

}
