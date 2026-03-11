package fadidev.orbitmines.api.handlers.npc;


import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.nms.Nms;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Mob;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 15-5-2016.
 */
public class NPC {

    private static OrbitMinesAPI api;
    private Nms nms;
    private Mob mob;
    private EntityType entityType;
    private String displayName;
    private Location location;
    private InteractAction interactAction;
    private Entity entity;
    private ArmorStand nameTag;
    private ArmorStand asBat;
    private boolean fire;

    private ItemStack itemInHand;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;

    public NPC(Mob mob, Location location, String displayName){
        api = OrbitMinesAPI.getApi();
        this.nms = api.getNms();
        this.mob = mob;
        this.entityType = EntityType.valueOf(mob.toString());
        this.location = location;
        this.displayName = displayName;
        this.fire = false;

        spawn();
    }

    public NPC(Mob mob, Location location, String displayName, InteractAction interactAction){
        api = OrbitMinesAPI.getApi();
        this.nms = api.getNms();
        this.mob = mob;
        this.entityType = EntityType.valueOf(mob.toString());
        this.location = location;
        this.displayName = displayName;
        this.interactAction = interactAction;
        this.fire = false;

        spawn();
    }

    public Mob getMob() {
        return mob;
    }

    public void setMob(Mob mob){
        this.mob = mob;
        this.entityType = EntityType.valueOf(mob.toString());

        spawn();
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
        this.mob = Mob.valueOf(entityType.toString());

        spawn();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;

        getEntity().setCustomName(getDisplayName());
        getEntity().setCustomNameVisible(true);

        if(getNameTag() != null){
            getNameTag().setCustomName(getDisplayName());
            getNameTag().setCustomNameVisible(true);
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;

        remove();
        spawn();
    }

    public Entity getEntity() {
        return entity;
    }

    public ArmorStand getNameTag() {
        return nameTag;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public short getEggId(){
        return getEntityType().getTypeId();
    }

    public Location getFixedLocation(){
        return (mob == Mob.BAT || mob == Mob.SQUID || mob == Mob.GUARDIAN) && nms.getVersion().startsWith("v1_10_R") ? new Location(getLocation().getWorld(), getLocation().getX(), getLocation().getY() + 1, getLocation().getZ(), getLocation().getYaw(), getLocation().getPitch()) : getLocation();
    }

    public void spawn(){
        remove();

        Chunk c = location.getChunk();
        c.load();
        if(!api.getChunks().contains(c))
            api.getChunks().add(c);

        if(this instanceof NPCMoving)
            this.entity = getMob().spawnMoving(getFixedLocation(), getDisplayName());
        else
            this.entity = getMob().spawn(getFixedLocation(), getDisplayName());

        if(entity != null){
            if(nms.getVersion().startsWith("v1_8_R")) {
                /* Spawn NameTag, as 1.8 doesn't show name tags. */
                spawnNameTag();
            }

            /* Stop bat from flying, doing this through nms can get tricky. */
            if(!nms.getVersion().startsWith("v1_10_R") && (mob == Mob.BAT || mob == Mob.SQUID || mob == Mob.GUARDIAN)){
                ArmorStand asBat = nms.armorstand().spawn(getLocation(), false);
                asBat.setGravity(false);
                asBat.setSmall(true);
                this.asBat = asBat;

                asBat.setPassenger(entity);
            }

            for(Entity en : getEntity().getNearbyEntities(0.1, 0.1, 0.1)){
                if(en instanceof Player || en instanceof ArmorStand && (Hologram.getHologram((ArmorStand) en) != null || NPCArmorStand.getNPCArmorStand((ArmorStand) en) != null) || NPC.getNpc(en) != null)
                    continue;

                en.remove();
            }
        }
        else{
            Utils.consoleWarning("Error while spawning Mob " + getMob().toString() + "!");
        }
    }

    public void spawnNameTag(){
        ArmorStand nameTag = nms.armorstand().spawn(getLocation(), false);
        nameTag.setCustomName(getDisplayName());
        nameTag.setCustomNameVisible(true);
        nameTag.setRemoveWhenFarAway(false);
        nameTag.setSmall(true);
        this.nameTag = nameTag;

        entity.setPassenger(nameTag);
    }

    public void remove(){
        if(getEntity() != null)
            getEntity().remove();

        if(getNameTag() != null)
            getNameTag().remove();

        if(asBat != null)
            asBat.remove();
    }

    public void click(Player player){
        if(interactAction != null)
            interactAction.click(player, this);
    }

    public void checkEntity(){
        if(getEntity() != null){
            if(getEntity().isDead() || !getEntity().isValid()) {
                spawn();
                setItemInHand(itemInHand);
                setHelmet(helmet);
                setChestplate(chestplate);
                setLeggings(leggings);
                setBoots(boots);
            }

            if(getNameTag() != null && getEntity().getPassenger() == null)
                spawnNameTag();

            if(!isFire())
                getEntity().setFireTicks(0);
            else
                getEntity().setFireTicks(Integer.MAX_VALUE);

            Location l = getFixedLocation();
            if(!(this instanceof NPCMoving) && getEntity().getLocation().distance(l) >= 0.1)
                getEntity().teleport(l);
        }

        if(asBat != null && getEntity().getVehicle() == null)
            asBat.setPassenger(getEntity());
    }

    public void setItemInHand(ItemStack item){
        itemInHand = item;
        ((LivingEntity) getEntity()).getEquipment().setItemInHand(item);
    }

    public void setHelmet(ItemStack item){
        helmet = item;
        ((LivingEntity) getEntity()).getEquipment().setHelmet(item);
    }

    public void setChestplate(ItemStack item){
        chestplate = item;
        ((LivingEntity) getEntity()).getEquipment().setChestplate(item);
    }

    public void setLeggings(ItemStack item){
        leggings = item;
        ((LivingEntity) getEntity()).getEquipment().setLeggings(item);
    }

    public void setBoots(ItemStack item){
        boots = item;
        ((LivingEntity) getEntity()).getEquipment().setBoots(item);
    }

    public void setSkeletonType(Skeleton.SkeletonType skeletonType){
        if(getEntity() != null && getMob() == Mob.SKELETON)
            ((Skeleton) getEntity()).setSkeletonType(skeletonType);
    }

    public void setVillagerProfession(Villager.Profession villagerProfession){
        if(getEntity() != null && getMob() == Mob.VILLAGER)
            ((Villager) getEntity()).setProfession(villagerProfession);
    }

    public static NPC getNpc(Entity entity){
        if(api == null)
            api = OrbitMinesAPI.getApi();

        for(NPC npc : api.getNpcs()){
            if(npc.getEntity() == entity)
                return npc;
        }

        return null;
    }

    public static abstract class InteractAction {

        public abstract void click(Player player, NPC clicked);

    }
}
