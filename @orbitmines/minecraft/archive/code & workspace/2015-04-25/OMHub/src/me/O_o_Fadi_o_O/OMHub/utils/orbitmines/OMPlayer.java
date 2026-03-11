package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OMHub.Start;
import me.O_o_Fadi_o_O.OMHub.managers.BossBarManager;
import me.O_o_Fadi_o_O.OMHub.managers.ScoreboardManager;
import me.O_o_Fadi_o_O.OMHub.managers.VoteManager;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.ChatColor;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.ComponentMessage;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Disguise;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Gadget;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Hat;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Pet;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Server;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.StaffRank;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Trail;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.TrailType;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.VIPRank;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.minecraft.server.v1_8_R2.AttributeInstance;
import net.minecraft.server.v1_8_R2.BlockLog1;
import net.minecraft.server.v1_8_R2.EntityArmorStand;
import net.minecraft.server.v1_8_R2.EntityBat;
import net.minecraft.server.v1_8_R2.EntityBlaze;
import net.minecraft.server.v1_8_R2.EntityCaveSpider;
import net.minecraft.server.v1_8_R2.EntityChicken;
import net.minecraft.server.v1_8_R2.EntityCow;
import net.minecraft.server.v1_8_R2.EntityCreeper;
import net.minecraft.server.v1_8_R2.EntityEnderDragon;
import net.minecraft.server.v1_8_R2.EntityEnderman;
import net.minecraft.server.v1_8_R2.EntityEndermite;
import net.minecraft.server.v1_8_R2.EntityFallingBlock;
import net.minecraft.server.v1_8_R2.EntityGhast;
import net.minecraft.server.v1_8_R2.EntityGiantZombie;
import net.minecraft.server.v1_8_R2.EntityGuardian;
import net.minecraft.server.v1_8_R2.EntityHorse;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EntityIronGolem;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntityMagmaCube;
import net.minecraft.server.v1_8_R2.EntityMushroomCow;
import net.minecraft.server.v1_8_R2.EntityOcelot;
import net.minecraft.server.v1_8_R2.EntityPig;
import net.minecraft.server.v1_8_R2.EntityPigZombie;
import net.minecraft.server.v1_8_R2.EntityRabbit;
import net.minecraft.server.v1_8_R2.EntitySheep;
import net.minecraft.server.v1_8_R2.EntitySilverfish;
import net.minecraft.server.v1_8_R2.EntitySkeleton;
import net.minecraft.server.v1_8_R2.EntitySlime;
import net.minecraft.server.v1_8_R2.EntitySnowman;
import net.minecraft.server.v1_8_R2.EntitySpider;
import net.minecraft.server.v1_8_R2.EntitySquid;
import net.minecraft.server.v1_8_R2.EntityVillager;
import net.minecraft.server.v1_8_R2.EntityWitch;
import net.minecraft.server.v1_8_R2.EntityWither;
import net.minecraft.server.v1_8_R2.EntityWolf;
import net.minecraft.server.v1_8_R2.EntityZombie;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R2.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_8_R2.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Chicken;
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
import org.bukkit.scheduler.BukkitRunnable;

public class OMPlayer {

	private Player player;
	
	private boolean silent;
	private boolean canchat;
	private boolean opmode;
	private boolean loaded;
	
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
	
	private List<UUID> friends;
	
	private List<Gadget> gadgets;
	private Entity soccermagmacube;
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
	
	private MindCraftPlayer mcplayer;
	private ChickenFightPlayer cfplayer;
	private SurvivalGamesPlayer sgplayer;
	private KitPvPPlayer kitpvpplayer;
	 
	private int votes;
	private int vippoints;
	private int orbitminestokens;
	private int minigamecoins;
	
	public OMPlayer(Player player, boolean loaded){
		this.player = player;
		this.loaded = loaded;
	}

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

	public boolean isAFK(){
		return afk;
	}
	public void setAFK(boolean afk){
		this.afk = afk;
	}

	public String getAFKName(){
		return afkname;
	}
	public void setAFKName(String afkname){
		this.afkname = afkname;
	}

	public VIPRank getVIPRank(){
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
	}

	public List<ChatColor> getChatColors(){
		return chatcolors;
	}
	public void setChatColors(List<ChatColor> chatcolors){
		this.chatcolors = chatcolors;
	}
	public void addChatColor(ChatColor chatcolor){
		this.chatcolors.add(chatcolor);
	}
	public boolean hasChatColor(ChatColor chatcolor){
		return chatcolors.contains(chatcolor);
	}
	
	public boolean hasUnlockedBold(){
		return unlockedchatcolorbold;
	}
	public void setUnlockedBold(boolean unlockedchatcolorbold){
		this.unlockedchatcolorbold = unlockedchatcolorbold;
	}

	public boolean isBold(){
		return chatcolorbold;
	}
	public void setBold(boolean chatcolorbold){
		this.chatcolorbold = chatcolorbold;
	}
	
	public boolean hasUnlockedCursive(){
		return unlockedchatcolorcursive;
	}
	public void setUnlockedCursive(boolean unlockedchatcolorcursive){
		this.unlockedchatcolorcursive = unlockedchatcolorcursive;
	}

	public boolean isCursive(){
		return chatcolorcursive;
	}
	public void setCursive(boolean chatcolorcursive){
		this.chatcolorcursive = chatcolorcursive;
	}

	public List<Disguise> getDisguises(){
		return disguises;
	}
	public void setDisguises(List<Disguise> disguises){
		this.disguises = disguises;
	}
	public void addDisguise(Disguise disguise){
		this.disguises.add(disguise);
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
	}
	public boolean hasHat(){
		return getHats().size() > 0;
	}
	public boolean hasHat(Hat hat){
		return hats.contains(hat);
	}

	public int getHatsInvPage(){
		return hatsinvpage;
	}
	public void setHatsInvPage(int hatsinvpage){
		this.hatsinvpage = hatsinvpage;
	}

	public boolean hasUnlockedHatsBlockTrail(){
		return unlockedhatsblocktrail;
	}
	public void setUnlockedHatsBlockTrail(boolean unlockedhatsblocktrail){
		this.unlockedhatsblocktrail = unlockedhatsblocktrail;
	}

	public boolean hasHatsBlockTrail(){
		return hatsblocktrail;
	}
	public void setHatsBlockTrail(boolean hatsblocktrail){
		this.hatsblocktrail = hatsblocktrail;
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
	}

	public Pet getPetEnabled(){
		return petenabled;
	}
	public void setPetEnabled(Pet petenabled){
		this.petenabled = petenabled;
	}
	
	public Trail getTrail(){
		return trail;
	}
	public void setTrail(Trail trail){
		this.trail = trail;
	}
	public boolean hasTrailEnabled(){
		return getTrail() != null;
	}

	public TrailType getTrailType(){
		return trailtype;
	}
	public void setTrailType(TrailType trailtype){
		this.trailtype = trailtype;
	}

	public List<Trail> getTrails(){
		return trails;
	}
	public void setTrails(List<Trail> trails){
		this.trails = trails;
	}
	public void addTrail(Trail trail){
		this.trails.add(trail);
	}
	public boolean hasTrail(){
		return getTrails().size() > 0;
	}
	public boolean hasTrail(Trail trail){
		return trails.contains(trail);
	}

	public int getTrailPA(){
		return trailparticlesamount;
	}
	public void setTrailPA(int trailparticlesamount){
		this.trailparticlesamount = trailparticlesamount;
	}

