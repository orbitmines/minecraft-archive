package om.api.handlers.players;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.BlockLog1;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityCaveSpider;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityCow;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityEndermite;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntityGuardian;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import net.minecraft.server.v1_8_R3.EntityMushroomCow;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.EntityPigZombie;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntitySilverfish;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntitySnowman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntitySquid;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.EntityWitch;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import om.api.API;
import om.api.handlers.Database;
import om.api.handlers.FireworkSettings;
import om.api.handlers.MGArena;
import om.api.handlers.Particle;
import om.api.handlers.Title;
import om.api.utils.ColorUtils;
import om.api.utils.ItemUtils;
import om.api.utils.Utils;
import om.api.utils.enums.Cooldown;
import om.api.utils.enums.Server;
import om.api.utils.enums.cp.ChatColor;
import om.api.utils.enums.cp.Disguise;
import om.api.utils.enums.cp.Gadget;
import om.api.utils.enums.cp.Hat;
import om.api.utils.enums.cp.Pet;
import om.api.utils.enums.cp.Trail;
import om.api.utils.enums.cp.TrailType;
import om.api.utils.enums.ranks.StaffRank;
import om.api.utils.enums.ranks.VIPRank;
import om.api.utils.others.ComponentMessage;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

public abstract class OMPlayer {

	private static API api;
	private Player player;
	
	private boolean newplayer;
	private boolean silent;
	private boolean canchat;
	private boolean opmode;
	private boolean loaded;
	
	private Location lastlocation;
	
	private boolean afk;
	private String afkname;
	
	private VIPRank viprank;
	private StaffRank staffrank;

	private ChatColor chatcolor;
	private List<ChatColor> chatcolors;
	private boolean unlockedchatcolorbold;
	private boolean chatcolorbold;
	private boolean unlockedchatcolorcursive;
	private boolean chatcolorcursive;
	
	private List<Disguise> disguises;
	private boolean isdisguised;
	private int disguiseblockid;
	private EntityType disguiseentitytype;
	private boolean isdisguisedbaby;
	private EntityLiving disguise;
	
	private List<UUID> friends;
	
	private List<Gadget> gadgets;
	private Entity soccermagmacube;
	private Entity swapteleporter;
	private int sgaseconds;
	private Entity sgaitem;
	private List<Entity> sgasnowgolems;
	
	private List<Hat> hats;
	private int hatsinvpage;
	private boolean unlockedhatsblocktrail;
	private boolean hatsblocktrail;
	
	private boolean petshroomtrail;
	private boolean petbabypigs;
	private List<Entity> petbabypigentities;
	private boolean petsheepdisco;
	private List<Pet> pets;
	private HashMap<Pet, String> petnames;
	private Entity pet;
	private Pet petenabled;

	private Trail trail;
	private TrailType trailtype;
	private List<Trail> trails;
	private int trailparticlesamount;
	private boolean specialtrail;
	private boolean unlockedspecialtrail;
	private List<TrailType> trailtypes;
	private Particle lastparticle;
	private boolean particleplaynext;
	
	private List<Color> wardrobe;
	private boolean unlockedwardrobedisco;
	private boolean wardrobedisco;
	
	private int fireworkpasses;
	private FireworkSettings fwsettings;
	
	private String nickname;
	
	private boolean hubplayersenabled;
	private boolean hubscoreboardenabled;
	private boolean hubstackerenabled;
	private boolean completedlapisparkour;
	private boolean inlapisparkour;
	private boolean inmindcraft;
	
	private boolean receivedmonthlybonus;

	private HashMap<Cooldown, Long> cooldowns;
	 
	private int votes;
	private int vippoints;
	private int orbitminestokens;
	private int minigamecoins;
	
	public OMPlayer(Player player, boolean loaded){
		api = API.getInstance();
		this.player = player;
		this.loaded = loaded;
		this.cooldowns = new HashMap<Cooldown, Long>();
		
		api.getPlayers().put(player, this);
		api.getOMPlayers().add(this);
		
		loadData();
	}
	
	public abstract String getPrefix();
	public abstract void vote();
	public abstract void unloadPlayerData();
	public abstract void loadPlayerData();
	
	public Player getPlayer(){
		return player;
	}
	public void setPlayer(Player player){
		this.player = player;
	}

	public boolean isSilentMode(){
		return silent;
	}
	public void setSilentMode(boolean silent){
		this.silent = silent;
	}

	public boolean canChat(){
		return canchat;
	}
	public void setCanChat(boolean canchat){
		this.canchat = canchat;
		
		if(canchat){
			getPlayer().sendMessage("§3§lHub §8| §7Chat §a§lENABLED§7!");
		}
	}

	public boolean isOpMode(){
		return opmode;
	}
	public void setOpMode(boolean opmode){
		this.opmode = opmode;
	}

	public boolean isLoaded(){
		return loaded;
	}
	public void setLoaded(boolean loaded){
		this.loaded = loaded;
	}

	public Location getLastLocation(){
		return lastlocation;
	}
	public void setLastLocation(Location lastlocation){
		this.lastlocation = lastlocation;
	}
	public void checkLastLocation(){
		if(getLastLocation() == null){
			setLastLocation(getPlayer().getLocation());
			setParticlePlayNext(true);
		}
		else{
			if(getLastLocation().getWorld().getName().equals(getPlayer().getWorld().getName())){
				if(getLastLocation().distance(getPlayer().getLocation()) <= 0.1){
					setParticlePlayNext(true);
				}
				else{
					setLastLocation(getPlayer().getLocation());
					setParticlePlayNext(false);
				}
			}
			else{
				setLastLocation(getPlayer().getLocation());
				setParticlePlayNext(false);
			}
		}
	}
	
	public boolean isAFK(){
		return afk;
	}
	public void setAFK(boolean afk){
		this.afk = afk;
		
		if(!afk){
			setAFKName(null);
		}
	}

	public String getAFKName(){
		return afkname;
	}
	public void setAFKName(String afkname){
		this.afkname = afkname;
	}
	public void nowAFK(String afkname){
		this.afk = true;
		
		if(afkname != null){
			Bukkit.broadcastMessage("§7 " + getName() + "§7 is now §6AFK§7. (§7" + afkname + "§7)");
			this.afkname = afkname;
		}
		else{
			Bukkit.broadcastMessage("§7 " + getName() + "§7 is now §6AFK§7.");
		}
	}
	public void noLongerAFK(){
		this.afk = false;
		
		if(getAFKName() != null){
			Bukkit.broadcastMessage("§7 " + getName() + "§7 is no longer §6AFK§7. (§7" + getAFKName() + "§7)");
		}
		else{
			Bukkit.broadcastMessage("§7 " + getName() + "§7 is no longer §6AFK§7.");
		}
	}

	public VIPRank getVIPRank(){
		if(hasPerms(StaffRank.Owner) && isOpMode()){
			return VIPRank.Emerald_VIP;
		}
		return viprank;
	}
	public void setVIPRank(VIPRank viprank){
		this.viprank = viprank;
	}

	public StaffRank getStaffRank(){
		return staffrank;
	}
	public void setStaffRank(StaffRank staffrank){
		this.staffrank = staffrank;
	}

	public ChatColor getChatColor(){
		return chatcolor;
	}
	public void setChatColor(ChatColor chatcolor){
		this.chatcolor = chatcolor;
		
		getPlayer().playSound(getPlayer().getLocation(), Sound.PISTON_EXTEND, 5, 1);
		getPlayer().sendMessage("§a§lENABLED §7your " + chatcolor.getName() + "§7.");
	}

