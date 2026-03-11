package fadidev.spigotbridge.handlers.plugins.factions.variables;

import com.massivecraft.factions.entity.MPlayer;
import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.utils.enums.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Fadi-Laptop on 6-4-2016.
 */
public class FactionNameVariable extends Variable {

    @Override
    public String getVariable() {
        return "factionname";
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
        MPlayer mplayer = MPlayer.get(p);
        String faction = mplayer.getFaction().getName();
        if(faction == null) faction = "";

        return faction;
    }

    @Override
    public Collection<? extends Player> getPlayers() {
        return Bukkit.getOnlinePlayers();
    }
}