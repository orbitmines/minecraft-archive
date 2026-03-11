package fadidev.orbitmines.api.handlers.npc;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Fadi on 3-9-2016.
 */
public class Hologram {

    private static OrbitMinesAPI api;
    private Location location;
    private List<ArmorStand> armorStands;
    private List<String> lines;
    private Map<String, ArmorStand> displayNames;

    public Hologram(Location location){
        api = OrbitMinesAPI.getApi();
        this.location = location;
        this.armorStands = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.displayNames = new HashMap<>();
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<ArmorStand> getArmorStands() {
        return armorStands;
    }

    public void setArmorStands(List<ArmorStand> armorStands) {
        this.armorStands = armorStands;
    }

    public Map<String, ArmorStand> getDisplayNames() {
        return displayNames;
    }

    public void setDisplayNames(Map<String, ArmorStand> displayNames) {
        this.displayNames = displayNames;
    }

    public List<String> getLines() {
        return lines;
    }

    public void addLine(String line){
        this.lines.add(line);
        this.displayNames.put(line, null);
    }

    public void setLine(int index, String line){
        if(lines.size() < index)
            return;

        String fromLine = lines.get(index -1);
        lines.set(index -1, line);
        displayNames.remove(fromLine);
        displayNames.put(line, null);
    }

    public void create(){
        create((List<Player>) null);
    }

    public void create(Player... players){
        create(Arrays.asList(players));
    }

    public void createHideFor(Player... players){
        List<Player> createfor = new ArrayList<>();
        createfor.addAll(Bukkit.getOnlinePlayers());

        for(Player player : players){
            createfor.remove(player);
        }

        create(createfor);
    }

    public void create(List<Player> players){
        if(getArmorStands().size() != 0){
            delete();
            this.armorStands.clear();
        }

        Chunk chunk = location.getChunk();
        chunk.load();
        api.getChunks().add(chunk);

        int index = 0;
        if(getLocation() == null)
            return;

        for(String displayName : lines){
            ArmorStand armorStand = api.getNms().armorstand().spawn(new Location(getLocation().getWorld(), getLocation().getX(), getLocation().getY() -(index * 0.25), getLocation().getZ()), false);
            armorStand.setCustomName(displayName);
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);

            this.displayNames.put(displayName, armorStand);
            this.armorStands.add(armorStand);

            index++;

            if(players != null){
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(!players.contains(player))
                        api.getNms().entity().destoryEntity(player, armorStand);
                }
            }
        }
    }

    public void delete(){
        for(ArmorStand as : getArmorStands()){
            as.remove();
        }
    }

    public static Hologram getHologram(ArmorStand armorStand){
        if(api == null)
            api = OrbitMinesAPI.getApi();

        for(Hologram hologram : api.getHolograms()){
            if(hologram.getArmorStands().contains(armorStand))
                return hologram;
        }
        return null;
    }
}
