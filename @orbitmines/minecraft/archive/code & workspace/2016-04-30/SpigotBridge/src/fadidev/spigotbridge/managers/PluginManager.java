package fadidev.spigotbridge.managers;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.handlers.Variable;
import fadidev.spigotbridge.handlers.plugins.cardinalpgm.CardinalPGMHandler;
import fadidev.spigotbridge.handlers.plugins.essentials.EssentialsHandler;
import fadidev.spigotbridge.handlers.plugins.essentials.variables.NicknameVariable;
import fadidev.spigotbridge.handlers.plugins.factions.FactionsHandler;
import fadidev.spigotbridge.handlers.plugins.factions.variables.FactionNameVariable;
import fadidev.spigotbridge.handlers.plugins.vault.VaultHandler;
import fadidev.spigotbridge.handlers.plugins.vault.variables.BalanceVariable;
import fadidev.spigotbridge.handlers.plugins.vault.variables.PrefixVariable;
import fadidev.spigotbridge.handlers.plugins.vault.variables.SuffixVariable;
import fadidev.spigotbridge.utils.Utils;
import net.ess3.api.IEssentials;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi-Laptop on 6-4-2016.
 */
public class PluginManager {

    private SpigotBridge sb;
    private FileConfiguration c;

    public PluginManager(FileConfiguration c){
        this.sb = SpigotBridge.getInstance();
        this.c = c;
    }

    public void loadPlugins(List<PluginHandler> pluginHandlers){
        /* Vault */
        if(pluginEnabled(c, "Vault")){
            long interval = (long) c.getInt("Plugins.Vault.Interval");
            boolean usePrefix = c.getBoolean("Plugins.Vault.Variables.Prefix");
            boolean useSuffix = c.getBoolean("Plugins.Vault.Variables.Suffix");
            boolean useBalance = c.getBoolean("Plugins.Vault.Variables.Balance");

            List<Variable> variables = new ArrayList<>();
            if(usePrefix){
                if(setupChat()){
                    variables.add(new PrefixVariable());
                    Utils.successConsole("[Vault] Chat Plugin found.");
                }
                else{
                    Utils.warnConsole("[Vault] Could not find a Chat Plugin.");
                    useSuffix = false;
                }
            }
            if(useSuffix){
                variables.add(new SuffixVariable());
            }
            if(useBalance) {
                if (setupEconomy()) {
                    variables.add(new BalanceVariable());
                    Utils.successConsole("[Vault] Economy Plugin found.");
                } else {
                    Utils.warnConsole("[Vault] Could not find an Economy Plugin.");
                }
            }

            pluginHandlers.add(new VaultHandler(interval, variables.toArray(new Variable[variables.size()])));
        }

        /* Essentials */
        if(pluginEnabled(c, "Essentials")){
            long interval = (long) c.getInt("Plugins.Essentials.Interval");
            boolean useNickname = c.getBoolean("Plugins.Essentials.Variables.Nickname");

            EssentialsHandler.essentials = (IEssentials)sb.getServer().getPluginManager().getPlugin("Essentials");

            List<Variable> variables = new ArrayList<>();
            if(useNickname){
                variables.add(new NicknameVariable());
            }

            pluginHandlers.add(new EssentialsHandler(interval, variables.toArray(new Variable[variables.size()])));
        }

        /* Factions */
        if(pluginEnabled(c, "Factions")){
            long interval = (long) c.getInt("Plugins.Factions.Interval");
            boolean useFactionName = c.getBoolean("Plugins.Factions.Variables.FactionName");

            List<Variable> variables = new ArrayList<>();
            if(useFactionName){
                variables.add(new FactionNameVariable());
            }

            pluginHandlers.add(new FactionsHandler(interval, variables.toArray(new Variable[variables.size()])));
        }

        /* CardinalPGM */
        if(pluginEnabled(c, "Cardinal")){
            long interval = (long) c.getInt("Plugins.Cardinal.Interval");
            boolean usePrefix = c.getBoolean("Plugins.Cardinal.Variables.Prefix");

            List<Variable> variables = new ArrayList<>();
            if(usePrefix){
                variables.add(new fadidev.spigotbridge.handlers.plugins.cardinalpgm.variables.PrefixVariable());
            }

            pluginHandlers.add(new CardinalPGMHandler(interval, variables.toArray(new Variable[variables.size()])));
        }
    }

    private boolean pluginEnabled(FileConfiguration c, String plugin){
        boolean use = c.getBoolean("Plugins." + plugin + ".Use");
        if(use){
            if(sb.getServer().getPluginManager().isPluginEnabled(plugin)){
                Utils.sendConsoleMSG("Enabling " + plugin + " Feature...");

                return true;
            }
            else{
                Utils.warnConsole(plugin + " Plugin was not found.");
            }
        }
        else{
            Utils.sendConsoleMSG("Disabling " + plugin + " Feature...");
        }

        return false;
    }

    private boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = sb.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            VaultHandler.chat = chatProvider.getProvider();
        }

        return (VaultHandler.chat != null);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = sb.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            VaultHandler.economy = economyProvider.getProvider();
        }

        return (VaultHandler.economy != null);
    }
}
