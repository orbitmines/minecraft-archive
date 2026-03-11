package fadidev.spigotbridge.handlers.plugins.vault;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.handlers.Variable;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class VaultHandler extends PluginHandler {

    private SpigotBridge sb;
    public static Economy economy = null;
    public static Chat chat = null;
    private Variable[] variables;

    public VaultHandler(long interval, Variable[] variables) {
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
