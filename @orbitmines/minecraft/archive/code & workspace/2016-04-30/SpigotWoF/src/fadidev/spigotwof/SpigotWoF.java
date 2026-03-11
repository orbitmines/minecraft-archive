package fadidev.spigotwof;

import fadidev.spigotwof.handlers.NPC;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Created by Fadi-Laptop on 7-2-2016.
 */
public class SpigotWoF extends JavaPlugin {

    private static SpigotWoF instance;

    private List<NPC> npcs;

    public void onEnable(){
        instance = this;
    }

    public static SpigotWoF getInstance() {
        return instance;
    }

    public List<NPC> getNPCs() {
        return npcs;
    }
}
