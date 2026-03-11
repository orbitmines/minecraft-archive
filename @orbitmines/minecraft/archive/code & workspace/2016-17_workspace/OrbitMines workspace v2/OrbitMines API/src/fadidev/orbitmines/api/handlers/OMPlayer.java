package fadidev.orbitmines.api.handlers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.handlers.chat.ComponentMessage;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.handlers.firework.FireworkSettings;
import fadidev.orbitmines.api.handlers.scoreboard.Scoreboard;
import fadidev.orbitmines.api.handlers.scoreboard.ScoreboardSet;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.nms.customitem.CustomItemNms;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.api.utils.enums.perks.ChatColor;
import fadidev.orbitmines.api.utils.enums.perks.Color;
import fadidev.orbitmines.api.utils.enums.perks.*;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by Fadi on 3-9-2016.
 */
public abstract class OMPlayer {

    private static OrbitMinesAPI api;

    /* Player */
    private Player player;
    private boolean opMode;
    private Language language;
    private Scoreboard scoreboard;
    private OMInventory lastInventory;
    private boolean stackerEnabled;
    private boolean playersEnabled;
    private Location tpLocation;

    /* Login */
    private boolean newPlayer;
    private boolean silent;
    private boolean loaded;
    private boolean receivedMonthlyBonus;
    private Location lastLocation;

    /* Afk */
    private boolean afk;
    private String afkName;

    /* Nickname */
    private String nickName;

    /* Ranks */
    private VIPRank vipRank;
    private StaffRank staffRank;

    /* Friends */
    private List<UUID> friends;

    /* Cooldowns */
    private Map<Cooldown, Long> cooldowns;

    /* Currencies */
    private int votes;
    private int vipPoints;
    private int orbitMinesTokens;

    /*
    * Below Cosmetic Perks:
     */

    /* ChatColor */
    private ChatColor chatColor;
    private List<ChatColor> chatColors;
    private boolean unlockedChatColorBold;
    private boolean chatColorBold;
    private boolean unlockedChatColorCursive;
    private boolean chatColorCursive;

    /* Disguise */
    private List<Disguise> disguises;
    private boolean disguised;
    private int disguiseBlockId;
    private EntityType disguiseEntityType;
    private boolean disguisedBaby;
    private Entity disguise;

    /* Gadgets */
    private List<Gadget> gadgets;
    private Entity soccerMagmaCube;
    private Entity swapTeleporter;
    private int sgaSeconds;
    private Entity sgaItem;
    private List<Entity> sgaSnowGolems;

    /* Hats */
    private List<Hat> hats;
    private int hatsInvPage;
    private boolean unlockedHatsBlockTrail;
    private boolean hatsBlockTrail;

    /* Pets */
    private boolean petShroomTrail;
    private boolean petBabyPigs;
    private List<Entity> petBabyPigEntities;
    private boolean petSheepDisco;
    private List<Pet> pets;
    private Map<Pet, String> petNames;
    private Entity pet;
    private Pet petEnabled;

    /* Trails */
    private Trail trail;
    private TrailType trailType;
    private List<Trail> trails;
    private int trailParticleAmount;
    private boolean specialTrail;
    private boolean unlockedSpecialTrail;
    private List<TrailType> trailTypes;
    private Particle lastParticle;
    private boolean particlePlayNext;

    /* Wardrobe */
    private List<Color> wardrobe;
    private boolean unlockedWardrobeDisco;
    private boolean wardrobeDisco;

    /* Firework */
    private int fireworkPasses;
    private FireworkSettings fireworkSettings;

    public OMPlayer(Player player, boolean loaded){
        api = OrbitMinesAPI.getApi();
        this.player = player;
        this.loaded = loaded;
        this.cooldowns = new HashMap<>();
        this.stackerEnabled = true;
        this.playersEnabled = true;

        api.getPlayers().put(player, this);
        api.getOMPlayers().add(this);

        setScoreboard(new Scoreboard(this));
    }

    /* Abstract Methods */
    public abstract String getPrefix();
    public abstract void vote();
    public abstract void unloadPlayerData();
    public abstract void loadPlayerData();
    public abstract boolean canReceiveVelocity();

    /* Getters & Setters */

