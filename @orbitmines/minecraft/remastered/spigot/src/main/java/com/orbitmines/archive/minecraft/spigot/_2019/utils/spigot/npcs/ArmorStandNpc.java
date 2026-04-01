package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.*;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public class ArmorStandNpc<P extends SpigotPlayer> extends Npc<ArmorStandNpc, P> {

    @Getter private static Map<World, List<ArmorStandNpc>> armorStandNpcs = new HashMap<>();

    protected ArmorStand armorStand;

    protected boolean arms;
    protected boolean basePlate;
    @Getter protected EulerAngle bodyPose;
    @Getter protected EulerAngle headPose;
    @Getter protected EulerAngle leftArmPose;
    @Getter protected EulerAngle leftLegPose;
    @Getter protected EulerAngle rightArmPose;
    @Getter protected EulerAngle rightLegPose;
    @Getter protected ItemStack itemInHand;
    @Getter protected ItemStack helmet;
    @Getter protected ItemStack chestPlate;
    @Getter protected ItemStack leggings;
    @Getter protected ItemStack boots;
    @Getter protected String customName;
    @Getter protected boolean customNameVisible;
    protected boolean gravity;
    @Getter protected boolean small;
    @Getter protected boolean visible;
    protected boolean marker;

    public ArmorStandNpc(Location spawnLocation) {
        super(spawnLocation);

        this.arms = false;
        this.basePlate = false;
        this.bodyPose = EulerAngle.ZERO;
        this.headPose = EulerAngle.ZERO;
        this.leftArmPose = EulerAngle.ZERO;
        this.leftLegPose = EulerAngle.ZERO;
        this.rightArmPose = EulerAngle.ZERO;
        this.rightLegPose = EulerAngle.ZERO;
        this.customNameVisible = false;
        this.gravity = true;
        this.small = false;
        this.visible = true;
        this.marker = false;
    }

    @Override
    protected void spawn() {
        armorStand = (ArmorStand) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ARMOR_STAND);
        armorStand.setRemoveWhenFarAway(false);

        armorStand.setArms(arms);
        armorStand.setBasePlate(basePlate);
        armorStand.setBodyPose(bodyPose);
        armorStand.setBoots(boots);
        armorStand.setChestplate(chestPlate);
        armorStand.setGravity(gravity);
        armorStand.setHeadPose(headPose);
        armorStand.setHelmet(helmet);
        armorStand.setItemInHand(itemInHand);
        armorStand.setLeftArmPose(leftArmPose);
        armorStand.setLeftLegPose(leftLegPose);
        armorStand.setLeggings(leggings);
        armorStand.setRightArmPose(rightArmPose);
        armorStand.setRightLegPose(rightLegPose);
        armorStand.setSmall(small);
        armorStand.setVisible(visible);
        armorStand.setMarker(marker);

        armorStand.setCustomName(customName);
        armorStand.setCustomNameVisible(customNameVisible);
    }

    @Override
    protected void despawn() {
        if (armorStand != null)
            armorStand.remove();
    }

    @Override
    public void update() {
        if (armorStand == null)
            return;

        armorStand.setRemoveWhenFarAway(false);

        armorStand.setCustomName(customName);
        armorStand.setCustomNameVisible(customNameVisible);
    }

    @Override
    public Collection<Entity> getEntities() {
        return Collections.singletonList(armorStand);
    }

    @Override
    protected Map<World, List<ArmorStandNpc>> getMapping() {
        return armorStandNpcs;
    }

    @Override
    protected ArmorStandNpc getInstance() {
        return this;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public boolean hasArms() {
        return arms;
    }

    public void setArms(boolean arms) {
        this.arms = arms;

        if (this.armorStand != null)
            this.armorStand.setArms(arms);
    }

    public boolean hasBasePlate() {
        return basePlate;
    }

    public void setBasePlate(boolean basePlate) {
        this.basePlate = basePlate;

        if (this.armorStand != null)
            this.armorStand.setBasePlate(basePlate);
    }

    public void setBodyPose(EulerAngle bodyPose) {
        this.bodyPose = bodyPose;

        if (this.armorStand != null)
            this.armorStand.setBodyPose(bodyPose);
    }

    public void setHeadPose(EulerAngle headPose) {
        this.headPose = headPose;

        if (this.armorStand != null)
            this.armorStand.setHeadPose(headPose);
    }

    public void setLeftArmPose(EulerAngle leftArmPose) {
        this.leftArmPose = leftArmPose;

        if (this.armorStand != null)
            this.armorStand.setLeftArmPose(leftArmPose);
    }

    public void setLeftLegPose(EulerAngle leftLegPose) {
        this.leftLegPose = leftLegPose;

        if (this.armorStand != null)
            this.armorStand.setLeftLegPose(leftLegPose);
    }

    public void setRightArmPose(EulerAngle rightArmPose) {
        this.rightArmPose = rightArmPose;

        if (this.armorStand != null)
            this.armorStand.setRightArmPose(rightArmPose);
    }

    public void setRightLegPose(EulerAngle rightLegPose) {
        this.rightLegPose = rightLegPose;

        if (this.armorStand != null)
            this.armorStand.setRightLegPose(rightLegPose);
    }

    public void setItemInHand(ItemStack itemInHand) {
        this.itemInHand = itemInHand;

        if (this.armorStand != null)
            this.armorStand.setItemInHand(itemInHand);
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;

        if (this.armorStand != null)
            this.armorStand.setHelmet(helmet);
    }

    public void setChestPlate(ItemStack chestPlate) {
        this.chestPlate = chestPlate;

        if (this.armorStand != null)
            this.armorStand.setChestplate(chestPlate);
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;

        if (this.armorStand != null)
            this.armorStand.setLeggings(leggings);
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;

        if (this.armorStand != null)
            this.armorStand.setBoots(boots);
    }

    public void setCustomName(String customName) {
        this.customName = customName;

        if (this.armorStand != null)
            this.armorStand.setCustomName(customName);
    }

    public void setCustomNameVisible(boolean customNameVisible) {
        this.customNameVisible = customNameVisible;

        if (this.armorStand != null)
            this.armorStand.setCustomNameVisible(customNameVisible);
    }

    public boolean isGravity() {
        return gravity;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;

        if (this.armorStand != null)
            this.armorStand.setGravity(gravity);
    }

    public void setSmall(boolean small) {
        this.small = small;

        if (this.armorStand != null)
            this.armorStand.setSmall(small);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;

        if (this.armorStand != null)
            this.armorStand.setVisible(visible);
    }

    public boolean hasMarker() {
        return marker;
    }

    public void setMarker(boolean marker) {
        this.marker = marker;

        if (this.armorStand != null)
            this.armorStand.setMarker(marker);
    }

    public static ArmorStandNpc getNpc(Entity entity) {
        if (!armorStandNpcs.containsKey(entity.getWorld()))
            return null;

        for (ArmorStandNpc npc : getArmorStandNpcsIn(entity.getWorld())) {
            if (npc.getEntities().contains(entity))
                return npc;
        }
        return null;
    }

    public static List<ArmorStandNpc> getArmorStandNpcsIn(World world) {
        return armorStandNpcs.get(world);
    }
}
