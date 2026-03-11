package fadidev.spigotbridge.handlers.spigot.world;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.handlers.Variable;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class WorldHandler extends PluginHandler {

    private SpigotBridge sb;
    private Variable[] variables;

    public WorldHandler(long interval, Variable[] variables) {
        sb = SpigotBridge.getInstance();

        this.variables = variables;

        this.setInterval(interval);
        this.startTask();
    }

    @Override
    public String getVariableName() {
        return "spigot";
    }

    @Override
    public Variable[] getVariables() {
        return variables;
    }
}
