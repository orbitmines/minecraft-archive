package fadidev.spigotbridge.handlers.plugins.cardinalpgm;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.handlers.Variable;

/**
 * Created by Fadi-Laptop on 30-04-2016.
 */
public class CardinalPGMHandler extends PluginHandler {

    private SpigotBridge sb;
    private Variable[] variables;

    public CardinalPGMHandler(long interval, Variable[] variables) {
        sb = SpigotBridge.getInstance();

        this.variables = variables;

        this.setInterval(interval);
        this.startTask();
    }

    @Override
    public String getVariableName() {
        return "cardinal";
    }

    @Override
    public Variable[] getVariables() {
        return variables;
    }
}
