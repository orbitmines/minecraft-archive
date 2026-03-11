package fadidev.spigotbridge.handlers.plugins.cardinalpgm.variables;

import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.handlers.plugins.vault.VaultHandler;
import fadidev.spigotbridge.utils.enums.VariableType;
import in.twizmwaz.cardinal.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Fadi on 30-04-2016.
 */
public class PrefixVariable extends Variable {

    @Override
    public String getVariable() {
        return "prefix";
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
        return Rank.getPrefix(p.getUniqueId());
    }

    @Override
    public Collection<? extends Player> getPlayers() {
        return Bukkit.getOnlinePlayers();
    }
}