	public List<ChatColor> getChatColors(){
		return chatcolors;
	}
	public void setChatColors(List<ChatColor> chatcolors){
		this.chatcolors = chatcolors;
	}
	public void addChatColor(ChatColor chatcolor){
		this.chatcolors.add(chatcolor);
		
		if(!Database.get().containsPath("ChatColors-" + chatcolor.getDatabaseName(), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("ChatColors-" + chatcolor.getDatabaseName(), "uuid", getUUID().toString());
		}
	}
	public boolean hasChatColor(ChatColor chatcolor){
		return chatcolors.contains(chatcolor);
	}
	
	public boolean hasUnlockedBold(){
		return unlockedchatcolorbold;
	}
	public void setUnlockedBold(boolean unlockedchatcolorbold){
		this.unlockedchatcolorbold = unlockedchatcolorbold;
		
		if(unlockedchatcolorbold && !Database.get().containsPath("ChatColors-Bold", "uuid", "uuid", getUUID().toString())){
			Database.get().insert("ChatColors-Bold", "uuid`, `bold", getUUID().toString() + "', '" + false);
		}
	}

	public boolean isBold(){
		return chatcolorbold;
	}
	public void setBold(boolean chatcolorbold){
		this.chatcolorbold = chatcolorbold;
		
		getPlayer().playSound(getPlayer().getLocation(), Sound.PISTON_EXTEND, 5, 1);
		getPlayer().sendMessage("" + Utils.statusString(chatcolorbold) + " §7your " + getChatColor().getColor() + "§lBold ChatColor§7.");
	}
	private String getBoldString(){
		if(isBold()){
			return "§l";
		}
		return "";
	}
	
	public boolean hasUnlockedCursive(){
		return unlockedchatcolorcursive;
	}
	public void setUnlockedCursive(boolean unlockedchatcolorcursive){
		this.unlockedchatcolorcursive = unlockedchatcolorcursive;
		
		if(unlockedchatcolorcursive && !Database.get().containsPath("ChatColors-Cursive", "uuid", "uuid", getUUID().toString())){
			Database.get().insert("ChatColors-Cursive", "uuid`, `cursive", getUUID().toString() + "', '" + false);
		}
	}
	private String getCursiveString(){
		if(isCursive()){
			return "§o";
		}
		return "";
	}

	public boolean isCursive(){
		return chatcolorcursive;
	}
	public void setCursive(boolean chatcolorcursive){
		this.chatcolorcursive = chatcolorcursive;
		
		getPlayer().playSound(getPlayer().getLocation(), Sound.PISTON_EXTEND, 5, 1);
		getPlayer().sendMessage("" + Utils.statusString(chatcolorcursive) + " §7your " + getChatColor().getColor() + "§oCursive ChatColor§7.");
	}

	public List<Disguise> getDisguises(){
		return disguises;
	}
	public void setDisguises(List<Disguise> disguises){
		this.disguises = disguises;
	}
	public void addDisguise(Disguise disguise){
		this.disguises.add(disguise);
		
		if(!Database.get().containsPath("Dis-" + disguise.getDatabaseName(), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Dis-" + disguise.getDatabaseName(), "uuid", getUUID().toString());
		}
	}
	public boolean hasDisguise(Disguise disguise){
		return disguises.contains(disguise);
	}

	public boolean isDisguised(){
		return isdisguised;
	}
	public void setDisguised(boolean isdisguised){
		this.isdisguised = isdisguised;
	}

	public int getDisguiseBlockID(){
		return disguiseblockid;
	}
	public void setDisguiseBlockID(int disguiseblockid){
		this.disguiseblockid = disguiseblockid;
	}

	public EntityType getDisguiseEntityType(){
		return disguiseentitytype;
	}
	public void setDisguiseEntityType(EntityType disguiseentitytype){
		this.disguiseentitytype = disguiseentitytype;
	}

	public boolean isDisguisedBaby(){
		return isdisguisedbaby;
	}
	public void setDisguisedBaby(boolean isdisguisedbaby){
		this.isdisguisedbaby = isdisguisedbaby;
	}

	public EntityLiving getDisguise(){
		return disguise;
	}
	public void setDisguise(EntityLiving disguise){
		this.disguise = disguise;
	}

	public List<UUID> getFriends(){
		return friends;
	}
	public void setFriends(List<UUID> friends){
		this.friends = friends;
	}
	public void addFriend(UUID friend){
		this.friends.add(friend);
	}

	public List<Gadget> getGadgets(){
		return gadgets;
	}
	public void setGadgets(List<Gadget> gadgets){
		this.gadgets = gadgets;
	}
	public void addGadget(Gadget gadget){
		this.gadgets.add(gadget);
		
		if(!Database.get().containsPath("Gadgets-" + gadget.getDatabaseName(), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Gadgets-" + gadget.getDatabaseName(), "uuid", getUUID().toString());
		}
	}
	public boolean hasGadget(Gadget gadget){
		return gadgets.contains(gadget);
	}

	public Entity getSoccerMagmaCube(){
		return soccermagmacube;
	}
	public void setSoccerMagmaCube(Entity soccermagmacube){
		this.soccermagmacube = soccermagmacube;
	}
	public void disableSoccerMagmaCube(){
		getSoccerMagmaCube().remove();
		setSoccerMagmaCube(null);
		getPlayer().sendMessage("§c§lDISABLED§7 your §cMagmaCube Ball§7!");
	}

	public Entity getSwapTeleporter(){
		return swapteleporter;
	}
	public void setSwapTeleporter(Entity swapteleporter){
		this.swapteleporter = swapteleporter;
	}

	public int getSGASeconds(){
		return sgaseconds;
	}
	public void setSGASeconds(int sgaseconds){
		this.sgaseconds = sgaseconds;
	}

	public Entity getSGAItem(){
		return sgaitem;
	}
	public void setSGAItem(Entity sgaitem){
		this.sgaitem = sgaitem;
	}

	public List<Entity> getSGASnowGolems(){
		return sgasnowgolems;
	}
	public void setSGASnowGolems(List<Entity> sgasnowgolems){
		this.sgasnowgolems = sgasnowgolems;
	}
	public void addSGASnowGolem(Entity sgasnowgolem){
		this.sgasnowgolems.add(sgasnowgolem);
	}

	public List<Hat> getHats(){
		return hats;
	}
	public void setHats(List<Hat> hats){
		this.hats = hats;
	}
	public void addHat(Hat hat){
		this.hats.add(hat);
		
		if(!Database.get().containsPath("Hats-" + hat.getDatabaseName(), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Hats-" + hat.getDatabaseName(), "uuid", getUUID().toString());
		}
	}
	public boolean hasHat(){
		return getHats().size() > 0 || hasPerms(VIPRank.Iron_VIP);
	}
	public boolean hasHat(Hat hat){
		return hats.contains(hat);
	}
	public void setHat(Hat hat){
		if(hasHatEnabled()){
			disableHat();
		}
		getPlayer().playSound(getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
		getPlayer().getInventory().setHelmet(ItemUtils.setItem(new ItemStack(hat.getMaterial()), hat.getName(), hat.getDurability()));
		getPlayer().sendMessage("§a§lENABLED §7your " + hat.getName() + "§7.");
	}

	public int getHatsInvPage(){
		return hatsinvpage;
	}
	public void setHatsInvPage(int hatsinvpage){
		this.hatsinvpage = hatsinvpage;
	}
	public void nextHatsPage(){
		this.hatsinvpage = this.hatsinvpage +1;
	}
	public void prevHatsPage(){
		this.hatsinvpage = this.hatsinvpage -1;
	}

	public boolean hasUnlockedHatsBlockTrail(){
		return unlockedhatsblocktrail;
	}
	public void setUnlockedHatsBlockTrail(boolean unlockedhatsblocktrail){
		this.unlockedhatsblocktrail = unlockedhatsblocktrail;
		
		if(unlockedhatsblocktrail && !Database.get().containsPath("Hats-BlockTrail", "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Hats-BlockTrail", "uuid`, `blocktrail", getUUID().toString() + "', '" + false);
		}
	}

	public boolean hasHatsBlockTrail(){
		return hatsblocktrail;
	}
	public void setHatsBlockTrail(boolean hatsblocktrail){
		this.hatsblocktrail = hatsblocktrail;

		getPlayer().playSound(getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
		if(hatsblocktrail){
			getPlayer().sendMessage("§a§lENABLED §7your §7Hat Block Trail§7.");
		}
		else{
			getPlayer().sendMessage("§c§lDISABLED §7your §7Hat Block Trail§7!");
		}
	}

	public boolean hasPetShroomTrail(){
		return petshroomtrail;
	}
	public void setPetShroomTrail(boolean petshroomtrail){
		this.petshroomtrail = petshroomtrail;
	}

	public boolean hasPetBabyPigs(){
		return petbabypigs;
	}
	public void setPetBabyPigs(boolean petbabypigs){
		this.petbabypigs = petbabypigs;
	}

	public List<Entity> getPetBabyPigEntities(){
		return petbabypigentities;
	}
	public void setPetBabyPigEntities(List<Entity> petbabypigentities){
		this.petbabypigentities = petbabypigentities;
	}
	public void addPetBabyPigEntity(Entity petbabypigentity){
		this.petbabypigentities.add(petbabypigentity);
	}

	public boolean hasPetSheepDisco(){
		return petsheepdisco;
	}
	public void setPetSheepDisco(boolean petsheepdisco){
		this.petsheepdisco = petsheepdisco;
	}

	public List<Pet> getPets(){
		return pets;
	}
	public void setPets(List<Pet> pets){
		this.pets = pets;
	}
	public void addPet(Pet pet){
		this.pets.add(pet);
		
		if(!Database.get().containsPath("Pets-" + pet.getDatabaseName(), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Pets-" + pet.getDatabaseName(), "uuid`, `petname", getUUID().toString() + "', '" + getPlayer().getName() + "`s " + pet.getDatabaseName());
			this.petnames.put(pet, getPlayer().getName() + "'s " + pet.getDatabaseName());
		}
	}
	public boolean hasPet(){
		return getPets().size() > 0;
	}
	public boolean hasPet(Pet pet){
		return pets.contains(pet);
	}

	public HashMap<Pet, String> getPetNames(){
		return petnames;
	}
	public void setPetNames(HashMap<Pet, String> petnames){
		this.petnames = petnames;
	}
	public void setPetName(Pet pet, String petname){
		this.petnames.put(pet, petname);
		
		petname = petname.replace("'", "`");
		switch(pet){
			case CHICKEN:
				Database.get().update("Pets-Chicken", "petname", petname, "uuid", getUUID().toString());
			case COW:
				Database.get().update("Pets-Cow", "petname", petname, "uuid", getUUID().toString());
			case CREEPER:
				Database.get().update("Pets-Creeper", "petname", petname, "uuid", getUUID().toString());
			case HORSE:
				Database.get().update("Pets-Horse", "petname", petname, "uuid", getUUID().toString());
			case MAGMA_CUBE:
				Database.get().update("Pets-MagmaCube", "petname", petname, "uuid", getUUID().toString());
			case MUSHROOM_COW:
				Database.get().update("Pets-MushroomCow", "petname", petname, "uuid", getUUID().toString());
			case OCELOT:
				Database.get().update("Pets-Ocelot", "petname", petname, "uuid", getUUID().toString());
			case PIG:
				Database.get().update("Pets-Pig", "petname", petname, "uuid", getUUID().toString());
			case SHEEP:
				Database.get().update("Pets-Sheep", "petname", petname, "uuid", getUUID().toString());
			case SILVERFISH:
				Database.get().update("Pets-Silverfish", "petname", petname, "uuid", getUUID().toString());
			case SLIME:
				Database.get().update("Pets-Slime", "petname", petname, "uuid", getUUID().toString());
			case SPIDER:
				Database.get().update("Pets-Spider", "petname", petname, "uuid", getUUID().toString());
			case SQUID:
				Database.get().update("Pets-Squid", "petname", petname, "uuid", getUUID().toString());
			case WOLF:
				Database.get().update("Pets-Wolf", "petname", petname, "uuid", getUUID().toString());
			default:
				break;
		}
	}
	public String getPetName(Pet pet){
		return this.petnames.get(pet);
	}

	public Entity getPet(){
		return pet;
	}
	public void setPet(Entity pet){
		this.pet = pet;
		
		if(pet != null){
			Pet.pets.add(pet);
		}
	}

	public Pet getPetEnabled(){
		return petenabled;
	}
	public void setPetEnabled(Pet petenabled){
		this.petenabled = petenabled;
	}
	public boolean hasPetEnabled(){
		return petenabled != null;
	}
	
	public Trail getTrail(){
		return trail;
	}
	public void setTrail(Trail trail){
		this.trail = trail;
		
		if(trail != null){
			getPlayer().playSound(getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
			getPlayer().sendMessage("§a§lENABLED §7your " + trail.getName() + "§7.");
		}
	}
	public boolean hasTrailEnabled(){
		return getTrail() != null;
	}

	public TrailType getTrailType(){
		return trailtype;
	}
	public void setTrailType(TrailType trailtype){
		this.trailtype = trailtype;
		this.lastparticle = null;
		this.particleplaynext = true;

		getPlayer().playSound(getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
		getPlayer().sendMessage("§a§lENABLED §7your " + trailtype.getName() +"§7.");
	}

	public List<Trail> getTrails(){
		return trails;
	}
	public void setTrails(List<Trail> trails){
		this.trails = trails;
	}
	public void addTrail(Trail trail){
		this.trails.add(trail);
		
		if(!Database.get().containsPath("Trails-" + trail.getDatabaseName(), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Trails-" + trail.getDatabaseName(), "uuid", getUUID().toString());
		}
	}
	public boolean hasTrail(){
		return getTrails().size() > 0 || hasPerms(VIPRank.Iron_VIP);
	}
	public boolean hasTrail(Trail trail){
		return trails.contains(trail);
	}
	public void playTrail(){
		if(hasTrailEnabled()){
			Trail trail = getTrail();
			Particle pa = new Particle(trail.getEnumParticle(), getPlayer().getLocation());
			double cylinderyadded = 0.0;
			
			switch(getTrailType()){
				case BASIC_TRAIL:
					pa.setSize(1, 1, 1);
					break;
				case BIG_TRAIL:
					pa.setSize(2, 2, 2);
					break;
				case BODY_TRAIL:
					pa.setYAdded(1);
					break;
				case GROUND_TRAIL:
					pa.setSize(1, 0, 1);
					break;
				case HEAD_TRAIL:
					pa.setYAdded(2);
					break;
				case VERTICAL_TRAIL:
					pa.setSize(0, 2, 0);
					break;
				case ORBIT_TRAIL:
					if(this.lastparticle != null && canParticlePlayNext()){
						pa = this.lastparticle;
						pa.setSize(0, 0, 0);

						if(pa.getYAdded() > 1.65){
							pa.setPositiv(false);
						}
						if(pa.getYAdded() < 0){
							pa.setPositiv(true);
						}
						
						if(pa.isPositiv()){
							pa.addY(0.05);
							pa.setXAdded(1.1 * Math.cos((pa.getYAdded() * 2)));
							pa.setZAdded(1.1 * Math.sin((pa.getYAdded() * 2)));
						}
						else{
							pa.addY(-0.05);
							pa.setXAdded(1.1 * Math.cos(-(pa.getYAdded() * 2)));
							pa.setZAdded(1.1 * Math.sin(-(pa.getYAdded() * 2)));
						}
					}
					else{
						pa.setSize(1, 1, 1);
					}
					break;
				case CYLINDER_TRAIL:
					if(this.lastparticle != null && canParticlePlayNext()){
						pa = this.lastparticle;
						pa.setSize(0, 0, 0);
						cylinderyadded = pa.getYAdded();
						
						if(pa.getIndex() > 30){
							pa.setIndex(0);
							cylinderyadded = cylinderyadded +1;
						}
						else{
							pa.setIndex(pa.getIndex() +1);
						}
						if(cylinderyadded > 7){
							cylinderyadded = 0;
						}
						
						pa.setYAdded(0);
						pa.addY(0.2 * pa.getIndex());
						pa.setXAdded(1.1 * Math.cos(pa.getYAdded()));
						pa.setZAdded(1.1 * Math.sin(pa.getYAdded()));

						pa.setYAdded(0.25 * cylinderyadded);
					}
					else{
						pa.setSize(1, 1, 1);
					}
					break;
				case SNAKE_TRAIL:
					if(this.lastparticle != null){
						pa = this.lastparticle;
						pa.setLocation(getPlayer().getLocation());
						pa.setSize(0, 0, 0);
						
						if(pa.getYAdded() > 1.65){
							pa.setPositiv(false);
						}
						if(pa.getYAdded() < 0){
							pa.setPositiv(true);
						}
						
						if(pa.isPositiv()){
							pa.addY(0.25);
						}
						else{
							pa.addY(-0.25);
						}
					}
					else{
						pa.setSize(1, 1, 1);
					}
					break;
				default:
					break;
			}
			
			pa.setEnumParticle(getTrail().getEnumParticle());
			pa.setAmount(getTrailPA());
			if(hasSpecialTrail()){
				pa.setSpecial(1);
			}
			
			List<Player> players = Arrays.asList(Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
			
			pa.send(players);
			if(trail == Trail.SNOW){
				pa.setEnumParticle(EnumParticle.SNOWBALL);
				pa.send(players);
				pa.setEnumParticle(EnumParticle.SNOW_SHOVEL);
			}
			this.lastparticle = pa;
			
			if(trailtype == TrailType.ORBIT_TRAIL){
				pa.sendOposite(players);
				
				if(trail == Trail.SNOW){
					pa.setEnumParticle(EnumParticle.SNOWBALL);
					pa.sendOposite(players);
					pa.setEnumParticle(EnumParticle.SNOW_SHOVEL);
				}
			}
			
			if(trailtype == TrailType.CYLINDER_TRAIL){
				pa.setYAdded(1.75 -(0.25 * cylinderyadded));
				pa.send(players);
				
				if(trail == Trail.SNOW){
					pa.setEnumParticle(EnumParticle.SNOWBALL);
					pa.sendOposite(players);
					pa.setEnumParticle(EnumParticle.SNOW_SHOVEL);
				}
				pa.setYAdded(cylinderyadded);
			}
		}
	}
	
	public void parseTrail(Trail trail, TrailType trailtype, int amount, boolean special){
		Particle pa = new Particle(trail.getEnumParticle(), getPlayer().getLocation());
		double cylinderyadded = 0.0;
		if(this.lastparticle == null){
			this.lastparticle = pa;
		}
		
		switch(trailtype){
			case BASIC_TRAIL:
				pa.setSize(1, 1, 1);
				break;
			case BIG_TRAIL:
				pa.setSize(2, 2, 2);
				break;
			case BODY_TRAIL:
				pa.setYAdded(1);
				break;
			case GROUND_TRAIL:
				pa.setSize(1, 0, 1);
				break;
			case HEAD_TRAIL:
				pa.setYAdded(2);
				break;
			case VERTICAL_TRAIL:
				pa.setSize(0, 2, 0);
				break;
			case ORBIT_TRAIL:
				pa = this.lastparticle;
				pa.setSize(0, 0, 0);

				if(pa.getYAdded() > 1.65){
					pa.setPositiv(false);
				}
				if(pa.getYAdded() < 0){
					pa.setPositiv(true);
				}
				
				if(pa.isPositiv()){
					pa.addY(0.05);
					pa.setXAdded(1.1 * Math.cos((pa.getYAdded() * 2)));
					pa.setZAdded(1.1 * Math.sin((pa.getYAdded() * 2)));
				}
				else{
					pa.addY(-0.05);
					pa.setXAdded(1.1 * Math.cos(-(pa.getYAdded() * 2)));
					pa.setZAdded(1.1 * Math.sin(-(pa.getYAdded() * 2)));
				}
				break;
			case CYLINDER_TRAIL:
				pa = this.lastparticle;
				pa.setSize(0, 0, 0);
				cylinderyadded = pa.getYAdded();
				
				if(pa.getIndex() > 30){
					pa.setIndex(0);
					cylinderyadded = cylinderyadded +1;
				}
				else{
					pa.setIndex(pa.getIndex() +1);
				}
				if(cylinderyadded > 7){
					cylinderyadded = 0;
				}
				
				pa.setYAdded(0);
				pa.addY(0.2 * pa.getIndex());
				pa.setXAdded(1.1 * Math.cos(pa.getYAdded()));
				pa.setZAdded(1.1 * Math.sin(pa.getYAdded()));

				pa.setYAdded(0.25 * cylinderyadded);
				break;
			case SNAKE_TRAIL:
				pa = this.lastparticle;
				pa.setLocation(getPlayer().getLocation());
				pa.setSize(0, 0, 0);
				
				if(pa.getYAdded() > 1.65){
					pa.setPositiv(false);
				}
				if(pa.getYAdded() < 0){
					pa.setPositiv(true);
				}
				
				if(pa.isPositiv()){
					pa.addY(0.25);
				}
				else{
					pa.addY(-0.25);
				}
				break;
			default:
				break;
		}
		
		pa.setEnumParticle(trail.getEnumParticle());
		pa.setAmount(amount);
		if(specialtrail){
			pa.setSpecial(1);
		}
		else{
			pa.setSpecial(0);
		}
		
		pa.send(Bukkit.getOnlinePlayers());
		if(trail == Trail.SNOW){
			pa.setEnumParticle(EnumParticle.SNOWBALL);
			pa.send(Bukkit.getOnlinePlayers());
			pa.setEnumParticle(EnumParticle.SNOW_SHOVEL);
		}
		this.lastparticle = pa;
		
		if(trailtype == TrailType.ORBIT_TRAIL){
			pa.sendOposite(Bukkit.getOnlinePlayers());
			
			if(trail == Trail.SNOW){
				pa.setEnumParticle(EnumParticle.SNOWBALL);
				pa.sendOposite(Bukkit.getOnlinePlayers());
				pa.setEnumParticle(EnumParticle.SNOW_SHOVEL);
			}
		}
		
		if(trailtype == TrailType.CYLINDER_TRAIL){
			pa.setYAdded(1.75 -(0.25 * cylinderyadded));
			pa.send(Bukkit.getOnlinePlayers());
			
			if(trail == Trail.SNOW){
				pa.setEnumParticle(EnumParticle.SNOWBALL);
				pa.sendOposite(Bukkit.getOnlinePlayers());
				pa.setEnumParticle(EnumParticle.SNOW_SHOVEL);
			}
			pa.setYAdded(cylinderyadded);
		}
	}
	public void parseCylinderTrail(Trail trail){
		Particle pa = new Particle(trail.getEnumParticle(), getPlayer().getLocation());
		
		for(int i = 0; i < 120; i++){
			double cylinderyadded = pa.getYAdded();
			
			pa.setSize(0, 0, 0);
			
			if(pa.getIndex() > 30){
				pa.setIndex(0);
				cylinderyadded = cylinderyadded +1;
			}
			else{
				pa.setIndex(pa.getIndex() +1);
			}
			if(cylinderyadded > 7){
				cylinderyadded = 0;
			}
			
			pa.setYAdded(0);
			pa.addY(0.2 * pa.getIndex());
			pa.setXAdded(1.1 * Math.cos(pa.getYAdded()));
			pa.setZAdded(1.1 * Math.sin(pa.getYAdded()));
	
			pa.setYAdded(0.25 * cylinderyadded);
			
			pa.setEnumParticle(trail.getEnumParticle());
			pa.setAmount(1);
			
			pa.send(Bukkit.getOnlinePlayers());
			if(trail == Trail.SNOW){
				pa.setEnumParticle(EnumParticle.SNOWBALL);
				pa.send(Bukkit.getOnlinePlayers());
				pa.setEnumParticle(EnumParticle.SNOW_SHOVEL);
			}
			
			pa.setYAdded(1.75 -(0.25 * cylinderyadded));
			pa.send(Bukkit.getOnlinePlayers());
			
			if(trail == Trail.SNOW){
				pa.setEnumParticle(EnumParticle.SNOWBALL);
				pa.sendOposite(Bukkit.getOnlinePlayers());
				pa.setEnumParticle(EnumParticle.SNOW_SHOVEL);
			}
			pa.setYAdded(cylinderyadded);
		}
	}
	
	public boolean canParticlePlayNext(){
		return particleplaynext;
	}
	public void setParticlePlayNext(boolean particleplaynext){
		this.particleplaynext = particleplaynext;
	}
	
	public int getTrailPA(){
		return trailparticlesamount;
	}
	public void setTrailPA(int trailparticlesamount){
		this.trailparticlesamount = trailparticlesamount;
		
		getPlayer().playSound(getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
		getPlayer().sendMessage("§7Set your §fTrail's §7§lParticle Amount§7 to §f§l" + trailparticlesamount + "§7.");
	}
	public void addTrailPA(){
		int amount = this.trailparticlesamount+1;
		if(amount == 6){
			amount = 1;
		}
		this.trailparticlesamount = amount;
		
		getPlayer().playSound(getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
		getPlayer().sendMessage("§7Set your §fTrail's §7§lParticle Amount§7 to §f§l" + amount + "§7.");
	}

	public boolean hasSpecialTrail(){
		return specialtrail;
	}
	public void setSpecialTrail(boolean specialtrail){
		this.specialtrail = specialtrail;

		getPlayer().playSound(getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
		getPlayer().sendMessage("" + Utils.statusString(specialtrail) + " §7your §7§lSpecial Trail§7.");
	}

	public boolean hasUnlockedSpecialTrail(){
		return unlockedspecialtrail;
	}
	public void setUnlockedSpecialTrail(boolean unlockedspecialtrail){
		this.unlockedspecialtrail = unlockedspecialtrail;
		
		if(unlockedspecialtrail && !Database.get().containsPath("Trails-TypeSpecial", "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Trails-TypeSpecial", "uuid`, `special", getUUID().toString() + "', '" + false);
		}
	}

	public List<TrailType> getTrailTypes(){
		return trailtypes;
	}
	public void setTrailTypes(List<TrailType> trailtypes){
		this.trailtypes = trailtypes;
	}
	public void addTrailType(TrailType trailtype){
		this.trailtypes.add(trailtype);
		
		if(!Database.get().containsPath("Trails-" + trailtype.getDatabaseName(), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Trails-" + trailtype.getDatabaseName(), "uuid", getUUID().toString());
		}
	}
	public boolean hasTrailType(TrailType trailtype){
		return this.trailtypes.contains(trailtype);
	}

	public List<Color> getWardrobe(){
		return wardrobe;
	}
	public void setWardrobe(List<Color> wardrobe){
		this.wardrobe = wardrobe;
	}
	public void addWardrobe(Color wardrobe){
		this.wardrobe.add(wardrobe);
		
		if(!Database.get().containsPath("Wardrobe-" + ColorUtils.getDatabaseName(wardrobe), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Wardrobe-" + ColorUtils.getDatabaseName(wardrobe), "uuid", getUUID().toString());
		}
	}
	public boolean hasWardrobe(Color color){
		return wardrobe.contains(color);
	}
	
	public boolean hasUnlockedWardrobeDisco(){
		return unlockedwardrobedisco;
	}
	public void setUnlockedWardrobeDisco(boolean unlockedwardrobedisco){
		this.unlockedwardrobedisco = unlockedwardrobedisco;
		
		if(unlockedwardrobedisco && !Database.get().containsPath("Wardrobe-Disco", "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Wardrobe-Disco", "uuid`, `disco", getUUID().toString() + "', '" + false);
		}
	}

	public boolean isWardrobeDisco(){
		return wardrobedisco;
	}
	public void setWardrobeDisco(boolean wardrobedisco){
		if(wardrobedisco){
			if(hasWardrobeEnabled() && !this.wardrobedisco){
				disableWardrobe();
			}

			getPlayer().playSound(getPlayer().getLocation(), Sound.ANVIL_LAND, 5, 1);
			getPlayer().sendMessage("§a§lENABLED §7your " + ColorUtils.getColor(ColorUtils.random(ColorUtils.getWardrobe())) + "Disco Armor§7.");
			discoWardrobe();
		}
		
		this.wardrobedisco = wardrobedisco;
	}

	public int getFireworkPasses(){
		return fireworkpasses;
	}
	public void setFireworkPasses(int fireworkpasses){
		this.fireworkpasses = fireworkpasses;
	}
	public void addFireworkPasses(int fireworkpasses){
		this.fireworkpasses += fireworkpasses;
	}
	public void removeFireworkPass(){
		this.fireworkpasses--;
	}

	public FireworkSettings getFWSettings(){
		return fwsettings;
	}
	public void setFWSettings(FireworkSettings fwsettings){
		this.fwsettings = fwsettings;
	}

	public String getNickname(){
		return nickname;
	}
	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public boolean hasPlayersEnabled(){
		return hubplayersenabled;
	}
	public void setPlayersEnabled(boolean hubplayersenabled){
		this.hubplayersenabled = hubplayersenabled;
	}

	public boolean hasScoreboardEnabled(){
		return hubscoreboardenabled;
	}
	public void setScoreboardEnabled(boolean hubscoreboardenabled){
		this.hubscoreboardenabled = hubscoreboardenabled;
		
		if(!hubscoreboardenabled){
			getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}

	public boolean hasStackerEnabled(){
		return hubstackerenabled;
	}
	public void setStackerEnabled(boolean hubstackerenabled){
		this.hubstackerenabled = hubstackerenabled;
	}

	public boolean CompletedLapisParkour(){
		return completedlapisparkour;
	}
	public void setCompletedLapisParkour(boolean completedlapisparkour){
		this.completedlapisparkour = completedlapisparkour;
	}

	public boolean isInLapisParkour(){
		return inlapisparkour;
	}
	public void setInLapisParkour(boolean inlapisparkour){
		this.inlapisparkour = inlapisparkour;
	}
	public void joinLapisParkour(){
		setInLapisParkour(true);
		Player p = getPlayer();
		
		if(getPetEnabled() != null){
			disablePet();
		}
		for(PotionEffect eff : p.getActivePotionEffects()){
			p.removePotionEffect(eff.getType());
		}					
		if(p.getVehicle() != null){
			p.leaveVehicle();
		}
		
		p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		p.sendMessage("");
		p.sendMessage("§1§lLapis Parkour");
		p.sendMessage(" §7- You can't stop sprinting.");
		p.sendMessage(" §7- Reward: §b§l250 VIP Points§7.");
		p.sendMessage("");
		p.sendMessage("§f§lGood Luck!");
		
		Title t = new Title("§1§lLapis Parkour", "§7You can't stop sprinting!");
		t.send(p);
	}
	public void leaveLapisParkour(){
		setInLapisParkour(false);
		getPlayer().sendMessage("§1§lLapis Parkour §8| §7You left the Parkour. Try again an other time!");
	}

	public boolean isInMindCraft(){
		return inmindcraft;
	}
	public void setInMindCraft(boolean inmindcraft){
		this.inmindcraft = inmindcraft;
	}

	public boolean hasReceivedMonthlyBonus(){
		return receivedmonthlybonus;
	}
	public void setReceivedMonthlyBonus(boolean receivedmonthlybonus){
		this.receivedmonthlybonus = receivedmonthlybonus;
		
		if(receivedmonthlybonus == true){
			if(!Database.get().containsPath("PlayerMonthlyVIPPoints", "uuid", "uuid", getUUID().toString())){
				Database.get().insert("PlayerMonthlyVIPPoints", "uuid", getUUID().toString());
			}
		}
		else{
			if(Database.get().containsPath("PlayerMonthlyVIPPoints", "uuid", "uuid", getUUID().toString())){
				Database.get().delete("PlayerMonthlyVIPPoints", "uuid", getUUID().toString());
			}
		}
	}

	public HashMap<Cooldown, Long> getCooldowns(){
		return cooldowns;
	}
	public void setCooldowns(HashMap<Cooldown, Long> cooldowns){
		this.cooldowns = cooldowns;
	}
	public long getCooldown(Cooldown cooldown){
		if(this.cooldowns.containsKey(cooldown)){
			return this.cooldowns.get(cooldown);
		}
		return -1;
	}
	public void resetCooldown(Cooldown cooldown){
		this.cooldowns.put(cooldown, System.currentTimeMillis());
		Cooldown.prevdouble.remove(this);
		
		if(cooldown == Cooldown.TELEPORTING){
			this.lastparticle = null;
		}
	}
	public void removeCooldown(Cooldown cooldown){
		this.cooldowns.remove(cooldown);
		Cooldown.prevdouble.remove(this);
	}
	public boolean onCooldown(Cooldown cooldown){
		if(this.cooldowns.containsKey(cooldown)){
			if(System.currentTimeMillis() - this.cooldowns.get(cooldown) >= cooldown.getCooldown(this)){
				return false;
			}
			return true;
		}
		return false;
	}
	public void updateCooldownActionBar(){
		for(Cooldown cooldown : this.cooldowns.keySet()){
			cooldown.updateActionBar(this);
		}
	}

	public int getVotes(){
		return votes;
	}
	public void setVotes(int votes){
		this.votes = votes;
	}
	public void addVote(){
		this.votes += 1;
	}
	public boolean hasVotes(int votes){
		return this.votes >= votes;
	}
	public void updateVotes(){
		this.votes = Database.get().getInt("Votes", "votes", "uuid", getUUID().toString());
	}

	public int getVIPPoints(){
		return vippoints;
	}
	public void setVIPPoints(int vippoints){
		this.vippoints = vippoints;
		
		Database.get().update("VIPPoints", "points", "" + getVIPPoints(), "uuid", getUUID().toString());
	}
	public void addVIPPoints(int vippoints){
		this.vippoints += vippoints;
		
		Database.get().update("VIPPoints", "points", "" + getVIPPoints(), "uuid", getUUID().toString());
		
		Title t = new Title("", "§b+" + vippoints + " VIP Points");
		t.send(getPlayer());
	}
	public void removeVIPPoints(int vippoints){
		this.vippoints -= vippoints;
		
		Database.get().update("VIPPoints", "points", "" + getVIPPoints(), "uuid", getUUID().toString());
	}
	public boolean hasVIPPoints(int vippoints){
		return this.vippoints >= vippoints;
	}

	public int getOrbitMinesTokens(){
		return orbitminestokens;
	}
	public void setOrbitMinesTokens(int orbitminestokens){
		this.orbitminestokens = orbitminestokens;
		
		Database.get().update("OrbitMinesTokens", "omt", "" + getOrbitMinesTokens(), "uuid", getUUID().toString());
	}
	public void addOrbitMinesTokens(int orbitminestokens){
		this.orbitminestokens += orbitminestokens;
		
		Database.get().update("OrbitMinesTokens", "omt", "" + getOrbitMinesTokens(), "uuid", getUUID().toString());
	}
	public void removeOrbitMinesTokens(int orbitminestokens){
		this.orbitminestokens -= orbitminestokens;
		
		Database.get().update("OrbitMinesTokens", "omt", "" + getOrbitMinesTokens(), "uuid", getUUID().toString());
	}
	public boolean hasOrbitMinesTokens(int orbitminestokens){
		return this.orbitminestokens >= orbitminestokens;
	}

	public int getMiniGameCoins(){
		return minigamecoins;
	}
	public void setMiniGameCoins(int minigamecoins){
		this.minigamecoins = minigamecoins;
	}
	public void addMiniGameCoins(int minigamecoins){
		this.minigamecoins += minigamecoins;
		
		Database.get().update("MiniGameCoins", "coins", "" + getMiniGameCoins(), "uuid", getUUID().toString());
	}
	public void removeMiniGameCoins(int minigamecoins){
		this.minigamecoins -= minigamecoins;
		
		Database.get().update("MiniGameCoins", "coins", "" + getMiniGameCoins(), "uuid", getUUID().toString());
	}
	public boolean hasMiniGameCoins(int minigamecoins){
		return this.minigamecoins >= minigamecoins;
	}

	public String getChatFormat(){
		if(getNickname() != null){
			return getPrefix() + getChatPrefix() + getRankColor() + "*" + getNickname() + getRankColor() + "*§7 » " + getChatColor().getColor() + getBoldString() + getCursiveString() + "%2$s";
		}
		return getPrefix() + getName() + "§7 » " + getChatColor().getColor() + getBoldString() + getCursiveString() + "%2$s";
	}
	
	public String getRankColor(){
		if(getStaffRank() != StaffRank.User){
			return getStaffRank().getColor();
		}
		else{
			return getVIPRank().getColor();
		}
	}
	
	public String getColorName(){
		return getRankColor() + getPlayer().getName();
	}
	
	public String getChatPrefix(){
		if(getStaffRank() != StaffRank.User){
			return getStaffRank().getChatPrefix();
		}
		else{
			return getVIPRank().getChatPrefix();
		}
	}
	
	public String getScoreboardPrefix(){
		if(getStaffRank() != StaffRank.User){
			return getStaffRank().getScoreboardPrefix();
		}
		else{
			return getVIPRank().getScoreboardPrefix();
		}
	}
	
	public String getRankString(){
		if(getStaffRank() != StaffRank.User){
			return getStaffRank().getRankString();
		}
		else{
			return getVIPRank().getRankString();
		}
	}
	
	public String getName(){
		return getChatPrefix() + getPlayer().getName();
	}
	
	public void setScoreboard(){
		api.getScoreboardManager().requestUpdate(getPlayer());
	}
	
	public boolean isNewPlayer() {
		return newplayer;
	}
	
	public void setTabList(String header, String footer){
        IChatBaseComponent tab1 = ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent tab2 = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter pack = new PacketPlayOutPlayerListHeaderFooter(tab1);
 
        try{
            Field field = pack.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(pack, tab2);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        
        ((CraftPlayer) getPlayer()).getHandle().playerConnection.sendPacket(pack);
	}
	
	public void notLoaded(){
		Player p = getPlayer();
		Server server = api.server();
		p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		p.sendMessage(server.getName() + " §8| §7This Server just " + server.getColor() + "restarted§7! Wait a few " + server.getColor() + "seconds§7.");
	}
	
	public boolean hasWardrobeEnabled(){
		return getPlayer().getInventory().getChestplate() != null;
	}
	public void disableWardrobe(){	
		Player p = getPlayer();
		
		p.sendMessage("§c§lDISABLED §7your " + p.getInventory().getChestplate().getItemMeta().getDisplayName() +"§7!");
	
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		
		setWardrobeDisco(false);
	}
	
	public void discoWardrobe(){
		Player p = getPlayer();
		Color color = ColorUtils.random(getWardrobe());
		String c = ColorUtils.getColor(color);
		
		p.getInventory().setChestplate(ItemUtils.setDisplayname(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), color), c + "Disco Armor"));
		p.getInventory().setLeggings(ItemUtils.setDisplayname(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), color), c + "Disco Armor"));
		p.getInventory().setBoots(ItemUtils.setDisplayname(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), color), c + "Disco Armor"));
	}
	
	public void wardrobe(Color color){
		Player p = getPlayer();
		String c = ColorUtils.getName(color);
		
		if(hasWardrobeEnabled()){
			disableWardrobe();
		}

		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);
		p.sendMessage("§a§lENABLED §7your " + ColorUtils.getName(color) +" Armor§7.");
		
		p.getInventory().setChestplate(ItemUtils.setDisplayname(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), color), c + " Armor"));
		p.getInventory().setLeggings(ItemUtils.setDisplayname(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), color), c + " Armor"));
		p.getInventory().setBoots(ItemUtils.setDisplayname(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), color), c + " Armor"));
	}
	public void wardrobe(VIPRank viprank){
		Player p = getPlayer();
		
		if(hasWardrobeEnabled()){
			disableWardrobe();
		}
		
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5, 1);

		switch(viprank){
			case Emerald_VIP:
				p.sendMessage("§a§lENABLED §7your §7Chainmail Armor§7.");
				
				p.getInventory().setChestplate(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "§7Chainmail Armor"));
				p.getInventory().setLeggings(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), "§7Chainmail Armor"));
				p.getInventory().setBoots(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS, 1), "§7Chainmail Armor"));
				break;
			case Diamond_VIP:
				p.sendMessage("§a§lENABLED §7your §bDiamond Armor§7.");
				
				p.getInventory().setChestplate(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE, 1), "§bDiamond Armor"));
				p.getInventory().setLeggings(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_LEGGINGS, 1), "§bDiamond Armor"));
				p.getInventory().setBoots(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_BOOTS, 1), "§bDiamond Armor"));
				break;
			case Gold_VIP:
				p.sendMessage("§a§lENABLED §7your §6Gold Armor§7.");
				
				p.getInventory().setChestplate(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE, 1), "§6Gold Armor"));
				p.getInventory().setLeggings(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS, 1), "§6Gold Armor"));
				p.getInventory().setBoots(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS, 1), "§6Gold Armor"));
				break;
			case Iron_VIP:
				p.sendMessage("§a§lENABLED §7your §7Iron Armor§7.");
				
				p.getInventory().setChestplate(ItemUtils.setDisplayname(new ItemStack(Material.IRON_CHESTPLATE, 1), "§7Iron Armor"));
				p.getInventory().setLeggings(ItemUtils.setDisplayname(new ItemStack(Material.IRON_LEGGINGS, 1), "§7Iron Armor"));
				p.getInventory().setBoots(ItemUtils.setDisplayname(new ItemStack(Material.IRON_BOOTS, 1), "§7Iron Armor"));
				break;
			case User:
				break;
			default:
				break;
		}
	}
	
	public void disableTrail(){
		getPlayer().sendMessage("§c§lDISABLED §7your " + getTrail().getName() + "§7!");
		setTrail(null);
	}
	
	public boolean hasHatEnabled(){
		ItemStack helmet = getPlayer().getInventory().getHelmet();
		return helmet != null && helmet.getType() != Material.LEATHER_HELMET && helmet.getType() != Material.GOLD_HELMET && helmet.getType() != Material.IRON_HELMET && helmet.getType() != Material.DIAMOND_HELMET && helmet.getType() != Material.CHAINMAIL_HELMET;
	}
	public void disableHat(){
		getPlayer().sendMessage("§c§lDISABLED §7your " + getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName() + "§7!");
		getPlayer().getInventory().setHelmet(null);
	}

	public void enableGadget(Gadget gadget){
		getPlayer().playSound(getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);

		ItemStack item = new ItemStack(gadget.getMaterial(), 1);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(gadget.getName().replace("§l", "§n"));
		item.setItemMeta(itemmeta);
		item.setDurability(gadget.getDurability());
		getPlayer().getInventory().setItem(api.getGadgetSlot(), new ItemStack(ItemUtils.hideFlags(ItemUtils.setUnbreakable(item), 4)));
		
		getPlayer().sendMessage("§a§lENABLED §7your " + gadget.getName() + "§7.");
	}
	
	public void disableGadget(){
		ItemStack item = getPlayer().getInventory().getItem(api.getGadgetSlot());
		if(item != null){
			String displayname = "Gadget";
			if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
				displayname = item.getItemMeta().getDisplayName();
			}
			getPlayer().sendMessage("§c§lDISABLED §7your " + displayname.replace("§n", "§l") + "§7!");
			getPlayer().getInventory().setItem(api.getGadgetSlot(), null);
		}
	}
	
	public void giveFireworkGun(){
		Player p = getPlayer();
		
		p.closeInventory();
		p.playSound(p.getLocation(), Sound.ANVIL_USE, 5, 1);
		
		ItemStack i = new ItemStack(Material.FIREBALL, 1);
		ItemMeta imeta = i.getItemMeta();
		imeta.setDisplayName("§c§nFirework Gun§r §c(§6" + getFireworkPasses() + "§c)");
		i.setItemMeta(imeta);
		p.getInventory().setItem(api.getGadgetSlot(), new ItemStack(i));

		p.sendMessage("§a§lENABLED §7your §c§lFirework Gun§7.");
	}
	
	public void disablePet(){
		Pet pet = getPetEnabled();
		getPlayer().sendMessage("§c§lDISABLED §f" + getPetName(pet) + "§7!");
		
		Entity en = getPet();
		if(en instanceof LivingEntity){
			en.remove();
		}
		if(hasPetShroomTrail()){
			setPetShroomTrail(false);
		}
		if(hasPetBabyPigs()){
			for(Entity ent : getPetBabyPigEntities()){
				ent.remove();
			}
			setPetBabyPigEntities(null);
			setPetBabyPigs(false);
		}
		if(hasPetSheepDisco()){
			setPetSheepDisco(false);
		}
		
		Pet.pets.remove(en);
		
		setPet(null);
		setPetEnabled(null);
	}
	public void spawnPet(Pet pet){
		pet.spawn(this);
		getPlayer().sendMessage("§a§lENABLED §7your §f" + pet.getName() + "§7.");
	}
	
	public void hide(){
		for(Player player : Bukkit.getOnlinePlayers()){
			player.hidePlayer(getPlayer());
		}
	}
	
	public void show(){
		for(Player player : Bukkit.getOnlinePlayers()){
			player.showPlayer(getPlayer());
		}
	}
	
	public void hidePlayers(){
		for(Player player : Bukkit.getOnlinePlayers()){
			getPlayer().hidePlayer(player);
		}
	}
	
	public void showPlayers(){
		for(Player player : Bukkit.getOnlinePlayers()){
			getPlayer().showPlayer(player);
			
			OMPlayer omplayer = OMPlayer.getOMPlayer(player);
			if(omplayer.isDisguised()){
				if(omplayer.getDisguiseEntityType() != null){
					omplayer.disguiseAsMob(omplayer.getDisguiseEntityType(), isDisguisedBaby(), getPlayer());
				}
				else{
					omplayer.disguiseAsBlock(omplayer.getDisguiseBlockID(), getPlayer());
				}
			}
		}
	}
	
	public void clearInventory(){
		getPlayer().getInventory().clear();
		getPlayer().getInventory().setHelmet(null);
		getPlayer().getInventory().setChestplate(null);
		getPlayer().getInventory().setLeggings(null);
		getPlayer().getInventory().setBoots(null);
	}
	
	public void clearPotionEffects(){
	    for(PotionEffect effect : getPlayer().getActivePotionEffects()){
	        getPlayer().removePotionEffect(effect.getType());
	    }
	}
	public void clearPotionEffect(PotionEffect effect){
		getPlayer().removePotionEffect(effect.getType());
	}
	public void addPotionEffect(PotionEffectType effecttype, int seconds, int amplifier){
		getPlayer().addPotionEffect(new PotionEffect(effecttype, seconds * 20, amplifier));
	}
	
	public void clearLevels(){
		getPlayer().setLevel(0);
		getPlayer().setExp(0F);
	}
	
	public void updateInventory(){
		new BukkitRunnable(){
			public void run(){
				getPlayer().getInventory().setArmorContents(getPlayer().getInventory().getArmorContents());
				getPlayer().updateInventory();
			}
		}.runTaskLater(api, 1);
	}
	
	@SuppressWarnings("deprecation")
	public void givePetInventory(){
		Player p = getPlayer();
		p.getInventory().clear();
		
		switch(getPetEnabled()){
			case CHICKEN:
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.EGG), "§7§nEgg Bomb"));
				
				int chickenage = 1;
				if(((Chicken) getPet()).isAdult()){
					chickenage = 2;
				}
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.RAW_CHICKEN, chickenage), "§c§nChange Age"));
				break;
			case COW:
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.MILK_BUCKET), "§f§nMilk Explosion"));
				
				int cowage = 1;
				if(((Cow) getPet()).isAdult()){
					cowage = 2;
				}
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.RAW_BEEF, cowage), "§c§nChange Age"));
				break;
			case CREEPER:
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.TNT), "§c§nExplode"));
				
				Creeper creeper = (Creeper) getPet();
				if(creeper.isPowered()){
					p.getInventory().setItem(6, ItemUtils.setItem(new ItemStack(Material.MONSTER_EGG), "§a§nChange Type§7 (§e§lLIGHTNING§7)", 50));
				}
				else{
					p.getInventory().setItem(6, ItemUtils.setItem(new ItemStack(Material.MONSTER_EGG), "§a§nChange Type§7 (§6§lNORMAL§7)", 50));
				}
				break;
			case HORSE:
				Horse h = (Horse) getPet();
				int speed = 1;
				
				AttributeInstance currentSpeed = ((EntityInsentient) ((CraftLivingEntity) h).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
				if(currentSpeed.b() == 0.25){speed = 1;}
				else if(currentSpeed.b() == 0.5){speed = 2;}
				else if(currentSpeed.b() == 0.75){speed = 3;}
				else{}
				
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.FEATHER, speed), "§f§nChange Speed"));
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.LEATHER), "§e§nChange Color"));
				break;
			case MAGMA_CUBE:
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.FIREBALL), "§6§nFireball"));
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.MAGMA_CREAM, ((MagmaCube) getPet()).getSize()), "§c§nChange Size"));
				break;
			case MUSHROOM_COW:
				if(hasPetShroomTrail() == true){
					p.getInventory().setItem(2, ItemUtils.setItem(new ItemStack(Material.HUGE_MUSHROOM_2), "§4§nShroom Trail§7 (§a§lENABLED§7)", 14));
				}
				else{
					p.getInventory().setItem(2, ItemUtils.setItem(new ItemStack(Material.HUGE_MUSHROOM_1), "§4§nShroom Trail§7 (§c§lDISABLED§7)", 14));
				}
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.FIREWORK), "§c§nBaby Firework"));
				break;
			case OCELOT:
				p.getInventory().setItem(2, ItemUtils.setItem(new ItemStack(Material.MONSTER_EGG), "§e§nKitty Cannon", 98));
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.RAW_FISH), "§9§nChange Color"));
				break;
			case PIG:
				if(hasPetBabyPigs() == true){
					p.getInventory().setItem(2, ItemUtils.setItem(new ItemStack(Material.MONSTER_EGG), "§d§nBaby Pigs§7 (§a§lENABLED§7)", 90));
				}
				else{
					p.getInventory().setItem(2, ItemUtils.setItem(new ItemStack(Material.MONSTER_EGG), "§d§nBaby Pigs§7 (§c§lDISABLED§7)", 90));
				}
				
				int pigage = 1;
				if(((Pig) getPet()).isAdult()){
					pigage = 2;
				}
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.PORK, pigage), "§d§nChange Age"));
				break;
			case SHEEP:
				if(hasPetSheepDisco() == true){
					p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.WOOL), "§f§nSheep Disco§7 (§a§lENABLED§7)"));
				}
				else{
					p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.WOOL), "§f§nSheep Disco§7 (§c§lDISABLED§7)"));
				}
				
				DyeColor dyecolor = ((Sheep) getPet()).getColor();
				p.getInventory().setItem(6, ItemUtils.setItem(new ItemStack(Material.INK_SACK), "§f§nChange Color§7 (" + ColorUtils.getName(dyecolor) + "§7)", dyecolor.getDyeData()));
				break;
			case SILVERFISH:
				p.getInventory().setItem(2, ItemUtils.setItem(new ItemStack(Material.MONSTER_EGG), "§7§nSilverfish Bomb", 60));
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.STONE_HOE), "§8§nLeap"));
				break;
			case SLIME:
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§6§nSuper Jump"));
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.SLIME_BALL, ((Slime) getPet()).getSize()), "§a§nChange Size"));
				break;
			case SPIDER:
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.WEB), "§f§nWebs"));
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.SPIDER_EYE), "§5§nSpider Launcher"));
				break;
			case SQUID:
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.INK_SACK), "§8§nInk Bomb"));
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.WATER_BUCKET), "§9§nWater Spout"));
				break;
			case WOLF:
				p.getInventory().setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.COOKED_BEEF), "§6§nBark"));
				
				int wolfage = 1;
				if(((Wolf) getPet()).isAdult()){
					wolfage = 2;
				}
				p.getInventory().setItem(6, ItemUtils.setDisplayname(new ItemStack(Material.BONE, wolfage), "§7§nChange Age"));
				break;
			default:
				break;
		}
	}
	
	public void setVIP(VIPRank viprank){
		if(Database.get().containsPath("Rank-VIP", "uuid", "uuid", getUUID().toString())){
			Database.get().update("Rank-VIP", "vip", viprank.toString(), "uuid", getUUID().toString());
		}
		else{
			Database.get().insert("Rank-VIP", "uuid`, `vip", getUUID().toString() + "', '" + viprank.toString());
		}
		
		setVIPRank(viprank);
		
		String subtitle = "";
		
		switch(viprank){
			case Emerald_VIP:
				subtitle = "§7You are now an §a§lEmerald VIP§7!";
				break;
			case Diamond_VIP:
				subtitle = "§7You are now a §b§lDiamond VIP§7!";
				break;
			case Gold_VIP:
				subtitle = "§7You are now a §6§lGold VIP§7!";
				break;
			case Iron_VIP:
				subtitle = "§7You are now an §7§lIron VIP§7!";
				break;
			case User:
				break;
			default:
				break;
		}
		
		Title t = new Title("", subtitle);
		t.send(getPlayer());
	}
	
	public void setStaff(StaffRank staffrank){
		if(Database.get().containsPath("Rank-Staff", "uuid", "uuid", getUUID().toString())){
			Database.get().update("Rank-Staff", "staff", viprank.toString(), "uuid", getUUID().toString());
		}
		else{
			Database.get().insert("Rank-Staff", "uuid`, `staff", getUUID().toString() + "', '" + viprank.toString());
		}
		
		setStaffRank(staffrank);
		
		String subtitle = "";
		
		switch(staffrank){
			case Builder:
				subtitle = "§7You are now a §d§lBuilder§7!";
			case Moderator:
				subtitle = "§7You are now a §b§lModerator§7!";
			case Owner:
				subtitle = "§7You are now an §4§lOwner§7!";
			case User:
				break;
			default:
				break;
		}
		
		Title t = new Title("", subtitle);
		t.send(getPlayer());
	}
	
    public void toServer(Server server){
	    if(server.isOnline()){
			if(server == Server.MINIGAMES){
				server = Server.HUB;
				toMiniGameArea();
			}
			
			getPlayer().sendMessage("§7Connecting to " + server.getName() + "§7...");

	        ByteArrayOutputStream b = new ByteArrayOutputStream();
	        DataOutputStream out = new DataOutputStream(b);
	 
	        try{
	            out.writeUTF("Connect");
	            out.writeUTF(server.toString().toLowerCase());
	        }catch(IOException eee){}
	 
	        getPlayer().sendPluginMessage(api, "BungeeCord", b.toByteArray());
    	}
    	else{
			getPlayer().sendMessage("§7The " + server.getName() + "§7 Server is §4§lOffline§7!");
    	}
    }
    
	public void toMiniGameArea(){
    	ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
 
        try{
        	out.writeUTF("Forward");
    		out.writeUTF("hub");
    		out.writeUTF("MGArea");
        	
        	out.writeUTF(getPlayer().getName());
    	}catch(IOException ex){
    		ex.printStackTrace();
    	}
    	
		getPlayer().sendPluginMessage(api, "BungeeCord", b.toByteArray());
    }
    
	public void toMiniGame(MGArena arena){
        {
    		ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            
	        try{
	        	out.writeUTF("Forward");
	        	out.writeUTF("ALL");
				out.writeUTF(arena.getType().getShortName());
	    	
	    		out.writeUTF(arena.getArenaID() + "|" + getPlayer().getName());
	    	}catch(IOException ex){
	    		ex.printStackTrace();
	    	}
	    	
			getPlayer().sendPluginMessage(api, "BungeeCord", b.toByteArray());
        }
		
		{
	        ByteArrayOutputStream b = new ByteArrayOutputStream();
	        DataOutputStream out = new DataOutputStream(b);
	 
	        try{
	            out.writeUTF("Connect");
	            out.writeUTF(Server.MINIGAMES.toString().toLowerCase());
	        }catch(IOException eee){}
	 
	        getPlayer().sendPluginMessage(api, "BungeeCord", b.toByteArray());
		}
	}
	
	public void sendTimeMessage(int seconds, final String message, final Sound sound){
		new BukkitRunnable(){
			public void run(){
				Player p = getPlayer();
				p.sendMessage(message);
				
				if(sound != null){
					p.playSound(p.getLocation(), sound, 5, 1);
				}
			}
		}.runTaskLater(api, seconds * 20);
	}
	public void sendTimeMessages(int seconds, final List<String> messages, final Sound sound){
		new BukkitRunnable(){
			public void run(){
				Player p = getPlayer();
				for(String message : messages){
					p.sendMessage(message);
				}
				
				if(sound != null){
					p.playSound(p.getLocation(), sound, 5, 1);
				}
			}
		}.runTaskLater(api, seconds * 20);
	}
	
	public boolean hasPerms(StaffRank rank){
		StaffRank staffrank = getStaffRank();
		
		if(staffrank == StaffRank.Owner){
			return true;
		}
		
		if(rank == StaffRank.User){
			if(staffrank == StaffRank.User || staffrank == StaffRank.Builder || staffrank == StaffRank.Moderator){
				return true;
			}
		}
		else if(rank == StaffRank.Builder){
			if(staffrank == StaffRank.Builder || staffrank == StaffRank.Moderator){
				return true;
			}
		}
		else if(rank == StaffRank.Moderator){
			if(staffrank == StaffRank.Moderator){
				return true;
			}
		}
		else{
			return false;
		}
		return false;
	}
	
	public boolean hasPerms(VIPRank rank){
		VIPRank viprank = getVIPRank();
		
		if(getStaffRank() == StaffRank.Owner && isOpMode()){
			return true;
		}
		
		if(rank == VIPRank.Iron_VIP){
			if(viprank == VIPRank.Iron_VIP || viprank == VIPRank.Gold_VIP || viprank == VIPRank.Diamond_VIP || viprank == VIPRank.Emerald_VIP){
				return true;
			}
		}
		else if(rank == VIPRank.Gold_VIP){
			if(viprank == VIPRank.Gold_VIP || viprank == VIPRank.Diamond_VIP || viprank == VIPRank.Emerald_VIP){
				return true;
			}
		}
		else if(rank == VIPRank.Diamond_VIP){
			if(viprank == VIPRank.Diamond_VIP || viprank == VIPRank.Emerald_VIP){
				return true;
			}
		}
		else if(rank == VIPRank.Emerald_VIP){
			if(viprank == VIPRank.Emerald_VIP){
				return true;
			}
		}
		else{
			return true;
		}
		return false;
	}
	
	public void unknownCommand(String command){
		getPlayer().sendMessage("§7Unknown command (§6" + command + "§7). Use §6/help§7 for help.");
	}
	
	public void requiredVIPRank(VIPRank viprank){
		Player p = getPlayer();
		String a = "a";
		if(viprank == VIPRank.Iron_VIP || viprank == VIPRank.Emerald_VIP){a = "an";}
		
		p.sendMessage("§7You have to be " + a + " " + viprank.getRankString() + " VIP§7 to do this!");
		p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
	}
	
	public void requiredVIPPoints(int price){
		Player p = getPlayer();
		
		p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
		int needed = price - getVIPPoints();
		if(needed == 1){
			p.sendMessage("§7You need §b§l" + needed + "§7 more §b§lVIP Point§7!");
		}
		else{
			p.sendMessage("§7You need §b§l" + needed + "§7 more §b§lVIP Points§7!");
		}
	}
	public void requiredOMT(int price){
		Player p = getPlayer();
		
		p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
		int needed = price - getOrbitMinesTokens();
		if(needed == 1){
			p.sendMessage("§7You need §e§l" + needed + "§7 more §e§lOrbitMines Token§7!");
		}
		else{
			p.sendMessage("§7You need §e§l" + needed + "§7 more §e§lOrbitMines Tokens§7!");
		}
	}
	public void requiredMiniGameCoins(int price){
		Player p = getPlayer();
		
		p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
		int needed = price - getMiniGameCoins();
		if(needed == 1){
			p.sendMessage("§7You need §f§l" + needed + "§7 more §f§lCoin§7!");
		}
		else{
			p.sendMessage("§7You need §f§l" + needed + "§7 more §f§lCoins§7!");
		}
	}
	
	public void logOut(){
		getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		if(isDisguised()){
			undisguise();
		}
		if(getPetEnabled() != null){
			disablePet();
		}
		if(getSoccerMagmaCube() != null){
			disableSoccerMagmaCube();
		}
		
		unloadPlayerData();
		
		String uuid = getUUID().toString();
		updateChatColors(uuid);
		updateNickname(uuid);
		updateTrails(uuid);
		
		sendQuitMessage();
		
		api.getOMPlayers().remove(this);
	}
	
	public void disguiseAsBlock(int id, Player... players){
		Player p = getPlayer();
		
		EntityFallingBlock disguise = new EntityFallingBlock(((CraftPlayer) p).getHandle().world, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), new BlockLog1().getBlockData());
		disguise.locX = p.getLocation().getX();
        disguise.locY = p.getLocation().getY();
        disguise.locZ = p.getLocation().getZ();
        disguise.yaw = p.getLocation().getYaw();
        disguise.pitch = p.getLocation().getPitch();
        disguise.d(((CraftPlayer) p).getHandle().getId());
        
        setDisguised(true);
        setDisguiseBlockID(id);
        
        for(Player player : players){
        	if(player != p){
        		OMPlayer omplayer = OMPlayer.getOMPlayer(player);
        		if(omplayer.hasPlayersEnabled()){
        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy((((CraftPlayer) p).getHandle().getId())));
        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntity(disguise, 70, id));
        		}
        	}
        }
	}
	
	public void disguiseAsMob(EntityType type, boolean baby, Player... players){
		Player p = getPlayer();
		
	    EntityLiving disguise = null;
	    
	    switch(type) {
    	case BAT:
    		disguise = new EntityBat(((CraftPlayer) p).getHandle().world);
	        break;
        case BLAZE:
            disguise = new EntityBlaze(((CraftPlayer) p).getHandle().world);
            break;
		case ARMOR_STAND:
            disguise = new EntityArmorStand(((CraftPlayer) p).getHandle().world);
			break;
		case ARROW:
			break;
		case BOAT:
			break;
		case CAVE_SPIDER:
			disguise = new EntityCaveSpider(((CraftPlayer) p).getHandle().world);
			break;
		case CHICKEN:
			disguise = new EntityChicken(((CraftPlayer) p).getHandle().world);
			break;
		case COMPLEX_PART:
			break;
		case COW:
			disguise = new EntityCow(((CraftPlayer) p).getHandle().world);
			break;
		case CREEPER:
			disguise = new EntityCreeper(((CraftPlayer) p).getHandle().world);
			break;
		case DROPPED_ITEM:
			break;
		case EGG:
			break;
		case ENDERMAN:
			disguise = new EntityEnderman(((CraftPlayer) p).getHandle().world);
			break;
		case ENDERMITE:
			disguise = new EntityEndermite(((CraftPlayer) p).getHandle().world);
			break;
		case ENDER_CRYSTAL:
			break;
		case ENDER_DRAGON:
			disguise = new EntityEnderDragon(((CraftPlayer) p).getHandle().world);
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
			disguise = new EntityGhast(((CraftPlayer) p).getHandle().world);
			break;
		case GIANT:
			disguise = new EntityGiantZombie(((CraftPlayer) p).getHandle().world);
			break;
		case GUARDIAN:
			disguise = new EntityGuardian(((CraftPlayer) p).getHandle().world);
			break;
		case HORSE:
			disguise = new EntityHorse(((CraftPlayer) p).getHandle().world);
			break;
		case IRON_GOLEM:
			disguise = new EntityIronGolem(((CraftPlayer) p).getHandle().world);
			break;
		case ITEM_FRAME:
			break;
		case LEASH_HITCH:
			break;
		case LIGHTNING:
			break;
		case MAGMA_CUBE:
			disguise = new EntityMagmaCube(((CraftPlayer) p).getHandle().world);
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
			disguise = new EntityMushroomCow(((CraftPlayer) p).getHandle().world);
			break;
		case OCELOT:
			disguise = new EntityOcelot(((CraftPlayer) p).getHandle().world);
			break;
		case PAINTING:
			break;
		case PIG:
			disguise = new EntityPig(((CraftPlayer) p).getHandle().world);
			break;
		case PIG_ZOMBIE:
			disguise = new EntityPigZombie(((CraftPlayer) p).getHandle().world);
			break;
		case PLAYER:
			break;
		case PRIMED_TNT:
			break;
		case RABBIT:
			disguise = new EntityRabbit(((CraftPlayer) p).getHandle().world);
			break;
		case SHEEP:
			disguise = new EntitySheep(((CraftPlayer) p).getHandle().world);
			break;
		case SILVERFISH:
			disguise = new EntitySilverfish(((CraftPlayer) p).getHandle().world);
			break;
		case SKELETON:
			disguise = new EntitySkeleton(((CraftPlayer) p).getHandle().world);
			break;
		case SLIME:
			disguise = new EntitySlime(((CraftPlayer) p).getHandle().world);
			break;
		case SMALL_FIREBALL:
			break;
		case SNOWBALL:
			break;
		case SNOWMAN:
			disguise = new EntitySnowman(((CraftPlayer) p).getHandle().world);
			break;
		case SPIDER:
			disguise = new EntitySpider(((CraftPlayer) p).getHandle().world);
			break;
		case SPLASH_POTION:
			break;
		case SQUID:
			disguise = new EntitySquid(((CraftPlayer) p).getHandle().world);
			break;
		case THROWN_EXP_BOTTLE:
			break;
		case UNKNOWN:
			break;
		case VILLAGER:
			disguise = new EntityVillager(((CraftPlayer) p).getHandle().world);
			break;
		case WEATHER:
			break;
		case WITCH:
			disguise = new EntityWitch(((CraftPlayer) p).getHandle().world);
			break;
		case WITHER:
			disguise = new EntityWither(((CraftPlayer) p).getHandle().world);
			break;
		case WITHER_SKULL:
			break;
		case WOLF:
			disguise = new EntityWolf(((CraftPlayer) p).getHandle().world);
			break;
		case ZOMBIE:
			disguise = new EntityZombie(((CraftPlayer) p).getHandle().world);
			break;
		default:
			break;
	    }
	    
	    if(disguise != null){
	        disguise.locX = p.getLocation().getX();
	        disguise.locY = p.getLocation().getY();
	        disguise.locZ = p.getLocation().getZ();
	        disguise.yaw = p.getLocation().getYaw();
	        disguise.pitch = p.getLocation().getPitch();
	        disguise.d(((CraftPlayer) p).getHandle().getId());
	        
	        if(baby && disguise.getBukkitEntity() instanceof Ageable){
	        	((Ageable) disguise.getBukkitEntity()).setBaby();
	        }
	        
	        setDisguised(true);
	        setDisguise(disguise);
	        setDisguiseEntityType(type);
	        setDisguisedBaby(baby);
	        
	        for(Player player : players){
	        	if(player != p){
	        		OMPlayer omplayer = OMPlayer.getOMPlayer(player);
	        		if(omplayer.hasPlayersEnabled()){
	        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy((((CraftPlayer) p).getHandle().getId())));
	        			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(disguise));
	        		}
	        	}
	        }
	    }
	}
	
	public void undisguise(){
    	Player p = getPlayer();
    	this.isdisguised = false;
    	
        for(Player player : Bukkit.getOnlinePlayers()){
    		player.hidePlayer(p);
    		player.showPlayer(p);
        }
    }
	
	public void updateTracker(List<OMPlayer> players){
		List<OMPlayer> newplayers = new ArrayList<OMPlayer>();
		newplayers.addAll(players);
		newplayers.remove(this);
		
		if(newplayers.size() != 0){
			double distance = 100000;
			OMPlayer nearest = null;
			
			for(OMPlayer omp : newplayers){
				Player p = omp.getPlayer();
				
				if(getPlayer().getWorld().getName().equals(p.getWorld().getName())){
					double pdistnace = getPlayer().getLocation().distance(p.getLocation());
					if(pdistnace <= distance){
						distance = pdistnace;
						nearest = omp;
					}
				}
			}
			
			if(nearest != null){
				for(ItemStack item : getPlayer().getInventory().getContents()){
					if(item != null && item.getType() == Material.COMPASS){
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName("§6§lTracking: §f§l" + nearest.getPlayer().getName() + " §6§lDistance: §f§l" + String.format("%.1f", distance));
						item.setItemMeta(meta);
					}
				}
				getPlayer().setCompassTarget(nearest.getPlayer().getLocation());
			}
		}
	}
	
	public boolean hasItems(Material material, short durability, int amount){
		int iAmount = 0;
		
		for(ItemStack item : getPlayer().getInventory().getContents()){
			if(item != null && item.getType() == material && item.getDurability() == durability){
				iAmount += item.getAmount();
			}
		}
		
		return iAmount >= amount;
	}
	
	public void removeItems(Material material, short durability, int amount){
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(ItemStack item : getPlayer().getInventory().getContents()){
			if(item != null && item.getType() == material && item.getDurability() == durability){
				getPlayer().getInventory().remove(item);
				items.add(item);
			}
		}
		List<ItemStack> itemstoremove = new ArrayList<ItemStack>();
		List<ItemStack> itemstoadd = new ArrayList<ItemStack>();
		
		int iAmount = 0;
		for(ItemStack item : items){
			if(iAmount != amount){
				if(iAmount + item.getAmount() <= amount){
					itemstoremove.add(item);
					
					iAmount += item.getAmount();
				}
				else{
					itemstoremove.add(item);
					
					ItemStack item3 = new ItemStack(item);
					item3.setAmount(item.getAmount() - (amount - iAmount));
					itemstoadd.add(item3);
					
					iAmount = amount;
				}
			}
		}
		
		for(ItemStack item : itemstoremove){
			items.remove(item);
		}
		for(ItemStack item : itemstoadd){
			items.add(item);
		}
		
		getPlayer().getInventory().addItem(items.toArray(new ItemStack[items.size()]));
	}
	
	public int removeAll(Material material, short durability){
		int amount = 0;
		for(ItemStack item : getPlayer().getInventory().getContents()){
			if(item != null && item.getType() == material && item.getDurability() == durability){
				getPlayer().getInventory().remove(item);
				amount += item.getAmount();
			}
		}
		return amount;
	}
	
	public int getAmount(Material material, short durability){
		int amount = 0;
		for(ItemStack item : getPlayer().getInventory().getContents()){
			if(item != null && item.getType() == material && item.getDurability() == durability){
				amount += item.getAmount();
			}
		}
		return amount;
	}
	
	public UUID getUUID(){
		return getPlayer().getUniqueId();
	}
	
	public static OMPlayer getOMPlayer(Player player){
		OMPlayer omp = api.getPlayers().get(player);
		if(omp != null) return omp;
		
		for(OMPlayer omplayer : api.getOMPlayers()){
			if(omplayer.getPlayer() == player){
				return omplayer;
			}
		}
		return null;
	}
	
	public void sendQuitMessage(){
		if(!api.isServer(Server.MINIGAMES)){
			if(isSilentMode()){
				for(Player p : Bukkit.getOnlinePlayers()){
					OMPlayer omp = OMPlayer.getOMPlayer(p);
					if(omp.hasPerms(StaffRank.Moderator)){
						p.sendMessage(" §c« " + getName() + "§c left. §6[Silent Mode]");
					}
				}
			}
			else{
				Bukkit.broadcastMessage(" §c« " + getName() + "§c left.");
			}
		}
	}
	
	public void sendJoinMessage(){
		new Title("§6§lOrbitMines§4§lNetwork", api.server().getName());
		
		new BukkitRunnable(){
			public void run(){
				Player p = getPlayer();
				p.sendMessage("§7§m----------------------------------------");
				p.sendMessage(" §6§lOrbitMines§4§lNetwork §7- " + api.server().getName());
				p.sendMessage(" ");
				p.sendMessage(" §7§lWebsite: §6www.orbitmines.com");
				p.sendMessage(" §7§lDonate: §3shop.orbitmines.com");
				p.sendMessage(" §7§lVote: §bwww.orbitmines.com/vote");
				p.sendMessage(" ");
				
				ComponentMessage cm = new ComponentMessage();
				cm.addPart(" §7§lSpawn Built By: ", null, null, null, null);
				cm.addPart("§e§l[View]", null, null, Action.SHOW_TEXT, api.getSpawnBuilders());
				cm.send(p);
				
				p.sendMessage(" §7§lDeveloped By: §4§lOwner §4O_o_Fadi_o_O");
				p.sendMessage("§7§m----------------------------------------");
			}
		}.runTaskLater(api, 20);
		
		if(!api.isServer(Server.MINIGAMES)){
			if(isSilentMode()){
				for(Player p : Bukkit.getOnlinePlayers()){
					OMPlayer omp = OMPlayer.getOMPlayer(p);
					if(omp.hasPerms(StaffRank.Moderator)){
						p.sendMessage(" §a» " + getName() + "§a joined. §6[Silent Mode]");
					}
				}
			}
			else{
				Bukkit.broadcastMessage(" §a» " + getName() + "§a joined.");
			}
		}
	}
	
	public void loadData(){
		this.loaded = false;
		this.opmode = false;
		this.canchat = false;
		this.inlapisparkour = false;
		this.inmindcraft = false;
		this.isdisguised = false;
		this.afk = false;
		
		getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		setTabList("§6§lOrbitMines§4§lNetwork", "§7Website: §6www.orbitmines.com §8| §7Twitter: §b@OrbitMines §8| §7Donate: §3shop.orbitmines.com");

		String uuid = getUUID().toString();
		boolean newplayer = false;
		
		try{
			loadMonthlyVIPPoints(uuid);
			loadSilentJoin(uuid);
			loadVIPRank(uuid);
			loadStaffRank(uuid);
			loadVotes(uuid);
			loadOrbitMinesTokens(uuid);
			loadVIPPoints(uuid);
			loadChatColors(uuid);
			loadTrails(uuid);
			loadNickname(uuid);
			
			loadPlayerData();
			
			api.getVoteManager().check(this);
			sendJoinMessage();
			setLoaded(true);
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
		
		this.newplayer = newplayer;
	}
	
	private void loadMonthlyVIPPoints(String uuid){
		this.receivedmonthlybonus = Database.get().containsPath("PlayerMonthlyVIPPoints", "uuid", "uuid", uuid);
	}
	
	private void loadSilentJoin(String uuid){
		if(Database.get().containsPath("PlayerSilentJoin", "uuid", "uuid", uuid)){
			this.silent = Boolean.parseBoolean(Database.get().getString("PlayerSilentJoin", "silentjoin", "uuid", uuid));
		}
		else{
			this.silent = false;
		}
	}
	
	private void loadVIPRank(String uuid){
		if(Database.get().containsPath("Rank-VIP", "uuid", "uuid", uuid)){
			this.viprank = VIPRank.valueOf(Database.get().getString("Rank-VIP", "vip", "uuid", uuid));
		}
		else{
			this.viprank = VIPRank.User;
		}
	}
	
	private void loadStaffRank(String uuid){
		if(Database.get().containsPath("Rank-Staff", "uuid", "uuid", uuid)){
			this.staffrank = StaffRank.valueOf(Database.get().getString("Rank-Staff", "staff", "uuid", uuid));
		}
		else{
			this.staffrank = StaffRank.User;
		}
	}
	
	private void loadVotes(String uuid){
		if(Database.get().containsPath("Votes", "uuid", "uuid", uuid)){
			this.votes = Database.get().getInt("Votes", "votes", "uuid", uuid);
		}
		else{
			Database.get().insert("Votes", "uuid`, `votes", uuid + "', '" + 0);
			this.votes = 0;
		}
	}
	
	private void loadOrbitMinesTokens(String uuid){
		if(Database.get().containsPath("OrbitMinesTokens", "uuid", "uuid", uuid)){
			this.orbitminestokens = Database.get().getInt("OrbitMinesTokens", "omt", "uuid", uuid);
		}
		else{
			Database.get().insert("OrbitMinesTokens", "uuid`, `omt", uuid + "', '" + 0);
			this.orbitminestokens = 0;
		}
	}
	
	private void loadVIPPoints(String uuid){
		if(Database.get().containsPath("VIPPoints", "uuid", "uuid", uuid)){
			this.vippoints = Database.get().getInt("VIPPoints", "points", "uuid", uuid);
			this.newplayer = false;
		}
		else{
			Database.get().insert("VIPPoints", "uuid`, `points", uuid + "', '" + 0);
			this.vippoints = 0;
			this.newplayer = true;
		}
	}
	
	private void loadFirework(String uuid){
		if(Database.get().containsPath("Fireworks-Passes", "uuid", "uuid", uuid)){
			this.fireworkpasses = Database.get().getInt("Fireworks-Passes", "passes", "uuid", uuid);
		}
		else{
			Database.get().insert("Fireworks-Passes", "uuid`, `passes", uuid + "', '" + 0);
			this.fireworkpasses = 0;
		}
		
		if(Database.get().containsPath("Fireworks-Settings", "uuid", "uuid", uuid)){
			String[] fwsettings = Database.get().getString("Fireworks-Settings", "settings", "uuid", uuid).split("\\|");
			if(fwsettings[6].equals("null")){fwsettings[6] = "BALL";}
			this.fwsettings = new FireworkSettings(ColorUtils.parse(fwsettings[0]), ColorUtils.parse(fwsettings[1]), ColorUtils.parse(fwsettings[2]), ColorUtils.parse(fwsettings[3]), Boolean.parseBoolean(fwsettings[4]), Boolean.parseBoolean(fwsettings[5]), Type.valueOf(fwsettings[6]));
		}
		else{
			Database.get().insert("Fireworks-Settings", "uuid`, `settings", uuid + "', '" + "AQUA|null|null|null|false|false|BALL");
			this.fwsettings = new FireworkSettings(Color.AQUA, null, null, null, false, false, Type.BALL);
		}
	}
	
	private void loadGadgets(String uuid){
		this.gadgets = new ArrayList<Gadget>();
		for(Gadget gadget : Gadget.values()){
			if(gadget != Gadget.STACKER && gadget != Gadget.FIREWORK_GUN){
				if(Database.get().containsPath("Gadgets-" + gadget.getDatabaseName(), "uuid", "uuid", uuid)){
					this.gadgets.add(gadget);
				}
			}
		}
	}
	
	protected void loadPets(String uuid){//TODO
		this.pets = new ArrayList<Pet>();
		this.petnames = new HashMap<Pet, String>();
		for(Pet pet : Pet.values()){
			if(Database.get().containsPath("Pets-" + pet.getDatabaseName(), "uuid", "uuid", uuid)){
				this.pets.add(pet);
				this.petnames.put(pet, Database.get().getString("Pets-" + pet.getDatabaseName(), "petname", "uuid", uuid));
			}	
		}
	}
	
	private void loadWardrobe(String uuid){
		this.wardrobe = new ArrayList<Color>();
		for(Color color : ColorUtils.getWardrobe()){
			if(Database.get().containsPath("Wardrobe-" + ColorUtils.getDatabaseName(color), "uuid", "uuid", uuid)){
				this.wardrobe.add(color);
			}	
		}
		
		if(Database.get().containsPath("Wardrobe-Disco", "uuid", "uuid", uuid)){
			this.wardrobedisco = Boolean.parseBoolean(Database.get().getString("Wardrobe-Disco", "disco", "uuid", uuid));
			this.unlockedwardrobedisco = true;
		}
		else{
			this.wardrobedisco = false;
			this.unlockedwardrobedisco = false;
		}
	}
	
	private void loadChatColors(String uuid){
		this.chatcolors = new ArrayList<ChatColor>();
		for(ChatColor chatcolor : ChatColor.values()){
			if(chatcolor.getVIPRank() == null && chatcolor != ChatColor.GRAY){
				if(Database.get().containsPath("ChatColors-" + chatcolor.getDatabaseName(), "uuid", "uuid", uuid)){
					this.chatcolors.add(chatcolor);
				}
			}
		}
		
		if(Database.get().containsPath("ChatColors-Bold", "uuid", "uuid", uuid)){
			this.chatcolorbold = Boolean.parseBoolean(Database.get().getString("ChatColors-Bold", "bold", "uuid", uuid));
			this.unlockedchatcolorbold = true;
		}
		else{
			this.chatcolorbold = false;
			this.unlockedchatcolorbold = false;
		}
		
		if(Database.get().containsPath("ChatColors-Cursive", "uuid", "uuid", uuid)){
			this.chatcolorcursive = Boolean.parseBoolean(Database.get().getString("ChatColors-Cursive", "cursive", "uuid", uuid));
			this.unlockedchatcolorcursive = true;
		}
		else{
			this.chatcolorcursive = false;
			this.unlockedchatcolorcursive = false;
		}
		
		if(Database.get().containsPath("ChatColors", "uuid", "uuid", uuid)){
			try{
				this.chatcolor = ChatColor.valueOf(Database.get().getString("ChatColors", "color", "uuid", uuid));
			}catch(IllegalArgumentException ex){
				this.chatcolor = ChatColor.GRAY;
			}
		}
		else{
			Database.get().insert("ChatColors", "uuid`, `color", uuid + "', '" + ChatColor.GRAY.toString());
			this.chatcolor = ChatColor.GRAY;
		}
	}
	
	private void loadTrails(String uuid){
		if(Database.get().containsPath("Trail", "uuid", "uuid", uuid)){
			this.trail = Trail.valueOf(Database.get().getString("Trail", "trail", "uuid", uuid));
		}
		
		this.trails = new ArrayList<Trail>();
		for(Trail trail : Trail.values()){
			if(trail.getVIPRank() == null){
				if(Database.get().containsPath("Trails-" + trail.getDatabaseName(), "uuid", "uuid", uuid)){
					this.trails.add(trail);
				}
			}
		}
		
		if(Database.get().containsPath("Trails-Type", "uuid", "uuid", uuid)){
			this.trailtype = TrailType.valueOf(Database.get().getString("Trails-Type", "type", "uuid", uuid));
		}
		else{
			Database.get().insert("Trails-Type", "uuid`, `type", uuid + "', '" + TrailType.BASIC_TRAIL.toString());
			this.trailtype = TrailType.BASIC_TRAIL;
		}
		
		this.trailtypes = new ArrayList<TrailType>();
		for(TrailType trailtype : TrailType.values()){
			if(trailtype != TrailType.BASIC_TRAIL){
				if(Database.get().containsPath("Trails-" + trailtype.getDatabaseName(), "uuid", "uuid", uuid)){
					this.trailtypes.add(trailtype);
				}
			}
		}
		
		if(Database.get().containsPath("Trails-TypeSpecial", "uuid", "uuid", uuid)){
			this.specialtrail = Boolean.parseBoolean(Database.get().getString("Trails-TypeSpecial", "special", "uuid", uuid));
			this.unlockedspecialtrail = true;
		}
		else{
			this.specialtrail = false;
			this.unlockedspecialtrail = false;
		}
		
		if(Database.get().containsPath("Trails-TypeParticlesAmount", "uuid", "uuid", uuid)){
			this.trailparticlesamount = Database.get().getInt("Trails-TypeParticlesAmount", "amount", "uuid", uuid);
		}
		else{
			Database.get().insert("Trails-TypeParticlesAmount", "uuid`, `amount", uuid + "', '" + 1);
			this.trailparticlesamount = 1;
		}
	}
	
	private void loadHats(String uuid){
		this.hats = new ArrayList<Hat>();
		for(Hat hat : Hat.values()){
			if(hat.getVIPRank() == null){
				if(Database.get().containsPath("Hats-" + hat.getDatabaseName(), "uuid", "uuid", uuid)){
					this.hats.add(hat);
				}
			}
		}
		
		this.hatsinvpage = 1;
		
		if(Database.get().containsPath("Hats-BlockTrail", "uuid", "uuid", uuid)){
			this.hatsblocktrail = Boolean.parseBoolean(Database.get().getString("Hats-BlockTrail", "blocktrail", "uuid", uuid));
			this.unlockedhatsblocktrail = true;
		}
		else{
			this.hatsblocktrail = false;
			this.unlockedhatsblocktrail = false;
		}
	}
	
	private void loadDisguises(String uuid){
		this.disguises = new ArrayList<Disguise>();
		for(Disguise disguise : Disguise.values()){
			if(disguise.getVIPRank() == null){
				if(Database.get().containsPath("Dis-" + disguise.getDatabaseName(), "uuid", "uuid", uuid)){
					this.disguises.add(disguise);
				}
			}
		}
	}
	
	private void loadNickname(String uuid){
		if(Database.get().containsPath("PlayerNicknames", "uuid", "uuid", uuid)){
			this.nickname = Database.get().getString("PlayerNicknames", "nick", "uuid", uuid);
		}
	}
	
	private void updateNickname(String uuid){
		if(Database.get().containsPath("PlayerNicknames", "uuid", "uuid", uuid)){
			if(getNickname() != null){
				Database.get().update("PlayerNicknames", "nick", getNickname().replace("'", "`"), "uuid", uuid);
			}
			else{
				Database.get().delete("PlayerNicknames", "uuid", uuid);
			}
		}
		else{
			if(getNickname() != null){
				Database.get().insert("PlayerNicknames", "uuid`, `nick", uuid + "', '" + getNickname());
			}
		}
	}

	private void updateTrails(String uuid){
		if(Database.get().containsPath("Trail", "uuid", "uuid", uuid)){
			if(hasTrailEnabled()){
				Database.get().update("Trail", "trail", getTrail().toString(), "uuid", uuid);
			}
			else{
				Database.get().delete("Trail", "uuid", uuid);
			}
		}
		else{
			if(hasTrailEnabled()){
				Database.get().insert("Trail", "uuid`, `trail", uuid + "', '" + getTrail());
			}
		}
		
		Database.get().update("Trails-TypeParticlesAmount", "amount", "" + getTrailPA(), "uuid", uuid);
		Database.get().update("Trails-TypeSpecial", "special", "" + hasSpecialTrail(), "uuid", uuid);
		Database.get().update("Trails-Type", "type", getTrailType().toString(), "uuid", uuid);
	}

	private void updateWardrobe(String uuid){
		if(Database.get().containsPath("Wardrobe-Disco", "uuid", "uuid", uuid)){
			Database.get().update("Wardrobe-Disco", "disco", isWardrobeDisco() + "", "uuid", uuid);
		}
	}

	private void updateFirework(String uuid){
		Database.get().update("Fireworks-Settings", "settings", getFWSettings().toString(), "uuid", uuid);
		Database.get().update("Fireworks-Passes", "passes", "" + getFireworkPasses(), "uuid", uuid);
	}

	private void updateHats(String uuid){
		if(hasUnlockedHatsBlockTrail()){
			Database.get().update("Hats-BlockTrail", "blocktrail", hasHatsBlockTrail() + "", "uuid", uuid);
		}
	}

	private void updateChatColors(String uuid){
		Database.get().update("ChatColors", "color", getChatColor().toString(), "uuid", uuid);
		if(hasUnlockedBold()){
			Database.get().update("ChatColors-Bold", "bold", isBold() + "", "uuid", uuid);
		}
		if(hasUnlockedCursive()){
			Database.get().update("ChatColors-Cursive", "cursive", isCursive() + "", "uuid", uuid);
		}
	}
}
