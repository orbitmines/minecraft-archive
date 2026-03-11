package fadidev.spigotspleef.nms.npc.bat;

import fadidev.spigotspleef.SpigotSpleef;
import fadidev.spigotspleef.nms.npc.NpcNms_1_8_R1;
import net.minecraft.server.v1_9_R1.EntityBat;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_9_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftBat;
import org.bukkit.entity.Entity;

import java.util.List;

/**
 * Created by Fadi on 30-4-2016.
 */
public class BatNpc_1_9_R1 implements BatNpc {

    public BatNpc_1_9_R1(){
        SpigotSpleef.getInstance().getNms().npc().addCustomEntity(BatNpc_1_9_R1.CustomBat.class, "CustomBat", 65);
    }

    @Override
    public Entity spawn(org.bukkit.World w, Location location, String displayName) {
        World nmsWorld = ((CraftWorld) w).getHandle();
        CustomBat e = new CustomBat(nmsWorld);
        e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        nmsWorld.addEntity(e);
        e.setCustomName(displayName);
        e.setCustomNameVisible(true);
        ((CraftBat) e.getBukkitEntity()).setRemoveWhenFarAway(false);

        return e.getBukkitEntity();
    }

    public class CustomBat extends EntityBat {

        public CustomBat(World world) {
            super(world);

            List goalB = (List) NpcNms_1_8_R1.getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector); goalB.clear();
            List goalC = (List) NpcNms_1_8_R1.getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector); goalC.clear();
            List targetB = (List) NpcNms_1_8_R1.getPrivateField("b", PathfinderGoalSelector.class, this.targetSelector); targetB.clear();
            List targetC = (List) NpcNms_1_8_R1.getPrivateField("c", PathfinderGoalSelector.class, this.targetSelector); targetC.clear();
        }

        @Override
        public void g(double x, double y, double z){}

        @Override
        public void m(){
            super.m();
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0D);
            this.getAttributeInstance(GenericAttributes.maxHealth).setValue(Double.MAX_VALUE);
            this.getAttributeInstance(GenericAttributes.c).setValue(Double.MAX_VALUE);
        }
    }
}
