package noteinclude;

import org.bukkit.Location;

public class WorldUtils {

	public static double getDistance(Location l1, Location l2){
		double distance = l1.distance(l2);
		return distance;
	}

}
