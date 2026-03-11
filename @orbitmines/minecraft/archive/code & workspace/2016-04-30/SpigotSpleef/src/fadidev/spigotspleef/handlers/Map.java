package fadidev.spigotspleef.handlers;

import fadidev.spigotspleef.SpigotSpleef;
import org.bukkit.Location;

import java.util.List;

/**
 * Created by Fadi on 30-4-2016.
 */
public class Map {

    private static SpigotSpleef ss;
    private int mapId;
    private String name;
    private Location specSpawn;
    private List<Location> spawns;
    private boolean used;

    public Map(int mapId, String name, Location specSpawn, List<Location> spawns, boolean used){
        this.ss = SpigotSpleef.getInstance();
        this.mapId = mapId;
        this.name = name;
        this.specSpawn = specSpawn;
        this.spawns = spawns;
        this.used = used;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getSpecSpawn() {
        return specSpawn;
    }

    public void setSpecSpawn(Location specSpawn) {
        this.specSpawn = specSpawn;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void setSpawn(int index, Location spawn) {
        this.spawns.set(index, spawn);
    }

    public void addSpawn(Location spawn){
        this.spawns.add(spawn);
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    public void teleport(List<SpleefPlayer> players){
        int index = 0;
        for(SpleefPlayer sp : players){
            if(index > (spawns.size() -1)) index = 0;
            sp.getPlayer().teleport(getSpawns().get(index));
            index++;
        }
    }

    public static Map fromId(int mapId){
        for(Map map : ss.getMaps()){
            if(map.getMapId() == mapId){
                return map;
            }
        }
        return null;
    }
}
