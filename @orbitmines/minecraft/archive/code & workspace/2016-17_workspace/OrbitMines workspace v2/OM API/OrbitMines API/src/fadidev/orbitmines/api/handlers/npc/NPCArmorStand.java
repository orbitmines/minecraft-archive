package fadidev.orbitmines.api.handlers.npc;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.utils.WorldUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

/**
 * Created by Fadi on 5-9-2016.
 */
public class NPCArmorStand {

    private static OrbitMinesAPI api;
    private Location location;
    private ArmorStand armorStand;
    private InteractAction interactAction;
    private boolean arms;
    private boolean basePlate;
    private EulerAngle bodyPose;
    private EulerAngle headPose;
    private EulerAngle leftArmPose;
    private EulerAngle leftLegPose;
    private EulerAngle rightArmPose;
    private EulerAngle rightLegPose;
    private ItemStack itemInHand;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private String customName;
    private boolean customNameVisible;
    private int fireTicks;
    private boolean gravity;
    private boolean small;
    private boolean visible;
    private Item item;
    private ItemStack itemStack;
    private String itemName;
    private boolean useItem;
    private Hologram hologramName;

    public NPCArmorStand(Location location){
        api = OrbitMinesAPI.getApi();
        this.location = location;
        this.arms = false;
        this.basePlate = false;
        this.bodyPose = EulerAngle.ZERO;
        this.headPose = EulerAngle.ZERO;
        this.leftArmPose = EulerAngle.ZERO;
        this.leftLegPose = EulerAngle.ZERO;
        this.rightArmPose = EulerAngle.ZERO;
        this.rightLegPose = EulerAngle.ZERO;
        this.customNameVisible = false;
        this.fireTicks = 0;
        this.gravity = true;
        this.small = false;
        this.visible = true;
    }