    public Player getPlayer() {
        return player;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("SetLanguage");
        out.writeUTF(getLanguage().toString());
        out.writeUTF(getPlayer().getName());

        getPlayer().sendPluginMessage(api, "OrbitMinesAPI", out.toByteArray());
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public OMInventory getLastInventory() {
        return lastInventory;
    }

    public void setLastInventory(OMInventory lastInventory) {
        this.lastInventory = lastInventory;
    }

    public Location getTpLocation() {
        return tpLocation;
    }

    public void setTpLocation(Location tpLocation) {
        this.tpLocation = tpLocation;
    }

    public boolean isNewPlayer() {
        return newPlayer;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public boolean isOpMode() {
        return opMode;
    }

    public void setOpMode(boolean opMode) {
        this.opMode = opMode;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public boolean hasStackerEnabled() {
        return stackerEnabled;
    }

    public void setStackerEnabled(boolean stackerEnabled) {
        this.stackerEnabled = stackerEnabled;
    }

    public boolean hasPlayersEnabled() {
        return playersEnabled;
    }

    public void setPlayersEnabled(boolean playersEnabled) {
        this.playersEnabled = playersEnabled;
    }

    public boolean isAfk() {
        return afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;

        if(!afk)
            setAfkName(null);
    }

    public String getAfkName() {
        return afkName;
    }

    public void setAfkName(String afkName) {
        this.afkName = afkName;
    }

    public VIPRank getVipRank() {
        if(hasPerms(StaffRank.OWNER) && isOpMode())
            return VIPRank.EMERALD_VIP;

        return vipRank;
    }

    public void setVipRank(VIPRank vipRank) {
        this.vipRank = vipRank;
    }

    public StaffRank getStaffRank() {
        return staffRank;
    }

    public void setStaffRank(StaffRank staffRank) {
        this.staffRank = staffRank;
    }

    public List<UUID> getFriends() {
        return friends;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public void setChatColor(ChatColor chatColor) {
        this.chatColor = chatColor;

        getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_PISTON_EXTEND, 5, 1);
        getPlayer().sendMessage(Messages.ENABLE_CHATCOLOR.get(this, chatColor.getName()));
    }

    public List<ChatColor> getChatColors() {
        return chatColors;
    }

    public void setChatColors(List<ChatColor> chatColors) {
        this.chatColors = chatColors;
    }

    public boolean hasUnlockedChatColorBold() {
        return unlockedChatColorBold;
    }

    public boolean isChatColorBold() {
        return chatColorBold;
    }

    public boolean hasUnlockedChatColorCursive() {
        return unlockedChatColorCursive;
    }

    public boolean isChatColorCursive() {
        return chatColorCursive;
    }

    public List<Disguise> getDisguises() {
        return disguises;
    }

    public boolean isDisguised() {
        return disguised;
    }

    public void setDisguised(boolean disguised) {
        this.disguised = disguised;
    }

    public int getDisguiseBlockId() {
        return disguiseBlockId;
    }

    public void setDisguiseBlockId(int disguiseBlockId) {
        this.disguiseBlockId = disguiseBlockId;
    }

    public EntityType getDisguiseEntityType() {
        return disguiseEntityType;
    }

    public void setDisguiseEntityType(EntityType disguiseEntityType) {
        this.disguiseEntityType = disguiseEntityType;
    }

    public boolean isDisguisedBaby() {
        return disguisedBaby;
    }

    public void setDisguisedBaby(boolean disguisedBaby) {
        this.disguisedBaby = disguisedBaby;
    }

    public Entity getDisguise() {
        return disguise;
    }

    public void setDisguise(Entity disguise) {
        this.disguise = disguise;
    }

    public List<Gadget> getGadgets() {
        return gadgets;
    }

    public Entity getSoccerMagmaCube() {
        return soccerMagmaCube;
    }

    public void setSoccerMagmaCube(Entity soccerMagmaCube) {
        this.soccerMagmaCube = soccerMagmaCube;
    }

    public Entity getSwapTeleporter() {
        return swapTeleporter;
    }

    public void setSwapTeleporter(Entity swapTeleporter) {
        this.swapTeleporter = swapTeleporter;
    }

    public int getSgaSeconds() {
        return sgaSeconds;
    }

    public void setSgaSeconds(int sgaSeconds) {
        this.sgaSeconds = sgaSeconds;
    }

    public Entity getSgaItem() {
        return sgaItem;
    }

    public void setSgaItem(Entity sgaItem) {
        this.sgaItem = sgaItem;
    }

    public List<Entity> getSgaSnowGolems() {
        return sgaSnowGolems;
    }

    public List<Hat> getHats() {
        return hats;
    }

    public int getHatsInvPage() {
        return hatsInvPage;
    }

    public void setHatsInvPage(int hatsInvPage) {
        this.hatsInvPage = hatsInvPage;
    }

    public boolean hasPetShroomTrail() {
        return petShroomTrail;
    }

    public void setPetShroomTrail(boolean petShroomTrail) {
        this.petShroomTrail = petShroomTrail;
    }

    public boolean hasPetBabyPigs() {
        return petBabyPigs;
    }

    public void setPetBabyPigs(boolean petBabyPigs) {
        this.petBabyPigs = petBabyPigs;
    }

    public List<Entity> getPetBabyPigEntities() {
        return petBabyPigEntities;
    }

    public void setPetBabyPigEntities(List<Entity> petBabyPigEntities) {
        this.petBabyPigEntities = petBabyPigEntities;
    }

    public boolean hasPetSheepDisco() {
        return petSheepDisco;
    }

    public void setPetSheepDisco(boolean petSheepDisco) {
        this.petSheepDisco = petSheepDisco;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public boolean hasUnlockedHatsBlockTrail() {
        return unlockedHatsBlockTrail;
    }

    public boolean hasHatsBlockTrail() {
        return hatsBlockTrail;
    }

    public void setHatsBlockTrail(boolean hatsBlockTrail) {
        this.hatsBlockTrail = hatsBlockTrail;

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
        if(hatsBlockTrail)
            getPlayer().sendMessage(Messages.ENABLE_HAT_BLOCK_TRAIL.get(this));
        else
            getPlayer().sendMessage(Messages.DISABLE_HAT_BLOCK_TRAIL.get(this));
    }

    public Map<Pet, String> getPetNames() {
        return petNames;
    }

    public Entity getPet() {
        return pet;
    }

    public void setPet(Entity pet) {
        this.pet = pet;

        if(pet != null)
            api.getPets().add(pet);
    }

    public Pet getPetEnabled() {
        return petEnabled;
    }

    public void setPetEnabled(Pet petEnabled) {
        this.petEnabled = petEnabled;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;

        if(trail != null){
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
            getPlayer().sendMessage(Messages.ENABLE_TRAIL.get(this, trail.getName()));
        }
    }

    public TrailType getTrailType() {
        return trailType;
    }

    public void setTrailType(TrailType trailType) {
        this.trailType = trailType;
        this.lastParticle = null;
        this.particlePlayNext = true;

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
        getPlayer().sendMessage(Messages.ENABLE_TRAIL_TYPE.get(this, trailType.getName()));
    }

    public List<Trail> getTrails() {
        return trails;
    }

    public boolean canParticlePlayNext() {
        return particlePlayNext;
    }

    public void setParticlePlayNext(boolean particlePlayNext) {
        this.particlePlayNext = particlePlayNext;
    }

    public int getTrailParticleAmount() {
        return trailParticleAmount;
    }

    public void setTrailParticleAmount(int trailParticleAmount) {
        this.trailParticleAmount = trailParticleAmount;

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
        getPlayer().sendMessage(Messages.SET_PARTICLE_AMOUNT.get(this, trailParticleAmount + ""));
    }

    public boolean hasSpecialTrail() {
        return specialTrail;
    }

    public void setSpecialTrail(boolean specialTrail) {
        this.specialTrail = specialTrail;

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
        if(specialTrail)
            getPlayer().sendMessage(Messages.ENABLE_SPECIAL_TRAIL.get(this));
        else
            getPlayer().sendMessage(Messages.DISABLE_SPECIAL_TRAIL.get(this));
    }

    public boolean hasUnlockedSpecialTrail() {
        return unlockedSpecialTrail;
    }

    public List<TrailType> getTrailTypes() {
        return trailTypes;
    }

    public List<Color> getWardrobe() {
        return wardrobe;
    }

    public boolean hasUnlockedWardrobeDisco() {
        return unlockedWardrobeDisco;
    }

    public boolean isWardrobeDisco() {
        return wardrobeDisco;
    }

    public void setWardrobeDisco(boolean wardrobeDisco){
        if(wardrobeDisco){
            if(hasWardrobeEnabled() && !this.wardrobeDisco)
                disableWardrobe();

            getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_ANVIL_LAND, 5, 1);
            getPlayer().sendMessage(Messages.ENABLE_WARDROBE_DISCO.get(this, ColorUtils.random(ColorUtils.WARDROBE).getColor()));
            discoWardrobe();
        }

        this.wardrobeDisco = wardrobeDisco;
    }

    public int getFireworkPasses() {
        return fireworkPasses;
    }

    public void setFireworkPasses(int fireworkPasses) {
        this.fireworkPasses = fireworkPasses;
    }

    public FireworkSettings getFireworkSettings() {
        return fireworkSettings;
    }

    public void setFireworkSettings(FireworkSettings fireworkSettings) {
        this.fireworkSettings = fireworkSettings;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean hasReceivedMonthlyBonus() {
        return receivedMonthlyBonus;
    }

    public Map<Cooldown, Long> getCooldowns() {
        return cooldowns;
    }

    /* Added for Prison */
    public void setCooldowns(Map<Cooldown, Long> cooldowns) {
        this.cooldowns = cooldowns;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getVipPoints() {
        return vipPoints;
    }

    public int getOrbitMinesTokens() {
        return orbitMinesTokens;
    }

    /* Database methods */

    public void addChatColor(ChatColor chatcolor){
        this.chatColors.add(chatcolor);

        if(!Database.get().containsPath("ChatColors-" + chatcolor.getDatabaseName(), "uuid", "uuid", getUUID().toString()))
            Database.get().insert("ChatColors-" + chatcolor.getDatabaseName(), "uuid", getUUID().toString());
    }

    public void setUnlockedBold(boolean unlockedChatColorBold){
        this.unlockedChatColorBold = unlockedChatColorBold;

        if(unlockedChatColorBold && !Database.get().containsPath("ChatColors-Bold", "uuid", "uuid", getUUID().toString()))
            Database.get().insert("ChatColors-Bold", "uuid`, `bold", getUUID().toString() + "', '" + false);
    }

    public void setUnlockedCursive(boolean unlockedChatColorCursive){
        this.unlockedChatColorCursive = unlockedChatColorCursive;

        if(unlockedChatColorCursive && !Database.get().containsPath("ChatColors-Cursive", "uuid", "uuid", getUUID().toString()))
            Database.get().insert("ChatColors-Cursive", "uuid`, `cursive", getUUID().toString() + "', '" + false);
    }

    public void addDisguise(Disguise disguise){
        this.disguises.add(disguise);

        if(!Database.get().containsPath("Dis-" + disguise.getDatabaseName(), "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Dis-" + disguise.getDatabaseName(), "uuid", getUUID().toString());
    }

    public void addGadget(Gadget gadget){
        this.gadgets.add(gadget);

        if(!Database.get().containsPath("Gadgets-" + gadget.getDatabaseName(), "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Gadgets-" + gadget.getDatabaseName(), "uuid", getUUID().toString());
    }

    public void addHat(Hat hat){
        this.hats.add(hat);

        if(!Database.get().containsPath("Hats-" + hat.getDatabaseName(), "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Hats-" + hat.getDatabaseName(), "uuid", getUUID().toString());
    }

    public void setUnlockedHatsBlockTrail(boolean unlockedHatsBlockTrail){
        this.unlockedHatsBlockTrail = unlockedHatsBlockTrail;

        if(unlockedHatsBlockTrail && !Database.get().containsPath("Hats-BlockTrail", "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Hats-BlockTrail", "uuid`, `blocktrail", getUUID().toString() + "', '" + false);
    }

    public void addPet(Pet pet){
        this.pets.add(pet);

        if(!Database.get().containsPath("Pets-" + pet.getDatabaseName(), "uuid", "uuid", getUUID().toString())){
            Database.get().insert("Pets-" + pet.getDatabaseName(), "uuid`, `petname", getUUID().toString() + "', '" + getPlayer().getName() + "`s " + pet.getDatabaseName());
            this.petNames.put(pet, getPlayer().getName() + "'s " + pet.getDatabaseName());
        }
    }

    public void addTrail(Trail trail){
        this.trails.add(trail);

        if(!Database.get().containsPath("Trails-" + trail.getDatabaseName(), "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Trails-" + trail.getDatabaseName(), "uuid", getUUID().toString());
    }

    public void setUnlockedSpecialTrail(boolean unlockedSpecialTrail){
        this.unlockedSpecialTrail = unlockedSpecialTrail;

        if(unlockedSpecialTrail && !Database.get().containsPath("Trails-TypeSpecial", "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Trails-TypeSpecial", "uuid`, `special", getUUID().toString() + "', '" + false);
    }

    public void addTrailType(TrailType trailType){
        this.trailTypes.add(trailType);

        if(!Database.get().containsPath("Trails-" + trailType.getDatabaseName(), "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Trails-" + trailType.getDatabaseName(), "uuid", getUUID().toString());
    }

    public void addWardrobe(Color wardrobe){
        this.wardrobe.add(wardrobe);

        if(!Database.get().containsPath("Wardrobe-" + wardrobe.getDatabaseName(), "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Wardrobe-" + wardrobe.getDatabaseName(), "uuid", getUUID().toString());
    }

    public void setUnlockedWardrobeDisco(boolean unlockedWardrobeDisco){
        this.unlockedWardrobeDisco = unlockedWardrobeDisco;

        if(unlockedWardrobeDisco && !Database.get().containsPath("Wardrobe-Disco", "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Wardrobe-Disco", "uuid`, `disco", getUUID().toString() + "', '" + false);
    }

    public void setReceivedMonthlyBonus(boolean receivedMonthlyBonus){
        this.receivedMonthlyBonus = receivedMonthlyBonus;

        if(receivedMonthlyBonus){
            if(!Database.get().containsPath("PlayerMonthlyVIPPoints", "uuid", "uuid", getUUID().toString()))
                Database.get().insert("PlayerMonthlyVIPPoints", "uuid", getUUID().toString());
        }
        else{
            if(Database.get().containsPath("PlayerMonthlyVIPPoints", "uuid", "uuid", getUUID().toString()))
                Database.get().delete("PlayerMonthlyVIPPoints", "uuid", getUUID().toString());
        }
    }

    public void setVIPPoints(int vipPoints){
        this.vipPoints = vipPoints;

        Database.get().update("VIPPoints", "points", "" + getVipPoints(), "uuid", getUUID().toString());
    }

    public void addVIPPoints(int vipPoints){
        this.vipPoints += vipPoints;

        Database.get().update("VIPPoints", "points", "" + getVipPoints(), "uuid", getUUID().toString());

        Title t = new Title("", "§b+" + vipPoints + " VIP Points", 20, 40, 20);
        t.send(getPlayer());
    }

    public void removeVipPoints(int vipPoints){
        this.vipPoints -= vipPoints;

        Database.get().update("VIPPoints", "points", "" + getVipPoints(), "uuid", getUUID().toString());
    }

    public void setOrbitMinesTokens(int orbitMinesTokens){
        this.orbitMinesTokens = orbitMinesTokens;

        Database.get().update("OrbitMinesTokens", "omt", "" + getOrbitMinesTokens(), "uuid", getUUID().toString());
    }

    public void addOrbitMinesTokens(int orbitMinesTokens){
        this.orbitMinesTokens += orbitMinesTokens;

        Database.get().update("OrbitMinesTokens", "omt", "" + getOrbitMinesTokens(), "uuid", getUUID().toString());
    }

    public void removeOrbitMinesTokens(int orbitMinesTokens){
        this.orbitMinesTokens -= orbitMinesTokens;

        Database.get().update("OrbitMinesTokens", "omt", "" + getOrbitMinesTokens(), "uuid", getUUID().toString());
    }

    public void setPetName(Pet pet, String petName){
        this.petNames.put(pet, petName);

        petName = petName.replace("'", "`");
        switch(pet){
            case CHICKEN:
                Database.get().update("Pets-Chicken", "petname", petName, "uuid", getUUID().toString());
            case COW:
                Database.get().update("Pets-Cow", "petname", petName, "uuid", getUUID().toString());
            case CREEPER:
                Database.get().update("Pets-Creeper", "petname", petName, "uuid", getUUID().toString());
            case HORSE:
                Database.get().update("Pets-Horse", "petname", petName, "uuid", getUUID().toString());
            case MAGMA_CUBE:
                Database.get().update("Pets-MagmaCubePet", "petname", petName, "uuid", getUUID().toString());
            case MUSHROOM_COW:
                Database.get().update("Pets-MushroomCow", "petname", petName, "uuid", getUUID().toString());
            case OCELOT:
                Database.get().update("Pets-Ocelot", "petname", petName, "uuid", getUUID().toString());
            case PIG:
                Database.get().update("Pets-Pig", "petname", petName, "uuid", getUUID().toString());
            case SHEEP:
                Database.get().update("Pets-Sheep", "petname", petName, "uuid", getUUID().toString());
            case SILVERFISH:
                Database.get().update("Pets-Silverfish", "petname", petName, "uuid", getUUID().toString());
            case SLIME:
                Database.get().update("Pets-Slime", "petname", petName, "uuid", getUUID().toString());
            case SPIDER:
                Database.get().update("Pets-Spider", "petname", petName, "uuid", getUUID().toString());
            case SQUID:
                Database.get().update("Pets-Squid", "petname", petName, "uuid", getUUID().toString());
            case WOLF:
                Database.get().update("Pets-Wolf", "petname", petName, "uuid", getUUID().toString());
            default:
                break;
        }
    }

    public void updateVotes(){
        this.votes = Database.get().getInt("Votes", "votes", "uuid", getUUID().toString());
    }

    public void setVIP(VIPRank vipRank){
        if(Database.get().containsPath("Rank-VIP", "uuid", "uuid", getUUID().toString()))
            Database.get().update("Rank-VIP", "vip", vipRank.toString(), "uuid", getUUID().toString());
        else
            Database.get().insert("Rank-VIP", "uuid`, `vip", getUUID().toString() + "', '" + vipRank.toString());

        setVipRank(vipRank);

        Title t = new Title("", Messages.RANK_RECEIVE.get(this, vipRank == VIPRank.EMERALD_VIP || vipRank == VIPRank.IRON_VIP ? "an" : "a", vipRank.getRankString() + " VIP"), 20, 80, 20);
        t.send(getPlayer());
    }

    public void setStaff(StaffRank staffRank){
        if(Database.get().containsPath("Rank-Staff", "uuid", "uuid", getUUID().toString()))
            Database.get().update("Rank-Staff", "staff", staffRank.toString(), "uuid", getUUID().toString());
        else
            Database.get().insert("Rank-Staff", "uuid`, `staff", getUUID().toString() + "', '" + staffRank.toString());

        setStaffRank(staffRank);

        Title t = new Title("", Messages.RANK_RECEIVE.get(this, staffRank == StaffRank.OWNER ? "an" : "a", staffRank.getRankString()), 20, 40, 20);
        t.send(getPlayer());
    }

    /* Other methods */

    public UUID getUUID(){
        return getPlayer().getUniqueId();
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

    public void nowAfk(String afkName){
        this.afk = true;

        String name = getName();

        if(afkName != null){
            for(OMPlayer omPlayer : api.getOMPlayers()){
                omPlayer.getPlayer().sendMessage(Messages.NOW_AFK.get(omPlayer, name, afkName));
            }

            this.afkName = afkName;
        }
        else{
            for(OMPlayer omPlayer : api.getOMPlayers()){
                omPlayer.getPlayer().sendMessage(Messages.NOW_AFK_EMPTY.get(omPlayer, name));
            }
        }
    }

    public void noLongerAfk(){
        this.afk = false;

        String name = getName();

        if(getAfkName() != null){
            for(OMPlayer omPlayer : api.getOMPlayers()){
                omPlayer.getPlayer().sendMessage(Messages.NO_LONGER_AFK.get(omPlayer, name, getAfkName()));
            }
        }
        else{
            for(OMPlayer omPlayer : api.getOMPlayers()){
                omPlayer.getPlayer().sendMessage(Messages.NO_LONGER_AFK_EMPTY.get(omPlayer, name));
            }
        }
    }

    public boolean hasChatColor(ChatColor chatColor){
        return chatColors.contains(chatColor);
    }

    private String getBoldString(){
        if(isChatColorBold())
            return "§l";

        return "";
    }

    private String getCursiveString(){
        if(isChatColorCursive())
            return "§o";

        return "";
    }

    public boolean hasDisguise(Disguise disguise){
        return disguises.contains(disguise);
    }

    public void addFriend(UUID friend){
        this.friends.add(friend);
    }

    public boolean hasGadget(Gadget gadget){
        return gadgets.contains(gadget);
    }

    public void disableSoccerMagmaCube(){
        getSoccerMagmaCube().remove();
        setSoccerMagmaCube(null);
        getPlayer().sendMessage(Messages.DISABLE_SOCCER_MAGMACUBE.get(this));
    }

    public void addSgaSnowGolem(Entity sgaSnowGolem){
        this.sgaSnowGolems.add(sgaSnowGolem);
    }

    public boolean hasHat(){
        return getHats().size() > 0 || hasPerms(VIPRank.IRON_VIP);
    }

    public boolean hasHat(Hat hat){
        return hats.contains(hat);
    }

    public void setHat(Hat hat){
        if(hasHatEnabled())
            disableHat();

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
        getPlayer().getInventory().setHelmet(ItemUtils.itemstack(hat.getMaterial(), 1, hat.getName(), hat.getDurability()));
        getPlayer().sendMessage(Messages.ENABLE_HAT.get(this, hat.getName()));
    }

    public void nextHatsPage(){
        this.hatsInvPage = this.hatsInvPage +1;
    }

    public void prevHatsPage(){
        this.hatsInvPage = this.hatsInvPage -1;
    }

    public void addPetBabyPigEntity(Entity petBabyPigEntity){
        this.petBabyPigEntities.add(petBabyPigEntity);
    }

    public boolean hasPet(){
        return getPets().size() > 0;
    }

    public boolean hasPet(Pet pet){
        return pets.contains(pet);
    }

    public String getPetName(Pet pet){
        return this.petNames.get(pet);
    }

    public boolean hasPetEnabled(){
        return petEnabled != null;
    }

    public boolean hasTrailEnabled(){
        return getTrail() != null;
    }

    public boolean hasTrail(){
        return getTrails().size() > 0 || hasPerms(VIPRank.IRON_VIP);
    }

    public boolean hasTrail(Trail trail){
        return trails.contains(trail);
    }

    public void playTrail(){
        if(hasTrailEnabled()){
            Trail trail = getTrail();
            Particle pa = new Particle(trail.getParticle(), getPlayer().getLocation());
            double cylinderYAdded = 0.0;

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
                    if(this.lastParticle != null && canParticlePlayNext()){
                        pa = this.lastParticle;
                        pa.setSize(0, 0, 0);

                        if(pa.getYAdded() > 1.65){
                            pa.setPositiv(false);
                        }
                        if(pa.getYAdded() < 0){
                            pa.setPositiv(true);
                        }

                        if(pa.isPositive()){
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
                    if(this.lastParticle != null && canParticlePlayNext()){
                        pa = this.lastParticle;
                        pa.setSize(0, 0, 0);
                        cylinderYAdded = pa.getYAdded();

                        if(pa.getIndex() > 30){
                            pa.setIndex(0);
                            cylinderYAdded = cylinderYAdded +1;
                        }
                        else{
                            pa.setIndex(pa.getIndex() +1);
                        }
                        if(cylinderYAdded > 7){
                            cylinderYAdded = 0;
                        }

                        pa.setYAdded(0);
                        pa.addY(0.2 * pa.getIndex());
                        pa.setXAdded(1.1 * Math.cos(pa.getYAdded()));
                        pa.setZAdded(1.1 * Math.sin(pa.getYAdded()));

                        pa.setYAdded(0.25 * cylinderYAdded);
                    }
                    else{
                        pa.setSize(1, 1, 1);
                    }
                    break;
                case SNAKE_TRAIL:
                    if(this.lastParticle != null){
                        pa = this.lastParticle;
                        pa.setLocation(getPlayer().getLocation());
                        pa.setSize(0, 0, 0);

                        if(pa.getYAdded() > 1.65){
                            pa.setPositiv(false);
                        }
                        if(pa.getYAdded() < 0){
                            pa.setPositiv(true);
                        }

                        if(pa.isPositive()){
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

            pa.setParticle(getTrail().getParticle());
            pa.setAmount(getTrailParticleAmount());
            if(hasSpecialTrail()){
                pa.setSpecial(1);
            }

            List<Player> players = Arrays.asList(Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));

            pa.send(players);
            if(trail == Trail.SNOW){
                pa.setParticle(org.bukkit.Particle.SNOWBALL);
                pa.send(players);
                pa.setParticle(org.bukkit.Particle.SNOW_SHOVEL);
            }
            this.lastParticle = pa;

            if(trailType == TrailType.ORBIT_TRAIL){
                pa.sendOpposite(players);

                if(trail == Trail.SNOW){
                    pa.setParticle(org.bukkit.Particle.SNOWBALL);
                    pa.sendOpposite(players);
                    pa.setParticle(org.bukkit.Particle.SNOW_SHOVEL);
                }
            }

            if(trailType == TrailType.CYLINDER_TRAIL){
                pa.setYAdded(1.75 -(0.25 * cylinderYAdded));
                pa.send(players);

                if(trail == Trail.SNOW){
                    pa.setParticle(org.bukkit.Particle.SNOWBALL);
                    pa.sendOpposite(players);
                    pa.setParticle(org.bukkit.Particle.SNOW_SHOVEL);
                }
                pa.setYAdded(cylinderYAdded);
            }
        }
    }

    public void parseTrail(Trail trail, TrailType trailType, int amount, boolean special){
        Particle pa = new Particle(trail.getParticle(), getPlayer().getLocation());
        double cylinderYAdded = 0.0;
        if(this.lastParticle == null)
            this.lastParticle = pa;

        switch(trailType){
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
                pa = this.lastParticle;
                pa.setSize(0, 0, 0);

                if(pa.getYAdded() > 1.65){
                    pa.setPositiv(false);
                }
                if(pa.getYAdded() < 0){
                    pa.setPositiv(true);
                }

                if(pa.isPositive()){
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
                pa = this.lastParticle;
                pa.setSize(0, 0, 0);
                cylinderYAdded = pa.getYAdded();

                if(pa.getIndex() > 30){
                    pa.setIndex(0);
                    cylinderYAdded = cylinderYAdded +1;
                }
                else{
                    pa.setIndex(pa.getIndex() +1);
                }
                if(cylinderYAdded > 7){
                    cylinderYAdded = 0;
                }

                pa.setYAdded(0);
                pa.addY(0.2 * pa.getIndex());
                pa.setXAdded(1.1 * Math.cos(pa.getYAdded()));
                pa.setZAdded(1.1 * Math.sin(pa.getYAdded()));

                pa.setYAdded(0.25 * cylinderYAdded);
                break;
            case SNAKE_TRAIL:
                pa = this.lastParticle;
                pa.setLocation(getPlayer().getLocation());
                pa.setSize(0, 0, 0);

                if(pa.getYAdded() > 1.65){
                    pa.setPositiv(false);
                }
                if(pa.getYAdded() < 0){
                    pa.setPositiv(true);
                }

                if(pa.isPositive()){
                    pa.addY(0.25);
                }
                else{
                    pa.addY(-0.25);
                }
                break;
            default:
                break;
        }

        pa.setParticle(trail.getParticle());
        pa.setAmount(amount);
        if(special)
            pa.setSpecial(1);
        else
            pa.setSpecial(0);

        pa.send(Bukkit.getOnlinePlayers());
        if(trail == Trail.SNOW){
            pa.setParticle(org.bukkit.Particle.SNOWBALL);
            pa.send(Bukkit.getOnlinePlayers());
            pa.setParticle(org.bukkit.Particle.SNOW_SHOVEL);
        }
        this.lastParticle = pa;

        if(trailType == TrailType.ORBIT_TRAIL){
            pa.sendOpposite(Bukkit.getOnlinePlayers());

            if(trail == Trail.SNOW){
                pa.setParticle(org.bukkit.Particle.SNOWBALL);
                pa.sendOpposite(Bukkit.getOnlinePlayers());
                pa.setParticle(org.bukkit.Particle.SNOW_SHOVEL);
            }
        }

        if(trailType == TrailType.CYLINDER_TRAIL){
            pa.setYAdded(1.75 -(0.25 * cylinderYAdded));
            pa.send(Bukkit.getOnlinePlayers());

            if(trail == Trail.SNOW){
                pa.setParticle(org.bukkit.Particle.SNOWBALL);
                pa.sendOpposite(Bukkit.getOnlinePlayers());
                pa.setParticle(org.bukkit.Particle.SNOW_SHOVEL);
            }
            pa.setYAdded(cylinderYAdded);
        }
    }

    public void parseCylinderTrail(Trail trail){
        Particle pa = new Particle(trail.getParticle(), getPlayer().getLocation());

        for(int i = 0; i < 120; i++){
            double cylinderYAdded = pa.getYAdded();

            pa.setSize(0, 0, 0);

            if(pa.getIndex() > 30){
                pa.setIndex(0);
                cylinderYAdded = cylinderYAdded +1;
            }
            else{
                pa.setIndex(pa.getIndex() +1);
            }
            if(cylinderYAdded > 7){
                cylinderYAdded = 0;
            }

            pa.setYAdded(0);
            pa.addY(0.2 * pa.getIndex());
            pa.setXAdded(1.1 * Math.cos(pa.getYAdded()));
            pa.setZAdded(1.1 * Math.sin(pa.getYAdded()));

            pa.setYAdded(0.25 * cylinderYAdded);

            pa.setParticle(trail.getParticle());
            pa.setAmount(1);

            pa.send(Bukkit.getOnlinePlayers());
            if(trail == Trail.SNOW){
                pa.setParticle(org.bukkit.Particle.SNOWBALL);
                pa.send(Bukkit.getOnlinePlayers());
                pa.setParticle(org.bukkit.Particle.SNOW_SHOVEL);
            }

            pa.setYAdded(1.75 -(0.25 * cylinderYAdded));
            pa.send(Bukkit.getOnlinePlayers());

            if(trail == Trail.SNOW){
                pa.setParticle(org.bukkit.Particle.SNOWBALL);
                pa.sendOpposite(Bukkit.getOnlinePlayers());
                pa.setParticle(org.bukkit.Particle.SNOW_SHOVEL);
            }
            pa.setYAdded(cylinderYAdded);
        }
    }

    public void addTrailParticleAmount(){
        int amount = this.trailParticleAmount +1;
        if(amount == 6)
            amount = 1;

        this.trailParticleAmount = amount;

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
        getPlayer().sendMessage(Messages.SET_PARTICLE_AMOUNT.get(this, "" + amount));
    }

    public boolean hasTrailType(TrailType trailType){
        return this.trailTypes.contains(trailType);
    }

    public boolean hasWardrobe(Color color){
        return wardrobe.contains(color);
    }

    public void addFireworkPasses(int fireworkPasses){
        this.fireworkPasses += fireworkPasses;
    }

    public void removeFireworkPass(){
        this.fireworkPasses--;
    }

    public long getCooldown(Cooldown cooldown){
        if(this.cooldowns.containsKey(cooldown))
            return this.cooldowns.get(cooldown);

        return -1;
    }

    public void resetCooldown(Cooldown cooldown){
        this.cooldowns.put(cooldown, System.currentTimeMillis());
        Cooldown.PREV_DOUBLE.remove(this);

        if(cooldown == Cooldowns.TELEPORTING) {
            this.lastParticle = null;
            setTpLocation(getPlayer().getLocation());
        }
    }

    public void removeCooldown(Cooldown cooldown){
        this.cooldowns.remove(cooldown);
        Cooldown.PREV_DOUBLE.remove(this);

        if(cooldown == Cooldowns.TELEPORTING)
            setTpLocation(null);
    }

    public boolean onCooldown(Cooldown cooldown){
        if(this.cooldowns.containsKey(cooldown))
            return System.currentTimeMillis() - this.cooldowns.get(cooldown) < cooldown.getCooldown(this);

        return false;
    }

    public void updateCooldownActionBar(){
        for(Cooldown cooldown : this.cooldowns.keySet()){
            cooldown.updateActionBar(this);
        }
    }

    public void addVote(){
        this.votes += 1;
    }

    public boolean hasVotes(int votes){
        return this.votes >= votes;
    }

    public boolean hasVIPPoints(int vipPoints){
        return this.vipPoints >= vipPoints;
    }

    public boolean hasOrbitMinesTokens(int orbitMinesTokens){
        return this.orbitMinesTokens >= orbitMinesTokens;
    }

    public void setBold(boolean chatColorBold){
        this.chatColorBold = chatColorBold;

        getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_PISTON_EXTEND, 5, 1);
        if(chatColorBold)
            getPlayer().sendMessage(Messages.ENABLE_CHATCOLOR_BOLD.get(this, getChatColor().getColor()));
        else
            getPlayer().sendMessage(Messages.DISABLE_CHATCOLOR_BOLD.get(this, getChatColor().getColor()));
    }

    public void setCursive(boolean chatColorCursive){
        this.chatColorCursive = chatColorCursive;

        getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_PISTON_EXTEND, 5, 1);
        if(chatColorBold)
            getPlayer().sendMessage(Messages.ENABLE_CHATCOLOR_CURSIVE.get(this, getChatColor().getColor()));
        else
            getPlayer().sendMessage(Messages.DISABLE_CHATCOLOR_CURSIVE.get(this, getChatColor().getColor()));
    }

    public String getChatFormat(){
        if(getNickName() != null)
            return getPrefix() + getChatPrefix() + getRankColor() + "*" + getNickName() + getRankColor() + "*§7 » " + getChatColor().getColor() + getBoldString() + getCursiveString() + "%2$s";

        return getPrefix() + getName() + "§7 » " + getChatColor().getColor() + getBoldString() + getCursiveString() + "%2$s";
    }

    public String getRankColor(){
        if(getStaffRank() != StaffRank.USER)
            return getStaffRank().getColor();

        return getVipRank().getColor();
    }

    public String getColorName(){
        return getRankColor() + getPlayer().getName();
    }

    public String getChatPrefix(){
        if(getStaffRank() != StaffRank.USER)
            return getStaffRank().getChatPrefix();

        return getVipRank().getChatPrefix();
    }

    public String getScoreboardPrefix(){
        if(getStaffRank() != StaffRank.USER)
            return getStaffRank().getScoreboardPrefix();

        return getVipRank().getScoreboardPrefix();
    }

    public String getRankString(){
        if(getStaffRank() != StaffRank.USER)
            return getStaffRank().getRankString();

        return getVipRank().getRankString();
    }

    public String getName(){
        return getChatPrefix() + getPlayer().getName();
    }

    public void setTabList(String header, String footer){
        api.getNms().tablist().send(getPlayer(), header, footer);
    }

    public void notLoaded(){
        Player p = getPlayer();
        Server server = api.getServerType();
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
        p.sendMessage(Messages.SERVER_STARTING.get(this, server.getName(), server.getColor()));
    }

    public boolean hasWardrobeEnabled(){
        return getPlayer().getInventory().getChestplate() != null;
    }

    public void disableWardrobe(){
        Player p = getPlayer();

        p.sendMessage(Messages.DISABLE_WARDROBE.get(this, p.getInventory().getChestplate().getItemMeta().getDisplayName()));

        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);

        setWardrobeDisco(false);
    }

    public void discoWardrobe(){
        Player p = getPlayer();
        Color color = ColorUtils.random(getWardrobe());
        String c = color.getColor();

        p.getInventory().setChestplate(ItemUtils.addColor(ItemUtils.itemstack(color.getMaterial(), 1, c + "Disco Armor"), color.getBukkitColor()));

        if(color == Color.ELYTRA)
            return;

        p.getInventory().setLeggings(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, c + "Disco Armor"), color.getBukkitColor()));
        p.getInventory().setBoots(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, c + "Disco Armor"), color.getBukkitColor()));
    }

    public void wardrobe(Color color){
        Player p = getPlayer();
        String c = color.getName();

        if(hasWardrobeEnabled()){
            disableWardrobe();
        }

        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 5, 1);
        p.sendMessage(Messages.ENABLE_WARDROBE.get(this, color.getName()));

        p.getInventory().setChestplate(ItemUtils.addColor(ItemUtils.itemstack(color.getMaterial(), 1, c + " Armor"), color.getBukkitColor()));

        if(color == Color.ELYTRA)
            return;

        p.getInventory().setLeggings(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, c + " Armor"), color.getBukkitColor()));
        p.getInventory().setBoots(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, c + " Armor"), color.getBukkitColor()));
    }

    public void wardrobe(VIPRank vipRank){
        Player p = getPlayer();

        if(hasWardrobeEnabled())
            disableWardrobe();

        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 5, 1);

        switch(vipRank){
            case EMERALD_VIP:
                p.sendMessage(Messages.ENABLE_WARDROBE_EMERALD.get(this));

                p.getInventory().setChestplate(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§7Chainmail Armor"));
                p.getInventory().setLeggings(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§7Chainmail Armor"));
                p.getInventory().setBoots(ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, "§7Chainmail Armor"));
                break;
            case DIAMOND_VIP:
                p.sendMessage(Messages.ENABLE_WARDROBE_DIAMOND.get(this));

                p.getInventory().setChestplate(ItemUtils.itemstack(Material.DIAMOND_CHESTPLATE, 1, "§bDiamond Armor"));
                p.getInventory().setLeggings(ItemUtils.itemstack(Material.DIAMOND_LEGGINGS, 1, "§bDiamond Armor"));
                p.getInventory().setBoots(ItemUtils.itemstack(Material.DIAMOND_BOOTS, 1, "§bDiamond Armor"));
                break;
            case GOLD_VIP:
                p.sendMessage(Messages.ENABLE_WARDROBE_GOLD.get(this));

                p.getInventory().setChestplate(ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, "§6Gold Armor"));
                p.getInventory().setLeggings(ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, "§6Gold Armor"));
                p.getInventory().setBoots(ItemUtils.itemstack(Material.GOLD_BOOTS, 1, "§6Gold Armor"));
                break;
            case IRON_VIP:
                p.sendMessage(Messages.ENABLE_WARDROBE_IRON.get(this));

                p.getInventory().setChestplate(ItemUtils.itemstack(Material.IRON_CHESTPLATE, 1, "§7Iron Armor"));
                p.getInventory().setLeggings(ItemUtils.itemstack(Material.IRON_LEGGINGS, 1, "§7Iron Armor"));
                p.getInventory().setBoots(ItemUtils.itemstack(Material.IRON_BOOTS, 1, "§7Iron Armor"));
                break;
            default:
                break;
        }
    }

    public void disableTrail(){
        getPlayer().sendMessage(Messages.DISABLE_TRAIL.get(this, getTrail().getName()));
        setTrail(null);
    }

    public boolean hasHatEnabled(){
        ItemStack helmet = getPlayer().getInventory().getHelmet();
        return helmet != null && helmet.getType() != Material.LEATHER_HELMET && helmet.getType() != Material.GOLD_HELMET && helmet.getType() != Material.IRON_HELMET && helmet.getType() != Material.DIAMOND_HELMET && helmet.getType() != Material.CHAINMAIL_HELMET;
    }

    public void disableHat(){
        getPlayer().sendMessage(Messages.DISABLE_HAT.get(this, getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName()));
        getPlayer().getInventory().setHelmet(null);
    }

    public void enableGadget(Gadget gadget){
        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

        CustomItemNms nms = api.getNms().customItem();
        getPlayer().getInventory().setItem(api.getServerPlugin().getGadgetSlot(), nms.hideFlags(nms.setUnbreakable(ItemUtils.itemstack(gadget.getMaterial(), 1, gadget.getName().replace("§l", "§n"), gadget.getDurability())), 4));

        getPlayer().sendMessage(Messages.ENABLE_GADGET.get(this, gadget.getName()));
    }

    public void disableGadget(){
        ItemStack item = getPlayer().getInventory().getItem(api.getServerPlugin().getGadgetSlot());
        if(item == null)
            return;

        String displayName = "Gadget";
        if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null)
            displayName = item.getItemMeta().getDisplayName();

        getPlayer().sendMessage(Messages.DISABLE_GADGET.get(this, displayName.replace("§n", "§l")));
        getPlayer().getInventory().setItem(api.getServerPlugin().getGadgetSlot(), null);
    }

    public void giveFireworkGun(){
        Player p = getPlayer();

        p.closeInventory();
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);

        p.getInventory().setItem(api.getServerPlugin().getGadgetSlot(), ItemUtils.itemstack(Material.FIREBALL, 1, "§c§nFirework Gun§r §c(§6" + getFireworkPasses() + "§c)"));

        p.sendMessage(Messages.ENABLE_FIREWORK_GUN.get(this));
    }

    public void disablePet(){
        Pet pet = getPetEnabled();
        getPlayer().sendMessage(Messages.DISABLE_PET.get(this, getPetName(pet)));

        Entity en = getPet();
        if(en instanceof LivingEntity)
            en.remove();

        if(hasPetShroomTrail())
            setPetShroomTrail(false);

        if(hasPetBabyPigs()){
            for(Entity ent : getPetBabyPigEntities()){
                ent.remove();
            }
            setPetBabyPigEntities(null);
            setPetBabyPigs(false);
        }

        if(hasPetSheepDisco())
            setPetSheepDisco(false);

        api.getPets().remove(en);

        setPet(null);
        setPetEnabled(null);
    }

    public void spawnPet(Pet pet){
        pet.spawn(this);
        getPlayer().sendMessage(Messages.ENABLE_PET.get(this, pet.getName()));
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
                if(omplayer.getDisguiseEntityType() != null)
                    omplayer.disguiseAsMob(omplayer.getDisguiseEntityType(), isDisguisedBaby(), omplayer.getDisguise().getCustomName(), getPlayer());
                else
                    omplayer.disguiseAsBlock(omplayer.getDisguiseBlockId(), getPlayer());
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

    public void givePetInventory(){
        Player p = getPlayer();
        p.getInventory().clear();

        switch(getPetEnabled()){
            case CHICKEN:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.EGG, 1, "§7§nEgg " + Messages.PET_BOMB.get(this)));

                int chickenage = 1;
                if(((Chicken) getPet()).isAdult())
                    chickenage = 2;

                p.getInventory().setItem(6, ItemUtils.itemstack(Material.RAW_CHICKEN, chickenage, "§c§n" + Messages.PET_CHANGE_AGE.get(this)));
                break;
            case COW:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.MILK_BUCKET, 1, "§f§nMilk Explosion"));

                int cowage = 1;
                if(((Cow) getPet()).isAdult())
                    cowage = 2;

                p.getInventory().setItem(6, ItemUtils.itemstack(Material.RAW_BEEF, cowage, "§c§n" + Messages.PET_CHANGE_AGE.get(this)));
                break;
            case CREEPER:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.TNT, 1, Messages.PET_EXPLODE.get(this)));

