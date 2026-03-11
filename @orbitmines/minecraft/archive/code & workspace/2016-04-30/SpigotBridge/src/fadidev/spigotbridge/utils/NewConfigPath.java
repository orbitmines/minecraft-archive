package fadidev.spigotbridge.utils;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.utils.enums.Config;
import org.bukkit.configuration.Configuration;

/**
 * Created by Fadi on 30-4-2016.
 */
public enum NewConfigPath {

    CARDINALPGM_USE("v1.0.1", Config.CONFIG, "Plugins.Cardinal.Use", false),
    CARDINALPGM_INTERVAL("v1.0.1", Config.CONFIG, "Plugins.Cardinal.Interval", 100),
    CARDINALPGM_PREFIX("v1.0.1", Config.CONFIG, "Plugins.Cardinal.Variables.Prefix", true);

    private SpigotBridge sb;
    private String version;
    private Config cfg;
    private String path;
    private Object value;

    NewConfigPath(String version, Config cfg, String path, Object value){
        this.sb = SpigotBridge.getInstance();
        this.version = version;
        this.cfg = cfg;
        this.path = path;
        this.value = value;
    }

    public String getVersion() {
        return version;
    }

    public Config getCfg() {
        return cfg;
    }

    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }

    public void check(){
        Configuration c = sb.getConfigManager().get(getCfg());
        if(c.get(getPath()) == null){
            c.set(getPath(), getValue());
            sb.getConfigManager().save(getCfg());

            Utils.sendConsoleMSG("§e" + getVersion() + " | New config path | " + getCfg().getFileName() + " | " + getPath());
        }
    }
}
