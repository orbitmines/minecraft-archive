package fadidev.spigotbridge;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fadidev.spigotbridge.cmd.ReloadCommand;
import fadidev.spigotbridge.events.JoinEvent;
import fadidev.spigotbridge.handlers.PluginHandler;
import fadidev.spigotbridge.managers.ConfigManager;
import fadidev.spigotbridge.managers.PluginManager;
import fadidev.spigotbridge.managers.WorldManager;
import fadidev.spigotbridge.utils.NewConfigPath;
import fadidev.spigotbridge.utils.UpdateUtils;
import fadidev.spigotbridge.utils.Utils;
import fadidev.spigotbridge.utils.enums.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class SpigotBridge extends JavaPlugin {

    private static SpigotBridge instance;
    private String version;

    private ConfigManager configManager;
    private List<PluginHandler> pluginHandlers;
    private String serverName;

    public void onEnable(){
        instance = this;
        this.version = "v1.0.1";

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "SpigotBridge");

        this.configManager = new ConfigManager();
        this.configManager.setup(Config.values());

        checkNewPaths();
        loadData();
        registerCommands();
        registerEvents();
    }

    public static SpigotBridge getInstance() {
        return instance;
    }

    public String getVersion() {
        return version;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public List<PluginHandler> getPluginHandlers() {
        return pluginHandlers;
    }

    public String getServerName() {
        return serverName;
    }

    private void registerCommands(){
        getCommand("sbreload").setExecutor(new ReloadCommand());
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
    }

    public void checkNewPaths(){
        for(NewConfigPath newpath : NewConfigPath.values()){
            newpath.check();
        }
    }

    public void loadData(){
        String version = UpdateUtils.getLatestVersion();
        if(!this.version.equals(version)){
            versionMessage(version);
        }

        for(Config config : Config.getCorrectOrder()){
            loadConfig(config);
        }
    }

    public void clearData(){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(getVersion());
        out.writeUTF(getServerName());
        out.writeUTF("CLEAR");
        out.writeUTF("done");

        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if(p != null) {
            p.sendPluginMessage(this, "SpigotBridge", out.toByteArray());
        }
    }

    public void clearData(Player p){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(getVersion());
        out.writeUTF(getServerName());
        out.writeUTF("CLEAR");
        out.writeUTF(p.getName());

        p.sendPluginMessage(this, "SpigotBridge", out.toByteArray());
    }

    public void cancelTasks(){
        for(PluginHandler pluginHandler : getPluginHandlers()){
            pluginHandler.stopTask();
        }
    }

    public void updateData(){
        for(PluginHandler pluginHandler : getPluginHandlers()) {
            pluginHandler.updateAll();
        }
    }

    public void updateData(Player p){
        Utils.sendConsoleMSG("Updating " + p.getName() + "'s Data...");
        for(PluginHandler pluginHandler : getPluginHandlers()){
            pluginHandler.updateAll(p);
        }
    }

    public void registerPluginHandler(PluginHandler pluginHandler){
        this.pluginHandlers.add(pluginHandler);
    }

    private void loadConfig(Config config){
        FileConfiguration c = getConfigManager().get(config);
        switch(config){
            case CONFIG:
                pluginHandlers = new ArrayList<>();

                this.serverName = c.getString("server");

                new WorldManager(c).loadWorld(this.pluginHandlers);
                new PluginManager(c).loadPlugins(this.pluginHandlers);

                break;
        }
    }

    private void versionMessage(String version){
        Utils.sendConsoleEmpty();
        Utils.sendConsoleMSG("§eNew Version Available! (" + version + ")");
        Utils.sendConsoleMSG("§ehttp://www.spigotmc.org/resources/spigotbridge.18145/");
        Utils.sendConsoleEmpty();
    }
}