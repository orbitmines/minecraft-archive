package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.zombie_villager;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.NpcNms;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.bukkit.event.entity.CreatureSpawnEvent;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftVillagerZombie;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class ZombieVillagerNpc_26_1 implements ZombieVillagerNpc {

    private final NpcNms npcNms;

    public ZombieVillagerNpc_26_1(NpcNms npcNms) {
        this.npcNms = npcNms;
    }

    @Override
    public Entity spawn(Location location, Collection<Option> options) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        CustomZombieVillagerNpc entity = new CustomZombieVillagerNpc(level, options, npcNms);

        entity.absSnapTo(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        level.addFreshEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);

        CraftEntity craftEntity = entity.getBukkitEntity();
        ((CraftVillagerZombie) craftEntity).setRemoveWhenFarAway(false);

        if (options.contains(Option.DISABLE_MOVEMENT))
            npcNms.setNoAI(craftEntity);

        if (options.contains(Option.DISABLE_GRAVITY))
            entity.setNoGravity(true);

        return craftEntity;
    }

    private static class CustomZombieVillagerNpc extends ZombieVillager {

        private final Collection<Option> options;
        private final NpcNms npcNms;

        CustomZombieVillagerNpc(Level level) {
            this(level, new ArrayList<>(), null);
        }

        CustomZombieVillagerNpc(Level level, Collection<Option> options, NpcNms npcNms) {
            super(EntityType.ZOMBIE_VILLAGER, level);
            this.options = options;
            this.npcNms = npcNms;
        }

        @Override
        public void push(double x, double y, double z) {
            if (!options.contains(Option.DISABLE_COLLISION))
                super.push(x, y, z);
        }

        @Override
        public void tick() {
            super.tick();
            if (!options.contains(Option.DISABLE_MOVEMENT))
                return;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
            this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(Double.MAX_VALUE);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(Double.MAX_VALUE);
        }

        @Override
        public void travel(Vec3 movementInput) {
            if (!options.contains(Option.RIDEABLE) || this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof net.minecraft.world.entity.player.Player)) {
                super.travel(movementInput);
                return;
            }
            CraftEntity entity = getBukkitEntity();
            float[] f = npcNms.handleMovement(entity, 0.25F, 0.25F, 0.5F, 0.5F);
            super.travel(new Vec3(f[0], f[1], f[2]));
            npcNms.handleJump(entity, 0.5F);
        }
    }
}
