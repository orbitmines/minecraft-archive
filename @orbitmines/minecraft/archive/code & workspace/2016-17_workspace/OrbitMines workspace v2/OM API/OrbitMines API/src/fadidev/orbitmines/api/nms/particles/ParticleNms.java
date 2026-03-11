package fadidev.orbitmines.api.nms.particles;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 30-4-2016.
 */
public interface ParticleNms {

    public void send(Player player, Particle particle, float x, float y, float z, float xSize, float ySize, float zSize, float special, int amount);

}
