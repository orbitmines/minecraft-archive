package fadidev.spigotspleef;

import fadidev.spigotspleef.handlers.ActionBar;
import fadidev.spigotspleef.handlers.Database;
import fadidev.spigotspleef.handlers.MessageLoader;
import fadidev.spigotspleef.managers.ConfigManager;
import fadidev.spigotspleef.nms.Nms;
import fadidev.spigotspleef.runnables.ActionBarRunnable;
import fadidev.spigotspleef.utils.UpdateUtils;
import fadidev.spigotspleef.utils.Utils;
import fadidev.spigotspleef.utils.enums.Config;
import fadidev.spigotspleef.utils.enums.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fadi on 30-4-2016.
 */
public class SpigotSpleef extends JavaPlugin {

    private static SpigotSpleef plugin;
    private String version;

    private Nms nms;

    private ConfigManager configManager;
    private Database database;

    private Map<Player, ActionBar> currentActionbars;

    private List<fadidev.spigotspleef.handlers.Map> maps;

    public void onEnable(){
        plugin = this;
        this.version = "v2.0.0_beta";

        nms = new Nms();

        this.configManager = new ConfigManager();
        configManager.setup(Config.getCorrectOrder());

        // Settings

        //checkNewPaths();
        loadData(false);

        registerEvents();
        startRunnables();
        loadMetrics();
    }

    public void onDisable(){

    }

    public static SpigotSpleef getInstance(){
        return plugin;
    }

    public Nms getNms() {
        return nms;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Database getDb() {
        return database;
    }

    public void setDb(Database database) {
        this.database = database;
    }

    public Map<Player, ActionBar> getCurrentActionbars() {
        return currentActionbars;
    }

    public List<fadidev.spigotspleef.handlers.Map> getMaps() {
        return maps;
    }

    private void registerEvents(){

    }

    private void startRunnables(){
        new ActionBarRunnable().runTaskTimer(this, 0, 20);
    }

    public void loadData(boolean reload){
        String version = UpdateUtils.getLatestVersion();
        if(!version.equals(this.version)){
            versionMessage(version);
        }

        for(Config config : Config.getCorrectOrder()){
            loadConfig(config, reload);
        }
        loadMessages();
    }

    private void loadMessages(){
        for(Message message : Message.values()){
            message.setMSGLoader(new MessageLoader(message));
        }
    }

    public void loadConfig(Config config, boolean reload){
        FileConfiguration c = getConfigManager().get(config);
        switch(config){
            case CONFIG:
                this.currentActionbars = new HashMap<>();

                break;
        }

        this.maps = new ArrayList<>();
    }

    private void loadMetrics(){
        try{
            Metrics metrics = new Metrics(this);
            metrics.start();
        }catch(IOException ex){
            Bukkit.getLogger().warning("[SpigotSpleef] Error while connecting to mcstats.org");
        }
    }

    private void versionMessage(String version){
        Utils.sendConsoleEmpty();
        Utils.sendConsoleMSG("§eNew Version Available! (" + version + ")");
        Utils.sendConsoleMSG("§ehttp://www.spigotmc.org/resources/spigotspleef.4997/");
        Utils.sendConsoleEmpty();
    }
}