	public boolean hasSpecialTrail(){
		return specialtrail;
	}
	public void setSpecialTrail(boolean specialtrail){
		this.specialtrail = specialtrail;
	}

	public boolean hasUnlockedSpecialTrail(){
		return unlockedspecialtrail;
	}
	public void setUnlockedSpecialTrail(boolean unlockedspecialtrail){
		this.unlockedspecialtrail = unlockedspecialtrail;
	}

	public List<TrailType> getTrailTypes(){
		return trailtypes;
	}
	public void setTrailTypes(List<TrailType> trailtypes){
		this.trailtypes = trailtypes;
	}
	public void addTrailType(TrailType trailtype){
		this.trailtypes.add(trailtype);
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
	}
	public boolean hasWardrobe(Color color){
		return wardrobe.contains(color);
	}
	
	public boolean hasUnlockedWardrobeDisco(){
		return unlockedwardrobedisco;
	}
	public void setUnlockedWardrobeDisco(boolean unlockedwardrobedisco){
		this.unlockedwardrobedisco = unlockedwardrobedisco;
	}

	public boolean isWardrobeDisco(){
		return wardrobedisco;
	}
	public void setWardrobeDisco(boolean wardrobedisco){
		this.wardrobedisco = wardrobedisco;
	}

	public int getFireworkPasses(){
		return fireworkpasses;
	}
	public void setFireworkPasses(int fireworkpasses){
		this.fireworkpasses = fireworkpasses;
	}
	public void addFireworkPasses(int fireworkpasses){
		this.fireworkpasses = fireworkpasses +fireworkpasses;
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
	public MindCraftPlayer getMCPlayer(){
		return mcplayer;
	}
	public void setMCPlayer(MindCraftPlayer mcplayer){
		this.mcplayer = mcplayer;
	}

	public ChickenFightPlayer getCFPlayer(){
		return cfplayer;
	}
	public void setCFPlayer(ChickenFightPlayer cfplayer){
		this.cfplayer = cfplayer;
	}

	public SurvivalGamesPlayer getSGPlayer(){
		return sgplayer;
	}
	public void setSGPlayer(SurvivalGamesPlayer sgplayer){
		this.sgplayer = sgplayer;
	}

	public KitPvPPlayer getKitPvPPlayer(){
		return kitpvpplayer;
	}
	public void setKitPvPPlayer(KitPvPPlayer kitpvpplayer){
		this.kitpvpplayer = kitpvpplayer;
	}

	public int getVotes(){
		return votes;
	}
	public void setVotes(int votes){
		this.votes = votes;
	}
	public void addVote(){
		this.votes = votes +1;
	}
	public boolean hasVotes(int votes){
		return this.votes >= votes;
	}

	public int getVIPPoints(){
		return vippoints;
	}
	public void setVIPPoints(int vippoints){
		this.vippoints = vippoints;
	}
	public void addVIPPoints(int vippoints){
		this.vippoints = this.vippoints +vippoints;
		
		Database.get().update("VIPPoints", "points", "" + getVIPPoints(), "uuid", getUUID().toString());
		
		Title t = new Title("", "§b+" + vippoints + " VIP Points");
		t.send(getPlayer());
	}
	public void removeVIPPoints(int vippoints){
		this.vippoints = this.vippoints -vippoints;
		
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
	}
	public void addOrbitMinesTokens(int orbitminestokens){
		this.orbitminestokens = this.orbitminestokens +orbitminestokens;
		
		Database.get().update("OrbitMinesTokens", "omt", "" + getOrbitMinesTokens(), "uuid", getUUID().toString());
	}
	public void removeOrbitMinesTokens(int orbitminestokens){
		this.orbitminestokens = this.orbitminestokens -orbitminestokens;
		
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
		this.minigamecoins = this.minigamecoins +minigamecoins;
		
		Database.get().update("MiniGameCoins", "coins", "" + getMiniGameCoins(), "uuid", getUUID().toString());
	}
	public void removeMiniGameCoins(int minigamecoins){
		this.minigamecoins = this.minigamecoins -minigamecoins;
		
		Database.get().update("MiniGameCoins", "coins", "" + getMiniGameCoins(), "uuid", getUUID().toString());
	}
	public boolean hasMiniGameCoins(int minigamecoins){
		return this.minigamecoins >= minigamecoins;
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
		ScoreboardManager.setScoreboard(this);
	}
	
	public void vote(){
		Player p = getPlayer();
		
		p.sendMessage("");
		p.sendMessage("§b§lVote §8| §7Thank you, §b§l" + p.getName() + " §7for your §b§lVote§7!");
		p.sendMessage("§b§lVote §8| §7Your reward in the " + ServerData.getServer().getName() + "§7 Server:");
		p.sendMessage("§b§lVote §8| §7");
		
		if(ServerData.isServer(Server.HUB)){
			addOrbitMinesTokens(1);
			
			Title t = new Title("§b§lVote", "§e+1 OrbitMines Token");
			t.send(getPlayer());
			
			p.sendMessage("§b§lVote §8| §7  - §e§l1 OrbitMines Token");
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		}
		//TODO Add other Servers.

		p.sendMessage("§b§lVote §8| §7");
		p.sendMessage("§b§lVote §8| §7Your Total Votes this Month: §b§l" + getVotes());
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player != p){
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §b§l" + p.getName() + "§7 has voted with §b§l/vote");
			}
		}
	}
	
	public void setBossBar(){
		BossBarManager.setBossBar(this);
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
		Server server = ServerData.getServer();
		p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		p.sendMessage(server.getName() + " §8| §7This Server just " + server.getColor() + "restarted§7! Wait a few " + server.getColor() + "seconds§7.");
	}
	
	public boolean hasWardrobeEnabled(){
		return getPlayer().getInventory().getChestplate() != null;
	}
	public void disableWardrobe(){	
		Player p = getPlayer();
		
		p.sendMessage("§9Cosmetic Perks §8| §c§lDISABLED §7your " + p.getInventory().getChestplate().getItemMeta().getDisplayName() +"§7!");
	
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		
		setWardrobeDisco(false);
	}
	
	public void discoWardrobe(){
		Player p = getPlayer();
		Color color = Utils.getRandomColor(getWardrobe());
		
		p.getInventory().setChestplate(Utils.setDisplayname(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), color), Utils.getColor(color) + "Disco Armor"));
		p.getInventory().setLeggings(Utils.setDisplayname(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), color), Utils.getColor(color) + "Disco Armor"));
		p.getInventory().setBoots(Utils.setDisplayname(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), color), Utils.getColor(color) + "Disco Armor"));
	}
	
	public void wardrobe(Color color){
		Player p = getPlayer();
		
		p.getInventory().setChestplate(Utils.setDisplayname(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), color), Utils.getColorName(color) + " Armor"));
		p.getInventory().setLeggings(Utils.setDisplayname(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), color), Utils.getColorName(color) + " Armor"));
		p.getInventory().setBoots(Utils.setDisplayname(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), color), Utils.getColorName(color) + " Armor"));
	}
	public void wardrobe(VIPRank viprank){
		Player p = getPlayer();
		switch(viprank){
			case Emerald_VIP:
				p.getInventory().setChestplate(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "§7Chainmail Armor"));
				p.getInventory().setLeggings(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), "§7Chainmail Armor"));
				p.getInventory().setBoots(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS, 1), "§7Chainmail Armor"));
				break;
			case Diamond_VIP:
				p.getInventory().setChestplate(Utils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE, 1), "§bDiamond Armor"));
				p.getInventory().setLeggings(Utils.setDisplayname(new ItemStack(Material.DIAMOND_LEGGINGS, 1), "§bDiamond Armor"));
				p.getInventory().setBoots(Utils.setDisplayname(new ItemStack(Material.DIAMOND_BOOTS, 1), "§bDiamond Armor"));
				break;
			case Gold_VIP:
				p.getInventory().setChestplate(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE, 1), "§6Gold Armor"));
				p.getInventory().setLeggings(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS, 1), "§6Gold Armor"));
				p.getInventory().setBoots(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS, 1), "§6Gold Armor"));
				break;
			case Iron_VIP:
				p.getInventory().setChestplate(Utils.setDisplayname(new ItemStack(Material.IRON_CHESTPLATE, 1), "§7Iron Armor"));
				p.getInventory().setLeggings(Utils.setDisplayname(new ItemStack(Material.IRON_LEGGINGS, 1), "§7Iron Armor"));
				p.getInventory().setBoots(Utils.setDisplayname(new ItemStack(Material.IRON_BOOTS, 1), "§7Iron Armor"));
				break;
			case User:
				break;
			default:
				break;
		}
	}
	
	public void disableTrail(){
		getPlayer().sendMessage("§9Cosmetic Perks §8| §c§lDISABLED §7your " + getTrail().getName() + "§7!");
		setTrail(null);
	}
	
	public boolean hasHatEnabled(){
		return getPlayer().getInventory().getHelmet() != null;
	}
	public void disableHat(){
		getPlayer().sendMessage("§9Cosmetic Perks §8| §c§lDISABLED §7your " + getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName() + "§7!");
		getPlayer().getInventory().setHelmet(null);
	}
	
	public void disableGadget(){
		ItemStack item = getPlayer().getInventory().getItem(5);
		String displayname = "Gadget";
		if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
			displayname = item.getItemMeta().getDisplayName();
		}
		getPlayer().sendMessage("§9Cosmetic Perks §8| §c§lDISABLED §7your " + displayname + "§7!");
		getPlayer().getInventory().setItem(5, null);
	}
	
	public void disablePet(){
		Pet pet = getPetEnabled();
		getPlayer().sendMessage("§9Cosmetic Perks §8| §c§lDISABLED §f" + getPetName(pet) + "§7!");
		
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
		
		setPet(null);
		setPetEnabled(null);
	}
	public void spawnPet(Pet pet){
		pet.spawn(this);
		//TODO MESSAGE
	}
	
	@SuppressWarnings("deprecation")
	public void givePetInventory(){
		Player p = getPlayer();
		p.getInventory().clear();
		
		switch(getPetEnabled()){
			case CHICKEN:
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.EGG, 1), "§7§nEgg Bomb"));
				
				int chickenage = 1;
				if(((Chicken) getPet()).isAdult()){
					chickenage = 2;
				}
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.RAW_CHICKEN, chickenage), "§c§nChange Age"));
				break;
			case COW:
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.MILK_BUCKET, 1), "§f§nMilk Explosion"));
				
				int cowage = 1;
				if(((Wolf) getPet()).isAdult()){
					cowage = 2;
				}
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.RAW_BEEF, cowage), "§c§nChange Age"));
				break;
			case CREEPER:
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.TNT, 1), "§c§nExplode"));
				
				Creeper creeper = (Creeper) getPet();
				if(creeper.isPowered()){
					p.getInventory().setItem(6, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.MONSTER_EGG, 1), "§a§nChange Type§7 (§e§lLIGHTNING§7)"), 50));
				}
				else{
					p.getInventory().setItem(6, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.MONSTER_EGG, 1), "§a§nChange Type§7 (§6§lNORMAL§7)"), 50));
				}
				break;
			case HORSE:
				Horse h = (Horse) getPet();
				int speed = 1;
				
				AttributeInstance currentSpeed = ((EntityInsentient) ((CraftLivingEntity) h).getHandle()).getAttributeInstance(GenericAttributes.d);
				if(currentSpeed.b() == 0.25){speed = 1;}
				else if(currentSpeed.b() == 0.5){speed = 2;}
				else if(currentSpeed.b() == 0.75){speed = 3;}
				else{}
				
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.FEATHER, speed), "§f§nChange Speed"));
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.LEATHER, 1), "§e§nChange Color"));
				break;
			case MAGMA_CUBE:
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.FIREBALL, 1), "§6§nFireball"));
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.MAGMA_CREAM, ((MagmaCube) getPet()).getSize()), "§c§nChange Size"));
				break;
			case MUSHROOM_COW:
				if(hasPetShroomTrail() == true){
					p.getInventory().setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.HUGE_MUSHROOM_2, 1), "§4§nShroom Trail§7 (§a§lENABLED§7)"), 14));
				}
				else{
					p.getInventory().setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.HUGE_MUSHROOM_1, 1), "§4§nShroom Trail§7 (§c§lDISABLED§7)"), 14));
				}
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.FIREWORK, 1), "§c§nBaby Firework"));
				break;
			case OCELOT:
				p.getInventory().setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.MONSTER_EGG, 1), "§e§nKitty Cannon"), 98));
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.RAW_FISH, 1), "§9§nChange Color"));
				break;
			case PIG:
				if(hasPetBabyPigs() == true){
					p.getInventory().setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.MONSTER_EGG, 1), "§d§nBaby Pigs§7 (§a§lENABLED§7)"), 90));
				}
				else{
					p.getInventory().setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.MONSTER_EGG, 1), "§d§nBaby Pigs§7 (§c§lDISABLED§7)"), 90));
				}
				
				int pigage = 1;
				if(((Pig) getPet()).isAdult()){
					pigage = 2;
				}
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.PORK, pigage), "§d§nChange Age"));
				break;
			case SHEEP:
				if(hasPetSheepDisco() == true){
					p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.WOOL, 1), "§f§nSheep Disco§7 (§a§lENABLED§7)"));
				}
				else{
					p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.WOOL, 1), "§f§nSheep Disco§7 (§c§lDISABLED§7)"));
				}
				
				DyeColor dyecolor = ((Sheep) getPet()).getColor();
				p.getInventory().setItem(6, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.INK_SACK, 1), "§f§nChange Color§7 (" + Utils.getDyeColorName(dyecolor) + "§7)"), dyecolor.getDyeData()));
				break;
			case SILVERFISH:
				p.getInventory().setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.MONSTER_EGG, 1), "§7§nSilverfish Bomb"), 60));
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.STONE_HOE, 1), "§8§nLeap"));
				break;
			case SLIME:
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS, 1), "§6§nSuper Jump"));
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.SLIME_BALL, ((Slime) getPet()).getSize()), "§a§nChange Size"));
				break;
			case SPIDER:
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.WEB, 1), "§f§nWebs"));
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.SPIDER_EYE, 1), "§5§nSpider Launcher"));
				break;
			case SQUID:
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.INK_SACK, 1), "§8§nInk Bomb"));
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.WATER_BUCKET, 1), "§9§nWater Spout"));
				break;
			case WOLF:
				p.getInventory().setItem(2, Utils.setDisplayname(new ItemStack(Material.COOKED_BEEF, 1), "§6§nBark"));
				
				int wolfage = 1;
				if(((Wolf) getPet()).isAdult()){
					wolfage = 2;
				}
				p.getInventory().setItem(6, Utils.setDisplayname(new ItemStack(Material.BONE, wolfage), "§7§nChange Age"));
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
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
 
        try {
            out.writeUTF("Connect");
            out.writeUTF(server.toString().toLowerCase());
        } catch (IOException eee) {
         
        }
 
        getPlayer().sendPluginMessage(Start.getInstance(), "BungeeCord", b.toByteArray());
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
		}.runTaskLater(Start.getInstance(), seconds * 20);
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
		}.runTaskLater(Start.getInstance(), seconds * 20);
	}
	
	public boolean hasPerms(StaffRank rank){
		StaffRank staffrank = getStaffRank();
		
		if(staffrank != StaffRank.Owner){
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
				return true;
			}
		}
		else{
			return true;
		}
		return false;
	}
	
	public boolean hasPerms(VIPRank rank){
		VIPRank viprank = getVIPRank();
		
		if(getStaffRank() != StaffRank.Owner && !isOpMode()){
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
		}
		else{
			return true;
		}
		return false;
	}
	
	public void requiredVIPPoints(int price){
		Player p = getPlayer();
		
		p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
		int needed = price - getVIPPoints();
		if(needed == 1){
			p.sendMessage("§9Cosmetic Perks §8| §7You need §b§l" + needed + "§7 more §b§lVIP Point§7!");
		}
		else{
			p.sendMessage("§9Cosmetic Perks §8| §7You need §b§l" + needed + "§7 more §b§lVIP Points§7!");
		}
	}
	
	public void logOut(){
		if(Database.get().containsPath("PlayerNicknames", "uuid", "uuid", getUUID().toString())){
			if(getNickname() != null){
				Database.get().update("PlayerNicknames", "nick", getNickname(), "uuid", getUUID().toString());
			}
			else{
				Database.get().delete("PlayerNicknames", "uuid", getUUID().toString());
			}
		}
		else{
			if(getNickname() != null){
				Database.get().insert("PlayerNicknames", "uuid`, `nick", getUUID().toString() + "', '" + getNickname());
			}
		}
		
		Database.get().update("Fireworks-Settings", "settings", getFWSettings().toString(), "uuid", getUUID().toString());
		Database.get().update("Fireworks-Passes", "passes", "" + getFireworkPasses(), "uuid", getUUID().toString());
		Database.get().update("Trails-TypeParticlesAmount", "amount", "" + getTrailPA(), "uuid", getUUID().toString());
		Database.get().update("Trails-TypeSpecial", "special", "" + hasSpecialTrail(), "uuid", getUUID().toString());
		Database.get().update("Trails-Type", "type", getTrailType().toString(), "uuid", getUUID().toString());
		
		if(isDisguised()){
			undisguise();
		}
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
	
	public void disguiseAsMob(EntityType type, Player... players){
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
	        
	        setDisguised(true);
	        setDisguiseEntityType(type);
	        
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
    	
        for(Player player : Bukkit.getOnlinePlayers()){
    		player.hidePlayer(p);
    		player.showPlayer(p);
        }
    }
	
	public UUID getUUID(){
		return getPlayer().getUniqueId();
	}
	
	public static List<OMPlayer> getOMPlayers(){
		return ServerStorage.omplayers;
	}
	
	public static OMPlayer getOMPlayer(Player player){
		OMPlayer omp = ServerStorage.players.get(player);
		if(omp != null){
			return omp;
		}
		for(OMPlayer omplayer : ServerStorage.omplayers){
			if(omplayer.getPlayer() == player){
				return omplayer;
			}
		}
		return null;
	}
	
	public static OMPlayer addOMPlayer(Player player, boolean loaded){
		OMPlayer omplayer = new OMPlayer(player, loaded);
		ServerStorage.omplayers.add(omplayer);
		return omplayer;
	}
	
	@SuppressWarnings("deprecation")
	public void load(){
		this.loaded = false;
		this.opmode = false;
		this.canchat = false;
		
		setTabList("§6§lOrbitMines§4§lNetwork", "§7§lWebsite: &6www.orbitmines.com §8| §7§lTwitter: &b@OrbitMines §8| §7§lDonate: &3shop.orbitmines.com");
		
		//TODO Give Lobby Items if necessary
		
		String uuid = getUUID().toString();
		
		try{
			if(Database.get().containsPath("PlayerSilentJoin", "uuid", "uuid", uuid)){
				this.silent = Boolean.parseBoolean(Database.get().getString("PlayerSilentJoin", "silentjoin", "uuid", uuid));
			}
			else{
				this.silent = false;
			}
			
			if(Database.get().containsPath("Rank-VIP", "uuid", "uuid", uuid)){
				this.viprank = VIPRank.valueOf(Database.get().getString("Rank-VIP", "vip", "uuid", uuid));
			}
			else{
				this.viprank = VIPRank.User;
			}
			
			if(Database.get().containsPath("Rank-Staff", "uuid", "uuid", uuid)){
				this.staffrank = StaffRank.valueOf(Database.get().getString("Rank-Staff", "staff", "uuid", uuid));
			}
			else{
				this.staffrank = StaffRank.User;
			}
		
			if(Database.get().containsPath("Votes", "uuid", "uuid", uuid)){
				this.votes = Database.get().getInt("Votes", "votes", "uuid", uuid);
			}
			else{
				Database.get().insert("Votes", "uuid`, `votes", uuid + "', '" + 0);
				this.votes = 0;
			}
		
			if(Database.get().containsPath("OrbitMinesTokens", "uuid", "uuid", uuid)){
				this.orbitminestokens = Database.get().getInt("OrbitMinesTokens", "omt", "uuid", uuid);
			}
			else{
				Database.get().insert("OrbitMinesTokens", "uuid`, `omt", uuid + "', '" + 0);
				this.orbitminestokens = 0;
			}
		
			if(Database.get().containsPath("VIPPoints", "uuid", "uuid", uuid)){
				this.vippoints = Database.get().getInt("VIPPoints", "points", "uuid", uuid);
			}
			else{
				Database.get().insert("VIPPoints", "uuid`, `points", uuid + "', '" + 0);
				this.vippoints = 0;
			}
		
			if(Database.get().containsPath("MiniGameCoins", "uuid", "uuid", uuid)){
				this.minigamecoins = Database.get().getInt("MiniGameCoins", "coins", "uuid", uuid);
			}
			else{
				Database.get().insert("MiniGameCoins", "uuid`, `coins", uuid + "', '" + 0);
				this.minigamecoins = 0;
			}
			
			{//TODO Change MiniGameKills, MiniGameLoses, MiniGameWins to ChickenFight-Kills, ChickenFight-Loeses, ChickenFight-Wins.
				int kills = 0;
				int wins = 0;
				int loses = 0;
				int beststreak = 0;
				List<ChickenFightKit> unlockedkits = new ArrayList<ChickenFightKit>();
				
				if(Database.get().containsPath("MiniGameKills", "uuid", "uuid", uuid)){
					kills = Database.get().getInt("MiniGameKills", "kills", "uuid", uuid);
				}
				else{
					Database.get().insert("MiniGameKills", "uuid`, `kills", uuid + "', '" + 0);
				}
				
				if(Database.get().containsPath("MiniGameWins", "uuid", "uuid", uuid)){
					wins = Database.get().getInt("MiniGameWins", "wins", "uuid", uuid);
				}
				else{
					Database.get().insert("MiniGameWins", "uuid`, `wins", uuid + "', '" + 0);
				}
				
				if(Database.get().containsPath("MiniGameLoses", "uuid", "uuid", uuid)){
					loses = Database.get().getInt("MiniGameLoses", "loses", "uuid", uuid);
				}
				else{
					Database.get().insert("MiniGameLoses", "uuid`, `loses", uuid + "', '" + 0);
				}
				
				if(Database.get().containsPath("ChickenFight-BestStreak", "uuid", "uuid", uuid)){
					beststreak = Database.get().getInt("ChickenFight-BestStreak", "beststreak", "uuid", uuid);
				}
				else{
					Database.get().insert("ChickenFight-BestStreak", "uuid`, `beststreak", uuid + "', '" + 0);
				}
				
				unlockedkits.add(ChickenFightKit.CHICKEN_MAMA);
				
				if(Database.get().containsPath("ChickenFight-BabyChicken", "uuid", "uuid", uuid)){
					unlockedkits.add(ChickenFightKit.BABY_CHICKEN);
				}
				if(Database.get().containsPath("ChickenFight-HotWing", "uuid", "uuid", uuid)){
					unlockedkits.add(ChickenFightKit.HOT_WING);
				}
				if(Database.get().containsPath("ChickenFight-ChickenWarrior", "uuid", "uuid", uuid)){
					unlockedkits.add(ChickenFightKit.CHICKEN_WARRIOR);
				}
				
				this.cfplayer = ChickenFightPlayer.addCFPlayer(this.player, kills, wins, loses, beststreak, unlockedkits);
			}
			
			{
				int kills = 0;
				int wins = 0;
				int loses = 0;
				int beststreak = 0;
				
				if(Database.get().containsPath("SurvivalGames-Kills", "uuid", "uuid", uuid)){
					kills = Database.get().getInt("SurvivalGames-Kills", "kills", "uuid", uuid);
				}
				else{
					Database.get().insert("SurvivalGames-Kills", "uuid`, `kills", uuid + "', '" + 0);
				}
				
				if(Database.get().containsPath("SurvivalGames-Wins", "uuid", "uuid", uuid)){
					wins = Database.get().getInt("SurvivalGames-Wins", "wins", "uuid", uuid);
				}
				else{
					Database.get().insert("SurvivalGames-Wins", "uuid`, `wins", uuid + "', '" + 0);
				}
				
				if(Database.get().containsPath("SurvivalGames-Loses", "uuid", "uuid", uuid)){
					loses = Database.get().getInt("SurvivalGames-Loses", "loses", "uuid", uuid);
				}
				else{
					Database.get().insert("SurvivalGames-Loses", "uuid`, `loses", uuid + "', '" + 0);
				}
				
				if(Database.get().containsPath("SurvivalGames-BestStreak", "uuid", "uuid", uuid)){
					beststreak = Database.get().getInt("SurvivalGames-BestStreak", "beststreak", "uuid", uuid);
				}
				else{
					Database.get().insert("SurvivalGames-BestStreak", "uuid`, `beststreak", uuid + "', '" + 0);
				}
				
				this.sgplayer = SurvivalGamesPlayer.addSGPlayer(this.player, kills, wins, loses, beststreak);
			}
			
			if(Database.get().containsPath("Fireworks-Passes", "uuid", "uuid", uuid)){
				this.fireworkpasses = Database.get().getInt("Fireworks-Passes", "passes", "uuid", uuid);
			}
			else{
				Database.get().insert("Fireworks-Passes", "uuid`, `passes", uuid + "', '" + 0);
				this.fireworkpasses = 0;
			}
			
			this.gadgets = new ArrayList<Gadget>();
			if(Database.get().containsPath("Gadgets-Paintballs", "uuid", "uuid", uuid)){
				this.gadgets.add(Gadget.PAINTBALLS);
			}
			if(Database.get().containsPath("Gadgets-CreeperLauncher", "uuid", "uuid", uuid)){
				this.gadgets.add(Gadget.CREEPER_LAUNCHER);
			}
			if(Database.get().containsPath("Gadgets-PetRide", "uuid", "uuid", uuid)){
				this.gadgets.add(Gadget.PET_RIDE);
			}
			if(Database.get().containsPath("Gadgets-BookExplosion", "uuid", "uuid", uuid)){
				this.gadgets.add(Gadget.BOOK_EXPLOSION);
			}
			if(Database.get().containsPath("Gadgets-SwapTeleporter", "uuid", "uuid", uuid)){
				this.gadgets.add(Gadget.SWAP_TELEPORTER);
			}
			if(Database.get().containsPath("Gadgets-MagmaCubeSoccer", "uuid", "uuid", uuid)){
				this.gadgets.add(Gadget.MAGMACUBE_SOCCER);
			}
			if(Database.get().containsPath("Gadgets-SnowmanAttack", "uuid", "uuid", uuid)){
				this.gadgets.add(Gadget.SNOWMAN_ATTACK);
			}
			
			this.pets = new ArrayList<Pet>();
			this.petnames = new HashMap<Pet, String>();
			if(Database.get().containsPath("Pets-MushroomCow", "uuid", "uuid", uuid)){
				this.pets.add(Pet.MUSHROOM_COW);
				this.petnames.put(Pet.MUSHROOM_COW, Database.get().getString("Pets-MushroomCow", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Pig", "uuid", "uuid", uuid)){
				this.pets.add(Pet.PIG);
				this.petnames.put(Pet.PIG, Database.get().getString("Pets-Pig", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Wolf", "uuid", "uuid", uuid)){
				this.pets.add(Pet.WOLF);
				this.petnames.put(Pet.WOLF, Database.get().getString("Pets-Wolf", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Sheep", "uuid", "uuid", uuid)){
				this.pets.add(Pet.SHEEP);
				this.petnames.put(Pet.SHEEP, Database.get().getString("Pets-Sheep", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Horse", "uuid", "uuid", uuid)){
				this.pets.add(Pet.HORSE);
				this.petnames.put(Pet.HORSE, Database.get().getString("Pets-Horse", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-MagmaCube", "uuid", "uuid", uuid)){
				this.pets.add(Pet.MAGMA_CUBE);
				this.petnames.put(Pet.MAGMA_CUBE, Database.get().getString("Pets-MagmaCube", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Slime", "uuid", "uuid", uuid)){
				this.pets.add(Pet.SLIME);
				this.petnames.put(Pet.SLIME, Database.get().getString("Pets-Slime", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Cow", "uuid", "uuid", uuid)){
				this.pets.add(Pet.COW);
				this.petnames.put(Pet.COW, Database.get().getString("Pets-Cow", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Silverfish", "uuid", "uuid", uuid)){
				this.pets.add(Pet.SILVERFISH);
				this.petnames.put(Pet.SILVERFISH, Database.get().getString("Pets-Silverfish", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Ocelot", "uuid", "uuid", uuid)){
				this.pets.add(Pet.OCELOT);
				this.petnames.put(Pet.OCELOT, Database.get().getString("Pets-Ocelot", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Creeper", "uuid", "uuid", uuid)){
				this.pets.add(Pet.CREEPER);
				this.petnames.put(Pet.CREEPER, Database.get().getString("Pets-Creeper", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Squid", "uuid", "uuid", uuid)){
				this.pets.add(Pet.SQUID);
				this.petnames.put(Pet.SQUID, Database.get().getString("Pets-Squid", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Spider", "uuid", "uuid", uuid)){
				this.pets.add(Pet.SPIDER);
				this.petnames.put(Pet.SPIDER, Database.get().getString("Pets-Spider", "petname", "uuid", uuid));
			}
			if(Database.get().containsPath("Pets-Chicken", "uuid", "uuid", uuid)){
				this.pets.add(Pet.CHICKEN);
				this.petnames.put(Pet.CHICKEN, Database.get().getString("Pets-Chicken", "petname", "uuid", uuid));
			}
			
			this.wardrobe = new ArrayList<Color>();
			if(Database.get().containsPath("Wardrobe-White", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.WHITE);
			}
			if(Database.get().containsPath("Wardrobe-Blue", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.BLUE);
			}
			if(Database.get().containsPath("Wardrobe-Green", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.GREEN);
			}
			if(Database.get().containsPath("Wardrobe-Black", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.BLACK);
			}
			if(Database.get().containsPath("Wardrobe-LightBlue", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.AQUA);
			}
			if(Database.get().containsPath("Wardrobe-Pink", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.FUCHSIA);
			}
			if(Database.get().containsPath("Wardrobe-LightGreen", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.LIME);
			}
			if(Database.get().containsPath("Wardrobe-DarkBlue", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.NAVY);
			}
			if(Database.get().containsPath("Wardrobe-Purple", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.PURPLE);
			}
			if(Database.get().containsPath("Wardrobe-Orange", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.ORANGE);
			}
			if(Database.get().containsPath("Wardrobe-Red", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.RED);
			}
			if(Database.get().containsPath("Wardrobe-Cyan", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.TEAL);
			}
			if(Database.get().containsPath("Wardrobe-Yellow", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.YELLOW);
			}
			if(Database.get().containsPath("Wardrobe-Gray", "uuid", "uuid", uuid)){
				this.wardrobe.add(Color.GRAY);
			}
			
			if(Database.get().containsPath("Wardrobe-Disco", "uuid", "uuid", uuid)){
				this.wardrobedisco = Boolean.parseBoolean(Database.get().getString("Wardrobe-Disco", "disco", "uuid", uuid));
				this.unlockedwardrobedisco = true;
			}
			else{
				this.wardrobedisco = false;
				this.unlockedwardrobedisco = false;
			}
			
			this.chatcolors = new ArrayList<ChatColor>();
			if(Database.get().containsPath("ChatColors-DarkRed", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.DARK_RED);
			}
			if(Database.get().containsPath("ChatColors-LightGreen", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.LIGHT_GREEN);
			}
			if(Database.get().containsPath("ChatColors-DarkGray", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.DARK_GRAY);
			}
			if(Database.get().containsPath("ChatColors-Red", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.RED);
			}
			if(Database.get().containsPath("ChatColors-White", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.WHITE);
			}
			if(Database.get().containsPath("ChatColors-LightBlue", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.LIGHT_BLUE);
			}
			if(Database.get().containsPath("ChatColors-Pink", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.PINK);
			}
			if(Database.get().containsPath("ChatColors-Blue", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.BLUE);
			}
			if(Database.get().containsPath("ChatColors-DarkBlue", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.DARK_BLUE);
			}
			if(Database.get().containsPath("ChatColors-Green", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.GREEN);
			}
			if(Database.get().containsPath("ChatColors-Black", "uuid", "uuid", uuid)){
				this.chatcolors.add(ChatColor.BLACK);
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
			
			this.trails = new ArrayList<Trail>();
			if(Database.get().containsPath("Trails-AngryVillager", "uuid", "uuid", uuid)){
				this.trails.add(Trail.ANGRY_VILLAGER);
			}
			if(Database.get().containsPath("Trails-Hearts", "uuid", "uuid", uuid)){
				this.trails.add(Trail.HEART);
			}
			if(Database.get().containsPath("Trails-Bubble", "uuid", "uuid", uuid)){
				this.trails.add(Trail.BUBBLE);
			}
			if(Database.get().containsPath("Trails-Crit", "uuid", "uuid", uuid)){
				this.trails.add(Trail.CRIT);
			}
			if(Database.get().containsPath("Trails-ETable", "uuid", "uuid", uuid)){
				this.trails.add(Trail.ENCHANTMENT_TABLE);
			}
			if(Database.get().containsPath("Trails-Explode", "uuid", "uuid", uuid)){
				this.trails.add(Trail.TNT);
			}
			if(Database.get().containsPath("Trails-Firework", "uuid", "uuid", uuid)){
				this.trails.add(Trail.FIREWORK_SPARK);
			}
			if(Database.get().containsPath("Trails-HappyVillager", "uuid", "uuid", uuid)){
				this.trails.add(Trail.HAPPY_VILLAGER);
			}
			if(Database.get().containsPath("Trails-MobSpawner", "uuid", "uuid", uuid)){
				this.trails.add(Trail.MOB_SPAWNER);
			}
			if(Database.get().containsPath("Trails-Music", "uuid", "uuid", uuid)){
				this.trails.add(Trail.MUSIC);
			}
			if(Database.get().containsPath("Trails-Slime", "uuid", "uuid", uuid)){
				this.trails.add(Trail.SLIME);
			}
			if(Database.get().containsPath("Trails-Smoke", "uuid", "uuid", uuid)){
				this.trails.add(Trail.SMOKE);
			}
			if(Database.get().containsPath("Trails-Snow", "uuid", "uuid", uuid)){
				this.trails.add(Trail.SNOW);
			}
			if(Database.get().containsPath("Trails-Water", "uuid", "uuid", uuid)){
				this.trails.add(Trail.WATER);
			}
			if(Database.get().containsPath("Trails-Witch", "uuid", "uuid", uuid)){
				this.trails.add(Trail.WITCH);
			}
			
			if(Database.get().containsPath("Trails-Type", "uuid", "uuid", uuid)){
				this.trailtype = TrailType.valueOf(Database.get().getString("Trails-Type", "type", "uuid", uuid));
			}
			else{
				Database.get().insert("Trails-Type", "uuid`, `type", uuid + "', '" + TrailType.BASIC_TRAIL.toString());
				this.trailtype = TrailType.BASIC_TRAIL;
			}
			
			this.trailtypes = new ArrayList<TrailType>();
			if(Database.get().containsPath("Trails-TypeBig", "uuid", "uuid", uuid)){
				this.trailtypes.add(TrailType.BIG_TRAIL);
			}
			if(Database.get().containsPath("Trails-TypeBody", "uuid", "uuid", uuid)){
				this.trailtypes.add(TrailType.BODY_TRAIL);
			}
			if(Database.get().containsPath("Trails-TypeGround", "uuid", "uuid", uuid)){
				this.trailtypes.add(TrailType.GROUND_TRAIL);
			}
			if(Database.get().containsPath("Trails-TypeHead", "uuid", "uuid", uuid)){
				this.trailtypes.add(TrailType.HEAD_TRAIL);
			}
			if(Database.get().containsPath("Trails-TypeVertical", "uuid", "uuid", uuid)){
				this.trailtypes.add(TrailType.VERTICAL_TRAIL);
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
			
			if(Database.get().containsPath("ChatColors", "uuid", "uuid", uuid)){
				this.chatcolor = ChatColor.valueOf(Database.get().getString("ChatColors", "color", "uuid", uuid));
			}
			else{
				Database.get().insert("ChatColors", "uuid`, `color", uuid + "', '" + ChatColor.GRAY.toString());
				this.chatcolor = ChatColor.GRAY;
			}
			
			this.hats = new ArrayList<Hat>();
			if(Database.get().containsPath("Hats-Bedrock", "uuid", "uuid", uuid)){
				this.hats.add(Hat.BEDROCK);
			}
			if(Database.get().containsPath("Hats-BlackGlass", "uuid", "uuid", uuid)){
				this.hats.add(Hat.BLACK_GLASS);
			}
			if(Database.get().containsPath("Hats-Cactus", "uuid", "uuid", uuid)){
				this.hats.add(Hat.CACTUS);
			}
			if(Database.get().containsPath("Hats-CoalBlock", "uuid", "uuid", uuid)){
				this.hats.add(Hat.COAL_BLOCK);
			}
			if(Database.get().containsPath("Hats-CoalOre", "uuid", "uuid", uuid)){
				this.hats.add(Hat.COAL_ORE);
			}
			if(Database.get().containsPath("Hats-Furnace", "uuid", "uuid", uuid)){
				this.hats.add(Hat.FURNACE);
			}
			if(Database.get().containsPath("Hats-Glass", "uuid", "uuid", uuid)){
				this.hats.add(Hat.GLASS);
			}
			if(Database.get().containsPath("Hats-Grass", "uuid", "uuid", uuid)){
				this.hats.add(Hat.GRASS);
			}
			if(Database.get().containsPath("Hats-GreenGlass", "uuid", "uuid", uuid)){
				this.hats.add(Hat.GREEN_GLASS);
			}
			if(Database.get().containsPath("Hats-HayBale", "uuid", "uuid", uuid)){
				this.hats.add(Hat.HAY_BALE);
			}
			if(Database.get().containsPath("Hats-Ice", "uuid", "uuid", uuid)){
				this.hats.add(Hat.ICE);
			}
			if(Database.get().containsPath("Hats-LapisBlock", "uuid", "uuid", uuid)){
				this.hats.add(Hat.LAPIS_BLOCK);
			}
			if(Database.get().containsPath("Hats-LapisOre", "uuid", "uuid", uuid)){
				this.hats.add(Hat.LAPIS_ORE);
			}
			if(Database.get().containsPath("Hats-Leaves", "uuid", "uuid", uuid)){
				this.hats.add(Hat.LEAVES);
			}
			if(Database.get().containsPath("Hats-MagentaGlass", "uuid", "uuid", uuid)){
				this.hats.add(Hat.MAGENTA_GLASS);
			}
			if(Database.get().containsPath("Hats-Melon", "uuid", "uuid", uuid)){
				this.hats.add(Hat.MELON);
			}
			if(Database.get().containsPath("Hats-Mycelium", "uuid", "uuid", uuid)){
				this.hats.add(Hat.MYCELIUM);
			}
			if(Database.get().containsPath("Hats-OrangeGlass", "uuid", "uuid", uuid)){
				this.hats.add(Hat.ORANGE_GLASS);
			}
			if(Database.get().containsPath("Hats-QuartzBlock", "uuid", "uuid", uuid)){
				this.hats.add(Hat.QUARTZ_BLOCK);
			}
			if(Database.get().containsPath("Hats-QuartzOre", "uuid", "uuid", uuid)){
				this.hats.add(Hat.QUARTZ_ORE);
			}
			if(Database.get().containsPath("Hats-RedGlass", "uuid", "uuid", uuid)){
				this.hats.add(Hat.RED_GLASS);
			}
			if(Database.get().containsPath("Hats-RedstoneBlock", "uuid", "uuid", uuid)){
				this.hats.add(Hat.REDSTONE_BLOCK);
			}
			if(Database.get().containsPath("Hats-RedstoneOre", "uuid", "uuid", uuid)){
				this.hats.add(Hat.REDSTONE_ORE);
			}
			if(Database.get().containsPath("Hats-Snow", "uuid", "uuid", uuid)){
				this.hats.add(Hat.SNOW);
			}
			if(Database.get().containsPath("Hats-StoneBricks", "uuid", "uuid", uuid)){
				this.hats.add(Hat.STONE_BRICKS);
			}
			if(Database.get().containsPath("Hats-TNT", "uuid", "uuid", uuid)){
				this.hats.add(Hat.TNT);
			}
			if(Database.get().containsPath("Hats-Workbench", "uuid", "uuid", uuid)){
				this.hats.add(Hat.WORKBENCH);
			}
			if(Database.get().containsPath("Hats-Diorite", "uuid", "uuid", uuid)){
				this.hats.add(Hat.DIORITE);
			}
			if(Database.get().containsPath("Hats-DarkPrismarine", "uuid", "uuid", uuid)){
				this.hats.add(Hat.DARK_PRISMARINE);
			}
			if(Database.get().containsPath("Hats-Sponge", "uuid", "uuid", uuid)){
				this.hats.add(Hat.SPONGE);
			}
			if(Database.get().containsPath("Hats-SlimeBlock", "uuid", "uuid", uuid)){
				this.hats.add(Hat.SLIME_BLOCK);
			}
			if(Database.get().containsPath("Hats-SeaLantern", "uuid", "uuid", uuid)){
				this.hats.add(Hat.SEA_LANTERN);
			}
			if(Database.get().containsPath("Hats-PrismarineBricks", "uuid", "uuid", uuid)){
				this.hats.add(Hat.PRISMARINE_BRICKS);
			}
			if(Database.get().containsPath("Hats-Granite", "uuid", "uuid", uuid)){
				this.hats.add(Hat.GRANITE);
			}
			if(Database.get().containsPath("Hats-Chest", "uuid", "uuid", uuid)){
				this.hats.add(Hat.CHEST);
			}
			if(Database.get().containsPath("Hats-Glowstone", "uuid", "uuid", uuid)){
				this.hats.add(Hat.GLOWSTONE);
			}
			if(Database.get().containsPath("Hats-WetSponge", "uuid", "uuid", uuid)){
				this.hats.add(Hat.WET_SPONGE);
			}
			if(Database.get().containsPath("Hats-Andesite", "uuid", "uuid", uuid)){
				this.hats.add(Hat.ANDESITE);
			}
			if(Database.get().containsPath("Hats-BlueGlass", "uuid", "uuid", uuid)){
				this.hats.add(Hat.BLUE_GLASS);
			}
			
			if(Database.get().containsPath("Hats-BlockTrail", "uuid", "uuid", uuid)){
				this.hatsblocktrail = Boolean.parseBoolean(Database.get().getString("Hats-BlockTrail", "blocktrail", "uuid", uuid));
				this.unlockedhatsblocktrail = true;
			}
			else{
				this.hatsblocktrail = false;
				this.unlockedhatsblocktrail = false;
			}
			
			this.disguises = new ArrayList<Disguise>();
			if(Database.get().containsPath("Dis-Witch", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.WITCH);
			}
			if(Database.get().containsPath("Dis-Bat", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.BAT);
			}
			if(Database.get().containsPath("Dis-Chicken", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.CHICKEN);
			}
			if(Database.get().containsPath("Dis-Ocelot", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.OCELOT);
			}
			if(Database.get().containsPath("Dis-MushroomCow", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.MUSHROOM_COW);
			}
			if(Database.get().containsPath("Dis-Squid", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.SQUID);
			}
			if(Database.get().containsPath("Dis-Slime", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.SLIME);
			}
			if(Database.get().containsPath("Dis-ZombiePigman", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.ZOMBIE_PIGMAN);
			}
			if(Database.get().containsPath("Dis-MagmaCube", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.MAGMA_CUBE);
			}
			if(Database.get().containsPath("Dis-Skeleton", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.SKELETON);
			}
			if(Database.get().containsPath("Dis-Wolf", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.WOLF);
			}
			if(Database.get().containsPath("Dis-Spider", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.SPIDER);
			}
			if(Database.get().containsPath("Dis-Silverfish", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.SILVERFISH);
			}
			if(Database.get().containsPath("Dis-Sheep", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.SHEEP);
			}
			if(Database.get().containsPath("Dis-CaveSpider", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.CAVE_SPIDER);
			}
			if(Database.get().containsPath("Dis-Creeper", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.CREEPER);
			}
			if(Database.get().containsPath("Dis-Cow", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.COW);
			}
			if(Database.get().containsPath("Dis-Enderman", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.ENDERMAN);
			}
			if(Database.get().containsPath("Dis-Horse", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.HORSE);
			}
			if(Database.get().containsPath("Dis-IronGolem", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.IRON_GOLEM);
			}
			if(Database.get().containsPath("Dis-Ghast", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.GHAST);
			}
			if(Database.get().containsPath("Dis-Snowman", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.SNOWMAN);
			}
			if(Database.get().containsPath("Dis-Rabbit", "uuid", "uuid", uuid)){
				this.disguises.add(Disguise.RABBIT);
			}
			if(Database.get().containsPath("ParkourCompleted", "uuid", "uuid", uuid)){
				this.completedlapisparkour = true;
			}
			else{
				this.completedlapisparkour = false;
			}
			
			{
				int wins = 0;
				int bestgame = -1;
				
				if(Database.get().containsPath("MasterMind-Wins", "uuid", "uuid", uuid)){
					wins = Database.get().getInt("MasterMind-Wins", "wins", "uuid", uuid);
				}
				else{
					Database.get().insert("MasterMind-Wins", "uuid`, `wins", uuid + "', '" + 0);
				}
				
				if(Database.get().containsPath("MasterMind-BestGame", "uuid", "uuid", uuid)){
					bestgame = Database.get().getInt("MasterMind-BestGame", "turns", "uuid", uuid);
				}
				
				this.mcplayer = MindCraftPlayer.addMCPlayer(this.player, wins, bestgame);
			}

			if(Database.get().containsPath("Hub-Players", "uuid", "uuid", uuid)){
				this.hubplayersenabled = Boolean.parseBoolean(Database.get().getString("Hub-Players", "players", "uuid", uuid));
			}
			else{
				Database.get().insert("Hub-Players", "uuid`, `players", uuid + "', '" + false);
				this.hubplayersenabled = false;
			}

			if(Database.get().containsPath("Hub-Stacker", "uuid", "uuid", uuid)){
				this.hubstackerenabled = Boolean.parseBoolean(Database.get().getString("Hub-Stacker", "stacker", "uuid", uuid));
			}
			else{
				Database.get().insert("Hub-Stacker", "uuid`, `stacker", uuid + "', '" + true);
				this.hubstackerenabled = true;
			}

			if(Database.get().containsPath("Hub-Scoreboard", "uuid", "uuid", uuid)){
				this.hubscoreboardenabled = Boolean.parseBoolean(Database.get().getString("Hub-Scoreboard", "scoreboard", "uuid", uuid));
			}
			else{
				Database.get().insert("Hub-Scoreboard", "uuid`, `scoreboard", uuid + "', '" + true);
				this.hubscoreboardenabled = true;
			}
			
			for(final OMPlayer omplayer : getOMPlayers()){
				if(!omplayer.hubplayersenabled){
					omplayer.getPlayer().hidePlayer(getPlayer());
				}
				else{
					omplayer.getPlayer().showPlayer(getPlayer());
					
					if(omplayer.isDisguised()){
						if(omplayer.getDisguiseEntityType() != null){
			    			new BukkitRunnable(){
			    				public void run(){
				        			omplayer.disguiseAsMob(omplayer.getDisguiseEntityType(), getPlayer());			    				
				        		}
			    			}.runTaskLater(Start.getInstance(), 40);
						}
						else{
			    			new BukkitRunnable(){
			    				public void run(){
				        			omplayer.disguiseAsBlock(omplayer.getDisguiseBlockID(), getPlayer());	    				
				        		}
			    			}.runTaskLater(Start.getInstance(), 40);
						}
					}
				}
			}
			
			if(Database.get().containsPath("PlayerNicknames", "uuid", "uuid", uuid)){
				this.nickname = Database.get().getString("PlayerNicknames", "nick", "uuid", uuid);
			}
			
			this.hatsinvpage = 1;
			
			if(Database.get().containsPath("Fireworks-Settings", "uuid", "uuid", uuid)){
				String[] fwsettings = Database.get().getString("Fireworks-Settings", "settings", "uuid", uuid).split("\\|");
				if(fwsettings[6].equals("null")){fwsettings[6] = "BALL";}
				this.fwsettings = new FireworkSettings(this, Utils.parseColor(fwsettings[0]), Utils.parseColor(fwsettings[1]), Utils.parseColor(fwsettings[2]), Utils.parseColor(fwsettings[3]), Boolean.parseBoolean(fwsettings[4]), Boolean.parseBoolean(fwsettings[5]), Type.valueOf(fwsettings[6]));
			}
			else{
				Database.get().insert("Fireworks-Settings", "uuid`, `settings", uuid + "', '" + "AQUA|null|null|null|false|false|BALL");
				this.fwsettings = new FireworkSettings(this, Color.AQUA, null, null, null, false, false, Type.BALL);
			}
			
			new VoteManager().check(this);
			
			if(ServerData.isServer(Server.HUB)){
				Title t = new Title("§6§lOrbitMines§4§lNetwork", "");
				t.send(getPlayer());
			}
			else{
				Title t = new Title("§6§lOrbitMines" + ServerData.getServer().getName(), "");
				t.send(getPlayer());
			}
			
			final Player p = getPlayer();
			
			new BukkitRunnable(){
				public void run(){
					p.sendMessage("§7§m----------------------------------------");
					p.sendMessage(" §6§lOrbitMines§4§lNetwork §7- §3§lHub");
					p.sendMessage(" ");
					p.sendMessage(" §7§lWebsite: §6www.orbitmines.com");
					p.sendMessage(" §7§lDonate: §3shop.orbitmines.com");
					p.sendMessage(" §7§lVote: §bwww.orbitmines.com/vote");
					p.sendMessage(" ");
					
					ComponentMessage cm = new ComponentMessage();
					cm.addPart(" §7§lHub Spawn Built By: ", null, null, null, null);
					cm.addPart("§e§l[View]", null, null, Action.SHOW_TEXT, "§b§lMod §bsharewoods\n§b§lMod §beekhoorn2000\n§d§lBuilder §drienk222\n§d§lBuilder §dcasidas\n§4§lOwner §4O_o_Fadi_o_O");
					cm.send(p);
					
					p.sendMessage(" §7§lDeveloped By: §4§lOwner §4O_o_Fadi_o_O");
					p.sendMessage("§7§m----------------------------------------");
				}
			}.runTaskLater(Start.getInstance(), 20);
			
			if(hasPerms(StaffRank.Builder)){
				World builderworld = ServerData.getHub().getBuilderWorld();
				Start.getInstance().permission.playerAddTransient(builderworld.getName(), p.getName(), "command.give.other");
				Start.getInstance().permission.playerAddTransient(builderworld.getName(), p.getName(), "command.skull");
				Start.getInstance().permission.playerAddTransient(builderworld.getName(), p.getName(), "command.fly");
				Start.getInstance().permission.playerAddTransient(builderworld.getName(), p.getName(), "command.gamemode");
				Start.getInstance().permission.playerAddTransient(builderworld.getName(), p.getName(), "worldedit.*");
			}
			
			this.loaded = true;
			
		}catch(Exception ex){ex.printStackTrace();}
	}
}
