package fadidev.orbitmines.api.nms.particles;

import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Fadi on 11-5-2016.
 */
public class ParticleNms_1_8_R1 implements ParticleNms {

    @Override
    public void send(Player player, Particle particle, float x, float y, float z, float xSize, float ySize, float zSize, float special, int amount) {
        try{
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(PacketPlayOutWorldParticles.class.getConstructor(EnumParticle.class, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class).newInstance(EnumParticle.valueOf(particle.toString()), true, x, y, z, xSize, ySize, zSize, special, amount, null));
        }catch(InstantiationException | IllegalAccessException| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){}
    }
}