                Creeper creeper = (Creeper) getPet();
                if(creeper.isPowered())
                    p.getInventory().setItem(6, ItemUtils.itemstack(Material.MONSTER_EGG, 1, "§a§n" + Messages.PET_CHANGE_TYPE.get(this) + "§7 (§e§lLIGHTNING§7)", 50));
                else
                    p.getInventory().setItem(6, ItemUtils.itemstack(Material.MONSTER_EGG, 1, "§a§n" + Messages.PET_CHANGE_TYPE.get(this) + "§7 (§6§lNORMAL§7)", 50));
                break;
            case HORSE:
                int amount = 1;
                double currentSpeed = api.getNms().entity().getSpeed(getPet());

                if(currentSpeed == 0.25)
                    amount = 1;
                else if(currentSpeed == 0.5)
                    amount = 2;
                else if(currentSpeed == 0.75)
                    amount = 3;

                p.getInventory().setItem(2, ItemUtils.itemstack(Material.FEATHER, amount, "§f§n" + Messages.PET_CHANGE_SPEED.get(this)));
                p.getInventory().setItem(6, ItemUtils.itemstack(Material.LEATHER, 1, "§e§n" + Messages.PET_CHANGE_COLOR.get(this)));
                break;
            case MAGMA_CUBE:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.FIREBALL, 1, "§6§nFireball"));
                p.getInventory().setItem(6, ItemUtils.itemstack(Material.MAGMA_CREAM, ((MagmaCube) getPet()).getSize(), "§c§n" + Messages.PET_CHANGE_SIZE.get(this)));
                break;
            case MUSHROOM_COW:
                if(hasPetShroomTrail())
                    p.getInventory().setItem(2, ItemUtils.itemstack(Material.HUGE_MUSHROOM_2, 1, "§4§nShroom Trail§7 (" + Utils.statusString(getLanguage(), true) + "§7)", 14));
                else
                    p.getInventory().setItem(2, ItemUtils.itemstack(Material.HUGE_MUSHROOM_1, 1, "§4§nShroom Trail§7 (" + Utils.statusString(getLanguage(), false) + "§7)", 14));

                p.getInventory().setItem(6, ItemUtils.itemstack(Material.FIREWORK, 1, "§c§nBaby Firework"));
                break;
            case OCELOT:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.MONSTER_EGG, 1, "§e§nKitty Cannon", 98));
                p.getInventory().setItem(6, ItemUtils.itemstack(Material.RAW_FISH, 1, "§9§n" + Messages.PET_CHANGE_COLOR.get(this)));
                break;
            case PIG:
                if(hasPetBabyPigs())
                    p.getInventory().setItem(2, ItemUtils.itemstack(Material.MONSTER_EGG, 1, "§d§nBaby Pigs§7 (§a§l" + Utils.statusString(getLanguage(), true) + "§7)", 90));
                else
                    p.getInventory().setItem(2, ItemUtils.itemstack(Material.MONSTER_EGG, 1, "§d§nBaby Pigs§7 (§c§l" + Utils.statusString(getLanguage(), false) + "§7)", 90));

                int pigage = ((Pig) getPet()).isAdult() ? 2 : 1;

                p.getInventory().setItem(6, ItemUtils.itemstack(Material.PORK, pigage, "§d§n" + Messages.PET_CHANGE_AGE.get(this)));
                break;
            case SHEEP:
                if(hasPetSheepDisco())
                    p.getInventory().setItem(2, ItemUtils.itemstack(Material.WOOL, 1, "§f§nSheep Disco§7 (§a§l" + Utils.statusString(getLanguage(), true) + "§7)"));
                else
                    p.getInventory().setItem(2, ItemUtils.itemstack(Material.WOOL, 1, "§f§nSheep Disco§7 (§c§l" + Utils.statusString(getLanguage(), false) + "§7)"));

                DyeColor dyecolor = ((Sheep) getPet()).getColor();
                p.getInventory().setItem(6, ItemUtils.itemstack(Material.INK_SACK, 1, "§f§n" + Messages.PET_CHANGE_COLOR.get(this) + "§7 (" + ColorUtils.getName(dyecolor) + "§7)", dyecolor.getDyeData()));
                break;
            case SILVERFISH:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.MONSTER_EGG, 1, "§7§nSilverfish " + Messages.PET_BOMB.get(this), 60));
                p.getInventory().setItem(6, ItemUtils.itemstack(Material.STONE_HOE, 1, "§8§nLeap"));
                break;
            case SLIME:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§6§nSuper Jump"));
                p.getInventory().setItem(6, ItemUtils.itemstack(Material.SLIME_BALL, ((Slime) getPet()).getSize(), "§a§n" + Messages.PET_CHANGE_SIZE.get(this)));
                break;
            case SPIDER:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.WEB, 1, "§f§nWebs"));
                p.getInventory().setItem(6, ItemUtils.itemstack(Material.SPIDER_EYE, 1, "§5§nSpider Launcher"));
                break;
            case SQUID:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.INK_SACK, 1, "§8§nInk " + Messages.PET_BOMB.get(this)));
                p.getInventory().setItem(6, ItemUtils.itemstack(Material.WATER_BUCKET, 1, "§9§nWater Spout"));
                break;
            case WOLF:
                p.getInventory().setItem(2, ItemUtils.itemstack(Material.COOKED_BEEF, 1, "§6§nBark"));

                int wolfage = 1;
                if(((Wolf) getPet()).isAdult())
                    wolfage = 2;

                p.getInventory().setItem(6, ItemUtils.itemstack(Material.BONE, wolfage, "§7§n" + Messages.PET_CHANGE_AGE.get(this)));
                break;
            default:
                break;
        }
    }

    public void toServer(Server server){
        if(server.isOnline()){
            if(server == Server.MINIGAMES){
                if(api.getServerType() == Server.HUB) {
                    return;
                }

                server = Server.HUB;
                toMiniGameArea();
            }

            getPlayer().sendMessage(Messages.SERVER_CONNECTING.get(this, server.getName()));

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            try{
                out.writeUTF("Connect");
                out.writeUTF(server.toString().toLowerCase());
            }catch(IOException eee){}

            getPlayer().sendPluginMessage(api, "BungeeCord", b.toByteArray());
        }
        else{
            getPlayer().sendMessage(Messages.SERVER_OFFLINE.get(this, server.getName()));
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

    public void sendTimeMessage(int seconds, final String message, final Sound sound){
        new BukkitRunnable(){
            public void run(){
                Player p = getPlayer();
                p.sendMessage(message);

                if(sound != null)
                    p.playSound(p.getLocation(), sound, 5, 1);
            }
        }.runTaskLater(api, seconds * 20);
    }

    public void sendTimeMessages(int seconds, final Sound sound, final String... messages){
        new BukkitRunnable(){
            public void run(){
                Player p = getPlayer();
                for(String message : messages){
                    p.sendMessage(message);
                }

                if(sound != null)
                    p.playSound(p.getLocation(), sound, 5, 1);
            }
        }.runTaskLater(api, seconds * 20);
    }

    public boolean hasPerms(StaffRank rank){
        StaffRank staffrank = getStaffRank();

        if(staffrank == StaffRank.OWNER){
            return true;
        }

        if(rank == StaffRank.USER)
            return staffrank == StaffRank.USER || staffrank == StaffRank.BUILDER || staffrank == StaffRank.MODERATOR;
        else if(rank == StaffRank.BUILDER)
            return staffrank == StaffRank.BUILDER || staffrank == StaffRank.MODERATOR;
        else if(rank == StaffRank.MODERATOR)
            return staffrank == StaffRank.MODERATOR;
        else
            return false;
    }

    public boolean hasPerms(VIPRank rank){
        VIPRank viprank = getVipRank();

        if(getStaffRank() == StaffRank.OWNER && isOpMode())
            return true;

        if(rank == VIPRank.IRON_VIP)
            return viprank == VIPRank.IRON_VIP || viprank == VIPRank.GOLD_VIP || viprank == VIPRank.DIAMOND_VIP || viprank == VIPRank.EMERALD_VIP;
        else if(rank == VIPRank.GOLD_VIP)
            return viprank == VIPRank.GOLD_VIP || viprank == VIPRank.DIAMOND_VIP || viprank == VIPRank.EMERALD_VIP;
        else if(rank == VIPRank.DIAMOND_VIP)
            return viprank == VIPRank.DIAMOND_VIP || viprank == VIPRank.EMERALD_VIP;
        else if(rank == VIPRank.EMERALD_VIP)
            return viprank == VIPRank.EMERALD_VIP;
        else
            return true;
    }

    public void unknownCommand(String command){
        getPlayer().sendMessage(Messages.UNKNOWN_COMMAND.get(this, command));
    }

    public void requiredVIPRank(VIPRank viprank){
        Player p = getPlayer();

        p.sendMessage(Messages.VIPRANK_REQUIRED.get(this, viprank == VIPRank.IRON_VIP || viprank == VIPRank.EMERALD_VIP ? "an" : "a", viprank.getRankString()));
        p.playSound(p.getLocation(), Sound.BLOCK_LAVA_POP, 5, 1);
    }

    public void requiredVIPPoints(int price){
        Player p = getPlayer();

        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
        int needed = price - getVipPoints();

        p.sendMessage(Messages.REQUIRED_CURRENCY.get(this, "§b§l", needed + "", needed == 1 ? "VIP Point" : "VIP Points"));
    }

    public void requiredOrbitMinesTokens(int price){
        Player p = getPlayer();

        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
        int needed = price - getOrbitMinesTokens();

        p.sendMessage(Messages.REQUIRED_CURRENCY.get(this, "§e§l", needed + "", needed == 1 ? "OrbitMines Token" : "OrbitMines Tokens"));
    }

    public void disguiseAsBlock(int Id, Player... players){
        api.getNms().entity().disguiseAsBlock(getPlayer(), Id, players);

        setDisguised(true);
        setDisguiseBlockId(Id);
    }

    public void disguiseAsMob(EntityType type, boolean baby, String displayName, Player... players){
        Entity entity = api.getNms().entity().disguiseAsMob(getPlayer(), type, baby, displayName, players);

        setDisguised(true);
        setDisguise(entity);
        setDisguiseEntityType(type);
        setDisguisedBaby(baby);
    }

    public void disguiseAsMob(EntityType type, boolean baby, Player... players){
        disguiseAsMob(type, baby, null, players);
    }

    public void unDisguise(){
        Player p = getPlayer();
        this.disguised = false;

        for(Player player : Bukkit.getOnlinePlayers()){
            player.hidePlayer(p);
            player.showPlayer(p);
        }
    }

    public void resetScoreboard(){
        getScoreboard().set(null);
    }

    public void clearScoreboard(){
        getScoreboard().clear();
    }

    public void setScoreboard(ScoreboardSet set){
        set.updateTitle();
        set.updateScores();
        set.updateTeams();

        getScoreboard().set(set);
    }

    public void updateScoreboard(){
        getScoreboard().update();
    }

    /* Items */
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
        List<ItemStack> items = new ArrayList<>();
        for(ItemStack item : getPlayer().getInventory().getContents()){
            if(item != null && item.getType() == material && item.getDurability() == durability){
                getPlayer().getInventory().remove(item);
                items.add(item);
            }
        }
        List<ItemStack> itemsToRemove = new ArrayList<>();
        List<ItemStack> itemsToAdd = new ArrayList<>();

        int iAmount = 0;
        for(ItemStack item : items){
            if(iAmount != amount){
                if(iAmount + item.getAmount() <= amount){
                    itemsToRemove.add(item);

                    iAmount += item.getAmount();
                }
                else{
                    itemsToRemove.add(item);

                    ItemStack item3 = new ItemStack(item);
                    item3.setAmount(item.getAmount() - (amount - iAmount));
                    itemsToAdd.add(item3);

                    iAmount = amount;
                }
            }
        }

        for(ItemStack item : itemsToRemove){
            items.remove(item);
        }
        for(ItemStack item : itemsToAdd){
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

    public void sendQuitMessage(){
        if(api.getServerType() != Server.MINIGAMES){
            if(isSilent()){
                for(OMPlayer omp : api.getOMPlayers()){
                    if(omp.hasPerms(StaffRank.MODERATOR))
                        omp.getPlayer().sendMessage(Messages.QUIT_SILENT.get(omp, getName()));
                }
            }
            else{
                for(OMPlayer omp : api.getOMPlayers()){
                    omp.getPlayer().sendMessage(Messages.QUIT.get(omp, getName()));
                }
            }
        }
    }

    public void sendJoinMessage(){
        Title t = new Title("§6§lOrbitMines§4§lNetwork", api.getServerType().getName(), 20, 40, 20);
        t.send(getPlayer());

        final OMPlayer omPlayer = this;

        new BukkitRunnable(){
            public void run(){
                Player p = getPlayer();
                p.sendMessage("§7§m----------------------------------------");
                p.sendMessage(" §6§lOrbitMines§4§lNetwork §7- " + api.getServerType().getName());
                p.sendMessage(" ");
                p.sendMessage(" §7§lWebsite: §6www.orbitmines.com");
                p.sendMessage(" §7§l" + Messages.WORD_DONATE.get(omPlayer) + ": §3shop.orbitmines.com");
                p.sendMessage(" §7§l" + Messages.WORD_VOTE.get(omPlayer) + ": §bwww.orbitmines.com/voten");
                p.sendMessage(" ");

                ComponentMessage cm = new ComponentMessage();
                cm.addPart(" §7§l" + Messages.WORD_SPAWN_BUILT_BY.get(omPlayer) +  ": ");
                cm.addPart("§e§l[" + Messages.WORD_VIEW.get(omPlayer) + "]", HoverEvent.Action.SHOW_TEXT, api.getServerPlugin() == null ? "" : api.getServerPlugin().getSpawnBuilders());
                cm.send(p);

                p.sendMessage(" §7§l" + Messages.WORD_DEVELOPED_BY.get(omPlayer) + ": §4§lOwner §4O_o_Fadi_o_O");
                p.sendMessage("§7§m----------------------------------------");
            }
        }.runTaskLater(api, 20);

        if(api.getServerType() != Server.MINIGAMES){
            if(isSilent()){
                for(OMPlayer omp : api.getOMPlayers()){
                    if(omp.hasPerms(StaffRank.MODERATOR))
                        omp.getPlayer().sendMessage(Messages.JOIN_SILENT.get(omp, getName()));
                }
            }
            else{
                for(OMPlayer omp : api.getOMPlayers()){
                    omp.getPlayer().sendMessage(Messages.JOIN.get(omp, getName()));
                }
            }
        }
    }

    public void registerVote(){
        vote();

        Player p = getPlayer();
        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);
        p.sendMessage("");
        p.sendMessage(Messages.CMD_VOTE_THANKS.get(this, getPlayer().getName()));
        p.sendMessage(Messages.CMD_VOTE_SERVER.get(this, api.getServerType().getName()));
        p.sendMessage("§b§lVote §8| §7");
        for(String s : api.getServerType().getVoteRewardMessages()){
            p.sendMessage("§b§lVote §8| §7  - " + s);
        }
        p.sendMessage("§b§lVote §8| §7");
        p.sendMessage(Messages.CMD_VOTE_AT.get(this));
        p.sendMessage(Messages.CMD_VOTE_TOTAL.get(this, this.getVotes() + ""));

        for(OMPlayer omp : api.getOMPlayers()){
            omp.getPlayer().sendMessage(Messages.CMD_VOTE_PLAYER.get(omp, getName()));
        }
    }

    /* login / logout */

    public void load(){
        this.loaded = false;
        this.opMode = false;
        this.disguised = false;
        this.afk = false;

        getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

        String uuid = getUUID().toString();

        try{
            loadMonthlyVIPPoints(uuid);
            loadSilentJoin(uuid);
            loadVIPRank(uuid);
            loadStaffRank(uuid);
            loadLanguage(uuid);
            loadVotes(uuid);
            loadOrbitMinesTokens(uuid);
            loadVIPPoints(uuid);
            loadNickname(uuid);

            OrbitMinesServer server = api.getServerPlugin();
            if(server != null) {
                if (server.chatcolorsEnabled())
                    loadChatColors(uuid);
                if (server.disguisesEnabled()) {
                    loadDisguises(uuid);

                    for(final OMPlayer omp : api.getOMPlayers()){
                        if(!hasPlayersEnabled()){
                            omp.getPlayer().hidePlayer(getPlayer());
                        }
                        else{
                            omp.getPlayer().showPlayer(getPlayer());

                            if(omp.isDisguised()){
                                if(omp.getDisguiseEntityType() != null){
                                    new BukkitRunnable(){
                                        public void run(){
                                            omp.disguiseAsMob(omp.getDisguiseEntityType(), isDisguisedBaby(), omp.getDisguise().getCustomName(), getPlayer());
                                        }
                                    }.runTaskLater(api, 40);
                                }
                                else{
                                    new BukkitRunnable(){
                                        public void run(){
                                            omp.disguiseAsBlock(omp.getDisguiseBlockId(), getPlayer());
                                        }
                                    }.runTaskLater(api, 40);
                                }
                            }
                        }
                    }
                }
                if (server.fireworksEnabled())
                    loadFirework(uuid);
                if (server.gadgetsEnabled()) {
                    loadGadgets(uuid);
                    loadStackerEnabled(uuid);
                    loadPlayersEnabled(uuid);
                }
                if (server.hatsEnabled())
                    loadHats(uuid);
                if (server.petsEnabled())
                    loadPets(uuid);
                if (server.trailsEnabled())
                    loadTrails(uuid);
                if (server.wardrobeEnabled())
                    loadWardrobe(uuid);
            }

            loadPlayerData();

            setTabList("§6§lOrbitMines§4§lNetwork", "§7Website: §6www.orbitmines.com §8| §7Twitter: §b@OrbitMines §8| §7" + Messages.WORD_DONATE.get(this) + ": §3shop.orbitmines.com");

            api.getVoteManager().check(this);
            setLoaded(true);
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }

    public void logOut(){
        getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

        if(isDisguised())
            unDisguise();

        if(getPetEnabled() != null)
            disablePet();

        if(getSoccerMagmaCube() != null)
            disableSoccerMagmaCube();

        unloadPlayerData();

        String uuid = getUUID().toString();
        updateLanguage(uuid);
        updateNickname(uuid);

        OrbitMinesServer server = api.getServerPlugin();
        if(server != null) {
            if (server.chatcolorsEnabled())
                updateChatColors(uuid);
            if (server.fireworksEnabled())
                updateFirework(uuid);
            if (server.hatsEnabled())
                updateHats(uuid);
            if (server.trailsEnabled())
                updateTrails(uuid);
            if (server.wardrobeEnabled())
                updateWardrobe(uuid);
            if(server.gadgetsEnabled()){
                updateStackerEnabled(uuid);
                updatePlayerEnabled(uuid);
            }
        }

        api.getOMPlayers().remove(this);
    }

    /* OMPlayer getter */

    public static OMPlayer getOMPlayer(Player player){
        if(api == null)
            api = OrbitMinesAPI.getApi();

        OMPlayer omp = api.getPlayers().get(player);
        if(omp != null)
            return omp;

        return null;
    }

    /* load */
    private void loadMonthlyVIPPoints(String uuid){
        this.receivedMonthlyBonus = Database.get().containsPath("PlayerMonthlyVIPPoints", "uuid", "uuid", uuid);
    }

    private void loadSilentJoin(String uuid){
        if(Database.get().containsPath("PlayerSilentJoin", "uuid", "uuid", uuid))
            this.silent = Boolean.parseBoolean(Database.get().getString("PlayerSilentJoin", "silentjoin", "uuid", uuid));
        else
            this.silent = false;
    }

    private void loadVIPRank(String uuid){
        if(Database.get().containsPath("Rank-VIP", "uuid", "uuid", uuid))
            this.vipRank = VIPRank.valueOf(Database.get().getString("Rank-VIP", "vip", "uuid", uuid).toUpperCase());
        else
            this.vipRank = VIPRank.USER;
    }

    private void loadStaffRank(String uuid){
        if(Database.get().containsPath("Rank-Staff", "uuid", "uuid", uuid))
            this.staffRank = StaffRank.valueOf(Database.get().getString("Rank-Staff", "staff", "uuid", uuid).toUpperCase());
        else
            this.staffRank = StaffRank.USER;
    }

    private void loadLanguage(String uuid){
        if(Database.get().containsPath("Language", "uuid", "uuid", uuid))
            this.language = Language.valueOf(Database.get().getString("Language", "language", "uuid", uuid));
        else{
            Database.get().insert("Language", "uuid`, `language", uuid + "', '" + Language.DUTCH.toString());
            this.language = Language.DUTCH;
        }
    }

    private void loadVotes(String uuid){
        if(Database.get().containsPath("Votes", "uuid", "uuid", uuid)) {
            this.votes = Database.get().getInt("Votes", "votes", "uuid", uuid);
        }
        else {
            Database.get().insert("Votes", "uuid`, `votes", uuid + "', '" + 0);
            this.votes = 0;
        }
    }

    private void loadOrbitMinesTokens(String uuid){
        if(Database.get().containsPath("OrbitMinesTokens", "uuid", "uuid", uuid)) {
            this.orbitMinesTokens = Database.get().getInt("OrbitMinesTokens", "omt", "uuid", uuid);
        }
        else{
            Database.get().insert("OrbitMinesTokens", "uuid`, `omt", uuid + "', '" + 0);
            this.orbitMinesTokens = 0;
        }
    }

    private void loadVIPPoints(String uuid){
        if(Database.get().containsPath("VIPPoints", "uuid", "uuid", uuid)){
            this.vipPoints = Database.get().getInt("VIPPoints", "points", "uuid", uuid);
            this.newPlayer = false;
        }
        else{
            Database.get().insert("VIPPoints", "uuid`, `points", uuid + "', '" + 0);
            this.vipPoints = 0;
            this.newPlayer = true;
        }
    }

    private void loadFirework(String uuid){
        if(Database.get().containsPath("Fireworks-Passes", "uuid", "uuid", uuid)){
            this.fireworkPasses = Database.get().getInt("Fireworks-Passes", "passes", "uuid", uuid);
        }
        else{
            Database.get().insert("Fireworks-Passes", "uuid`, `passes", uuid + "', '" + 0);
            this.fireworkPasses = 0;
        }

        if(Database.get().containsPath("Fireworks-Settings", "uuid", "uuid", uuid)){
            String[] fireworkSettings = Database.get().getString("Fireworks-Settings", "settings", "uuid", uuid).split("\\|");
            if(fireworkSettings[6].equals("null"))
                fireworkSettings[6] = "BALL";

            this.fireworkSettings = new FireworkSettings(ColorUtils.parse(fireworkSettings[0]), ColorUtils.parse(fireworkSettings[1]), ColorUtils.parse(fireworkSettings[2]), ColorUtils.parse(fireworkSettings[3]), Boolean.parseBoolean(fireworkSettings[4]), Boolean.parseBoolean(fireworkSettings[5]), FireworkEffect.Type.valueOf(fireworkSettings[6]));
        }
        else{
            Database.get().insert("Fireworks-Settings", "uuid`, `settings", uuid + "', '" + "AQUA|null|null|null|false|false|BALL");
            this.fireworkSettings = new FireworkSettings(org.bukkit.Color.AQUA, null, null, null, false, false, FireworkEffect.Type.BALL);
        }
    }

    private void loadGadgets(String uuid){
        this.gadgets = new ArrayList<>();
        this.sgaSnowGolems = new ArrayList<>();
        for(Gadget gadget : Gadget.values()){
            if(gadget != Gadget.STACKER && gadget != Gadget.FIREWORK_GUN && Database.get().containsPath("Gadgets-" + gadget.getDatabaseName(), "uuid", "uuid", uuid))
                this.gadgets.add(gadget);
        }
    }

    private void loadPets(String uuid){
        this.pets = new ArrayList<>();
        this.petNames = new HashMap<>();
        for(Pet pet : Pet.values()){
            if(Database.get().containsPath("Pets-" + pet.getDatabaseName(), "uuid", "uuid", uuid)){
                this.pets.add(pet);
                this.petNames.put(pet, Database.get().getString("Pets-" + pet.getDatabaseName(), "petname", "uuid", uuid));
            }
        }
    }

    private void loadWardrobe(String uuid){
        this.wardrobe = new ArrayList<>();
        for(Color color : ColorUtils.WARDROBE){
            if(Database.get().containsPath("Wardrobe-" + color.getDatabaseName(), "uuid", "uuid", uuid))
                this.wardrobe.add(color);
        }

        if(Database.get().containsPath("Wardrobe-Disco", "uuid", "uuid", uuid)){
            this.wardrobeDisco = Boolean.parseBoolean(Database.get().getString("Wardrobe-Disco", "disco", "uuid", uuid));
            this.unlockedWardrobeDisco = true;
        }
        else{
            this.wardrobeDisco = false;
            this.unlockedWardrobeDisco = false;
        }
    }

    private void loadChatColors(String uuid){
        this.chatColors = new ArrayList<>();
        for(ChatColor chatcolor : ChatColor.values()){
            if(chatcolor.getVIPRank() == null && chatcolor != ChatColor.GRAY && Database.get().containsPath("ChatColors-" + chatcolor.getDatabaseName(), "uuid", "uuid", uuid))
                this.chatColors.add(chatcolor);
        }

        if(Database.get().containsPath("ChatColors-Bold", "uuid", "uuid", uuid)){
            this.chatColorBold = Boolean.parseBoolean(Database.get().getString("ChatColors-Bold", "bold", "uuid", uuid));
            this.unlockedChatColorBold = true;
        }
        else{
            this.chatColorBold = false;
            this.unlockedChatColorBold = false;
        }

        if(Database.get().containsPath("ChatColors-Cursive", "uuid", "uuid", uuid)){
            this.chatColorCursive = Boolean.parseBoolean(Database.get().getString("ChatColors-Cursive", "cursive", "uuid", uuid));
            this.unlockedChatColorCursive = true;
        }
        else{
            this.chatColorCursive = false;
            this.unlockedChatColorCursive = false;
        }

        if(Database.get().containsPath("ChatColors", "uuid", "uuid", uuid)){
            try{
                this.chatColor = ChatColor.valueOf(Database.get().getString("ChatColors", "color", "uuid", uuid));
            }catch(IllegalArgumentException ex){
                this.chatColor = ChatColor.GRAY;
            }
        }
        else{
            Database.get().insert("ChatColors", "uuid`, `color", uuid + "', '" + ChatColor.GRAY.toString());
            this.chatColor = ChatColor.GRAY;
        }
    }

    private void loadTrails(String uuid){
        if(Database.get().containsPath("Trail", "uuid", "uuid", uuid))
            this.trail = Trail.valueOf(Database.get().getString("Trail", "trail", "uuid", uuid));

        this.trails = new ArrayList<>();
        for(Trail trail : Trail.values()){
            if(trail.getVIPRank() == null && Database.get().containsPath("Trails-" + trail.getDatabaseName(), "uuid", "uuid", uuid))
                this.trails.add(trail);
        }

        if(Database.get().containsPath("Trails-Type", "uuid", "uuid", uuid)){
            this.trailType = TrailType.valueOf(Database.get().getString("Trails-Type", "type", "uuid", uuid));
        }
        else{
            Database.get().insert("Trails-Type", "uuid`, `type", uuid + "', '" + TrailType.BASIC_TRAIL.toString());
            this.trailType = TrailType.BASIC_TRAIL;
        }

        this.trailTypes = new ArrayList<>();
        for(TrailType trailtype : TrailType.values()){
            if(trailtype != TrailType.BASIC_TRAIL && Database.get().containsPath("Trails-" + trailtype.getDatabaseName(), "uuid", "uuid", uuid))
                this.trailTypes.add(trailtype);
        }

        if(Database.get().containsPath("Trails-TypeSpecial", "uuid", "uuid", uuid)){
            this.specialTrail = Boolean.parseBoolean(Database.get().getString("Trails-TypeSpecial", "special", "uuid", uuid));
            this.unlockedSpecialTrail = true;
        }
        else{
            this.specialTrail = false;
            this.unlockedSpecialTrail = false;
        }

        if(Database.get().containsPath("Trails-TypeParticlesAmount", "uuid", "uuid", uuid)){
            this.trailParticleAmount = Database.get().getInt("Trails-TypeParticlesAmount", "amount", "uuid", uuid);
        }
        else{
            Database.get().insert("Trails-TypeParticlesAmount", "uuid`, `amount", uuid + "', '" + 1);
            this.trailParticleAmount = 1;
        }
    }

    private void loadHats(String uuid){
        this.hats = new ArrayList<>();
        for(Hat hat : Hat.values()){
            if(hat.getVIPRank() == null && Database.get().containsPath("Hats-" + hat.getDatabaseName(), "uuid", "uuid", uuid))
                this.hats.add(hat);
        }

        this.hatsInvPage = 1;

        if(Database.get().containsPath("Hats-BlockTrail", "uuid", "uuid", uuid)){
            this.hatsBlockTrail = Boolean.parseBoolean(Database.get().getString("Hats-BlockTrail", "blocktrail", "uuid", uuid));
            this.unlockedHatsBlockTrail = true;
        }
        else{
            this.hatsBlockTrail = false;
            this.unlockedHatsBlockTrail = false;
        }
    }

    private void loadDisguises(String uuid){
        this.disguises = new ArrayList<>();
        for(Disguise disguise : Disguise.values()){
            if(disguise.getVIPRank() == null && Database.get().containsPath("Dis-" + disguise.getDatabaseName(), "uuid", "uuid", uuid))
                this.disguises.add(disguise);
        }
    }

    private void loadNickname(String uuid){
        if(Database.get().containsPath("PlayerNicknames", "uuid", "uuid", uuid))
            this.nickName = Database.get().getString("PlayerNicknames", "nick", "uuid", uuid);
    }

    private void loadStackerEnabled(String uuid){
        if(Database.get().containsPath("Hub-Stacker", "uuid", "uuid", uuid)){
            this.stackerEnabled = Boolean.parseBoolean(Database.get().getString("Hub-Stacker", "stacker", "uuid", uuid));
        }
        else{
            Database.get().insert("Hub-Stacker", "uuid`, `stacker", uuid + "', '" + true);
            this.stackerEnabled = true;
        }
    }

    private void loadPlayersEnabled(String uuid){
        if(Database.get().containsPath("Hub-Players", "uuid", "uuid", uuid)){
            this.playersEnabled = Boolean.parseBoolean(Database.get().getString("Hub-Players", "players", "uuid", uuid));
        }
        else{
            Database.get().insert("Hub-Players", "uuid`, `players", uuid + "', '" + false);
            this.playersEnabled = false;
        }
    }

    /* update */

    private void updateNickname(String uuid){
        if(Database.get().containsPath("PlayerNicknames", "uuid", "uuid", uuid)){
            if(getNickName() != null)
                Database.get().update("PlayerNicknames", "nick", getNickName().replace("'", "`"), "uuid", uuid);
            else
                Database.get().delete("PlayerNicknames", "uuid", uuid);
        }
        else{
            if(getNickName() != null)
                Database.get().insert("PlayerNicknames", "uuid`, `nick", uuid + "', '" + getNickName());
        }
    }

    private void updateTrails(String uuid){
        if(Database.get().containsPath("Trail", "uuid", "uuid", uuid)){
            if(hasTrailEnabled())
                Database.get().update("Trail", "trail", getTrail().toString(), "uuid", uuid);
            else
                Database.get().delete("Trail", "uuid", uuid);
        }
        else{
            if(hasTrailEnabled())
                Database.get().insert("Trail", "uuid`, `trail", uuid + "', '" + getTrail());
        }

        Database.get().update("Trails-TypeParticlesAmount", "amount", "" + getTrailParticleAmount(), "uuid", uuid);
        Database.get().update("Trails-TypeSpecial", "special", "" + hasSpecialTrail(), "uuid", uuid);
        Database.get().update("Trails-Type", "type", getTrailType().toString(), "uuid", uuid);
    }

    private void updateWardrobe(String uuid){
        if(Database.get().containsPath("Wardrobe-Disco", "uuid", "uuid", uuid))
            Database.get().update("Wardrobe-Disco", "disco", isWardrobeDisco() + "", "uuid", uuid);
    }

    private void updateFirework(String uuid){
        Database.get().update("Fireworks-Settings", "settings", getFireworkSettings().toString(), "uuid", uuid);
        Database.get().update("Fireworks-Passes", "passes", "" + getFireworkPasses(), "uuid", uuid);
    }

    private void updateLanguage(String uuid){
        Database.get().update("Language", "language", getLanguage().toString(), "uuid", uuid);
    }

    private void updateHats(String uuid){
        if(hasUnlockedHatsBlockTrail())
            Database.get().update("Hats-BlockTrail", "blocktrail", hasHatsBlockTrail() + "", "uuid", uuid);
    }

    private void updateChatColors(String uuid){
        Database.get().update("ChatColors", "color", getChatColor().toString(), "uuid", uuid);
        if(hasUnlockedChatColorBold())
            Database.get().update("ChatColors-Bold", "bold", isChatColorBold() + "", "uuid", uuid);
        if(hasUnlockedChatColorCursive())
            Database.get().update("ChatColors-Cursive", "cursive", isChatColorCursive() + "", "uuid", uuid);
    }

    private void updateStackerEnabled(String uuid){
        Database.get().update("Hub-Stacker", "stacker", hasStackerEnabled() + "", "uuid", getUUID().toString());
    }

    private void updatePlayerEnabled(String uuid){
        Database.get().update("Hub-Players", "players", hasPlayersEnabled() + "", "uuid", getUUID().toString());
    }
}
