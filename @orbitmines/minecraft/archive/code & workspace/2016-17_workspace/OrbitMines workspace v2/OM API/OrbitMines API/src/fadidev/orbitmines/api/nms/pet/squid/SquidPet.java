package fadidev.orbitmines.api.nms.pet.squid;

import fadidev.orbitmines.api.utils.enums.Mob;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by Fadi on 30-4-2016.
 */
public interface SquidPet {

    int Id = Mob.SQUID.getEggId();

    public Entity spawn(Location location, String displayname);

}
