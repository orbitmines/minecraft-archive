package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OMHub.Hub;
import me.O_o_Fadi_o_O.OMHub.managers.ScoreboardManager;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.ChatColor;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Disguise;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Gadget;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Hat;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Pet;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.StaffRank;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Trail;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.TrailType;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.VIPRank;

import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
	private boolean chatcolorbold;
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
	
	private boolean petshroomtrail;
	private boolean petbabypigs;
	private List<Entity> petbabypigentities;
	private boolean petsheepdisco;
	private List<Pet> pets;
	private HashMap<Pet, String> petnames;
	private Entity pet;

	private TrailType trailtype;
	private List<Trail> trails;
	private int trailparticlesamount;
	private boolean specialtrail;
	private List<TrailType> trailtypes;
	
	private List<Color> wardrobe;
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

	public boolean isBold(){
		return chatcolorbold;
	}
	public void setBold(boolean chatcolorbold){
		this.chatcolorbold = chatcolorbold;
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

	public int getHatsInvPage(){
		return hatsinvpage;
	}
	public void setHatsInvPage(int hatsinvpage){
		this.hatsinvpage = hatsinvpage;
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

	public HashMap<Pet, String> getPetNames(){
		return petnames;
	}
	public void setPetNames(HashMap<Pet, String> petnames){
		this.petnames = petnames;
	}
	public void setPetName(Pet pet, String petname){
		this.petnames.put(pet, petname);
	}

	public Entity getPet(){
		return pet;
	}
	public void setPet(Entity pet){
		this.pet = pet;
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

	public List<TrailType> getTrailTypes(){
		return trailtypes;
	}
	public void setTrailTypes(List<TrailType> trailtypes){
		this.trailtypes = trailtypes;
	}
	public void addTrailType(TrailType trailtype){
		this.trailtypes.add(trailtype);
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

	public boolean isStackerEnabled(){
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

	public int getVIPPoints(){
		return vippoints;
	}
	public void setVIPPoints(int vippoints){
		this.vippoints = vippoints;
	}
	public void addVIPPoints(int vippoints){
		this.vippoints = this.vippoints +vippoints;
	}

	public int getOrbitMinesTokens(){
		return orbitminestokens;
	}
	public void setOrbitMinesTokens(int orbitminestokens){
		this.orbitminestokens = orbitminestokens;
	}
	public void addOrbitMinesTokens(int orbitminestokens){
		this.orbitminestokens = this.orbitminestokens +orbitminestokens;
	}

	public int getMiniGameCoins(){
		return minigamecoins;
	}
	public void setMiniGameCoins(int minigamecoins){
		this.minigamecoins = minigamecoins;
	}
	public void addMiniGameCoins(int minigamecoins){
		this.minigamecoins = this.minigamecoins +minigamecoins;
	}
	
	public void setScoreboard(){
		ScoreboardManager.setScoreboard(this);
	}
	
	public void vote(){
		//TODO
	}
	
	public void setBossBar(){
		
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
		}.runTaskLater(Hub.getInstance(), seconds * 20);
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
		}.runTaskLater(Hub.getInstance(), seconds * 20);
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
}
