package fadidev.orbitmines.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Fadi on 3-9-2016.
 */
public class WorldUtils {

    /*
     * Locations & Blocks
     */

    public static boolean equalsLoc(Location l1, Location l2){
        return l1.getWorld().getName().equals(l2.getWorld().getName()) && l1.getBlockX() == l2.getBlockX() && l1.getBlockY() == l2.getBlockY() && l1.getBlockZ() == l2.getBlockZ();
    }

    public static Location asNewLocation(Location l, double xAdded, double yAdded, double zAdded){
        return new Location(l.getWorld(), l.getX() +xAdded, l.getY() +yAdded, l.getZ() +zAdded);
    }

    public static List<Location> getPaintballLocations(Location l){
        return new ArrayList<>(Arrays.asList(asNewLocation(l, 0, -1, 0), asNewLocation(l, 0, 0, 0), asNewLocation(l, 1, -1, 0), asNewLocation(l, 0, -1, 1), asNewLocation(l, -1, -1, 0), asNewLocation(l, 0, -2, 0), asNewLocation(l, 0, -1, -1), asNewLocation(l, 0, +1, 0), asNewLocation(l, 1, 0, 0), asNewLocation(l, -1, 0, 0), asNewLocation(l, 0, 0, 1), asNewLocation(l, 0, 0, -1), asNewLocation(l, 2, -1, 0), asNewLocation(l, 0, -1, 2), asNewLocation(l, -2, -1, 0), asNewLocation(l, 0, -1, -2), asNewLocation(l, 1, -1, 1), asNewLocation(l, 1, -1, -1), asNewLocation(l, -1, -1, 1), asNewLocation(l, -1, -1, -1), asNewLocation(l, 0, -3, 0), asNewLocation(l, 1, -2, 0), asNewLocation(l, -1, -2, 0), asNewLocation(l, 0, -2, 1), asNewLocation(l, 0, -2, -1)));
    }

    public static List<Block> getBlocksBetween(Location l1, Location l2){
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int bottomBlockX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());

        int topBlockY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int bottomBlockY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());

        int topBlockZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        int bottomBlockZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++){
                for(int y = bottomBlockY; y <= topBlockY; y++){
                    Block block = l1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    /*
     * Entities
     */

    public static void removeAllEntities(){
        for(World world : Bukkit.getWorlds()){
            for(Entity en : world.getEntities()){
                if(en instanceof Player)
                    continue;

                en.remove();
            }
        }
    }

    public static void removeAllEntitiesFrame(){
        for(World world : Bukkit.getWorlds()){
            for(Entity en : world.getEntities()){
                if(en instanceof Player || en instanceof ItemFrame)
                    continue;

                en.remove();
            }
        }
    }

    public static void removeEntities(World world){
        for(Entity en : world.getEntities()){
            if(en instanceof Player)
                continue;

            en.remove();
        }
    }

    public static void navigate(LivingEntity le, Location l, double v){
        try{
            Object entityLiving = ReflectionUtils.getMethod("getHandle", le.getClass(), 0).invoke(le);
            Object nav = ReflectionUtils.getMethod("getNavigation", entityLiving.getClass(), 0).invoke(entityLiving);
            ReflectionUtils.getMethod("a", nav.getClass(), 4).invoke(nav, l.getX(), l.getY(), l.getZ(), v);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
