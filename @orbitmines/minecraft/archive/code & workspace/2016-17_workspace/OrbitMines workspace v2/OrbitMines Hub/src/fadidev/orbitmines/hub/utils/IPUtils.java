package fadidev.orbitmines.hub.utils;

import net.firefang.ip2c.Country;
import net.firefang.ip2c.IP2Country;
import org.bukkit.entity.Player;

import java.io.IOException;

/**
 * Created by Fadi on 3-10-2016.
 */
public class IPUtils {

    public static String getCountry(Player player){
        String ip = player.getAddress().getHostString();

        int caching1 = IP2Country.NO_CACHE;  // Straight on file, Fastest startup, slowest queries
        int caching2 = IP2Country.MEMORY_MAPPED; // Memory mapped file, fast startup, fast quries.
        int caching3 = IP2Country.MEMORY_CACHE; // load file into memory, slowerst startup, fastest queries

        Country c = null;
        try {
            IP2Country ip2c = new IP2Country(caching1);
            c = ip2c.getCountry(ip);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (c == null) {
            return null;
        }
        else {
            /*
                   c.get2cStr() -> NL
                   c.get3cStr() -> NLD
                   c.getName() -> Netherlands
             */
            return c.getName();
        }
    }
}
