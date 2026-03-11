package fadidev.spigotwof.handlers;

import fadidev.spigotwof.SpigotWoF;
import fadidev.spigotwof.nms.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class NPC {

	private static SpigotWoF swof;
	private Location location;
	private EntityType entitytype;
	private String displayname;
	private Entity entity;
	private ArmorStand nameTag;
	private ArmorStand asbat;
	
	public NPC(){
		swof = SpigotWoF.getInstance();
        swof.getNPCs().add(this);
		
		new BukkitRunnable(){
			public void run(){
				if(getEntity() != null && !getEntity().isDead()){
					checkEntity();
				}
			}
		}.runTaskTimer(swof, 0, 3);
	}

	public Location getLocation(){
		return this.location;
	}
	public void setLocation(Location location){
		this.location = location;
	}
	
	public EntityType getEntityType(){
		return entitytype;
	}
	public void setEntityType(EntityType entitytype){
		this.entitytype = entitytype;
	}

	public String getDisplayname() {
		return this.displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Entity getEntity(){
		return this.entity;
	}
	public void setEntity(Entity entity){
		this.entity = entity;
	}

	public ArmorStand getNameTag(){
		return this.nameTag;
	}
	public void setNameTag(ArmorStand nameTag){
		this.nameTag = nameTag;
	}

	public void checkEntity(){
	    if(getEntity() != null){
		    if(getEntity().getPassenger() == null){
				ArmorStand as = (ArmorStand) getEntity().getLocation().getWorld().spawnEntity(getEntity().getLocation(), EntityType.ARMOR_STAND);
				as.setCustomName(getEntity().getCustomName());
				as.setCustomNameVisible(true);
				as.setRemoveWhenFarAway(false);
				as.setSmall(true);
				as.setVisible(false);
				
				((CraftArmorStand) as).getHandle().setInvisible(true);
			    
			    getEntity().setPassenger(as);
			    setNameTag(as);
		    }
		    
	    	if(getEntity().getLocation().distance(getLocation()) >= 0.1){
	    		getEntity().teleport(getLocation());
	    	}
	    }
	}
	
	public void setItemInHand(ItemStack item){
	    EntityEquipment ee = ((LivingEntity) getEntity()).getEquipment();
	    ee.setItemInHand(item);
	}
	public void setHelmet(ItemStack item){
	    EntityEquipment ee = ((LivingEntity) getEntity()).getEquipment();
	    ee.setHelmet(item);
	}
	public void setChestplate(ItemStack item){
	    EntityEquipment ee = ((LivingEntity) getEntity()).getEquipment();
	    ee.setChestplate(item);
	}
	public void setLeggings(ItemStack item){
	    EntityEquipment ee = ((LivingEntity) getEntity()).getEquipment();
	    ee.setLeggings(item);
	}
	public void setBoots(ItemStack item){
	    EntityEquipment ee = ((LivingEntity) getEntity()).getEquipment();
	    ee.setBoots(item);
	}
	
	public void newEntity(EntityType entitytype, Location location, String displayname){
		if(getEntity() != null){
			getEntity().remove();
		}
		if(getNameTag() != null){
			getNameTag().remove();
		}
		if(asbat != null){
			asbat.remove();
		}
		
		setEntityType(entitytype);
		setLocation(location);
		setDisplayname(displayname);
		
		World w = location.getWorld();
		
		switch(entitytype){
			case ARMOR_STAND:
				break;
			case ARROW:
				break;
			case BAT:
				spawnBat(w, location, displayname);
				break;
			case BLAZE:
			    spawnBlaze(w, location, displayname);
				break;
			case BOAT:
				break;
			case CAVE_SPIDER:
				spawnCaveSpider(w, location, displayname);
				break;
			case CHICKEN:
				spawnChicken(w, location, displayname);
				break;
			case COMPLEX_PART:
				break;
			case COW:
				spawnCow(w, location, displayname);
				break;
			case CREEPER:
				spawnCreeper(w, location, displayname);
				break;
			case DROPPED_ITEM:
				break;
			case EGG:
				break;
			case ENDERMAN:
				spawnEnderman(w, location, displayname);
				break;
			case ENDERMITE:
				spawnEndermite(w, location, displayname);
				break;
			case ENDER_CRYSTAL:
				break;
			case ENDER_DRAGON:
				break;
			case ENDER_PEARL:
				break;
			case ENDER_SIGNAL:
				break;
			case EXPERIENCE_ORB:
				break;
			case FALLING_BLOCK:
				break;
			case FIREBALL:
				break;
			case FIREWORK:
				break;
			case FISHING_HOOK:
				break;
			case GHAST:
				spawnGhast(w, location, displayname);
				break;
			case GIANT:
				break;
			case GUARDIAN:
				break;
			case HORSE:
				spawnHorse(w, location, displayname);
				break;
			case IRON_GOLEM:
				spawnIronGolem(w, location, displayname);
				break;
			case ITEM_FRAME:
				break;
			case LEASH_HITCH:
				break;
			case LIGHTNING:
				break;
			case MAGMA_CUBE:
				spawnMagmaCube(w, location, displayname);
				break;
			case MINECART:
				break;
			case MINECART_CHEST:
				break;
			case MINECART_COMMAND:
				break;
			case MINECART_FURNACE:
				break;
			case MINECART_HOPPER:
				break;
			case MINECART_MOB_SPAWNER:
				break;
			case MINECART_TNT:
				break;
			case MUSHROOM_COW:
				spawnMushroomCow(w, location, displayname);
				break;
			case OCELOT:
				spawnOcelot(w, location, displayname);
				break;
			case PAINTING:
				break;
			case PIG:
				spawnPig(w, location, displayname);
				break;
			case PIG_ZOMBIE:
				spawnPigZombie(w, location, displayname);
				break;
			case PLAYER:
				break;
			case PRIMED_TNT:
				break;
			case RABBIT:
				spawnRabbit(w, location, displayname);
				break;
			case SHEEP:
				spawnSheep(w, location, displayname);
				break;
			case SILVERFISH:
				spawnSilverfish(w, location, displayname);
				break;
			case SKELETON:
				spawnSkeleton(w, location, displayname);
				break;
			case SLIME:
				spawnSlime(w, location, displayname);
				break;
			case SMALL_FIREBALL:
				break;
			case SNOWBALL:
				break;
			case SNOWMAN:
				spawnSnowman(w, location, displayname);
				break;
			case SPIDER:
				spawnSpider(w, location, displayname);
				break;
			case SPLASH_POTION:
				break;
			case SQUID:
				spawnSquid(w, location, displayname);
				break;
			case THROWN_EXP_BOTTLE:
				break;
			case UNKNOWN:
				break;
			case VILLAGER:
				spawnVillager(w, location, displayname);
				break;
			case WEATHER:
				break;
			case WITCH:
				spawnWitch(w, location, displayname);
				break;
			case WITHER:
				break;
			case WITHER_SKULL:
				break;
			case WOLF:
				spawnWolf(w, location, displayname);
				break;
			case ZOMBIE:
				spawnZombie(w, location, displayname);
				break;
			default:
				break;
		}
	}
	
	public void setSkeletonType(SkeletonType skeletontype){
		if(getEntity() != null && getEntity() instanceof Skeleton){
			Skeleton sk = (Skeleton) getEntity();
			sk.setSkeletonType(skeletontype);
		}
	}
	
	public void setVillagerProfession(Profession profession){
		if(getEntity() != null && getEntity() instanceof Villager){
			Villager v = (Villager) getEntity();
			v.setProfession(profession);
		}
	}
	
	@SuppressWarnings("deprecation")
	public short getSpawnEggID(){
		return getEntityType().getTypeId();
	}
	
	public Item getDisplaynameItem(World w, Location location, String displayname){
		ItemStack i = new ItemStack(Material.WOOD_BUTTON);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(displayname);
		i.setItemMeta(meta);
		Item item = w.dropItem(location, i);
		item.setCustomName(displayname);
		item.setCustomNameVisible(true);
		item.setPickupDelay(Integer.MAX_VALUE);
		((CraftItem) item).getHandle().setInvisible(true);
		
	    return item;
	}
	
	public static List<NPC> getNPCs(){
		return swof.getNPCs();
	}
	
	public static NPC getNPC(Entity en){
		for(NPC npc : swof.getNPCs()){
			if(npc.getEntity() == en){
				return npc;
			}
		}
		return null;
	}
	
	private void spawnBat(World w, Location location, String displayname) {
        if (this.asbat != null) {
            this.asbat.remove();
        }

        ArmorStand asbat = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
        ((CraftArmorStand) asbat).getHandle().setInvisible(true);
        this.asbat = asbat;

        net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
        final CustomBat e = new CustomBat(nmsWorld);
        e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        nmsWorld.addEntity(e);
        ((CraftBat) e.getBukkitEntity()).setRemoveWhenFarAway(false);

        setEntity(e.getBukkitEntity());
        getEntity().teleport(location);

        ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
        as.setCustomName(displayname);
        as.setCustomNameVisible(true);
        as.setRemoveWhenFarAway(false);
        as.setSmall(true);
        as.setVisible(false);

        ((CraftArmorStand) as).getHandle().setInvisible(true);

        getEntity().setPassenger(as);
        setNameTag(as);
    }
	
	private void spawnBlaze(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomBlaze e = new CustomBlaze(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftBlaze) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnCaveSpider(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomCaveSpider e = new CustomCaveSpider(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftCaveSpider) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	    
	}
	
	private void spawnChicken(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomChicken e = new CustomChicken(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftChicken) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnCow(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomCow e = new CustomCow(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftCow) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnCreeper(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomCreeper e = new CustomCreeper(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftCreeper) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnEnderman(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomEnderman e = new CustomEnderman(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftEnderman) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnEndermite(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomEndermite e = new CustomEndermite(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftEndermite) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnGhast(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomGhast e = new CustomGhast(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftGhast) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnHorse(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomHorse e = new CustomHorse(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftHorse) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnIronGolem(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomIronGolem e = new CustomIronGolem(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftIronGolem) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnMagmaCube(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomMagmaCube e = new CustomMagmaCube(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftMagmaCube) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnMushroomCow(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomMushroomCow e = new CustomMushroomCow(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftMushroomCow) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnOcelot(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomOcelot e = new CustomOcelot(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftOcelot) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnPig(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomPig e = new CustomPig(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftPig) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnPigZombie(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomPigZombie e = new CustomPigZombie(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftPigZombie) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnRabbit(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomRabbit e = new CustomRabbit(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftRabbit) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnSheep(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomSheep e = new CustomSheep(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftSheep) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnSilverfish(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomSilverfish e = new CustomSilverfish(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftSilverfish) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnSkeleton(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();

        final CustomSkeleton e = new CustomSkeleton(nmsWorld);
        e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        nmsWorld.addEntity(e);
        ((CraftSkeleton) e.getBukkitEntity()).setRemoveWhenFarAway(false);

        setEntity(e.getBukkitEntity());
        getEntity().teleport(location);

        ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
        as.setCustomName(displayname);
        as.setCustomNameVisible(true);
        as.setRemoveWhenFarAway(false);
        as.setSmall(true);
        as.setVisible(false);

        ((CraftArmorStand) as).getHandle().setInvisible(true);

        getEntity().setPassenger(as);
        setNameTag(as);
		
		getEntity().teleport(location);
	}
	
	private void spawnSlime(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomSlime e = new CustomSlime(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftSlime) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
		
		getEntity().teleport(location);
	}
	
	private void spawnSnowman(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomSnowman e = new CustomSnowman(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftSnowman) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
		
		getEntity().teleport(location);
	}
	
	private void spawnSpider(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomSpider e = new CustomSpider(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftSpider) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnSquid(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomSquid e = new CustomSquid(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftSquid) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnVillager(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomVillager e = new CustomVillager(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftVillager) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnWitch(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomWitch e = new CustomWitch(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftWitch) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnWolf(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomWolf e = new CustomWolf(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftWolf) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
	
	private void spawnZombie(World w, Location location, String displayname){
	    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) w).getHandle();
	    final CustomZombie e = new CustomZombie(nmsWorld);
	    e.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	    nmsWorld.addEntity(e);
	    ((CraftZombie) e.getBukkitEntity()).setRemoveWhenFarAway(false);
	    
		setEntity(e.getBukkitEntity());
		getEntity().teleport(location);		
		
		ArmorStand as = (ArmorStand) w.spawnEntity(location, EntityType.ARMOR_STAND);
		as.setCustomName(displayname);
		as.setCustomNameVisible(true);
		as.setRemoveWhenFarAway(false);
		as.setSmall(true);
		as.setVisible(false);
		
		((CraftArmorStand) as).getHandle().setInvisible(true);
	    
	    getEntity().setPassenger(as);
	    setNameTag(as);
	}
}
