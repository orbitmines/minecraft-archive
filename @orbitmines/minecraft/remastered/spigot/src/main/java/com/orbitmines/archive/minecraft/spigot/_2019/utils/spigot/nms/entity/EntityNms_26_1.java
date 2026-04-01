package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ReflectionUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityNms_26_1 implements EntityNms {

    private static JavaPlugin plugin;

    static {
        plugin = SpigotServer.getInstance();
    }

    @Override
    public void setInvisible(Entity entity, boolean bl) {
        ((CraftEntity) entity).getHandle().setInvisible(bl);
    }

    @Override
    public void destroyEntity(Player player, Entity entity) {
        ((CraftPlayer) player).getHandle().connection.send(new ClientboundRemoveEntitiesPacket(((CraftEntity) entity).getHandle().getId()));
    }

    @Override
    public void setAttribute(Entity entity, Attribute attribute, double d) {
        AttributeInstance instance = ((CraftLivingEntity) entity).getHandle().getAttribute(getAttribute(attribute));
        instance.setBaseValue(d);
    }

    @Override
    public double getAttribute(Entity entity, Attribute attribute) {
        return ((CraftLivingEntity) entity).getHandle().getAttribute(getAttribute(attribute)).getValue();
    }

    private Holder<net.minecraft.world.entity.ai.attributes.Attribute> getAttribute(Attribute attribute) {
        switch (attribute) {
            case MAX_HEALTH:
                return Attributes.MAX_HEALTH;
            case FOLLOW_RANGE:
                return Attributes.FOLLOW_RANGE;
            case KNOCKBACK_RESISTANCE:
                return Attributes.KNOCKBACK_RESISTANCE;
            case MOVEMENT_SPEED:
                return Attributes.MOVEMENT_SPEED;
            case FLYING_SPEED:
                return Attributes.FLYING_SPEED;
            case ATTACK_DAMAGE:
                return Attributes.ATTACK_DAMAGE;
            case ATTACK_KNOCKBACK:
                return Attributes.ATTACK_KNOCKBACK;
            case ATTACK_SPEED:
                return Attributes.ATTACK_SPEED;
            case ARMOR:
                return Attributes.ARMOR;
            case ARMOR_TOUGHNESS:
                return Attributes.ARMOR_TOUGHNESS;
            case LUCK:
                return Attributes.LUCK;
            default:
                return null;
        }
    }

    @Override
    public void destroyEntitiesFor(Collection<? extends Entity> entities, Collection<? extends Player> players) {
        for (Entity entity : entities) {
            ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(((CraftEntity) entity).getHandle().getId());

            for (Player player : players) {
                ((CraftPlayer) player).getHandle().connection.send(packet);
            }
        }
    }

    @Override
    public void disguiseAsBlock(Player player, int Id, Collection<? extends Player> players) {
        //TODO BLOCK DISGUISE
    }

    @Override
    public Entity disguiseAsMob(Player player, EntityType entityType, boolean baby, String displayName, Collection<? extends Player> players) {
        //TODO MOB DISGUISE
        return null;
    }

    @Override
    public void mountUpdate(Entity vehicle) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ClientboundSetPassengersPacket packet = new ClientboundSetPassengersPacket(((CraftPlayer) vehicle).getHandle());
                ((CraftPlayer) vehicle).getHandle().connection.send(packet);
            }
        }.runTaskLater(plugin, 1);
    }

    @Override
    public void navigate(LivingEntity le, Location location, double v) {
        ((net.minecraft.world.entity.Mob) ((CraftLivingEntity) le).getHandle()).getNavigation().moveTo(location.getX(), location.getY(), location.getZ(), v);
    }

    @Override
    public int nextEntityId() {
        try {
            Field entityCount = ReflectionUtils.getDeclaredField(net.minecraft.world.entity.Entity.class, "ENTITY_COUNTER");
            AtomicInteger id = (AtomicInteger) entityCount.get(null);
            return id.incrementAndGet();
        } catch (Exception ex) {
            ex.printStackTrace();
            return (int) Math.round(Math.random() * Integer.MAX_VALUE * 0.25);
        }
    }

    @Override
    public void clearArrowsInBody(Player player) {
        ((CraftPlayer) player).getHandle().setArrowCount(0);
    }
}
