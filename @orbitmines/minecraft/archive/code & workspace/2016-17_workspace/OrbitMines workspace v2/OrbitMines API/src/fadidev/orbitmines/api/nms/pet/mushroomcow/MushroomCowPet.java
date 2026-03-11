package fadidev.orbitmines.api.nms.pet.mushroomcow;

import fadidev.orbitmines.api.utils.enums.Mob;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Created by Fadi on 30-4-2016.
 */
public interface MushroomCowPet {

    int Id = Mob.MUSHROOM_COW.getEggId();

    public Entity spawn(Location location, String displayname);

}
