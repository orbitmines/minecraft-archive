package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftMob;
import org.bukkit.entity.Entity;

@Deprecated
public class NpcNms_26_1 implements NpcNms {

    @Override
    public void setNoAI(Entity entity) {
        ((CraftMob) entity).getHandle().setNoAi(true);
    }

    @Override
    public float[] handleMovement(Entity rideable, float speed, float backMultiplier, float sideMultiplier, float walkHeight) {
        LivingEntity nmsRideable = ((CraftLivingEntity) rideable).getHandle();

        nmsRideable.yRotO = nmsRideable.getYRot();
        nmsRideable.setYRot(nmsRideable.getPassengers().get(0).getYRot());
        nmsRideable.setXRot(nmsRideable.getPassengers().get(0).getXRot() * 0.5F);
        nmsRideable.setRot(nmsRideable.getYRot(), nmsRideable.getXRot());
        nmsRideable.yBodyRot = nmsRideable.getYRot();

        float sideMot = ((LivingEntity) nmsRideable.getPassengers().get(0)).xxa * 0.5F;
        float highMot = ((LivingEntity) nmsRideable.getPassengers().get(0)).yya;
        float forMot = ((LivingEntity) nmsRideable.getPassengers().get(0)).zza;

        if (forMot <= 0.0F)
            forMot *= backMultiplier;

        sideMot *= sideMultiplier;

        nmsRideable.setSpeed(speed);

        return new float[] { sideMot, highMot, forMot };
    }

    @Override
    public void handleJump(Entity rideable, float jumpHeight) {
        LivingEntity nmsRideable = ((CraftLivingEntity) rideable).getHandle();

        if (!nmsRideable.onGround())
            return;

        net.minecraft.world.entity.Entity passenger = nmsRideable.getPassengers().isEmpty() ? null : nmsRideable.getPassengers().get(0);
        if (passenger instanceof LivingEntity livingPassenger && livingPassenger.isJumping()) {
            Vec3 mot = nmsRideable.getDeltaMovement();
            nmsRideable.setDeltaMovement(mot.x, jumpHeight, mot.z);
        }
    }
}
