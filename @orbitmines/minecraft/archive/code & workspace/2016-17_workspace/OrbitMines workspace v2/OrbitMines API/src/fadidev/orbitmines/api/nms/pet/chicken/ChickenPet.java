package fadidev.orbitmines.api.nms.pet.chicken;

import fadidev.orbitmines.api.utils.enums.Mob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Created by Fadi on 30-4-2016.
 */
public interface ChickenPet {

    int Id = Mob.CHICKEN.getEggId();

    public Entity spawn(Location location, String displayname);

}
