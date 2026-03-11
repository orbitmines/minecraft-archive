package fadidev.spigotbridge.handlers.plugins.vault.variables;

import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.handlers.plugins.vault.VaultHandler;
import fadidev.spigotbridge.utils.enums.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class BalanceVariable extends Variable {

    @Override
    public String getVariable() {
        return "balance";
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
        return "" + VaultHandler.economy.getBalance(p);
    }

    @Override
    public Collection<? extends Player> getPlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
