package fadidev.spigotbridge.handlers.plugins.factions;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.handlers.Variable;

/**
 * Created by Fadi-Laptop on 6-4-2016.
 */
public class FactionsHandler extends PluginHandler {

    private SpigotBridge sb;
    private Variable[] variables;

    public FactionsHandler(long interval, Variable[] variables) {
        sb = SpigotBridge.getInstance();

        this.variables = variables;

        this.setInterval(interval);
        this.startTask();
    }

    @Override
    public String getVariableName() {
        return "factions";
    }

    @Override
    public Variable[] getVariables() {
        return variables;
    }
}
