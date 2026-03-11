package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Direction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi-Laptop on 23-9-2016.
 */
public abstract class Podium {

    private Location location;
    private Direction direction;
    private List<Location> locations;

    public Podium(Location location, Direction direction){
        this.location = location;
        this.direction = direction;
        this.locations = new ArrayList<>();

        setup();
    }

    public abstract String[] getLines(int index, StringInt stringInt);

    public abstract List<StringInt> getStringInts();

    public Location getLocation() {
        return location;
    }

    public Direction getDirection() {
        return direction;
    }

    private void setup(){
        switch(getDirection()){
            case NORTH:
                locations.add(WorldUtils.asNewLocation(getLocation(), 0, +2, -1));
                locations.add(WorldUtils.asNewLocation(getLocation(), -1, +1, -1));
                locations.add(WorldUtils.asNewLocation(getLocation(), +1, 0, -1));
                break;
            case EAST:
                locations.add(WorldUtils.asNewLocation(getLocation(), +1, +2, 0));
                locations.add(WorldUtils.asNewLocation(getLocation(), +1, +1, -1));
                locations.add(WorldUtils.asNewLocation(getLocation(), +1, 0, +1));
                break;
            case SOUTH:
                locations.add(WorldUtils.asNewLocation(getLocation(), 0, +2, +1));
                locations.add(WorldUtils.asNewLocation(getLocation(), +1, +1, +1));
                locations.add(WorldUtils.asNewLocation(getLocation(), -1, 0, +1));
                break;
            case WEST:
                locations.add(WorldUtils.asNewLocation(getLocation(), -1, +2, 0));
                locations.add(WorldUtils.asNewLocation(getLocation(), -1, +1, +1));
                locations.add(WorldUtils.asNewLocation(getLocation(), -1, 0, -1));
                break;
        }
    }

    private Skull getSkull(Location location){
        Block b = location.getWorld().getBlockAt(getSkullLocation(location));

        if(b.getType() != Material.SKULL) {
            b.setType(Material.SKULL);
            b.setData((byte) 1);
        }

        return (Skull) b.getState();
    }

    private Location getSkullLocation(Location location){
        switch(getDirection()){
            case NORTH:
                return WorldUtils.asNewLocation(location, 0, 1, 1);
            case EAST:
                return WorldUtils.asNewLocation(location, -1, 1, 0);
            case SOUTH:
                return WorldUtils.asNewLocation(location, 0, 1, -1);
            case WEST:
                return WorldUtils.asNewLocation(location, 1, 1, 0);
            default:
                return null;
        }
    }

    public void update(){
        List<StringInt> stringInts = getStringInts();
        if(stringInts == null)
            return;

        int index = 0;
        for(Location location : locations){
            StringInt stringInt = null;
            if(getStringInts().size() > index)
                stringInt = getStringInts().get(index);

            updateSkull(getSkull(location), stringInt);
            updateSign(location, getLines(index, stringInt));

            index++;
        }
    }

    private void updateSkull(Skull skull, StringInt stringInt){
        if(stringInt == null || stringInt.getString() == null){
            skull.setOwner(null);
            skull.setSkullType(SkullType.SKELETON);
        }
        else{
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(stringInt.getString());
        }
        skull.setRotation(BlockFace.valueOf(getDirection().toString()));
        skull.update();
    }

    private void updateSign(Location location, String[] lines){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendSignChange(location, lines);
        }
    }
}
