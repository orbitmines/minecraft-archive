package fadidev.spigotbridge.handlers.plugins.essentials.variables;

import com.earth2me.essentials.User;
import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.handlers.plugins.essentials.EssentialsHandler;
import fadidev.spigotbridge.utils.enums.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class NicknameVariable extends Variable {

    @Override
    public String getVariable() {
        return "nick";
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
        return EssentialsHandler.essentials.getUser(p).getNickname();
    }

    @Override
    public Collection<? extends Player> getPlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
