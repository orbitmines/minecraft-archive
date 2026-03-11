package fadidev.orbitmines.api.nms.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 30-4-2016.
 */
public interface EntityNms {

    public void setInvisible(Player player, boolean bl);
    public void destoryEntity(Player player, Entity entity);
    public void setSpeed(Entity entity, double d);
    public double getSpeed(Entity entity);
    public void disguiseAsBlock(Player player, int Id, Player... players);
    public Entity disguiseAsMob(Player player, EntityType type, boolean baby, String displayName, Player... players);
    public Entity disguiseAsMob(Player player, EntityType type, boolean baby, Player... players);
    public void mountUpdate(Entity vehicle);

}
