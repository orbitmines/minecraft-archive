package fadidev.spigotspleef.nms.npc.bat;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by Fadi on 30-4-2016.
 */
public interface BatNpc {

    public Entity spawn(World w, Location location, String displayname);

}
