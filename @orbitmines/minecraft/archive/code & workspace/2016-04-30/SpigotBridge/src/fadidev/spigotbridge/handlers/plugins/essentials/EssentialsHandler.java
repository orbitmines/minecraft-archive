package fadidev.spigotbridge.handlers.plugins.essentials;

import com.earth2me.essentials.IEssentials;
import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.handlers.Variable;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class EssentialsHandler extends PluginHandler {

    private SpigotBridge sb;
    public static IEssentials essentials;
    private Variable[] variables;

    public EssentialsHandler(long interval, Variable[] variables) {
        sb = SpigotBridge.getInstance();

        this.variables = variables;

        this.setInterval(interval);
        this.startTask();
    }

    @Override
    public String getVariableName() {
        return "vault";
    }

    @Override
    public Variable[] getVariables() {
        return variables;
    }
}