    public NPCArmorStand(Location location, InteractAction interactAction){
        api = OrbitMinesAPI.getApi();
        this.location = location;
        this.interactAction = interactAction;
        this.arms = false;
        this.basePlate = false;
        this.bodyPose = EulerAngle.ZERO;
        this.headPose = EulerAngle.ZERO;
        this.leftArmPose = EulerAngle.ZERO;
        this.leftLegPose = EulerAngle.ZERO;
        this.rightArmPose = EulerAngle.ZERO;
        this.rightLegPose = EulerAngle.ZERO;
        this.customNameVisible = false;
        this.fireTicks = 0;
        this.gravity = true;
        this.small = false;
        this.visible = true;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public InteractAction getInteractAction() {
        return interactAction;
    }

    public void setInteractAction(InteractAction interactAction) {
        this.interactAction = interactAction;
    }

    public boolean hasArms() {
        return arms;
    }

    public void setArmorStand(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public boolean hasBasePlate() {
        return basePlate;
    }

    public void setBasePlate(boolean basePlate) {
        this.basePlate = basePlate;
    }

    public EulerAngle getBodyPose() {
        return bodyPose;
    }

    public void setBodyPose(EulerAngle bodyPose) {
        this.bodyPose = bodyPose;
    }

    public EulerAngle getHeadPose() {
        return headPose;
    }

    public void setHeadPose(EulerAngle headPose) {
        this.headPose = headPose;
    }

    public EulerAngle getLeftArmPose() {
        return leftArmPose;
    }

    public void setLeftArmPose(EulerAngle leftArmPose) {
        this.leftArmPose = leftArmPose;
    }

    public EulerAngle getLeftLegPose() {
        return leftLegPose;
    }

    public void setLeftLegPose(EulerAngle leftLegPose) {
        this.leftLegPose = leftLegPose;
    }

    public EulerAngle getRightArmPose() {
        return rightArmPose;
    }

    public void setRightArmPose(EulerAngle rightArmPose) {
        this.rightArmPose = rightArmPose;
    }

    public EulerAngle getRightLegPose() {
        return rightLegPose;
    }

    public void setRightLegPose(EulerAngle rightLegPose) {
        this.rightLegPose = rightLegPose;
    }

    public ItemStack getItemInHand() {
        return itemInHand;
    }

    public void setItemInHand(ItemStack itemInHand) {
        this.itemInHand = itemInHand;
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public void setChestplate(ItemStack chestplate) {
        this.chestplate = chestplate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;

        updateName();
    }

    /* 1.9+ Small ArmorStand NameTag Fix */
    public void updateName(){
        if(getArmorStand() == null || !isCustomNameVisible())
            return;

        if(getHologramName() != null)
            getHologramName().delete();

        if(!isSmall()) {
            getArmorStand().setCustomName(getCustomName());
            getArmorStand().setCustomNameVisible(isCustomNameVisible());
        }
        else{
            Hologram hologram = new Hologram(WorldUtils.asNewLocation(getLocation(), 0, -1, 0));
            hologram.addLine(getCustomName());
            hologram.create();
            api.registerHologram(hologram);
            setHologramName(hologram);
        }
    }

    public boolean isCustomNameVisible() {
        return customNameVisible;
    }

    public void setCustomNameVisible(boolean customNameVisible) {
        this.customNameVisible = customNameVisible;
    }

    public int getFireTicks() {
        return fireTicks;
    }

    public void setFireTicks(int fireTicks) {
        this.fireTicks = fireTicks;
    }

    public boolean isGravity() {
        return gravity;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    public boolean isSmall() {
        return small;
    }

    public void setSmall(boolean small) {
        this.small = small;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public boolean hasItem() {
        return useItem;
    }

    public void setUseItem(boolean useItem) {
        this.useItem = useItem;
    }

    public Hologram getHologramName() {
        return hologramName;
    }

    public void setHologramName(Hologram hologramName) {
        this.hologramName = hologramName;
    }

    public void click(Player player){
        if(interactAction != null)
            interactAction.click(player, this);
    }

    public void checkEntity(){
        if(!getArmorStand().isValid()){
            clear();
            spawn();
            return;
        }

        if(!hasItem())
            return;

        if(getArmorStand().isDead() || getItem().isDead() || !getItem().isValid() || getArmorStand().getPassenger() == null || getItem().getVehicle() == null){
            clear();
            spawn();
        }

        if(getArmorStand() != null && getArmorStand().getLocation().distance(getLocation()) >= 0.1)
            getArmorStand().teleport(getLocation());
    }

    public void spawn(){
        Chunk c = location.getChunk();
        c.load();
        if(!api.getChunks().contains(c))
            api.getChunks().add(c);
        for(Entity en : location.getWorld().getNearbyEntities(location, 0.1, 0.1, 0.1)){
            if(en instanceof Player)
                continue;

            en.remove();
        }

        ArmorStand as = (ArmorStand) getLocation().getWorld().spawnEntity(getLocation(), EntityType.ARMOR_STAND);
        setArmorStand(as);

        update();

        if(hasItem()){
            Item item = getLocation().getWorld().dropItem(location, getItemStack());
            item.setPickupDelay(Integer.MAX_VALUE);
            item.setCustomName(getItemName());
            item.setCustomNameVisible(true);

            as.setPassenger(item);
            setItem(item);
        }
    }

    public void update(){
        ArmorStand as = getArmorStand();
        as.setRemoveWhenFarAway(false);
        as.setArms(hasArms());
        as.setBasePlate(hasBasePlate());
        as.setBodyPose(getBodyPose());
        as.setBoots(getBoots());
        as.setChestplate(getChestplate());
        as.setFireTicks(getFireTicks());
        as.setGravity(isGravity());
        as.setHeadPose(getHeadPose());
        as.setHelmet(getHelmet());
        as.setItemInHand(getItemInHand());
        as.setLeftArmPose(getLeftArmPose());
        as.setLeftLegPose(getLeftLegPose());
        as.setLeggings(getLeggings());
        as.setRightArmPose(getRightArmPose());
        as.setRightLegPose(getRightLegPose());
        as.setSmall(isSmall());
        as.setVisible(isVisible());

        updateName();
    }

    public void clear(){
        if(this.armorStand != null)
            this.armorStand.remove();
        if(this.item != null)
            this.item.remove();
        if(this.hologramName != null)
            this.hologramName.delete();
    }

    public void delete(){
        clear();

        api.getNpcArmorStands().remove(this);
    }

    public static NPCArmorStand getNPCArmorStand(ArmorStand armorstand){
        for(NPCArmorStand npcas : api.getNpcArmorStands()){
            if(npcas.getArmorStand() == armorstand)
                return npcas;
        }
        return null;
    }

    public static abstract class InteractAction {

        public abstract void click(Player player, NPCArmorStand clicked);

    }
}
